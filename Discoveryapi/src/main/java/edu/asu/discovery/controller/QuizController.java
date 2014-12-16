package edu.asu.discovery.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import edu.asu.discovery.model.AttemptSummary;
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
		
		HashMap<String, List<AttemptSummary>> quizattempts = userAnswer.getQuizattempts();
		//QuizAttempt quizattempt = null;
		int index = -1;
		Iterator<QuizAttempt> it;
		if(quizattempts == null){
			quizattempts = new HashMap<String, List<AttemptSummary>>();
		}
		
		List<AttemptSummary> attempts = quizattempts.get(quizid);
		
		
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
		
//		List<AttemptSummary> attempted = quizattempt.getAttempts();		
//		
//		//to avoid null pointer exception
//		if(attempted == null){
//			attempted = new ArrayList<AttemptSummary>();
//		}
		
		AttemptSummary attemptSummary = new AttemptSummary();
		attemptSummary.setQuizId(quizid);
		
		attemptSummary.setId(attempt.getId());
		attemptSummary.setDate(attempt.getDate());
		attemptSummary.setScore(attempt.getScore());
		attemptSummary.setSubmitstatus(attempt.isSubmitstatus());
		
		//quizattempt.setAttempts(attempted);	
		if(attempts != null){
			attempts.add(attemptSummary);
		}
		else{
			//quizattempts.put(quizid, attempts);
			attempts = new ArrayList<AttemptSummary>();
			attempts.add(attemptSummary);
		}
		
//		if(index != -1){
//			//quizattempts.set(index, quizattempt);
//			
//			attempts.add(quizattempt);
//			quizattempts.put(quizid, attempts);
//		}
//		else{
//			//quizattempts.add(quizattempt);
//			attempts.add(quizattempt);
//			quizattempts.put(quizid, attempts);
//		}
		
		quizattempts.put(quizid, attempts);
		
		userAnswer.setQuizattempts(quizattempts);
		
		System.out.println(userAnswer);
		System.out.println(attempt);
		
		userAnswer = userAnswerService.saveAnswer(userAnswer);
		
		return new ResponseEntity<Attempt>(attempt, HttpStatus.OK);
	}
	
	@RequestMapping(value="/submitAnswer/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Attempt> submitAnswer(@PathVariable int id, @RequestBody Attempt attempt){
		logger.info("Submitting answer for ..." + attempt.getUserid());
		
		//System.out.println(attempt);	
		
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
			UserAnswer userAnswer = userAnswerService.getUserAnswerDoc(question.getLabid(), attempt.getUserid());
			List<AttemptSummary> attempts = userAnswer.getQuizattempts().get(attempt.getQuizid()); 
			Iterator<AttemptSummary> it = attempts.iterator();
			while (it.hasNext()) {
				AttemptSummary item = it.next();
				if(item.getId().equals(attempt.getId())){
					item.setScore(attempt.getScore());
					item.setSubmitstatus(attempt.isSubmitstatus());
					break;
				}
				
			}
			
			userAnswer.getQuizattempts().put(attempt.getQuizid(), attempts);
			userAnswerService.saveAnswer(userAnswer);
		}
		
		List<SubAnswer> subanswers = attempt.getSubanswers();
		subanswers.set(id, subans);
		attempt.setSubanswers(subanswers);
		
		attempt = attemptService.saveAttempt(attempt);
		System.out.println(attempt);
		
		return new ResponseEntity<Attempt>(attempt, HttpStatus.OK);
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
