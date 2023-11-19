package com.MMT_Shop.form;

import java.sql.*;
import com.MMT_Shop.mani.Main;
import com.MMT_Shop.model.HoaDon;
import com.MMT_Shop.model.HoaDonChiTiet;
import com.MMT_Shop.service.HoaDonService;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Form_Hoa_Don extends javax.swing.JPanel {

    private Main main;
    int index = -1;
    public static String mahd;
    HoaDonService serviceHD = new HoaDonService();
    List<HoaDon> listHD = new ArrayList<>();
    List<HoaDonChiTiet> listHDCT = new ArrayList<>();
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel modelHDCT = new DefaultTableModel();

    public Form_Hoa_Don(Frame parent) {
        initComponents();
        this.main = (Main) parent;
        fillTable(serviceHD.getAllHD());
    }

    private void fillTable(List<HoaDon> list) {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        for (HoaDon hd : list) {
            model.addRow(new Object[]{
                hd.getStt(),
                hd.getMaHD(),
                hd.getMaNV(),
                hd.getTenKH(),
                hd.getSdt(),
                hd.getTongTien(),
                hd.getNgayTao(),
                hd.getTrangThai()
            });
        }
    }

    private void fillTableHDCT(List<HoaDonChiTiet> list) {
        modelHDCT = (DefaultTableModel) tblHDCT.getModel();
        modelHDCT.setRowCount(0);
        for (HoaDonChiTiet hdct : list) {
            modelHDCT.addRow(new Object[]{
                hdct.getMaCTSP(),
                hdct.getTenSP(),
                hdct.getHang(),
                hdct.getMauSac(),
                hdct.getSize(),
                hdct.getSoLuong(),
                hdct.getGia(),
                hdct.getTongTien()
            });
        }
    }

    private void showXemChiTiet() {
        int row = tblHoaDon.getSelectedRow();
        mahd = tblHoaDon.getValueAt(row, 0).toString();
        new XemChiTietHD(new javax.swing.JFrame(), true).setVisible(true);
    }

    private void timKiem() {
        String maHD = txtTimKiem.getText();
        String sdt = txtTimKiem.getText();
        String tenKH = txtTimKiem.getText();
        HoaDon hd1 = new HoaDon();
        if (maHD.trim().length() > 0 || sdt.trim().length() > 0 || hd1.getTenKH().equalsIgnoreCase(tenKH)) {
            model.setRowCount(0);
            listHD = serviceHD.timKiemHoaDon(maHD, sdt, tenKH);
            for (HoaDon hd : listHD) {
                model.addRow(new Object[]{
                    hd.getStt(),
                    hd.getMaHD(),
                    hd.getMaNV(),
                    hd.getTenKH(),
                    hd.getSdt(),
                    hd.getTongTien(),
                    hd.getNgayTao(),
                    hd.getTrangThai(),
                    hd.getHinhThucTT()
                });
            }
        }
    }

    private void loc() {
        String trangThai = (String) cboTrangThai.getSelectedItem();
        String hinhThucTT = (String) cboHinhThucTT.getSelectedItem();
        if (!(trangThai.isEmpty() && hinhThucTT.isEmpty())) {
            model.setRowCount(0);
            listHD = serviceHD.locHoaDon(trangThai, hinhThucTT);
            for (HoaDon hd : listHD) {
                model.addRow(new Object[]{
                    hd.getStt(),
                    hd.getMaHD(),
                    hd.getMaNV(),
                    hd.getTenKH(),
                    hd.getSdt(),
                    hd.getTongTien(),
                    hd.getNgayTao(),
                    hd.getTrangThai(),
                    hd.getHinhThucTT()
                });
            }
        }
    }

    private void xuatDanhSach() {
        try {
            XSSFWorkbook wordbook = new XSSFWorkbook();
            XSSFSheet sheet = wordbook.createSheet("Danh sách hóa đơn");

            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(2);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã hóa đơn");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã NV");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên KH");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("SDT");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Tổng tiền");

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Ngày tạo");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Trạng thái");

            ArrayList<HoaDon> arrHD = serviceHD.getAllHD();
            for (int i = 0; i < arrHD.size(); i++) {
                HoaDon hd = arrHD.get(i);
                row = sheet.createRow(3 + i);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getMaHD());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getMaNV());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getTenKH());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getSdt());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getTongTien());

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getNgayTao());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(arrHD.get(i).getTrangThai());
            }

            File f = new File("D:\\DuAn1\\DSHD.xlsx");
            try {
                FileOutputStream fo = new FileOutputStream(f);
                wordbook.write(fo);
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Xuất thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cboHinhThucTT = new javax.swing.JComboBox<>();
        maxNext5 = new javax.swing.JButton();
        btnXuatDS5 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnTaoHoaDon = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnTimKiem = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        btnXem = new javax.swing.JButton();
        maxNext6 = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNgayTT = new com.toedter.calendar.JDateChooser();
        txtNgayTao = new com.toedter.calendar.JDateChooser();
        txtMaHD = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        btnThemHD = new javax.swing.JButton();
        btnSuaHD = new javax.swing.JButton();
        cboTT = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setToolTipText("Hóa đơn");

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18)))); // NOI18N

        jLabel35.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel35.setText("Trạng thái hóa đơn");

        jLabel36.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel36.setText("Hình thức thanh toán");

        cboHinhThucTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Kết hợp", "Chuyển khoản", "Tiền mặt", " " }));

        maxNext5.setBackground(new java.awt.Color(204, 255, 204));
        maxNext5.setText(">>");
        maxNext5.setPreferredSize(new java.awt.Dimension(72, 28));
        maxNext5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxNext5ActionPerformed(evt);
            }
        });

        btnXuatDS5.setBackground(new java.awt.Color(204, 255, 204));
        btnXuatDS5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnXuatDS5.setForeground(new java.awt.Color(51, 255, 51));
        btnXuatDS5.setText("Xuất danh sách");
        btnXuatDS5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDS5ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Số hóa đơn:");

        lblSoHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblSoHoaDon.setForeground(new java.awt.Color(255, 0, 51));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("1/5");

        btnTaoHoaDon.setBackground(new java.awt.Color(204, 255, 204));
        btnTaoHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnTaoHoaDon.setForeground(new java.awt.Color(51, 255, 51));
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(204, 255, 204), null, new java.awt.Color(102, 102, 0)));
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        btnInHoaDon.setBackground(new java.awt.Color(204, 255, 204));
        btnInHoaDon.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(51, 255, 51));
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel38.setText("Từ");

        jDateChooser3.setDateFormatString("yyyy-MM-dd");

        jDateChooser4.setDateFormatString("yyyy-MM-dd");

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setText("Đến");

        jLabel40.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel40.setText("Tìm kiếm theo giá");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "100.000 - 300.000 VNĐ", "300.000 - 500.000 VNĐ", "500.000 - 1.000.000 VNĐ" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(204, 255, 204));
        btnTimKiem.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(51, 255, 51));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnLoc.setBackground(new java.awt.Color(204, 255, 204));
        btnLoc.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(51, 255, 51));
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Đã thanh toán", "Chưa thanh toán", "Đang giao", "Đã hủy", " " }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel41.setText("Tìm kiếm");

        btnXem.setBackground(new java.awt.Color(204, 255, 204));
        btnXem.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnXem.setForeground(new java.awt.Color(51, 255, 51));
        btnXem.setText("Xem chi tiết");
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });

        maxNext6.setBackground(new java.awt.Color(204, 255, 204));
        maxNext6.setText("<<");
        maxNext6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxNext6ActionPerformed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Mã NV", "Tên KH", "SDT", "Tổng tiền", "Ngày tạo", "Trang Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setMinimumSize(new java.awt.Dimension(155, 80));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tblHoaDon);

        txtTimKiem.setText("MaHD, sdt\n");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(maxNext6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maxNext5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXem, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXuatDS5))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addGap(27, 27, 27))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35)
                            .addComponent(jLabel41))
                        .addGap(20, 20, 20)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboTrangThai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboHinhThucTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem))
                .addGap(18, 18, 18)
                .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane14)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel41)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel40)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(17, 17, 17)
                            .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnTaoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36)
                    .addComponent(cboHinhThucTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXem))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXuatDS5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(maxNext5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(maxNext6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã CTSP", "Tên SP", "Hãng", "Màu", "Size", "Số lượng", "Giá", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT.setMinimumSize(new java.awt.Dimension(155, 80));
        jScrollPane13.setViewportView(tblHDCT);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hóa Đơn", jPanel13);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Mã hóa đơn");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Ngày tạo");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Ngày thánh toán");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Tên KH");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Địa chỉ");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("SDT");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("Tổng tiền");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Trạng thái");

        txtNgayTT.setDateFormatString("yyy-MM-dd");

        txtNgayTao.setDateFormatString("yyy-MM-dd");

        btnThemHD.setText("Thêm Hóa Đơn");
        btnThemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHDActionPerformed(evt);
            }
        });

        btnSuaHD.setText("Sửa Hóa Đơn");
        btnSuaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHDActionPerformed(evt);
            }
        });

        cboTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã thanh toán", "Chưa thanh toán", "" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(63, 63, 63)
                        .addComponent(txtTongTien))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(95, 95, 95)
                        .addComponent(txtSDT))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(78, 78, 78)
                        .addComponent(txtDiaChi))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayTT, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(txtMaHD)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(57, 57, 57)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnThemHD)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSuaHD))
                            .addComponent(cboTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(270, 270, 270))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtNgayTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(cboTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemHD)
                    .addComponent(btnSuaHD))
                .addContainerGap(228, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("TT HĐ", jPanel7);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setText("Tìm kiếm");

        jButton1.setBackground(new java.awt.Color(153, 255, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Search.png"))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã giao dịch", "Ngày giao dịch", "Hóa đơn", "Trạng thái", "Hình thức TT"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel37.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel37.setText("Hình thức thanh toán");

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuyển khoản", "Tiền mặt" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 32, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lịch sử đơn hàng", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void maxNext5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxNext5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxNext5ActionPerformed

    private void btnXuatDS5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDS5ActionPerformed
        xuatDanhSach();
    }//GEN-LAST:event_btnXuatDS5ActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        main.setForm(new Form_Ban_Hang());
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
//        inHoaDon();
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        timKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        loc();
    }//GEN-LAST:event_btnLocActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        showXemChiTiet();
    }//GEN-LAST:event_btnXemActionPerformed

    private void maxNext6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxNext6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxNext6ActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        int row = tblHoaDon.getSelectedRow();
        String id = tblHoaDon.getValueAt(row, 0).toString();
        if (id.trim().length() > 0) {
            modelHDCT.setRowCount(0);
            listHDCT = serviceHD.lienKetHoaDon(id);
            for (HoaDonChiTiet hdct : listHDCT) {
                modelHDCT.addRow(new Object[]{
                    hdct.getMaCTSP(),
                    hdct.getTenSP(),
                    hdct.getHang(),
                    hdct.getMauSac(),
                    hdct.getSize(),
                    hdct.getSoLuong(),
                    hdct.getGia(),
                    hdct.getTongTien()
                });
            }
        }
        fillTableHDCT(listHDCT);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnThemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHDActionPerformed
        //        HoaDon hd = getModel();
        //        if (serviceHD.addHoaDon(hd) > 0) {
        //            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        //            fillTable(serviceHD.getAllHD());
        //        } else {
        //            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        //        }
    }//GEN-LAST:event_btnThemHDActionPerformed

    private void btnSuaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHDActionPerformed
        //        index = tblHoaDon.getSelectedRow();
        //        String ID = tblHoaDon.getValueAt(index, 0).toString();
        //        HoaDon hd = getModel();
        //        if (serviceHD.deleteHoaDon(ID) > 0) {
        //            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        //            fillTable(serviceHD.getAllHD());
        //        } else {
        //            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        //
        //        }
    }//GEN-LAST:event_btnSuaHDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnSuaHD;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThemHD;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXem;
    private javax.swing.JButton btnXuatDS5;
    private javax.swing.JComboBox<String> cboHinhThucTT;
    private javax.swing.JComboBox<String> cboTT;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox20;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JButton maxNext5;
    private javax.swing.JButton maxNext6;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtMaHD;
    private com.toedter.calendar.JDateChooser txtNgayTT;
    private com.toedter.calendar.JDateChooser txtNgayTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
