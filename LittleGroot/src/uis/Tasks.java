/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
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
        
        // Change background color of the table area
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        
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
            
            // Set the text of lblTask and lblDate
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
    
    public String getTaskText() {
        return lblTask.getText();
    }

    public String getDateText() {
        return lblDate.getText();
    }
    
    public String getPercentageText() {
        return lblPercentage.getText();
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
        Color altRowColor = new Color(251, 251, 251);
        Color headerBorderColor = new Color(242, 242, 242);
        Color headerSepColor = new Color(229, 229, 229);
        Color selectedRowColor = new Color(3, 162, 0);
        Color secondaryTextColor = new Color(128, 128, 128);
        tableTasks = new javax.swing.JTable() {
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

        jScrollPane1.setBorder(null);

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
        tableTasks.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                tableTasksComponentResized(evt);
            }
        });
        // Remove cell borders
        tableTasks.setShowGrid(false);

        // Change header color and add border
        JTableHeader header = tableTasks.getTableHeader();
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

    private void tableTasksComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableTasksComponentResized
        int height = 0;
        for (int row = 0; row < tableTasks.getRowCount(); row++) {
            height += tableTasks.getRowHeight(row);
        }
        jScrollPane1.setPreferredSize(new Dimension(jScrollPane1.getWidth(), height));
        jScrollPane1.revalidate();
    }//GEN-LAST:event_tableTasksComponentResized


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
