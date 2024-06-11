/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Image;
import main.ModelChart;
import java.sql.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author gamitha
 */
public class AllFields extends javax.swing.JPanel {
    Cursor hand = new Cursor(Cursor.HAND_CURSOR);
    static List<ModelChart> chartData = new ArrayList<>();
    /**
     * Creates new form AllFields
     */  
    public AllFields() {
        initComponents();
        
        // Set SVGs
        sVGSalesGraph.setSvgImage("./svgcomponents/SalesGraph.svg", 604, 294);

        // Set PNGs
        addImageToLabel();
        
        // Set hand cursors
        pngTomato.setCursor(hand);
        pngCorn.setCursor(hand);
        pngApple.setCursor(hand);
        pngCarrot.setCursor(hand);
        pngOrange.setCursor(hand);
        pngMango.setCursor(hand);
        
        // Set data for the graph
        
        salesChart.addLegend("Tomato", new Color(255, 99, 71));
        salesChart.addLegend("Corn", new Color(255, 255, 0));
        salesChart.addLegend("Apple", new Color(0, 255, 0));
        salesChart.addLegend("Carrot", new Color(255, 165, 0));
        salesChart.addLegend("Orange", new Color(255, 140, 0));
        salesChart.addLegend("Mango", new Color(255, 130, 67));
        
        // Establish connection, execute SQL queries and populate the chart
        Connection conn = null;
        Statement st = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            st = conn.createStatement();
                        
            // Get the current date and the dates for the previous two months
            LocalDate currentDate = LocalDate.now();
            LocalDate oneMonthAgo = currentDate.minusMonths(1);
            LocalDate twoMonthsAgo = currentDate.minusMonths(2);

            // Format the dates to match the format in your database
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String currentDateStr = currentDate.format(formatter);
            String oneMonthAgoStr = oneMonthAgo.format(formatter);
            String twoMonthsAgoStr = twoMonthsAgo.format(formatter);

            // Create an SQL query to select the relevant data
            String sql = "SELECT MONTH(TDate) as Month, Category, SUM(Price) as TotalPrice FROM Finance WHERE TType = 'I' AND Category IN ('Tomato', 'Corn', 'Apple', 'Carrot', 'Orange', 'Mango') AND TDate BETWEEN '" + twoMonthsAgoStr + "' AND '" + currentDateStr + "' GROUP BY Month, Category";
            // Execute the query and get the result
            ResultSet rs = st.executeQuery(sql);

            // Create a map to store the total price for each category for each month
            Map<YearMonth, Map<String, Double>> totalByMonthAndCategory = new HashMap<>();

            // Loop through the result and add the total price to the map
            while (rs.next()) {
                int month = rs.getInt("Month");
                String category = rs.getString("Category");
                double totalPrice = rs.getDouble("TotalPrice");

                YearMonth yearMonth = YearMonth.of(currentDate.getYear(), month);

                if (!totalByMonthAndCategory.containsKey(yearMonth)) {
                    totalByMonthAndCategory.put(yearMonth, new HashMap<>());
                }

                totalByMonthAndCategory.get(yearMonth).put(category, totalPrice);
            }
            
            InsideField.getDataFromAllFields(totalByMonthAndCategory);

            // Define the order of categories
            List<String> categories = Arrays.asList("Tomato", "Corn", "Apple", "Carrot", "Orange", "Mango");

            // Loop through the map and add the data to the chart
            for (Map.Entry<YearMonth, Map<String, Double>> entry : totalByMonthAndCategory.entrySet()) {
                YearMonth yearMonth = entry.getKey();
                Map<String, Double> totalsByCategory = entry.getValue();

                double[] totalsArray = categories.stream()
                    .mapToDouble(category -> totalsByCategory.getOrDefault(category, 0.0))
                    .toArray();

                // Get the month name
                String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

                salesChart.addData(new ModelChart(monthName, totalsArray));
                
                // Sends data to dashboard
                chartData.add(new ModelChart(monthName, totalsArray));
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
    }
    
    public static List<ModelChart> getChartDate() {
        return chartData;
    }
    
    private void addImageToLabel() {
        //pngTomato
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Tomato.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngTomato.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //pngCorn
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Corn.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngCorn.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //pngApple
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Apple.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngApple.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //pngCarrot
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Carrot.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngCarrot.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //pngOrange
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Orange.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngOrange.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //pngMango
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Mango.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(188, 112, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngMango.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
                
    private void lblBackBtnMouseClicked(java.awt.event.MouseEvent evt, JPanel insideField, JLabel lblBackBtn) {                                      
        insideField.setVisible(false);
        this.setPreferredSize(new Dimension(649, 682));
        remove(lblBackBtn);
        // Set the scroll bar to the default position
        setScrollDef();
    }   
    
    private void selectField(String field) {
        InsideField.clickField = field;
        
        JPanel insideField = new uis.InsideField();
        add(insideField);
        setComponentZOrder(insideField, 0);
        insideField.setBounds(0, 0, 649, 834);
        
        JLabel lblBackBtn = new javax.swing.JLabel();
        lblBackBtn.setFont(new java.awt.Font("SF Pro Text", 0, 15)); // NOI18N
        lblBackBtn.setForeground(new java.awt.Color(0, 0, 0));
        lblBackBtn.setText("􀯶");
        lblBackBtn.setCursor(hand);
        lblBackBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackBtnMouseClicked(evt, insideField, lblBackBtn);
            }
        });
        add(lblBackBtn);
        setComponentZOrder(lblBackBtn, 0);
        lblBackBtn.setBounds(25, 20, 11, 20);
        
        this.setPreferredSize(new Dimension(649, 834));
        this.revalidate();
        this.repaint();
        
        // Set the scroll bar to the default position
        setScrollDef();
    }
    
