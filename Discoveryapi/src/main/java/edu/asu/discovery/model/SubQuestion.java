package edu.asu.discovery.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class SubQuestion{

	@NotEmpty
	private String subquestion;
	@NotEmpty
	private String correctanswer;
	@NotEmpty
	private int answerrange;
	@NotEmpty
	private OptionType optiontype;
	private List<String> options;


	public String getSubquestion() {
		return subquestion;
	}

	public void setSubquestion(String subquestion) {
		this.subquestion = subquestion;
	}

	public String getCorrectanswer() {
		return correctanswer;
	}

	public void setCorrectanswer(String correctanswer) {
		this.correctanswer = correctanswer;
	}

	public int getAnswerrange() {
		return answerrange;
	}

	public void setAnswerrange(int answerrange) {
		this.answerrange = answerrange;
	}

	public OptionType getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(OptionType optiontype) {
		this.optiontype = optiontype;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	
}
