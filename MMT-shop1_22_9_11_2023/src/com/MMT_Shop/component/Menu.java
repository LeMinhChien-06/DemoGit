package com.MMT_Shop.component;

import com.MMT_Shop.event.EventMenuSelect;
import com.MMT_Shop.model.Model_Menu;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

public class Menu extends javax.swing.JPanel {

    private EventMenuSelect event;

    public void addEventMenuSelect(EventMenuSelect event){
        this.event=event;
        listMenu1.addEventMenuSelect(event);
    }
    
    public Menu() {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        inti();
    }

    private void inti() {
        listMenu1.addItem(new Model_Menu("1", "Bán hàng", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("2", "Hóa đơn", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("3", "Sảm phẩm", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("4", "Voucher", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("5", "Thống kê", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("6", "Nhân viên", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("7", "Khách hàng", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("8", "Đăng xuất", Model_Menu.MenuType.MENU));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new com.MMT_Shop.swing.ListMenu<>();

        setPreferredSize(new java.awt.Dimension(215, 380));

        pnlMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/logo.png"))); // NOI18N

        javax.swing.GroupLayout pnlMovingLayout = new javax.swing.GroupLayout(pnlMoving);
        pnlMoving.setLayout(pnlMovingLayout);
        pnlMovingLayout.setHorizontalGroup(
            pnlMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMovingLayout.createSequentialGroup()
                .addGroup(pnlMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMovingLayout.setVerticalGroup(
            pnlMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMovingLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintChildren(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#ACB6E5"), 0, getHeight(), Color.decode("#74ebd5"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(graphics);
    }
    private int x;
    private int y;

    public void initMoving(JFrame fram) {
        pnlMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
        pnlMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                fram.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.MMT_Shop.swing.ListMenu<String> listMenu1;
    private javax.swing.JPanel pnlMoving;
    // End of variables declaration//GEN-END:variables
}
