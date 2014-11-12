package edu.asu.discovery.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class QuizAttempt {
	@NotEmpty
	private String quizid;
	private List<AttemptSummary> attempts;
	
	
	public String getQuizid() {
		return quizid;
	}
	public void setQuizid(String quizid) {
		this.quizid = quizid;
	}
	public List<AttemptSummary> getAttempts() {
		return attempts;
	}
	public void setAttempts(List<AttemptSummary> attempts) {
		this.attempts = attempts;
	}
	@Override
	public String toString() {
		return "QuizAttempt [quizid=" + quizid + ", attempts=" + attempts + "]";
	}	
}