    private void setScrollDef() {
        // Get the parent JScrollPane
        JScrollPane jScrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, this);
        if (jScrollPane != null) {
            // Set the scroll bar to the default position
            jScrollPane.getVerticalScrollBar().setValue(0);
            jScrollPane.getHorizontalScrollBar().setValue(0);
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

        salesChart = new main.Chart();
        sVGSalesGraph = new main.SVGImage();
        pngTomato = new javax.swing.JLabel();
        pngCorn = new javax.swing.JLabel();
        pngApple = new javax.swing.JLabel();
        lblTomato = new javax.swing.JLabel();
        lblTomatoAcre = new javax.swing.JLabel();
        lblCorn = new javax.swing.JLabel();
        lblCornAcre = new javax.swing.JLabel();
        lblAppleAcre = new javax.swing.JLabel();
        lblApple = new javax.swing.JLabel();
        lblCarrotAcre = new javax.swing.JLabel();
        lblCarrot = new javax.swing.JLabel();
        lblOrange = new javax.swing.JLabel();
        lblOrangeAcre = new javax.swing.JLabel();
        lblMango = new javax.swing.JLabel();
        lblMangoAcre = new javax.swing.JLabel();
        pngCarrot = new javax.swing.JLabel();
        pngOrange = new javax.swing.JLabel();
        pngMango = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setPreferredSize(new java.awt.Dimension(649, 478));
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);
        add(salesChart);
        salesChart.setBounds(32, 57, 590, 250);

        sVGSalesGraph.setForeground(new java.awt.Color(0, 0, 0));
        sVGSalesGraph.setText("sVGSalesGraph");
        add(sVGSalesGraph);
        sVGSalesGraph.setBounds(25, 20, 604, 294);

