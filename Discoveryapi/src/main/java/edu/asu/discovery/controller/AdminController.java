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
import edu.asu.discovery.model.Question;
import edu.asu.discovery.model.Quiz;
import edu.asu.discovery.model.SubQuestion;
import edu.asu.discovery.service.InstructorService;
import edu.asu.discovery.service.LabService;
import edu.asu.discovery.service.QuestionService;
import edu.asu.discovery.service.UserService;

@Controller
public class AdminController {

	@Autowired
	MongoTemplate temp;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private LabService labService;
	
	@Autowired
	private QuestionService questionService;
	
	private static Logger logger = Logger.getLogger(MainController.class);
	
	/**
	 * This method is used to check the admin credentials
	 * @param user This is the user object that should be saved in the DB.
	 * @return User This returns a user object that is saved in the DB.
	 */
	@RequestMapping(value="/adminLogin", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Instructor> adminLogin(@RequestBody Instructor instructor){
		logger.info("checking admin..." + instructor);
		Instructor ret = instructorService.checkUser(instructor);
		if(ret == null){
			return new ResponseEntity<Instructor>(ret, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Instructor>(ret, HttpStatus.OK);
	}
	
	/**
	 * This method is used to save the Instructor data entered by the admin during creation of account.
	 * @param user This is the instructor object that should be saved in the DB.
	 * @return User This returns a user object that is saved in the DB.
	 */
	@RequestMapping(value="/addInstructor", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor){
		logger.info("Adding Instructor..." + instructor);
		Instructor ret = instructorService.addUser(instructor);
		return new ResponseEntity<Instructor>(ret, HttpStatus.OK);
	}
	
	/**
	 * This method takes the id of the user as input and gives the Instructor object belonging to that id 
	 * @param id This is id of the user
	 * @return Instructor This returns instructor object
	 */
	@RequestMapping(value="/getInstructor/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Instructor> getInstructor(@PathVariable String id){		
		logger.info("Getting details of Instructor..");
		return new ResponseEntity<Instructor>(instructorService.findUser(id), HttpStatus.OK);
	}
	
	/**
	 * This method takes the id of the user as input and deletes the Instructor object belonging to that id 
	 * @param id This is id of the user
	 * @return Boolean This returns true if object is deleted
	 */
	@RequestMapping(value="/deleteInstructor/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteInstructor(@PathVariable String id){		
		logger.info("Deleting Instructor..");
		return new ResponseEntity<Boolean>(instructorService.deleteInstructor(id), HttpStatus.OK);
	}
	
	/**
	 * This method takes the id of the user as input and gives the Instructor object belonging to that id 
	 * @param id This is id of the user
	 * @return Instructor This returns instructor object
	 */
	@RequestMapping(value="/getInstructors", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Instructor>> getInstructors(){		
		logger.info("Getting details of Instructor..");
		return new ResponseEntity<List<Instructor>>(instructorService.getAllUsers(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */	
	@RequestMapping(value="/addQuiz/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> addQuiz(@PathVariable String id, @RequestBody Quiz quiz){		
		logger.info("adding Quiz..");
		
		Question question = new Question();
		question.setLabid(id);
		question = questionService.addQuestion(question);
		
		quiz.setId(question.getId());
		
		Lab lab = labService.findLab(id);
		List<Quiz> quizzes = lab.getQuizzes();
		quizzes.add(quiz);
		lab.setQuizzes(quizzes);
		lab = labService.saveLab(lab);
		return new ResponseEntity<Lab>(lab, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */	
	@RequestMapping(value="/createQuestion", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> createQuestion(){		
		logger.info("Creating question..");
		Question question = new Question();		
		return new ResponseEntity<Question>(question, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */	
	@RequestMapping(value="/createSubQuestion", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SubQuestion> createSubQuestion(){		
		logger.info("Creating subquestion..");
		SubQuestion subquestion = new SubQuestion();
		return new ResponseEntity<SubQuestion>(subquestion, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param lab
	 * @return
	 */	
	@RequestMapping(value="/addLab", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Lab> addLab(@RequestBody Lab lab){		
		logger.info("Adding Lab...");
		Lab result = labService.addLab(lab);
		
		return new ResponseEntity<Lab>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param lab
	 * @return
	 */	
	@RequestMapping(value="/deleteLab", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> deleteLab(@RequestBody Lab lab){		
		logger.info("Deleting Lab...");
		labService.deleteLab(lab);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Status", "success");
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}
}
