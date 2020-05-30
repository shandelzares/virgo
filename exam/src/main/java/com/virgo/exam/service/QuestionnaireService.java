package com.virgo.exam.service;

import cn.hutool.core.bean.BeanUtil;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.exam.model.ExamPaper;
import com.virgo.exam.repository.ExamPaperQuestionRepository;
import com.virgo.exam.repository.ExamPaperRepository;
import com.virgo.exam.vo.QuestionnaireVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {
    @Resource
    private ExamPaperRepository examPaperRepository;
    @Resource
    private ExamPaperQuestionRepository examPaperQuestionRepository;


    public Object findById(Long id) {
        ExamPaper examPaper = examPaperRepository.findById(id).orElseThrow(() -> new BusinessException(ResultEnum.EXAM_RECORD_NOT_FOUND));
        QuestionnaireVO vo = BeanUtil.copyProperties(examPaper, QuestionnaireVO.class);
        vo.setQuestions(examPaperQuestionRepository.findByExamPaper(examPaper)
                .stream().map(it -> BeanUtil.copyProperties(it, QuestionnaireVO.QuestionVO.class)).collect(Collectors.toList()));
        return vo;
    }
}
