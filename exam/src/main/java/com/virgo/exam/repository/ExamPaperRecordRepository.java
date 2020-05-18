package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaperRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExamPaperRecordRepository extends JpaRepository<ExamPaperRecord, Long>, JpaSpecificationExecutor<ExamPaperRecord> {

    @Modifying
    @Query("update ExamPaperRecord set answer = ?1 , version=version+1 where id = ?2")
    void updateAnswerById(String answer, Long id);

}
