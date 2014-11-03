package edu.asu.discovery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.discovery.model.Instructor;
import edu.asu.discovery.model.Lab;
import edu.asu.discovery.model.User;
import edu.asu.discovery.service.InstructorService;
import edu.asu.discovery.service.LabService;
import edu.asu.discovery.service.UserService;


/**
 * <h1>MainController</h1>
 * <p>
 * This is the main controller for the entire application. It serves the GET requests for getting guest user by id, getting all the labs,
 * getting lab by id. It serves the POST requests for guest user login, add lab and delete lab. 
 * </p>
 * 
 * @author MohanRaj Balumuri
 *
 */

@Controller
public class MainController {
	
	@Autowired
	MongoTemplate temp;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private LabService labService;
	
	private static Logger logger = Logger.getLogger(MainController.class);
	
	/**
	 * This method is just for testing purpose. It is a GET request. 
	 * @return string This returns a string called test.
	 */
	@RequestMapping(value="/test", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> test(){		
		return new ResponseEntity<String>("test", HttpStatus.OK);
	}

	
	
	/**
	 * This method is used to save the guest user data entered by the user during the guest login.
	 * @param user This is the user object that should be saved in the DB.
	 * @return User This returns a user object that is saved in the DB.
	 */
	@RequestMapping(value="/guestUserLogin", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> guestuserlogin(@RequestBody User user){
		logger.info("Logging guest user..." + user);
		User ret = userService.addUser(user);
		return new ResponseEntity<User>(ret, HttpStatus.OK);
	}
	
	
	/**
	 * This method takes the id of the user as input and gives the User object belonging to that id 
	 * @param id This is id of the user
	 * @return Instructor This returns User object
	 */
	
	@RequestMapping(value="/getUser/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable String id){		
		logger.info("Getting details of User..");
		return new ResponseEntity<User>(userService.findUser(id), HttpStatus.OK);
	}
	
	/**
	 * This method returns all the users in the Database
	 * @return List<User> This returns a list of all users
	 */
	@RequestMapping(value="/getAllUsers", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> getAllUsers(){		
		logger.info("Getting all Users..");
		return new ResponseEntity<List<User>>(userService.getallUsers(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getLabs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Lab>> getLabs(){		
		logger.info("Getting details of all Labs..");
		return new ResponseEntity<List<Lab>>(labService.getAllLabs(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping(value="/getLab/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> getLab(@PathVariable String id){		
		logger.info("Getting details of Lab..");
		return new ResponseEntity<Lab>(labService.findLab(id), HttpStatus.OK);
	}
	
	
}
