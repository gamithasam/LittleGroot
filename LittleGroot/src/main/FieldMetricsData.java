/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author gamitha
 */
public class FieldMetricsData {
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
