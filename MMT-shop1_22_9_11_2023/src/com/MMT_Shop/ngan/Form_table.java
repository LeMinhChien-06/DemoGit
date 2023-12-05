/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.MMT_Shop.ngan;

import com.MMT_Shop.Chien.HoaDon;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Form_table extends javax.swing.JPanel {

    static String maNV;

    NhanVienService service = new NhanVienService();
    List<NhanVien> listNhanVien = new ArrayList<>();
    private int indexRowChoice = 0;
    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<NhanVien> list = service.selectAll();
    boolean trangThai;
    MsgBox msgBox = new MsgBox();

    public static String MANV;

    public Form_table() {
        initComponents();
        fillToTable();

    }

    private void showData() {
        int row = tblNhanVien1.getSelectedRow();

        // Check if a row is selected
        if (row >= 0) {
            MANV = tblNhanVien1.getValueAt(row, 1).toString();
            // Perform actions with MANV or display it as needed
        } else {
            // Handle the case where no row is selected, e.g., show a message to the user
            System.out.println("No row selected");
        }
    }

    public void nhanVien() {
        int row = tblNhanVien1.getSelectedRow();

        trangThai = tblNhanVien1.getValueAt(row, 9).toString() == "Đang làm" ? false : true;
    }

    public void fillToTable() {
        listNhanVien = service.selectAll();
        System.out.println(listNhanVien);
        DefaultTableModel model = (DefaultTableModel) tblNhanVien1.getModel();
        model.setRowCount(0);
        for (int i = listNhanVien.size() - 1; i >= 0; i--) {
            NhanVien s = listNhanVien.get(i);
            model.addRow(new Object[]{tblNhanVien1.getRowCount() + 1,
                s.getMaNV(), s.getHoTen(), s.getSdt(), s.getEmail(),
                s.isGioiTinh() == true ? "Nam" : "Nữ", s.getSoCMND(),
                s.getDiaChi(), s.getNgaySinh().substring(0, 10),
                s.isTrangThai() == false ? "Đang làm" : "Đã nghỉ"
            });

        }
    }

    private void setForm(JPanel com) {
        p4.removeAll();
        p4.add(com);
        p4.repaint();
        p4.revalidate();
    }

    public void loatdate(ArrayList<NhanVien> list) {
        dtm = (DefaultTableModel) tblNhanVien1.getModel();
        dtm.setRowCount(0);
            for (int i = list.size() - 1; i >= 0; i--) {
            NhanVien s = list.get(i);
            dtm.addRow(new Object[]{
                tblNhanVien1.getRowCount() + 1,
                s.getMaNV(), s.getHoTen(), s.getSdt(), s.getEmail(), s.isGioiTinh() == true ? "Nam" : "Nữ", s.getSoCMND(), s.getDiaChi(), s.getNgaySinh(), s.isTrangThai() == false ? "Đang làm" : "Đã nghỉ"
            });

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p4 = new javax.swing.JPanel();
        p3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien1 = new com.MMT_Shop.swing.Table();

        setBackground(new java.awt.Color(255, 255, 255));

        p4.setBackground(new java.awt.Color(255, 255, 255));
        p4.setLayout(new java.awt.BorderLayout());

        p3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        txtTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem1ActionPerformed(evt);
            }
        });
        txtTimKiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtTimKiem1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        tblNhanVien1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MaNV", "Tên NV", "SDT", "Email", "Giới Tính", "CCCD", "Địa chỉ", "Ngày sinh", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVien1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien1);

        javax.swing.GroupLayout p3Layout = new javax.swing.GroupLayout(p3);
        p3.setLayout(p3Layout);
        p3Layout.setHorizontalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p3Layout.setVerticalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
        );

        p4.add(p3, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(p4, javax.swing.GroupLayout.DEFAULT_SIZE, 895, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(p4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem1ActionPerformed

//        clear();
    }//GEN-LAST:event_txtTimKiem1ActionPerformed

    private void txtTimKiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyReleased
         DefaultTableModel ob = (DefaultTableModel) tblNhanVien1.getModel();
            TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
            tblNhanVien1.setRowSorter(obj);
            obj.setRowFilter(RowFilter.regexFilter(txtTimKiem1.getText()));
    }//GEN-LAST:event_txtTimKiem1KeyReleased

    private void tblNhanVien1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVien1MouseClicked
        if (evt.getClickCount() == 2) {
            showData();

            setForm(new Form_Update1());
            nhanVien();

        }
    }//GEN-LAST:event_tblNhanVien1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel p3;
    private javax.swing.JPanel p4;
    private com.MMT_Shop.swing.Table tblNhanVien1;
    private javax.swing.JTextField txtTimKiem1;
    // End of variables declaration//GEN-END:variables
}
