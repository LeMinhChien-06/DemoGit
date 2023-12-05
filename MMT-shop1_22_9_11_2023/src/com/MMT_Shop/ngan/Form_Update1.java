package com.MMT_Shop.ngan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.table.DefaultTableModel;

public class Form_Update1 extends javax.swing.JPanel {

    NhanVienService service = new NhanVienService();
    //List<NhanVien> listNhanVien = new ArrayList<>();

    private int indexRowChoice = 0;
    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<NhanVien> list = service.selectAll();
    boolean trangThai;
    MsgBox msgBox = new MsgBox();

    public Form_Update1() {
        initComponents();
        fillToForm();
    }

    private void setForm(JPanel com) {
        jPanel2.removeAll();
        jPanel2.add(com);
        jPanel2.repaint();
        jPanel2.revalidate();
    }

    public void fillToForm() {
        ArrayList<NhanVien> list = service.timKiemMa(Form_table.MANV);
        // listNhanVien = service.timKiemMa(Form_table.MANV);
        System.out.println(Form_table.MANV);
        for (NhanVien s : list) {
            txtMaNV1.setText(s.getMaNV());
            txtTen1.setText(s.getHoTen());
            txtCMND1.setText(s.getSoCMND());
            boolean gt = s.isGioiTinh();
            rdoNam1.setSelected(gt);
            rdoNu1.setSelected(!gt);
            txtEmail1.setText(s.getEmail());
            txtDiaChi1.setText(s.getDiaChi());
            txtSDT1.setText(s.getSdt());
            Date ngay = Model_Form.layNgayDate(s.getNgaySinh());
            txtNgaySinh1.setDate(ngay);

        }

    }

    protected JRootPane rootPane;

    public boolean validateForm() {
        if (txtTen1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống");
            txtTen1.requestFocus();
            return false;
        }
//
        if (txtCMND1.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "CCCD khong duoc de trong");
            txtCMND1.requestFocus();
            return false;
        }

        if (txtEmail1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "email khong duoc de trong");
            txtEmail1.requestFocus();
            return false;
        } else if (!(txtEmail1.getText()).matches("^[\\w-_\\.]+\\@[\\w&&[^0-9]]+\\.com$")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng email", "Error", 1);
            txtEmail1.requestFocus();
            return false;
        }
        if (txtSDT1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Sdt khong duoc de trong");
            txtSDT1.requestFocus();
            return false;
        }

        if (txtDiaChi1.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Dia chi khong duoc de trong");
            txtDiaChi1.requestFocus();
            return false;
        }
        return true;
    }

    public NhanVien getModel() {
        NhanVien nhanVien = new NhanVien();
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        String mnv = "NV01";
        String ngay = Model_Form.layNgayString(txtNgaySinh1.getDate());
        nhanVien.setMaNV(txtMaNV1.getText());
        nhanVien.setHoTen(txtTen1.getText());
        nhanVien.setSdt(txtSDT1.getText());
        nhanVien.setDiaChi(txtDiaChi1.getText());
        nhanVien.setGioiTinh(rdoNam1.isSelected());
        nhanVien.setSoCMND(txtCMND1.getText());
        nhanVien.setEmail(txtEmail1.getText());
        nhanVien.setNgaySinh(ngay);
        System.out.println(txtNgaySinh1.getDate());
        nhanVien.setUpdated_At(At);
        nhanVien.setUpdated_By(mnv);
        nhanVien.setDeleted(mnv);
        return nhanVien;
    }

    private void update() {
        NhanVien nv = getModel();
        try {
            if (service.UpdateNhanVien(nv) != null) {
                JOptionPane.showMessageDialog(this, "sửa thanh cong");

            } else {
                JOptionPane.showMessageDialog(this, "sửa that bai");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " loi nut sửa");
        }
    }

    private NhanVien adddelete() {
//        NhanVien nv = new NhanVien();
        if (trangThai == false) {
            NhanVien nv = new NhanVien();
            nv.setTrangThai(true);
            nv.setMaNV(txtMaNV1.getText().trim());
            return nv;
        }
//        else{
//            NhanVien nv = new NhanVien();
//            nv.setTrangThai(false);
//            nv.setMaNV(txtMaNV1.getText().trim());
//            return nv;
//        }
        return null;

    }

    public void clear() {
        txtMaNV1.setText("");
        txtTen1.setText("");
        txtSDT1.setText("");
        txtDiaChi1.setText("");
        txtCMND1.setText("");
        txtEmail1.setText("");
        txtNgaySinh1.setDateFormatString("");
        rdoNam1.setSelected(true);
//        
    }
//

    private void deletednv() {
        System.out.println("Nhân viên");
        NhanVien nv = adddelete();

        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn chuyển đổi trạng thái nhân viên", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (service.DeleteNhanVien(nv) != null) {
                JOptionPane.showMessageDialog(this, "Chuyển đổi thanh cong");

            } else {
                JOptionPane.showMessageDialog(this, "Chuyển đổi that bai");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi xoa");
        }

    }
