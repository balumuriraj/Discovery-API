package edu.asu.discovery.service;

import java.util.List;

import edu.asu.discovery.model.Instructor;

public interface InstructorService {
	public Instructor addUser(Instructor instructor);
	public Instructor findUser(String id);
	public Instructor checkUser(Instructor instructor);
	public List<Instructor> getAllUsers();
	public boolean deleteInstructor(String id);
}
