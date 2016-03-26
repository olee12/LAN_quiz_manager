/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NeededClass;

import NeededClass.BasicQuestion;

/**
 * this is true false question class which extends BasicQuestion class
 * @author Tahmid
 */
public class TrueFalse extends BasicQuestion {
    /**
     * empty constructro
     */
    public TrueFalse() {
        
    }
    /**
     * constructor with data
     * @param name question name
     * @param body question body
     * @param ca correct answer of the question
     */
    public TrueFalse(String name, String body, String ca) {
        super(name, body, ca);
    }

    @Override
    public String toString() {
        return name;
    }

}
