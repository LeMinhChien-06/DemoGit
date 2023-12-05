package com.MMT_Shop.Tung;

import com.MMT_Shop.model.ChuyenDoi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Form_Khach_Hang extends javax.swing.JPanel {

    ArrayList<KhachHang> listKhachHang = new ArrayList<>();
    private final KhachHangSV khsv = new KhachHangSV();
    DefaultTableModel model = new DefaultTableModel();
    String maKH;

    public Form_Khach_Hang() {
        initComponents();
        fillToTable();
        taoMa();
        txtMaKH.setText(maKH);
    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt() + 10;
            if (x < 0) {
                x = x * -1;
            }
            maKH = "KH" + x;
        }
    }

    public void fillToTable() {
        listKhachHang = khsv.selectAll();
        System.out.println(listKhachHang);
        model =(DefaultTableModel) tblTTKH.getModel();
        model.setRowCount(0);
        for (KhachHang s : listKhachHang) {
            model.addRow(new Object[]{tblTTKH.getRowCount() + 1,
                s.getMaKH(), s.getTenKH(), s.isGioiTinh() == true ? "Nam" : "Nữ", s.getNgaySinh().substring(0, 10), s.getSDT(), s.getEmail(), s.getDiaChi()});
        }
    }

    public void click() {
        int row = tblTTKH.getSelectedRow();
        txtMaKH.setText(tblTTKH.getValueAt(row, 1).toString());
        txtTenKH.setText(tblTTKH.getValueAt(row, 2).toString());
        dclDate.setDate(ChuyenDoi.layNgayDate(tblTTKH.getValueAt(row, 4).toString()));
        txtSDT.setText(tblTTKH.getValueAt(row, 5).toString());
        txtEmail.setText(tblTTKH.getValueAt(row, 6).toString());
        txtDiaChi.setText(tblTTKH.getValueAt(row, 7).toString());
        if (tblTTKH.getValueAt(row, 3).toString().equalsIgnoreCase("Nam")) {
            rdoNu.setSelected(false);
            rdoNam.setSelected(true);
        } else {
            rdoNam.setSelected(false);
            rdoNu.setSelected(true);
        }

    }

    public KhachHang getModel() {
        KhachHang kh = new KhachHang();
        String ngay = ChuyenDoi.layNgayString(dclDate.getDate());
        kh.setMaKH(txtMaKH.getText());
        kh.setNgaySinh(ngay);
        kh.setTenKH(txtTenKH.getText());
        kh.setGioiTinh(rdoNam.isSelected());
        kh.setSDT(txtSDT.getText());
        kh.setEmail(txtEmail.getText());
        kh.setDiaChi(txtDiaChi.getText());
        return kh;
    }

    private void upDate() {
        KhachHang sp = addkh();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (khsv.UpdatedKH(sp) != null) {
                JOptionPane.showMessageDialog(this, "sửa thành công");
                fillToTable();
            } else {
                JOptionPane.showMessageDialog(this, "sửa thất bại");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi nút sửa");
        }
    }

    public void ADDKH() {
        KhachHang sp = addkh();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (khsv.themKH(sp) != null) {
                JOptionPane.showMessageDialog(this, "thêm thành công");
                fillToTable();
            } else {
                JOptionPane.showMessageDialog(this, "thêm thất bại");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi nút thêm");
        }
    }

    public void DeleteKH() {
        KhachHang sp = addkh();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (khsv.DeleteKH(sp) != null) {
                JOptionPane.showMessageDialog(this, "xóa thành công");
                fillToTable();
            } else {
                JOptionPane.showMessageDialog(this, "xóa thất bại");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "lỗi nút xóa");
        }
    }

    public KhachHang addkh() {
        String MaKH = txtMaKH.getText();
        String TenKH = txtTenKH.getText();
        String SDT = txtSDT.getText();
        String Email = txtEmail.getText();
        String DiaChi = txtDiaChi.getText();
        String ns = ChuyenDoi.layNgayString(dclDate.getDate());
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        boolean valuedate = true;
        if (TenKH.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Vui long nhap ten");
            txtTenKH.requestFocus();
            return null;
        }
        if (SDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui long nhap SDT");
            valuedate = false;
            return null;
        }
        if (Email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui long nhap Email");
            valuedate = false;
            return null;
        }
        if (DiaChi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "vui long nhap Dia Chi");
            valuedate = false;
            return null;
        }
        if (valuedate) {
            KhachHang kh = new KhachHang();
            kh.setMaKH(MaKH);
            kh.setTenKH(TenKH);
            kh.setGioiTinh(rdoNam.isSelected());
            kh.setNgaySinh(ns);
            kh.setSDT(SDT);
            kh.setEmail(Email);
            kh.setDiaChi(DiaChi);
            kh.setCreated_At(At);
            kh.setUpdated_At(At);
            kh.setCreated_By(mnv);
            kh.setUpdated_By(mnv);
            kh.setDeleted(mnv);
            return kh;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        lblMaKH = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        lblName2 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        lblNgaySinh = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblDiaChi = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        dclDate = new com.toedter.calendar.JDateChooser();
        btnLamMoi = new com.MMT_Shop.swing.button.Button();
        btnXoa = new com.MMT_Shop.swing.button.Button();
        btnThem = new com.MMT_Shop.swing.button.Button();
        lblTLTTKH = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        lblTimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        abc = new javax.swing.JScrollPane();
        tblTTKH = new com.MMT_Shop.swing.Table();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLSMH = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        lblMaKH.setText("Mã KH:");

        lblName2.setText("Họ và Tên:");

        lblGioiTinh.setText("Giới tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        lblNgaySinh.setText("Ngày sinh:");

        lblSDT.setText("SDT:");

        lblEmail.setText("Email:");

        lblDiaChi.setText("Địa chỉ:");

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setText("Thông tin khách hàng");

        dclDate.setDateFormatString("yyyy-MM-dd");

        btnLamMoi.setBackground(new java.awt.Color(172, 182, 229));
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("sửa");
        btnLamMoi.setRippleColor(new java.awt.Color(255, 255, 255));
        btnLamMoi.setShadowColor(new java.awt.Color(172, 182, 229));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(172, 182, 229));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXoa.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(172, 182, 229));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblMaKH)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(lblGioiTinh)
                                .addGap(7, 7, 7))
                            .addComponent(lblNgaySinh, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblName2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(79, 79, 79)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaKH)
                            .addComponent(txtTenKH)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(75, 75, 75)
                                .addComponent(rdoNu)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(dclDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(106, 106, 106))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSDT)
                            .addComponent(lblEmail)
                            .addComponent(lblDiaChi))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(txtDiaChi)
                            .addComponent(txtSDT)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMaKH)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSDT)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName2)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGioiTinh)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu)
                    .addComponent(lblDiaChi)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNgaySinh)
                    .addComponent(dclDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        lblTLTTKH.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        lblTLTTKH.setText("Thiết lập thông tin khách hàng");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lblTimKiem.setText("Tìm kiếm:");

        tblTTKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Họ tên", "Giới tính ", "Ngày sinh", "SDT", "Email", "Địa chỉ "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTTKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTTKHMouseClicked(evt);
            }
        });
        abc.setViewportView(tblTTKH);
        if (tblTTKH.getColumnModel().getColumnCount() > 0) {
            tblTTKH.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblTTKH.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTimKiem)
                .addGap(18, 18, 18)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(abc, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimKiem)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abc, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin khách hàng", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblLSMH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Ngày giao dịch", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblLSMH);
        if (tblLSMH.getColumnModel().getColumnCount() > 0) {
            tblLSMH.getColumnModel().getColumn(0).setResizable(false);
            tblLSMH.getColumnModel().getColumn(1).setResizable(false);
            tblLSMH.getColumnModel().getColumn(2).setResizable(false);
            tblLSMH.getColumnModel().getColumn(3).setResizable(false);
            tblLSMH.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lịch sử giao dịch", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblTLTTKH)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTLTTKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        upDate();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        DeleteKH();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        ADDKH();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblTTKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTTKHMouseClicked
      click();
    }//GEN-LAST:event_tblTTKHMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane abc;
    private com.MMT_Shop.swing.button.Button btnLamMoi;
    private com.MMT_Shop.swing.button.Button btnThem;
    private com.MMT_Shop.swing.button.Button btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dclDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblName2;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTLTTKH;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblLSMH;
    private com.MMT_Shop.swing.Table tblTTKH;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
