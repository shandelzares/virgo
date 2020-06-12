package com.virgo.exam.repository;

import com.virgo.exam.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question,String>{

    int countByCategory(String category);

    List<Question> findByIdIn(List<String> ids);

}
