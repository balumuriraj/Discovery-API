package edu.asu.discovery.service;

import edu.asu.discovery.model.Attempt;

public interface AttemptService {
	public Attempt saveAttempt(Attempt attempt);
	public Attempt getAttempt(String id);
}
