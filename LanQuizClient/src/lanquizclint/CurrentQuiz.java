/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanquizclint;

import NeededClass.Student;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.awt.Desktop;
import java.io.File;
import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.MemoryHandler;
import java.util.logging.StreamHandler;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import NeededClass.QuizQuestionPaper;

/**
 *
 * @author Tahmid
 */
public class CurrentQuiz {

    public static boolean quizStartedFlag = false;
    public static QuizQuestionPaper question;
   
    public static int totalQuestions;
    public static int mcqQuestionNumber = 0; /// value of this variable is the index of the mcq question which is currently shown to the user
    public static int fillInTheBlanksQuestionNumber = 0; ///value of this variable is the index of the fill in the blank question which is current shown to the user
    public static int trueFalseQuestionNumber = 0; ///valude of this variable is the index of the true false question which is currently shown to the user

    public static void initialDetail() {
        totalQuestions = 0;
        if (question == null) {
            return;
        }
        if (question.mcqQuestions != null) {
            totalQuestions += question.mcqQuestions.size();
        }
        if (question.fillInTheBlanksesQuestions != null) {
            totalQuestions += question.fillInTheBlanksesQuestions.size();
        }
        if (question.trueFalseQuestions != null) {
            totalQuestions += question.trueFalseQuestions.size();
        }

        mcqQuestionNumber = question.getStudentAnswer().getMcqAns().size();
        fillInTheBlanksQuestionNumber = question.getStudentAnswer().getFillInTheBlanksAns().size();
        trueFalseQuestionNumber = question.getStudentAnswer().getTrueFalseAns().size();
        System.out.println("all okk here");

    }

    public static void setQuizQuestion(QuizQuestionPaper question) {
        CurrentQuiz.question = question;
    }

    public static QuizQuestionPaper getCurrentQuizQuestion() {
        return CurrentQuiz.question;
    }

    public static void updateTimeTable() {
        if (CurrentQuiz.quizStartedFlag == false) {
            Date stTime = ServerConnection.askStartTime();
          
            if (stTime == null) {

            } else if (CurrentQuiz.getCurrentQuizQuestion().getStartTime().getTime() != stTime.getTime()) {
                System.out.println("prv time: " + CurrentQuiz.getCurrentQuizQuestion().getStartTime() + " . new time : " + stTime);
                CurrentQuiz.getCurrentQuizQuestion().setStartTime(stTime);
                System.out.println("hellow start time changed");
            }
        }  
        
        int duration = ServerConnection.askQuizDuration();
        if (duration == -1) {
            /// do nothing error in getting to server
        } else if (CurrentQuiz.getCurrentQuizQuestion().getDuration() != duration) {
            CurrentQuiz.getCurrentQuizQuestion().setDuration(duration);
            System.out.println("duration changed");
        }
    }

}
