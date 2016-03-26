/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeededClass;

import java.io.Serializable;
import java.util.Vector;

/**
 * this class keep track of clients answer of varius questions
 *
 * @author olee
 */
public class StudentAnswer implements Serializable {

    public Vector<String> mcqAns; /// stors the mcq questions ans
    public Vector<String> fillInTheBlankAns; /// store the fill in the blanks ans
    public Vector<String> trueFalseAns; /// store the true false question ans
    public int correct_ans = 0; /// number of correct ans
    public int wrong_ans = 0; /// number of worng ans
    public int not_answered = 0; ///

    /**
     * constructor with studentAnswer type parameter
     *
     * @param ans StudentAnswer object
     */
    public StudentAnswer(StudentAnswer ans) {
        this.mcqAns = ans.mcqAns;
        this.fillInTheBlankAns = ans.fillInTheBlankAns;
        this.trueFalseAns = ans.trueFalseAns;
    }

    /**
     * empty constructor
     */
    public StudentAnswer() {
        correct_ans = wrong_ans = not_answered = 0;
        this.mcqAns = new Vector<>();
        this.fillInTheBlankAns = new Vector<>();
        this.trueFalseAns = new Vector<>();
    }

    /**
     * constructor with data
     *
     * @param mcqAns mcq answer vector
     * @param fillIntheBlanksAns fill in the blanks answer vector
     * @param trueFalseAns true false ans vector
     */
    public StudentAnswer(Vector<String> mcqAns, Vector<String> fillIntheBlanksAns, Vector<String> trueFalseAns) {
        this.mcqAns = mcqAns;
        this.fillInTheBlankAns = fillIntheBlanksAns;
        this.trueFalseAns = trueFalseAns;
    }

    /**
     * get the mcq question ans
     *
     * @return mcq question ans vector
     */
    public Vector<String> getMcqAns() {
        return this.mcqAns;
    }

    /**
     * get fill in the blanks answer vector
     *
     * @return fill in the blanks answer vector
     */
    public Vector<String> getFillInTheBlanksAns() {
        return this.fillInTheBlankAns;
    }

    /**
     * get true false answer vector
     *
     * @return true false answer vector
     */
    public Vector<String> getTrueFalseAns() {
        return this.trueFalseAns;
    }

    /**
     * set mcq answer vector
     *
     * @param mcqAns mcq answer vector
     */
    public void setMcqAns(Vector<String> mcqAns) {
        this.mcqAns = mcqAns;
    }

    /**
     * set fill in the blanks answer vector
     *
     * @param fillInTheBlanksAns fill in the blanks answer vector
     */
    public void setFillInTheBlanksAns(Vector<String> fillInTheBlanksAns) {
        this.fillInTheBlankAns = fillInTheBlanksAns;
    }

    /**
     * set true false answer vector
     *
     * @param trueFalseAns fill in the blanks answer vector
     */
    public void setTrueFlaseAns(Vector<String> trueFalseAns) {
        this.trueFalseAns = trueFalseAns;
    }
    

}
