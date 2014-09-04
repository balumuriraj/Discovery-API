package edu.asu.discovery.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Answer {
	private int id;
	private List<SubAnswer> subanswers;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<SubAnswer> getSubanswers() {
		return subanswers;
	}
	public void setSubanswers(List<SubAnswer> subanswers) {
		this.subanswers = subanswers;
	}
	@Override
	public String toString() {
		return "Answer [id=" + id + ", subanswers=" + subanswers + "]";
	}
	
	
	
}
