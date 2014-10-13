package edu.asu.discovery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.Instructor;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class InstructorServiceImpl implements InstructorService{

	@Autowired
	@Qualifier("instructorMongo")
	MongoDAO<Instructor> instructorMongo;	
	
	@Override
	public Instructor addUser(Instructor instructor) {
		Instructor exist = instructorMongo.findone(instructor.getId());
		if(exist == null){
			return instructorMongo.add(instructor);
		} else{
			return instructorMongo.update(instructor);
		}
	}

	@Override
	public Instructor findUser(String id) {
		return instructorMongo.findone(id);
	}

	@Override
	public List<Instructor> getAllUsers() {
		return instructorMongo.getall();
	}

	@Override
	public boolean deleteInstructor(String id) {
		Instructor instructor = instructorMongo.findone(id);
		if(instructor == null){
			return false;
		} else{
			instructorMongo.delete(instructor);
			return true;
		}
	}

	@Override
	public Instructor checkUser(Instructor instructor) {		
		return instructorMongo.findone("email", "name", instructor.getEmail(), instructor.getName());
	}

}
