/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanquizclint;

import NeededClass.QuizQuestionPaper;
import NeededClass.Request;
import NeededClass.StudentAnswer;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * this class connect with the server , send and receive  various kind of data 
 * @author Tahmid
 */
public class ServerConnection {

    public static int port; /// port of the server
    public static String userName; /// user name of the user
    public static String regNo; /// registration number of the user
    public static String pass; /// password of the user
    public static String serverIp; /// IP address of server
    public static boolean finalStatusFlag = false; /// a flag for the final status flag
    /**
     * 
     * @param request type of the request that the client application have to the server application 
     * @param ara is a variable argument parameter . it var
     * @return 
     */
    public static Object askServer(Request request, Object... ara) {
        try {
            Socket server = new Socket(serverIp, port); /// trying to connect the server
            
            /*
            initilizing the input and output streams
            */
            ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(server.getInputStream());
    
            output.writeObject(request); ///sending the request type to the server
            output.flush();
            /// send adition data to server
           
            for (int i = 0; i < ara.length; i++) {
               
                output.writeObject(ara[i]);
              
                output.flush();
            }
            output.flush();
       
             
            Object ret = input.readObject(); /// recieving data from server
           
            output.close();
            input.close();
            server.close();

            return ret;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("olee :*");
            return null;
        }
    }
    
    /**
     * this method request server for log in 
     * @return -1 if error in connection , 1 if successful , 0 if error in data
     */
    
    public static int logIn() {
        
        Object ret = askServer(Request.LOGIN, userName, regNo, pass); /// this call ask the serber to log in
        if (ret == null) { /// error in log in .
            return -1;
        }
        if ((boolean) ret == true) { /// successfully loged in
            return 1;
        }
        return 0; /// error in data
    }
    
    /**
     * this method ask the server to log out
     * @return 
     */
    public static boolean logout() {
        return false;
    }
    
    /**
     * this method ask the server for question
     * @return null if error in getting question from server , otherwise returns the question   
     */
    public static QuizQuestionPaper getQuestions() {
        QuizQuestionPaper question = (QuizQuestionPaper) askServer(Request.QUESTION,regNo);///request for question
        if (question == null) { /// if any error happens then it try one more time
            question = (QuizQuestionPaper) askServer(Request.QUESTION,regNo);
        }
        return question;
    }
    /**
     * this method send the current status of the student to the server
     * @param ans current answer sheet
     * @return true if successful , false otherwise
     */
    public static boolean submit_current_status(StudentAnswer ans) {
        Object ob = askServer(Request.SUBMIT_CURRENT_STATUS, regNo,ans);
        if(ob==null) return false;
        boolean flag = (boolean) askServer(Request.SUBMIT_CURRENT_STATUS, regNo,ans);
        return flag;
    }
    
    /**
     * this method sends the final status to the server
     * @param ans student answer sheet
     * @return true if successful , false otherwise
     */
    public static boolean submit_final_status(StudentAnswer ans) {
        Object ob = askServer(Request.SUBMIT_FULL_STATUS_AND_LOGOUT, regNo ,ans); /// asking the server to take the final status
        if(ob==null){ ///ob == null if any error happent during sending data , and try one more time
            ob = askServer(Request.SUBMIT_FULL_STATUS_AND_LOGOUT, regNo ,ans);
        }
        if(ob==null) return false; /// error contition
       
        boolean flag = (boolean) ob; ///flag returned from server
        finalStatusFlag = flag;
        return flag;
    }
    /**
     * this method ask the server for the start time of the quiz
     * @return null if any error happen , otherwise the start time of the quiz
     */
    public static Date askStartTime() {
        Object start = askServer(Request.START_TIME);
        if (start == null) { /// if any thing error then try one more time
            start = askServer(Request.START_TIME);
        }
        if(start==null) return null;
        return (Date) start;
    }

    public static long askEndTime() {
        Object end = askServer(Request.FINISH_TIME);
        if (end == null) {
            return -1;
        }
        return (long) end;
    }
    
    /**
     * this method ask the server for the duration of the quiz
     * @return -1 if any error , duration otherwise
     */
    public static int askQuizDuration(){
        Object duration = askServer(Request.DURATION);
        if(duration==null){
            return -1;   
        }
        return (int)duration;
    }
}
