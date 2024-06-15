/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author gamitha
 */
public class LoginReset extends javax.swing.JPanel {
    int empID;
    int secQQ;
    String secQA;
    String newPass;
    String verifyPass;
    /**
     * Creates new form LoginReset
     */
    // Document listener and focus listener for textboxes
    private void addDocumentListenerAndFocusListener(JTextField textField, JLabel label) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                label.setVisible(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    label.setVisible(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This method is not used in this example
            }
        });

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setVisible(false);
                }
            }
        });
    }
    
    public LoginReset() {
        initComponents();
        
        // Visibility off
        cmbSecQuestion.setVisible(false);
        txtSecQuestionAnswer.setVisible(false);
        sVGSecQuestionAnswerClear.setVisible(false);
        txtEmpID.setVisible(false);
        sVGEmpIDClear.setVisible(false);
        sVGVerifyPasswordTextBox.setVisible(false);
        txtVerifyPassword.setVisible(false);
        sVGVerifyPasswordClear.setVisible(false);
        sVGNewPasswordTextBox.setVisible(false);
        txtNewPassword.setVisible(false);
        sVGNewPasswordClear.setVisible(false);
        sVGSubmitBtn2.setVisible(false);
        
        // Add document and focus listeners
        addDocumentListenerAndFocusListener(txtSecQuestionAnswer, sVGSecQuestionAnswerClear);
        addDocumentListenerAndFocusListener(txtEmpID, sVGEmpIDClear);
        addDocumentListenerAndFocusListener(txtVerifyPassword, sVGVerifyPasswordClear);
        addDocumentListenerAndFocusListener(txtNewPassword, sVGNewPasswordClear);
        
        // Set SVGs
        sVGNameLogo.setSvgImage("./svgcomponents/NameLogo.svg", 160, 32);
        sVGSecQuestionCmb.setSvgImage("./svgcomponents/SecurityQuestionCmb.svg", 206, 22);
        sVGSecQuestionAnswerTextBox.setSvgImage("./svgcomponents/SecurityQuestionTextBox.svg", 206, 22);
        sVGSubmitBtn1.setSvgImage("./svgcomponents/SubmitBtn.svg", 58, 22);
        sVGSecQuestionAnswerClear.setSvgImage("./svgcomponents/ClearBtn.svg", 16, 16);
        sVGEmpIDTextBox.setSvgImage("./svgcomponents/EmpIDTextBox.svg", 206, 22);
        sVGEmpIDClear.setSvgImage("./svgcomponents/ClearBtn.svg", 16, 16);
        sVGVerifyPasswordClear.setSvgImage("./svgcomponents/ClearBtn.svg", 16, 16);
        sVGNewPasswordClear.setSvgImage("./svgcomponents/ClearBtn.svg", 16, 16);
        sVGSubmitBtn2.setSvgImage("./svgcomponents/SubmitBtn.svg", 58, 22);
        sVGVerifyPasswordTextBox.setSvgImage("./svgcomponents/ResetVerifyPasswordTextBox.svg", 206, 22);
        sVGNewPasswordTextBox.setSvgImage("./svgcomponents/ResetNewPasswordTextBox.svg", 206, 22);
        
        // Set PNGs
        addImageToLabel();
        
        // Set Cursors
        Cursor txtCur = new Cursor(Cursor.TEXT_CURSOR);
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGSecQuestionAnswerTextBox.setCursor(txtCur);
        sVGSubmitBtn1.setCursor(hand);
        sVGEmpIDTextBox.setCursor(txtCur);
        sVGSubmitBtn2.setCursor(hand);
        sVGVerifyPasswordTextBox.setCursor(txtCur);
        sVGNewPasswordTextBox.setCursor(txtCur);
        lblBackBtn.setCursor(hand);
    }
    
    private void addImageToLabel() {
        // Logo
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Logo.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(150, 145, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngLogo.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pngLogo = new javax.swing.JLabel();
        sVGNameLogo = new main.SVGImage();
        sVGEmpIDClear = new main.SVGImage();
        txtEmpID = new javax.swing.JTextField();
        sVGEmpIDTextBox = new main.SVGImage();
        cmbSecQuestion = new javax.swing.JComboBox<>();
        sVGSecQuestionCmb = new main.SVGImage();
        sVGSecQuestionAnswerClear = new main.SVGImage();
        txtSecQuestionAnswer = new javax.swing.JTextField();
        sVGSecQuestionAnswerTextBox = new main.SVGImage();
        sVGSubmitBtn1 = new main.SVGImage();
        sVGVerifyPasswordClear = new main.SVGImage();
        txtVerifyPassword = new javax.swing.JPasswordField();
        sVGVerifyPasswordTextBox = new main.SVGImage();
        sVGNewPasswordClear = new main.SVGImage();
        txtNewPassword = new javax.swing.JPasswordField();
        sVGNewPasswordTextBox = new main.SVGImage();
        sVGSubmitBtn2 = new main.SVGImage();
        lblResetPassword = new javax.swing.JLabel();
        lblBackBtn = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setLayout(null);

        pngLogo.setBackground(new java.awt.Color(0, 0, 0));
        pngLogo.setForeground(new java.awt.Color(0, 0, 0));
        pngLogo.setText("pngLogo");
        add(pngLogo);
        pngLogo.setBounds(350, 75, 150, 145);

        sVGNameLogo.setForeground(new java.awt.Color(0, 0, 0));
        sVGNameLogo.setText("sVGNameLogo");
        add(sVGNameLogo);
        sVGNameLogo.setBounds(345, 236, 160, 32);

        sVGEmpIDClear.setForeground(new java.awt.Color(0, 0, 0));
        sVGEmpIDClear.setText("sVGEmpIDClear");
        sVGEmpIDClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGEmpIDClearMouseClicked(evt);
            }
        });
        add(sVGEmpIDClear);
        sVGEmpIDClear.setBounds(507, 314, 16, 16);

        txtEmpID.setFont(new java.awt.Font("SF Pro Text", 0, 13)); // NOI18N
        txtEmpID.setForeground(new java.awt.Color(0, 0, 0));
        txtEmpID.setBorder(null);
        txtEmpID.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtEmpIDPropertyChange(evt);
            }
        });
        add(txtEmpID);
        txtEmpID.setBounds(329, 314, 192, 16);

        sVGEmpIDTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGEmpIDTextBox.setText("sVGEmpIDTextBox");
        sVGEmpIDTextBox.setFocusable(false);
        sVGEmpIDTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sVGEmpIDTextBoxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sVGEmpIDTextBoxFocusLost(evt);
            }
        });
        sVGEmpIDTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGEmpIDTextBoxMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sVGEmpIDTextBoxMouseReleased(evt);
            }
        });
        add(sVGEmpIDTextBox);
        sVGEmpIDTextBox.setBounds(322, 311, 206, 22);

        cmbSecQuestion.setFont(new java.awt.Font("SF Pro Text", 0, 13)); // NOI18N
        cmbSecQuestion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "What was the name of your first pet?", "What was the make and model of your first car?", "What was the name of the street where you grew up?", "What was the name of your first school?", "What is your mother’s maiden name?", "What is your favorite book?", "What is your favorite movie?", "What is your favorite food?", "What city were you born in?", "What is the name of your favorite childhood friend?" }));
        cmbSecQuestion.setSelectedIndex(-1);
        cmbSecQuestion.setToolTipText("");
        cmbSecQuestion.setBorder(null);
        cmbSecQuestion.setName(""); // NOI18N
        cmbSecQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSecQuestionActionPerformed(evt);
            }
        });
        add(cmbSecQuestion);
        cmbSecQuestion.setBounds(322, 353, 206, 22);

        sVGSecQuestionCmb.setForeground(new java.awt.Color(0, 0, 0));
        sVGSecQuestionCmb.setText("sVGSecQuestionCmb");
        sVGSecQuestionCmb.setFocusable(false);
        sVGSecQuestionCmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sVGSecQuestionCmbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sVGSecQuestionCmbFocusLost(evt);
            }
        });
        sVGSecQuestionCmb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSecQuestionCmbMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sVGSecQuestionCmbMouseReleased(evt);
            }
        });
        add(sVGSecQuestionCmb);
        sVGSecQuestionCmb.setBounds(322, 353, 206, 22);

        sVGSecQuestionAnswerClear.setForeground(new java.awt.Color(0, 0, 0));
        sVGSecQuestionAnswerClear.setText("sVGSecQuestionAnswerClear");
        sVGSecQuestionAnswerClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSecQuestionAnswerClearMouseClicked(evt);
            }
        });
        add(sVGSecQuestionAnswerClear);
        sVGSecQuestionAnswerClear.setBounds(507, 398, 16, 16);

        txtSecQuestionAnswer.setFont(new java.awt.Font("SF Pro Text", 0, 13)); // NOI18N
        txtSecQuestionAnswer.setForeground(new java.awt.Color(0, 0, 0));
        txtSecQuestionAnswer.setBorder(null);
        txtSecQuestionAnswer.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtSecQuestionAnswerPropertyChange(evt);
            }
        });
        add(txtSecQuestionAnswer);
        txtSecQuestionAnswer.setBounds(329, 398, 192, 16);

        sVGSecQuestionAnswerTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGSecQuestionAnswerTextBox.setText("sVGSecQuestionAnswerTextBox");
        sVGSecQuestionAnswerTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSecQuestionAnswerTextBoxMouseClicked(evt);
            }
        });
        add(sVGSecQuestionAnswerTextBox);
        sVGSecQuestionAnswerTextBox.setBounds(322, 395, 206, 22);

        sVGSubmitBtn1.setForeground(new java.awt.Color(0, 0, 0));
        sVGSubmitBtn1.setText("sVGSubmitBtn1");
        sVGSubmitBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSubmitBtn1MouseClicked(evt);
            }
        });
        add(sVGSubmitBtn1);
        sVGSubmitBtn1.setBounds(471, 432, 58, 22);

        sVGVerifyPasswordClear.setForeground(new java.awt.Color(0, 0, 0));
        sVGVerifyPasswordClear.setText("sVGVerifyPasswordClear");
        sVGVerifyPasswordClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGVerifyPasswordClearMouseClicked(evt);
            }
        });
        add(sVGVerifyPasswordClear);
        sVGVerifyPasswordClear.setBounds(507, 356, 16, 16);

        txtVerifyPassword.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtVerifyPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtVerifyPassword.setBorder(null);
        add(txtVerifyPassword);
        txtVerifyPassword.setBounds(329, 356, 192, 16);

        sVGVerifyPasswordTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGVerifyPasswordTextBox.setText("sVGVerifyPasswordTextBox");
        sVGVerifyPasswordTextBox.setToolTipText("");
        sVGVerifyPasswordTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGVerifyPasswordTextBoxMouseClicked(evt);
            }
        });
        add(sVGVerifyPasswordTextBox);
        sVGVerifyPasswordTextBox.setBounds(322, 353, 206, 22);

        sVGNewPasswordClear.setForeground(new java.awt.Color(0, 0, 0));
        sVGNewPasswordClear.setText("sVGNewPasswordClear");
        sVGNewPasswordClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGNewPasswordClearMouseClicked(evt);
            }
        });
        add(sVGNewPasswordClear);
        sVGNewPasswordClear.setBounds(507, 314, 16, 16);

        txtNewPassword.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtNewPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtNewPassword.setBorder(null);
        add(txtNewPassword);
        txtNewPassword.setBounds(329, 314, 192, 16);

        sVGNewPasswordTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGNewPasswordTextBox.setText("sVGNewPasswordTextBox");
        sVGNewPasswordTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGNewPasswordTextBoxMouseClicked(evt);
            }
        });
        add(sVGNewPasswordTextBox);
        sVGNewPasswordTextBox.setBounds(322, 311, 206, 22);

        sVGSubmitBtn2.setForeground(new java.awt.Color(0, 0, 0));
        sVGSubmitBtn2.setText("sVGSubmitBtn2");
        sVGSubmitBtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSubmitBtn2MouseClicked(evt);
            }
        });
        add(sVGSubmitBtn2);
        sVGSubmitBtn2.setBounds(471, 390, 58, 22);

        lblResetPassword.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblResetPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblResetPassword.setText("Reset Password");
        lblResetPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResetPasswordMouseClicked(evt);
            }
        });
        add(lblResetPassword);
        lblResetPassword.setBounds(46, 25, 130, 20);

        lblBackBtn.setFont(new java.awt.Font("SF Pro Text", 0, 15)); // NOI18N
        lblBackBtn.setForeground(new java.awt.Color(0, 0, 0));
        lblBackBtn.setText("􀯶");
        lblBackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackBtnMouseClicked(evt);
            }
        });
        add(lblBackBtn);
        lblBackBtn.setBounds(25, 25, 11, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void sVGEmpIDTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sVGEmpIDTextBoxFocusGained

    }//GEN-LAST:event_sVGEmpIDTextBoxFocusGained

    private void sVGEmpIDTextBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sVGEmpIDTextBoxFocusLost

    }//GEN-LAST:event_sVGEmpIDTextBoxFocusLost

    private void sVGEmpIDTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGEmpIDTextBoxMouseClicked
        txtEmpID.setVisible(true);
        txtEmpID.requestFocusInWindow();
    }//GEN-LAST:event_sVGEmpIDTextBoxMouseClicked

    private void sVGEmpIDTextBoxMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGEmpIDTextBoxMouseReleased

    }//GEN-LAST:event_sVGEmpIDTextBoxMouseReleased

    private void txtEmpIDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtEmpIDPropertyChange

    }//GEN-LAST:event_txtEmpIDPropertyChange

    private void cmbSecQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSecQuestionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSecQuestionActionPerformed

    private void sVGSecQuestionCmbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sVGSecQuestionCmbFocusGained

    }//GEN-LAST:event_sVGSecQuestionCmbFocusGained

    private void sVGSecQuestionCmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sVGSecQuestionCmbFocusLost

    }//GEN-LAST:event_sVGSecQuestionCmbFocusLost

    private void sVGSecQuestionCmbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSecQuestionCmbMouseClicked
        cmbSecQuestion.setVisible(true);
        cmbSecQuestion.requestFocusInWindow();
    }//GEN-LAST:event_sVGSecQuestionCmbMouseClicked

    private void sVGSecQuestionCmbMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSecQuestionCmbMouseReleased

    }//GEN-LAST:event_sVGSecQuestionCmbMouseReleased

    private void sVGSecQuestionAnswerTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSecQuestionAnswerTextBoxMouseClicked
        txtSecQuestionAnswer.setVisible(true);
        txtSecQuestionAnswer.requestFocusInWindow();
    }//GEN-LAST:event_sVGSecQuestionAnswerTextBoxMouseClicked

    private void txtSecQuestionAnswerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtSecQuestionAnswerPropertyChange

    }//GEN-LAST:event_txtSecQuestionAnswerPropertyChange

    private void sVGSubmitBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSubmitBtn1MouseClicked
        String stringEID = txtEmpID.getText();
        secQQ = cmbSecQuestion.getSelectedIndex();
        secQA = txtSecQuestionAnswer.getText();
        
        // Check for invalid emp id
        if (stringEID.matches("\\d{4}")) {
            empID = Integer.parseInt(stringEID);
        } else {
            MessageDialog invEmpID = new MessageDialog(1, this, "Login", "Login Failed", "Invalid employee ID. Please try again.");
            return;
        }
        
        if (secQQ == -1) { // Check whether selected a question
            MessageDialog noQuestionSelected = new MessageDialog(1, this, "Login", "No Security Question Selected", "Please select a security question for your account.");
            return;
        } else if (secQA.isBlank()) { // Check whether answer is blank
            MessageDialog noAnswerProvided = new MessageDialog(1, this, "Login", "No Answer Provided", "Please provide an answer to the security question.");
            return;
        }
        
        // Validate with the database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT SecQQ, SecQA FROM Employee WHERE EmpID = '" + empID + "' AND SecQQ = '" + secQQ + "' AND SecQA = '" + secQA + "'");
            
            if (rs.next()) {
                txtEmpID.setVisible(false);
                sVGEmpIDTextBox.setVisible(false);
                cmbSecQuestion.setVisible(false);
                sVGSecQuestionCmb.setVisible(false);
                txtSecQuestionAnswer.setVisible(false);
                sVGSecQuestionAnswerTextBox.setVisible(false);
                sVGSubmitBtn1.setVisible(false);
                sVGVerifyPasswordTextBox.setVisible(true);
                sVGNewPasswordTextBox.setVisible(true);
                sVGSubmitBtn2.setVisible(true);
                sVGEmpIDClear.setVisible(false);
                sVGSecQuestionAnswerClear.setVisible(false);
            } else {
                // Display Error Message
                MessageDialog logFailure = new MessageDialog(1, this, "Reset Password", "Security Check Failed", "Security question and answer don't match. Please try again.");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            MessageDialog dbConnectionFailure = new MessageDialog(1, this, "Database", "Database Connection Failed", "Unable to connect to the database.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    MessageDialog closeConnectionFailure = new MessageDialog(1, this, "Database", "Connection Closure Failed", "Failed to close the database connection.");
                }
            }
        }
    }//GEN-LAST:event_sVGSubmitBtn1MouseClicked

    private void sVGEmpIDClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGEmpIDClearMouseClicked
        txtEmpID.setText("");
        sVGEmpIDClear.setVisible(false);
    }//GEN-LAST:event_sVGEmpIDClearMouseClicked

    private void sVGSecQuestionAnswerClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSecQuestionAnswerClearMouseClicked
        txtSecQuestionAnswer.setText("");
        sVGSecQuestionAnswerClear.setVisible(false);
    }//GEN-LAST:event_sVGSecQuestionAnswerClearMouseClicked

    private void sVGVerifyPasswordTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGVerifyPasswordTextBoxMouseClicked
        txtVerifyPassword.setVisible(true);
        txtVerifyPassword.requestFocusInWindow();
    }//GEN-LAST:event_sVGVerifyPasswordTextBoxMouseClicked

    private void sVGVerifyPasswordClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGVerifyPasswordClearMouseClicked
        txtVerifyPassword.setText("");
        sVGVerifyPasswordClear.setVisible(false);
    }//GEN-LAST:event_sVGVerifyPasswordClearMouseClicked

    private void sVGNewPasswordClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGNewPasswordClearMouseClicked
        txtNewPassword.setText("");
        sVGNewPasswordClear.setVisible(false);
    }//GEN-LAST:event_sVGNewPasswordClearMouseClicked

    private void sVGNewPasswordTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGNewPasswordTextBoxMouseClicked
        txtNewPassword.setVisible(true);
        txtNewPassword.requestFocusInWindow();
    }//GEN-LAST:event_sVGNewPasswordTextBoxMouseClicked

    private void sVGSubmitBtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSubmitBtn2MouseClicked
        verifyPass = txtVerifyPassword.getText();
        newPass = txtNewPassword.getText();
        
        // Check for password mismatch
        if (!newPass.equals(verifyPass)) {
            MessageDialog passwordMismatch = new MessageDialog(1, this, "Reset Password", "Password Mismatch", "New password and verify password doesn't match. Please try again.");
            return;
        }
        
        // Update the database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");

            Statement st = conn.createStatement();
            int updatedRows = st.executeUpdate("UPDATE Employee SET Pass = '" + newPass + "' WHERE EmpID = " + empID);
            if(updatedRows > 0) {
                // Show message
                MessageDialog passwordUpdatedSuccess = new MessageDialog(0, this, "Reset Password", "Password Updated", "Password has been updated successfully.");

                // Close self
                this.setVisible(false);
            } else {
                MessageDialog passwordUpdateFailure = new MessageDialog(1, this, "Reset Password", "Password Update Failed", "Failed to update password. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            MessageDialog dbConnectionFailure = new MessageDialog(1, this, "Database", "Database Connection Failed", "Unable to connect to the database.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    MessageDialog closeConnectionFailure = new MessageDialog(1, this, "Database", "Connection Closure Failed", "Failed to close the database connection.");
                }
            }
        }
    }//GEN-LAST:event_sVGSubmitBtn2MouseClicked

    private void lblResetPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResetPasswordMouseClicked

    }//GEN-LAST:event_lblResetPasswordMouseClicked

    private void lblBackBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackBtnMouseClicked
        this.setVisible(false);
    }//GEN-LAST:event_lblBackBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbSecQuestion;
    private javax.swing.JLabel lblBackBtn;
    private javax.swing.JLabel lblResetPassword;
    private javax.swing.JLabel pngLogo;
    private main.SVGImage sVGEmpIDClear;
    private main.SVGImage sVGEmpIDTextBox;
    private main.SVGImage sVGNameLogo;
    private main.SVGImage sVGNewPasswordClear;
    private main.SVGImage sVGNewPasswordTextBox;
    private main.SVGImage sVGSecQuestionAnswerClear;
    private main.SVGImage sVGSecQuestionAnswerTextBox;
    private main.SVGImage sVGSecQuestionCmb;
    private main.SVGImage sVGSubmitBtn1;
    private main.SVGImage sVGSubmitBtn2;
    private main.SVGImage sVGVerifyPasswordClear;
    private main.SVGImage sVGVerifyPasswordTextBox;
    private javax.swing.JTextField txtEmpID;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JTextField txtSecQuestionAnswer;
    private javax.swing.JPasswordField txtVerifyPassword;
    // End of variables declaration//GEN-END:variables
}
