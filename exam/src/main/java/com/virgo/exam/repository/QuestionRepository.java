package com.virgo.exam.repository;

import com.virgo.exam.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question,String>{

}
