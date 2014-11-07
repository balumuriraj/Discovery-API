package edu.asu.discovery.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.discovery.model.Answer;
import edu.asu.discovery.model.Attempt;
import edu.asu.discovery.model.Lab;
import edu.asu.discovery.model.Question;
import edu.asu.discovery.model.QuizAttempt;
import edu.asu.discovery.model.SubAnswer;
import edu.asu.discovery.model.SubQuestion;
import edu.asu.discovery.model.User;
import edu.asu.discovery.model.UserAnswer;
import edu.asu.discovery.service.AttemptService;
import edu.asu.discovery.service.LabService;
import edu.asu.discovery.service.QuestionService;
import edu.asu.discovery.service.UserAnswerService;

@Controller
public class QuizController {
	
	@Autowired
	private LabService labService;
	
	@Autowired
	UserAnswerService userAnswerService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AttemptService attemptService;
	
	private static Logger logger = Logger.getLogger(MainController.class);
	
	@RequestMapping(value="/getReport/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAnswer> getReport(@PathVariable String id){		
		logger.info("Getting details of report..");
		return new ResponseEntity<UserAnswer>(userAnswerService.getReport(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getReports/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserAnswer>> getReports(@PathVariable String id){		
		logger.info("Getting details of report..");
		return new ResponseEntity<List<UserAnswer>>(userAnswerService.getReports(id), HttpStatus.OK);
	}
	
	/**
	 * This method is used to provide all reports
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getAllReports", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserAnswer>> getAllReports(){		
		logger.info("Getting all reports..");
		return new ResponseEntity<List<UserAnswer>>(userAnswerService.getAllReports(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAttemptDoc/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attempt> getAttemptDoc(@PathVariable String id){		
		logger.info("Getting UserAnswerDoc..");
		return new ResponseEntity<Attempt>(attemptService.getAttempt(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getQuestion/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Question> getQuestion(@PathVariable String id){		
		logger.info("Getting Question..");
		return new ResponseEntity<Question>(questionService.getQuestion(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/createAttemptDoc/{quizid}/{userid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attempt> createAttemptDoc(@PathVariable("quizid") String quizid, @PathVariable("userid") String userid){		
		logger.info("Getting createAttemptDoc..");

		Question question = questionService.getQuestion(quizid);
		String labid = question.getLabid();
		UserAnswer userAnswer = userAnswerService.getUserAnswerDoc(labid, userid);		
		
		if(userAnswer == null){
			userAnswer = new UserAnswer();
			userAnswer.setLabid(labid);
			userAnswer.setUserid(userid);
		}
		
		List<QuizAttempt> quizattempts = userAnswer.getQuizattempts();
		QuizAttempt quizattempt = null;
		int index = -1;
		Iterator<QuizAttempt> it;
		
		if(quizattempts != null){
			it = quizattempts.iterator();
			int counter = 0;
			while(it.hasNext()){
				QuizAttempt next = it.next();
				if(next.getQuizid().equals(quizid)){
					quizattempt = next;
					index = counter;
				}
				counter++;
			}
		}
		else{
			quizattempts = new ArrayList<QuizAttempt>();
		}
		
		if(quizattempt == null){
			quizattempt = new QuizAttempt();
			quizattempt.setQuizid(quizid);
		}		
		
		Attempt attempt = new Attempt();
		attempt.setQuizid(quizid);
		attempt.setUserid(userid);
		attempt.setDate(new Date());
		attempt.setSubmitstatus(false);
		attempt.setScore(0);
		attempt.setClock(0);
		attempt.setSubmitcount(0);
		
		List<SubAnswer> subanswers = new ArrayList<SubAnswer>();
		
		for(int i=0; i<question.getSubquestions().size(); i++){		
			SubAnswer subAnswer = new SubAnswer();
			subAnswer.setId(i+1);
			subAnswer.setSubanswer("");
			subAnswer.setCorrect(false);
			subAnswer.setSaved(false);
			subAnswer.setSubmitted(false);
			subanswers.add(subAnswer);
				
		}
		
		attempt.setSubanswers(subanswers);
		
		attempt = attemptService.saveAttempt(attempt);
		
		List<String> attempts = quizattempt.getAttempts();		
		
		//to avoid null pointer exception
		if(attempts == null){
			attempts = new ArrayList<String>();
		}
		attempts.add(attempt.getId());		
		quizattempt.setAttempts(attempts);	
		
		if(index != -1){
			quizattempts.set(index, quizattempt);
		}
		else{
			quizattempts.add(quizattempt);
		}
		
		userAnswer.setQuizattempts(quizattempts);
		
		System.out.println(userAnswer);
		System.out.println(attempt);
		
		userAnswer = userAnswerService.saveAnswer(userAnswer);
		
		return new ResponseEntity<Attempt>(attempt, HttpStatus.OK);
	}
	
	@RequestMapping(value="/submitAnswer/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attempt> submitAnswer(@PathVariable int id, @RequestBody Attempt attempt){
		logger.info("Submitting answer for ..." + attempt.getUserid());
		
		System.out.println(attempt);	
		
		Question question = questionService.getQuestion(attempt.getQuizid());
		List<SubQuestion> subquestions = question.getSubquestions();
		SubQuestion subques = subquestions.get(id);
		SubAnswer subans = attempt.getSubanswers().get(id);
		
		double score = attempt.getScore();
		
		if(subques.getAnswerrange() == 0){
			
			if(subques.getCorrectanswer().equals(subans.getSubanswer())){
				score = score + 10.0;
				subans.setCorrect(true);
			}
		} else{
			double range1 = Double.valueOf(subques.getCorrectanswer()) -  Double.valueOf(subques.getAnswerrange());
			double range2 = Double.valueOf(subques.getCorrectanswer()) +  Double.valueOf(subques.getAnswerrange());
			double rang_ans = Double.valueOf(subans.getSubanswer());
			if(range1 <= rang_ans && rang_ans <= range2){
				score = score + 10.0;
				subans.setCorrect(true);
			}
		}
			
		System.out.println("Score: " + score);
		subans.setSaved(false);
		subans.setSubmitted(true);
		attempt.setScore(score);	
		attempt.setSubmitcount(attempt.getSubmitcount()+1);
		
		if(attempt.getSubmitcount() == attempt.getSubanswers().size()){
			attempt.setSubmitstatus(true);
		}
		
		List<SubAnswer> subanswers = attempt.getSubanswers();
		subanswers.set(id, subans);
		attempt.setSubanswers(subanswers);
		
		attempt = attemptService.saveAttempt(attempt);
		
		return new ResponseEntity<Attempt>(attempt, HttpStatus.OK);
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
