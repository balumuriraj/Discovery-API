package edu.asu.discovery.service;

import java.util.List;

import edu.asu.discovery.model.User;

public interface UserService {
	public User addUser(User user);
	public User findUser(String id);
	public List<User> getallUsers();
}
