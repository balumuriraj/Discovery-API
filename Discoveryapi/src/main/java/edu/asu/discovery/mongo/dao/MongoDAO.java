package edu.asu.discovery.mongo.dao;

import java.util.List;

import edu.asu.discovery.model.MongoDoc;

public interface MongoDAO<T extends MongoDoc> {
	public T add(T obj);
	public List<T> getall();
	public T findone(String id);
	public T findone(String id1, String id2, String value1, String value2);
	public List<T> customfindone(String field, String value);
	public T update(T obj);
	public void delete(T obj);
}
