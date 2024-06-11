/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package uis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import main.FieldMetricsData;

/**
 *
 * @author gamitha
 */

public class InsideField extends javax.swing.JPanel {
    Connection conn = null;
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
    
    public InsideField() {
        initComponents();
        
        // Change background color of the table area
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        
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
        lblOverview.setText(main.PlantConditions.getPlantCondition(clickField, fData.pH, fData.moisture, fData.lightIntensity));
        main.PlantConditions.adjustFontSizeOfOverview(lblOverview);
        
        //Get data from database
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
            
            Statement stTasks = conn.createStatement();
            String queryTasks = "SELECT TStatus, Task, Category, Tdate FROM Tasks WHERE TStatus = false AND Category = '" + clickField + "' ORDER BY Tdate ASC";

            ResultSet rsTasks = stTasks.executeQuery(queryTasks);

            // Get metadata
            ResultSetMetaData rsmd = rsTasks.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // for storing data from database in an array
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rsTasks.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    if (columnIndex == 1) {
                        vector.add(rsTasks.getBoolean(columnIndex)); // Handle TStatus as Boolean
                    } else {
                        vector.add(rsTasks.getObject(columnIndex));
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

        // Rotate the axis labels
        CategoryAxis domainAxis = lineCategoryPlot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

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
        lblOverview.setMaximumSize(new java.awt.Dimension(166, 32));
        lblOverview.setMinimumSize(new java.awt.Dimension(166, 32));
        lblOverview.setPreferredSize(new java.awt.Dimension(166, 32));
        lblOverview.setSize(new java.awt.Dimension(166, 32));
        add(lblOverview);
        lblOverview.setBounds(36, 108, 166, 32);

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
        jScrollPane1.setBounds(20, 408, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void lblFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFieldMouseClicked

    }//GEN-LAST:event_lblFieldMouseClicked

    private void cmbMetricsChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMetricsChartActionPerformed
        updateMetricsChart(cmbMetricsChart.getSelectedItem().toString());
    }//GEN-LAST:event_cmbMetricsChartActionPerformed

    private void tableTasksComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tableTasksComponentResized
        int height = 0;
        for (int row = 0; row < tableTasks.getRowCount(); row++) {
            height += tableTasks.getRowHeight(row);
        }
        jScrollPane1.setPreferredSize(new Dimension(jScrollPane1.getWidth(), height));
        jScrollPane1.revalidate();
    }//GEN-LAST:event_tableTasksComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbMetricsChart;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTable tableTasks;
    // End of variables declaration//GEN-END:variables
}
