/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;

/**
 *
 * @author gamitha
 */
public class PlantConditions {
    public static String getPlantCondition(String plantType, double ph, int soilMoisture, int lightIntensity) {
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
    
    public static void adjustFontSizeOfOverview(JLabel lblOverview) {
        Font labelFont = lblOverview.getFont();
        String labelText = lblOverview.getText();

        int stringWidth = lblOverview.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = lblOverview.getWidth();

        // Reduce font size until text fits within label width
        while (stringWidth > componentWidth) {
            // Reduce font size by 1
            int fontSize = labelFont.getSize() - 1;
            labelFont = new Font(labelFont.getName(), Font.PLAIN, fontSize);
            lblOverview.setFont(labelFont);

            // Recalculate string width with new font size
            stringWidth = lblOverview.getFontMetrics(labelFont).stringWidth(labelText);
        }
    }
    
    public static String getFieldOverview() {
        // Load objects
        FieldMetricsData fDataTomato = uis.InsideField.getFieldData("Tomato");
        FieldMetricsData fDataCorn = uis.InsideField.getFieldData("Corn");
        FieldMetricsData fDataApple = uis.InsideField.getFieldData("Apple");
        FieldMetricsData fDataCarrot = uis.InsideField.getFieldData("Carrot");
        FieldMetricsData fDataOrange = uis.InsideField.getFieldData("Orange");
        FieldMetricsData fDataMango = uis.InsideField.getFieldData("Mango");
        
        ArrayList<String> conditions = new ArrayList<String>();
        conditions.add(getPlantCondition("Tomato", fDataTomato.pH, fDataTomato.moisture, fDataTomato.lightIntensity));
        conditions.add(getPlantCondition("Corn", fDataCorn.pH, fDataCorn.moisture, fDataCorn.lightIntensity));
        conditions.add(getPlantCondition("Apple", fDataApple.pH, fDataApple.moisture, fDataApple.lightIntensity));
        conditions.add(getPlantCondition("Carrot", fDataCarrot.pH, fDataCarrot.moisture, fDataCarrot.lightIntensity));
        conditions.add(getPlantCondition("Orange", fDataOrange.pH, fDataOrange.moisture, fDataOrange.lightIntensity));
        conditions.add(getPlantCondition("Mango", fDataMango.pH, fDataMango.moisture, fDataMango.lightIntensity));

        Map<String, Integer> freqMap = new HashMap<>();
        String mode = null;
        int maxCount = 0;
        boolean isMode = false;

        for (String condition : conditions) {
            int count = freqMap.getOrDefault(condition, 0) + 1;
            freqMap.put(condition, count);

            if (count > maxCount) {
                maxCount = count;
                mode = condition;
                isMode = true;
            } else if (count == maxCount) {
                isMode = false;
            }
        }

        if (isMode) {
            return mode;
        } else {
            return "Mixed";
        }
    }
}
