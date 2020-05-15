package com.virgo.exam.service;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.virgo.common.JsonUtils;
import com.virgo.common.page.PageResult;
import com.virgo.exam.dto.ExamPaperQueryParam;
import com.virgo.exam.model.ExamPaper;
import com.virgo.exam.model.ExamPaperQuestion;
import com.virgo.exam.model.PersonalExamPaper;
import com.virgo.exam.repository.ExamPaperQuestionRepository;
import com.virgo.exam.repository.ExamPaperRepository;
import com.virgo.exam.repository.PersonalExamPaperRecordRepository;
import com.virgo.exam.vo.ExamPaperVO;
import com.virgo.exam.vo.QuestionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExamPaperService {
    @Resource
    private ExamPaperRepository examPaperRepository;
    @Resource
    private ExamPaperQuestionRepository examPaperQuestionRepository;
    @Resource
    private PersonalExamPaperRecordRepository personalExamPaperRecordRepository;

    public PageResult<ExamPaperVO> findPage(@Valid ExamPaperQueryParam questionQueryParam) {

        Page<PersonalExamPaper> personalExamPapers = personalExamPaperRecordRepository.findAll((Specification<PersonalExamPaper>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(questionQueryParam.getCode())) {
                predicates.add(criteriaBuilder.like(root.get("code"), questionQueryParam.getCode() + "%"));
            }

            if (!StringUtils.isEmpty(questionQueryParam.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), questionQueryParam.getTitle() + "%"));
            }
            if (!StringUtils.isEmpty(questionQueryParam.getCategory())) {
                predicates.add(criteriaBuilder.equal(root.get("category"), questionQueryParam.getCategory()));
            }
            if (!CollectionUtils.isEmpty(questionQueryParam.getType())) {
                CriteriaBuilder.In<ExamPaper.Type> in = criteriaBuilder.in(root.get("type"));
                questionQueryParam.getType().forEach(in::value);
                predicates.add(criteriaBuilder.and(in));
            }
            query.where(predicates.toArray(new Predicate[0])).orderBy();
            return query.getRestriction();
        }, questionQueryParam.pageable());

        List<ExamPaper> examPapers = examPaperRepository.findByIdIn(personalExamPapers.getContent().stream().map(PersonalExamPaper::getExamPaperId).collect(Collectors.toList()));

        return new PageResult<>(personalExamPapers.getTotalElements(), examPapers.stream().map(paper -> {
            ExamPaperVO vo = BeanUtil.copyProperties(paper, ExamPaperVO.class);
            vo.setExamPaperId(vo.getId());
            vo.setId(personalExamPapers.getContent().stream().filter(it -> Objects.equals(it.getExamPaperId(), paper.getId())).findAny().map(PersonalExamPaper::getId).orElse(null));
            return vo;
        }).collect(Collectors.toList()));
    }

    public ExamPaperVO findById(Long id) {
        return personalExamPaperRecordRepository.findById(id).map(personalExamPaper -> {
            ExamPaper examPaper = examPaperRepository.findById(personalExamPaper.getExamPaperId()).get();
            List<ExamPaperQuestion> questions = examPaperQuestionRepository.findByExamPaper(examPaper);
            ExamPaperVO vo = BeanUtil.copyProperties(examPaper, ExamPaperVO.class);

            vo.setQuestions(questions.stream().map(q -> {
                QuestionVO questionVO = new QuestionVO();
                BeanUtil.copyProperties(q, questionVO, "answer");
                questionVO.setAnswer(JsonUtils.parse(q.getAnswer(), new TypeReference<>() {
                }));
                return questionVO;
            }).collect(Collectors.toList()));
            return vo;
        }).orElse(null);
    }
}
