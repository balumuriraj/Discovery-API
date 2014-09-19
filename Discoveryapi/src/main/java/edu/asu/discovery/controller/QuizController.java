package edu.asu.discovery.controller;

import java.text.DecimalFormat;
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
	public ResponseEntity<UserAnswer> getLab(@PathVariable String id){		
		logger.info("Getting details of report..");
		return new ResponseEntity<UserAnswer>(userAnswerService.getReport(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/submitAnswer", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAnswer> submitAnswer(@RequestBody UserAnswer userAnswer){
		logger.info("Submitting answer for ..." + userAnswer.getUserid());
		
		System.out.println(userAnswer);
		
		if(userAnswer.getDate() == null){
			userAnswer.setDate(new Date());
		}
		
		int currentanswerid = userAnswer.getCurrentquestion();		
		
		Lab lab = labService.findLab(userAnswer.getLabid());
		Question currentquestion = lab.getLabquestions().get(currentanswerid);
		List<SubQuestion> subques = currentquestion.getSubquestions();		
		
		List<Answer> ans = userAnswer.getAnswers();
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
			userAnswer.setSubmitstatus(true);
		}
		
		userAnswer.setScore(userAnswer.getScore() + totalscore);		
		userAnswer.setCurrentquestion(currentanswerid+1);		
		
		UserAnswer ret = userAnswerService.saveAnswer(userAnswer);
		
		return new ResponseEntity<UserAnswer>(ret, HttpStatus.OK);
	}
	
	double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
