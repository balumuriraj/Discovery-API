package edu.asu.discovery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.asu.discovery.model.Lab;
import edu.asu.discovery.model.User;
import edu.asu.discovery.service.LabService;
import edu.asu.discovery.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	MongoTemplate temp;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LabService labService;
	
	private static Logger logger = Logger.getLogger(MainController.class);
	
	@RequestMapping(value="/test", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> test(@RequestBody Lab lab){
		// query to search user
		Query searchUserQuery = new Query(Criteria.where("id").is("0eb611de-3807-4968-9a7d-4cd204916794"));
	 
		System.out.println(searchUserQuery);
		// find the saved user again.
		Lab result = temp.findOne(searchUserQuery, Lab.class, "labs");
		System.out.println(result);
		return new ResponseEntity<Lab>(result, HttpStatus.OK);
	}

	@RequestMapping(value="/guestUserLogin", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> guestuserlogin(@RequestBody User user){
		logger.info("Logging guest user..." + user);
		User ret = userService.addUser(user);
		return new ResponseEntity<User>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getLabs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Lab>> getLabs(){		
		logger.info("Getting details of all Labs..");
		return new ResponseEntity<List<Lab>>(labService.getAllLabs(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getLab/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> getLab(@PathVariable String id){		
		logger.info("Getting details of Lab..");
		return new ResponseEntity<Lab>(labService.findLab(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/addLab", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> addLab(@RequestBody Lab lab){		
		logger.info("Adding Lab...");
		Lab result = labService.addLab(lab);
		
		return new ResponseEntity<Lab>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteLab", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> deleteLab(@RequestBody Lab lab){		
		logger.info("Deleting Lab...");
		labService.deleteLab(lab);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Status", "success");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
}
