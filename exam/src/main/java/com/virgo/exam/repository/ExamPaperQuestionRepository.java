package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaperQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExamPaperQuestionRepository extends MongoRepository<ExamPaperQuestion, String> {

    List<ExamPaperQuestion> findByExamPaperId(String examPaperId);

}
