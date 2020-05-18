package com.virgo.exam.service;

import cn.hutool.core.util.BooleanUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virgo.common.JsonUtils;
import com.virgo.common.RequestHolder;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.exam.dto.ExamSaveParam;
import com.virgo.exam.model.*;
import com.virgo.exam.repository.ExamPaperQuestionRepository;
import com.virgo.exam.repository.ExamPaperRecordRepository;
import com.virgo.exam.repository.ExamPaperRepository;
import com.virgo.exam.repository.PersonalExamPaperRecordRepository;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.interfaces.StringSimilarity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ExamService {
    @Resource
    private PersonalExamPaperRecordRepository personalExamPaperRecordRepository;
    @Resource
    private ExamPaperRecordRepository examPaperRecordRepository;
    @Resource
    private ExamPaperRepository examPaperRepository;
    @Resource
    private ExamPaperQuestionRepository examPaperQuestionRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Object exam(ExamSaveParam examSaveParam) {
        PersonalExamPaper personalExamPaper = getPersonalExamPaper(examSaveParam);

        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.START)) {
            return createRecord(personalExamPaper);
        }
        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.EXAMIING)) {
            examPaperRecordRepository.updateAnswerById(JsonUtils.toJson(examSaveParam.getAnswers()), examSaveParam.getRecordId());
            return null;
        }
        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.CONTINUE)) {
            //todo 可间断考试
            return null;
        }
        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.SUBMIT)) {
            ExamPaperRecord examPaperRecord = examPaperRecordRepository.findById(examSaveParam.getRecordId()).orElseThrow(() -> new BusinessException(ResultEnum.EXAM_RECORD_NOT_FOUND));

            examPaperRecord.setAnswer(JsonUtils.toJson(examSaveParam.getAnswers()));
            ExamPaper examPaper = examPaperRepository.findById(personalExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));

            List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaper(examPaper);

            int score = questions.stream().mapToInt(question -> examSaveParam.getAnswers().stream().filter(re -> Objects.equals(re.getQuestionId(), question.getId()))
                    .findFirst().map(it -> {
                        if (question.getType() == Question.Type.SINGLE_SELECT || question.getType() == Question.Type.TRUE_FALSE) {
                            Object correctAnswer = JsonUtils.parse(question.getCorrectAnswer(), List.class).get(0);
                            return Objects.equals(it.getAnswer(), correctAnswer.toString()) ? question.getScore() : 0;
                        }
                        if (question.getType() == Question.Type.MULTI_SELECT) {


                            List<Question.Answer> answersss = JsonUtils.parse(question.getAnswer(), new TypeReference<>() {
                            });

                            List<String> cAnswer = JsonUtils.parse(question.getCorrectAnswer(), new TypeReference<>() {
                            });
                            List<String> userAns = JsonUtils.parse(it.getAnswer(), new TypeReference<>() {
                            });


                            List<Question.Answer> isTrue = answersss.stream()
                                    .filter(answer -> cAnswer.contains(answer.getPrefix()))
                                    .filter(c -> userAns.contains(c.getPrefix()))
                                    .collect(Collectors.toList());

                            return isTrue.stream().mapToInt(i -> i.getScore() == null ? 0 : i.getScore()).sum();
                        }
                        if (question.getType() == Question.Type.COMPLETION) {
//                                        List<String> userAns = JsonUtils.parse(it.getAnswer(), new TypeReference<>() {
//                                        });
//                                        List<String> cAnswer = JsonUtils.parse(question.getCorrectAnswer(), new TypeReference<>() {
//                                        });
//                                        if (userAns.size() != cAnswer.size())
//                                            return 0;
//
                            return Objects.equals(it.getAnswer(), question.getCorrectAnswer()) ? question.getScore() : 0;
                        }

                        if (question.getType() == Question.Type.SHORT_ANSWER) {
                            StringSimilarity stringSimilarity = new Jaccard();
                            if (StringUtils.isEmpty(it.getAnswer())) return 0;
                            double result = stringSimilarity.similarity(question.getCorrectAnswer(), it.getAnswer());
                            return (int) result * question.getScore();
                        }
                        return 0;
                    }).orElse(0)).sum();
            examPaperRecord.setExamScore(score);

            //如果设置最大考试次数且 当前考试次数大于等于最大考试次数则考试结束
            if (examPaper.getMaxExamCount() > 0 && personalExamPaper.getExamCount() + 1 >= examPaper.getMaxExamCount())
                personalExamPaper.setStatus(PersonalExamPaper.Status.END);
            personalExamPaper.setExamCount(personalExamPaper.getExamCount() + 1);
            //设置考试最高分数
            if (personalExamPaper.getHighestScore() == null || examPaperRecord.getExamScore() > personalExamPaper.getHighestScore())
                personalExamPaper.setHighestScore(examPaperRecord.getExamScore());
            //设置考试最低分数
            if (personalExamPaper.getLowestScore() == null || examPaperRecord.getExamScore() < personalExamPaper.getLowestScore())
                personalExamPaper.setLowestScore(examPaperRecord.getExamScore());
            //设置考试是否通过
            if (BooleanUtil.isFalse(personalExamPaper.getPass()) && examPaper.getPassScore() != null && examPaperRecord.getExamScore() >= examPaper.getPassScore()) {
                personalExamPaper.setPass(true);
            }
            personalExamPaperRecordRepository.save(personalExamPaper);
            examPaperRecordRepository.save(examPaperRecord);

            return examPaperRecord;
        }
        return null;
    }

    private Object createRecord(PersonalExamPaper personalExamPaper) {
        ExamPaperRecord record = new ExamPaperRecord();
        record.setUserId(Long.valueOf(RequestHolder.getMemberId()));
        record.setExamPaperId(personalExamPaper.getExamPaperId());
        record.setStartTime(LocalDateTime.now());
        record.setPass(false);
        record.setPersonalExamPaperId(personalExamPaper.getId());
        return examPaperRecordRepository.save(record);
    }

    private PersonalExamPaper getPersonalExamPaper(ExamSaveParam examSaveParam) {
        PersonalExamPaper personalExamPaper;
        ExamPaper examPaper;
        String exam = stringRedisTemplate.opsForValue().get(RequestHolder.getMemberId());
        if (StringUtils.isEmpty(exam)) {
            personalExamPaper = personalExamPaperRecordRepository.findById(examSaveParam.getExamId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
            if (personalExamPaper.getStatus() == PersonalExamPaper.Status.END)
                throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
            examPaper = examPaperRepository.findById(personalExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
            stringRedisTemplate.opsForValue().set(RequestHolder.getMemberId() + "examPaper", JsonUtils.toJson(examPaper), examPaper.getExamTime() + 30, TimeUnit.SECONDS);
        } else {
            personalExamPaper = JsonUtils.parse(exam, PersonalExamPaper.class);
            if (!Objects.equals(personalExamPaper.getId(), examSaveParam.getExamId()))
                throw new BusinessException(ResultEnum.PARAM_ERROR);//当前用户上传考试id和正在考试记录id冲突
        }
        return personalExamPaper;
    }
}
