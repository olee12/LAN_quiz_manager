/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanQuizServer;

import NeededClass.StudentAnswer;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * this class control the quiz status , update the student data table and status box .
 * @author Tahmid
 */
public class QuizStatus {
    public static JTextArea statusBox; ///status box ref.
    public static JTable studentTable; ///student table ref.
    /**
     * set the status box ref.
     * @param tmp stutus box ref.
     */
    public static void setStatusBox(JTextArea tmp){
         statusBox = tmp;
    }
    /**
     * set the student table ref.
     * @param tmp student table ref.
     */
    public static void SetStudentTable(JTable tmp){
        studentTable = tmp;
    }
    /**
     * adds a status in the status box
     * @param msg 
     */
    public static void addStatus(String msg){
        statusBox.append(msg+"\n");
    }
    //{"ID","Name","Registration No","password","Status","Question sent","Login Time","Correct Ans","Wrong Ans","Not Answered","Logout time"};
    
    /**
     * update the log in time in the table
     * @param reg student registration number
     * @param i index of the row in the table
     */
    public static void setLogInTime(String reg,int i){
        studentTable.setValueAt(new Date(), i, 5);
    }
     /**
     * update the log out time in the table
     * @param i index of the row in the table
     */
    public static void setLogOutTime(int i){
        studentTable.setValueAt(new Date(), i, 8);
    }
    
    /**
     * set a student present in the student table
     * @param reg student's registration number
     * @param pass student's password 
     * @return true if everything is all right or false if something is wrong
     */
    public static boolean setStudentPresent(String reg,String pass){
        for(int i = 0;i<studentTable.getRowCount();i++){
            if(((String)studentTable.getValueAt(i, 2)).trim().equals(reg) && ((String)studentTable.getValueAt(i, 3)).trim().equals(pass)){
              //  if(((String)studentTable.getValueAt(i, 4)).trim().equals("Present")==true) return false;
                studentTable.setValueAt("Present", i, 4);
                setLogInTime(reg, i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * update the current status
     * @param reg student's registration number
     * @param ans student's answer sheet
     * @return true if everything is all right else returns false
     */
    public static boolean update_current_status(String reg,StudentAnswer ans){
        CurrentQuiz.allStudentAnsSheet.put(reg, ans);
        return update_current_status(reg, ans.correct_ans,ans.wrong_ans);
    }
    ///{"ID","Name","Registration No","password","Status","Login Time","Correct Ans","Wrong Ans","Logout time"};
    
    /**
     * update the current status
     * @param reg student's registration number
     * @param ca number of correct ans
     * @param wa number of wrong ans
     * @return true if everything is all right else returns false
     */
    public static boolean update_current_status(String reg,int ca,int wa){
        for(int i = 0;i<studentTable.getRowCount();i++){
            if(((String)studentTable.getValueAt(i, 2)).trim().equals(reg)){
                studentTable.setValueAt(ca, i, 6);
                studentTable.setValueAt(wa, i, 7);
               
                return true;
            }
        }
        return false;
    }
      /**
     * update the final status
     * @param reg student's registration number
     * @param ans student's answer sheet
     * @return true if everything is all right else returns false
     */
    public static boolean update_final_status(String reg,StudentAnswer ans){
        CurrentQuiz.allStudentAnsSheet.put(reg, ans);
        return update_final_status(reg, ans.correct_ans,ans.wrong_ans);
    }
     /**
     * update the current status
     * @param reg student's registration number
     * @param ca number of correct ans
     * @param wa number of wrong ans
     * @return true if everything is all right else returns false
     */
    public static boolean update_final_status(String reg,int ca,int wa){
          for(int i = 0;i<studentTable.getRowCount();i++){
            if(((String)studentTable.getValueAt(i, 2)).trim().equals(reg)){
                studentTable.setValueAt(ca, i, 6);
                studentTable.setValueAt(wa, i, 7);
                 
                setLogOutTime(i);
                return true;
            }
        }
        return false;
    }
    
    public static void saveStudentIP(String regNo,String ip){
        CurrentQuiz.studentsIP.get(regNo).add(ip);
    }
}
