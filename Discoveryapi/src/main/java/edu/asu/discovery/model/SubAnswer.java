package edu.asu.discovery.model;

public class SubAnswer {
	private int id;
	private String subanswer;
	private boolean correct;
	private boolean saved;
	private boolean submitted;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubanswer() {
		return subanswer;
	}
	public void setSubanswer(String subanswer) {
		this.subanswer = subanswer;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	@Override
	public String toString() {
		return "SubAnswer [id=" + id + ", subanswer=" + subanswer
				+ ", correct=" + correct + ", saved=" + saved + ", submitted="
				+ submitted + "]";
	}
	

	
	
}
