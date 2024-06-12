/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import java.sql.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author gamitha
 */
public class Settings extends javax.swing.JPanel {

    /**
     * Creates new form Settings
     */
    private void addFocusListener(JTextField textField) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setVisible(false);
                }
            }
        });
    }
    
    public Settings() {
        initComponents();
        
        // Set visibility off
        txtOldPassword.setVisible(false);
        txtNewPassword.setVisible(false);
        txtVerifyPassword.setVisible(false);
        
        // Add focus listeners to textboxes
        addFocusListener(txtOldPassword);
        addFocusListener(txtNewPassword);
        addFocusListener(txtVerifyPassword);
       
        // Set SVGs
        sVGAccountForm.setSvgImage("./svgcomponents/AccountForm.svg", 451, 52);
        sVGInfo1.setSvgImage("./svgcomponents/Info.svg", 16, 16);
        sVGInfo2.setSvgImage("./svgcomponents/Info.svg", 16, 16);
        sVGLogoutBtn.setSvgImage("./svgcomponents/LogoutBtn.svg", 57, 22);
        sVGChangePasswordForm.setSvgImage("./svgcomponents/ChangePasswordForm.svg", 451, 111);
        sVGOldPasswordTextBox.setSvgImage("./svgcomponents/OldPasswordTextBox.svg", 159, 23);
        sVGNewPasswordTextBox.setSvgImage("./svgcomponents/NewPasswordTextBox.svg", 159, 23);
        sVGVerifyPasswordsTextBox.setSvgImage("./svgcomponents/VerifyPasswordTextBox.svg", 159, 23);
        sVGChangePasswordBtn.setSvgImage("./svgcomponents/ChangePasswordBtn.svg", 124, 22);
        sVGAboutForm.setSvgImage("./svgcomponents/AboutForm.svg", 451, 52);
        
        // Set Cursors
        Cursor txtCur = new Cursor(Cursor.TEXT_CURSOR);
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGOldPasswordTextBox.setCursor(txtCur);
        sVGNewPasswordTextBox.setCursor(txtCur);
        sVGVerifyPasswordsTextBox.setCursor(txtCur);
        sVGChangePasswordBtn.setCursor(hand);
        sVGLogoutBtn.setCursor(hand);
        
        // Set user related
        User user = Main.getUser();
        if (user != null) {
            lblUserName.setText(user.fName);
            lblUserRole.setText(user.role);
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

        lblUserName = new javax.swing.JLabel();
        lblUserRole = new javax.swing.JLabel();
        lblAbout = new javax.swing.JLabel();
        sVGInfo2 = new main.SVGImage();
        sVGInfo1 = new main.SVGImage();
        sVGAccountForm = new main.SVGImage();
        sVGLogoutBtn = new main.SVGImage();
        sVGChangePasswordBtn = new main.SVGImage();
        lblAccount = new javax.swing.JLabel();
        sVGAboutForm = new main.SVGImage();
        txtOldPassword = new javax.swing.JPasswordField();
        txtNewPassword = new javax.swing.JPasswordField();
        txtVerifyPassword = new javax.swing.JPasswordField();
        sVGOldPasswordTextBox = new main.SVGImage();
        sVGNewPasswordTextBox = new main.SVGImage();
        sVGVerifyPasswordsTextBox = new main.SVGImage();
        sVGChangePasswordForm = new main.SVGImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);

        lblUserName.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(0, 0, 0));
        lblUserName.setText("Name");
        add(lblUserName);
        lblUserName.setBounds(30, 56, 290, 20);

        lblUserRole.setFont(new java.awt.Font("SF Pro Display", 0, 11)); // NOI18N
        lblUserRole.setForeground(new java.awt.Color(0, 0, 0));
        lblUserRole.setText("Role");
        add(lblUserRole);
        lblUserRole.setBounds(30, 74, 220, 14);

        lblAbout.setFont(new java.awt.Font("SF Pro Text", 1, 13)); // NOI18N
        lblAbout.setForeground(new java.awt.Color(0, 0, 0));
        lblAbout.setText("About");
        add(lblAbout);
        lblAbout.setBounds(20, 342, 40, 16);

        sVGInfo2.setForeground(new java.awt.Color(0, 0, 0));
        sVGInfo2.setText("sVGInfo2");
        sVGInfo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGInfo2MouseClicked(evt);
            }
        });
        add(sVGInfo2);
        sVGInfo2.setBounds(445, 386, 16, 16);

        sVGInfo1.setForeground(new java.awt.Color(0, 0, 0));
        sVGInfo1.setText("sVGInfo1");
        sVGInfo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGInfo1MouseClicked(evt);
            }
        });
        add(sVGInfo1);
        sVGInfo1.setBounds(445, 64, 16, 16);

        sVGAccountForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAccountForm.setText("sVGAccountForm");
        add(sVGAccountForm);
        sVGAccountForm.setBounds(20, 46, 451, 52);

        sVGLogoutBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGLogoutBtn.setText("sVGLogoutBtn");
        sVGLogoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGLogoutBtnMouseClicked(evt);
            }
        });
        add(sVGLogoutBtn);
        sVGLogoutBtn.setBounds(414, 113, 57, 22);

        sVGChangePasswordBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGChangePasswordBtn.setText("sVGChangePasswordBtn");
        sVGChangePasswordBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGChangePasswordBtnMouseClicked(evt);
            }
        });
        add(sVGChangePasswordBtn);
        sVGChangePasswordBtn.setBounds(347, 290, 124, 22);

        lblAccount.setFont(new java.awt.Font("SF Pro Text", 1, 13)); // NOI18N
        lblAccount.setForeground(new java.awt.Color(0, 0, 0));
        lblAccount.setText("Account");
        add(lblAccount);
        lblAccount.setBounds(20, 20, 60, 16);

        sVGAboutForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAboutForm.setText("sVGAboutForm");
        add(sVGAboutForm);
        sVGAboutForm.setBounds(20, 368, 451, 52);

        txtOldPassword.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtOldPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtOldPassword.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOldPassword.setBorder(null);
        add(txtOldPassword);
        txtOldPassword.setBounds(308, 174, 147, 17);

        txtNewPassword.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtNewPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtNewPassword.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNewPassword.setBorder(null);
        add(txtNewPassword);
        txtNewPassword.setBounds(308, 211, 147, 17);

        txtVerifyPassword.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtVerifyPassword.setForeground(new java.awt.Color(0, 0, 0));
        txtVerifyPassword.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtVerifyPassword.setBorder(null);
        add(txtVerifyPassword);
        txtVerifyPassword.setBounds(308, 248, 147, 17);

        sVGOldPasswordTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGOldPasswordTextBox.setText("sVGOldPasswordTextBox");
        sVGOldPasswordTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGOldPasswordTextBoxMouseClicked(evt);
            }
        });
        add(sVGOldPasswordTextBox);
        sVGOldPasswordTextBox.setBounds(302, 171, 159, 23);

        sVGNewPasswordTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGNewPasswordTextBox.setText("sVGNewPasswordTextBox");
        sVGNewPasswordTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGNewPasswordTextBoxMouseClicked(evt);
            }
        });
        add(sVGNewPasswordTextBox);
        sVGNewPasswordTextBox.setBounds(302, 208, 159, 23);

        sVGVerifyPasswordsTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGVerifyPasswordsTextBox.setText("sVGVerifyPasswordsTextBox");
        sVGVerifyPasswordsTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGVerifyPasswordsTextBoxMouseClicked(evt);
            }
        });
        add(sVGVerifyPasswordsTextBox);
        sVGVerifyPasswordsTextBox.setBounds(302, 245, 159, 23);

        sVGChangePasswordForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGChangePasswordForm.setText("sVGChangePasswordForm");
        add(sVGChangePasswordForm);
        sVGChangePasswordForm.setBounds(20, 164, 451, 111);
    }// </editor-fold>//GEN-END:initComponents

    private void sVGOldPasswordTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGOldPasswordTextBoxMouseClicked
        txtOldPassword.setVisible(true);
        txtOldPassword.requestFocusInWindow();
    }//GEN-LAST:event_sVGOldPasswordTextBoxMouseClicked

    private void sVGNewPasswordTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGNewPasswordTextBoxMouseClicked
        txtNewPassword.setVisible(true);
        txtNewPassword.requestFocusInWindow();
    }//GEN-LAST:event_sVGNewPasswordTextBoxMouseClicked

    private void sVGVerifyPasswordsTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGVerifyPasswordsTextBoxMouseClicked
        txtVerifyPassword.setVisible(true);
        txtVerifyPassword.requestFocusInWindow();
    }//GEN-LAST:event_sVGVerifyPasswordsTextBoxMouseClicked

    private void sVGChangePasswordBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGChangePasswordBtnMouseClicked
        String oldPass = txtOldPassword.getText();
        String newPass = txtNewPassword.getText();
        String verifyPass = txtVerifyPassword.getText();
        
        // Check whether verify pass is valid
        if (newPass.equals(verifyPass)) {
            // Validate old pass with the user password of the object
            User user = Main.getUser();
            if (oldPass.equals(user.passWord)) {
                // Updates the password
                Connection conn = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");

                    Statement st = conn.createStatement();
                    int updatedRows = st.executeUpdate("UPDATE Employee SET Pass = '" + newPass + "' WHERE EmpID = " + user.eid);
                    if(updatedRows > 0) {
                        // Update the password of the object
                        Main.passwordChange(newPass);
                        // Clear the textboxes
                        txtOldPassword.setText("");
                        txtNewPassword.setText("");
                        txtVerifyPassword.setText("");
                        // Show message
                        MessageDialog passwordUpdatedSuccess = new MessageDialog(0, this, "Settings", "Password Updated", "Password has been updated successfully.");
                    } else {
                        MessageDialog passwordUpdateFailure = new MessageDialog(1, this, "Settings", "Password Update Failed", "Failed to update password. Please try again.");
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
            } else {
                MessageDialog incorrectPassword = new MessageDialog(1, this, "Settings", "Incorrect Password", "Password is incorrect. Try again.");
            }
        } else {
            MessageDialog passwordMismatch = new MessageDialog(1, this, "Settings", "Password Mismatch", "New password and verify password doesn't match. Please try again.");
        }
    }//GEN-LAST:event_sVGChangePasswordBtnMouseClicked

    private void sVGLogoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGLogoutBtnMouseClicked
        // Open Login
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        // Close self
        SwingUtilities.getWindowAncestor(sVGLogoutBtn).dispose();
    }//GEN-LAST:event_sVGLogoutBtnMouseClicked

    private void sVGInfo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGInfo1MouseClicked
        MessageDialog empDialog = new MessageDialog(
                3,
                this,
                null,
                null,
                null
        );
    }//GEN-LAST:event_sVGInfo1MouseClicked

    private void sVGInfo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGInfo2MouseClicked
        MessageDialog aboutDialog = new MessageDialog(
                2,
                this,
                "About",
                "About",
                "LittleGroot is a farm management system developed in Java. It is designed to streamline the process of managing a farm, from tracking finances to monitoring crop growth. It is developed as an assignment of the module \"Object-oriented programming with Java\" at NIBM Kandy Innovation Center.");
    }//GEN-LAST:event_sVGInfo2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAbout;
    private javax.swing.JLabel lblAccount;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRole;
    private main.SVGImage sVGAboutForm;
    private main.SVGImage sVGAccountForm;
    private main.SVGImage sVGChangePasswordBtn;
    private main.SVGImage sVGChangePasswordForm;
    private main.SVGImage sVGInfo1;
    private main.SVGImage sVGInfo2;
    private main.SVGImage sVGLogoutBtn;
    private main.SVGImage sVGNewPasswordTextBox;
    private main.SVGImage sVGOldPasswordTextBox;
    private main.SVGImage sVGVerifyPasswordsTextBox;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JPasswordField txtOldPassword;
    private javax.swing.JPasswordField txtVerifyPassword;
    // End of variables declaration//GEN-END:variables
}
