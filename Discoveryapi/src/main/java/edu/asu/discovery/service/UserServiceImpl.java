package edu.asu.discovery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.User;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	@Qualifier("userMongo")
	MongoDAO<User> userMongo;	
	
	@Override
	public User addUser(User user) {	
		//User exist = userMongo.findone(user.getId());
		if(user.getId().equals("")){
			return userMongo.add(user);
		} else{
			return userMongo.update(user);
		}
		
	}

	@Override
	public User findUser(String id) {
		return userMongo.findone(id);
	}

	@Override
	public List<User> getallUsers() {
		return userMongo.getall();
	}

}
