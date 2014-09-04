package edu.asu.discovery.model;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserAnswer implements MongoDoc {

	@Id
	private String id;	
	@NotEmpty
	private String userid;
	@NotEmpty
	private String labid;
	@NotNull
	private int currentquestion;
	@NotNull
	private double score;
	@NotNull
	private int clock;
	@NotNull
	private boolean submitstatus;
	
	private List<Answer> answers;

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

	public String getLabid() {
		return labid;
	}

	public void setLabid(String labid) {
		this.labid = labid;
	}

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

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "UserAnswer [id=" + id + ", userid=" + userid + ", labid="
				+ labid + ", currentquestion=" + currentquestion + ", score="
				+ score + ", clock=" + clock + ", submitstatus=" + submitstatus
				+ ", answers=" + answers + "]";
	}
	
}
