package edu.asu.discovery.service;

import java.util.List;

import edu.asu.discovery.model.UserAnswer;

public interface UserAnswerService {
	public UserAnswer saveAnswer(UserAnswer obj);
	public UserAnswer getReport(String id);
	public UserAnswer getUserAnswerDoc(String labid, String userid);
	public List<UserAnswer> getReports(String userid);
}
