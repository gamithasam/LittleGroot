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

/**
 *
 * @author gamitha
 */
public class AddTransactions extends javax.swing.JPanel {

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
        
        // Add focus listeners to textboxes
        addFocusListener(txtAmount);
        
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
        sVGAddTransactionsForm.setSvgImage("./svgcomponents/AddTransactionsForm.svg", 451, 145);
        sVGAddTransactionsType.setSvgImage("./svgcomponents/AddTransactionsType.svg", 431, 36);
        sVGAddTransactionsCategory.setSvgImage("./svgcomponents/AddTransactionsCategory.svg", 431, 36);
        sVGAddTransactionsDate.setSvgImage("./svgcomponents/AddTransactionsDate.svg", 431, 36);
        sVGAddTransactionsAmount.setSvgImage("./svgcomponents/AddTransactionsAmount.svg", 431, 36);
        sVGAddBtn.setSvgImage("./svgcomponents/AddBtn.svg", 39, 22);
        sVGAddTransactionsAmountTextBox.setSvgImage("./svgcomponents/AddTransactionsAmountTextBox.svg", 160, 23);
        sVGAddTransactionTypeIncomeRadioBtn.setSvgImage("./svgcomponents/RadioBtnSelected.svg", 14, 14);
        sVGAddTransactionTypeExpenseRadioBtn.setSvgImage("./svgcomponents/RadioBtn.svg", 14, 14);
        
        // Set Cursors
        Cursor txtCur = new Cursor(Cursor.TEXT_CURSOR);
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGAddTransactionsAmountTextBox.setCursor(txtCur);
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
        cmbCategory.setBounds(301, 54, 160, 21);

        stepperMonth.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperMonth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 31, 1));
        stepperMonth.setBorder(null);
        stepperMonth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperMonthStateChanged(evt);
            }
        });
        add(stepperMonth);
        stepperMonth.setBounds(276, 88, 50, 24);

        stepperDay.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperDay.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));
        stepperDay.setBorder(null);
        stepperDay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperDayStateChanged(evt);
            }
        });
        add(stepperDay);
        stepperDay.setBounds(336, 88, 50, 24);

        stepperYear.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperYear.setModel(new javax.swing.SpinnerNumberModel(2000, 2000, 2100, 1));
        stepperYear.setBorder(null);
        stepperYear.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperYearStateChanged(evt);
            }
        });
        add(stepperYear);
        stepperYear.setBounds(396, 88, 65, 24);

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
        txtAmount.setBounds(307, 128, 147, 17);

        sVGAddBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddBtn.setText("sVGAddBtn");
        add(sVGAddBtn);
        sVGAddBtn.setBounds(432, 170, 39, 22);

        sVGAddTransactionsAmountTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsAmountTextBox.setText("sVGAddTransactionsAmountTextBox");
        sVGAddTransactionsAmountTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddTransactionsAmountTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddTransactionsAmountTextBox);
        sVGAddTransactionsAmountTextBox.setBounds(301, 125, 160, 23);

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
        sVGAddTransactionsCategory.setBounds(30, 46, 431, 36);

        sVGAddTransactionsDate.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsDate.setText("sVGAddTransactionsDate");
        add(sVGAddTransactionsDate);
        sVGAddTransactionsDate.setBounds(30, 82, 431, 36);

        sVGAddTransactionsAmount.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsAmount.setText("sVGAddTransactionsAmount");
        add(sVGAddTransactionsAmount);
        sVGAddTransactionsAmount.setBounds(30, 118, 431, 36);

        sVGAddTransactionsForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddTransactionsForm.setText("sVGAddTransactionsForm");
        add(sVGAddTransactionsForm);
        sVGAddTransactionsForm.setBounds(20, 10, 451, 145);
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
    private main.SVGImage sVGAddTransactionsType;
    private javax.swing.JSpinner stepperDay;
    private javax.swing.JSpinner stepperMonth;
    private javax.swing.JSpinner stepperYear;
    private javax.swing.JTextField txtAmount;
    // End of variables declaration//GEN-END:variables
}
