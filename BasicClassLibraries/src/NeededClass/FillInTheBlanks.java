/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * this class is the Fill In The Blanks question class . It extends the BasicQuestion class
 */
package NeededClass;

import NeededClass.BasicQuestion;

/**
 *
 * @author Tahmid
 */
public class FillInTheBlanks extends BasicQuestion {
    /**
     * empty constructor
     */
    public FillInTheBlanks(){
        
    }
    /**
     * constructor function
     * 
     * @param name name of the question
     * @param body qestion body
     * @param ca correct ans of the question
     */
    public FillInTheBlanks(String name,String body,String ca){
        super(name,body,ca);
    }
    @Override
    public String toString(){
        return name;
    }
}
