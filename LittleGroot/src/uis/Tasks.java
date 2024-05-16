/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.SVGImage;

/**
 *
 * @author gamitha
 */
public class Tasks extends javax.swing.JPanel {
    
    Connection conn = null;
    Statement st = null;
    
    
    /**
     * Creates new form Tasks
     */
    public Tasks() {
        initComponents();
        
        // Set SVGs
        sVGTasksCompletedBg.setSvgImage("./svgcomponents/TasksTaskCompletedBg.svg", 610, 89);
        sVGTasksSegCtrlBg.setSvgImage("./svgcomponents/TasksSegCtrlBg.svg", 181, 22);
        sVGTasksSegCtrlUnchecked.setSvgImage("./svgcomponents/TasksSegCtrlUncheckedSelected.svg", 90, 20);
        sVGTasksSegCtrlCompleted.setSvgImage("./svgcomponents/TasksSegCtrlCompleted.svg", 90, 20);
        
        // Set hand cursors
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGTasksSegCtrlCompleted.setCursor(hand);
        
        
        // Get data from database to table
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            Statement st = conn.createStatement();
            String query = "SELECT TStatus, Task, Category, Tdate FROM Tasks WHERE TStatus = false ORDER BY Tdate ASC";

            ResultSet rs = st.executeQuery(query);

            // Get metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // for storing data from database in an array
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    if (columnIndex == 1) {
                        vector.add(rs.getBoolean(columnIndex)); // Handle TStatus as Boolean
                    } else {
                        vector.add(rs.getObject(columnIndex));
                    }
                }
                data.add(vector);
            }

            // Column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Status");
            columnNames.add("Task");
            columnNames.add("Category");
            columnNames.add("Date");

            // Set the data to the table
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // For the first column return Boolean.class
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Only the first column is editable
                }

                @Override
                public void setValueAt(Object aValue, int row, int column) {
                    super.setValueAt(aValue, row, column);
                    if (column == 0) {
                        try {
                            String task = getValueAt(row, 1).toString();
                            PreparedStatement pstmt = conn.prepareStatement("UPDATE Tasks SET TStatus = ? WHERE Task = ?");
                            pstmt.setBoolean(1, (Boolean) aValue);
                            pstmt.setString(2, task);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            tableTasks.setModel(model);
            
            // Set the text of lblTask
            if (data.size() > 0) {
                lblTask.setText(data.get(0).get(1).toString());
                lblDate.setText(data.get(0).get(3).toString());
            }
            
            // Calculate the percentage of completed tasks and the text of lblPercentage
            Statement stTotal = conn.createStatement();
            ResultSet rsTotal = stTotal.executeQuery("SELECT COUNT(*) FROM Tasks");
            Statement stCompleted = conn.createStatement();
            ResultSet rsCompleted = stCompleted.executeQuery("SELECT COUNT(*) FROM Tasks WHERE TStatus = true");
            if (rsTotal.next() && rsCompleted.next()) {
                int total = rsTotal.getInt(1);
                int completed = rsCompleted.getInt(1);
                if (total > 0) {
                    int percentage = (int) ((double) completed / total * 100);
                    lblPercentage.setText(String.format("%d%%", percentage));
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close statement");
                }
            }
        }
        
    }
    
    private void selectSegCtrlButton(String button) {
        // Declare and initialize the map
        Map<String, SVGImage> svgMap = new HashMap<>();
        svgMap.put("TasksSegCtrlUnchecked", sVGTasksSegCtrlUnchecked);
        svgMap.put("TasksSegCtrlCompleted", sVGTasksSegCtrlCompleted);
        
        // Declare and initialize an array of Sidebar buttons
        String[] segCtrlButtons = {"TasksSegCtrlUnchecked", "TasksSegCtrlCompleted"};
        
        // Intialize cursor objects
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        
        // Iterate thorugh the array
        for (String idx : segCtrlButtons) {
            SVGImage svg = svgMap.get(idx); // Create the svg name from the String of the index
            if (idx.equals(button)) {
                // Select button
                svg.setSvgImage("./svgcomponents/" + idx + "Selected.svg", 90, 20);
                svg.setCursor(defaultCursor);
            } else {
                // Unselect other buttons
                svg.setSvgImage("./svgcomponents/" + idx + ".svg", 90, 20);
                svg.setCursor(hand);
            }
        }
        
        // Repaint
        this.revalidate(); // TODO: check if this correct
        this.repaint();
    }
    
    public void filterTable(String filter) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            Statement st = conn.createStatement();

            String query;
            if (filter.equals("TasksSegCtrlCompleted")) {
                query = "SELECT TStatus, Task, Category, Tdate FROM Tasks WHERE TStatus = true ORDER BY Tdate ASC";
            } else {
                query = "SELECT TStatus, Task, Category, Tdate FROM Tasks WHERE TStatus = false ORDER BY Tdate ASC";
            }

            ResultSet rs = st.executeQuery(query);

            // Get metadata
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // for storing data from database in an array
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    if (columnIndex == 1) {
                        vector.add(rs.getBoolean(columnIndex)); // Handle TStatus as Boolean
                    } else {
                        vector.add(rs.getObject(columnIndex));
                    }
                }
                data.add(vector);
            }

            // Column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Status");
            columnNames.add("Task");
            columnNames.add("Category");
            columnNames.add("Date");

            // Set the data to the table
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class; // For the first column return Boolean.class
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Only the first column is editable
                }

                @Override
                public void setValueAt(Object aValue, int row, int column) {
                    super.setValueAt(aValue, row, column);
                    if (column == 0) {
                        try {
                            String task = getValueAt(row, 1).toString();
                            PreparedStatement pstmt = conn.prepareStatement("UPDATE Tasks SET TStatus = ? WHERE Task = ?");
                            pstmt.setBoolean(1, (Boolean) aValue);
                            pstmt.setString(2, task);
                            pstmt.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            tableTasks.setModel(model);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close statement");
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
        tableTasks = new javax.swing.JTable();
        lblPercentage = new javax.swing.JLabel();
        lblTask = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        sVGTasksCompletedBg = new main.SVGImage();
        sVGTasksSegCtrlUnchecked = new main.SVGImage();
        sVGTasksSegCtrlCompleted = new main.SVGImage();
        sVGTasksSegCtrlBg = new main.SVGImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);

        tableTasks.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableTasks);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 181, 600, 277);

        lblPercentage.setFont(new java.awt.Font("SF Pro", 0, 26)); // NOI18N
        lblPercentage.setForeground(new java.awt.Color(0, 0, 0));
        lblPercentage.setText("lblPercentage");
        add(lblPercentage);
        lblPercentage.setBounds(31, 73, 200, 32);

        lblTask.setFont(new java.awt.Font("SF Pro Display", 0, 10)); // NOI18N
        lblTask.setForeground(new java.awt.Color(0, 0, 0));
        lblTask.setText("lblTask");
        lblTask.setToolTipText("");
        add(lblTask);
        lblTask.setBounds(186, 60, 425, 13);

        lblDate.setFont(new java.awt.Font("SF Pro Display", 0, 10)); // NOI18N
        lblDate.setForeground(new java.awt.Color(0, 0, 0));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDate.setText("lblDate");
        lblDate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblDate.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        add(lblDate);
        lblDate.setBounds(514, 40, 97, 13);

        sVGTasksCompletedBg.setForeground(new java.awt.Color(0, 0, 0));
        sVGTasksCompletedBg.setText("sVGTasksCompletedBg");
        add(sVGTasksCompletedBg);
        sVGTasksCompletedBg.setBounds(20, 20, 610, 89);

        sVGTasksSegCtrlUnchecked.setForeground(new java.awt.Color(0, 0, 0));
        sVGTasksSegCtrlUnchecked.setText("sVGTasksSegCtrlUnchecked");
        sVGTasksSegCtrlUnchecked.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGTasksSegCtrlUncheckedMouseClicked(evt);
            }
        });
        add(sVGTasksSegCtrlUnchecked);
        sVGTasksSegCtrlUnchecked.setBounds(21, 140, 90, 20);

        sVGTasksSegCtrlCompleted.setForeground(new java.awt.Color(0, 0, 0));
        sVGTasksSegCtrlCompleted.setText("sVGTasksSegCtrlCompleted");
        sVGTasksSegCtrlCompleted.setToolTipText("");
        sVGTasksSegCtrlCompleted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGTasksSegCtrlCompletedMouseClicked(evt);
            }
        });
        add(sVGTasksSegCtrlCompleted);
        sVGTasksSegCtrlCompleted.setBounds(110, 140, 90, 20);

        sVGTasksSegCtrlBg.setForeground(new java.awt.Color(0, 0, 0));
        sVGTasksSegCtrlBg.setText("sVGTasksSegCtrlBg");
        add(sVGTasksSegCtrlBg);
        sVGTasksSegCtrlBg.setBounds(20, 139, 181, 22);
    }// </editor-fold>//GEN-END:initComponents

    private void sVGTasksSegCtrlCompletedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGTasksSegCtrlCompletedMouseClicked
        // Filter Table
        filterTable("TasksSegCtrlCompleted");

        // Select Button
        selectSegCtrlButton("TasksSegCtrlCompleted");
    }//GEN-LAST:event_sVGTasksSegCtrlCompletedMouseClicked

    private void sVGTasksSegCtrlUncheckedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGTasksSegCtrlUncheckedMouseClicked
        // Filter Table
        filterTable("TasksSegCtrlUnchecked");
        
        // Select Button
        selectSegCtrlButton("TasksSegCtrlUnchecked");
    }//GEN-LAST:event_sVGTasksSegCtrlUncheckedMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblPercentage;
    private javax.swing.JLabel lblTask;
    private main.SVGImage sVGTasksCompletedBg;
    private main.SVGImage sVGTasksSegCtrlBg;
    private main.SVGImage sVGTasksSegCtrlCompleted;
    private main.SVGImage sVGTasksSegCtrlUnchecked;
    private javax.swing.JTable tableTasks;
    // End of variables declaration//GEN-END:variables
}
