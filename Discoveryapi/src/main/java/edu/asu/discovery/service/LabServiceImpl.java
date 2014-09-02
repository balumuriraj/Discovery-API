package edu.asu.discovery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.discovery.model.Lab;
import edu.asu.discovery.mongo.dao.MongoDAO;

@Service
public class LabServiceImpl implements LabService{

	@Autowired
	@Qualifier("labMongo")
	MongoDAO<Lab> labMongo;	
	
	@Override
	public List<Lab> getAllLabs() {
		return labMongo.getall();
	}

	@Override
	public Lab addLab(Lab lab) {
		Lab exist = labMongo.findone(lab.getId());
		if(exist == null){
			Lab result = labMongo.add(lab);
			return result;
		} else{
			Lab result = labMongo.update(lab);
			return result;
		}
	}

	@Override
	public Lab findLab(String id) {
		return labMongo.findone(id);
	}

	@Override
	public void deleteLab(Lab lab) {
		labMongo.delete(lab);
	}

}
