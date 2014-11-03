package edu.asu.discovery.model;

public class Quiz {
	private int id;
	private String quizname;
	private String quizdescription;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
