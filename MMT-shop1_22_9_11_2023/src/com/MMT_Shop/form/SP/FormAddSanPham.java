package com.MMT_Shop.form.SP;

import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.EnTiTy.SanPham;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;

public class FormAddSanPham extends javax.swing.JDialog {

    private final SanPhamDAO dataSP = new SanPhamDAO();
    private String code;

    public FormAddSanPham(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        taoMa();
    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt() + 10;
            if (x < 0) {
                x = x * -1;
            }
            code ="SP"+ x ;

        }
    }

    private SanPham addSP() {

        String ten = txtTenSP.getText();
        String ma = code;
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        boolean validated = true;
        if (ten.isEmpty()) {
            showMessageBox("Vui lòng nhập tên");
            validated = false;
            return null;
        }
        if (validated) {
            SanPham sp = new SanPham();
            sp.setMa(ma);
            sp.setTen(ten);
            sp.setCreated_At(At);
            sp.setUpdated_At(At);
            sp.setCreated_By(mnv);
            sp.setUpdated_By(mnv);
            sp.setDeleted(mnv);
            return sp;
        }
        return null;
    }

    private void insertSP() {
        System.out.println("Sản phẩm");
        SanPham sp = addSP();
        try {
            if (dataSP.InsertSanPham(sp) != null) {
                showMessageBox("Thêm thành công");
                txtTenSP.setText("");
                 this.dispose();
            } else {
                showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnThem = new com.MMT_Shop.swing.button.Button();
        txtTenSP = new com.MMT_Shop.swing.textfield.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnThem.setBackground(new java.awt.Color(30, 180, 114));
        btnThem.setForeground(new java.awt.Color(245, 245, 245));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm Sản phẩm");
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(30, 180, 114));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtTenSP.setLabelText("Tên sản phẩm");
        txtTenSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insertSP();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTenSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenSPMouseClicked
        taoMa();
    }//GEN-LAST:event_txtTenSPMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormAddSanPham dialog = new FormAddSanPham(new javax.swing.JFrame(), true);
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
    private com.MMT_Shop.swing.button.Button btnThem;
    private javax.swing.JPanel jPanel1;
    private com.MMT_Shop.swing.textfield.TextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
