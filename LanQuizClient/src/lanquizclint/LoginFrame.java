/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanquizclint;

import javax.swing.JOptionPane;

/**
 *
 * @author Tahmid
 */
public class LoginFrame extends javax.swing.JFrame {

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameLabel = new javax.swing.JLabel();
        regNoLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        serverIpLabel = new javax.swing.JLabel();
        ServerPortLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        regNoTextField = new javax.swing.JTextField();
        serverIpTextField = new javax.swing.JTextField();
        serverPortTextField = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        titelTextLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        nameLabel.setText("Name");

        regNoLabel.setText("Reg No");

        passwordLabel.setText("Password");

        serverIpLabel.setText("Server IP");

        ServerPortLabel.setText("Server Port");

        serverIpTextField.setText("10.100.32.163");
        serverIpTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverIpTextFieldActionPerformed(evt);
            }
        });

        serverPortTextField.setText("12375");

        loginButton.setText("Log In");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        titelTextLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        titelTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titelTextLabel.setText("Quiz Manager ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("To know your password contact your teacher");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(regNoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(serverIpLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ServerPortLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nameTextField)
                                .addComponent(regNoTextField)
                                .addComponent(serverIpTextField)
                                .addComponent(serverPortTextField)
                                .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(titelTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titelTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regNoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regNoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverIpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverIpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ServerPortLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(loginButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * this method try to get question from the server
     * @return true if it gets the question correctly , false if any error
     */
    public boolean getQuestionFromServer(){
        ServerConnection.regNo = regNoTextField.getText().trim(); 
        ServerConnection.userName = nameTextField.getText().trim();
        CurrentQuiz.setQuizQuestion(ServerConnection.getQuestions());
        if(CurrentQuiz.getCurrentQuizQuestion()==null) return false;
        return true;
    }
    /**
     * this method try to log into the quiz using the given data 
     */
    public void tryToLogIn(){
         
         try{
        ServerConnection.serverIp = serverIpTextField.getText().trim(); ///setting the server ip
        ServerConnection.port= Integer.parseInt(serverPortTextField.getText().trim()); /// setting the server port
        ServerConnection.userName = nameTextField.getText().trim(); // setting the user name
        ServerConnection.regNo = regNoTextField.getText().trim(); // setting the registration number
        ServerConnection.pass = new String(passwordField.getPassword()); /// setting the password
         }catch(Exception e){
             JOptionPane.showMessageDialog(this, "Enter data correctly");
             return;
         }
        int flag = ServerConnection.logIn(); /// try to log into the server
        String error = "";
        if(flag==0){
            error = "Cheak your password";
        }
        else if(flag==-1){
            error = "Failed to connect";
        }
        else if(flag==1){
            
            boolean isQuestionReceived = getQuestionFromServer();
            
            if(isQuestionReceived == false){
                JOptionPane.showMessageDialog(this, "Error in getting Question from server . Please Try to log in again");
                return;
            }
            logIn();
            return;
        }
        JOptionPane.showMessageDialog(this, error);
    }
    /**
     * this method log into the current  quiz 
     */
    public void logIn(){
        CurrentQuiz.question.setStudentName(ServerConnection.userName);
        CurrentQuiz.question.setStudentRegNo(ServerConnection.regNo);
        long startTime = CurrentQuiz.question.getStartTime().getTime();
        long endTime = CurrentQuiz.getCurrentQuizQuestion().duration * 60 *1000 + startTime;
        QuizStarted frm = new QuizStarted(startTime, endTime);
        frm.setVisible(true);
        frm.parRef = this;
        this.setVisible(false);
    }
    /**
     * if the login button is pressed
     * @param evt login button pressed event
     */
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
        tryToLogIn();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void serverIpTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverIpTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serverIpTextFieldActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ServerPortLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel regNoLabel;
    private javax.swing.JTextField regNoTextField;
    private javax.swing.JLabel serverIpLabel;
    private javax.swing.JTextField serverIpTextField;
    private javax.swing.JTextField serverPortTextField;
    private javax.swing.JLabel titelTextLabel;
    // End of variables declaration//GEN-END:variables
}
