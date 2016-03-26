/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeededClass;

import java.io.Serializable;
import java.util.Date;
import java.util.Stack;
import java.util.Vector;

/**
 * This is the hole Quiz class . It keep track of every thing in a quiz. 
 * all type of questions
 * student data
 * start time
 * duration 
 * quiz titel
 * 
 * @author Tahmid
 */
public class Quiz implements Serializable {
    
    public Vector<McqQuestion> mcqQuestions;
    public Vector<TrueFalse> trueFalseQuestions;
    public Vector<FillInTheBlanks> fillInTheBlanksesQuestions;
    public Vector<Student> studentData;
    public Date startTime;
    public int duration;
    public String titel;
    /**
     * empty constructor
     */
    public Quiz() {
        titel = "";
        mcqQuestions = new Vector<>();
        trueFalseQuestions = new Vector<>();
        fillInTheBlanksesQuestions = new Vector<>();
        studentData = new Vector<>();
        startTime = new Date();
    }
    /**
     * gets the questions(all mcq,fill,trueFalse)
     * @return 
     */
    public QuizQuestionPaper getQuizQuestion(){
       return new QuizQuestionPaper(this.titel,this.mcqQuestions,this.fillInTheBlanksesQuestions,this.trueFalseQuestions,this.startTime,this.duration);
    }
    /**
     * set the titel of the quiz
     * @param titel titel of the quiz
     */
    public void setTitel(String titel){
        this.titel = titel;
    }
    /**
     * set the mcq question 
     * @param mcq mcq question vector 
     */
    public void setMcq(Vector<McqQuestion> mcq) {
        this.mcqQuestions = mcq;
    }
    /**
     * set the fill in the blanks question
     * @param fill fill in the blanks question vector
     */
    public void setFillInTheBlanksQuestion(Vector<FillInTheBlanks> fill) {
        this.fillInTheBlanksesQuestions = fill;
    }
    /**
     * set the fill in the blanks question
     * @param trueFalse True false question vector
     */
    public void setTrueFalseQuestion(Vector<TrueFalse> trueFalse) {
        this.trueFalseQuestions = trueFalse;
    }
    /**
     * set Student data 
     * @param student student list vector
     */
    public void setStudentsData(Vector<Student> student) {
        this.studentData = student;
    }
    /**
     * set the start time of quiz
     * @param startTime start time of  the quiz
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    /**
     * set the duration of the quiz
     * @param duration duration of the quiz
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
    /**
     * gets the mcq question vector
     * @return mcq quetion vector
     */
    public Vector<McqQuestion> getMcqQuestion() {
        return mcqQuestions;
    }
    /**
     * get the fill in the blanks question
     * @return fill in the blanks question vector
     */

    public Vector<FillInTheBlanks> getFillInTheBlanksQuestion() {
        return fillInTheBlanksesQuestions;
    }
    
    /**
     * get the true false question list
     * @return true false question vector
     */
    public Vector<TrueFalse> getTrueFalseQuestion(){
        return this.trueFalseQuestions;
    }
    /**
     * get the student data
     * @return student data vector
     */
    public Vector<Student> getStudentsData() {
        return studentData;
    }
    
    /**
     * get the start time of quiz
     * @return start time of the quiz 
     */
    public Date getStartTime() {
        return startTime;
    }
    /**
     * get the duration of the quiz
     * @return duration of the quiz
     */
    public int getDuration() {
        return duration;
    }
    /**
     * get the title of the quiz
     * @return title of the quiz
     */
    public String getTitel(){
        return this.titel;
    }
}
