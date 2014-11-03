package edu.asu.discovery.service;

import java.util.List;

import edu.asu.discovery.model.Lab;

public interface LabService {
	public List<Lab> getAllLabs();
	public Lab addLab(Lab lab);
	public Lab saveLab(Lab lab);
	public Lab findLab(String id);
	public void deleteLab(Lab lab);
}
