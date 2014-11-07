package edu.asu.discovery.model;

import java.util.Arrays;
import java.util.Date;
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
	private List<QuizAttempt> quizattempts;
	
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
	public List<QuizAttempt> getQuizattempts() {
		return quizattempts;
	}
	public void setQuizattempts(List<QuizAttempt> quizattempts) {
		this.quizattempts = quizattempts;
	}
	@Override
	public String toString() {
		return "UserAnswer [id=" + id + ", userid=" + userid + ", labid="
				+ labid + ", quizattempts=" + quizattempts + "]";
	}
}
