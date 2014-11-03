package edu.asu.discovery.service;

import edu.asu.discovery.model.Question;

public interface QuestionService {
	public Question addQuestion(Question question);
	public Question saveQuestion(Question question);
}
