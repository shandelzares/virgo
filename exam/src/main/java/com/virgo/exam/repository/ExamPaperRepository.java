package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExamPaperRepository extends MongoRepository<ExamPaper, String> {
    List<ExamPaper> findByIdIn(List<String> collect);
}
