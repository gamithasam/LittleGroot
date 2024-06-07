/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.font.NumericShaper.Range;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import uis.AllFields;

/**
 *
 * @author gamitha
 */

class FieldMetricsData {
    private final String field;
    public double pH;
    public int moisture;
    public int lightIntensity;
    public Map<Date, FieldMetricsData> history;

    public FieldMetricsData(String field) {
        this.field = field;
        this.history = new HashMap<>();

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root" , "toor");
            Statement st = conn.createStatement();

            String sql = "SELECT * FROM Field WHERE Field = '" + field + "' ORDER BY fmDate DESC";
            ResultSet rs = st.executeQuery(sql);

            // Populate the map and the latest data
            boolean first = true;
            while (rs.next()) {
                FieldMetricsData data = new FieldMetricsData(field, 'x');
                data.pH = rs.getDouble("pH");
                data.moisture = rs.getInt("moisture");
                data.lightIntensity = rs.getInt("lightIntensity");

                // Store the data in the map
                history.put(rs.getDate("fmDate"), data);

                // If this is the first row, it's the latest data
                if (first) {
                    this.pH = data.pH;
                    this.moisture = data.moisture;
                    this.lightIntensity = data.lightIntensity;
                    first = false;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error" + e);
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

    // Empty constructor for creating FieldMetricsData objects without opening a new connection
    private FieldMetricsData(String field, char x) {
        this.field = field;
        this.history = null;
    }
}

public class InsideField extends javax.swing.JPanel {
    public static String clickField;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    FieldMetricsData fData = getFieldData(clickField);
    DefaultCategoryDataset datasetPH = new DefaultCategoryDataset();
    DefaultCategoryDataset datasetMoisture = new DefaultCategoryDataset();
    DefaultCategoryDataset datasetLightIntensity = new DefaultCategoryDataset();
    DefaultCategoryDataset datasetSales = new DefaultCategoryDataset();
    
    private static FieldMetricsData tomato = new FieldMetricsData("Tomato");
    private static FieldMetricsData corn = new FieldMetricsData("Corn");
    private static FieldMetricsData apple = new FieldMetricsData("Apple");
    private static FieldMetricsData carrot = new FieldMetricsData("Carrot");
    private static FieldMetricsData orange = new FieldMetricsData("Orange");
    private static FieldMetricsData mango = new FieldMetricsData("Mango");
    
    // Method to retrieve created FieldMetricsData objects
    public static FieldMetricsData getFieldData(String field) {
        switch (field) {
            case "Tomato": return tomato;
            case "Corn": return corn;
            case "Apple": return apple;
            case "Carrot": return carrot;
            case "Orange": return orange;
            case "Mango": return mango;
        }
        return null;
    }
    
    private LocalDate estimateHarvestDate(String plantType, LocalDate saleDate, double ph, int soilMoisture, int lightIntensity) {
        int maturityDays;
        LocalDate plantDate = saleDate.plusDays(10); // Estimates the planted date is 10 days after the last sale

        switch (plantType) {
            case "Tomato":
                maturityDays = 60;
                break;
            case "Corn":
                maturityDays = 70;
                break;
            case "Apple":
                maturityDays = 180;
                break;
            case "Carrot":
                maturityDays = 75;
                break;
            case "Orange":
                maturityDays = 240;
                break;
            case "Mango":
                maturityDays = 200;
                break;
            default:
                throw new IllegalArgumentException("Unknown plant type: " + plantType);
        }

        if (!isOptimalConditions(plantType, ph, soilMoisture, lightIntensity)) {
            maturityDays += 10; // Add extra days if conditions are not optimal
        }

        return plantDate.plusDays(maturityDays);
    }

    private boolean isOptimalConditions(String plantType, double ph, int soilMoisture, int lightIntensity) {
        switch (plantType) {
            case "Tomato":
                return ph >= 6.0 && ph <= 6.8 && soilMoisture >= 60 && lightIntensity >= 200;
            case "Corn":
                return ph >= 5.8 && ph <= 7.0 && soilMoisture >= 70 && lightIntensity >= 250;
            case "Apple":
                return ph >= 6.0 && ph <= 7.0 && soilMoisture >= 80 && lightIntensity >= 300;
            case "Carrot":
                return ph >= 6.0 && ph <= 6.8 && soilMoisture >= 60 && lightIntensity >= 250;
            case "Orange":
                return ph >= 6.0 && ph <= 7.5 && soilMoisture >= 50 && lightIntensity >= 250;
            case "Mango":
                return ph >= 5.5 && ph <= 7.5 && soilMoisture >= 50 && lightIntensity >= 300;
            default:
                return false;
        }
    }
    
    private String getPlantCondition(String plantType, double ph, int soilMoisture, int lightIntensity) {
        String condition = "Healthy";

        switch (plantType) {
            case "Tomato":
                if (!(ph >= 6.0 && ph <= 6.8)) condition = "Adjust pH";
                else if (soilMoisture < 60) condition = "Low water";
                else if (soilMoisture > 80) condition = "Too much water";
                else if (lightIntensity < 200) condition = "Low light";
                else if (lightIntensity > 300) condition = "Too much light";
                break;
            case "Corn":
                if (!(ph >= 5.8 && ph <= 7.0)) condition = "Adjust pH";
                else if (soilMoisture < 70) condition = "Low water";
                else if (soilMoisture > 90) condition = "Too much water";
                else if (lightIntensity < 250) condition = "Low light";
                else if (lightIntensity > 350) condition = "Too much light";
                break;
            case "Apple":
                if (!(ph >= 6.0 && ph <= 7.0)) condition = "Adjust pH";
                else if (soilMoisture < 80) condition = "Low water";
                else if (soilMoisture > 100) condition = "Too much water";
                else if (lightIntensity < 300) condition = "Low light";
                else if (lightIntensity > 400) condition = "Too much light";
                break;
            case "Carrot":
                if (!(ph >= 6.0 && ph <= 6.8)) condition = "Adjust pH";
                else if (soilMoisture < 60) condition = "Low water";
                else if (soilMoisture > 80) condition = "Too much water";
                else if (lightIntensity < 250) condition = "Low light";
                else if (lightIntensity > 350) condition = "Too much light";
                break;
            case "Orange":
                if (!(ph >= 6.0 && ph <= 7.5)) condition = "Adjust pH";
                else if (soilMoisture < 50) condition = "Low water";
                else if (soilMoisture > 70) condition = "Too much water";
                else if (lightIntensity < 250) condition = "Low light";
                else if (lightIntensity > 350) condition = "Too much light";
                break;
            case "Mango":
                if (!(ph >= 5.5 && ph <= 7.5)) condition = "Adjust pH";
                else if (soilMoisture < 50) condition = "Low water";
                else if (soilMoisture > 70) condition = "Too much water";
                else if (lightIntensity < 300) condition = "Low light";
                else if (lightIntensity > 400) condition = "Too much light";
                break;
            default:
                condition = "Unknown plant";
        }

        return condition;
    }
    
    public InsideField() {
        initComponents();
        
        // Set SVGs
        sVGOverview.setSvgImage("./svgcomponents/InsideFieldOverview.svg", 188, 89);
        sVGPlantedOn.setSvgImage("./svgcomponents/InsideFieldPlantedOn.svg", 188, 89);
        sVGEstHarvestOn.setSvgImage("./svgcomponents/InsideFieldEstHarvestOn.svg", 188, 89);
        sVGSalesGraph.setSvgImage("./svgcomponents/InsideFieldSalesGraph.svg", 292, 224);
        sVGMetricsGraph.setSvgImage("./svgcomponents/MetricsGraph.svg", 292, 224);
        
        // Set lblField, lblOverview
        if(clickField != null) {
            lblField.setText(clickField);
        }
        lblOverview.setText(getPlantCondition(clickField, fData.pH, fData.moisture, fData.lightIntensity));
        
        //Get data from database to table
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LittleGroot", "root", "toor");
            Statement st = conn.createStatement();
            String query = "SELECT TDate FROM Finance WHERE TType = 'I' AND Category = '" + clickField + "' ORDER BY TDate DESC LIMIT 1";
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                LocalDate sDate = rs.getDate("TDate").toLocalDate();
                
                // Add texts to labels
                lblPlantedOn.setText(sDate.plusDays(10).format(formatter)); // Assumes that the planted day is 10 days after the last sale
                LocalDate estDate = estimateHarvestDate(clickField, sDate, fData.pH, fData.moisture, fData.lightIntensity);
                lblEstHarvestOn.setText(estDate.format(formatter));
            }
            
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
        }
        
        showLineChart();
        showSalesChart();
    }
    
    public void showLineChart() { //create dataset for the graph
        for (Map.Entry<Date, FieldMetricsData> entry : fData.history.entrySet()) {
            FieldMetricsData data = entry.getValue();
            java.sql.Date sqlDate = entry.getKey();
            LocalDate localDate = sqlDate.toLocalDate();
            String sDate = localDate.format(formatter);

            // Access the fields of the data
            double pH = data.pH;
            int moisture = data.moisture;
            int lightIntensity = data.lightIntensity;

            datasetPH.setValue(pH, "Soil pH", sDate);
            datasetMoisture.setValue(moisture, "Soil Moisture", sDate);
            datasetLightIntensity.setValue(lightIntensity, "Light Intensity", sDate);
        }
        
        updateMetricsChart("Soil pH");
    }
    
    private void updateMetricsChart(String selected) {
        DefaultCategoryDataset dataset;
        Color color;

        switch (selected) {
            case "Soil Moisture":
                dataset = datasetMoisture;
                color = new Color(0, 128, 0); // Green color for soil moisture
                break;
            case "Light Intensity":
                dataset = datasetLightIntensity;
                color = new Color(255, 255, 0); // Yellow color for light intensity
                break;
            default:
                dataset = datasetPH;
                color = new Color(139, 69, 19); // Brown color for soil pH
        }

        createLineChartAndPanel(dataset, color, 'M');
    }

    private void createLineChartAndPanel(DefaultCategoryDataset dataset, Color color, char chart) {
        JFreeChart linechart = ChartFactory.createLineChart("", "Date", "Amount",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
        lineCategoryPlot.setBackgroundPaint(Color.white);

        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
        lineRenderer.setSeriesPaint(0, color);

        NumberAxis rangeAxis = (NumberAxis) lineCategoryPlot.getRangeAxis();
        org.jfree.data.Range range = DatasetUtilities.findRangeBounds(dataset);
        if (range != null) {
            // Check for zero-length range and adjust it
            if (range.getLength() == 0) {
                range = new org.jfree.data.Range(range.getLowerBound(), range.getUpperBound() + 0.01);
            }
            rangeAxis.setRange(range);
        }

        ChartPanel lineChartPanel = new ChartPanel(linechart);
        if (chart == 'M') {
            panelMetricsGraph.removeAll();
            panelMetricsGraph.add(lineChartPanel, BorderLayout.CENTER);
            panelMetricsGraph.validate();
        } else {
            panelSalesGraph.removeAll();
            panelSalesGraph.add(lineChartPanel, BorderLayout.CENTER);
            panelSalesGraph.validate();
        }
    }

    static Map<YearMonth, Map<String, Double>> totalByMonthAndCategory = new HashMap<>();
    
    public static void getDataFromAllFields(Map<YearMonth, Map<String, Double>> recMap) {
        totalByMonthAndCategory = recMap;
    }
    
    public void showSalesChart() { //create dataset for the graph
        String targetCategory = clickField;
        Map<YearMonth, Double> totalByMonthForCategory = new HashMap<>();

        for (Map.Entry<YearMonth, Map<String, Double>> entry : totalByMonthAndCategory.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            Map<String, Double> categoryMap = entry.getValue();

            if (categoryMap.containsKey(targetCategory)) {
                totalByMonthForCategory.put(yearMonth, categoryMap.get(targetCategory));
            }
        }
        for (Map.Entry<YearMonth, Double> entry : totalByMonthForCategory.entrySet()) {
            YearMonth yearMonth = entry.getKey();
            Double totalPrice = entry.getValue();

            String sDate = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            datasetSales.setValue(totalPrice, "Total Price", sDate);
        }

        updateSalesChart("Total Price");
    }
    
    private void updateSalesChart(String selected) {
        DefaultCategoryDataset dataset;
        Color color;

        switch (selected) {
            case "Tomato":
                color = new Color(0, 128, 0); // Green color for soil moisture
                break;
            case "Light Intensity":
                color = new Color(255, 255, 0); // Yellow color for light intensity
                break;
            default:
                color = new Color(139, 69, 19); // Brown color for soil pH
        }

        createLineChartAndPanel(datasetSales, color, 'S');
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbMetricsChart = new javax.swing.JComboBox<>();
        panelSalesGraph = new javax.swing.JPanel();
        panelMetricsGraph = new javax.swing.JPanel();
        lblField = new javax.swing.JLabel();
        lblEstHarvestOn = new javax.swing.JLabel();
        lblPlantedOn = new javax.swing.JLabel();
        lblOverview = new javax.swing.JLabel();
        sVGOverview = new main.SVGImage();
        sVGPlantedOn = new main.SVGImage();
        sVGEstHarvestOn = new main.SVGImage();
        sVGMetricsGraph = new main.SVGImage();
        sVGSalesGraph = new main.SVGImage();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(null);
        setSize(new java.awt.Dimension(649, 478));
        setLayout(null);

        cmbMetricsChart.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Soil pH", "Soil Moisture", "Light Intensity" }));
        cmbMetricsChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMetricsChartActionPerformed(evt);
            }
        });
        add(cmbMetricsChart);
        cmbMetricsChart.setBounds(199, 171, 114, 21);

