package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ExamPaperRepository extends JpaRepository<ExamPaper, Long> , JpaSpecificationExecutor<ExamPaper> {
    List<ExamPaper> findByIdIn(List<Long> collect);
}
