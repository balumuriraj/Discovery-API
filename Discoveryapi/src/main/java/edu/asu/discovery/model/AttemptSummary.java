package edu.asu.discovery.model;

import java.util.Date;

public class AttemptSummary {
	private String id;
	private String quizId;
	private double score;
	private Date date;
	private boolean submitstatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSubmitstatus() {
		return submitstatus;
	}

	public void setSubmitstatus(boolean submitstatus) {
		this.submitstatus = submitstatus;
	}
	
	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

}