        panelSalesGraph.setLayout(new java.awt.BorderLayout());
        add(panelSalesGraph);
        panelSalesGraph.setBounds(344, 201, 278, 180);

        panelMetricsGraph.setLayout(new java.awt.BorderLayout());
        add(panelMetricsGraph);
        panelMetricsGraph.setBounds(32, 201, 278, 180);

        lblField.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblField.setForeground(new java.awt.Color(0, 0, 0));
        lblField.setText("lblField");
        lblField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFieldMouseClicked(evt);
            }
        });
        add(lblField);
        lblField.setBounds(46, 20, 60, 20);

        lblEstHarvestOn.setFont(new java.awt.Font("SF Pro", 0, 26)); // NOI18N
        lblEstHarvestOn.setForeground(new java.awt.Color(0, 0, 0));
        lblEstHarvestOn.setText("lblEstHarvestOn");
        add(lblEstHarvestOn);
        lblEstHarvestOn.setBounds(452, 108, 177, 32);

        lblPlantedOn.setFont(new java.awt.Font("SF Pro", 0, 26)); // NOI18N
        lblPlantedOn.setForeground(new java.awt.Color(0, 0, 0));
        lblPlantedOn.setText("lblPlantedOn");
        add(lblPlantedOn);
        lblPlantedOn.setBounds(244, 108, 177, 32);

        lblOverview.setFont(new java.awt.Font("SF Pro", 0, 26)); // NOI18N
        lblOverview.setForeground(new java.awt.Color(0, 0, 0));
        lblOverview.setText("lblOverview");
        add(lblOverview);
        lblOverview.setBounds(36, 108, 177, 32);

        sVGOverview.setForeground(new java.awt.Color(0, 0, 0));
        sVGOverview.setText("sVGOverview");
        add(sVGOverview);
        sVGOverview.setBounds(25, 55, 188, 89);

        sVGPlantedOn.setForeground(new java.awt.Color(0, 0, 0));
        sVGPlantedOn.setText("sVGPlantedOn");
        add(sVGPlantedOn);
        sVGPlantedOn.setBounds(233, 55, 188, 89);

        sVGEstHarvestOn.setForeground(new java.awt.Color(0, 0, 0));
        sVGEstHarvestOn.setText("sVGEstHarvestOn");
        add(sVGEstHarvestOn);
        sVGEstHarvestOn.setBounds(441, 55, 188, 89);

        sVGMetricsGraph.setForeground(new java.awt.Color(0, 0, 0));
        sVGMetricsGraph.setText("sVGMetricsGraph");
        add(sVGMetricsGraph);
        sVGMetricsGraph.setBounds(25, 164, 292, 224);

        sVGSalesGraph.setForeground(new java.awt.Color(0, 0, 0));
        sVGSalesGraph.setText("sVGSalesGraph");
        add(sVGSalesGraph);
        sVGSalesGraph.setBounds(337, 164, 292, 224);
    }// </editor-fold>//GEN-END:initComponents

    private void lblFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFieldMouseClicked

    }//GEN-LAST:event_lblFieldMouseClicked

    private void cmbMetricsChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMetricsChartActionPerformed
        updateMetricsChart(cmbMetricsChart.getSelectedItem().toString());
    }//GEN-LAST:event_cmbMetricsChartActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbMetricsChart;
    private javax.swing.JLabel lblEstHarvestOn;
    private javax.swing.JLabel lblField;
    private javax.swing.JLabel lblOverview;
    private javax.swing.JLabel lblPlantedOn;
    private javax.swing.JPanel panelMetricsGraph;
    private javax.swing.JPanel panelSalesGraph;
    private main.SVGImage sVGEstHarvestOn;
    private main.SVGImage sVGMetricsGraph;
    private main.SVGImage sVGOverview;
    private main.SVGImage sVGPlantedOn;
    private main.SVGImage sVGSalesGraph;
    // End of variables declaration//GEN-END:variables
}
