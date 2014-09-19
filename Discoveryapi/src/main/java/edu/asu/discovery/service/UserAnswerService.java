package edu.asu.discovery.service;

import edu.asu.discovery.model.UserAnswer;

public interface UserAnswerService {
	public UserAnswer saveAnswer(UserAnswer obj);
	public UserAnswer getReport(String id);
}
