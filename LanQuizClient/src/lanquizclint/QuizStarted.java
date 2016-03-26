/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanquizclint;

import NeededClass.FillInTheBlanks;
import NeededClass.McqQuestion;
import NeededClass.QuizQuestionPaper;
import NeededClass.TrueFalse;
import com.sun.javafx.animation.TickCalculation;
import com.sun.jmx.snmp.SnmpDataTypeEnums;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.omg.CORBA.Current;

/**
 * this the  class where the quiz is done by the user.
 * he receive the question , reads the questions and answers the question
 * @author Tahmid
 */
public class QuizStarted extends javax.swing.JFrame {

    /**
     * Creates new form QuizStarted
     */
    Timer timer; /// timer for various work , like updating the time label , sending status , getting update about time table
    long startTime, endTime; /// start and end time of the quiz
    LoginFrame parRef; /// parent form ref.

    boolean waitingFlag; /// flag for waiting , 
    boolean quizStartedFlag; /// flag for starting quiz

    /**
     *
     * @param start start time of the quiz
     * @param end end time of the quiz
     *
     * this is the constructor of this class it set the start and end time of
     * the quiz all last it calls a method that initialize the gui
     */
    public QuizStarted(long start, long end) {
        initComponents();
        this.SetToFullFocus();
        this.startTime = start;
        this.endTime = end;
        timer = new Timer();
        waitingFlag = true;
        quizStartedFlag = true;
        init();
    }
    /**
     * set the quiz GUI to full screen 
     */
    private void SetToFullFocus() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setExtendedState(QuizStarted.MAXIMIZED_BOTH);
        this.setSize(screenSize);
        this.setFocusableWindowState(true);
    }

    /**
     * this method initialize the timer for various timertask  and calls the set
     * mark distribution method
     */
    public void init() {
        studentNameLabel.setText("Student name : " + ServerConnection.userName); /// set the student name label
        studentRegNoLabel.setText("Reg No : " + ServerConnection.regNo); /// set the student registration lebel
        exitButton.setVisible(false); /// unvisivle the exit without logout button
        CurrentQuiz.initialDetail(); /// this call  is for initialize the currect ans, wrong ans and not answered variable
        this.setTimeLabelTasker(); /// this call sets the time label tasker . this tasker shows user the remaining time of the quiz
        this.setTimeUpdateTasker(); /// this call set the local time update tasker . local means it just check the time in CurrentQuiz class
        this.setCurrentStatusTasker(); /// this call set the current status tasker.
        setQuizTimeTableUpdateTasker(); /// this call set a timer task that update the quiz time from the server in every 2 of 3 minute
        //  setMarkDistribution(); /// this call distribute all the marks . and sets all the tabed panel for mcq questionn , true false question , fill in the blanks questions .
        System.out.println("Completed");
    }

    /**
     * this method add all 4 mcq options radio button in a button group
     * mcqRButtonGroup
     */
    public void mcqPanelReady() {
        mcqQuestionBodyText.setEditable(false);
        mcqRButtonGroup.add(optionOneRButton);
        mcqRButtonGroup.add(optionTwoRButton);
        mcqRButtonGroup.add(optionThreeRButton);
        mcqRButtonGroup.add(optionFourRbutton);
    }

    /**
     * this method add true and false radio button in a radio button group
     * trueFalseRButtonGroup
     */
    public void trueFalsePanelReady() {
        trueFalseQuestionBodyText.setEditable(false);
        trueFalseRButtonGroup.add(trueRButton);
        trueFalseRButtonGroup.add(falseRButton);
    }
    /**
     * this method ready the fill in the blanks panel
     */
    public void fillInTheBlanksPanelReady() {
        fillInTheBlanksQuestionBodyText.setEditable(false);
    }

    /**
     * @param indx is the index of the current mcq question this method takes a
     * parameter indx and set the mcq panel with mcqQuestion no indx;
     */
    public void setMcqQuestionDisplay(int indx) {
        Vector<McqQuestion> question = CurrentQuiz.question.getMcqQuestion();

        if (indx >= question.size()) { /// if indx is greatter then or equal to the size of the mcq questioon , then it means that user have answered all the mcq question
            mcqTopPanel.setVisible(false); /// unvisible the mcq top panal
            noMcqLabel.setText("You have answerd all the MCQ questions :)"); /// setting the no mcq label to tell the user that he/she have answered all the mcq question
          //  System.err.println("index = " + indx + " mcq question size : " + question.size());
            /// disable all the option radio button 
            optionFourRbutton.setEnabled(false);
            optionOneRButton.setEnabled(false);
            optionThreeRButton.setEnabled(false);
            optionTwoRButton.setEnabled(false);
            return;
        }

        McqQuestion tmp = question.get(indx);
        String options[] = tmp.getOptions(); ///getting the options of the current mcq question

        mcqQuestionBodyText.setText(tmp.getBody()); /// setting the question body text
        mcqQuestionNameLable.setText(tmp.getName()); /// setting the question name

        ///setting the options of the current mcq question to the options radio buttons  
        optionOneRButton.setText(options[0]);
        optionTwoRButton.setText(options[1]);
        optionThreeRButton.setText(options[2]);
        optionFourRbutton.setText(options[3]);
        mcqRButtonGroup.clearSelection();
        mcqSubmitButton.setEnabled(false); /// enables the submit button 
    }

    /**
     * @param indx this method takes a parameter indx and set the true false
     * panel with mcqQuestion no indx;
     */
    public void setTrueFalseQuestionDisplay(int indx) {
        Vector<TrueFalse> question = CurrentQuiz.question.getTrueFalseQuestion();

        if (indx >= question.size()) { ///very simple logic. this means the user have answered all the true false
            noTrueFalseLabel.setText("You have answerd all the quesrions"); /// set the no true false label to remind the user that he has answered all true false question
            trueFalseTopPanel.setVisible(false); /// making unvisible the top panel of true false
            /// making the radio buttons disaable
            trueRButton.setEnabled(false);
            falseRButton.setEnabled(false);
            return;
        }

        TrueFalse tmp = question.get(indx); /// getting the indx number question ref.
        trueFalseQuestionBodyText.setText(tmp.getBody()); /// set the question body
        trueFalseQuestionNameLabel.setText(tmp.getName()); // set the question name
        trueFalseRButtonGroup.clearSelection();
        return;
    }

    /**
     * @param indx this method takes a parameter indx and set the fill in the
     * blanks panel with mcqQuestion no indx;
     */
    public void setFillInTheBlanksQuestionDisplay(int indx) {
        Vector<FillInTheBlanks> question = CurrentQuiz.question.getFillInTheBlanksQuestion();

        if (indx >= question.size()) { /// this means the user have answered all the fill in the blanks question
            fillInTheBlanksSubmitButton.setEnabled(false); // making the submit button disable 
            fillInTheBlanksTopPanel.setVisible(false); /// set the top fill in the blanks panel unvisible
            noFillInTheBlanksLabel.setText("You have answered all the Questions :)"); /// setting the label to remind the user that he / she has answered all the question
            return;
        }

        FillInTheBlanks tmp = question.get(indx);
        fillInTheBlanksQuestionBodyText.setText(tmp.getBody()); /// sets the current question body
        fillInTheBlanksQuestionNameText.setText(tmp.getName()); /// sets the current question name
        fillInTheBlanksUserAnsText.setText(""); /// empty the user ans text field
    }

    /**
     * this method initialize all 3 section (mcq, trueFalse, fill in the Blanks)
     * , set marks distribution . And check which section of question is active
     * and which section is not active
     */
    public void setMarkDistribution() {
        quizStartedFlag = false;
        totalMarkLabel.setText("Total " + CurrentQuiz.totalQuestions + " Questions and " + CurrentQuiz.totalQuestions + " Marks "); /// set the total marks label . 
        int mcq, fill, truefalse; /// to hold the numer of question in each section

        QuizQuestionPaper question = CurrentQuiz.getCurrentQuizQuestion(); ///getting the ref. of current quiz question paper 

        if (question.getMcqQuestion() != null) { /// if mcq question ref. is not equals to null  , that means there is some mcq question
            mcq = question.getMcqQuestion().size(); /// getting the size of mcq question as it is the masks in this section
            mcqMarkLabel.setText("Total " + mcq + " Questions and " + mcq + " Marks"); /// setting the mcq mark lable with the marks in mcq section
            mcqPanelReady();/// this call put all the four option radio button in  mcq radio button group
            mcqTopPanel.setVisible(true); // visible the mcq top panel
            noMcqLabel.setText(""); /// there are some mcq question
            setMcqQuestionDisplay(CurrentQuiz.mcqQuestionNumber); /// this call display the 1st question on the mcq tabed paner

        } else { /// this means the mcq question section is null 
            mcqTopPanel.setVisible(false); /// making the mcq top panel unvisible . beacuse there is no mcq question today
            noMcqLabel.setText("No MCQ Question Today . Check Other Sections"); /// setting the no mcq label . 
            mcqMarkLabel.setText("0 Quistion 0 Mark");
        }

        if (question.getFillInTheBlanksQuestion() != null) { /// if fill in the blanks question ref. is null then there is some fill in the blanks question
            fill = question.getFillInTheBlanksQuestion().size(); /// getting the size of the fill in the blanks question size as it is the marks in fill in the blanks section
            fillInTheBlanksMarksLabel.setText("Total " + fill + " Questions and " + fill + " Marks"); /// sets the fill in the blanks marks label
            fillInTheBlanksPanelReady();
            fillInTheBlanksTopPanel.setVisible(true); 
            noFillInTheBlanksLabel.setText(""); /// there are some fill in the flanks question
            setFillInTheBlanksQuestionDisplay(CurrentQuiz.fillInTheBlanksQuestionNumber); /// this call set the 1st fill in the blanks question in the fill in the blanks tabed panel

        } else { /// this means there is no fill in the blanks today 
            noFillInTheBlanksLabel.setText("No Fill In The Blanks Today . Check Other Sections"); /// sets the no fill in the  blanks label
            fillInTheBlanksTopPanel.setVisible(false); /// making fill in the blanks top panel unvisible
            fillInTheBlanksMarksLabel.setText("0 Quistion 0 Mark");
        }
        if (question.getTrueFalseQuestion() != null) { /// if the treu false question res. is not equals to null then it means there is some true false question
            truefalse = question.getTrueFalseQuestion().size(); /// getting the size of the true false question as it is the mark in this section 
            trueFalseMarkLabel.setText("Total " + truefalse + " Questions and " + truefalse + " Marks"); /// setting the true false mark label  
            trueFalsePanelReady(); /// this call puts true and false radio button in the true false button group
            trueFalseTopPanel.setVisible(true);
            noTrueFalseLabel.setText(""); /// there are some true false question
            setTrueFalseQuestionDisplay(CurrentQuiz.trueFalseQuestionNumber); /// this call sets the true false question panel with the 1st question

        } else { /// else means there is no true false question today
            trueFalseTopPanel.setVisible(false); /// making the true false top panel unvisible
            noTrueFalseLabel.setText("No True False Question Today . Check Other Sections"); /// setting the no true false question label
            trueFalseMarkLabel.setText("0 Quistion 0 Mark");
        }
    }

    /**
     * this method set all the label to wait until the quiz starts.
     */
    public void waitingForStart() {
        waitingFlag = false;
        mcqTopPanel.setVisible(false);
        fillInTheBlanksTopPanel.setVisible(false);
        trueFalseTopPanel.setVisible(false);
        noMcqLabel.setText("Please wait ...");
        noFillInTheBlanksLabel.setText("Please wait ...");
        noTrueFalseLabel.setText("Please wait");
    }
    
    /**
     * set a timertaks to send the current status in every 1 minute
     */
    public void setCurrentStatusTasker() {
        TimerTask tt3 = new TimerTask() {
            @Override
            public void run() {
                sendCurrentStatus();
            }
        };

        timer.scheduleAtFixedRate(tt3, 0, 60 * 1000);///in every 1 minute
    }

    /**
     * This method sets a timertask that check any update in the local start and
     * end time
     *
     */
    public void setTimeUpdateTasker() {

        TimerTask tt2 = new TimerTask() {
            @Override
            public void run() {
                getTimeUpdate(); /// this method check the local start and end time update 
            }
        };
        timer.scheduleAtFixedRate(tt2, 0, 6100); //in every 1.1 minute
    }

    /**
     * This method set a timerTask that update the remaining time label
     */
    public void setTimeLabelTasker() {

        TimerTask tt1 = new TimerTask() {
            @Override
            public void run() {
                setTimeLabel(); /// this call set the remaining time label
            }
        };
        timer.scheduleAtFixedRate(tt1, 0, 200); // in every 200 m. second
    }

    /**
     * this method set a TimeTasker for checking any update from server about
     * Time Table of quiz
     */
    public void setQuizTimeTableUpdateTasker() {

        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                CurrentQuiz.updateTimeTable(); /// this call ask the server for any update of time in current quiz
            }
        };
        timer.scheduleAtFixedRate(tt, 0, 1000 * 1 * 60); /// in every 1 minute
    }

    /**
     * This method control the remainingTime label;
     */
    public void setTimeLabel() {
        long now = System.currentTimeMillis();
        if (endTime < now) { /// this means quiz time is over

            finishTheQuiz();

        } else if (now >= startTime) { /// else means quiz is still running
            if (quizStartedFlag) {
                CurrentQuiz.quizStartedFlag = true;
                setMarkDistribution();
            }
            long remain = endTime - now;
            remain /= 1000;
            long min = remain / 60;
            long sec = remain % 60;
            remainingTimeLabel1.setText("Time Remain : ");
            remainingTimeLabel2.setText(min + " Minutes " + sec + " Seconds"); /// set the remaining time label
        } else { /// wait untill the quiz statrs 
            if (waitingFlag) {
                waitingForStart();
            }
            remainingTimeLabel1.setText("Quiz starts in : ");

            long remain = startTime - now;
            remain /= 1000;
            long min = remain / 60;
            long sec = remain % 60;
            remainingTimeLabel2.setText(min + " minutes " + sec + " seconds");
        }
    }
    
    /**
     * this method finish the quiz if the time is over
     */
    public void finishTheQuiz() {

        mcqTopPanel.setVisible(false);
        fillInTheBlanksTopPanel.setVisible(false);
        trueFalseTopPanel.setVisible(false);
        noMcqLabel.setText("Quiz Finished");
        noFillInTheBlanksLabel.setText("Quiz Finished");
        noTrueFalseLabel.setText("Quiz Finished");
        logout();
        exitButton.setVisible(true);
        timer.cancel();

    }

    /**
     * This method sends the current status to the server
     */
    public void sendCurrentStatus() {
        try{
        ServerConnection.submit_current_status(CurrentQuiz.question.getStudentAnswer());
        }catch(Exception e){
            System.err.println("There is an exception : "+e);
        }
    }

    /**
     * this method send the final status to server
     *
     * @return true if successful , false if unsuccessful
     */
    public boolean sendFinalStatus() {
        return ServerConnection.submit_final_status(CurrentQuiz.question.getStudentAnswer());
    }

    /**
     * This method update the current quiz start and end time from local
     * question
     */
    public void getTimeUpdate() {
        startTime = CurrentQuiz.getCurrentQuizQuestion().getStartTime().getTime();
        int duration = CurrentQuiz.getCurrentQuizQuestion().getDuration();
        endTime = startTime + duration * 60000;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mcqRButtonGroup = new javax.swing.ButtonGroup();
        trueFalseRButtonGroup = new javax.swing.ButtonGroup();
        remainingTimeLabel1 = new javax.swing.JLabel();
        remainingTimeLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        mcqMarkLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fillInTheBlanksMarksLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        trueFalseMarkLabel = new javax.swing.JLabel();
        totalMarkLabel = new javax.swing.JLabel();
        mainQuizPanel = new javax.swing.JTabbedPane();
        mcqBottumPanel = new javax.swing.JLayeredPane();
        mcqTopPanel = new javax.swing.JLayeredPane();
        mcqQuestionNameLable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mcqQuestionBodyText = new javax.swing.JTextArea();
        optionOneRButton = new javax.swing.JRadioButton();
        optionTwoRButton = new javax.swing.JRadioButton();
        optionThreeRButton = new javax.swing.JRadioButton();
        optionFourRbutton = new javax.swing.JRadioButton();
        mcqSubmitButton = new javax.swing.JButton();
        noMcqLabel = new javax.swing.JLabel();
        trueFalseButtomPanel = new javax.swing.JLayeredPane();
        trueFalseTopPanel = new javax.swing.JLayeredPane();
        trueFalseQuestionNameLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        trueFalseQuestionBodyText = new javax.swing.JTextArea();
        trueRButton = new javax.swing.JRadioButton();
        falseRButton = new javax.swing.JRadioButton();
        trueFalseSubmitButton = new javax.swing.JButton();
        noTrueFalseLabel = new javax.swing.JLabel();
        fillInTheBlanksButtomPanel = new javax.swing.JLayeredPane();
        fillInTheBlanksTopPanel = new javax.swing.JLayeredPane();
        fillInTheBlanksQuestionNameText = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        fillInTheBlanksQuestionBodyText = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        fillInTheBlanksUserAnsText = new javax.swing.JTextField();
        fillInTheBlanksSubmitButton = new javax.swing.JButton();
        noFillInTheBlanksLabel = new javax.swing.JLabel();
        studentNameLabel = new javax.swing.JLabel();
        studentRegNoLabel = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Quiz");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        remainingTimeLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        remainingTimeLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabel1.setText("Time Remaining ");

        remainingTimeLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        remainingTimeLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabel2.setText("Remaining Time");

        jButton3.setText("LOGOUT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("MCQ Questions   : ");

        mcqMarkLabel.setText("Please wait until the quiz starts");

        jLabel4.setText("Fill In The Blanks :");

        fillInTheBlanksMarksLabel.setText("Please wait until the quiz starts");

        jLabel6.setText("True False  :");

        trueFalseMarkLabel.setText("Please wait until the quiz starts");

        totalMarkLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        totalMarkLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalMarkLabel.setText("Please wait until the quiz starts");

        mcqTopPanel.setBackground(new java.awt.Color(0, 255, 255));
        mcqTopPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        mcqQuestionNameLable.setText("Question ");

        mcqQuestionBodyText.setColumns(20);
        mcqQuestionBodyText.setRows(5);
        jScrollPane1.setViewportView(mcqQuestionBodyText);

        optionOneRButton.setText("Option 1: ");
        optionOneRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionOneRButtonActionPerformed(evt);
            }
        });

        optionTwoRButton.setText("Option 2:");
        optionTwoRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionTwoRButtonActionPerformed(evt);
            }
        });

        optionThreeRButton.setText("Option 3:");
        optionThreeRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionThreeRButtonActionPerformed(evt);
            }
        });

        optionFourRbutton.setText("Option 4:");
        optionFourRbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionFourRbuttonActionPerformed(evt);
            }
        });

        mcqSubmitButton.setText("Submit");
        mcqSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mcqSubmitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mcqTopPanelLayout = new javax.swing.GroupLayout(mcqTopPanel);
        mcqTopPanel.setLayout(mcqTopPanelLayout);
        mcqTopPanelLayout.setHorizontalGroup(
            mcqTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mcqTopPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(mcqTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mcqTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(optionOneRButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                        .addComponent(optionTwoRButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(optionThreeRButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(optionFourRbutton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(mcqSubmitButton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mcqQuestionNameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(377, Short.MAX_VALUE))
        );
        mcqTopPanelLayout.setVerticalGroup(
            mcqTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mcqTopPanelLayout.createSequentialGroup()
                .addComponent(mcqQuestionNameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(optionOneRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionTwoRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionThreeRButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionFourRbutton)
                .addGap(18, 18, 18)
                .addComponent(mcqSubmitButton)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        mcqTopPanel.setLayer(mcqQuestionNameLable, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(optionOneRButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(optionTwoRButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(optionThreeRButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(optionFourRbutton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqTopPanel.setLayer(mcqSubmitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        noMcqLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        noMcqLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noMcqLabel.setText("No Mcq Question Today");

        javax.swing.GroupLayout mcqBottumPanelLayout = new javax.swing.GroupLayout(mcqBottumPanel);
        mcqBottumPanel.setLayout(mcqBottumPanelLayout);
        mcqBottumPanelLayout.setHorizontalGroup(
            mcqBottumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mcqTopPanel)
            .addGroup(mcqBottumPanelLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(noMcqLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mcqBottumPanelLayout.setVerticalGroup(
            mcqBottumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mcqBottumPanelLayout.createSequentialGroup()
                .addComponent(noMcqLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(mcqTopPanel)
                .addContainerGap())
        );
        mcqBottumPanel.setLayer(mcqTopPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        mcqBottumPanel.setLayer(noMcqLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mainQuizPanel.addTab("MCQ Questoion", mcqBottumPanel);

        trueFalseTopPanel.setBackground(new java.awt.Color(255, 0, 51));
        trueFalseTopPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 51)));
        trueFalseTopPanel.setForeground(new java.awt.Color(255, 0, 102));

        trueFalseQuestionNameLabel.setText("Question No");

        trueFalseQuestionBodyText.setColumns(20);
        trueFalseQuestionBodyText.setRows(5);
        jScrollPane2.setViewportView(trueFalseQuestionBodyText);

        trueRButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        trueRButton.setForeground(new java.awt.Color(0, 102, 0));
        trueRButton.setText("TRUE");
        trueRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trueRButtonActionPerformed(evt);
            }
        });

        falseRButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        falseRButton.setForeground(new java.awt.Color(255, 0, 0));
        falseRButton.setText("FALSE");
        falseRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                falseRButtonActionPerformed(evt);
            }
        });

        trueFalseSubmitButton.setText("Submit");
        trueFalseSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trueFalseSubmitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout trueFalseTopPanelLayout = new javax.swing.GroupLayout(trueFalseTopPanel);
        trueFalseTopPanel.setLayout(trueFalseTopPanelLayout);
        trueFalseTopPanelLayout.setHorizontalGroup(
            trueFalseTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trueFalseTopPanelLayout.createSequentialGroup()
                .addGroup(trueFalseTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(trueFalseTopPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(trueFalseTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trueFalseQuestionNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(trueFalseTopPanelLayout.createSequentialGroup()
                                .addComponent(trueRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(falseRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(trueFalseTopPanelLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(trueFalseSubmitButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        trueFalseTopPanelLayout.setVerticalGroup(
            trueFalseTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trueFalseTopPanelLayout.createSequentialGroup()
                .addComponent(trueFalseQuestionNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(trueFalseTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trueRButton)
                    .addComponent(falseRButton))
                .addGap(18, 18, 18)
                .addComponent(trueFalseSubmitButton)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        trueFalseTopPanel.setLayer(trueFalseQuestionNameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        trueFalseTopPanel.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        trueFalseTopPanel.setLayer(trueRButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        trueFalseTopPanel.setLayer(falseRButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        trueFalseTopPanel.setLayer(trueFalseSubmitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        noTrueFalseLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        noTrueFalseLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noTrueFalseLabel.setText("No True False Question Today");

        javax.swing.GroupLayout trueFalseButtomPanelLayout = new javax.swing.GroupLayout(trueFalseButtomPanel);
        trueFalseButtomPanel.setLayout(trueFalseButtomPanelLayout);
        trueFalseButtomPanelLayout.setHorizontalGroup(
            trueFalseButtomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trueFalseButtomPanelLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(noTrueFalseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(281, Short.MAX_VALUE))
            .addComponent(trueFalseTopPanel)
        );
        trueFalseButtomPanelLayout.setVerticalGroup(
            trueFalseButtomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(trueFalseButtomPanelLayout.createSequentialGroup()
                .addComponent(noTrueFalseLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(trueFalseTopPanel)
                .addContainerGap())
        );
        trueFalseButtomPanel.setLayer(trueFalseTopPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        trueFalseButtomPanel.setLayer(noTrueFalseLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mainQuizPanel.addTab("TrueFalse", trueFalseButtomPanel);

        fillInTheBlanksTopPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 0)));
        fillInTheBlanksTopPanel.setForeground(new java.awt.Color(0, 0, 204));

        fillInTheBlanksQuestionNameText.setText("Question No :");

        fillInTheBlanksQuestionBodyText.setColumns(20);
        fillInTheBlanksQuestionBodyText.setRows(5);
        jScrollPane3.setViewportView(fillInTheBlanksQuestionBodyText);

        jLabel5.setText("Answere : ");

        fillInTheBlanksSubmitButton.setText("Submit");
        fillInTheBlanksSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillInTheBlanksSubmitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fillInTheBlanksTopPanelLayout = new javax.swing.GroupLayout(fillInTheBlanksTopPanel);
        fillInTheBlanksTopPanel.setLayout(fillInTheBlanksTopPanelLayout);
        fillInTheBlanksTopPanelLayout.setHorizontalGroup(
            fillInTheBlanksTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fillInTheBlanksTopPanelLayout.createSequentialGroup()
                .addGroup(fillInTheBlanksTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fillInTheBlanksTopPanelLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fillInTheBlanksUserAnsText, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fillInTheBlanksTopPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(fillInTheBlanksTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fillInTheBlanksQuestionNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(fillInTheBlanksTopPanelLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(fillInTheBlanksSubmitButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fillInTheBlanksTopPanelLayout.setVerticalGroup(
            fillInTheBlanksTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fillInTheBlanksTopPanelLayout.createSequentialGroup()
                .addComponent(fillInTheBlanksQuestionNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(fillInTheBlanksTopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fillInTheBlanksUserAnsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(fillInTheBlanksSubmitButton)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        fillInTheBlanksTopPanel.setLayer(fillInTheBlanksQuestionNameText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fillInTheBlanksTopPanel.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fillInTheBlanksTopPanel.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fillInTheBlanksTopPanel.setLayer(fillInTheBlanksUserAnsText, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fillInTheBlanksTopPanel.setLayer(fillInTheBlanksSubmitButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        noFillInTheBlanksLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        noFillInTheBlanksLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noFillInTheBlanksLabel.setText("No Fill In the Blanks Question Today");

        javax.swing.GroupLayout fillInTheBlanksButtomPanelLayout = new javax.swing.GroupLayout(fillInTheBlanksButtomPanel);
        fillInTheBlanksButtomPanel.setLayout(fillInTheBlanksButtomPanelLayout);
        fillInTheBlanksButtomPanelLayout.setHorizontalGroup(
            fillInTheBlanksButtomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fillInTheBlanksButtomPanelLayout.createSequentialGroup()
                .addComponent(fillInTheBlanksTopPanel)
                .addContainerGap())
            .addGroup(fillInTheBlanksButtomPanelLayout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(noFillInTheBlanksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );
        fillInTheBlanksButtomPanelLayout.setVerticalGroup(
            fillInTheBlanksButtomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fillInTheBlanksButtomPanelLayout.createSequentialGroup()
                .addComponent(noFillInTheBlanksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(fillInTheBlanksTopPanel)
                .addContainerGap())
        );
        fillInTheBlanksButtomPanel.setLayer(fillInTheBlanksTopPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        fillInTheBlanksButtomPanel.setLayer(noFillInTheBlanksLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mainQuizPanel.addTab("Fill In The Blanks", fillInTheBlanksButtomPanel);

        studentNameLabel.setText("Student name: ");

        studentRegNoLabel.setText("Reg No :");

        exitButton.setText("Exit without submitting final status");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(53, 53, 53))
            .addComponent(mainQuizPanel, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(remainingTimeLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remainingTimeLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(studentNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                            .addComponent(studentRegNoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalMarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fillInTheBlanksMarksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trueFalseMarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mcqMarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(totalMarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mcqMarkLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fillInTheBlanksMarksLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addComponent(trueFalseMarkLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(remainingTimeLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remainingTimeLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentRegNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainQuizPanel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(exitButton))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * this method try to log out from current quiz
     */
    public void logout() {
        boolean ret = sendFinalStatus(); ///try to send the final status 
        if (ret == false) {
            ret = sendFinalStatus();
        }
        if (ret == false) { /// this means error while sending final status 
            JOptionPane.showMessageDialog(this, "Error while logout , Please try again");
            exitButton.setVisible(true);
        } else {
            /// else successfully submitted the final status
            timer.cancel(); ///cancel the timer from all tasks
            JOptionPane.showMessageDialog(this, "Logout successfully");
            parRef.setVisible(true);
            this.dispose();
        }
    }

    /**
     *
     * @param evt window closing event
     */

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int flag = JOptionPane.showConfirmDialog(this, "Are you sure to logout from this Quiz"); /// making sure that the user want to close the window
        
        if (flag == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Please wait... Submitting your answers");
            boolean ret = sendFinalStatus(); /// try to submit the final status  

            if (ret == true) { /// successfully submitted the final status , now the windows can close
                JOptionPane.showMessageDialog(this, "Your final status has been successfully submited");
                timer.cancel();
                this.dispose();
                parRef.setVisible(true);

            } else { /// tells the user about the error
                JOptionPane.showMessageDialog(this, "Error submiting your final status");
            }
        } else {
            return;
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     *
     * @param evt logout button event
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        logout(); /// this call try to log out from the current quiz
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     *
     * @param evt mcq question submit button event
     */
    private void mcqSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mcqSubmitButtonActionPerformed
        // TODO add your handling code here:
        mcqSubmitButton.setEnabled(false);
        takeMcqAns();
        mcqRButtonGroup.clearSelection();
        CurrentQuiz.mcqQuestionNumber++;
        setMcqQuestionDisplay(CurrentQuiz.mcqQuestionNumber);
    }//GEN-LAST:event_mcqSubmitButtonActionPerformed

    /**
     * when user press the submit button in mcq section then this function take
     * the user's answer and match with the correct answer.
     *
     */
    public void takeMcqAns() {
        McqQuestion ques = CurrentQuiz.question.getMcqQuestion().get(CurrentQuiz.mcqQuestionNumber);
        int ans = Integer.parseInt(ques.getCorrectAns()); /// get the correct ans and convert it into interger
        boolean flag = false; /// this variable is true if the user has currectly answered this question , false otherwise

        /*
         * checking the answer of the user and saving it in the student answere field
         */
        if (mcqRButtonGroup.isSelected(optionOneRButton.getModel()) == true) {
            if (ans == 1) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getMcqAns().add("1");

        } else if (mcqRButtonGroup.isSelected(optionTwoRButton.getModel()) == true) {
            if (ans == 2) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getMcqAns().add("2");
        } else if (mcqRButtonGroup.isSelected(optionThreeRButton.getModel()) == true) {
            if (ans == 3) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getMcqAns().add("3");
        } else if (mcqRButtonGroup.isSelected(optionFourRbutton.getModel()) == true) {
            if (ans == 4) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getMcqAns().add("4");
        }

        if (flag == true) { /// user has correctly answered this question
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().correct_ans++;
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().not_answered--;

        } else { /// user has given a wrong answer to current question
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().wrong_ans++;
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().not_answered--;
        }
    }

    private void optionOneRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionOneRButtonActionPerformed
        // TODO add your handling code here:
        mcqSubmitButton.setEnabled(true);
    }//GEN-LAST:event_optionOneRButtonActionPerformed

    private void optionTwoRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionTwoRButtonActionPerformed
        // TODO add your handling code here:
        mcqSubmitButton.setEnabled(true);
    }//GEN-LAST:event_optionTwoRButtonActionPerformed

    private void optionThreeRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionThreeRButtonActionPerformed
        // TODO add your handling code here:
        mcqSubmitButton.setEnabled(true);
    }//GEN-LAST:event_optionThreeRButtonActionPerformed

    private void optionFourRbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionFourRbuttonActionPerformed
        // TODO add your handling code here:
        mcqSubmitButton.setEnabled(true);
    }//GEN-LAST:event_optionFourRbuttonActionPerformed
    /**
     *
     * @param evt true false submit button event
     */
    private void trueFalseSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trueFalseSubmitButtonActionPerformed
        // TODO add your handling code here:
        trueFalseSubmitButton.setEnabled(false); /// disable the submit button
        takeTrueFalseAns(); /// this call check and stor the answer given by the user 
        trueFalseRButtonGroup.clearSelection(); /// clear the prv selection of the true false radio button
        CurrentQuiz.trueFalseQuestionNumber++; /// increassing question numner
        setTrueFalseQuestionDisplay(CurrentQuiz.trueFalseQuestionNumber); /// this call update the true false panel with the new true false question
    }//GEN-LAST:event_trueFalseSubmitButtonActionPerformed

    private void trueRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trueRButtonActionPerformed
        // TODO add your handling code here:
        trueFalseSubmitButton.setEnabled(true);
    }//GEN-LAST:event_trueRButtonActionPerformed

    private void falseRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_falseRButtonActionPerformed
        // TODO add your handling code here:
        trueFalseSubmitButton.setEnabled(true);
    }//GEN-LAST:event_falseRButtonActionPerformed

    /**
     *
     * @param evt fill in the blanks submit button event
     */
    private void fillInTheBlanksSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillInTheBlanksSubmitButtonActionPerformed
        // TODO add your handling code here:
        if (fillInTheBlanksUserAnsText.getText().trim().length() == 0) { /// user have enter nothing in the ans text field
            return;
        }

        takeFillInTheBlanksAns(); /// this call check and store user's answere of the current question
        fillInTheBlanksUserAnsText.setText(""); /// emptying the ans text field of the fill in the blanks
        CurrentQuiz.fillInTheBlanksQuestionNumber++; /// increassing the question number 
        setFillInTheBlanksQuestionDisplay(CurrentQuiz.fillInTheBlanksQuestionNumber); /// this call set the fill in the blanks question panel with new questtion
    }//GEN-LAST:event_fillInTheBlanksSubmitButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // TODO add your handling code here:
        timer.cancel();
        this.dispose();
        parRef.setVisible(true);
    }//GEN-LAST:event_exitButtonActionPerformed

    /**
     * when user press the submit button in fill in the gap section then this
     * function take the user's answer and match with the correct answer.
     */
    public void takeFillInTheBlanksAns() {
        FillInTheBlanks ques = CurrentQuiz.question.getFillInTheBlanksQuestion().get(CurrentQuiz.fillInTheBlanksQuestionNumber);
        String ca = ques.getCorrectAns();

        CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getFillInTheBlanksAns().add(fillInTheBlanksUserAnsText.getText().trim().toUpperCase());/// stor the user answer in the student ans field

        /*
         checking user's ans.
         */
        if (ca.trim().toUpperCase().equals(fillInTheBlanksUserAnsText.getText().trim().toUpperCase()) == true) { /// user has correctly answered this question
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().correct_ans++;
        } else { /// user has giver a worng ans
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().wrong_ans++;
        }
    }

    /**
     * when user press the submit button in true false section then this
     * function take the user's answer and match with the correct answer.
     */
    public void takeTrueFalseAns() {
        TrueFalse ques = CurrentQuiz.question.getTrueFalseQuestion().get(CurrentQuiz.trueFalseQuestionNumber);
        boolean flag = false;
        String ca = ques.getCorrectAns().trim().toUpperCase();

        /*
         checking user's answer , and storing it in the student answer field
         */
        if (trueFalseRButtonGroup.isSelected(trueRButton.getModel())) {
            if (ca.equals("TRUE")) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getTrueFalseAns().add("TRUE");
        } else if (trueFalseRButtonGroup.isSelected(falseRButton.getModel())) {
            if (ca.equals("FALSE")) {
                flag = true;
            }
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().getTrueFalseAns().add("FALSE");
        }

        if (flag == true) { /// user has correctly answered this question
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().correct_ans++;

        } else { /// user has given a wrong answer to current question
            CurrentQuiz.getCurrentQuizQuestion().getStudentAnswer().wrong_ans++;
        }
    }

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JRadioButton falseRButton;
    private javax.swing.JLayeredPane fillInTheBlanksButtomPanel;
    private javax.swing.JLabel fillInTheBlanksMarksLabel;
    private javax.swing.JTextArea fillInTheBlanksQuestionBodyText;
    private javax.swing.JLabel fillInTheBlanksQuestionNameText;
    private javax.swing.JButton fillInTheBlanksSubmitButton;
    private javax.swing.JLayeredPane fillInTheBlanksTopPanel;
    private javax.swing.JTextField fillInTheBlanksUserAnsText;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane mainQuizPanel;
    private javax.swing.JLayeredPane mcqBottumPanel;
    private javax.swing.JLabel mcqMarkLabel;
    private javax.swing.JTextArea mcqQuestionBodyText;
    private javax.swing.JLabel mcqQuestionNameLable;
    private javax.swing.ButtonGroup mcqRButtonGroup;
    private javax.swing.JButton mcqSubmitButton;
    private javax.swing.JLayeredPane mcqTopPanel;
    private javax.swing.JLabel noFillInTheBlanksLabel;
    private javax.swing.JLabel noMcqLabel;
    private javax.swing.JLabel noTrueFalseLabel;
    private javax.swing.JRadioButton optionFourRbutton;
    private javax.swing.JRadioButton optionOneRButton;
    private javax.swing.JRadioButton optionThreeRButton;
    private javax.swing.JRadioButton optionTwoRButton;
    private javax.swing.JLabel remainingTimeLabel1;
    private javax.swing.JLabel remainingTimeLabel2;
    private javax.swing.JLabel studentNameLabel;
    private javax.swing.JLabel studentRegNoLabel;
    private javax.swing.JLabel totalMarkLabel;
    private javax.swing.JLayeredPane trueFalseButtomPanel;
    private javax.swing.JLabel trueFalseMarkLabel;
    private javax.swing.JTextArea trueFalseQuestionBodyText;
    private javax.swing.JLabel trueFalseQuestionNameLabel;
    private javax.swing.ButtonGroup trueFalseRButtonGroup;
    private javax.swing.JButton trueFalseSubmitButton;
    private javax.swing.JLayeredPane trueFalseTopPanel;
    private javax.swing.JRadioButton trueRButton;
    // End of variables declaration//GEN-END:variables
}
