      package com.MMT_Shop.ngan;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class Form_Add extends javax.swing.JPanel implements Runnable, ThreadFactory {

    NhanVienService service = new NhanVienService();
    List<NhanVien> listNhanVien = new ArrayList<>();
    private int indexRowChoice = 0;
    private MsgBox msgBox = new MsgBox();
    DefaultTableModel dtm = new DefaultTableModel();
    String maNV;

    private WebcamPanel panel = null;
    private Webcam webcam = null;

    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    public Form_Add() {
        initComponents();
        //  insert();
        taoMa();
        txtMaNV.setText(maNV);
        initWebcam();
        fill();
    }

    private void setForm(JPanel com) {
        jPanel1.removeAll();
        jPanel1.add(com);
        jPanel1.repaint();
        jPanel1.revalidate();
    }

    public void clear() {
        txtMaNV.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtCMND.setText("");
        txtEmail.setText("");
        txtNgaySinh.setDateFormatString("");
        rdoNam.setSelected(true);
//       
    }

    public NhanVien getModel() {
        NhanVien nhanVien = new NhanVien();

        String ngay = Model_Form.layNgayString(txtNgaySinh.getDate());
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        String mnv = "NV01";
        nhanVien.setMaNV(txtMaNV.getText());
        nhanVien.setHoTen(txtTen.getText());
        nhanVien.setSdt(txtSDT.getText());
        nhanVien.setDiaChi(txtDiaChi.getText());
        nhanVien.setGioiTinh(rdoNam.isSelected());
        nhanVien.setSoCMND(txtCMND.getText());
        nhanVien.setEmail(txtEmail.getText());
        nhanVien.setChucVu("Nhân viên");
        nhanVien.setNgaySinh(ngay);
        nhanVien.setMatKhau("12345");
        nhanVien.setCreated_At(At);
        nhanVien.setUpdated_At(At);
        nhanVien.setCreated_By(mnv);
        nhanVien.setUpdated_By(mnv);
        nhanVien.setDeleted(mnv);
        return nhanVien;
    }

    private void insert() {
        NhanVien nv = getModel();
        try {
            if (service.InsertNhanVien(nv) != null) {
                JOptionPane.showMessageDialog(this, "them thanh cong");
                clear();
//                
            } else {
                JOptionPane.showMessageDialog(this, "them that bai");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " loi nut them");
        }
    }

    protected JRootPane rootPane;

    public boolean validateForm() {
        if (txtTen.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Tên không được để trống");
            txtTen.requestFocus();
            return false;
        }

        if (txtCMND.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "CCCD khong duoc de trong");
            txtCMND.requestFocus();
            return false;
        }

        if (txtEmail.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "email khong duoc de trong");
            txtEmail.requestFocus();
            return false;
        } else if (!(txtEmail.getText()).matches("^[\\w-_\\.]+\\@[\\w&&[^0-9]]+\\.com$")) {
            JOptionPane.showMessageDialog(rootPane, "Sai định dạng email", "Error", 1);
            txtEmail.requestFocus();
            return false;
        }
        if (txtSDT.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Sdt khong duoc de trong");
            txtSDT.requestFocus();
            return false;
        }

        if (txtDiaChi.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Dia chi khong duoc de trong");
            txtDiaChi.requestFocus();
            return false;
        }

        List<NhanVien> list = service.selectAll();
        for (int i = 0; i < list.size(); i++) {
            if (txtMaNV.getText().equalsIgnoreCase(list.get(i).getMaNV())) {
                JOptionPane.showMessageDialog(this, "Trùng Mã Nhân Viên", "Error", 1);
                return false;
            }

        }
        return true;
    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            // Generate a random two-digit number
            int x = random.nextInt(900) + 10; // Generates a random number between 10 (inclusive) and 100 (exclusive)

            maNV = "NV" + x;
            System.out.println(maNV);
        }
    }

    private void sendMail() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", 587);

            final String email = "vtuan25062k4@gmail.com";
            final String pass = "xrzr sxad zalb sxxj";
            Session s = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, pass);
                }

            });
            String sender = "vtuan25062k4@gmail.com";
            String receiver = txtEmail.getText();
            String subject = "Thư mời";
            String text = "Chào bạn\n"
                    + "Mình là Ngân, thuộc bộ phận tuyển dụng của hệ thống bán giường ngủ MMT_Shop\n"
                    + "Bạn đã ứng tuyển thành công vào vị trí: Nhân viên\n"
                    + "Để có thể nắm rõ về chi tiết công việc của bạn, bạn vui lòng đến Phòng P401, FPT Polytechnic cơ sở Kiểu Mai nhé\n"
                    + "Trân trọng!";

            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        p2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        result_field = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtClear = new com.MMT_Shop.swing.button.Button();
        txtBack = new com.MMT_Shop.swing.button.Button();
        btnThem = new com.MMT_Shop.swing.button.Button();

        p2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Thông tin nhân viên");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("MaNV");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Tên Nhân Viên");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Mã Định Danh (Số CMND/CCCD)");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Thông tin chi tiết");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Giới Tính");

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNam);
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNu);
        rdoNu.setText("Nữ");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setText("Số Điện Thoại");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Địa Chỉ");

        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        txtNgaySinh.setDateFormatString("yyyy-MM-dd");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Ngày Sinh");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(rdoNam)
                                        .addGap(42, 42, 42)
                                        .addComponent(rdoNu))
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 39, Short.MAX_VALUE)))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGap(29, 29, 29)
                .addComponent(jLabel14)
                .addGap(32, 32, 32)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel10)
                .addGap(29, 29, 29)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        result_field.setBorder(null);
        jPanel3.add(result_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 310, 30));

        jLabel2.setForeground(new java.awt.Color(105, 105, 105));
        jLabel2.setText("QUÉT CCCD");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, -1, -1));

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 230, 230)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 300, 230));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setText("Email");

        txtClear.setBackground(new java.awt.Color(172, 182, 229));
        txtClear.setForeground(new java.awt.Color(255, 255, 255));
        txtClear.setText("Mới");
        txtClear.setRippleColor(new java.awt.Color(255, 255, 255));
        txtClear.setShadowColor(new java.awt.Color(172, 182, 229));
        txtClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClearActionPerformed(evt);
            }
        });

        txtBack.setBackground(new java.awt.Color(172, 182, 229));
        txtBack.setForeground(new java.awt.Color(255, 255, 255));
        txtBack.setText("Trở về");
        txtBack.setRippleColor(new java.awt.Color(255, 255, 255));
        txtBack.setShadowColor(new java.awt.Color(172, 182, 229));
        txtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBackActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(172, 182, 229));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Lưu");
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCMND, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(48, 48, 48)))
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(295, 295, 295)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtClear, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel1)
                                .addGap(40, 40, 40)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel5)
                                .addGap(26, 26, 26)
                                .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)))))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void txtClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClearActionPerformed
        clear();
    }//GEN-LAST:event_txtClearActionPerformed

    private void txtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBackActionPerformed
        webcam.close();
        setForm(new Form_table());
    }//GEN-LAST:event_txtBackActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateForm()) {
            insert();
            sendMail();
            webcam.close();
            setForm(new Form_table());
        }
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel p2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTextField result_field;
    private com.MMT_Shop.swing.button.Button txtBack;
    private javax.swing.JTextField txtCMND;
    private com.MMT_Shop.swing.button.Button txtClear;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables

    private void initWebcam() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        jPanel6.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 230));

        executor.execute(this);
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException e) {
                //No result...
            }

            if (result != null) {
                result_field.setText(result.getText());
                webcam.close();
                System.out.println(result.getText());
                return;
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    public void fill() {
        NhanVien nhanVien = new NhanVien();

        result_field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                if (result_field.getText().equals("")) {
                    System.out.println("JTextField is empty");
                } else {
                    String input = result_field.getText();
                    String[] parts = input.split("\\|");

                    String id = parts[0];
                    String name = parts[2];
                    String dob = parts[3].formatted("yyyy-MM-dd");
                    String gender = parts[4];
                    String address = parts[5];
                    String date = parts[6];

                    Date date2 = Model_Form.layNgayDate(parts[3].toString());
                    txtCMND.setText(id);
                    txtTen.setText(name);
//                    txtNgaySinh.setDate(date2);
//                    System.out.println(date2);
                    // txtNG1.setText(dob);
                    System.out.println(dob);
                    txtDiaChi.setText(address);
                    if ("Nam".equals(gender)) {
                        rdoNam.setSelected(true);
                    } else {
                        rdoNu.setSelected(true);
                    }

                }
            }
        }
        );

    }

}
