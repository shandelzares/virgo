package com.virgo.exam.repository;

import com.virgo.exam.model.PublishExamPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublishExamPaperRepository extends MongoRepository<PublishExamPaper, String> {

//    @Query("update PublishExamPaper set examCount  = examCount+1 , version=version+1 where id = ?1")
//    void incExamCount(Long id);

}
