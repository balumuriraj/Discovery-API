package edu.asu.discovery.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

public class Attempt implements MongoDoc{

	@Id
	private String id;	
	@NotEmpty
	private String userid;
	@NotEmpty
	private String quizid;
	@NotNull
	private double score;
	@NotNull
	private int clock;
	@NotNull
	private int submitcount;
	@NotNull
	private boolean submitstatus;
	@NotNull
	private Date date;	
	private List<SubAnswer> subanswers;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getQuizid() {
		return quizid;
	}
	public void setQuizid(String quizid) {
		this.quizid = quizid;
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
	public int getSubmitcount() {
		return submitcount;
	}
	public void setSubmitcount(int submitcount) {
		this.submitcount = submitcount;
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
	public List<SubAnswer> getSubanswers() {
		return subanswers;
	}
	public void setSubanswers(List<SubAnswer> subanswers) {
		this.subanswers = subanswers;
	}
	@Override
	public String toString() {
		return "Attempt [id=" + id + ", userid=" + userid + ", quizid="
				+ quizid + ", score=" + score + ", clock=" + clock
				+ ", submitcount=" + submitcount + ", submitstatus="
				+ submitstatus + ", date=" + date + ", subanswers="
				+ subanswers + "]";
	}
	
	
	
}
