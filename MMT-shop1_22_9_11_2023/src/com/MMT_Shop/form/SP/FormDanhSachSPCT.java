package com.MMT_Shop.form.SP;

import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.Dao.ThuocTinhDAO;
import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.EnTiTy.SanPham;
import com.MMT_Shop.EnTiTy.SanPhamCT;
import static com.MMT_Shop.form.SP.FormAddSanPhamCT.maSPCT;
import com.MMT_Shop.swing.ButtonTable.TableActionCellEditor;
import com.MMT_Shop.swing.ButtonTable.TableActionCellRender;
import com.MMT_Shop.swing.ButtonTable.TableActionEvent;
import com.MMT_Shop.swing.ScrollBar;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FormDanhSachSPCT extends javax.swing.JPanel {

    private final SanPhamDAO dataSP = new SanPhamDAO();
    private final ThuocTinhDAO dataTT = new ThuocTinhDAO();
    private DefaultTableModel model = new DefaultTableModel();
    public static String luaChon;
    boolean lc;

    public FormDanhSachSPCT() {
        initComponents();
        inti();
        System.out.println(luaChon);

    }

    private void inti() {
        loadCBX();
        addRowTableSPCT();
        tableSPCTMOD();

    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void loadCBX() {
        fillComboBoxSP();
        fillComboBoxCL();
        fillComboBoxH();
        fillComboBoxKT();
        fillComboBoxLK();
        fillComboBoxMS();
    }

    public void addRowTableSPCT() {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        spSPCT.setVerticalScrollBar(new ScrollBar());
        ArrayList<SanPhamCT> data = dataSP.SelectSanPhamCT();
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPhamCT sp = data.get(i);
            tblSPCT.addRow(new Object[]{lc, tblSPCT.getRowCount() + 1, sp.getMaSPCT(), sp.getTenSP(),
                sp.getThuongHieu(), sp.getLoaiKhung(), sp.getChatLieu(), sp.getKichThuoc(), sp.getMauSac(), sp.getGia(), sp.getSoLuong()});

        }

    }

    public void timKiem() {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        spSPCT.setVerticalScrollBar(new ScrollBar());
        ArrayList<SanPhamCT> data = dataSP.SelectSanPhamCTTheoTT(luaChon);
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPhamCT sp = data.get(i);
            tblSPCT.addRow(new Object[]{lc, tblSPCT.getRowCount() + 1, sp.getMaSPCT(), sp.getTenSP(),
                sp.getThuongHieu(), sp.getLoaiKhung(), sp.getChatLieu(), sp.getKichThuoc(), sp.getMauSac(), sp.getGia(), sp.getSoLuong()});

        }
    }

    private void tableSPCTMOD() {

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onDelete(int row) {
                if (tblSPCT.isEditing()) {
                    tblSPCT.getCellEditor().stopCellEditing();
                }
                maSPCT = tblSPCT.getValueAt(row, 2).toString();
                System.out.println(maSPCT);
                deletedSPCT();

//                DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                maSPCT = tblSPCT.getValueAt(row, 2).toString();
                Frame parent = null;
                FormChiTietSP fh = new FormChiTietSP(parent, true);
                fh.setVisible(true);
                addRowTableSPCT();

            }
        };
        tblSPCT.getColumnModel().getColumn(11).setCellRenderer(new TableActionCellRender());
        tblSPCT.getColumnModel().getColumn(11).setCellEditor(new TableActionCellEditor(event));

    }

    private SanPhamCT addSPCT() {
        String maspct = maSPCT;
        SanPhamCT sp = new SanPhamCT();
        sp.setMaSPCT(maspct);
        return sp;
    }

    private void deletedSPCT() {
        SanPhamCT sp = addSPCT();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataSP.DeleteSanPhamCT(sp) != null) {
                showMessageBox("Xoa thành công");
                addRowTableSPCT();

            } else {
                showMessageBox("Xoa thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút Xoa");
        }
        addRowTableSPCT();
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

    private void luuQR() {
        JFileChooser jfc = new JFileChooser("C:\\Users\\Authentic\\Desktop");
        jfc.setDialogTitle("Save QR");

        int excelChooser = jfc.showSaveDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean tt = (boolean) tblSPCT.getValueAt(i, 0);
                if (tt == true) {
                    try {
                        ByteArrayOutputStream out = QRCode.from(tblSPCT.getValueAt(i, 2).toString())
                                .to(ImageType.PNG).stream();
                        String f_name = tblSPCT.getValueAt(i, 2).toString();

                        if (tt == true) {
                            System.out.println(jfc.getSelectedFile());
                            FileOutputStream fout = new FileOutputStream(new File(jfc.getSelectedFile() + (f_name + ".PNG")));
                            fout.write(out.toByteArray());
                            fout.flush();
                        }
                    } catch (Exception e) {
                        System.out.print(e);
                    }

                }
            }
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

                for (int i = 0; i < model.getRowCount(); i++) {
                    XSSFRow excelColumn = excelSheet.createRow(0);
                    XSSFRow excelRow = excelSheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount() - 1; j++) {
                        XSSFCell excelCell = excelRow.createCell(j);
                        XSSFCell nameColumn = excelColumn.createCell(j);
                        nameColumn.setCellValue(model.getColumnName(j));
                        excelCell.setCellValue(model.getValueAt(i, j).toString());
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        cbxTenSP = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel4 = new javax.swing.JPanel();
        cbxHang = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel5 = new javax.swing.JPanel();
        cbxLoaiKhung = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel6 = new javax.swing.JPanel();
        cbxChatLieu = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel7 = new javax.swing.JPanel();
        cbxKichThuoc = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel8 = new javax.swing.JPanel();
        cbxMauSac = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        spSPCT = new javax.swing.JScrollPane();
        tblSPCT = new com.MMT_Shop.swing.Table();
        jPanel9 = new javax.swing.JPanel();
        txtTimKiem = new com.MMT_Shop.swing.textfield.TextField();
        btnXuatFile = new com.MMT_Shop.swing.button.Button();
        btnLamMoi = new com.MMT_Shop.swing.button.Button();
        btnLuuQR = new com.MMT_Shop.swing.button.Button();
        cbAll = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1060, 586));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                formAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Bộ lọc tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 5));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tên sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxTenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTenSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hãng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Loại khung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxLoaiKhung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLoaiKhungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxLoaiKhung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Chất liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxChatLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Kích thước", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel7.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxKichThuocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel7);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Màu sắc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(163, 70));

        cbxMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMauSacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(1, 1, 1)
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 149, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addComponent(cbxMauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel8);

        spSPCT.setBackground(new java.awt.Color(255, 255, 255));

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "STT", "Mã SP chi tiết", "Tên SP", "Hãng ", "Loại khung", "Chất liệu", "Kích thước", "Màu sắc", "Giá", "Số lượng", "Thao tác"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spSPCT.setViewportView(tblSPCT);
        if (tblSPCT.getColumnModel().getColumnCount() > 0) {
            tblSPCT.getColumnModel().getColumn(0).setPreferredWidth(20);
            tblSPCT.getColumnModel().getColumn(0).setMaxWidth(20);
            tblSPCT.getColumnModel().getColumn(9).setMinWidth(70);
            tblSPCT.getColumnModel().getColumn(9).setPreferredWidth(70);
            tblSPCT.getColumnModel().getColumn(9).setMaxWidth(70);
        }

        txtTimKiem.setLabelText("Thông tin muốn tìm kiếm.....");
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnXuatFile.setBackground(new java.awt.Color(172, 182, 229));
        btnXuatFile.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXuatFile.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(172, 182, 229));
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setText("Làm mới ");
        btnLamMoi.setRippleColor(new java.awt.Color(255, 255, 255));
        btnLamMoi.setShadowColor(new java.awt.Color(172, 182, 229));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnLuuQR.setBackground(new java.awt.Color(172, 182, 229));
        btnLuuQR.setForeground(new java.awt.Color(255, 255, 255));
        btnLuuQR.setText("Lưu mã QR");
        btnLuuQR.setRippleColor(new java.awt.Color(255, 255, 255));
        btnLuuQR.setShadowColor(new java.awt.Color(172, 182, 229));
        btnLuuQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuQRActionPerformed(evt);
            }
        });

        cbAll.setText("All");
        cbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbAll)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLuuQR, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLuuQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbAll)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1038, Short.MAX_VALUE)
                    .addComponent(spSPCT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxHangActionPerformed
        luaChon = cbxHang.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxHangActionPerformed

    private void cbxTenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTenSPActionPerformed
        luaChon = cbxTenSP.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxTenSPActionPerformed

    private void cbxLoaiKhungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLoaiKhungActionPerformed
        luaChon = cbxLoaiKhung.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxLoaiKhungActionPerformed

    private void cbxChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxChatLieuActionPerformed
        luaChon = cbxChatLieu.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxChatLieuActionPerformed

    private void cbxKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxKichThuocActionPerformed
        luaChon = cbxKichThuoc.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxKichThuocActionPerformed

    private void cbxMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMauSacActionPerformed
        luaChon = cbxMauSac.getSelectedItem().toString();
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_cbxMauSacActionPerformed

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        timKiem();
        luaChon = "%SP%";
    }//GEN-LAST:event_formAncestorAdded

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        xuatFile();
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        lc = false;
        cbAll.setSelected(lc);
        addRowTableSPCT();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnLuuQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuQRActionPerformed
        luuQR();
    }//GEN-LAST:event_btnLuuQRActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tblSPCT.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tblSPCT.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTimKiem.getText()));
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllActionPerformed
        lc = true;
        addRowTableSPCT();
    }//GEN-LAST:event_cbAllActionPerformed

    private void formAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorMoved
       timKiem();
    }//GEN-LAST:event_formAncestorMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button btnLamMoi;
    private com.MMT_Shop.swing.button.Button btnLuuQR;
    private com.MMT_Shop.swing.button.Button btnXuatFile;
    private javax.swing.JCheckBox cbAll;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxChatLieu;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxHang;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxKichThuoc;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxLoaiKhung;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxMauSac;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxTenSP;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane spSPCT;
    private com.MMT_Shop.swing.Table tblSPCT;
    private com.MMT_Shop.swing.textfield.TextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
