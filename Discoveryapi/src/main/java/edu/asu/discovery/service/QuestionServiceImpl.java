package edu.asu.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.Question;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	@Qualifier("questionMongo")
	MongoDAO<Question> questionMongo;	

	@Override
	public Question addQuestion(Question question) {
		Question result = questionMongo.add(question);
		return result;
	}

	@Override
	public Question saveQuestion(Question question) {
		Question exist = questionMongo.findone(question.getId());
		if(exist == null){
			return null;
		} else{
			Question result = questionMongo.update(question);
			return result;
		}
	}

}
