package com.MMT_Shop.mani;

import com.MMT_Shop.Dao.LoginDAO;
import com.MMT_Shop.EnTiTy.Logine;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JDialog {

    LoginDAO data = new LoginDAO();

    public Login(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    public boolean chekFrom() {
        if (txtName.getText().isEmpty() || txtPass.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void DangNhap() {
        if (chekFrom()) {
            String usename = txtName.getText().toUpperCase();
            String password = new String(txtPass.getPassword());
            Logine login = data.selectById(usename);
            if (login != null) {
                String chekMK = login.getMatKhau();
                if (password.equals(chekMK)) {
                    LoginDAO.lg = login;
                    System.out.println(LoginDAO.lg.getMaNV());
                     Main mi = new Main();
                     mi.setVisible(true);
                      System.out.println(LoginDAO.lg.getChucVu());
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, " Mật Khẩu không đúng");
                }

            } else {
                JOptionPane.showMessageDialog(this, " Tên đăng nhập không đúng");
            }
        } else {
            JOptionPane.showMessageDialog(this, "vui lonh nhap day du user name va password");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        pnlMNV1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        pnlMNV = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jPanel9 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 395));

        jPanel1.setPreferredSize(new java.awt.Dimension(450, 400));

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 100));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("ĐĂNG NHẬP");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(jLabel1)
                .addContainerGap(169, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        jPanel5.setPreferredSize(new java.awt.Dimension(400, 140));

        pnlMNV1.setPreferredSize(new java.awt.Dimension(262, 50));
        pnlMNV1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        jLabel10.setText("Mã nhân viên");
        pnlMNV1.add(jLabel10);

        jPanel14.add(jLabel11);

        txtName.setPreferredSize(new java.awt.Dimension(210, 22));
        jPanel14.add(txtName);

        pnlMNV1.add(jPanel14);

        jPanel5.add(pnlMNV1);

        pnlMNV.setPreferredSize(new java.awt.Dimension(262, 50));
        pnlMNV.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        jLabel9.setText("Mật khẩu");
        pnlMNV.add(jLabel9);

        jPanel13.add(jLabel8);

        txtPass.setPreferredSize(new java.awt.Dimension(210, 22));
        jPanel13.add(txtPass);

        pnlMNV.add(jPanel13);

        jPanel5.add(pnlMNV);

        jPanel1.add(jPanel5);

        jPanel9.setPreferredSize(new java.awt.Dimension(400, 50));

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Login");
        jButton1.setPreferredSize(new java.awt.Dimension(250, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jPanel1.add(jPanel9);

        jPanel2.setBackground(new java.awt.Color(211, 204, 196));
        jPanel2.setMinimumSize(new java.awt.Dimension(350, 201));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 400));
        jPanel2.setLayout(new java.awt.BorderLayout(40, 20));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/logo_ok.jpg"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(360, 201));
        jPanel2.add(jLabel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1510, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DangNhap();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Login dialog = new Login(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel pnlMNV;
    private javax.swing.JPanel pnlMNV1;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
