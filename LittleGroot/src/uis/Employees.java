/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Component;
import java.sql.*;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author gamitha
 */
public class Employees extends javax.swing.JPanel {
    /**
     * Creates new form Employees
     */
    public Employees() {
        initComponents();
        
        // Change background color of the table area
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        
        //Get data from database to table
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            Statement st = conn.createStatement();
            String query = "SELECT EmpID, FName, LName, EmpRole, Phone, Email, Address FROM Employee ORDER BY EmpID ASC";
            
            ResultSet rs = st.executeQuery(query);

            // Get metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // for storing data from database in an array
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            // Column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Employee ID");
            columnNames.add("First Name");
            columnNames.add("Last Name");
            columnNames.add("Role");
            columnNames.add("Phone Number");
            columnNames.add("Email");
            columnNames.add("Address");

            // Set the data to the table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            tableEmployees.setModel(model);
            
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
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new main.ScrollPaneWin11();
        Color altRowColor = new Color(251, 251, 251);
        Color headerBorderColor = new Color(242, 242, 242);
        Color headerSepColor = new Color(229, 229, 229);
        Color selectedRowColor = new Color(3, 162, 0);
        Color secondaryTextColor = new Color(128, 128, 128);
        tableEmployees = new javax.swing.JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // Change background color
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : altRowColor);
                    if (column == 0) {
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setForeground(secondaryTextColor);
                    }
                } else {
                    c.setBackground(selectedRowColor);
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        };

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(450, 438));
        jScrollPane1.setSize(new java.awt.Dimension(606, 438));

        tableEmployees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        // Remove cell borders
        tableEmployees.setShowGrid(false);

        // Change header color and add border
        JTableHeader header = tableEmployees.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.WHITE);
                // Set text colors
                if (column == 0) {
                    c.setForeground(Color.BLACK);
                } else {
                    c.setForeground(secondaryTextColor);
                }
                // Add border to the top and bottom
                ((JComponent) c).setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, headerBorderColor));
                // Add separator between header cells
                if (column < table.getColumnCount() - 1) {
                    ((JComponent) c).setBorder(BorderFactory.createCompoundBorder(
                        ((JComponent) c).getBorder(),
                        BorderFactory.createCompoundBorder(
                            BorderFactory.createEmptyBorder(3, 0, 3, 0),
                            BorderFactory.createMatteBorder(0, 0, 0, 1, headerSepColor)
                        )
                    ));
                }
                // Add padding to the text
                ((JComponent) c).setBorder(BorderFactory.createCompoundBorder(
                    ((JComponent) c).getBorder(),
                    BorderFactory.createEmptyBorder(0, 7, 0, 7)
                ));
                return c;
            }
        });
        jScrollPane1.setViewportView(tableEmployees);

        add(jScrollPane1);
        jScrollPane1.setBounds(19, 20, 606, 438);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEmployees;
    // End of variables declaration//GEN-END:variables
}
