package com.virgo.exam.repository;

import com.virgo.exam.model.ExamRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExamPaperRecordRepository extends MongoRepository<ExamRecord, String> {

//    @Query("update ExamRecord set answer = ?1 , version=version+1 where id = ?2")
//    void updateAnswerById(String answer, Long id);

}
