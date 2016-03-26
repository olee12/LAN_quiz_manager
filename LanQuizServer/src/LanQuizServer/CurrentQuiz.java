/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanQuizServer;

import NeededClass.Quiz;
import NeededClass.QuizQuestionPaper;
import NeededClass.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olee
 */
public class CurrentQuiz {

    public static Quiz curQuiz;
    public static HashMap allStudentAnsSheet;
   public static HashMap< String, TreeSet<String> > studentsIP;
    public static void setAllStudentAnsSheet(HashMap data){
        CurrentQuiz.allStudentAnsSheet = data;
    }
    
    public static HashMap getAllStudentAnsSheet(){
        return CurrentQuiz.allStudentAnsSheet;
    }
    
    public static Quiz getCurrentQuiz(){
        return CurrentQuiz.curQuiz;
    }
    
    public static void setCurrentQuiz(Quiz curQ){
        CurrentQuiz.curQuiz = curQ;
    }
    
  // public static Date startTime;
    //  public static int duration;

}
