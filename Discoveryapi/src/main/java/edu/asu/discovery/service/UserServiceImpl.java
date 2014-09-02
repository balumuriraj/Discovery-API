package edu.asu.discovery.service;

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
		return userMongo.add(user);
	}

}
