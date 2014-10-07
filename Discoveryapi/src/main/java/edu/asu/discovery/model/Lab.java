package edu.asu.discovery.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <h1>Lab</h1>
 * <p>The class consists of a lab details. It consist of id, name, its description, 
 * and a list of questions. Here question is another object. Every document that 
 * will be saved to the mongodb must implement MongoDoc class which ensures that
 * id is set.</p> 
 * 
 * @author MohanRaj Balumuri
 * @version 1.0
 */

@Document
public class Lab implements MongoDoc{
	@Id
	private String id;
	@Indexed(unique = true)
	@NotEmpty
	private String labname;
	@NotEmpty
	private String labdescription;

	private List<Question> labquestions;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabname() {
		return labname;
	}
	public void setLabname(String labname) {
		this.labname = labname;
	}
	public String getLabdescription() {
		return labdescription;
	}
	public void setLabdescription(String labdescription) {
		this.labdescription = labdescription;
	}
	public List<Question> getLabquestions() {
		return labquestions;
	}
	public void setLabquestions(List<Question> labquestions) {
		this.labquestions = labquestions;
	}
	
}
