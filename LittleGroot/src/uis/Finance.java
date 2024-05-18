/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Cursor;
import main.ModelPieChart;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.SVGImage;

/**
 *
 * @author gamitha
 */
public class Finance extends javax.swing.JPanel {

    Connection conn = null;
    Statement st = null;
    
    // Get current year and month
    Calendar cal = Calendar.getInstance();
    int currentYear = cal.get(Calendar.YEAR);
    int currentMonth = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        
    /**
     * Creates new form Finance
     */
    public Finance() {
        initComponents();
        
        // Set SVGs
        sVGSummary.setSvgImage("./svgcomponents/FinanceSummary.svg", 609, 89);
        sVGIncomeGraph.setSvgImage("./svgcomponents/FinanceIncomeGraph.svg", 295, 183);
        sVGExpensesGraph.setSvgImage("./svgcomponents/FinanceExpensesGraph.svg", 295, 183);
        sVGFinanceSegCtrlExpenses.setSvgImage("./svgcomponents/FinanceSegCtrlExpenses.svg", 90, 20);
        sVGFinanceSegCtrlIncome.setSvgImage("./svgcomponents/FinanceSegCtrlIncome.svg", 90, 20);
        sVGFinanceSegCtrlAll.setSvgImage("./svgcomponents/FinanceSegCtrlAllSelected.svg", 90, 20);
        sVGFinanceSegCtrlBg.setSvgImage("./svgcomponents/FinanceSegCtrlBg.svg", 268, 22);
        
        // Set hand cursors
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGFinanceSegCtrlIncome.setCursor(hand);
        sVGFinanceSegCtrlExpenses.setCursor(hand);
        
        // SQL Queries
        String dateCondition = "YEAR(TDate) = " + currentYear + " AND MONTH(TDate) = " + currentMonth;
        String queryExpenses = "SELECT Category, SUM(Price) FROM Finance WHERE TType = 'E' AND " + dateCondition + " GROUP BY Category";
        String queryIncome = "SELECT Category, SUM(Price) FROM Finance WHERE TType = 'I' AND " + dateCondition + " GROUP BY Category";
        String queryTotalIncome = "SELECT SUM(Price) FROM Finance WHERE TType = 'I' AND " + dateCondition;
        String queryTotalExpenses = "SELECT SUM(Price) FROM Finance WHERE TType = 'E' AND " + dateCondition;
        String queryTable = "SELECT Transactions, Category, TDate, Price FROM Finance";
        
        // Establish connection, execute SQL queries and populate pie charts
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            st = conn.createStatement();

            Random rand = new Random();
                        
            // Populate Expenses Pie Chart
            ResultSet rsExpenses = st.executeQuery(queryExpenses);
            while (rsExpenses.next()) {
                String category = rsExpenses.getString(1);
                double price = rsExpenses.getDouble(2);
                Color color = new Color(0, rand.nextFloat(), 0);
                pieChartExpenses.addData(new ModelPieChart(category, price, color));
            }
            // Set summary set
            ResultSet rsTotalIncome = st.executeQuery(queryTotalIncome);
            if (rsTotalIncome.next()) {
                double totalIncome = rsTotalIncome.getDouble(1);
                lblTotalIncome.setText(String.format("$%.2f", totalIncome));
            }

            // Populate Income Pie Chart
            ResultSet rsIncome = st.executeQuery(queryIncome);
            while (rsIncome.next()) {
                String category = rsIncome.getString(1);
                double price = rsIncome.getDouble(2);
                Color color = new Color(0, rand.nextFloat(), 0);
                pieChartIncome.addData(new ModelPieChart(category, price, color));
            }
            // Set summary set
            ResultSet rsTotalExpenses = st.executeQuery(queryTotalExpenses);
            if (rsTotalExpenses.next()) {
                double totalExpenses = rsTotalExpenses.getDouble(1);
                lblTotalExpenses.setText(String.format("$%.2f", totalExpenses));
            }
            
            // Populate table
            ResultSet rsTable = st.executeQuery(queryTable);
            // Get metadata
            ResultSetMetaData rsmd = rsTable.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // for storing data from database in an array
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rsTable.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rsTable.getObject(columnIndex));
                }
                data.add(vector);
            }

            // Column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Transaction");
            columnNames.add("Category");
            columnNames.add("Date");
            columnNames.add("Price ($)");

            // Set the data to the table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            tableFinance.setModel(model);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close connection");
                }
            }
        }
        
    }
    
    private void selectSegCtrlButton(String button) {
        // Declare and initialize the map
        Map<String, SVGImage> svgMap = new HashMap<>();
        svgMap.put("FinanceSegCtrlAll", sVGFinanceSegCtrlAll);
        svgMap.put("FinanceSegCtrlIncome", sVGFinanceSegCtrlIncome);
        svgMap.put("FinanceSegCtrlExpenses", sVGFinanceSegCtrlExpenses);
        
        // Declare and initialize an array of Sidebar buttons
        String[] segCtrlButtons = {"FinanceSegCtrlAll", "FinanceSegCtrlIncome", "FinanceSegCtrlExpenses"};
        
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
            st = conn.createStatement();

            String query;
            if (filter.equals("FinanceSegCtrlIncome")) {
                query = "SELECT Transactions, Category, TDate, Price FROM Finance WHERE TType = 'I'";
            } else if (filter.equals("FinanceSegCtrlIncome")) {
                query = "SELECT Transactions, Category, TDate, Price FROM Finance WHERE TType = 'E'";
            } else {
                query = "SELECT Transactions, Category, TDate, Price FROM Finance";
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
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            // Column names
            Vector<String> columnNames = new Vector<String>();
            columnNames.add("Transaction");
            columnNames.add("Category");
            columnNames.add("Date");
            columnNames.add("Price ($)");

            // Set the data to the table
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            tableFinance.setModel(model);

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close connection");
                }
            }
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

        cmbIncome = new javax.swing.JComboBox<>();
        cmbExpenses = new javax.swing.JComboBox<>();
        pieChartIncome = new main.PieChart();
        pieChartExpenses = new main.PieChart();
        lblTotalIncome = new javax.swing.JLabel();
        lblTotalExpenses = new javax.swing.JLabel();
        sVGSummary = new main.SVGImage();
        sVGIncomeGraph = new main.SVGImage();
        sVGExpensesGraph = new main.SVGImage();
        sVGFinanceSegCtrlAll = new main.SVGImage();
        sVGFinanceSegCtrlIncome = new main.SVGImage();
        sVGFinanceSegCtrlExpenses = new main.SVGImage();
        sVGFinanceSegCtrlBg = new main.SVGImage();
        jScrollPane1 = new main.ScrollPaneWin11();
        tableFinance = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);

        cmbIncome.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "Year" }));
        cmbIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIncomeActionPerformed(evt);
            }
        });
        add(cmbIncome);
        cmbIncome.setBounds(238, 136, 70, 21);

        cmbExpenses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "Year" }));
        cmbExpenses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbExpensesActionPerformed(evt);
            }
        });
        add(cmbExpenses);
        cmbExpenses.setBounds(552, 136, 70, 21);
        add(pieChartIncome);
        pieChartIncome.setBounds(20, 129, 295, 183);
        add(pieChartExpenses);
        pieChartExpenses.setBounds(341, 129, 281, 183);

        lblTotalIncome.setFont(new java.awt.Font("SF Pro Text", 0, 26)); // NOI18N
        lblTotalIncome.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalIncome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalIncome.setText("lblTotalIncome");
        lblTotalIncome.setToolTipText("");
        add(lblTotalIncome);
        lblTotalIncome.setBounds(38, 59, 268, 32);

        lblTotalExpenses.setFont(new java.awt.Font("SF Pro Text", 0, 26)); // NOI18N
        lblTotalExpenses.setForeground(new java.awt.Color(0, 0, 0));
        lblTotalExpenses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalExpenses.setText("lblTotalExpenses");
        lblTotalExpenses.setToolTipText("");
        add(lblTotalExpenses);
        lblTotalExpenses.setBounds(344, 59, 268, 32);

        sVGSummary.setForeground(new java.awt.Color(0, 0, 0));
        sVGSummary.setText("sVGSummary");
        add(sVGSummary);
        sVGSummary.setBounds(20, 20, 609, 89);

        sVGIncomeGraph.setForeground(new java.awt.Color(0, 0, 0));
        sVGIncomeGraph.setText("sVGIncomeGraph");
        add(sVGIncomeGraph);
        sVGIncomeGraph.setBounds(20, 129, 295, 183);

        sVGExpensesGraph.setForeground(new java.awt.Color(0, 0, 0));
        sVGExpensesGraph.setText("sVGExpensesGraph");
        add(sVGExpensesGraph);
        sVGExpensesGraph.setBounds(334, 129, 295, 183);

        sVGFinanceSegCtrlAll.setForeground(new java.awt.Color(0, 0, 0));
        sVGFinanceSegCtrlAll.setText("sVGSegCtrlAll");
        sVGFinanceSegCtrlAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGFinanceSegCtrlAllMouseClicked(evt);
            }
        });
        add(sVGFinanceSegCtrlAll);
        sVGFinanceSegCtrlAll.setBounds(20, 343, 90, 20);

        sVGFinanceSegCtrlIncome.setForeground(new java.awt.Color(0, 0, 0));
        sVGFinanceSegCtrlIncome.setText("sVGSegCtrlIncome");
        sVGFinanceSegCtrlIncome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGFinanceSegCtrlIncomeMouseClicked(evt);
            }
        });
        add(sVGFinanceSegCtrlIncome);
        sVGFinanceSegCtrlIncome.setBounds(109, 343, 90, 20);

        sVGFinanceSegCtrlExpenses.setForeground(new java.awt.Color(0, 0, 0));
        sVGFinanceSegCtrlExpenses.setText("sVGSegCtrlExpenses");
        sVGFinanceSegCtrlExpenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGFinanceSegCtrlExpensesMouseClicked(evt);
            }
        });
        add(sVGFinanceSegCtrlExpenses);
        sVGFinanceSegCtrlExpenses.setBounds(198, 343, 90, 20);

        sVGFinanceSegCtrlBg.setForeground(new java.awt.Color(0, 0, 0));
        sVGFinanceSegCtrlBg.setText("sVGSegCtrlBg");
        add(sVGFinanceSegCtrlBg);
        sVGFinanceSegCtrlBg.setBounds(19, 342, 268, 22);

        tableFinance.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableFinance);

        add(jScrollPane1);
        jScrollPane1.setBounds(20, 370, 600, 100);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIncomeActionPerformed
        // Clear existing data
        pieChartIncome.clearData();

        // Get selected value
        String selectedValue = cmbIncome.getSelectedItem().toString();

        // Modify SQL queries based on selected value
        String dateCondition = selectedValue.equals("Year") ? "YEAR(TDate) = " + currentYear : "YEAR(TDate) = " + currentYear + " AND MONTH(TDate) = " + currentMonth;
        String queryIncome = "SELECT Category, SUM(Price) FROM Finance WHERE TType = 'I' AND " + dateCondition + " GROUP BY Category";
        String queryTotalIncome = "SELECT SUM(Price) FROM Finance WHERE TType = 'I' AND " + dateCondition;

        // Establish connection, fetch data and update charts
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            Statement st = conn.createStatement();

            Random rand = new Random();

            ResultSet rsIncome = st.executeQuery(queryIncome);
            while (rsIncome.next()) {
                String category = rsIncome.getString(1);
                double price = rsIncome.getDouble(2);
                Color color = new Color(0, rand.nextFloat(), 0);
                pieChartIncome.addData(new ModelPieChart(category, price, color));
            }
            
            // Set summary set
            ResultSet rsTotalIncome = st.executeQuery(queryTotalIncome);
            if (rsTotalIncome.next()) {
                double totalIncome = rsTotalIncome.getDouble(1);
                lblTotalIncome.setText(String.format("$%.2f", totalIncome));
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close connection");
                }
            }
        }
    }//GEN-LAST:event_cmbIncomeActionPerformed

    private void cmbExpensesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbExpensesActionPerformed
        // Clear existing data
        pieChartExpenses.clearData();

        // Get selected value
        String selectedValue = cmbExpenses.getSelectedItem().toString();

        // Modify SQL queries based on selected value
        String dateCondition = selectedValue.equals("Year") ? "YEAR(TDate) = " + currentYear : "YEAR(TDate) = " + currentYear + " AND MONTH(TDate) = " + currentMonth;
        String queryExpenses = "SELECT Category, SUM(Price) FROM Finance WHERE TType = 'E' AND " + dateCondition + " GROUP BY Category";
        String queryTotalExpenses = "SELECT SUM(Price) FROM Finance WHERE TType = 'E' AND " + dateCondition;
        
        // Establish connection, fetch data and update charts
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            st = conn.createStatement();

            Random rand = new Random();
            
            ResultSet rsExpenses = st.executeQuery(queryExpenses);
            while (rsExpenses.next()) {
                String category = rsExpenses.getString(1);
                double price = rsExpenses.getDouble(2);
                Color color = new Color(0, rand.nextFloat(), 0);
                pieChartExpenses.addData(new ModelPieChart(category, price, color));
            }
            
            // Set summary set
            ResultSet rsTotalExpenses = st.executeQuery(queryTotalExpenses);
            if (rsTotalExpenses.next()) {
                double totalExpenses = rsTotalExpenses.getDouble(1);
                lblTotalExpenses.setText(String.format("$%.2f", totalExpenses));
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Failed to close connection");
                }
            }
        }
    }//GEN-LAST:event_cmbExpensesActionPerformed

    private void sVGFinanceSegCtrlIncomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGFinanceSegCtrlIncomeMouseClicked
        // Filter table
        filterTable("FinanceSegCtrlIncome");

        // Select Button
        selectSegCtrlButton("FinanceSegCtrlIncome");
    }//GEN-LAST:event_sVGFinanceSegCtrlIncomeMouseClicked

    private void sVGFinanceSegCtrlAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGFinanceSegCtrlAllMouseClicked
        // Filter table
        filterTable("FinanceSegCtrlAll");
        
        // Select Button
        selectSegCtrlButton("FinanceSegCtrlAll");
    }//GEN-LAST:event_sVGFinanceSegCtrlAllMouseClicked

    private void sVGFinanceSegCtrlExpensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGFinanceSegCtrlExpensesMouseClicked
        // Filter table
        filterTable("FinanceSegCtrlExpenses");

        // Select Button
        selectSegCtrlButton("FinanceSegCtrlExpenses");
    }//GEN-LAST:event_sVGFinanceSegCtrlExpensesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbExpenses;
    private javax.swing.JComboBox<String> cmbIncome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalExpenses;
    private javax.swing.JLabel lblTotalIncome;
    private main.PieChart pieChartExpenses;
    private main.PieChart pieChartIncome;
    private main.SVGImage sVGExpensesGraph;
    private main.SVGImage sVGFinanceSegCtrlAll;
    private main.SVGImage sVGFinanceSegCtrlBg;
    private main.SVGImage sVGFinanceSegCtrlExpenses;
    private main.SVGImage sVGFinanceSegCtrlIncome;
    private main.SVGImage sVGIncomeGraph;
    private main.SVGImage sVGSummary;
    private javax.swing.JTable tableFinance;
    // End of variables declaration//GEN-END:variables
}
