package edu.asu.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.Lab;
import edu.asu.discovery.model.UserAnswer;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class UserAnswerServiceImpl implements UserAnswerService{

	@Autowired
	@Qualifier("userAnswerMongo")
	MongoDAO<UserAnswer> userAnswerMongo;	
	
	@Override
	public UserAnswer saveAnswer(UserAnswer ans) {
		UserAnswer exist = userAnswerMongo.findone(ans.getId());
		if(exist == null){
			UserAnswer result = userAnswerMongo.add(ans);
			return result;
		} else{
			UserAnswer result = userAnswerMongo.update(ans);
			return result;
		}
	}

}
