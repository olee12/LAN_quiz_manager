/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LanQuizServer;

import NeededClass.FillInTheBlanks;
import NeededClass.McqQuestion;
import NeededClass.StudentAnswer;
import NeededClass.TrueFalse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 * this class process the final result and create files for result in csv format
 *
 * @author olee
 */
public class ProcessFinalResult {

    ///C:\Users\olee\Documents
    public static String curDate; ///current date and time in string format
    final public static String COMMA = ",";
    final public static String NEW_LINE = "\n";
    final public static String DEFAULT_DIRECTORY = "C:\\Users\\olee\\Documents"; //default director
    public static String root_path; /// root path for the students final answere sheet

    /**
     * this method makes the root path for the students final answere sheet
     */
    public static void makeRootPath() {
        curDate = getCurrentTimeInStringFormated();
        root_path = DEFAULT_DIRECTORY + "\\" + CurrentQuiz.curQuiz.getTitel() + curDate + "Result";

    }

    /**
     * this method process all the final result
     */
    public static void processFinalStatus() {

        makeRootPath();
        writeFinalResult();
        writeFinalAnswerSheet();
        processFinalStatusBoxData();

    }

    /**
     * save the data on the quiz status box
     */
    public static void processFinalStatusBoxData() {
        try {
            String path = root_path + "\\Data on Quiz status box" + curDate;
            File file = new File(path);
            file.mkdirs();

            String str = QuizStatus.statusBox.getText();
            path = path + "\\Status box.txt";
            file = new File(path);
            file.createNewFile();

            Formatter formatter = null;

            formatter = new Formatter(file);
            formatter.format("%s\n", str);
            formatter.flush();

        } catch (IOException ex) {
            Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    /**
     * this method write the students answer sheets
     */
    public static void writeFinalAnswerSheet() {
        try {
            String final_ans_path_root = root_path + "\\Final Result Answer Sheet" + curDate;
            Set studentAnsSet = CurrentQuiz.allStudentAnsSheet.entrySet();
            Iterator it = studentAnsSet.iterator();

            while (it.hasNext()) {
                Map.Entry me = (Map.Entry) it.next();
                String regNo = (String) me.getKey();
                StudentAnswer ans = (StudentAnswer) me.getValue();

                String path = final_ans_path_root + "\\" + regNo;

                writeMcqAnsSheet(path, ans.getMcqAns());
                writeFillInTheBlanksAnsSheet(path, ans.getFillInTheBlanksAns());
                writeTrueFalseAnsSheet(path, ans.getTrueFalseAns());

            }
        } catch (Exception e) {
            QuizStatus.addStatus(new Date() + "There is an exception : " + e + " while writing student ans sheet.");
        }

    }

    /**
     * this method write the mcq answer sheet of one student at a time
     *
     * @param path saving path
     * @param mcqAns mcq answer sheet
     */
    public static void writeMcqAnsSheet(String path, Vector<String> mcqAns) {
        try {
            if (CurrentQuiz.curQuiz.getMcqQuestion() == null) {
                return;
            }
            File file = new File(path);
            file.mkdirs();

            file = new File(path + "\\Mcq answer sheet.csv");

            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            Formatter formatter = null;

            try {
                formatter = new Formatter(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }
            String hader = "Question ID,Question Body,Option 1,Option 2,Option 3,Option 4, Correct answer,Student answer,Status";
            formatter.format("%s\n", hader);
            Vector<McqQuestion> question = CurrentQuiz.curQuiz.getMcqQuestion();
            int size = CurrentQuiz.curQuiz.getMcqQuestion().size();
            String str = null;
            int counter = 0;
            for (int i = 0; i < size; i++) {
                McqQuestion qus = question.get(i);
                String options[] = qus.getOptions();
                str = String.format("%s,%s,%s,%s,%s,%s,%s", qus.getName(), qus.getBody(), options[0], options[1], options[2], options[3], qus.getCorrectAns());
                String status = null;
                if (i < mcqAns.size()) {
                    if (mcqAns.get(i).equals(qus.getCorrectAns())) {
                        counter++;
                        status = "Correct";
                    } else {
                        status = "Wrong";
                    }
                    str += "," + mcqAns.get(i) + "," + status;
                } else {
                    status = "Not Answered";
                    str += "," + status + "," + status;
                }

                formatter.format("%s\n", str);
            }

            formatter.format("%s : %s\n", "Total Correct Answer", counter);
            if (formatter != null) {
                formatter.close();
            }
        } catch (Exception e) {
            QuizStatus.addStatus(new Date() + "There is an exception : " + e + " while writing student ans sheet (mcq).");
        }
    }

    /**
     * this method write the fill in the blanks answer sheet of one student at a
     * time
     *
     * @param path saving path
     * @param fillInTheBlanksAns fill in the blanks answer sheet
     */
    public static void writeFillInTheBlanksAnsSheet(String path, Vector<String> fillInTheBlanksAns) {
        try {
            if (CurrentQuiz.curQuiz.getFillInTheBlanksQuestion() == null) {
                return;
            }

            File file = new File(path);
            file.mkdirs();

            file = new File(path + "\\FillInTheBlanks answer sheet.csv");

            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            Formatter formatter = null;

            try {
                formatter = new Formatter(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            String hader = "Question ID,Question Body,Correct answer,Student answer,Status";
            formatter.format("%s\n", hader);
            Vector<FillInTheBlanks> question = CurrentQuiz.curQuiz.getFillInTheBlanksQuestion();
            int size = question.size();
            String str = null;
            int counter = 0;
            for (int i = 0; i < size; i++) {
                FillInTheBlanks qus = question.get(i);

                str = String.format("%s,%s,%s", qus.getName(), qus.getBody(), qus.getCorrectAns());
                String status = null;
                if (i < fillInTheBlanksAns.size()) {
                    if (fillInTheBlanksAns.get(i).equals(qus.getCorrectAns().trim().toUpperCase())) {
                        counter++;
                        status = "Correct";
                    } else {
                        status = "wrong";
                    }
                    str += "," + fillInTheBlanksAns.get(i) + "," + status;
                } else {
                    status = "Not Answered";
                    str += "," + status + "," + status;
                }

                formatter.format("%s\n", str);
            }

            formatter.format("%s : %s\n", "Total Correct Answer", counter);
            if (formatter != null) {
                formatter.close();
            }
        } catch (Exception e) {
            QuizStatus.addStatus(new Date() + "There is an exception : " + e + " while writing student ans sheet (Fill in the blanks).");
        }
    }

    /**
     * this method write the true false answer sheet of one student at a time
     *
     * @param path saving path
     * @param trueFalseAns true false answer sheet
     */
    public static void writeTrueFalseAnsSheet(String path, Vector<String> trueFalseAns) {
        try {
            if (CurrentQuiz.curQuiz.getTrueFalseQuestion() == null) {
                return;
            }

            File file = new File(path);
            file.mkdirs();

            file = new File(path + "\\TrueFalse answer sheet.csv");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            Formatter formatter = null;

            try {
                formatter = new Formatter(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            String hader = "Question ID,Question Body,Correct answer,Student answer,Status";
            formatter.format("%s\n", hader);
            Vector<TrueFalse> question = CurrentQuiz.curQuiz.getTrueFalseQuestion();
            int size = question.size();
            String str = null;
            int counter = 0;
            for (int i = 0; i < size; i++) {
                TrueFalse qus = question.get(i);

                str = String.format("%s,%s,%s", qus.getName(), qus.getBody(), qus.getCorrectAns());
                String status = null;
                if (i < trueFalseAns.size()) {
                    if (trueFalseAns.get(i).equals(qus.getCorrectAns().trim().toUpperCase())) {
                        counter++;
                        status = "Correct";
                    } else {
                        status = "wrong";
                    }
                    str += "," + trueFalseAns.get(i) + "," + status;
                } else {
                    status = "Not Answered";
                    str += "," + status + "," + status;
                }

                formatter.format("%s\n", str);
            }

            formatter.format("%s : %s\n", "Total Correct Answer", counter);
            if (formatter != null) {
                formatter.close();
            }
        } catch (Exception e) {
            QuizStatus.addStatus(new Date() + "There is an exception : " + e + " while writing student ans sheet (TrueFalse).");
        }
    }

    /**
     * this method write the final result in final result folder
     */
    public static void writeFinalResult() {
        try {
            String final_result_path = root_path + "\\Final Result " + curDate;
            File file = new File(final_result_path);
            file.mkdirs();

            file = new File(final_result_path + "\\Final Result.csv");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            Formatter formatter = null;

            try {
                formatter = new Formatter(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ProcessFinalResult.class.getName()).log(Level.SEVERE, null, ex);
            }

            String header = "Registration Number,Name,Correct Answer,Wrong Answer,Final Marks";

            formatter.format("%s\n", header);
            formatter.flush();
            JTable tabel = QuizStatus.studentTable;
            //{"ID","Name","Registration No","password","Status","Question sent","Login Time","Correct Ans","Wrong Ans","Not Answered","Logout time"};
            for (int i = 0; i < tabel.getRowCount(); i++) {
                formatter.format("%s,%s,%s,%s,%s\n", tabel.getValueAt(i, 2), tabel.getValueAt(i, 1), tabel.getValueAt(i, 6), tabel.getValueAt(i, 7), tabel.getValueAt(i, 6));
            }
            formatter.close();
        } catch (Exception e) {
            QuizStatus.addStatus(new Date() + "There is an exception : " + e + " while writing students Final Result (TrueFalse).");
        }
    }

    /**
     * this method format the current date and return it in string format
     *
     * @return current time in formated string
     */
    public static String getCurrentTimeInStringFormated() {

        Date now = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("[dd.MMMM.yyyy (hh.mm.aa)]");
        return formater.format(now);
    }

}
