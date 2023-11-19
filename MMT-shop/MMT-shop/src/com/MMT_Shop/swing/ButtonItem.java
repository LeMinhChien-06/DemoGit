package com.MMT_Shop.swing;

import com.MMT_Shop.model.Model_Button;
import com.MMT_Shop.model.Model_Menu;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultButtonModel;
import javax.swing.SwingUtilities;

public class ButtonItem extends javax.swing.JPanel {

    private final DefaultButtonModel model = new DefaultButtonModel();
    private int selectedIndex = -1;

    public ButtonItem(Model_Button data) {
        initComponents();
        setOpaque(false);
        if (data.getType() == Model_Button.ButtonType.Button) {
            lbName.setText(data.getName());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        lbName = new javax.swing.JLabel();

        jToggleButton1.setText("jToggleButton1");

        lbName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbName.setText("Button name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(33, 33, 33))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
        super.paintComponent(g);
    }

    public ButtonItem() {
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    
                }
            }

        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lbName;
    // End of variables declaration//GEN-END:variables
}
