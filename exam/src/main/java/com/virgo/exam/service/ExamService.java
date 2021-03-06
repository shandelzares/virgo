package com.virgo.exam.service;

import cn.hutool.core.bean.BeanUtil;
import com.aliyuncs.utils.StringUtils;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.exam.dto.ExamSaveParam;
import com.virgo.exam.dto.ExamSubmitParam;
import com.virgo.exam.model.*;
import com.virgo.exam.repository.*;
import com.virgo.exam.vo.ExamVO;
import info.debatty.java.stringsimilarity.Jaccard;
import info.debatty.java.stringsimilarity.interfaces.StringSimilarity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private MongoTemplate mongoTemplate;
    @Resource
    private QuestionRepository questionRepository;

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
//            //todo ???????????????
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
//            //????????????????????????????????? ???????????????????????????????????????????????????????????????
//            if (examPaper.getMaxExamCount() > 0 && publishExamPaper.getExamCount() + 1 >= examPaper.getMaxExamCount())
//                publishExamPaper.setStatus(PublishExamPaper.Status.END);
//            publishExamPaper.setExamCount(publishExamPaper.getExamCount() + 1);
//            //????????????????????????
//            if (publishExamPaper.getHighestScore() == null || examRecord.getExamScore() > publishExamPaper.getHighestScore())
//                publishExamPaper.setHighestScore(examRecord.getExamScore());
//            //????????????????????????
//            if (publishExamPaper.getLowestScore() == null || examRecord.getExamScore() < publishExamPaper.getLowestScore())
//                publishExamPaper.setLowestScore(examRecord.getExamScore());
//            //????????????????????????
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
//                throw new BusinessException(ResultEnum.PARAM_ERROR);//????????????????????????id?????????????????????id??????
//        }
//        return publishExamPaper;
        PublishExamPaper publishExamPaper = publishExamPaperRepository.findById(examSaveParam.getExamId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));


