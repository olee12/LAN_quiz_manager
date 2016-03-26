/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanQuizServer;

import NeededClass.FillInTheBlanks;
import NeededClass.McqQuestion;
import NeededClass.Student;
import NeededClass.TrueFalse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//import org.*;
/**
 * this class process all kind of question and student data and return them in desired format (vector)
 * @author Tahmid
 */
public class ProcessQuestion {

    final public static String COMMA = ",";
    final public static String NEW_LINE = "\n";
    
    
    /**
     * this method process mcq question file and return the question in a vector of mcqQuestion
     * @param mcqFile mcq question file path
     * @return a vector of mcqQuestion
     */
    public static Vector<McqQuestion> processMcq(File mcqFile) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(mcqFile));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error in openning mcqFile");
            return null;
        }

        Vector<McqQuestion> question = new Vector<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(COMMA);
                if (token.length == 6) {
                    McqQuestion tmpQ = new McqQuestion();
                    String options[] = new String[4];
                    tmpQ.setbody(token[0]);
                    for (int i = 1; i < 1 + 4; i++) {
                        options[i - 1] = token[i];
                    }
                    tmpQ.setOptions(options);
                    tmpQ.setCorrectAns(token[5]);
                    tmpQ.setName(("Question : " + (question.size() + 1)));
                    question.add(tmpQ);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in Mcq Question File Formate");
                    return null;
                }
            }
            return question;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in Reading File");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * this method process fill in the blanks question file and return the question in a vector of fillInTheBlanksQuestion
     * @param qFile fillInTheBlanksQuestion question file path
     * @return a vector of fillInTheBlanksQuestion
     */
    public static Vector<FillInTheBlanks> processFillInTheBlanks(File qFile) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(qFile));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error in Fill In The Blanks Question file");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        Vector<FillInTheBlanks> questions = new Vector<>();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(COMMA);
                if (token.length >= 2) {
                    FillInTheBlanks tmpQ = new FillInTheBlanks( ("Question : " + (questions.size() + 1)),token[0], token[1]);
                    questions.add(tmpQ);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in Fill In The Blanks file formet");
                    return null;
                }
            }
            return questions;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in Reading File");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    /**
     * this method process true false question file and return the question in a vector of TrueFalse
     * @param trueFalseFile trueFalse question file path
     * @return a vector of trueFalseFile question
     */
    public static Vector<TrueFalse> processTrueFalse(File trueFalseFile) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(trueFalseFile));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error in TrueFalse Question File");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }

        Vector<TrueFalse> questions = new Vector<>();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(COMMA);
                TrueFalse tmpQ;
                if (token.length == 2) {
                    tmpQ = new TrueFalse("Question No : " + (questions.size() + 1), token[0], token[1]);
                    questions.add(tmpQ);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in TrueFalse Question Formate");
                    return null;
                }

            }
            return questions;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in Reading File");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    /**
     * this method process the student data without password file and return a studentData vector
     * @param studentFile student data without password file
     * @return a vector of student data
     */
    public static Vector<Student> processStudentDataWithoutPassword(File studentFile) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(studentFile));
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error In Student Data File");
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        Vector<Student> studentData = new Vector<>();

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(COMMA);
                if (token.length == 2) {
                    Student tmpS = new Student(studentData.size() + 1, token[0], token[1]);
                    studentData.add(tmpS);
                } else {
                    JOptionPane.showMessageDialog(null, "Error In Student Data File Formate . Please Load a student data file which has no password");
                    return null;
                }
            }
            return studentData;

        } catch (IOException ex) {
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    /**
     * this method process the student data with password file and return a studentData vector
     * @param studentFile student data with password file
     * @return a vector of student data
     */
    public static Vector<Student> processStudentDataWithPassword(File studentFile) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(studentFile));
        } catch (Exception ex) {
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error in Student Data With Password File");
            return null;
        }

        Vector<Student> student = new Vector<>();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String token[] = line.split(COMMA);
                if (token.length == 3) {
                    student.add(new Student(student.size() + 1, token[0], token[1], token[2]));
                } else {
                    JOptionPane.showMessageDialog(null, "Error in student data with password file format. \"The format should be Reg no,Name,Password \".");
                    return null;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return student;
    }
    /**
     * this method write student data whih password in the specific file 
     * @param path file saving path
     */
    public static void makeStudentDataWithPassword(String path) {
        FileWriter filewriter = null;
        try {
            filewriter = new FileWriter(new File(path));

            Vector<Student> student = CurrentQuiz.curQuiz.getStudentsData();
            for (int i = 0; i < student.size(); i++) {
                filewriter.append(student.get(i).getStudentRegNo());
                filewriter.append(COMMA);
                filewriter.append(student.get(i).getStudentName());
                filewriter.append(COMMA);
                filewriter.append(student.get(i).getPassword());
                filewriter.append(NEW_LINE);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProcessQuestion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                filewriter.flush();
                filewriter.close();
            } catch (IOException e) {

            }
        }
    }

}
