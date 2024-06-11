/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.sql.*;

/**
 *
 * @author gamitha
 */
public class AddTransactions extends javax.swing.JPanel {
    public boolean needRefresh = false;
    public boolean needDashRefresh = false;
    boolean radioIncome = true;
    
    private void radioSelect(char btn) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbCategory.getModel();
        switch(btn) {
            case 'I':
                if (!radioIncome) {
                    sVGAddTransactionTypeIncomeRadioBtn.setSvgImage("./svgcomponents/RadioBtnSelected.svg", 14, 14);
                    sVGAddTransactionTypeExpenseRadioBtn.setSvgImage("./svgcomponents/RadioBtn.svg", 14, 14);
                    radioIncome = true;
                    if (model.getElementAt(0).equals("Generic")) {
                        model.removeElementAt(0);
                    }
                }
                break;
            case 'E':
                if (radioIncome) {
                    sVGAddTransactionTypeIncomeRadioBtn.setSvgImage("./svgcomponents/RadioBtn.svg", 14, 14);
                    sVGAddTransactionTypeExpenseRadioBtn.setSvgImage("./svgcomponents/RadioBtnSelected.svg", 14, 14);
                    radioIncome = false;
                    if (!model.getElementAt(0).equals("Generic")) {
                        model.insertElementAt("Generic", 0);
                    }
                }
                break;
        }
    }
    
    int month = (int) Calendar.getInstance().get(Calendar.MONTH)+1;
    int year = (int) Calendar.getInstance().get(Calendar.YEAR);
    int day = (int) Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        
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
    
    public AddTransactions() {
        initComponents();
        
        // Set visibilities off
        txtAmount.setVisible(false);
        txtTransaction.setVisible(false);
        
        // Add focus listeners to textboxes
        addFocusListener(txtAmount);
        addFocusListener(txtTransaction);
        
        // Steppers setup
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(stepperYear, "#");
        stepperYear.setEditor(editor);
        stepperYear.setValue(year);
        stepperMonth.setValue(month);
        stepperDay.setValue(day);
        
        int maxDay;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                maxDay = 29; // Leap year
            } else {
                maxDay = 28; // Not a leap year
            }
        } else {
            maxDay = 31;
        }
        SpinnerNumberModel model = (SpinnerNumberModel) stepperDay.getModel();
        model.setMaximum(maxDay);
        
        // Set SVGs
        sVGAddTransactionsForm.setSvgImage("./svgcomponents/AddTransactionsForm.svg", 451, 182);
        sVGAddTransactionsType.setSvgImage("./svgcomponents/AddTransactionsType.svg", 431, 36);
        sVGAddTransactionsCategory.setSvgImage("./svgcomponents/AddTransactionsCategory.svg", 431, 36);
        sVGAddTransactionsDate.setSvgImage("./svgcomponents/AddTransactionsDate.svg", 431, 36);
        sVGAddTransactionsAmount.setSvgImage("./svgcomponents/AddTransactionsAmount.svg", 431, 36);
        sVGAddBtn.setSvgImage("./svgcomponents/AddBtn.svg", 39, 22);
        sVGAddTransactionsAmountTextBox.setSvgImage("./svgcomponents/AddTransactionsAmountTextBox.svg", 160, 23);
        sVGAddTransactionTypeIncomeRadioBtn.setSvgImage("./svgcomponents/RadioBtnSelected.svg", 14, 14);
        sVGAddTransactionTypeExpenseRadioBtn.setSvgImage("./svgcomponents/RadioBtn.svg", 14, 14);
        sVGAddTransactionsTransaction.setSvgImage("./svgcomponents/AddTransactionsTransaction.svg", 431, 36);
        sVGAddTransactionsTransactionTextBox.setSvgImage("./svgcomponents/AddTransactionsTransactionTextBox.svg", 160, 23);
        
        // Set Cursors
        Cursor txtCur = new Cursor(Cursor.TEXT_CURSOR);
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGAddTransactionsAmountTextBox.setCursor(txtCur);
        sVGAddTransactionsTransactionTextBox.setCursor(txtCur);
        sVGAddTransactionTypeIncomeRadioBtn.setCursor(hand);
        sVGAddTransactionTypeExpenseRadioBtn.setCursor(hand);
        lblIncome.setCursor(hand);
        lblExpense.setCursor(hand);
        sVGAddBtn.setCursor(hand);
    }
    
    private void setMaxDate() {
        int month = (int) stepperMonth.getValue();
        int year = (int) stepperYear.getValue();
        int maxDay;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                maxDay = 29; // Leap year
            } else {
                maxDay = 28; // Not a leap year
            }
        } else {
            maxDay = 31;
        }
        SpinnerNumberModel model = (SpinnerNumberModel) stepperDay.getModel();
        model.setMaximum(maxDay);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbCategory = new javax.swing.JComboBox<>();
        stepperMonth = new javax.swing.JSpinner();
        stepperDay = new javax.swing.JSpinner();
        stepperYear = new javax.swing.JSpinner();
        txtAmount = new javax.swing.JTextField();
        txtTransaction = new javax.swing.JTextField();
        sVGAddBtn = new main.SVGImage();
        sVGAddTransactionsAmountTextBox = new main.SVGImage();
        sVGAddTransactionTypeIncomeRadioBtn = new main.SVGImage();
        sVGAddTransactionTypeExpenseRadioBtn = new main.SVGImage();
        lblIncome = new javax.swing.JLabel();
        lblExpense = new javax.swing.JLabel();
        sVGAddTransactionsType = new main.SVGImage();
        sVGAddTransactionsCategory = new main.SVGImage();
        sVGAddTransactionsDate = new main.SVGImage();
        sVGAddTransactionsAmount = new main.SVGImage();
        sVGAddTransactionsTransactionTextBox = new main.SVGImage();
        sVGAddTransactionsTransaction = new main.SVGImage();
        sVGAddTransactionsForm = new main.SVGImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 426));
        setLayout(null);

        cmbCategory.setBackground(new java.awt.Color(255, 255, 255));
        cmbCategory.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        cmbCategory.setForeground(new java.awt.Color(0, 0, 0));
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tomato", "Corn", "Apple", "Carrot", "Orange", "Mango" }));
        cmbCategory.setBorder(null);
        cmbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoryActionPerformed(evt);
            }
        });
        add(cmbCategory);
        cmbCategory.setBounds(301, 91, 160, 21);

        stepperMonth.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperMonth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        stepperMonth.setBorder(null);
        stepperMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperMonthStateChanged(evt);
            }
        });
        add(stepperMonth);
        stepperMonth.setBounds(276, 125, 50, 24);

        stepperDay.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperDay.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        stepperDay.setBorder(null);
        stepperDay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperDayStateChanged(evt);
            }
        });
        add(stepperDay);
        stepperDay.setBounds(336, 125, 50, 24);

        stepperYear.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperYear.setModel(new javax.swing.SpinnerNumberModel(2000, 2000, 2100, 1));
        stepperYear.setBorder(null);
        stepperYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperYearStateChanged(evt);
            }
        });
        add(stepperYear);
        stepperYear.setBounds(396, 125, 65, 24);

        txtAmount.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtAmount.setForeground(new java.awt.Color(0, 0, 0));
        txtAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAmount.setBorder(null);
        txtAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAmountKeyTyped(evt);
            }
        });
        add(txtAmount);
        txtAmount.setBounds(307, 165, 147, 17);

        txtTransaction.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtTransaction.setForeground(new java.awt.Color(0, 0, 0));
        txtTransaction.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTransaction.setBorder(null);
        txtTransaction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTransactionKeyTyped(evt);
            }
        });
        add(txtTransaction);
        txtTransaction.setBounds(307, 56, 147, 17);

        sVGAddBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddBtn.setText("sVGAddBtn");
        sVGAddBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddBtnMouseClicked(evt);
            }
        });
        add(sVGAddBtn);
        sVGAddBtn.setBounds(432, 216, 39, 22);

        sVGAddTransactionsAmountTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsAmountTextBox.setText("sVGAddTransactionsAmountTextBox");
        sVGAddTransactionsAmountTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddTransactionsAmountTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddTransactionsAmountTextBox);
        sVGAddTransactionsAmountTextBox.setBounds(301, 162, 160, 23);

        sVGAddTransactionTypeIncomeRadioBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionTypeIncomeRadioBtn.setText("sVGAddTransactionTypeIncomeRadioBtn");
        sVGAddTransactionTypeIncomeRadioBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddTransactionTypeIncomeRadioBtnMouseClicked(evt);
            }
        });
        add(sVGAddTransactionTypeIncomeRadioBtn);
        sVGAddTransactionTypeIncomeRadioBtn.setBounds(308, 21, 14, 14);

        sVGAddTransactionTypeExpenseRadioBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionTypeExpenseRadioBtn.setText("sVGAddTransactionTypeExpenseRadioBtn");
        sVGAddTransactionTypeExpenseRadioBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddTransactionTypeExpenseRadioBtnMouseClicked(evt);
            }
        });
        add(sVGAddTransactionTypeExpenseRadioBtn);
        sVGAddTransactionTypeExpenseRadioBtn.setBounds(389, 21, 14, 14);

        lblIncome.setFont(new java.awt.Font("SF Pro Text", 0, 13)); // NOI18N
        lblIncome.setForeground(new java.awt.Color(0, 0, 0));
        lblIncome.setText("Income");
        lblIncome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIncomeMouseClicked(evt);
            }
        });
        add(lblIncome);
        lblIncome.setBounds(328, 20, 45, 16);

        lblExpense.setFont(new java.awt.Font("SF Pro Text", 0, 13)); // NOI18N
        lblExpense.setForeground(new java.awt.Color(0, 0, 0));
        lblExpense.setText("Expense");
        lblExpense.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExpenseMouseClicked(evt);
            }
        });
        add(lblExpense);
        lblExpense.setBounds(409, 20, 52, 16);

        sVGAddTransactionsType.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsType.setText("sVGAddTransactionsType");
        add(sVGAddTransactionsType);
        sVGAddTransactionsType.setBounds(30, 10, 431, 36);

        sVGAddTransactionsCategory.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsCategory.setText("sVGAddTransactionsCategory");
        add(sVGAddTransactionsCategory);
        sVGAddTransactionsCategory.setBounds(30, 83, 431, 36);

        sVGAddTransactionsDate.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsDate.setText("sVGAddTransactionsDate");
        add(sVGAddTransactionsDate);
        sVGAddTransactionsDate.setBounds(30, 119, 431, 36);

        sVGAddTransactionsAmount.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsAmount.setText("sVGAddTransactionsAmount");
        add(sVGAddTransactionsAmount);
        sVGAddTransactionsAmount.setBounds(30, 155, 431, 36);

        sVGAddTransactionsTransactionTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsTransactionTextBox.setText("sVGAddTransactionsTransactionTextBox");
        sVGAddTransactionsTransactionTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddTransactionsTransactionTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddTransactionsTransactionTextBox);
        sVGAddTransactionsTransactionTextBox.setBounds(301, 53, 160, 23);

        sVGAddTransactionsTransaction.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsTransaction.setText("sVGAddTransactionsTransaction");
        add(sVGAddTransactionsTransaction);
        sVGAddTransactionsTransaction.setBounds(30, 46, 431, 36);

        sVGAddTransactionsForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsForm.setText("sVGAddTransactionsForm");
        add(sVGAddTransactionsForm);
        sVGAddTransactionsForm.setBounds(20, 10, 451, 182);
    }// </editor-fold>//GEN-END:initComponents

    private void lblExpenseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExpenseMouseClicked
        radioSelect('E');
    }//GEN-LAST:event_lblExpenseMouseClicked

    private void sVGAddTransactionTypeExpenseRadioBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddTransactionTypeExpenseRadioBtnMouseClicked
        radioSelect('E');
    }//GEN-LAST:event_sVGAddTransactionTypeExpenseRadioBtnMouseClicked

    private void lblIncomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIncomeMouseClicked
        radioSelect('I');
    }//GEN-LAST:event_lblIncomeMouseClicked

    private void sVGAddTransactionTypeIncomeRadioBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddTransactionTypeIncomeRadioBtnMouseClicked
        radioSelect('I');
    }//GEN-LAST:event_sVGAddTransactionTypeIncomeRadioBtnMouseClicked

    private void cmbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoryActionPerformed

    private void stepperMonthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stepperMonthStateChanged
        setMaxDate();
    }//GEN-LAST:event_stepperMonthStateChanged

    private void stepperDayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stepperDayStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_stepperDayStateChanged

    private void stepperYearStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stepperYearStateChanged
        setMaxDate();
    }//GEN-LAST:event_stepperYearStateChanged

    private void txtAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAmountKeyTyped
        // Only accepts integers
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9') ||
           (c == KeyEvent.VK_BACK_SPACE) ||
           (c == KeyEvent.VK_DELETE))) {
          evt.consume();  // ignore event
        }
    }//GEN-LAST:event_txtAmountKeyTyped

    private void sVGAddTransactionsAmountTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddTransactionsAmountTextBoxMouseClicked
        txtAmount.setVisible(true);
        txtAmount.requestFocusInWindow();
    }//GEN-LAST:event_sVGAddTransactionsAmountTextBoxMouseClicked

    private void sVGAddBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddBtnMouseClicked
        char ttype = 'I';
        if (radioIncome) {
            ttype = 'I';
        } else {
            ttype = 'E';
        }
        String transaction = txtTransaction.getText();
        String category = (String) cmbCategory.getSelectedItem();
        String selDay = String.format("%02d", (Integer) stepperDay.getValue());
        String selMonth = String.format("%02d", (Integer) stepperMonth.getValue());
        String selYear = Integer.toString((Integer) stepperYear.getValue());
        String selDate = selYear + "-" + selMonth + "-" + selDay;
        double amount = Double.parseDouble(txtAmount.getText());

        // Add to the database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            
            Statement st = conn.createStatement();
            
            int updatedRows = st.executeUpdate("INSERT INTO Finance VALUES ('" + ttype + "', '" + transaction + "', '" + category + "', '" + selDate + "', '" + amount + "');");
            if(updatedRows > 0) {
                // Clear the textboxes
                txtAmount.setText("");
                txtTransaction.setText("");
                radioSelect('I');
                cmbCategory.setSelectedIndex(0);
                stepperDay.setValue(day);
                stepperMonth.setValue(month);
                stepperYear.setValue(year);
                needRefresh = true;
                needDashRefresh = true;
                // Show message
                MessageDialog transactionAddedSuccess = new MessageDialog(0, this, "Add", "Transaction Added", "Transaction has been added successfully.");
            } else {
                MessageDialog transactionAdditionFailure = new MessageDialog(1, this, "Add", "Transaction Addition Failed", "Failed to add transaction. Please try again.");
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
    }//GEN-LAST:event_sVGAddBtnMouseClicked

    private void sVGAddTransactionsTransactionTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddTransactionsTransactionTextBoxMouseClicked
        txtTransaction.setVisible(true);
        txtTransaction.requestFocusInWindow();
    }//GEN-LAST:event_sVGAddTransactionsTransactionTextBoxMouseClicked

    private void txtTransactionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTransactionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTransactionKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel lblExpense;
    private javax.swing.JLabel lblIncome;
    private main.SVGImage sVGAddBtn;
    private main.SVGImage sVGAddTransactionTypeExpenseRadioBtn;
    private main.SVGImage sVGAddTransactionTypeIncomeRadioBtn;
    private main.SVGImage sVGAddTransactionsAmount;
    private main.SVGImage sVGAddTransactionsAmountTextBox;
    private main.SVGImage sVGAddTransactionsCategory;
    private main.SVGImage sVGAddTransactionsDate;
    private main.SVGImage sVGAddTransactionsForm;
    private main.SVGImage sVGAddTransactionsTransaction;
    private main.SVGImage sVGAddTransactionsTransactionTextBox;
    private main.SVGImage sVGAddTransactionsType;
    private javax.swing.JSpinner stepperDay;
    private javax.swing.JSpinner stepperMonth;
    private javax.swing.JSpinner stepperYear;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtTransaction;
    // End of variables declaration//GEN-END:variables
}
