package edu.asu.discovery.mongo.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.discovery.model.MongoDoc;

/**
 * @author mbalumur
 * This implementation class uses Mongo Template functions to query the DB
 * @param <T>
 */
@Repository
public class MongoDAOImpl<T extends MongoDoc> implements MongoDAO<T>{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	private String mongoCollection;
	private Class<T> classname;

	public MongoDAOImpl(String mongoCollection, Class<T> classname) {
		super();
		this.mongoCollection = mongoCollection;
		this.classname = classname;
	}

	@Override
	public T add(T obj) {
		obj.setId(UUID.randomUUID().toString());
		mongoTemplate.save(obj, mongoCollection);
		return obj;
	}

	@Override
	public List<T> getall() {
		return mongoTemplate.findAll(classname, mongoCollection);
	}

	@Override
	public T findone(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		T result = mongoTemplate.findOne(query, classname, mongoCollection);
		return result;
	}

	@Override
	public T update(T obj) {
		mongoTemplate.save(obj, mongoCollection);
		return obj;
	}

	@Override
	public void delete(T obj) {
		mongoTemplate.remove(obj, mongoCollection);
	}

	@Override
	public T findone(String id1, String id2, String value1, String value2) {
		Query query = new Query();
		query.addCriteria(Criteria.where(id1).is(value1));
		query.addCriteria(Criteria.where(id2).is(value2));
		T result = mongoTemplate.findOne(query, classname, mongoCollection);
		return result;
	}
	
	@Override
	public List<T> customfindone(String field, String value) {
		Query query = new Query();
		query.addCriteria(Criteria.where(field).is(value));
		List<T> result = mongoTemplate.find(query, classname, mongoCollection);
		return result;
	}

}
