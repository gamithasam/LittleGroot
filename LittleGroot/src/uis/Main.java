/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package uis;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import main.SVGImage;

/**
 *
 * @author gamitha
 */
class User {
    protected final int eid;
    public final String fName;
    public final String lName;
    public final String role;
    protected String passWord;
    
    public User(int eid, String fName, String lName, String role, String passWord) {
        this.eid = eid;
        this.fName = fName;
        this.lName = lName;
        this.role = role;
        this.passWord = passWord;
    }
    
    public String[] returnSidebarItems() {
        String[] sidebarItems = {"Dashboard", "All Fields", "Tasks", "Inventory", "Add", "Settings"};
        return sidebarItems;
    }
}

class Manager extends User {
    public Manager(int eid, String fName, String lName, String role, String passWord) {
        super(eid, fName, lName, role, passWord);
    }
    
    @Override
    public String[] returnSidebarItems() {
        String[] sidebarItems = {"Dashboard", "All Fields", "Tasks", "Inventory", "Finance", "Employees", "Add", "Settings"};
        return sidebarItems;
    }
}

public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    // Create objects
    private static User user;
    
    // Function to create a user object
    public static void createUserObj(int eid, String fName, String lName, String role, String passWord) {
        switch (role) {
            case "Manager" :
                user = new Manager(eid, fName, lName, role, passWord);
            default :
                user = new User(eid, fName, lName, role, passWord);
        }
    }
    
    // Method to retrieve created user object
    public static User getUser() {
        return user;
    }
    
    // Method to change password object
    public static void passwordChange(String newPass) {
        user.passWord = newPass;
        System.out.println(user.passWord);
    }
    
    public Main() {
        // Set macOS menu bar
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            // Set the About handler
            desktop.setAboutHandler(e -> {
                selectSidebarButton("SidebarSettings");
                selectContentArea("SidebarSettings");
            });
        }
        
        initComponents();
        
        // Set the size of the Main frame
        /* I had to implement this because no matter how many times I set this up in the visual editor
        it changes in the runtime for some reason */
        pack();
        Insets insets = getInsets();
        setSize(new Dimension(
                850 + insets.left + insets.right,
                530 + insets.top + insets.bottom
        ));
        
        // Set user related componenets
        lblUserName.setText(user.fName);
        lblUserRole.setText(user.role);
        
        // Visibility off
        allFieldsScroll.setVisible(false);
        settings.setVisible(false);
        add.setVisible(false);
        inventory.setVisible(false);
        employees.setVisible(false);
        tasks.setVisible(false);
        finance.setVisible(false);
        
        // Set SVGs
        sVGSidebar.setSvgImage("./svgcomponents/Sidebar.svg", 200, 530);
        sVGSeparator.setSvgImage("./svgcomponents/Separator.svg", 1, 530);
        sVGToolBar.setSvgImage("./svgcomponents/Toolbar.svg", 649, 52);
        sVGSidebarDashboard.setSvgImage("./svgcomponents/SidebarDashboardSelected.svg", 180, 28);
        sVGSidebarAllFields.setSvgImage("./svgcomponents/SidebarAllFields.svg", 180, 28);
        sVGSidebarTasks.setSvgImage("./svgcomponents/SidebarTasks.svg", 180, 28);
        sVGSidebarInventory.setSvgImage("./svgcomponents/SidebarInventory.svg", 180, 28);
        sVGSidebarFinance.setSvgImage("./svgcomponents/SidebarFinance.svg", 180, 28);
        sVGSidebarEmployees.setSvgImage("./svgcomponents/SidebarEmployees.svg", 180, 28);
        sVGSidebarAdd.setSvgImage("./svgcomponents/SidebarAdd.svg", 180, 28);
        sVGSidebarSettings.setSvgImage("./svgcomponents/SidebarSettings.svg", 180, 28);
        
        // Set PNGs
        addImageToLabel();
        
        // Set hand cursors
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        sVGSidebarAllFields.setCursor(hand);
        sVGSidebarTasks.setCursor(hand);
        sVGSidebarInventory.setCursor(hand);
        sVGSidebarFinance.setCursor(hand);
        sVGSidebarEmployees.setCursor(hand);
        sVGSidebarAdd.setCursor(hand);
        sVGSidebarSettings.setCursor(hand);
    }
    
    private void addImageToLabel() {
        // Toolbar Logo
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/ToolbarLogo.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngToolBarLogo.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
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

        sidebar = new javax.swing.JPanel();
        sVGSidebarTasks = new main.SVGImage();
        sVGSidebarAllFields = new main.SVGImage();
        sVGSidebarDashboard = new main.SVGImage();
        sVGSidebarInventory = new main.SVGImage();
        sVGSidebarFinance = new main.SVGImage();
        sVGSidebarEmployees = new main.SVGImage();
        sVGSidebarAdd = new main.SVGImage();
        sVGSidebarSettings = new main.SVGImage();
        sVGSidebar = new main.SVGImage();
        contentAreaNToolBar = new javax.swing.JPanel();
        toolBar = new javax.swing.JPanel();
        lblUserRole = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        lblLogoName = new javax.swing.JLabel();
        pngToolBarLogo = new javax.swing.JLabel();
        sVGToolBar = new main.SVGImage();
        dashboardScroll = new main.ScrollPaneWin11();
        dashboard = new uis.Dashboard();
        finance = new uis.Finance();
        tasks = new uis.Tasks();
        employees = new uis.Employees();
        inventory = new uis.Inventory();
        add = new uis.Add();
        allFieldsScroll = new main.ScrollPaneWin11();
        allFields = new uis.AllFields();
        settings = new uis.Settings();
        sVGSeparator = new main.SVGImage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(234, 234, 234));
        setForeground(new java.awt.Color(234, 234, 234));
        setLocation(new java.awt.Point(0, 0));
        setPreferredSize(new java.awt.Dimension(850, 530));
        setResizable(false);
        setSize(new java.awt.Dimension(850, 530));
        getContentPane().setLayout(null);

        sidebar.setBackground(new java.awt.Color(234, 234, 234));
        sidebar.setLayout(null);

        sVGSidebarTasks.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarTasks.setText("sVGSidebarTasks");
        sVGSidebarTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarTasksMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarTasks);
        sVGSidebarTasks.setBounds(10, 66, 180, 28);

        sVGSidebarAllFields.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarAllFields.setText("sVGSidebarAllFields");
        sVGSidebarAllFields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarAllFieldsMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarAllFields);
        sVGSidebarAllFields.setBounds(10, 38, 180, 28);

        sVGSidebarDashboard.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarDashboard.setText("sVGSidebarDashboard");
        sVGSidebarDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarDashboardMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarDashboard);
        sVGSidebarDashboard.setBounds(10, 10, 180, 28);

        sVGSidebarInventory.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarInventory.setText("sVGSidebarInventory");
        sVGSidebarInventory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarInventoryMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarInventory);
        sVGSidebarInventory.setBounds(10, 94, 180, 28);

        sVGSidebarFinance.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarFinance.setText("sVGSidebarFinance");
        sVGSidebarFinance.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarFinanceMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarFinance);
        sVGSidebarFinance.setBounds(10, 122, 180, 28);

        sVGSidebarEmployees.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarEmployees.setText("sVGSidebarEmployees");
        sVGSidebarEmployees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarEmployeesMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarEmployees);
        sVGSidebarEmployees.setBounds(10, 150, 180, 28);

        sVGSidebarAdd.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarAdd.setText("sVGSidebarAdd");
        sVGSidebarAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarAddMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarAdd);
        sVGSidebarAdd.setBounds(10, 464, 180, 28);

        sVGSidebarSettings.setForeground(new java.awt.Color(0, 0, 0));
        sVGSidebarSettings.setText("sVGSidebarSettings");
        sVGSidebarSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sVGSidebarSettingsMouseClicked(evt);
            }
        });
        sidebar.add(sVGSidebarSettings);
        sVGSidebarSettings.setBounds(10, 492, 180, 28);

        sVGSidebar.setToolTipText("");
        sidebar.add(sVGSidebar);
        sVGSidebar.setBounds(0, 0, 200, 530);

        getContentPane().add(sidebar);
        sidebar.setBounds(0, 0, 200, 530);

        contentAreaNToolBar.setBackground(new java.awt.Color(234, 234, 234));
        contentAreaNToolBar.setPreferredSize(new java.awt.Dimension(649, 530));
        contentAreaNToolBar.setLayout(null);

        toolBar.setBackground(new java.awt.Color(234, 234, 234));
        toolBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUserRole.setFont(new java.awt.Font("SF Pro Display", 0, 11)); // NOI18N
        lblUserRole.setForeground(new java.awt.Color(0, 0, 0));
        lblUserRole.setText("Role");
        toolBar.add(lblUserRole, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 29, 70, -1));

        lblUserName.setFont(new java.awt.Font("SF Pro Display", 1, 15)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(0, 0, 0));
        lblUserName.setText("Name");
        toolBar.add(lblUserName, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 9, -1, 20));

        lblLogoName.setBackground(new java.awt.Color(0, 0, 0));
        lblLogoName.setFont(new java.awt.Font("SF Pro Text", 1, 15)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(0, 0, 0));
        lblLogoName.setText("Little Groot");
        toolBar.add(lblLogoName, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 0, 100, 52));

        pngToolBarLogo.setForeground(new java.awt.Color(0, 0, 0));
        pngToolBarLogo.setText("Logo");
        toolBar.add(pngToolBarLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 9, 35, 35));
        toolBar.add(sVGToolBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 649, 52));

        contentAreaNToolBar.add(toolBar);
        toolBar.setBounds(0, 0, 649, 52);

        dashboardScroll.setBorder(null);
        dashboardScroll.setViewportView(dashboard);

        contentAreaNToolBar.add(dashboardScroll);
        dashboardScroll.setBounds(0, 52, 649, 478);
        contentAreaNToolBar.add(finance);
        finance.setBounds(0, 52, 649, 478);
        contentAreaNToolBar.add(tasks);
        tasks.setBounds(0, 52, 649, 478);
        contentAreaNToolBar.add(employees);
        employees.setBounds(0, 52, 649, 478);
        contentAreaNToolBar.add(inventory);
        inventory.setBounds(0, 52, 649, 478);
        contentAreaNToolBar.add(add);
        add.setBounds(0, 52, 649, 478);

        allFieldsScroll.setBorder(null);

        allFields.setMinimumSize(new java.awt.Dimension(649, 682));
        allFields.setPreferredSize(new java.awt.Dimension(649, 682));
        allFieldsScroll.setViewportView(allFields);

        contentAreaNToolBar.add(allFieldsScroll);
        allFieldsScroll.setBounds(0, 52, 649, 478);

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 649, Short.MAX_VALUE)
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        contentAreaNToolBar.add(settings);
        settings.setBounds(0, 52, 649, 478);

        getContentPane().add(contentAreaNToolBar);
        contentAreaNToolBar.setBounds(201, 0, 649, 530);
        getContentPane().add(sVGSeparator);
        sVGSeparator.setBounds(200, 0, 1, 530);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectSidebarButton(String button) {
        // Declare and initialize the map
        Map<String, SVGImage> svgMap = new HashMap<>();
        svgMap.put("SidebarDashboard", sVGSidebarDashboard);
        svgMap.put("SidebarAllFields", sVGSidebarAllFields);
        svgMap.put("SidebarTasks", sVGSidebarTasks);
        svgMap.put("SidebarInventory", sVGSidebarInventory);
        svgMap.put("SidebarFinance", sVGSidebarFinance);
        svgMap.put("SidebarEmployees", sVGSidebarEmployees);
        svgMap.put("SidebarAdd", sVGSidebarAdd);
        svgMap.put("SidebarSettings", sVGSidebarSettings);
        
        // Declare and initialize an array of Sidebar buttons
        String[] sidebarButtons = {"SidebarDashboard", "SidebarAllFields", "SidebarTasks", "SidebarInventory", "SidebarFinance", "SidebarEmployees", "SidebarAdd", "SidebarSettings"};
        
        // Intialize cursor objects
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        
        // Iterate thorugh the array
        for (String idx : sidebarButtons) {
            SVGImage svg = svgMap.get(idx); // Create the svg name from the String of the index
            if (idx.equals(button)) {
                // Select button
                svg.setSvgImage("./svgcomponents/" + idx + "Selected.svg", 180, 28);
                svg.setCursor(defaultCursor);
            } else {
                // Unselect other buttons
                svg.setSvgImage("./svgcomponents/" + idx + ".svg", 180, 28);
                svg.setCursor(hand);
            }
        }
        
        // Repaint
        sidebar.revalidate();
        sidebar.repaint();
    }

    private void selectContentArea(String selectedArea) {
        // Declare and initialize the map
        Map<String, JComponent> contentAreaMap = new HashMap<>();
        contentAreaMap.put("SidebarDashboard", dashboardScroll);
        contentAreaMap.put("SidebarAllFields", allFieldsScroll);
        contentAreaMap.put("SidebarTasks", tasks);
        contentAreaMap.put("SidebarInventory", inventory);
        contentAreaMap.put("SidebarFinance", finance);
        contentAreaMap.put("SidebarEmployees", employees);
        contentAreaMap.put("SidebarAdd", add);
        contentAreaMap.put("SidebarSettings", settings);

        // Iterate through the map
        for (Map.Entry<String, JComponent> entry : contentAreaMap.entrySet()) {
            JComponent component = entry.getValue();
            if (entry.getKey().equals(selectedArea)) {
                // Select Content Area
                component.setVisible(true);
            } else {
                // Unselect other JPanels
                component.setVisible(false);
            }
        }
    }
    
    private void sVGSidebarAllFieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarAllFieldsMouseClicked
        // Select ContentArea
        selectContentArea("SidebarAllFields");
        
        // Select Button
        selectSidebarButton("SidebarAllFields");
    }//GEN-LAST:event_sVGSidebarAllFieldsMouseClicked

    private void sVGSidebarDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarDashboardMouseClicked
        // Select ContentArea
        selectContentArea("SidebarDashboard");
        
        // Select Button
        selectSidebarButton("SidebarDashboard");
    }//GEN-LAST:event_sVGSidebarDashboardMouseClicked

    private void sVGSidebarTasksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarTasksMouseClicked
        // Select ContentArea
        selectContentArea("SidebarTasks");
        
        // Select Button
        selectSidebarButton("SidebarTasks");
    }//GEN-LAST:event_sVGSidebarTasksMouseClicked

    private void sVGSidebarInventoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarInventoryMouseClicked
        // Select ContentArea
        selectContentArea("SidebarInventory");
        
        // Select Button
        selectSidebarButton("SidebarInventory");
    }//GEN-LAST:event_sVGSidebarInventoryMouseClicked

    private void sVGSidebarFinanceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarFinanceMouseClicked
        // Select ContentArea
        selectContentArea("SidebarFinance");
        
        // Select Button
        selectSidebarButton("SidebarFinance");
    }//GEN-LAST:event_sVGSidebarFinanceMouseClicked

    private void sVGSidebarEmployeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarEmployeesMouseClicked
        // Select ContentArea
        selectContentArea("SidebarEmployees");
        
        // Select Button
        selectSidebarButton("SidebarEmployees");
    }//GEN-LAST:event_sVGSidebarEmployeesMouseClicked

    private void sVGSidebarAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarAddMouseClicked
        // Select ContentArea
        selectContentArea("SidebarAdd");
        
        // Select Button
        selectSidebarButton("SidebarAdd");
    }//GEN-LAST:event_sVGSidebarAddMouseClicked

    private void sVGSidebarSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sVGSidebarSettingsMouseClicked
        // Select ContentArea
        selectContentArea("SidebarSettings");
        
        // Select Button
        selectSidebarButton("SidebarSettings");
    }//GEN-LAST:event_sVGSidebarSettingsMouseClicked

    public void repaintJScroll() {
        allFieldsScroll.revalidate();
        allFieldsScroll.repaint();
    }
    /**
     * @param args the command line arguments
     */
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private uis.Add add;
    private uis.AllFields allFields;
    private javax.swing.JScrollPane allFieldsScroll;
    private javax.swing.JPanel contentAreaNToolBar;
    private uis.Dashboard dashboard;
    private javax.swing.JScrollPane dashboardScroll;
    private uis.Employees employees;
    private uis.Finance finance;
    private uis.Inventory inventory;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRole;
    private javax.swing.JLabel pngToolBarLogo;
    private main.SVGImage sVGSeparator;
    private main.SVGImage sVGSidebar;
    private main.SVGImage sVGSidebarAdd;
    private main.SVGImage sVGSidebarAllFields;
    private main.SVGImage sVGSidebarDashboard;
    private main.SVGImage sVGSidebarEmployees;
    private main.SVGImage sVGSidebarFinance;
    private main.SVGImage sVGSidebarInventory;
    private main.SVGImage sVGSidebarSettings;
    private main.SVGImage sVGSidebarTasks;
    private main.SVGImage sVGToolBar;
    private uis.Settings settings;
    private javax.swing.JPanel sidebar;
    private uis.Tasks tasks;
    private javax.swing.JPanel toolBar;
    // End of variables declaration//GEN-END:variables
}
