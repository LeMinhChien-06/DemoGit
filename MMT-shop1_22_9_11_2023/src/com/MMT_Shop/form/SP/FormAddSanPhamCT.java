package com.MMT_Shop.form.SP;

import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.Dao.ThuocTinhDAO;
import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.HinhAnh;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.EnTiTy.SanPham;
import com.MMT_Shop.EnTiTy.SanPhamCT;
import java.awt.Frame;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class FormAddSanPhamCT extends javax.swing.JPanel {

    private final ThuocTinhDAO dataTT = new ThuocTinhDAO();
    // private DefaultTableModel model = new DefaultTableModel();
    private final SanPhamDAO dataSP = new SanPhamDAO();
    public static String maSPCT;
    String maHinhAnh;
    String code = null;
    boolean lc;

    public FormAddSanPhamCT() {
        initComponents();
        init();
       
    }

    private void init() {
        taoMa();
        loadCBX();
//        addRowTableSPCT();
//        tableSPCTMOD();
    }

    private void loadCBX() {
        fillComboBoxSP();
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
            maSPCT = "SPCT" + code;
            maHinhAnh = "HA" + code;
        }
    }

    private void addQRCode() {
        try {
            ByteArrayOutputStream out = QRCode.from(maSPCT)
                    .to(ImageType.PNG).stream();
            String f_name = maSPCT;
            String Path_name = "G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\QR\\";
            FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
            fout.write(out.toByteArray());
            fout.flush();
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private HinhAnh addAnh() {
        String ten = lblHinhAnh.getText();
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

    private SanPhamCT addSPCT() {

        String maspct = maSPCT;
        String tenSP = cbxTenSP.getSelectedItem().toString();
        String loaiKhung = cbxLoaiKhung.getSelectedItem().toString();
        String Mau = cbxMauSac.getSelectedItem().toString();
        String hang = cbxHang.getSelectedItem().toString();
        String kichThuoc = cbxKichThuoc.getSelectedItem().toString();
        String chatLieu = cbxChatLieu.getSelectedItem().toString();
        String gia = txtGia.getText();
        String soLuong = txtSoLuong.getText();
        String maQR = maspct;
        String hinhAnh = lblHinhAnh.getText();
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
            sp.setTenSP(tenSP);
            sp.setLoaiKhung(loaiKhung);
            sp.setMauSac(Mau);
            sp.setThuongHieu(hang);
            sp.setKichThuoc(kichThuoc);
            sp.setChatLieu(chatLieu);
            sp.setGia(Float.valueOf(gia));
            sp.setSoLuong(Integer.valueOf(soLuong));
            sp.setAnhQR(maQR);
            sp.setCreated_At(At);
            sp.setUpdated_At(At);
            sp.setCreated_By(mnv);
            sp.setUpdated_By(mnv);
            sp.setDeleted(mnv);
            sp.setTrangThai(trangThai);
            sp.setHinhAnh(hinhAnh);
            return sp;
        }
        return null;
    }

    private void insertSPCT() {
        //System.out.println("Sản phẩm");
        SanPhamCT sp = addSPCT();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataSP.InsertSanPhamCT(sp) != null) {
                showMessageBox("Thêm thành công");
                //addRowTableSPCT();
                addQRCode();
                taoMa();

            } else {
                showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    void fillComboBoxSP() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxTenSP.getModel();
        modelCBX.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<SanPham> data = dataSP.SelectSanPham();
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPham sp = data.get(i);
            modelCBX.addElement(sp.getTen());
        }

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

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        addSanPham = new com.MMT_Shop.swing.button.Button();
        addKhung = new com.MMT_Shop.swing.button.Button();
        addMau = new com.MMT_Shop.swing.button.Button();
        addHang = new com.MMT_Shop.swing.button.Button();
        addKichThuoc = new com.MMT_Shop.swing.button.Button();
        addChatLieu = new com.MMT_Shop.swing.button.Button();
        cbxTenSP = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxLoaiKhung = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxMauSac = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxHang = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxKichThuoc = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        cbxChatLieu = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        lblHinhAnh = new javax.swing.JLabel();
        cmd2 = new com.MMT_Shop.swing.button.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1060, 586));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel2.setText("Tên sản phẩm: ");

        jLabel4.setText("Giá bán:");

        jLabel5.setText("Loại khung:");

        jLabel7.setText("Màu sắc:");

        jLabel8.setText("Kích Thước:");

        jLabel9.setText("Hãng:");

        jLabel10.setText("Chất liệu:");

        jLabel11.setText("Số lượng : ");

        addSanPham.setBackground(new java.awt.Color(116, 235, 213));
        addSanPham.setForeground(new java.awt.Color(245, 245, 245));
        addSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addSanPham.setRippleColor(new java.awt.Color(255, 255, 255));
        addSanPham.setShadowColor(new java.awt.Color(116, 235, 213));
        addSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSanPhamActionPerformed(evt);
            }
        });

        addKhung.setBackground(new java.awt.Color(116, 235, 213));
        addKhung.setForeground(new java.awt.Color(245, 245, 245));
        addKhung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addKhung.setRippleColor(new java.awt.Color(255, 255, 255));
        addKhung.setShadowColor(new java.awt.Color(116, 235, 213));
        addKhung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addKhungActionPerformed(evt);
            }
        });

        addMau.setBackground(new java.awt.Color(116, 235, 213));
        addMau.setForeground(new java.awt.Color(245, 245, 245));
        addMau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addMau.setRippleColor(new java.awt.Color(255, 255, 255));
        addMau.setShadowColor(new java.awt.Color(116, 235, 213));
        addMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMauActionPerformed(evt);
            }
        });

        addHang.setBackground(new java.awt.Color(116, 235, 213));
        addHang.setForeground(new java.awt.Color(245, 245, 245));
        addHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addHang.setRippleColor(new java.awt.Color(255, 255, 255));
        addHang.setShadowColor(new java.awt.Color(116, 235, 213));
        addHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addHangActionPerformed(evt);
            }
        });

        addKichThuoc.setBackground(new java.awt.Color(116, 235, 213));
        addKichThuoc.setForeground(new java.awt.Color(245, 245, 245));
        addKichThuoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addKichThuoc.setRippleColor(new java.awt.Color(255, 255, 255));
        addKichThuoc.setShadowColor(new java.awt.Color(116, 235, 213));
        addKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addKichThuocActionPerformed(evt);
            }
        });

        addChatLieu.setBackground(new java.awt.Color(116, 235, 213));
        addChatLieu.setForeground(new java.awt.Color(245, 245, 245));
        addChatLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addChatLieu.setRippleColor(new java.awt.Color(255, 255, 255));
        addChatLieu.setShadowColor(new java.awt.Color(116, 235, 213));
        addChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addChatLieuActionPerformed(evt);
            }
        });

        lblHinhAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Image_2.png"))); // NOI18N
        lblHinhAnh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhAnhMouseClicked(evt);
            }
        });

        cmd2.setBackground(new java.awt.Color(172, 182, 229));
        cmd2.setForeground(new java.awt.Color(255, 255, 255));
        cmd2.setText("Thêm Sản phẩm");
        cmd2.setRippleColor(new java.awt.Color(255, 255, 255));
        cmd2.setShadowColor(new java.awt.Color(172, 182, 229));
        cmd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                                    .addComponent(txtSoLuong)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)
                                        .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addHang, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addMau, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(399, 399, 399)
                .addComponent(cmd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbxMauSac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(50, 50, 50)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(addHang, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(50, 50, 50)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(addKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cbxKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8)))
                                    .addGap(50, 50, 50)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(6, 6, 6))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(addMau, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(addSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(39, 39, 39)
                .addComponent(cmd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd2ActionPerformed
        lc = false;

        if (dataTT.SelectHinhAnh(lblHinhAnh.getText().trim()).size() == 0) {
            insertHinhAnh();
        }

        insertSPCT();


    }//GEN-LAST:event_cmd2ActionPerformed

    private void addSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSanPhamActionPerformed
        Frame parent = null;
        FormAddSanPham fh = new FormAddSanPham(parent, true);
        fh.setVisible(true);
        fillComboBoxSP();
    }//GEN-LAST:event_addSanPhamActionPerformed

    private void addKhungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addKhungActionPerformed
        Frame parent = null;
        FormAddLoaiKhung lk = new FormAddLoaiKhung(parent, true);
        lk.setVisible(true);
        fillComboBoxLK();
    }//GEN-LAST:event_addKhungActionPerformed

    private void addMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMauActionPerformed
        Frame parent = null;
        FormAddMauSac ms = new FormAddMauSac(parent, true);
        ms.setVisible(true);
        fillComboBoxMS();
    }//GEN-LAST:event_addMauActionPerformed

    private void addHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addHangActionPerformed
        Frame parent = null;
        FormAddHang hang = new FormAddHang(parent, true);
        hang.setVisible(true);
        fillComboBoxH();
    }//GEN-LAST:event_addHangActionPerformed

    private void addKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addKichThuocActionPerformed
        Frame parent = null;
        FormAddKichThuoc kt = new FormAddKichThuoc(parent, true);
        kt.setVisible(true);
        fillComboBoxKT();
    }//GEN-LAST:event_addKichThuocActionPerformed

    private void addChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addChatLieuActionPerformed
        Frame parent = null;
        FormAddChatLieu cl = new FormAddChatLieu(parent, true);
        cl.setVisible(true);
        fillComboBoxCL();
    }//GEN-LAST:event_addChatLieuActionPerformed

    private void lblHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMouseClicked
        try {
            JFileChooser jfc = new JFileChooser("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\SP");
            jfc.showOpenDialog(null);
            File file = jfc.getSelectedFile();
            Image img = ImageIO.read(file);
            String tenA = file.toString().substring(59);
            System.out.println(tenA);
            lblHinhAnh.setText(tenA);
            int Width = lblHinhAnh.getWidth();
            int height = lblHinhAnh.getHeight();
            lblHinhAnh.setIcon(new ImageIcon(img.getScaledInstance(Width, height, 0)));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_lblHinhAnhMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button addChatLieu;
    private com.MMT_Shop.swing.button.Button addHang;
    private com.MMT_Shop.swing.button.Button addKhung;
    private com.MMT_Shop.swing.button.Button addKichThuoc;
    private com.MMT_Shop.swing.button.Button addMau;
    private com.MMT_Shop.swing.button.Button addSanPham;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxChatLieu;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxHang;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxKichThuoc;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxLoaiKhung;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxMauSac;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxTenSP;
    private com.MMT_Shop.swing.button.Button cmd2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
