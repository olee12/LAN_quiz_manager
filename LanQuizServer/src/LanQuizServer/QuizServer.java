/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanQuizServer;

import NeededClass.QuizQuestionPaper;
import NeededClass.Request;
import NeededClass.StudentAnswer;
import com.sun.corba.se.impl.io.IIOPInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *  this class control the quiz server , connects with the clients and send and receive various king of data. 
 * @author Tahmid
 */
public class QuizServer {

    public static boolean stopServer = false;
    public static ObjectInputStream input; /// to take input from the client
    public static ObjectOutputStream output; /// to output to the client
    public static ServerSocket server; // main server 
    public static Socket clientSoket; /// current client
    public static Request request; /// client 
    public static int port; /// server port number
    
    /**
     * gets the IP address of the server
     * @return the the IP address of the server
     */
    public static String getIpAdress() {
        try {
            InetAddress IP = InetAddress.getLocalHost();
            return IP.getHostAddress();
        } catch (UnknownHostException ex) {
            return "127.0.0.1";
        }
    }

    /**
     * activating Server
     */
    public static void readyServer() throws Exception {
        try {
            if (readySocket(CurrentQuiz.curQuiz.studentData.size() + 10) == false) {
                readySocket(CurrentQuiz.curQuiz.studentData.size() + 10);
            }
            /**
             * starting a new Thread for responding to clients.
             */

            Thread t = null;

            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    startworking();
                }
            });
            t.start();

        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * this method make the socket ready
     * @param sz /// the size of server socket
     * @return true if everything is all right
     * @throws Exception if any
     */
    public static boolean readySocket(int sz) throws Exception {
        try {
            if (server != null) {
                server.close();
            }
            server = new ServerSocket(QuizServer.port, sz);
            server.setReuseAddress(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * starts the server , server then starts to listening to the clients
     */
    public static void startworking() {
        try {
            String serverIp = getIpAdress();
            Date current_Date = new Date();
            QuizStatus.addStatus(current_Date + " : Server is Startting...");
            int local_port = server.getLocalPort();
            QuizStatus.addStatus(current_Date + " : Server Ip : " + serverIp + " Server Port : " + local_port);
            QuizStatus.addStatus("Waiting for Clients");

            while (true) {
                try {
                    clientSoket = server.accept();
                    output = new ObjectOutputStream(clientSoket.getOutputStream());
                    input = new ObjectInputStream(clientSoket.getInputStream());

                    request = (Request) input.readObject();

                    processRequest(request);

                    output.close();
                    input.close();
                    clientSoket.close();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e + " olee");
                }
            }
        } catch (Exception e) {
            QuizStatus.addStatus("error while making the server . please restart the application and check your network connection");
        }

    }
    /**
     * this method gets the client's IP address
     * @return client ip address
     */
    
    public static String getClientIP()
    {
        String ip = clientSoket.getRemoteSocketAddress().toString();
        if (ip.startsWith("/")) ip = ip.substring(1);
        return ip;
    }
    
    /**
     * 
     * this method process the clients request and add status to status box and send necessary data  
     * 
     * @param request the request the the client asked for
     * @throws IOException if any
     * @throws ClassNotFoundException if any 
     */
    public static void processRequest(Request request) throws IOException, ClassNotFoundException {
        String clintIp = getClientIP();
      //  getIpAdress();
        try {
            if (request == Request.LOGIN) {
                String name = (String) input.readObject();
                String reg = (String) input.readObject();
                String pass = (String) input.readObject();

                boolean flag = QuizStatus.setStudentPresent(reg, pass);
                output.writeObject(flag);
                if (flag == true) {
                    QuizStatus.addStatus(new Date() + " : " + "Registration No : " + reg + " loged in with IP : " + clintIp);
                    
                   // QuizStatus.saveStudentIP(reg, clintIp);
                    
                    
                } else {
                    QuizStatus.addStatus(new Date() + " : " + "Registration No : " + reg + " trying to login with wrong password ");
                }

            } else if (request == Request.LOGOUT) {

            } else if (request == Request.START_TIME) {
                output.writeObject(CurrentQuiz.curQuiz.getStartTime());
            } else if (request == Request.FINISH_TIME) {
                output.writeObject((CurrentQuiz.curQuiz.getStartTime().getTime() + CurrentQuiz.curQuiz.getDuration() * 60000));
            } else if (request == Request.DURATION) {
                output.writeObject(CurrentQuiz.curQuiz.getDuration());
            } else if (request == Request.SUBMIT_CURRENT_STATUS) {

                String reg = (String) input.readObject();
                StudentAnswer ans = (StudentAnswer) input.readObject();

                boolean flag = QuizStatus.update_current_status(reg, ans);
                if (flag == true) {
                    QuizStatus.addStatus(new Date() + " : Registration No: " + reg + " has submited his/her Current status");
                }
                output.writeObject(flag);

            } else if (request == Request.SUBMIT_FULL_STATUS_AND_LOGOUT) {
                String reg = (String) input.readObject();
                StudentAnswer ans = (StudentAnswer) input.readObject();
                boolean flag = QuizStatus.update_final_status(reg, ans);
                if (flag == true) {
                    QuizStatus.addStatus(new Date() + " : Registration No: " + reg + " has loged out and submited his/her status");
                }
                output.writeObject(flag);

            } else if (request == Request.QUESTION) {
                String reg = (String) input.readObject();
                System.out.println(reg);
                QuizQuestionPaper qs = CurrentQuiz.curQuiz.getQuizQuestion();

                qs.setStudentAnswer((StudentAnswer) CurrentQuiz.allStudentAnsSheet.get(reg));

                output.writeObject(qs);
                output.flush();
                QuizStatus.addStatus(new Date() + " : Question sent to " + clintIp);
            }
            output.flush();

        } catch (Exception e) {

            System.err.println("process request : " + request + " " + e);
        }

    }
}
