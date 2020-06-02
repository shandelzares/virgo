package com.virgo.exam.service;

import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.exam.dto.ExamSaveParam;
import com.virgo.exam.model.PublishExamPaper;
import com.virgo.exam.repository.ExamPaperQuestionRepository;
import com.virgo.exam.repository.ExamPaperRecordRepository;
import com.virgo.exam.repository.ExamPaperRepository;
import com.virgo.exam.repository.PublishExamPaperRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ExamService {
    @Resource
    private PublishExamPaperRepository publishExamPaperRepository;
    @Resource
    private ExamPaperRecordRepository examPaperRecordRepository;
    @Resource
    private ExamPaperRepository examPaperRepository;
    @Resource
    private ExamPaperQuestionRepository examPaperQuestionRepository;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ApplicationContext applicationContext;
    private static final String REDIS_PREFIX = "exam:member";

    public Object exam(ExamSaveParam examSaveParam) {
//        PublishExamPaper publishExamPaper = getPersonalExamPaper(examSaveParam);
//
//        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.START)) {
//            return applicationContext.getBean(ExamService.class).createRecord(publishExamPaper);
//        }
//        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.EXAMIING)) {
//            examPaperRecordRepository.updateAnswerById(JsonUtils.toJson(examSaveParam.getAnswers()), examSaveParam.getRecordId());
//            return null;
//        }
//        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.CONTINUE)) {
//            //todo 可间断考试
//            return null;
//        }
//        if (Objects.equals(examSaveParam.getStatus(), ExamSaveParam.Status.SUBMIT)) {
//            ExamRecord examRecord = examPaperRecordRepository.findById(examSaveParam.getRecordId()).orElseThrow(() -> new BusinessException(ResultEnum.EXAM_RECORD_NOT_FOUND));
//
//            examRecord.setAnswer(JsonUtils.toJson(examSaveParam.getAnswers()));
//            ExamPaper examPaper = examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
//
//            List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaper(examPaper);
//
//            int score = questions.stream().mapToInt(question -> examSaveParam.getAnswers().stream().filter(re -> Objects.equals(re.getQuestionId(), question.getId()))
//                    .findFirst().map(it -> {
//                        if (question.getType() == Question.Type.SINGLE_SELECT || question.getType() == Question.Type.TRUE_FALSE) {
//                            Object correctAnswer = JsonUtils.parse(question.getCorrectAnswer(), List.class).get(0);
//                            return Objects.equals(it.getAnswer(), correctAnswer.toString()) ? question.getScore() : 0;
//                        }
//                        if (question.getType() == Question.Type.MULTI_SELECT) {
//
//
//                            List<Question.Answer> answersss = JsonUtils.parse(question.getAnswer(), new TypeReference<>() {
//                            });
//
//                            List<String> cAnswer = JsonUtils.parse(question.getCorrectAnswer(), new TypeReference<>() {
//                            });
//                            List<String> userAns = JsonUtils.parse(it.getAnswer(), new TypeReference<>() {
//                            });
//
//
//                            List<Question.Answer> isTrue = answersss.stream()
//                                    .filter(answer -> cAnswer.contains(answer.getPrefix()))
//                                    .filter(c -> userAns.contains(c.getPrefix()))
//                                    .collect(Collectors.toList());
//
//                            return isTrue.stream().mapToInt(i -> i.getScore() == null ? 0 : i.getScore()).sum();
//                        }
//                        if (question.getType() == Question.Type.COMPLETION) {
////                                        List<String> userAns = JsonUtils.parse(it.getAnswer(), new TypeReference<>() {
////                                        });
////                                        List<String> cAnswer = JsonUtils.parse(question.getCorrectAnswer(), new TypeReference<>() {
////                                        });
////                                        if (userAns.size() != cAnswer.size())
////                                            return 0;
////
//                            return Objects.equals(it.getAnswer(), question.getCorrectAnswer()) ? question.getScore() : 0;
//                        }
//
//                        if (question.getType() == Question.Type.SHORT_ANSWER) {
//                            StringSimilarity stringSimilarity = new Jaccard();
//                            if (StringUtils.isEmpty(it.getAnswer())) return 0;
//                            double result = stringSimilarity.similarity(question.getCorrectAnswer(), it.getAnswer());
//                            return (int) result * question.getScore();
//                        }
//                        return 0;
//                    }).orElse(0)).sum();
//            examRecord.setExamScore(score);
//
//            //如果设置最大考试次数且 当前考试次数大于等于最大考试次数则考试结束
//            if (examPaper.getMaxExamCount() > 0 && publishExamPaper.getExamCount() + 1 >= examPaper.getMaxExamCount())
//                publishExamPaper.setStatus(PublishExamPaper.Status.END);
//            publishExamPaper.setExamCount(publishExamPaper.getExamCount() + 1);
//            //设置考试最高分数
//            if (publishExamPaper.getHighestScore() == null || examRecord.getExamScore() > publishExamPaper.getHighestScore())
//                publishExamPaper.setHighestScore(examRecord.getExamScore());
//            //设置考试最低分数
//            if (publishExamPaper.getLowestScore() == null || examRecord.getExamScore() < publishExamPaper.getLowestScore())
//                publishExamPaper.setLowestScore(examRecord.getExamScore());
//            //设置考试是否通过
//            if (BooleanUtil.isFalse(publishExamPaper.getPass()) && examPaper.getPassScore() != null && examRecord.getExamScore() >= examPaper.getPassScore()) {
//                publishExamPaper.setPass(true);
//            }
//            publishExamPaperRepository.save(publishExamPaper);
//            examPaperRecordRepository.save(examRecord);
//            stringRedisTemplate.delete(REDIS_PREFIX + RequestHolder.getMemberId());
//            return examRecord;
//        }
        return null;
    }

    @Transactional
    public Object createRecord(PublishExamPaper publishExamPaper) {
//        ExamRecord record = new ExamRecord();
//        record.setUserId(Long.valueOf(RequestHolder.getMemberId()));
//        record.setExamPaperId(publishExamPaper.getExamPaperId());
//        record.setStartTime(LocalDateTime.now());
//        record.setPass(false);
//        record.setPersonalExamPaperId(publishExamPaper.getId());
//        publishExamPaperRepository.incExamCount(publishExamPaper.getId());
//        return examPaperRecordRepository.save(record);
        return null;
    }

    private PublishExamPaper getPersonalExamPaper(ExamSaveParam examSaveParam) {
//        PublishExamPaper publishExamPaper;
//        ExamPaper examPaper;
//
//
//
//        String exam = stringRedisTemplate.opsForValue().get(REDIS_PREFIX + RequestHolder.getMemberId());
//        if (StringUtils.isEmpty(exam)) {
//            publishExamPaper = personalExamPaperRecordRepository.findById(examSaveParam.getExamId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
//            if (publishExamPaper.getStatus() == PublishExamPaper.Status.END)
//                throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
//            examPaper = examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
//            stringRedisTemplate.opsForValue().set(REDIS_PREFIX + RequestHolder.getMemberId(), JsonUtils.toJson(publishExamPaper), examPaper.getExamTime() + 30, TimeUnit.SECONDS);
//        } else {
//            publishExamPaper = JsonUtils.parse(exam, PublishExamPaper.class);
//            if (!Objects.equals(publishExamPaper.getId(), examSaveParam.getExamId()))
//                throw new BusinessException(ResultEnum.PARAM_ERROR);//当前用户上传考试id和正在考试记录id冲突
//        }
//        return publishExamPaper;
        PublishExamPaper publishExamPaper = publishExamPaperRepository.findById(examSaveParam.getExamId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));


//        if (publishExamPaper.getStatus() == PublishExamPaper.Status.END)
//            throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
//        return examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
        return null;
    }
}
