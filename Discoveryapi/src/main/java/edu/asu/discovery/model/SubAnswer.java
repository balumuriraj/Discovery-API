package edu.asu.discovery.model;

public class SubAnswer {
	private int id;
	private String subanswer;
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
	@Override
	public String toString() {
		return "SubAnswer [id=" + id + ", subanswer=" + subanswer + "]";
	}
	
	
}
