package edu.asu.discovery.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class Attempt {
	
	@NotNull
	private int currentquestion;
	@NotNull
	private double score;
	@NotNull
	private int clock;
	@NotNull
	private boolean submitstatus;
	@NotNull
	private Date date;	
	private List<Answer> answers;
	
	public int getCurrentquestion() {
		return currentquestion;
	}
	public void setCurrentquestion(int currentquestion) {
		this.currentquestion = currentquestion;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getClock() {
		return clock;
	}
	public void setClock(int clock) {
		this.clock = clock;
	}
	public boolean isSubmitstatus() {
		return submitstatus;
	}
	public void setSubmitstatus(boolean submitstatus) {
		this.submitstatus = submitstatus;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	@Override
	public String toString() {
		return "Attempt [currentquestion=" + currentquestion + ", score="
				+ score + ", clock=" + clock + ", submitstatus=" + submitstatus
				+ ", date=" + date + ", answers=" + answers + "]";
	}
	
	
}
