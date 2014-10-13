package edu.asu.discovery.service;

import java.util.List;

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

	@Override
	public UserAnswer getReport(String id) {		
		return userAnswerMongo.findone(id);
	}

	@Override
	public UserAnswer getUserAnswerDoc(String labid, String userid) {
		return userAnswerMongo.findone("labid", "userid", labid, userid);
	}

	@Override
	public List<UserAnswer> getReports(String userid) {
		return userAnswerMongo.customfindone("userid", userid);
	}

	@Override
	public List<UserAnswer> getAllReports() {
		return userAnswerMongo.getall();
	}

}