//

    public void deleteForm() {
        String maCd = txtMaNV1.getText();
        if (maCd.isEmpty()) {
            msgBox.alert(this, "Vui Lòng Nhập Mã Nhân Viên");
        } else {
            boolean choice = msgBox.confirm(this, "Bạn có muốn xóa Form này");
            if (choice) {
                msgBox.alert(this, service.deleteForm(maCd));
            };

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        rdoNam1 = new javax.swing.JRadioButton();
        rdoNu1 = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        txtEmail1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDiaChi1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaNV1 = new javax.swing.JTextField();
        txtTen1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtCMND1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSDT1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNgaySinh1 = new com.toedter.calendar.JDateChooser();
        btnBack = new com.MMT_Shop.swing.button.Button();
        btnSua = new com.MMT_Shop.swing.button.Button();
        btnXoa = new com.MMT_Shop.swing.button.Button();
        btnXoaHoSo = new com.MMT_Shop.swing.button.Button();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setText("Giới Tính");

        rdoNam1.setBackground(new java.awt.Color(255, 255, 255));
        rdoNam1.setSelected(true);
        rdoNam1.setText("Nam");

        rdoNu1.setBackground(new java.awt.Color(255, 255, 255));
        rdoNu1.setText("Nữ");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setText("Email");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel18.setText("Địa Chỉ");

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel19.setText("Thông tin nhân viên");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel20.setText("MaNV");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel21.setText("Tên Nhân Viên");

        txtMaNV1.setEditable(false);

        txtTen1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        txtTen1.setSelectionColor(new java.awt.Color(0, 0, 0));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel22.setText("Mã Định Danh (Số CMND/CCCD)");

        txtCMND1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        txtCMND1.setSelectionColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCMND1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                    .addComponent(txtTen1)
                    .addComponent(txtMaNV1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(26, 26, 26)
                .addComponent(txtMaNV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(26, 26, 26)
                .addComponent(txtTen1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(txtCMND1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel17.setText("Số Điện Thoại");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setText("Ngày Sinh");

        txtNgaySinh1.setDateFormatString("yyyy-MM-dd");

        btnBack.setBackground(new java.awt.Color(172, 182, 229));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Trở về ");
        btnBack.setRippleColor(new java.awt.Color(255, 255, 255));
        btnBack.setShadowColor(new java.awt.Color(172, 182, 229));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(172, 182, 229));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa ");
        btnSua.setRippleColor(new java.awt.Color(255, 255, 255));
        btnSua.setShadowColor(new java.awt.Color(172, 182, 229));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
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

        btnXoaHoSo.setBackground(new java.awt.Color(172, 182, 229));
        btnXoaHoSo.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHoSo.setText("Xóa hồ sơ");
        btnXoaHoSo.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXoaHoSo.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXoaHoSo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHoSoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 35, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(129, 129, 129)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSDT1)
                        .addComponent(txtNgaySinh1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                        .addComponent(txtEmail1))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(rdoNam1)
                                .addGap(45, 45, 45)
                                .addComponent(rdoNu1))
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnXoaHoSo, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(jLabel15)
                                .addGap(37, 37, 37)
                                .addComponent(txtNgaySinh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addGap(26, 26, 26)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDiaChi1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoNam1)
                                    .addComponent(rdoNu1))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel18)))
                        .addGap(34, 34, 34)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoaHoSo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(56, 56, 56))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
         setForm(new Form_table());
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
          if (validateForm()) {
            update();
            JPanel parent = null;
            Form_table kt = new Form_table();
            kt.setVisible(true);
            kt.fillToTable();
            setForm(new Form_table());
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
         deletednv();
        JPanel parent = null;
        Form_table kt = new Form_table();
        kt.setVisible(true);
        kt.fillToTable();
//        clear();
        setForm(new Form_table());
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXoaHoSoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHoSoActionPerformed
        deleteForm();
        JPanel parent = null;
        Form_table kt = new Form_table();
        kt.setVisible(true);
        kt.fillToTable();
        clear();
    }//GEN-LAST:event_btnXoaHoSoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button btnBack;
    private com.MMT_Shop.swing.button.Button btnSua;
    private com.MMT_Shop.swing.button.Button btnXoa;
    private com.MMT_Shop.swing.button.Button btnXoaHoSo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton rdoNam1;
    private javax.swing.JRadioButton rdoNu1;
    private javax.swing.JTextField txtCMND1;
    private javax.swing.JTextField txtDiaChi1;
    private javax.swing.JTextField txtEmail1;
    private javax.swing.JTextField txtMaNV1;
    private com.toedter.calendar.JDateChooser txtNgaySinh1;
    private javax.swing.JTextField txtSDT1;
    private javax.swing.JTextField txtTen1;
    // End of variables declaration//GEN-END:variables
}
