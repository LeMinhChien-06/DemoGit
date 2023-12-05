package com.MMT_Shop.Chien;

import com.MMT_Shop.form.Form_Ban_Hang;
import com.MMT_Shop.form.banHang.FormQuetQRBH;
import com.MMT_Shop.mani.Main;
import com.MMT_Shop.model.ChuyenDoi;
import com.MMT_Shop.swing.ScrollBar;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Form_Hoa_Don extends javax.swing.JPanel {

    private Main main;
    int index = -1;
    public static String mahd;
    HoaDonService dataHD = new HoaDonService();
    private ArrayList<HoaDon> listHD = new ArrayList<>();
    private DefaultTableModel model = new DefaultTableModel();

    private ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
    public static String maHD;

    Double tongTien = 0.0;
    Double tienMat = 0.0;
    Double CK = 0.0;
    Double traLai = 0.0;
    Double bHeight = 0.0;
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> subtotal = new ArrayList<>();

    public Form_Hoa_Don(Frame parent) {
        initComponents();
        this.main = (Main) parent;
        addRowTableHD();
    }

    private void addRowTableHD() {
        model = (DefaultTableModel) tblHoaDon1.getModel();
        model.setRowCount(0);
        spHD.setVerticalScrollBar(new ScrollBar());
        listHD = dataHD.selectALLHD();
        for (int i = listHD.size() - 1; i >= 0; i--) {
            HoaDon hd = listHD.get(i);
            tblHoaDon1.addRow(new Object[]{tblHoaDon1.getRowCount() + 1, hd.getMaHD(),
                hd.getCreated_At().substring(0, 10), hd.getMaNV(), hd.getTenKH(),
                hd.getSdt(), hd.getDiaChi(), hd.getTongTien(), hd.getTrangThai()});
        }

    }

    private void addRowTableHDTheoNgay(String bd, String kT) {
        model = (DefaultTableModel) tblHoaDon1.getModel();
        model.setRowCount(0);
        spHD.setVerticalScrollBar(new ScrollBar());
        listHD = dataHD.timKiemTheoNgay(bd, kT);
        for (int i = listHD.size() - 1; i >= 0; i--) {
            HoaDon hd = listHD.get(i);
            tblHoaDon1.addRow(new Object[]{tblHoaDon1.getRowCount() + 1, hd.getMaHD(),
                hd.getCreated_At().substring(0, 10), hd.getMaNV(), hd.getTenKH(),
                hd.getSdt(), hd.getDiaChi(), hd.getTongTien(), hd.getTrangThai()});
        }

    }

    private void addRowTableHDTheoThanhTien(String min, String max) {
        model = (DefaultTableModel) tblHoaDon1.getModel();
        model.setRowCount(0);
        spHD.setVerticalScrollBar(new ScrollBar());
        listHD = dataHD.timKiemTheoMinMax(min, max);
        for (int i = listHD.size() - 1; i >= 0; i--) {
            HoaDon hd = listHD.get(i);
            tblHoaDon1.addRow(new Object[]{tblHoaDon1.getRowCount() + 1, hd.getMaHD(),
                hd.getCreated_At().substring(0, 10), hd.getMaNV(), hd.getTenKH(),
                hd.getSdt(), hd.getDiaChi(), hd.getTongTien(), hd.getTrangThai()});
        }

    }

    private void clickHD() {
        int row = tblHoaDon1.getSelectedRow();
        maHD = tblHoaDon1.getValueAt(row, 1).toString();
        addRowTableHDCT(maHD);
    }

    public void addRowTableHDCT(String maHD) {
        model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
        spHDCT.setVerticalScrollBar(new ScrollBar());
        listHDCT = dataHD.selectALLHDCT(maHD);
        for (int i = listHDCT.size() - 1; i >= 0; i--) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            model.addRow(new Object[]{
                tblHDCT.getRowCount() + 1,
                hdct.getMaHDCT(),
                hdct.getMaCTSP(),
                hdct.getTenSP(),
                hdct.getHang(),
                hdct.getLoaiKhung(),
                hdct.getChatLieu(),
                hdct.getKichThuoc(),
                hdct.getMauSac(),
                hdct.getGia(),
                hdct.getSoLuong(),
                hdct.getThanhTien(),});
        }

    }

    private void xuatFile() {
        FileOutputStream excelFOU = null;
        BufferedOutputStream excelBOU = null;
        XSSFWorkbook excelJTableExporter = null;
        JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\Authentic\\Desktop");
        excelFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelJTableExporter = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExporter.createSheet("JTable Sheet");

                for (int i = 0; i < tblHoaDon1.getRowCount(); i++) {
                    XSSFRow excelColumn = excelSheet.createRow(0);
                    XSSFRow excelRow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < tblHoaDon1.getColumnCount() - 1; j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        XSSFCell nameColumn = excelColumn.createCell(j);
                        nameColumn.setCellValue(tblHoaDon1.getColumnName(j));
                        excelCell.setCellValue(tblHoaDon1.getValueAt(i, j).toString());
                    }
                }
                excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBOU = new BufferedOutputStream(excelFOU);
                excelJTableExporter.write(excelBOU);
                JOptionPane.showMessageDialog(null, "Lưu file thành công");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBOU != null) {
                        excelBOU.close();
                    }
                    if (excelFOU != null) {
                        excelFOU.close();
                    }
                    if (excelJTableExporter != null) {
                        excelJTableExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    void addinHoaDon() {
        listHDCT = dataHD.selectALLHDCT(maHD);
        for (int i = listHDCT.size() - 1; i >= 0; i--) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            itemName.add(hdct.getTenSP() + " " + hdct.getMaCTSP());
            quantity.add(hdct.getSoLuong() + "");
            itemPrice.add(hdct.getGia() + "");
            subtotal.add(hdct.getThanhTien() + "");
        }

        ArrayList<HoaDon> datain = dataHD.HDTheoMa(maHD);
        for (int i = datain.size() - 1; i >= 0; i--) {
            HoaDon hd = datain.get(i);
            tongTien = Double.valueOf(hd.getTongTien());
            tienMat = Double.valueOf(hd.getTienMat());
            CK = Double.valueOf(hd.getChuyuenKhoan());
            traLai = Double.valueOf(hd.getTraLai());
        }

    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 100d;
    }

    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            int r = itemName.size();
            ImageIcon icon = new ImageIcon("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\logo1.png");
            ImageIcon QR = new ImageIcon("G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\QRHD\\" + maHD.trim() + ".PNG");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    // int headerRectHeighta=40;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(icon.getImage(), 70, 20, 90, 40, null);
                    y += yShift + 30;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("             MMT-Shop.com        ", 12, y);
                    y += yShift;
                    g2d.drawString("Địa chỉ: .................       ", 12, y);
                    y += yShift;
                    g2d.drawString("      Address Line 02 SRI LANKA ", 12, y);
                    y += yShift;
                    g2d.drawString("      www.facebook.com/.........", 12, y);
                    y += yShift;
                    g2d.drawString("      Điện thoại: 0899842018   ", 12, y);
                    y += yShift;
                    g2d.drawString("Phiếu tính tiền: " + maHD, 12, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;

                    g2d.drawString(" Tên sản phẩm           Thành tiền   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;

                    for (int s = 0; s < r; s++) {
                        g2d.drawString(" " + itemName.get(s) + "                            ", 10, y);
                        y += yShift;
                        g2d.drawString("      " + quantity.get(s) + " * " + itemPrice.get(s), 10, y);
                        g2d.drawString(subtotal.get(s), 160, y);
                        y += yShift;

                    }

                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Tổng tiền:               " + tongTien, 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" tiền mặt:                " + tienMat, 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Tiền chuyển khoản:       " + CK, 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Tiền trả lại:            " + traLai, 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(QR.getImage(), 70, y, 90, 90, null);
                    y += yShift + 30;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    public void addRowTableHDCT1(String maHD) {
        model = (DefaultTableModel) tblHDCT1.getModel();
        model.setRowCount(0);
        spHDCT1.setVerticalScrollBar(new ScrollBar());
        listHDCT = dataHD.selectALLHDCT(maHD);
        for (int i = listHDCT.size() - 1; i >= 0; i--) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            model.addRow(new Object[]{
                tblHDCT1.getRowCount() + 1,
                hdct.getMaHDCT(),
                hdct.getMaCTSP(),
                hdct.getTenSP(),
                hdct.getHang(),
                hdct.getLoaiKhung(),
                hdct.getChatLieu(),
                hdct.getKichThuoc(),
                hdct.getMauSac(),
                hdct.getGia(),
                hdct.getSoLuong(),
                hdct.getThanhTien(),});
        }

    }

    void capNhap() {
        listHD = dataHD.HDTheoMa(maHD);
        for (int i = listHD.size() - 1; i >= 0; i--) {
            HoaDon hd = listHD.get(i);
            if (hd.getTrangThai().equals("Đã thanh toán."
            )) {
                cbxTT1.setSelectedIndex(0);
            } else {
                cbxTT1.setSelectedIndex(1);
            }
            txtMa.setText(hd.getMaHD());
            txtNgayTao.setText(hd.getCreated_At().substring(0, 10));
            txtMaNV.setText(hd.getMaNV());
            txtKhachHang.setText(hd.getTenKH());
            txtSDT.setText(hd.getSdt());
            if (hd.getSdtNguoiShip() == null) {
                txtSDTShip.setText("");
            } else {
                txtSDTShip.setText(hd.getSdtNguoiShip());
            }
            if (hd.getTenNGuoiShip() == null) {
                txtNguoiShip.setText("");
            } else {
                txtNguoiShip.setText(hd.getTenNGuoiShip());
            }
            txtDiaChi.setText(hd.getDiaChi());
            txtTongTien.setText(hd.getTongTien() + "");
        }
        addRowTableHDCT1(maHD);
    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        TabCTHD = new javax.swing.JTabbedPane();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        spHD = new javax.swing.JScrollPane();
        tblHoaDon1 = new com.MMT_Shop.swing.Table();
        jPanel4 = new javax.swing.JPanel();
        addThanhToan1 = new com.MMT_Shop.swing.button.Button();
        addThanhToan = new com.MMT_Shop.swing.button.Button();
        addThanhToan2 = new com.MMT_Shop.swing.button.Button();
        addThanhToan5 = new com.MMT_Shop.swing.button.Button();
        jPanel15 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtTimKiemTable = new com.MMT_Shop.swing.textfield.TextField();
        jPanel11 = new javax.swing.JPanel();
        cbxTT = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        dcsNgayBatDau = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        dcsNgayKet = new com.toedter.calendar.JDateChooser();
        addThanhToan3 = new com.MMT_Shop.swing.button.Button();
        jPanel10 = new javax.swing.JPanel();
        txtMin = new com.MMT_Shop.swing.textfield.TextField();
        txtMax = new com.MMT_Shop.swing.textfield.TextField();
        addThanhToan4 = new com.MMT_Shop.swing.button.Button();
        jPanel14 = new javax.swing.JPanel();
        spHDCT = new javax.swing.JScrollPane();
        tblHDCT = new com.MMT_Shop.swing.Table();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtMa = new com.MMT_Shop.swing.textfield.TextField();
        txtNgayTao = new com.MMT_Shop.swing.textfield.TextField();
        txtMaNV = new com.MMT_Shop.swing.textfield.TextField();
        txtKhachHang = new com.MMT_Shop.swing.textfield.TextField();
        txtSDT = new com.MMT_Shop.swing.textfield.TextField();
        txtSDTShip = new com.MMT_Shop.swing.textfield.TextField();
        txtNguoiShip = new com.MMT_Shop.swing.textfield.TextField();
        jPanel16 = new javax.swing.JPanel();
        cbxTT1 = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        txtDiaChi = new com.MMT_Shop.swing.textfield.TextField();
        txtTongTien = new com.MMT_Shop.swing.textfield.TextField();
        jPanel3 = new javax.swing.JPanel();
        spHDCT1 = new javax.swing.JScrollPane();
        tblHDCT1 = new com.MMT_Shop.swing.Table();
        addThanhToan6 = new com.MMT_Shop.swing.button.Button();
        jPanel5 = new javax.swing.JPanel();
        txtTimKiemHDTM = new com.MMT_Shop.swing.textfield.TextField();
        addThanhToan7 = new com.MMT_Shop.swing.button.Button();
        addThanhToan8 = new com.MMT_Shop.swing.button.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setToolTipText("Hóa đơn");

        TabCTHD.setBackground(new java.awt.Color(255, 255, 255));
        TabCTHD.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18)))); // NOI18N

        tblHoaDon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày tạo  ", "Mã nhân viên", "Khách hàng", "Số điện thoại", "Địa chỉ ", "Tổng tiền ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDon1MouseClicked(evt);
            }
        });
        spHD.setViewportView(tblHoaDon1);
        if (tblHoaDon1.getColumnModel().getColumnCount() > 0) {
            tblHoaDon1.getColumnModel().getColumn(0).setPreferredWidth(40);
        }

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        addThanhToan1.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan1.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan1.setText("Cập nhập");
        addThanhToan1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan1.setPreferredSize(new java.awt.Dimension(124, 45));
        addThanhToan1.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan1.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan1ActionPerformed(evt);
            }
        });
        jPanel4.add(addThanhToan1);

        addThanhToan.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan.setText("In hóa đơn");
        addThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan.setPreferredSize(new java.awt.Dimension(124, 45));
        addThanhToan.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToanActionPerformed(evt);
            }
        });
        jPanel4.add(addThanhToan);

        addThanhToan2.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan2.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan2.setText("Xuất danh sách");
        addThanhToan2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan2.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan2.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan2ActionPerformed(evt);
            }
        });
        jPanel4.add(addThanhToan2);

        addThanhToan5.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan5.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan5.setText("Tạo hóa đơn mới");
        addThanhToan5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan5.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan5.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan5ActionPerformed(evt);
            }
        });
        jPanel4.add(addThanhToan5);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        txtTimKiemTable.setLabelText("Tìm kiếm theo các trường");
        txtTimKiemTable.setPreferredSize(new java.awt.Dimension(140, 46));
        txtTimKiemTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiemTableKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 188;
        gridBagConstraints.ipady = -6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        jPanel12.add(txtTimKiemTable, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Trạng thái", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel11.setMinimumSize(new java.awt.Dimension(190, 46));
        jPanel11.setPreferredSize(new java.awt.Dimension(140, 46));
        jPanel11.setLayout(new java.awt.BorderLayout());

        cbxTT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả ", "Đã thanh toán.", "Đang vận chuyển." }));
        cbxTT.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTTItemStateChanged(evt);
            }
        });
        jPanel11.add(cbxTT, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 62;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        jPanel12.add(jPanel11, gridBagConstraints);

        jPanel15.add(jPanel12);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tìm kiếm theo khoảng thời gian ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ngày bắt đầu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));
        jPanel7.add(dcsNgayBatDau);

        jPanel9.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Ngày kết thúc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));
        jPanel8.add(dcsNgayKet);

        jPanel9.add(jPanel8);

        addThanhToan3.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan3.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan3.setText("TÌm kiếm");
        addThanhToan3.setPreferredSize(new java.awt.Dimension(80, 45));
        addThanhToan3.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan3.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan3ActionPerformed(evt);
            }
        });
        jPanel9.add(addThanhToan3);

        jPanel15.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tìm kiếm theo khoảng Tổng tiền ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMin.setLabelText("Tổng tiền Min");
        txtMin.setMinimumSize(new java.awt.Dimension(64, 40));
        txtMin.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel10.add(txtMin);

        txtMax.setLabelText("Tổng tiền Max");
        txtMax.setMinimumSize(new java.awt.Dimension(64, 40));
        txtMax.setPreferredSize(new java.awt.Dimension(90, 40));
        jPanel10.add(txtMax);

        addThanhToan4.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan4.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan4.setText("TÌm kiếm");
        addThanhToan4.setPreferredSize(new java.awt.Dimension(80, 45));
        addThanhToan4.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan4.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan4ActionPerformed(evt);
            }
        });
        jPanel10.add(addThanhToan4);

        jPanel15.add(jPanel10);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spHD)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spHD, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18))); // NOI18N

        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "", "Mã SP ", "Tên SP ", "Hãng ", "Loại khung", "Chất liệu ", "Kích thước ", "Màu sắc ", "Đơn giá ", "Số lượng ", "Thành tiền "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spHDCT.setViewportView(tblHDCT);
        if (tblHDCT.getColumnModel().getColumnCount() > 0) {
            tblHDCT.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblHDCT.getColumnModel().getColumn(1).setMinWidth(0);
            tblHDCT.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblHDCT.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spHDCT, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spHDCT, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabCTHD.addTab("Hóa Đơn", jPanel13);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N

        txtMa.setEditable(false);
        txtMa.setBackground(new java.awt.Color(255, 255, 255));
        txtMa.setLabelText("Mã hóa đơn");
        txtMa.setPreferredSize(new java.awt.Dimension(140, 46));

        txtNgayTao.setEditable(false);
        txtNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayTao.setLabelText("Ngày tạo");
        txtNgayTao.setPreferredSize(new java.awt.Dimension(140, 46));

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setLabelText("Mã nhân viên");
        txtMaNV.setPreferredSize(new java.awt.Dimension(140, 46));

        txtKhachHang.setLabelText("Khách hàng");
        txtKhachHang.setPreferredSize(new java.awt.Dimension(140, 46));

        txtSDT.setLabelText("Số điện thoại khách hàng");
        txtSDT.setPreferredSize(new java.awt.Dimension(140, 46));

        txtSDTShip.setLabelText("Số người Ships");
        txtSDTShip.setPreferredSize(new java.awt.Dimension(140, 46));

        txtNguoiShip.setEditable(false);
        txtNguoiShip.setBackground(new java.awt.Color(255, 255, 255));
        txtNguoiShip.setLabelText("Tên người Ships");
        txtNguoiShip.setPreferredSize(new java.awt.Dimension(140, 46));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Trạng thái", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel16.setMinimumSize(new java.awt.Dimension(190, 46));
        jPanel16.setPreferredSize(new java.awt.Dimension(140, 46));
        jPanel16.setLayout(new java.awt.BorderLayout());

        cbxTT1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Đã thanh toán.", "Đang vận chuyển." }));
        cbxTT1.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxTT1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTT1ItemStateChanged(evt);
            }
        });
        jPanel16.add(cbxTT1, java.awt.BorderLayout.CENTER);

        txtDiaChi.setLabelText("Địa chỉ");
        txtDiaChi.setPreferredSize(new java.awt.Dimension(140, 46));

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setLabelText("Tổng tiền ");
        txtTongTien.setPreferredSize(new java.awt.Dimension(140, 46));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtSDTShip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtNguoiShip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSDTShip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNguoiShip, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 16))); // NOI18N

        tblHDCT1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "", "Mã SP ", "Tên SP ", "Hãng ", "Loại khung", "Chất liệu ", "Kích thước ", "Màu sắc ", "Đơn giá ", "Số lượng ", "Thành tiền "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDCT1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCT1MouseClicked(evt);
            }
        });
        spHDCT1.setViewportView(tblHDCT1);
        if (tblHDCT1.getColumnModel().getColumnCount() > 0) {
            tblHDCT1.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblHDCT1.getColumnModel().getColumn(1).setMinWidth(0);
            tblHDCT1.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblHDCT1.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        addThanhToan6.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan6.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan6.setText("Cập nhập trạng thái");
        addThanhToan6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan6.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan6.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spHDCT1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addThanhToan6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spHDCT1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addThanhToan6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        txtTimKiemHDTM.setLabelText("Tìm kiếm theo mã hóa đơn");
        txtTimKiemHDTM.setPreferredSize(new java.awt.Dimension(140, 46));
        txtTimKiemHDTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemHDTMActionPerformed(evt);
            }
        });

        addThanhToan7.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan7.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Qr Code.png"))); // NOI18N
        addThanhToan7.setText("Quét QR");
        addThanhToan7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan7.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan7.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan7ActionPerformed(evt);
            }
        });

        addThanhToan8.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan8.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan8.setText("Tìm kiếm");
        addThanhToan8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addThanhToan8.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan8.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToan8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemHDTM, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addThanhToan8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(535, 535, 535)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addThanhToan7, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiemHDTM, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addThanhToan7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addThanhToan8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        TabCTHD.addTab("Cập nhập trạng thái", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabCTHD)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabCTHD)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToanActionPerformed
        addinHoaDon();
        bHeight = Double.valueOf(itemName.size());
        //JOptionPane.showMessageDialog(rootPane, bHeight);

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_addThanhToanActionPerformed

    private void addThanhToan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan1ActionPerformed
        TabCTHD.setSelectedIndex(1);
        capNhap();
    }//GEN-LAST:event_addThanhToan1ActionPerformed

    private void addThanhToan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan2ActionPerformed
        xuatFile();
    }//GEN-LAST:event_addThanhToan2ActionPerformed

    private void tblHoaDon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDon1MouseClicked
        clickHD();
    }//GEN-LAST:event_tblHoaDon1MouseClicked

    private void addThanhToan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan3ActionPerformed
        String ngayBatDau = ChuyenDoi.layNgayString(dcsNgayBatDau.getDate());
        String ngayKetThuc = ChuyenDoi.layNgayString(dcsNgayKet.getDate());
        addRowTableHDTheoNgay(ngayBatDau, ngayKetThuc);
    }//GEN-LAST:event_addThanhToan3ActionPerformed

    private void addThanhToan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan4ActionPerformed
        String min = txtMin.getText();
        String max = txtMax.getText();
        addRowTableHDTheoThanhTien(min, max);
    }//GEN-LAST:event_addThanhToan4ActionPerformed

    private void cbxTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTTItemStateChanged
        if (cbxTT.getSelectedIndex() == 0) {
            addRowTableHD();
        } else if (cbxTT.getSelectedIndex() == 1) {
            DefaultTableModel ob = (DefaultTableModel) tblHoaDon1.getModel();
            TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
            tblHoaDon1.setRowSorter(obj);
            obj.setRowFilter(RowFilter.regexFilter(cbxTT.getSelectedItem().toString()));
        } else if (cbxTT.getSelectedIndex() == 2) {
            DefaultTableModel ob = (DefaultTableModel) tblHoaDon1.getModel();
            TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
            tblHoaDon1.setRowSorter(obj);
            obj.setRowFilter(RowFilter.regexFilter(cbxTT.getSelectedItem().toString()));
        }

    }//GEN-LAST:event_cbxTTItemStateChanged

    private void addThanhToan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan5ActionPerformed
        main.setForm(new Form_Ban_Hang());
    }//GEN-LAST:event_addThanhToan5ActionPerformed

    private void txtTimKiemTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemTableKeyTyped
        DefaultTableModel ob = (DefaultTableModel) tblHoaDon1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tblHoaDon1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTimKiemTable.getText()));
    }//GEN-LAST:event_txtTimKiemTableKeyTyped

    private void tblHDCT1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCT1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHDCT1MouseClicked

    private void addThanhToan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan6ActionPerformed
        String tt = cbxTT1.getSelectedItem().toString();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhập trạng thái", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataHD.upDateHD(maHD, tt) != null) {
                showMessageBox("Cập nhập thành công");
                TabCTHD.setSelectedIndex(0);
                addRowTableHD();
            } else {
                showMessageBox("Cập nhập thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút Cập nhập");
        }

    }//GEN-LAST:event_addThanhToan6ActionPerformed

    private void addThanhToan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan7ActionPerformed
        Frame parent = null;
        FormQuetQRHD fh = new FormQuetQRHD(parent, true);
        fh.setVisible(true);
        capNhap();
    }//GEN-LAST:event_addThanhToan7ActionPerformed

    private void cbxTT1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTT1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTT1ItemStateChanged

    private void addThanhToan8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToan8ActionPerformed
        maHD = txtTimKiemHDTM.getText();
        capNhap();
    }//GEN-LAST:event_addThanhToan8ActionPerformed

    private void txtTimKiemHDTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemHDTMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemHDTMActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabCTHD;
    private com.MMT_Shop.swing.button.Button addThanhToan;
    private com.MMT_Shop.swing.button.Button addThanhToan1;
    private com.MMT_Shop.swing.button.Button addThanhToan2;
    private com.MMT_Shop.swing.button.Button addThanhToan3;
    private com.MMT_Shop.swing.button.Button addThanhToan4;
    private com.MMT_Shop.swing.button.Button addThanhToan5;
    private com.MMT_Shop.swing.button.Button addThanhToan6;
    private com.MMT_Shop.swing.button.Button addThanhToan7;
    private com.MMT_Shop.swing.button.Button addThanhToan8;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxTT;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxTT1;
    private com.toedter.calendar.JDateChooser dcsNgayBatDau;
    private com.toedter.calendar.JDateChooser dcsNgayKet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane spHD;
    private javax.swing.JScrollPane spHDCT;
    private javax.swing.JScrollPane spHDCT1;
    private com.MMT_Shop.swing.Table tblHDCT;
    private com.MMT_Shop.swing.Table tblHDCT1;
    private com.MMT_Shop.swing.Table tblHoaDon1;
    private com.MMT_Shop.swing.textfield.TextField txtDiaChi;
    private com.MMT_Shop.swing.textfield.TextField txtKhachHang;
    private com.MMT_Shop.swing.textfield.TextField txtMa;
    private com.MMT_Shop.swing.textfield.TextField txtMaNV;
    private com.MMT_Shop.swing.textfield.TextField txtMax;
    private com.MMT_Shop.swing.textfield.TextField txtMin;
    private com.MMT_Shop.swing.textfield.TextField txtNgayTao;
    private com.MMT_Shop.swing.textfield.TextField txtNguoiShip;
    private com.MMT_Shop.swing.textfield.TextField txtSDT;
    private com.MMT_Shop.swing.textfield.TextField txtSDTShip;
    private com.MMT_Shop.swing.textfield.TextField txtTimKiemHDTM;
    private com.MMT_Shop.swing.textfield.TextField txtTimKiemTable;
    private com.MMT_Shop.swing.textfield.TextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
