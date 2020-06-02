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
import com.virgo.exam.vo.AnswerVO;
import com.virgo.exam.vo.PublishExamPaperVO;
import com.virgo.exam.vo.QuestionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    public PageResult<PublishExamPaperVO> findPage(@Valid ExamPaperQueryParam questionQueryParam) {
        PublishExamPaper query = BeanUtil.copyProperties(questionQueryParam, PublishExamPaper.class);
        query.setMemberId(RequestHolder.getMemberId());
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
        return publishExamPaperRepository.findById(id).map(publishExamPaper -> {
            ExamPaper examPaper = examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.EXAM_RECORD_NOT_FOUND));
            List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaperId(examPaper.getId());
            PublishExamPaperVO publishExamPaperVO = BeanUtil.copyProperties(examPaper, PublishExamPaperVO.class);
            BeanUtil.copyProperties(examPaper, publishExamPaperVO, "id", "examPaperId", "status", "startTime", "createTime", "version");
            publishExamPaperVO.setExamPaperId(publishExamPaper.getExamPaperId());
            List<QuestionVO> questionVOS = questions.stream().map(question -> {
                List<Question.Answer> answers = question.getAnswer();
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
            }).collect(Collectors.toList());
            publishExamPaperVO.setQuestions(questionVOS);
            return publishExamPaperVO;
        }).orElse(null);
    }
}
