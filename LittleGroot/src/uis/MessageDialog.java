/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.SVGImage;

/**
 *
 * @author gamitha
 */
public class MessageDialog {
    JLabel pngIcon = new JLabel();
    Color bgColor = new Color(250, 250, 250);
    Cursor hand = new Cursor(Cursor.HAND_CURSOR);
    
    public MessageDialog(int type, JPanel parent, String DTitle, String title, String desc) {
        // Create a JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Message");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.getContentPane().setBackground(bgColor);

        // Set the layout of the JDialog
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));
        
        // Icon
        addImageToLabel();
        SVGImage sVGIconFrame = new SVGImage();
        sVGIconFrame.setSvgImage("./svgcomponents/MessageDialogIconFrame.svg", 52, 52);
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(52, 52));
        sVGIconFrame.setBounds(0, 0, 52, 52);
        pngIcon.setBounds(4, 4, 44, 44);
        layeredPane.add(sVGIconFrame, Integer.valueOf(1));
        layeredPane.add(pngIcon, Integer.valueOf(2));
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setBackground(bgColor);
        iconPanel.add(layeredPane);
        
        // Title
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new java.awt.Font("SF Pro Text", 1, 13));
        int preferredHeightTitle = lblTitle.getPreferredSize().height;
        lblTitle.setPreferredSize(new Dimension(228, preferredHeightTitle));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Description
        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new java.awt.Font("SF Pro Text", 0, 11));
        int preferredHeightDesc = lblDesc.getPreferredSize().height;
        lblDesc.setPreferredSize(new Dimension(228, preferredHeightDesc));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Button
        SVGImage sVGOKBtn = new SVGImage();
        if (type == 2) {
            sVGOKBtn.setSvgImage("./svgcomponents/MessageDialogOKBtnDest.svg", 228, 28);
        } else if (type == 3) {
            sVGOKBtn.setSvgImage("./svgcomponents/MessageDialogCloseBtn.svg", 110, 28);
        } else {
            sVGOKBtn.setSvgImage("./svgcomponents/MessageDialogOKBtn.svg", 228, 28);
        }
        sVGOKBtn.setCursor(hand);
        sVGOKBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dialog.dispose();
            }
        });

        // Add everything to the dialog
        dialog.getContentPane().add(Box.createVerticalStrut(20));
        dialog.getContentPane().add(createCenteredBox(iconPanel)); // Icon
        dialog.getContentPane().add(Box.createVerticalStrut(16));
        dialog.getContentPane().add(createCenteredBox(lblTitle)); // Title
        dialog.getContentPane().add(Box.createVerticalStrut(10));
        dialog.getContentPane().add(createCenteredBox(lblDesc)); // Desc
        dialog.getContentPane().add(Box.createVerticalStrut(16));
        // Name buttons
        if (type == 3) {
            SVGImage sVGGamitha = new SVGImage();
            SVGImage sVGHeshani = new SVGImage();
            SVGImage sVGHimasha = new SVGImage();
            SVGImage sVGRashmi = new SVGImage();
            sVGGamitha.setSvgImage("./svgcomponents/MessageDialogGamitha.svg", 228, 28);
            sVGHeshani.setSvgImage("./svgcomponents/MessageDialogHeshani.svg", 228, 28);
            sVGHimasha.setSvgImage("./svgcomponents/MessageDialogHimasha.svg", 228, 28);
            sVGRashmi.setSvgImage("./svgcomponents/MessageDialogRashmi.svg", 228, 28);
            sVGGamitha.setCursor(hand);
            sVGHeshani.setCursor(hand);
            sVGHimasha.setCursor(hand);
            sVGRashmi.setCursor(hand);
            sVGGamitha.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/gamithasam"));
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            });
            sVGHeshani.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/heshanichamudika"));
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            });
            sVGHimasha.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/HimashaSawani"));
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            });
            sVGRashmi.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/RashmiAyodhya"));
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            });
            dialog.getContentPane().add(createCenteredBox(sVGGamitha));
            dialog.getContentPane().add(createCenteredBox(sVGHeshani));
            dialog.getContentPane().add(createCenteredBox(sVGHimasha));
            dialog.getContentPane().add(createCenteredBox(sVGRashmi));
            dialog.getContentPane().add(Box.createVerticalStrut(16));
        }
        // Emp Details
        if (type == 4) {
            // Create panel
            JPanel empPanel = new JPanel();
            empPanel.setLayout(null);
            empPanel.setPreferredSize(new Dimension(228, 144));
            empPanel.setBackground(bgColor);
            // Create SVGs
            SVGImage sVGEmpForm = new SVGImage();
            SVGImage sVGEmpEmpID = new SVGImage();
            SVGImage sVGEmpPhone = new SVGImage();
            SVGImage sVGEmpEmail = new SVGImage();
            SVGImage sVGEmpAddress = new SVGImage();
            // Set bounds
            sVGEmpForm.setBounds(0, 0, 228, 144);
            sVGEmpEmpID.setBounds(10, 0, 208, 36);
            sVGEmpPhone.setBounds(10, 36, 208, 36);
            sVGEmpEmail.setBounds(10, 72, 208, 36);
            sVGEmpAddress.setBounds(10, 108, 208, 36);
            // Set SVGs
            sVGEmpForm.setSvgImage("./svgcomponents/MessageDialogEmpForm.svg", 228, 144);
            sVGEmpEmpID.setSvgImage("./svgcomponents/MessageDialogEmpEmpID.svg", 208, 36);
            sVGEmpPhone.setSvgImage("./svgcomponents/MessageDialogEmpPhone.svg", 208, 36);
            sVGEmpEmail.setSvgImage("./svgcomponents/MessageDialogEmpEmail.svg", 208, 36);
            sVGEmpAddress.setSvgImage("./svgcomponents/MessageDialogEmpAddress.svg", 208, 36);
            // Retrieve object
            User user = Main.getUser();
            // Create Labels
            JLabel lblEmpID = new JLabel(String.format("%04d", user.eid));
            lblEmpID.setHorizontalAlignment(SwingConstants.RIGHT);
            lblEmpID.setBounds(98, 10, 120, 16);
            lblEmpID.setFont(new java.awt.Font("SF Pro Display", 0, 13));
            lblEmpID.setForeground(new java.awt.Color(120, 120, 120));
            
            JLabel lblPhone = new JLabel("0760026902");
            lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
            lblPhone.setBounds(112, 46, 106, 16);
            lblPhone.setFont(new java.awt.Font("SF Pro Display", 0, 13));
            lblPhone.setForeground(new java.awt.Color(120, 120, 120));
            
            JLabel lblEmail = new JLabel("heshani@littlegroot.com");
            lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
            lblEmail.setBounds(57, 82, 161, 16);
            lblEmail.setFont(new java.awt.Font("SF Pro Display", 0, 13));
            lblEmail.setForeground(new java.awt.Color(120, 120, 120));
            
            JLabel lblAddress = new JLabel("Peradeniya, Sri Lanka");
            lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
            lblAddress.setBounds(74, 118, 144, 16);
            lblAddress.setFont(new java.awt.Font("SF Pro Display", 0, 13));
            lblAddress.setForeground(new java.awt.Color(120, 120, 120));
            // Add components to panel
            empPanel.add(sVGEmpForm);
            empPanel.add(sVGEmpEmpID);
            empPanel.add(sVGEmpPhone);
            empPanel.add(sVGEmpEmail);
            empPanel.add(sVGEmpAddress);
            empPanel.add(lblEmpID);
            empPanel.add(lblPhone);
            empPanel.add(lblEmail);
            empPanel.add(lblAddress);
            // Add panel to dialog
            dialog.getContentPane().add(createCenteredBox(empPanel));
            dialog.getContentPane().add(Box.createVerticalStrut(16));
        }
        
        if (type == 3) {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
            SVGImage sVGRepoBtn = new SVGImage();
            sVGRepoBtn.setSvgImage("./svgcomponents/MessageDialogRepoBtn.svg", 110, 28);
            sVGRepoBtn.setCursor(hand);
            sVGRepoBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    try {
                            Desktop.getDesktop().browse(new URI("https://github.com/gamithasam/LittleGroot"));
                    } catch (IOException | URISyntaxException e) {
                    }
                }
            });
            buttonPanel.setBackground(bgColor);
            buttonPanel.add(sVGOKBtn); // Close Button
            buttonPanel.add(Box.createHorizontalStrut(8));
            buttonPanel.add(sVGRepoBtn); // Repo Button
            dialog.getContentPane().add(createCenteredBox(buttonPanel));
        } else {
            dialog.getContentPane().add(createCenteredBox(sVGOKBtn)); // OK Button
        }
        
        dialog.getContentPane().add(Box.createVerticalStrut(16));

        // Pack and display the JDialog
        dialog.pack();
      
        // Set location
        dialog.setLocationRelativeTo(parent);
        
        // Set width
        Dimension prefSize = dialog.getPreferredSize();
        int newWidth = 260;
        dialog.setSize(newWidth, prefSize.height);
        
        // Set shape
        dialog.setShape(new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), 15, 15));

        dialog.setVisible(true);
    }
                
    private Box createCenteredBox(Component component) {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(component);
        box.add(Box.createHorizontalGlue());
        return box;
    }
    
    private void addImageToLabel() {
        try {
            // Get the image
            java.net.URL imgURL = getClass().getResource("/pngcomponents/Logo.png");
            ImageIcon icon = new ImageIcon(imgURL);

            // Scale the image
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(44, 44, java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way 
            icon = new ImageIcon(newimg);  // Transform it back

            // Add the image to the label
            pngIcon.setIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
