/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * 
 * this class is the base class of all questions . 
 * 
 */
package NeededClass;

import java.io.Serializable;

/**
 *
 * @author Tahmid
 */
public class BasicQuestion implements Serializable {
    
    public String qBody;// question body
    public String name; // question name
    public String correctAns; // correct ans of the question
   

    public BasicQuestion() {
        
    }

    public BasicQuestion(String nam, String body, String ca) {
        this.qBody = body;
        this.name = nam;
        this.correctAns = ca;
    }

    /**
     *  set question body
     * @param body question body
     */
    public void setbody(String body) {
        qBody = body;
    }
    
    /**
     * set question name
     * @param N question name
     */
    public void setName(String N) {
        name = N;
    }
    /**
     * set correct answer of the question
     * @param ca question correct answer
     */
    public void setCorrectAns(String ca) {
        correctAns = ca;
    }
    /**
     * get question name
     * @return question name
     */
    public String getName() {
        return name;
    }
    /**
     * get question body
     * @return question body
     */
    public String getBody() {
        return qBody;
    }
    /**
     * get correct answer of the question
     * @return correct answer
     */
    public String getCorrectAns() {
        return correctAns;
    }
//    public String toString(){
//        return name;
//    }

}
