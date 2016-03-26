package NeededClass;

import NeededClass.BasicQuestion;

/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
/**
 * this class is the Fill In The Blanks question class . It extends the
 * BasicQuestion class
 *
 * @author Tahmid
 */
public class McqQuestion extends BasicQuestion {

    public String options[]; /// options of the mcq question
    public int number_of_options; /// number of options 
    /**
     * empty constructor
     */
    public McqQuestion() {

    }
    /**
     * constructor for mcq Question
     * @param Body question body
     * @param name question name
     * @param ca correct answer
     * @param opt option array ref.
     */
    public McqQuestion(String Body, String name, String ca, String opt[]) {
        super(name, Body, ca);
        options = opt;
        number_of_options = opt.length;
    }
    /**
     * set mcq question's options
     * @param ara options array ref.
     */
    public void setOptions(String ara[]) {
        number_of_options = ara.length;
        options = ara;
    }
    /**
     * get mcq question's options
     * @return options of the mcq questions
     */
    public String[] getOptions() {
        return options;
    }

    @Override
    public String toString() {
        return name;
    }

}
