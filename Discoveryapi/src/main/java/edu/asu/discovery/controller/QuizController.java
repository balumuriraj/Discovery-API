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
import edu.asu.discovery.model.SubAnswer;
import edu.asu.discovery.model.SubQuestion;
import edu.asu.discovery.model.User;
import edu.asu.discovery.model.UserAnswer;
import edu.asu.discovery.service.LabService;
import edu.asu.discovery.service.UserAnswerService;

@Controller
public class QuizController {
	
	@Autowired
	private LabService labService;
	
	@Autowired
	UserAnswerService userAnswerService;
	
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
	
	@RequestMapping(value="/getUserAnswerDoc/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAnswer> getUserAnswerDoc(@PathVariable String id){		
		logger.info("Getting UserAnswerDoc..");
		return new ResponseEntity<UserAnswer>(userAnswerService.getReport(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/createUserAnswerDoc/{labid}/{userid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAnswer> createUserAnswerDoc(@PathVariable("labid") String labid, @PathVariable("userid") String userid){		
		logger.info("Getting UserAnswerDoc..");
		
		UserAnswer userAnswer = userAnswerService.getUserAnswerDoc(labid, userid);
		Lab lab = labService.findLab(labid);
		
		if(userAnswer == null){
			userAnswer = new UserAnswer();
			userAnswer.setLabid(labid);
			userAnswer.setUserid(userid);
		}
		
		Attempt attempt = new Attempt();
		attempt.setDate(new Date());
		attempt.setSubmitstatus(false);
		attempt.setCurrentquestion(0);
		attempt.setScore(0);
		attempt.setClock(0);
		
		for(int i=0; i<lab.getLabquestions().size(); i++){
		
			Answer answer = new Answer();
			answer.setId(i+1);
			
			for(int j=0; j<lab.getLabquestions().get(i).getSubquestions().size(); j++){
				
				SubAnswer subAnswer = new SubAnswer();
				subAnswer.setId(j+1);
				subAnswer.setSubanswer("");		
				List<SubAnswer> subanswers = answer.getSubanswers();
				
				//to avoid null pointer exception
				if(subanswers == null){
					subanswers = new ArrayList<SubAnswer>();
				}
				subanswers.add(subAnswer);
				answer.setSubanswers(subanswers);
			}
			
			List<Answer> answers = attempt.getAnswers();
			
			//to avoid null pointer exception
			if(answers == null){
				answers = new ArrayList<Answer>();
			}
			answers.add(answer);
			attempt.setAnswers(answers);			
		}
		
		List<Attempt> attempts = userAnswer.getAttempts();
		
		//to avoid null pointer exception
		if(attempts == null){
			attempts = new ArrayList<Attempt>();
		}
		attempts.add(attempt);
		userAnswer.setAttempts(attempts);	
		
		System.out.println(userAnswer);
		
		userAnswer = userAnswerService.saveAnswer(userAnswer);
		
		return new ResponseEntity<UserAnswer>(userAnswer, HttpStatus.OK);
	}
	
	@RequestMapping(value="/submitAnswer", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAnswer> submitAnswer(@RequestBody UserAnswer userAnswer){
		logger.info("Submitting answer for ..." + userAnswer.getUserid());
		
		System.out.println(userAnswer);
		int attemptindex = userAnswer.getAttempts().size() - 1;
		
		int currentanswerid = userAnswer.getAttempts().get(attemptindex).getCurrentquestion();		
		
		Lab lab = labService.findLab(userAnswer.getLabid());
		System.out.println(lab.toString());
		Question currentquestion = lab.getLabquestions().get(currentanswerid);
		List<SubQuestion> subques = currentquestion.getSubquestions();		
		
		List<Answer> ans = userAnswer.getAttempts().get(attemptindex).getAnswers();
		List<SubAnswer> subans = ans.get(currentanswerid).getSubanswers();
		
		double totalscore = 10.0;
		double eachscore = roundTwoDecimals((double) (totalscore/(double)subques.size()));
		
		System.out.println("Eachscore: "+eachscore);
		
		Iterator<SubQuestion> ques_it = subques.iterator();
		Iterator<SubAnswer> ans_it = subans.iterator();
		
		while(ques_it.hasNext() && ans_it.hasNext()){
			SubQuestion currentques = ques_it.next();
			SubAnswer currentans = ans_it.next();
			
			if(currentques.getAnswerrange() == 0){
				
				if(!currentques.getCorrectanswer().equals(currentans.getSubanswer())){
					totalscore = roundTwoDecimals(totalscore - eachscore);
					System.out.println("Score after bit: " + totalscore);
				}
			} else{
				double range1 = Double.valueOf(currentques.getCorrectanswer()) -  Double.valueOf(currentques.getAnswerrange());
				double range2 = Double.valueOf(currentques.getCorrectanswer()) +  Double.valueOf(currentques.getAnswerrange());
				double rang_ans = Double.valueOf(currentans.getSubanswer());
				if(!(range1 <= rang_ans && rang_ans <= range2)){
					totalscore = roundTwoDecimals(totalscore - eachscore);
					System.out.println("Score after bit: " + totalscore);
				}
			}
		}
		
		System.out.println("Score: " + totalscore);
		
		if(currentanswerid+1 == ans.size()){
			userAnswer.getAttempts().get(attemptindex).setSubmitstatus(true);
		}
		
		userAnswer.getAttempts().get(attemptindex).setScore(userAnswer.getAttempts().get(attemptindex).getScore() + totalscore);		
		userAnswer.getAttempts().get(attemptindex).setCurrentquestion(currentanswerid+1);		
		
		UserAnswer ret = userAnswerService.saveAnswer(userAnswer);
		
		return new ResponseEntity<UserAnswer>(ret, HttpStatus.OK);
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
