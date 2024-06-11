/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import main.SVGImage;

/**
 *
 * @author gamitha
 */
public class MessageDialog {
    JLabel pngIcon = new JLabel();
    
    public MessageDialog(int type, JPanel parent, String DTitle, String title, String desc) {
        // Create a JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Message");
        dialog.setModal(true);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.getContentPane().setBackground(Color.WHITE);

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
        iconPanel.setBackground(Color.WHITE);
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
        if (type == 0) {
            sVGOKBtn.setSvgImage("./svgcomponents/MessageDialogOKBtn.svg", 228, 28);
        } else {
            sVGOKBtn.setSvgImage("./svgcomponents/MessageDialogOKBtnDest.svg", 228, 28);
        }
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
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
        dialog.getContentPane().add(createCenteredBox(sVGOKBtn)); // OK Button
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