        pngTomato.setForeground(new java.awt.Color(0, 0, 0));
        pngTomato.setText("pngTomato");
        pngTomato.setPreferredSize(new java.awt.Dimension(188, 112));
        pngTomato.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngTomatoMouseClicked(evt);
            }
        });
        add(pngTomato);
        pngTomato.setBounds(25, 334, 188, 112);

        pngCorn.setForeground(new java.awt.Color(0, 0, 0));
        pngCorn.setText("pngCorn");
        pngCorn.setToolTipText("");
        pngCorn.setPreferredSize(new java.awt.Dimension(188, 112));
        pngCorn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngCornMouseClicked(evt);
            }
        });
        add(pngCorn);
        pngCorn.setBounds(233, 334, 188, 112);

        pngApple.setForeground(new java.awt.Color(0, 0, 0));
        pngApple.setText("pngApple");
        pngApple.setPreferredSize(new java.awt.Dimension(188, 112));
        pngApple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngAppleMouseClicked(evt);
            }
        });
        add(pngApple);
        pngApple.setBounds(441, 334, 188, 112);

        lblTomato.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblTomato.setForeground(new java.awt.Color(0, 0, 0));
        lblTomato.setText("Tomato");
        add(lblTomato);
        lblTomato.setBounds(25, 454, 60, 20);

        lblTomatoAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblTomatoAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblTomatoAcre.setText("1.5 acre");
        add(lblTomatoAcre);
        lblTomatoAcre.setBounds(25, 474, 42, 20);

        lblCorn.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblCorn.setForeground(new java.awt.Color(0, 0, 0));
        lblCorn.setText("Corn");
        add(lblCorn);
        lblCorn.setBounds(233, 454, 60, 20);

        lblCornAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblCornAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblCornAcre.setText("2 acre");
        add(lblCornAcre);
        lblCornAcre.setBounds(233, 474, 42, 20);

        lblAppleAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblAppleAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblAppleAcre.setText("2.5 acre");
        add(lblAppleAcre);
        lblAppleAcre.setBounds(441, 474, 42, 20);

        lblApple.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblApple.setForeground(new java.awt.Color(0, 0, 0));
        lblApple.setText("Apple");
        add(lblApple);
        lblApple.setBounds(441, 454, 60, 20);

        lblCarrotAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblCarrotAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrotAcre.setText("0.4 acre");
        add(lblCarrotAcre);
        lblCarrotAcre.setBounds(25, 648, 42, 20);

        lblCarrot.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblCarrot.setForeground(new java.awt.Color(0, 0, 0));
        lblCarrot.setText("Carrot");
        add(lblCarrot);
        lblCarrot.setBounds(25, 628, 60, 20);

        lblOrange.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblOrange.setForeground(new java.awt.Color(0, 0, 0));
        lblOrange.setText("Orange");
        add(lblOrange);
        lblOrange.setBounds(233, 628, 60, 20);

        lblOrangeAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblOrangeAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblOrangeAcre.setText("3.2 acre");
        add(lblOrangeAcre);
        lblOrangeAcre.setBounds(233, 648, 42, 20);

        lblMango.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblMango.setForeground(new java.awt.Color(0, 0, 0));
        lblMango.setText("Mango");
        add(lblMango);
        lblMango.setBounds(441, 628, 60, 20);

        lblMangoAcre.setFont(new java.awt.Font("SF Pro Text", 0, 11)); // NOI18N
        lblMangoAcre.setForeground(new java.awt.Color(0, 0, 0));
        lblMangoAcre.setText("2.3 acre");
        add(lblMangoAcre);
        lblMangoAcre.setBounds(441, 648, 42, 20);

        pngCarrot.setForeground(new java.awt.Color(0, 0, 0));
        pngCarrot.setText("pngCarrot");
        pngCarrot.setPreferredSize(new java.awt.Dimension(188, 112));
        pngCarrot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngCarrotMouseClicked(evt);
            }
        });
        add(pngCarrot);
        pngCarrot.setBounds(25, 508, 188, 112);

        pngOrange.setForeground(new java.awt.Color(0, 0, 0));
        pngOrange.setText("pngOrange");
        pngOrange.setPreferredSize(new java.awt.Dimension(188, 112));
        pngOrange.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngOrangeMouseClicked(evt);
            }
        });
        add(pngOrange);
        pngOrange.setBounds(233, 508, 188, 112);

        pngMango.setForeground(new java.awt.Color(0, 0, 0));
        pngMango.setText("pngMango");
        pngMango.setPreferredSize(new java.awt.Dimension(188, 112));
        pngMango.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pngMangoMouseClicked(evt);
            }
        });
        add(pngMango);
        pngMango.setBounds(441, 508, 188, 112);
    }// </editor-fold>//GEN-END:initComponents

    private void pngTomatoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngTomatoMouseClicked
        selectField("Tomato");
    }//GEN-LAST:event_pngTomatoMouseClicked

    private void pngCornMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngCornMouseClicked
        selectField("Corn");
    }//GEN-LAST:event_pngCornMouseClicked

    private void pngAppleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngAppleMouseClicked
        selectField("Apple");
    }//GEN-LAST:event_pngAppleMouseClicked

    private void pngCarrotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngCarrotMouseClicked
        selectField("Carrot");
    }//GEN-LAST:event_pngCarrotMouseClicked

    private void pngOrangeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngOrangeMouseClicked
        selectField("Orange");
    }//GEN-LAST:event_pngOrangeMouseClicked

    private void pngMangoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pngMangoMouseClicked
        selectField("Mango");
    }//GEN-LAST:event_pngMangoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblApple;
    private javax.swing.JLabel lblAppleAcre;
    private javax.swing.JLabel lblCarrot;
    private javax.swing.JLabel lblCarrotAcre;
    private javax.swing.JLabel lblCorn;
    private javax.swing.JLabel lblCornAcre;
    private javax.swing.JLabel lblMango;
    private javax.swing.JLabel lblMangoAcre;
    private javax.swing.JLabel lblOrange;
    private javax.swing.JLabel lblOrangeAcre;
    private javax.swing.JLabel lblTomato;
    private javax.swing.JLabel lblTomatoAcre;
    private javax.swing.JLabel pngApple;
    private javax.swing.JLabel pngCarrot;
    private javax.swing.JLabel pngCorn;
    private javax.swing.JLabel pngMango;
    private javax.swing.JLabel pngOrange;
    private javax.swing.JLabel pngTomato;
    private main.SVGImage sVGSalesGraph;
    private main.Chart salesChart;
    // End of variables declaration//GEN-END:variables
}
