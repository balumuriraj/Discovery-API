package edu.asu.discovery.model;

public class Quiz {
	private String id;
	private String quizname;
	private String quizdescription;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuizname() {
		return quizname;
	}
	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}
	public String getQuizdescription() {
		return quizdescription;
	}
	public void setQuizdescription(String quizdescription) {
		this.quizdescription = quizdescription;
	}
	@Override
	public String toString() {
		return "Quiz [id=" + id + ", quizname=" + quizname
				+ ", quizdescription=" + quizdescription + "]";
	}
	
	
	
}
