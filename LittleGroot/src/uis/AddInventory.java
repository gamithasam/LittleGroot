/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Cursor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

/**
 *
 * @author gamitha
 */

class InventoryDB {
    public String item;
    public String itemID;
    public String cat;
    public String unit;

    public InventoryDB(String item, String itemID, String cat, String unit) {
        this.item = item;
        this.itemID = itemID;
        this.cat = cat;
        this.unit = unit;
    }
}

public class AddInventory extends javax.swing.JPanel {
    public boolean needRefresh = false;
    Map<String, InventoryDB> inventoryMap = new HashMap<>();
    /**
     * Creates new form AddInventory
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
    
    private void setLblUnit(String item) {
        InventoryDB itemIns = inventoryMap.get(item);
        if (itemIns != null) {
            lblUnit.setVisible(true);
            lblUnit.setText(itemIns.unit);
        }
    }
    
    public AddInventory() {
        initComponents();
        
        // Set visibilities off
        txtNewItem.setVisible(false);
        txtNewItemID.setVisible(false);
        txtNewUnit.setVisible(false);
        
        // Add focus listeners to textboxes
        addFocusListener(txtNewItem);
        addFocusListener(txtNewItemID);
        addFocusListener(txtNewUnit);
        
        // Set SVGs
        sVGUpdateBtn.setSvgImage("./svgcomponents/UpdateBtn.svg", 59, 22);
        sVGAddInventoryForm.setSvgImage("./svgcomponents/AddInventoryForm.svg", 451, 72);
        sVGAddInventoryItem.setSvgImage("./svgcomponents/AddInventoryItem.svg", 431, 36);
        sVGAddInventoryQty.setSvgImage("./svgcomponents/AddInventoryQty.svg", 431, 36);
        
        sVGAddBtn.setSvgImage("./svgcomponents/AddBtn.svg", 39, 22);
        sVGAddInventoryNewForm.setSvgImage("./svgcomponents/AddInventoryNewForm.svg", 451, 147);
        sVGAddInventoryNewItem.setSvgImage("./svgcomponents/AddInventoryNewItem.svg", 431, 37);
        sVGAddInventoryNewItemTextBox.setSvgImage("./svgcomponents/AddInventoryNewItemTextBox.svg", 160, 23);
        sVGAddInventoryNewItemID.setSvgImage("./svgcomponents/AddInventoryNewItemID.svg", 431, 37);
        sVGAddInventoryNewItemIDTextBox.setSvgImage("./svgcomponents/AddInventoryNewItemIDTextBox.svg", 160, 23);
        sVGAddInventoryNewUnit.setSvgImage("./svgcomponents/AddInventoryNewUnit.svg", 431, 37);
        sVGAddInventoryNewUnitTextBox.setSvgImage("./svgcomponents/AddInventoryNewUnitTextBox.svg", 160, 23);
        sVGAddInventoryNewCategory.setSvgImage("./svgcomponents/AddInventoryNewCategory.svg", 431, 37);
        
        // Set Cursors
        Cursor txtCur = new Cursor(Cursor.TEXT_CURSOR);
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGAddInventoryNewItemTextBox.setCursor(txtCur);
        sVGAddInventoryNewItemIDTextBox.setCursor(txtCur);
        sVGAddInventoryNewUnitTextBox.setCursor(txtCur);
        sVGUpdateBtn.setCursor(hand);
        sVGAddBtn.setCursor(hand);
        
        // Create objects
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            
            st = conn.createStatement();
            
            rs = st.executeQuery("SELECT Item, Item_id, Category, Unit FROM Inventory");

            while (rs.next()) {
                String item = rs.getString("Item");
                String itemID = rs.getString("Item_id");
                String cat = rs.getString("Category");
                String unit = rs.getString("Unit");

                InventoryDB inventory = new InventoryDB(item, itemID, cat, unit);
                inventoryMap.put(item, inventory);
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
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    MessageDialog closeStatementFailure = new MessageDialog(1, this, "Database", "Statement Closure Failed", "Failed to close the database statement.");
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    MessageDialog rsConnectionFailure = new MessageDialog(1, this, "Database", "Resultset Closure Failed", "Failed to close the database result set.");
                }
            }
        }
        
        // Populate ComboBoxes
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String itemName : inventoryMap.keySet()) {
            model.addElement(itemName);
        }
        cmbItem.setModel(model);
        
        DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<>();
        for (InventoryDB inventoryItem : inventoryMap.values()) {
            if (categoryModel.getIndexOf(inventoryItem.cat) == -1) {
                categoryModel.addElement(inventoryItem.cat);
            }
        }
        cmbNewCategory.setModel(categoryModel);
        
        // Set text of lblUnit
        setLblUnit((String)cmbItem.getSelectedItem());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbItem = new javax.swing.JComboBox<>();
        cmbNewCategory = new javax.swing.JComboBox<>();
        sVGAddInventoryItem = new main.SVGImage();
        panelQty = new javax.swing.JPanel();
        stepperQty = new javax.swing.JSpinner();
        lblUnit = new javax.swing.JLabel();
        sVGAddInventoryQty = new main.SVGImage();
        sVGAddInventoryForm = new main.SVGImage();
        sVGUpdateBtn = new main.SVGImage();
        lblNewItem = new javax.swing.JLabel();
        txtNewUnit = new javax.swing.JTextField();
        txtNewItemID = new javax.swing.JTextField();
        txtNewItem = new javax.swing.JTextField();
        sVGAddInventoryNewItemTextBox = new main.SVGImage();
        sVGAddInventoryNewItemIDTextBox = new main.SVGImage();
        sVGAddInventoryNewUnitTextBox = new main.SVGImage();
        sVGAddInventoryNewItem = new main.SVGImage();
        sVGAddInventoryNewItemID = new main.SVGImage();
        sVGAddInventoryNewUnit = new main.SVGImage();
        sVGAddInventoryNewCategory = new main.SVGImage();
        sVGAddInventoryNewForm = new main.SVGImage();
        sVGAddBtn = new main.SVGImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 426));
        setLayout(null);

        cmbItem.setBackground(new java.awt.Color(255, 255, 255));
        cmbItem.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        cmbItem.setForeground(new java.awt.Color(0, 0, 0));
        cmbItem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tomato Seeds" }));
        cmbItem.setBorder(null);
        cmbItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbItemMouseClicked(evt);
            }
        });
        cmbItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemActionPerformed(evt);
            }
        });
        add(cmbItem);
        cmbItem.setBounds(301, 18, 160, 21);

        cmbNewCategory.setBackground(new java.awt.Color(255, 255, 255));
        cmbNewCategory.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        cmbNewCategory.setForeground(new java.awt.Color(0, 0, 0));
        cmbNewCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seeds" }));
        cmbNewCategory.setBorder(null);
        cmbNewCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNewCategoryActionPerformed(evt);
            }
        });
        add(cmbNewCategory);
        cmbNewCategory.setBounds(301, 256, 160, 21);

        sVGAddInventoryItem.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryItem.setText("sVGAddInventoryItem");
        sVGAddInventoryItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddInventoryItemMouseClicked(evt);
            }
        });
        add(sVGAddInventoryItem);
        sVGAddInventoryItem.setBounds(30, 10, 431, 36);

        panelQty.setForeground(null);
        panelQty.setOpaque(false);
        panelQty.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING, 6, 5));

        stepperQty.setFont(new java.awt.Font("SF Pro", 0, 13)); // NOI18N
        stepperQty.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        stepperQty.setBorder(null);
        stepperQty.setPreferredSize(new java.awt.Dimension(70, 24));
        stepperQty.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stepperQtyStateChanged(evt);
            }
        });
        panelQty.add(stepperQty);

        lblUnit.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        lblUnit.setForeground(new java.awt.Color(0, 0, 0));
        lblUnit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUnit.setText("lblUnit");
        panelQty.add(lblUnit);

        add(panelQty);
        panelQty.setBounds(210, 46, 250, 36);

        sVGAddInventoryQty.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryQty.setText("sVGAddInventoryQty");
        add(sVGAddInventoryQty);
        sVGAddInventoryQty.setBounds(30, 46, 431, 36);

        sVGAddInventoryForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryForm.setText("sVGAddInventoryForm");
        add(sVGAddInventoryForm);
        sVGAddInventoryForm.setBounds(20, 10, 451, 72);

        sVGUpdateBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGUpdateBtn.setText("sVGUpdateBtn");
        sVGUpdateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGUpdateBtnMouseClicked(evt);
            }
        });
        add(sVGUpdateBtn);
        sVGUpdateBtn.setBounds(412, 97, 59, 22);

        lblNewItem.setFont(new java.awt.Font("SF Pro Text", 1, 13)); // NOI18N
        lblNewItem.setForeground(new java.awt.Color(0, 0, 0));
        lblNewItem.setText("New Item");
        add(lblNewItem);
        lblNewItem.setBounds(20, 149, 70, 16);

        txtNewUnit.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtNewUnit.setForeground(new java.awt.Color(0, 0, 0));
        txtNewUnit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNewUnit.setBorder(null);
        txtNewUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewUnitKeyTyped(evt);
            }
        });
        add(txtNewUnit);
        txtNewUnit.setBounds(307, 295, 147, 17);

        txtNewItemID.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtNewItemID.setForeground(new java.awt.Color(0, 0, 0));
        txtNewItemID.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNewItemID.setBorder(null);
        txtNewItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewItemIDKeyTyped(evt);
            }
        });
        add(txtNewItemID);
        txtNewItemID.setBounds(307, 222, 147, 17);

        txtNewItem.setFont(new java.awt.Font("SF Pro Display", 0, 13)); // NOI18N
        txtNewItem.setForeground(new java.awt.Color(0, 0, 0));
        txtNewItem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNewItem.setBorder(null);
        txtNewItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewItemKeyTyped(evt);
            }
        });
        add(txtNewItem);
        txtNewItem.setBounds(307, 185, 147, 17);

        sVGAddInventoryNewItemTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewItemTextBox.setText("sVGAddInventoryNewItemTextBox");
        sVGAddInventoryNewItemTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddInventoryNewItemTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddInventoryNewItemTextBox);
        sVGAddInventoryNewItemTextBox.setBounds(301, 182, 160, 23);

        sVGAddInventoryNewItemIDTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewItemIDTextBox.setText("sVGAddInventoryNewItemIDTextBox");
        sVGAddInventoryNewItemIDTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddInventoryNewItemIDTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddInventoryNewItemIDTextBox);
        sVGAddInventoryNewItemIDTextBox.setBounds(301, 219, 160, 23);

        sVGAddInventoryNewUnitTextBox.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewUnitTextBox.setText("sVGAddInventoryNewUnitTextBox");
        sVGAddInventoryNewUnitTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddInventoryNewUnitTextBoxMouseClicked(evt);
            }
        });
        add(sVGAddInventoryNewUnitTextBox);
        sVGAddInventoryNewUnitTextBox.setBounds(301, 292, 160, 23);

        sVGAddInventoryNewItem.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewItem.setText("sVGAddInventoryNewItem");
        add(sVGAddInventoryNewItem);
        sVGAddInventoryNewItem.setBounds(30, 175, 431, 37);

        sVGAddInventoryNewItemID.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewItemID.setText("sVGAddInventoryNewItemID");
        add(sVGAddInventoryNewItemID);
        sVGAddInventoryNewItemID.setBounds(30, 212, 431, 37);

        sVGAddInventoryNewUnit.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewUnit.setText("sVGAddInventoryNewUnit");
        add(sVGAddInventoryNewUnit);
        sVGAddInventoryNewUnit.setBounds(30, 285, 431, 37);

        sVGAddInventoryNewCategory.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewCategory.setText("sVGAddInventoryNewCategory");
        add(sVGAddInventoryNewCategory);
        sVGAddInventoryNewCategory.setBounds(30, 249, 431, 37);

        sVGAddInventoryNewForm.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddInventoryNewForm.setText("sVGAddInventoryNewForm");
        add(sVGAddInventoryNewForm);
        sVGAddInventoryNewForm.setBounds(20, 175, 451, 147);

        sVGAddBtn.setForeground(new java.awt.Color(0, 0, 0));
        sVGAddBtn.setText("sVGAddBtn");
        sVGAddBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGAddBtnMouseClicked(evt);
            }
        });
        add(sVGAddBtn);
        sVGAddBtn.setBounds(432, 337, 39, 22);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemActionPerformed
        setLblUnit((String) cmbItem.getSelectedItem());
    }//GEN-LAST:event_cmbItemActionPerformed

    private void stepperQtyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stepperQtyStateChanged
        
    }//GEN-LAST:event_stepperQtyStateChanged

    private void sVGUpdateBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGUpdateBtnMouseClicked
        String item = (String) cmbItem.getSelectedItem();
        int qty = (int) stepperQty.getValue();
        
        // Update on the database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            
            Statement st = conn.createStatement();
            
            int updatedRows = st.executeUpdate("UPDATE Inventory SET Availability = '" + qty + "' WHERE Item = '" + item + "';");
            
            if(updatedRows > 0) {
                // Clear the inputs
                cmbItem.setSelectedIndex(0);
                stepperQty.setValue(0);
                needRefresh = true;
                // Show message
                MessageDialog inventoryUpdatedSuccess = new MessageDialog(0, this, "Add", "Inventory Updated", "Inventory has been updated successfully.");
            } else {
                MessageDialog inventoryUpdateFailure = new MessageDialog(1, this, "Add", "Inventory Update Failed", "Failed to update the inventory. Please try again.");
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
    }//GEN-LAST:event_sVGUpdateBtnMouseClicked

    private void sVGAddInventoryItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddInventoryItemMouseClicked
        
    }//GEN-LAST:event_sVGAddInventoryItemMouseClicked

    private void txtNewItemKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewItemKeyTyped

    }//GEN-LAST:event_txtNewItemKeyTyped

    private void txtNewItemIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewItemIDKeyTyped

    }//GEN-LAST:event_txtNewItemIDKeyTyped

    private void txtNewUnitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewUnitKeyTyped

    }//GEN-LAST:event_txtNewUnitKeyTyped

    private void sVGAddInventoryNewItemTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddInventoryNewItemTextBoxMouseClicked
        txtNewItem.setVisible(true);
        txtNewItem.requestFocusInWindow();
    }//GEN-LAST:event_sVGAddInventoryNewItemTextBoxMouseClicked

    private void sVGAddInventoryNewItemIDTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddInventoryNewItemIDTextBoxMouseClicked
        txtNewItemID.setVisible(true);
        txtNewItemID.requestFocusInWindow();
    }//GEN-LAST:event_sVGAddInventoryNewItemIDTextBoxMouseClicked

    private void sVGAddInventoryNewUnitTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddInventoryNewUnitTextBoxMouseClicked
        txtNewUnit.setVisible(true);
        txtNewUnit.requestFocusInWindow();
    }//GEN-LAST:event_sVGAddInventoryNewUnitTextBoxMouseClicked

    private void sVGAddBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGAddBtnMouseClicked
        String item = txtNewItem.getText();
        String itemID = txtNewItemID.getText();
        String unit = txtNewUnit.getText();
        String cat = (String) cmbNewCategory.getSelectedItem();
        
        // Add to the database
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            
            Statement st = conn.createStatement();
            
            int updatedRows = st.executeUpdate("INSERT INTO Inventory VALUES ('"+ item + "', '" + itemID + "', '" + cat + "', 0, '" + unit + "');");

            if(updatedRows > 0) {
                // Clear the textboxes
                txtNewItem.setText("");
                txtNewItemID.setText("");
                txtNewUnit.setText("");
                cmbNewCategory.setSelectedIndex(0);
                
                // Create an object
                InventoryDB inventoryAdd = new InventoryDB(item, itemID, cat, unit);
                inventoryMap.put(item, inventoryAdd);
                
                needRefresh = true;
                
                // Show message
                MessageDialog inventoryItemAddedSuccess = new MessageDialog(0, this, "Add", "New Inventory Item Added", "New inventory item has been added successfully.");
            } else {
                MessageDialog inventoryItemAdditionFailure = new MessageDialog(1, this, "Add", "Inventory Item Addition Failed", "Failed to add new inventory item. Please try again.");
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

    private void cmbNewCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNewCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNewCategoryActionPerformed

    private void cmbItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbItemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbItemMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbItem;
    private javax.swing.JComboBox<String> cmbNewCategory;
    private javax.swing.JLabel lblNewItem;
    private javax.swing.JLabel lblUnit;
    private javax.swing.JPanel panelQty;
    private main.SVGImage sVGAddBtn;
    private main.SVGImage sVGAddInventoryForm;
    private main.SVGImage sVGAddInventoryItem;
    private main.SVGImage sVGAddInventoryNewCategory;
    private main.SVGImage sVGAddInventoryNewForm;
    private main.SVGImage sVGAddInventoryNewItem;
    private main.SVGImage sVGAddInventoryNewItemID;
    private main.SVGImage sVGAddInventoryNewItemIDTextBox;
    private main.SVGImage sVGAddInventoryNewItemTextBox;
    private main.SVGImage sVGAddInventoryNewUnit;
    private main.SVGImage sVGAddInventoryNewUnitTextBox;
    private main.SVGImage sVGAddInventoryQty;
    private main.SVGImage sVGUpdateBtn;
    private javax.swing.JSpinner stepperQty;
    private javax.swing.JTextField txtNewItem;
    private javax.swing.JTextField txtNewItemID;
    private javax.swing.JTextField txtNewUnit;
    // End of variables declaration//GEN-END:variables
}
