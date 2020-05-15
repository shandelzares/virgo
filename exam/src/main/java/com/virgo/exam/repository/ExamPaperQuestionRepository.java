package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaper;
import com.virgo.exam.model.ExamPaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ExamPaperQuestionRepository extends JpaRepository<ExamPaperQuestion, Long>, JpaSpecificationExecutor<ExamPaperQuestion> {

    List<ExamPaperQuestion> findByExamPaper(ExamPaper examPaper);
    void deleteAllByExamPaper(ExamPaper examPaper);
}
