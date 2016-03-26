/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeededClass;


import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * this is the question paper . This class's object is sent to the client pc. This is like a simple question paper of the quiz.
 * @author Tahmid
 */
public class QuizQuestionPaper extends Quiz implements Serializable{
    public String name ;
    public String regNo;
    public StudentAnswer answerShit; 
    public QuizQuestionPaper() {
         super();
    }
    
    public  QuizQuestionPaper(String titel,Vector<McqQuestion> mcqQuestions,Vector<FillInTheBlanks> fillInTheBlankses, Vector<TrueFalse> trueFalseQuestions,Date startTime,int duration){
        
        this.fillInTheBlanksesQuestions = fillInTheBlankses;
        this.mcqQuestions = mcqQuestions;
        this.trueFalseQuestions = trueFalseQuestions;
        this.startTime = startTime;
        this.duration = duration;
        this.titel = titel;
    }
    /**
     * set student answer sheet vector
     * @param ans student answer vector
     */
    public void setStudentAnswer(StudentAnswer ans){
        this.answerShit = ans;
    }
    /**
     * get student answer sheet vector
     * @return student answer vector
     */
    public StudentAnswer getStudentAnswer(){
        return this.answerShit;
    }
    
    /**
     * set student name
     * @param name student name
     */
    public void setStudentName(String name){
        this.name = name;
    }
    /**
     * set student registration number
     * @param regNo student registration number
     */
    public void setStudentRegNo(String regNo){
        this.regNo = regNo;
    }
    /**
     * get student name
     * @return student name
     */
    public String getStudentName(){
        return this.name;
    }
    /**
     * get student registration number 
     * @return student registration number 
     */
    public String getStudentRegNo(){
        return this.regNo;
    }
    
    
}
