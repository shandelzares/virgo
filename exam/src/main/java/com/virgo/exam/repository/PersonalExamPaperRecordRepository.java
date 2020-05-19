package com.virgo.exam.repository;

import com.virgo.exam.model.PersonalExamPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PersonalExamPaperRecordRepository extends JpaRepository<PersonalExamPaper, Long>, JpaSpecificationExecutor<PersonalExamPaper> {

    @Modifying
    @Query("update PersonalExamPaper set examCount  = examCount+1 , version=version+1 where id = ?1")
    void incExamCount(Long id);

}
