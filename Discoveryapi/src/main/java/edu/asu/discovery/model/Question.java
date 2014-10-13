package edu.asu.discovery.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


public class Question{

	@NotEmpty
	private String question;
	@NotEmpty
	private String description;
	@NotEmpty
	private String imagepath;
	@NotEmpty
	private String hint;

	private List<SubQuestion> subquestions;

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public List<SubQuestion> getSubquestions() {
		return subquestions;
	}
	public void setSubquestions(List<SubQuestion> subquestions) {
		this.subquestions = subquestions;
	}
	@Override
	public String toString() {
		return "Question [question=" + question + ", description="
				+ description + ", imagepath=" + imagepath + ", hint=" + hint
				+ ", subquestions=" + subquestions + "]";
	}
	
	
	
}
