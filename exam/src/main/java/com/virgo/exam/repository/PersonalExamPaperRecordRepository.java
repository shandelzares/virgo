package com.virgo.exam.repository;

import com.virgo.exam.model.PersonalExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonalExamPaperRecordRepository extends JpaRepository<PersonalExamPaper, Long>, JpaSpecificationExecutor<PersonalExamPaper> {

}
