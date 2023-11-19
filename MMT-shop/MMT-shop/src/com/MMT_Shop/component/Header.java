package com.MMT_Shop.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Header extends javax.swing.JPanel {

    public Header() {
        initComponents();
        setOpaque(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchText1 = new com.MMT_Shop.swing.SearchText();
        searchText2 = new com.MMT_Shop.swing.SearchText();
        jLabel1 = new javax.swing.JLabel();
        searchText3 = new com.MMT_Shop.swing.SearchText();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Search.png"))); // NOI18N

        searchText3.setBorder(null);
        searchText3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchText3ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Menu.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(searchText3, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchText3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchText3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchText3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchText3ActionPerformed

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(0, 0, 25, getHeight());
        g2.fillRect(getWidth()-25, getHeight()-25, getWidth(), getHeight());
        super.paintComponent(g); 
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.MMT_Shop.swing.SearchText searchText1;
    private com.MMT_Shop.swing.SearchText searchText2;
    private com.MMT_Shop.swing.SearchText searchText3;
    // End of variables declaration//GEN-END:variables
}
