package com.virgo.exam.repository;

import com.virgo.exam.model.ExamPaperRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExamPaperRecordRepository extends JpaRepository<ExamPaperRecord, Long>, JpaSpecificationExecutor<ExamPaperRecord> {

}
