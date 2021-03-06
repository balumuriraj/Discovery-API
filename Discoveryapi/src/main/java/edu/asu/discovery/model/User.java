package edu.asu.discovery.model;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <h1>User</h1>
 * <p>
 * This class is a model for User. It consists of id, email, name and type.
 * The type denotes, if a user is ASU user or guest user.
 * </p>
 * 
 * @author MohanRaj Balumuri
 * @version 1.0
 */

@Document
public class User implements MongoDoc{
	@Id
	private String id;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String name;
	private boolean type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name
				+ ", type=" + type + "]";
	}
}
