package com.MMT_Shop.form.SP;

import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.Dao.ThuocTinhDAO;
import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.HinhAnh;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.EnTiTy.SanPhamCT;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FormChiTietSP extends javax.swing.JDialog {

    private final SanPhamDAO dataSP = new SanPhamDAO();
    private final ThuocTinhDAO dataTT = new ThuocTinhDAO();
    String maHinhAnh;
    boolean lc;
    String code = null;

    public FormChiTietSP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        loadCBX();
        addForm();

    }

    private void loadCBX() {
        fillComboBoxCL();
        fillComboBoxH();
        fillComboBoxKT();
        fillComboBoxLK();
        fillComboBoxMS();
    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt() + 10;
            if (x < 0) {
                x = x * -1;
            }
            code = x + "";
            maHinhAnh = "HA" + code;
        }
    }

    private void addForm() {
        SanPhamCT data = dataSP.SelectSanPhamCTTheoMaSPCT(FormAddSanPhamCT.maSPCT);

        cbxLoaiKhung.setSelectedItem(data.getLoaiKhung());
        cbxHang.setSelectedItem(data.getThuongHieu());
        cbxKichThuoc.setSelectedItem(data.getKichThuoc());
        cbxChatLieu.setSelectedItem(data.getChatLieu());
        cbxMauSac.setSelectedItem(data.getMauSac());
        txtSoLuong.setText(data.getSoLuong() + "");
        txtGia.setText(data.getGia() + "");
        UpQR(data.getAnhQR());
        Upimg(data.getHinhAnh());
        lblIng.setText(data.getHinhAnh());

    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void insertHinhAnh() {

        HinhAnh ha = addAnh();
        try {
            if (dataTT.InsertHinhAnh(ha) != null) {
                //showMessageBox("Thêm thành công");
                //addRowTableSPCT();
                taoMa();
            } else {
                //showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    private HinhAnh addAnh() {
        String ten = lblIng.getText();
        String ma = maHinhAnh;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        String mnv = "NV01";
        boolean validated = true;
        if (ten.isEmpty()) {
            showMessageBox("Vui lòng chọn hình ảnh");
            validated = false;
            return null;

        }
        if (validated) {
            HinhAnh ha = new HinhAnh();
            ha.setMa(ma);
            ha.setTen(ten);
            ha.setCreated_At(At);
            ha.setUpdated_At(At);
            ha.setCreated_By(mnv);
            ha.setUpdated_By(mnv);
            ha.setDeleted(mnv);
            return ha;
        }
        return null;
    }

    private SanPhamCT getForm() {
        String maspct = FormAddSanPhamCT.maSPCT;
        String loaiKhung = cbxLoaiKhung.getSelectedItem().toString();
        String Mau = cbxMauSac.getSelectedItem().toString();
        String hang = cbxHang.getSelectedItem().toString();
        String kichThuoc = cbxKichThuoc.getSelectedItem().toString();
        String chatLieu = cbxChatLieu.getSelectedItem().toString();
        String gia = txtGia.getText();
        String soLuong = txtSoLuong.getText();
        String maQR = maspct;
        String hinhAnh = lblIng.getText();
        // System.out.println(hinhAnh);
        boolean trangThai = true;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        boolean validated = true;
        if (gia.isEmpty()) {
            showMessageBox("Vui lòng nhập giá");
            validated = false;
            return null;
        }
        if (soLuong.isEmpty()) {
            showMessageBox("Vui lòng nhập số lượng");
            validated = false;
            return null;
        }
        if (lc != true) {
            if (hinhAnh.isEmpty()) {
                showMessageBox("Vui lòng chọn hình");
                validated = false;
                return null;
            }
        }
        if (validated) {
            SanPhamCT sp = new SanPhamCT();
            sp.setMaSPCT(maspct);
            sp.setLoaiKhung(loaiKhung);
            sp.setMauSac(Mau);
            sp.setThuongHieu(hang);
            sp.setKichThuoc(kichThuoc);
            sp.setChatLieu(chatLieu);
            sp.setGia(Float.valueOf(gia));
            sp.setSoLuong(Integer.valueOf(soLuong));
            sp.setAnhQR(maQR);
            sp.setUpdated_At(At);
            sp.setUpdated_By(mnv);
            sp.setDeleted(mnv);
            sp.setTrangThai(trangThai);
            sp.setHinhAnh(hinhAnh);
            return sp;
        }
        return null;
    }

    private void upDatedSPCT() {
        //System.out.println("Sản phẩm");
        SanPhamCT sp = getForm();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataSP.UpDateSPCT(sp) != null) {
                showMessageBox("sửa thành công");
            } else {
                showMessageBox("sửa thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút sửa");
        }
    }

    public void UpQR(String image) {
        ImageIcon icon1 = new ImageIcon("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\QR\\" + image + ".PNG");
        Image im = icon1.getImage();
        System.out.println(image);
        ImageIcon icon = new ImageIcon(im.getScaledInstance(lblQR.getWidth(), lblQR.getHeight(), im.SCALE_SMOOTH));
        lblQR.setIcon(icon);
    }

    public void Upimg(String image) {
        ImageIcon icon1 = new ImageIcon("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\SP\\" + image);
        Image im = icon1.getImage();
        ImageIcon icon = new ImageIcon(im.getScaledInstance(lblIng.getWidth(), lblIng.getHeight(), im.SCALE_SMOOTH));
        lblIng.setIcon(icon);
    }

    void fillComboBoxLK() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxLoaiKhung.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<LoaiKhung> data = dataTT.SelectKhung();
        for (int i = data.size() - 1; i >= 0; i--) {
            LoaiKhung tt = data.get(i);
            modelCBX.addElement(tt.getTen());
        }

    }

    void fillComboBoxMS() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxMauSac.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<MauSac> data = dataTT.SelectMauSac();
        for (int i = data.size() - 1; i >= 0; i--) {
            MauSac tt = data.get(i);
            modelCBX.addElement(tt.getTen());
        }

    }

    void fillComboBoxH() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxHang.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<Hang> data = dataTT.SelectHang();
        for (int i = data.size() - 1; i >= 0; i--) {
            Hang tt = data.get(i);
            modelCBX.addElement(tt.getTen());
        }

    }

    void fillComboBoxKT() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxKichThuoc.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<KichThuoc> data = dataTT.SelectKichThuoc();
        for (int i = data.size() - 1; i >= 0; i--) {
            KichThuoc tt = data.get(i);
            modelCBX.addElement(tt.getTen());
        }

    }

    void fillComboBoxCL() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxChatLieu.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<ChatLieu> data = dataTT.SelectChatLieu();
        for (int i = data.size() - 1; i >= 0; i--) {
            ChatLieu tt = data.get(i);
            modelCBX.addElement(tt.getTen());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbxLoaiKhung = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxKichThuoc = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxMauSac = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxHang = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxChatLieu = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        txtSoLuong = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblQR = new javax.swing.JLabel();
        lblIng = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnUpdated = new com.MMT_Shop.swing.button.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(234, 234, 234));

        jLabel1.setText("Số lượng");

        jLabel2.setText("Loại khung ");

        jLabel3.setText("Màu sắc");

        jLabel4.setText("Hãng");

        jLabel5.setText("Kích thước");

        jLabel6.setText("Chất liệu");

        lblQR.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblIng.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblIng.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIngMouseClicked(evt);
            }
        });

        jLabel9.setText("Giá tiền");

        btnUpdated.setBackground(new java.awt.Color(172, 182, 229));
        btnUpdated.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdated.setText("Sửa");
        btnUpdated.setRippleColor(new java.awt.Color(255, 255, 255));
        btnUpdated.setShadowColor(new java.awt.Color(172, 182, 229));
        btnUpdated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdatedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtSoLuong)
                                .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cbxMauSac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxKichThuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblQR, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(lblIng, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGia)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdated, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
                        .addComponent(btnUpdated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblIng, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdatedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdatedActionPerformed
        upDatedSPCT();

    }//GEN-LAST:event_btnUpdatedActionPerformed

    private void lblIngMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIngMouseClicked
        try {
            JFileChooser jfc = new JFileChooser("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\SP");
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            Image img = ImageIO.read(file);
            String tenA = file.toString().substring(59);
            //System.out.println(tenA);
            lblIng.setText(tenA);
            int Width = lblIng.getWidth();
            int height = lblIng.getHeight();
            lblIng.setIcon(new ImageIcon(img.getScaledInstance(Width, height, 0)));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblIngMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormChiTietSP dialog = new FormChiTietSP(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button btnUpdated;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxChatLieu;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxHang;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxKichThuoc;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxLoaiKhung;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxMauSac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblIng;
    private javax.swing.JLabel lblQR;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
