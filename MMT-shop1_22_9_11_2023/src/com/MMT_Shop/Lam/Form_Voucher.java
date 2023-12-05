package com.MMT_Shop.Lam;

import com.MMT_Shop.Dao.VoucherDAO;
import com.MMT_Shop.model.ChuyenDoi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.Random;
import java.time.LocalDate;
import java.util.Date;
import java.util.Timer;
import javax.swing.JOptionPane;

public class Form_Voucher extends javax.swing.JPanel {

    VoucherDAO data = new VoucherDAO();
    LocalDate currentDate = LocalDate.now();
    Timer timer;
    String code;

    public Form_Voucher() {

        initComponents();
        loadData();
        taoMa();
    }

    private void taoMa() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(900) + 10;
            code = "VC" + x;
        }
    }

    void loadData() {
        DefaultTableModel model = (DefaultTableModel) tblVou.getModel();
        model.setRowCount(0);
        ArrayList<Voucher> vouchers = data.selectALL();

        for (Voucher voucher : vouchers) {
            String status = "Sắp bát đầu";

            if (voucher.getStartDate().isEqual(currentDate)) {
                status = "Đang diễn ra";
            } else if (voucher.getEndDate().isBefore(currentDate)) {
                status = "Đã kết thúc";
            } else if (voucher.getStartDate().isBefore(currentDate) || voucher.getStartDate().isEqual(currentDate)) {
                status = "Đang diễn ra";
            }
            voucher.setStatus(status);
            data.updatVC(voucher.getCode(), voucher.getStatus());
            model.addRow(new Object[]{
                tblVou.getRowCount() + 1, voucher.getCode(), voucher.getTen(), voucher.getStartDate(),
                voucher.getEndDate(), voucher.getSoLuong(), voucher.getStatus(), voucher.getPhanTramGiam(),
                voucher.getGiaTriHoaDonToiThieu(), voucher.getSoTienGiamToiDa(), voucher.getCreated_At().substring(0, 10),
                voucher.getCreated_By()
            });
        }
    }

    public Voucher getmodel() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        Voucher tt = new Voucher();
        tt.setCode(code);
        tt.setTen(txtTen.getText());
        tt.setStartDate(ChuyenDoi.layNgayLocalDate(dcsNgayBatDau.getDate()));
        tt.setEndDate(ChuyenDoi.layNgayLocalDate(dcsNgayKet.getDate()));
        LocalDate ngayTao = LocalDate.now();
        tt.setCreated_At(ngayTao.toString());
        tt.setStatus("Sắp diễn ra");
        tt.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        tt.setCreated_At(At);
        tt.setUpdated_At(At);
        tt.setCreated_By(mnv);
        tt.setUpdated_By(mnv);
        tt.setGiaTriHoaDonToiThieu(Integer.valueOf(txtHoaDonTT.getText()));
        tt.setPhanTramGiam(Integer.valueOf(txtPhanTram.getText()));
        tt.setSoTienGiamToiDa(Integer.valueOf(txtTienGiamTD.getText()));
        return tt;
    }

    private void insert() {
        Voucher tt = getmodel();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (data.adddata(tt) != null) {
                JOptionPane.showMessageDialog(this, "Thêm thành công, ma: " + code);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm dữ liệu");
        }
    }

    private void click() {
        int row = tblVou.getSelectedRow();
        txtTen.setText(tblVou.getValueAt(row, 2).toString());
        txtSoLuong.setText(tblVou.getValueAt(row, 5).toString());
        txtHoaDonTT.setText(tblVou.getValueAt(row, 8).toString());
        txtPhanTram.setText(tblVou.getValueAt(row, 7).toString());
        txtTienGiamTD.setText(tblVou.getValueAt(row, 9).toString());
        txtTrangThai.setText(tblVou.getValueAt(row, 6).toString());
        dcsNgayBatDau.setDate(ChuyenDoi.layNgayDate(tblVou.getValueAt(row, 3).toString()));
        dcsNgayKet.setDate(ChuyenDoi.layNgayDate(tblVou.getValueAt(row, 4).toString()));
        code = tblVou.getValueAt(row, 2).toString();
    }

    void xoa() {
        int chon = JOptionPane.showConfirmDialog(this, "ban muon xoa khong");

        if (chon == 0) {
            data.DeleteData(code);
            JOptionPane.showMessageDialog(this, "xoa thanh cong");
            loadData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "khong xoa");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtHoaDonTT = new com.MMT_Shop.swing.textfield.TextField();
        txtPhanTram = new com.MMT_Shop.swing.textfield.TextField();
        txtTienGiamTD = new com.MMT_Shop.swing.textfield.TextField();
        txtTen = new com.MMT_Shop.swing.textfield.TextField();
        txtSoLuong = new com.MMT_Shop.swing.textfield.TextField();
        jPanel1 = new javax.swing.JPanel();
        dcsNgayBatDau = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        dcsNgayKet = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new com.MMT_Shop.swing.button.Button();
        btnXoa = new com.MMT_Shop.swing.button.Button();
        btnNew = new com.MMT_Shop.swing.button.Button();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new com.MMT_Shop.swing.textfield.TextField();
        txtTrangThai = new com.MMT_Shop.swing.textfield.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVou = new com.MMT_Shop.swing.Table();

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chương trình giảm giá", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel12.setForeground(new java.awt.Color(255, 255, 255));

        txtHoaDonTT.setLabelText("Hóa đơn tối thiểu");

        txtPhanTram.setLabelText("Phần trăm giảm");

        txtTienGiamTD.setLabelText("Số tiền Giảm tối đa");

        txtTen.setLabelText("Tên khuyến mãi ");

        txtSoLuong.setLabelText("Số lượng ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ngày bắt đầu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(dcsNgayBatDau);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ngày kết thúc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));
        jPanel3.add(dcsNgayKet);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnThem.setBackground(new java.awt.Color(172, 182, 229));
        btnThem.setForeground(new java.awt.Color(245, 245, 245));
        btnThem.setText("Thêm");
        btnThem.setPreferredSize(new java.awt.Dimension(74, 41));
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem);

        btnXoa.setBackground(new java.awt.Color(172, 182, 229));
        btnXoa.setForeground(new java.awt.Color(245, 245, 245));
        btnXoa.setText("Xóa ");
        btnXoa.setPreferredSize(new java.awt.Dimension(74, 41));
        btnXoa.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXoa.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa);

        btnNew.setBackground(new java.awt.Color(172, 182, 229));
        btnNew.setForeground(new java.awt.Color(245, 245, 245));
        btnNew.setText("Làm mới ");
        btnNew.setRippleColor(new java.awt.Color(255, 255, 255));
        btnNew.setShadowColor(new java.awt.Color(172, 182, 229));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel2.add(btnNew);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTienGiamTD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                    .addComponent(txtPhanTram, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtHoaDonTT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(txtHoaDonTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addComponent(txtTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txtTienGiamTD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh sách KM"));

        txtTimKiem.setLabelText("Tìm kiếm theo các trường");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        txtTrangThai.setEditable(false);
        txtTrangThai.setBackground(new java.awt.Color(255, 255, 255));
        txtTrangThai.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTrangThai.setLabelText("Trạng thái");

        tblVou.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên ", "Ngày BD", "Ngày KT", "Số lượng", "Trạng thái ", "% Giảm", "Hóa đơn tối thiểu ", "Só tiền giảm tối đa", "Ngày tạo", "Người tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVou.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVouMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVou);
        if (tblVou.getColumnModel().getColumnCount() > 0) {
            tblVou.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSP1ActionPerformed
        
    }//GEN-LAST:event_btnThemSP1ActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
    xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        resetForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblVouMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVouMouseClicked
        click();
    }//GEN-LAST:event_tblVouMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tblVou.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tblVou.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTimKiem.getText()));
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
         taoMa();
        insert();
    }//GEN-LAST:event_btnThemActionPerformed
    private void resetForm() {
        taoMa();
        txtTen.setText("");
        dcsNgayBatDau.setDate(null);
        dcsNgayKet.setDate(null);
        txtSoLuong.setText("");
        txtTrangThai.setText("");
        txtPhanTram.setText("");
        txtHoaDonTT.setText("");
        txtTienGiamTD.setText("");
        tblVou.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button btnNew;
    private com.MMT_Shop.swing.button.Button btnThem;
    private com.MMT_Shop.swing.button.Button btnXoa;
    private com.toedter.calendar.JDateChooser dcsNgayBatDau;
    private com.toedter.calendar.JDateChooser dcsNgayKet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.MMT_Shop.swing.Table tblVou;
    private com.MMT_Shop.swing.textfield.TextField txtHoaDonTT;
    private com.MMT_Shop.swing.textfield.TextField txtPhanTram;
    private com.MMT_Shop.swing.textfield.TextField txtSoLuong;
    private com.MMT_Shop.swing.textfield.TextField txtTen;
    private com.MMT_Shop.swing.textfield.TextField txtTienGiamTD;
    private com.MMT_Shop.swing.textfield.TextField txtTimKiem;
    private com.MMT_Shop.swing.textfield.TextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
