package edu.asu.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.Attempt;
import edu.asu.discovery.model.UserAnswer;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class AttemptServiceImpl implements AttemptService{

	@Autowired
	@Qualifier("attemptMongo")
	MongoDAO<Attempt> attemptMongo;	
	
	@Override
	public Attempt saveAttempt(Attempt attempt) {
		Attempt exist = attemptMongo.findone(attempt.getId());
		if(exist == null){
			Attempt result = attemptMongo.add(attempt);
			return result;
		} else{
			Attempt result = attemptMongo.update(attempt);
			return result;
		}
	}

	@Override
	public Attempt getAttempt(String id) {
		return attemptMongo.findone(id);
	}

}
