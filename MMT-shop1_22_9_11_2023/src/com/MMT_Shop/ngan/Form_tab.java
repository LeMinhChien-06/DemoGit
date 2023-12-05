/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.MMT_Shop.ngan;

import com.MMT_Shop.ngan.Form_Add;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author DELL
 */
public class Form_tab extends javax.swing.JPanel {

    NhanVienService service = new NhanVienService();
    List<NhanVien> listNhanVien = new ArrayList<>();
    private int indexRowChoice = 0;
    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<NhanVien> list = service.selectAll();
    boolean trangThai;
    MsgBox msgBox = new MsgBox();

    public Form_tab() {
        initComponents();
        setForm(new Form_table());
        fillToTable();
    }

    private void setForm(JPanel com) {
        p3.removeAll();
        p3.add(com);
        p3.repaint();
        p3.revalidate();
    }

    public void fillToTable() {
        listNhanVien = service.timKiemMa(Form_table.MANV);
        System.out.println(listNhanVien);
        for (NhanVien s : listNhanVien) {
            s.getMaNV();
            s.getHoTen();
            s.getSoCMND();
            s.isGioiTinh();
            s.getNgaySinh();
            s.getSdt();
            s.getEmail();
            s.getDiaChi();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        P1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        p2 = new javax.swing.JPanel();
        p3 = new javax.swing.JPanel();
        btnThem = new com.MMT_Shop.swing.button.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        P1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setText("DANH SÁCH NHÂN VIÊN");

        p2.setBackground(new java.awt.Color(255, 255, 255));

        p3.setBackground(new java.awt.Color(255, 255, 255));
        p3.setLayout(new java.awt.BorderLayout());

        btnThem.setBackground(new java.awt.Color(172, 182, 229));
        btnThem.setForeground(new java.awt.Color(245, 245, 245));
        btnThem.setText("Thêm Nhân Viên");
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p3, javax.swing.GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)))
                .addContainerGap())
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout P1Layout = new javax.swing.GroupLayout(P1);
        P1.setLayout(P1Layout);
        P1Layout.setHorizontalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71))
        );
        P1Layout.setVerticalGroup(
            P1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, P1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(P1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
       setForm(new Form_Add());
        JPanel parent = null;
        Form_table kt = new Form_table();
        kt.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel P1;
    private com.MMT_Shop.swing.button.Button btnThem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p3;
    // End of variables declaration//GEN-END:variables
}