//        if (publishExamPaper.getStatus() == PublishExamPaper.Status.END)
//            throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
//        return examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
        return null;
    }

    public ExamVO exam(String id, ExamSubmitParam examSaveParam) {
        ExamVO vo = new ExamVO();
        LocalDateTime now = LocalDateTime.now();
        PublishExamPaper publishExamPaper = publishExamPaperRepository.findById(id).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
        ExamPaper examPaper = examPaperRepository.findById(publishExamPaper.getExamPaperId()).orElseThrow(() -> new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_FOUND));
        if (publishExamPaper.getExamCount() >= examPaper.getMaxExamCount()) {
            throw new BusinessException(ResultEnum.EXAM_COUNT_USED_ALL);
        }
        if (examPaper.getExamEndTime().isBefore(now)) {
            throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_END);
        }
        if (examPaper.getExamStartTime().isAfter(now)) {
            throw new BusinessException(ResultEnum.PERSONAL_EXAM_PAPER_NOT_START);
        }

        ExamRecord record = new ExamRecord();
        record.setExamPaperId(examPaper.getId());
        record.setPublishExamPaperId(publishExamPaper.getId());
        List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaperId(examPaper.getId());
        List<String> ids = examSaveParam.getAnswers().stream().filter(it -> questions.stream().noneMatch(a -> Objects.equals(a.getId(), it.getQuestionId()))).map(ExamSubmitParam.Answer::getQuestionId).collect(Collectors.toList());
        List<ExamPaperQuestion> extQ = questionRepository.findByIdIn(ids).stream().map(it -> {
            return BeanUtil.copyProperties(it, ExamPaperQuestion.class);
        }).collect(Collectors.toList());
        questions.addAll(extQ);

        List<ExamRecord.Question> questionsRecord = getQuestions(examSaveParam, questions);


        if (examPaper.getAutoScoring()) {
            int score = questionsRecord.stream().mapToInt(ExamRecord.Question::getObtainScore).sum();
            publishExamPaper.setPass(score >= examPaper.getPassScore());
            record.setExamScore(score);
            //????????????????????????
            if (publishExamPaper.getHighestScore() == null || score > publishExamPaper.getHighestScore())
                publishExamPaper.setHighestScore(score);
            //????????????????????????
            if (publishExamPaper.getLowestScore() == null || score < publishExamPaper.getLowestScore())
                publishExamPaper.setLowestScore(score);
        }
        publishExamPaper.setLastScore(record.getExamScore());
        //????????????????????????????????? ???????????????????????????????????????????????????????????????
        if (examPaper.getMaxExamCount() > 0 && publishExamPaper.getExamCount() + 1 >= examPaper.getMaxExamCount())
            publishExamPaper.setStatus(PublishExamPaper.Status.END);
        publishExamPaper.setExamCount(publishExamPaper.getExamCount() + 1);

        record.setQuestions(questionsRecord.stream().filter(it -> it.getType() != Question.Type.RANDOM).collect(Collectors.toList()));

        publishExamPaperRepository.save(publishExamPaper);
        examPaperRecordRepository.save(record);
        vo.setScoring(examPaper.getAutoScoring());
        vo.setExamScore(record.getExamScore());
        vo.setPass(record.getPass());
        return vo;
    }

    private List<ExamRecord.Question> getQuestions(ExamSubmitParam examSaveParam, List<ExamPaperQuestion> questions) {
        return questions.stream().map(question -> {
            ExamRecord.Question examQuestion = new ExamRecord.Question();
            BeanUtil.copyProperties(question, examQuestion);

            examSaveParam.getAnswers()
                    .stream()
                    .filter(it -> Objects.equals(it.getQuestionId(), question.getId()))
                    .findFirst()
                    .ifPresentOrElse(answer -> {
                        examQuestion.setObtainScore(0);
                        if (question.getType() == Question.Type.SINGLE_SELECT || question.getType() == Question.Type.TRUE_FALSE) {
                            if (answer.getContent() != null) {
                                int selected = Integer.valueOf(answer.getContent() == null ? "0" : answer.getContent().toString());
                                question.getAnswer().stream().filter(Question.Answer::getIsCorrect).findFirst().ifPresent(correctAnswer -> {
                                    if (correctAnswer.getId() == selected) {
                                        examQuestion.setObtainScore(answer.getScore());
                                    }
                                });
                            }
                        } else if (question.getType() == Question.Type.MULTI_SELECT) {
                            if (answer.getContent() != null) {
                                List<String> selected = new ArrayList<>((List<String>) answer.getContent());

                                List<Integer> correct = question.getAnswer()
                                        .stream()
                                        .filter(Question.Answer::getIsCorrect).map(Question.Answer::getId)
                                        .collect(Collectors.toList());
                                if (selected.size() == correct.size()) {
                                    correct.removeAll(selected.stream().map(Integer::valueOf).collect(Collectors.toList()));
                                    if (correct.size() == 0) {
                                        examQuestion.setObtainScore(answer.getScore());
                                    }
                                }
                            }
                        } else if (question.getType() == Question.Type.COMPLETION) {
                            if (answer.getContent() != null) {

                                List<Map<String, Object>> selected = new ArrayList<>((List<Map<String, Object>>) answer.getContent());
                                int sum;
                                List<Question.Answer> answers = new ArrayList<>();
                                if (answer.getScore() > 0) {
                                    String stem = question.getStem();

                                    String rx = "(\\{[^}]+\\})";

                                    int i = 0;

                                    Pattern p = Pattern.compile(rx);
                                    Matcher m = p.matcher(stem);
                                    while (m.find()) {
                                        Question.Answer a = new Question.Answer();
                                        a.setId(i);
                                        String s = m.group();
                                        a.setContent(s.substring(1, s.length() - 1));
                                        answers.add(a);
                                    }

                                    int size = answers.size();
                                    long count = answers
                                            .stream()
                                            .filter(correct -> {
                                                Boolean fl = selected.stream()
                                                        .filter(s -> (s.get("id")) == correct.getId())
                                                        .findFirst()
                                                        .filter(it -> org.apache.commons.lang3.StringUtils.equalsIgnoreCase((CharSequence) it.get("content"), correct.getContent())).isPresent();
                                                return fl;
                                            }).count();

                                    if (count == size)
                                        sum = answer.getScore();
                                    else
                                        sum = Math.toIntExact((count * answer.getScore()) / size);
                                } else {
                                    sum = question.getAnswer()
                                            .stream()
                                            .mapToInt(correct -> selected.stream()
                                                    .filter(s -> (s.get("id")) == correct.getId())
                                                    .findFirst()
                                                    .map(it -> {
                                                        if (Objects.equals(it.get("content"), correct.getContent()))
                                                            return correct.getScore();
                                                        return 0;
                                                    }).orElse(0)).sum();
                                }
                                examQuestion.setObtainScore(sum);
                            }

                        } else if (question.getType() == Question.Type.SHORT_ANSWER) {
                            if (answer.getContent() != null) {

                                String selected = (String) answer.getContent();
                                if (!StringUtils.isEmpty(selected)) {
                                    StringSimilarity stringSimilarity = new Jaccard();
                                    double result = stringSimilarity.similarity(question.getAnalysis(), selected);
                                    examQuestion.setObtainScore((int) result * answer.getScore());
                                }
                            }
                        }
                    }, () -> {
                        examQuestion.setObtainScore(0);
                    });

            return examQuestion;
        }).collect(Collectors.toList());
    }
}
