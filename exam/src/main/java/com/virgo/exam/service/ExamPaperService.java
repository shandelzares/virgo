package com.virgo.exam.service;

import cn.hutool.core.bean.BeanUtil;
import com.virgo.common.RequestHolder;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.common.page.PageResult;
import com.virgo.exam.dto.ExamPaperQueryParam;
import com.virgo.exam.model.ExamPaper;
import com.virgo.exam.model.ExamPaperQuestion;
import com.virgo.exam.model.PublishExamPaper;
import com.virgo.exam.model.Question;
import com.virgo.exam.repository.ExamPaperQuestionRepository;
import com.virgo.exam.repository.ExamPaperRepository;
import com.virgo.exam.repository.PublishExamPaperRepository;
import com.virgo.exam.repository.QuestionRepository;
import com.virgo.exam.vo.AnswerVO;
import com.virgo.exam.vo.PublishExamPaperVO;
import com.virgo.exam.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamPaperService {
    @Resource
    private ExamPaperRepository examPaperRepository;
    @Resource
    private ExamPaperQuestionRepository examPaperQuestionRepository;
    @Resource
    private PublishExamPaperRepository publishExamPaperRepository;
    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private MongoTemplate mongoTemplate;

    public PageResult<PublishExamPaperVO> findPage(@Valid ExamPaperQueryParam questionQueryParam) {
        PublishExamPaper query = BeanUtil.copyProperties(questionQueryParam, PublishExamPaper.class);
        query.setUserId(RequestHolder.getUserId());
        Page<PublishExamPaper> personalExamPapers = publishExamPaperRepository.findAll(Example.of(query), questionQueryParam.pageable());

        List<ExamPaper> examPapers = examPaperRepository.findByIdIn(personalExamPapers.getContent().stream().map(PublishExamPaper::getExamPaperId).collect(Collectors.toList()));

        return new PageResult<>(personalExamPapers.getTotalElements(), personalExamPapers.stream().map(publishExamPaper -> {
            PublishExamPaperVO vo = BeanUtil.copyProperties(publishExamPaper, PublishExamPaperVO.class);
            examPapers.forEach(examPaper -> {
                if (Objects.equals(publishExamPaper.getExamPaperId(), examPaper.getId())) {
                    BeanUtil.copyProperties(examPaper, vo, "id", "examPaperId", "status", "startTime", "createTime", "version");
                }
            });
            return vo;
        }).collect(Collectors.toList()));
    }

    public PublishExamPaperVO findById(String id) {
        LocalDateTime now = LocalDateTime.now();
        return publishExamPaperRepository.findById(id).map(publishExamPaper -> {
            ExamPaper examPaper = examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.EXAM_RECORD_NOT_FOUND));
            if (examPaper.getExamEndTime().isBefore(now)) {
                throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
            }
            if (examPaper.getExamStartTime().isAfter(now)) {
                throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_START);
            }
            if (publishExamPaper.getExamCount() >= examPaper.getMaxExamCount()) {
                throw new BusinessException(ResultEnum.EXAM_COUNT_USED_ALL);
            }


            List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaperId(examPaper.getId());
            PublishExamPaperVO publishExamPaperVO = BeanUtil.copyProperties(publishExamPaper, PublishExamPaperVO.class);
            BeanUtil.copyProperties(examPaper, publishExamPaperVO, "id", "examPaperId", "status", "startTime", "createTime", "version");
            publishExamPaperVO.setExamPaperId(publishExamPaper.getExamPaperId());
            List<QuestionVO> questionVOS = new ArrayList<>();
            for (ExamPaperQuestion question : questions) {
                if (question.getType() == Question.Type.RANDOM) {
                    String category = question.getRandomConfig().getCategory();
                    Criteria criteria = Criteria.where("category").is(category);
                    if (question.getRandomConfig().getType() != null)
                        criteria.and("type").is(question.getRandomConfig().getType());


                    AggregationResults<Question> qus = mongoTemplate.aggregate(
                            Aggregation.newAggregation(
                                    Aggregation.match(criteria),
                                    Aggregation.sample(question.getRandomConfig().getQuestionCount())),
                            Question.class, Question.class);
                    qus.forEach(q -> {
                        QuestionVO questionVO = getQuestionVO(q, q.getAnswer());
                        questionVOS.add(questionVO);
                    });
                    continue;
                }
                List<Question.Answer> answers = question.getAnswer();
                QuestionVO questionVO = getQuestionVO(question, answers);
                questionVOS.add(questionVO);
            }

            publishExamPaperVO.setQuestions(questionVOS);
            return publishExamPaperVO;
        }).orElse(null);
    }

    private QuestionVO getQuestionVO(Object question, List<Question.Answer> answers) {
        List<AnswerVO> answerVOS = answers.stream().map(answer -> {
            AnswerVO answerVO = BeanUtil.copyProperties(answer, AnswerVO.class);
            answerVO.setIsCorrect(null);
            return answerVO;
        }).collect(Collectors.toList());
        QuestionVO questionVO = BeanUtil.copyProperties(question, QuestionVO.class);
        questionVO.setAnswer(answerVOS);
        questionVO.setAnalysis(null);
        questionVO.setShortAnswerAnalysis(null);
        return questionVO;
    }
}
