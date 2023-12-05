package com.MMT_Shop.form;

import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.Dao.ThuocTinhDAO;
import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.EnTiTy.SanPham;
import com.MMT_Shop.event.EventItemSelected;
import com.MMT_Shop.form.SP.FormAddSanPhamCT;
import com.MMT_Shop.form.SP.FormDanhSachSPCT;
import com.MMT_Shop.form.SP.FormQuetQR;
import com.MMT_Shop.model.StatusType;
import com.MMT_Shop.swing.ButtonTable.TableActionCellEditor;
import com.MMT_Shop.swing.ButtonTable.TableActionCellRender;
import com.MMT_Shop.swing.ButtonTable.TableActionEvent;
import com.MMT_Shop.swing.FormOfFhoice.BreadcrumbItem;
import com.MMT_Shop.swing.ScrollBar;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Form_San_Pham extends javax.swing.JPanel {

    private final ThuocTinhDAO dataTT = new ThuocTinhDAO();
    private final SanPhamDAO dataSP = new SanPhamDAO();
    private DefaultTableModel model = new DefaultTableModel();
    String code = null;
    String maTT;
    String maSP;
    FormDanhSachSPCT form;

    public Form_San_Pham() {
        initComponents();
        init();
        setForm(new FormDanhSachSPCT());
        FormDanhSachSPCT.luaChon = "%SP%";
    }

    public void setForm(JPanel com) {
        plSPCT.removeAll();
        plSPCT.add(com);
        plSPCT.repaint();
        plSPCT.revalidate();
    }

    private void init() {
        taoMa();
        addRowTableSP();
        addRowTableTTSP();
        tableSPMOD();
        tableTTMOD();
        breadcrumb.setEvent(new EventItemSelected() {
            @Override
            public void selected(BreadcrumbItem item) {
                //JOptionPane.showMessageDialog(Form_San_Pham.this, item.getIndex());
                if (item.getIndex() == 0) {
                    setForm(new FormDanhSachSPCT());
                    FormDanhSachSPCT.luaChon = "%SP%";
                    breadcrumb.remove(1);
                   
                }
            }
        });

    }

    private void tableSPMOD() {

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onDelete(int row) {
                if (tblSanPham.isEditing()) {
                    tblSanPham.getCellEditor().stopCellEditing();
                }
                clickSP();
                deletedSP();
//                DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                tbSP.setSelectedIndex(1);
                FormDanhSachSPCT.luaChon = tblSanPham.getValueAt(row, 2).toString();

            }
        };
        tblSanPham.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender());
        tblSanPham.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event));

    }

    private void tableTTMOD() {

        TableActionEvent event = new TableActionEvent() {

            @Override
            public void onDelete(int row) {
                if (tblThoucTinh.isEditing()) {
                    tblThoucTinh.getCellEditor().stopCellEditing();
                }
                clickTT();
                deletedTT();
//                DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
//                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                tbSP.setSelectedIndex(1);
                FormDanhSachSPCT.luaChon = tblThoucTinh.getValueAt(row, 2).toString();
            }
        };
        tblThoucTinh.getColumnModel().getColumn(3).setCellRenderer(new TableActionCellRender());
        tblThoucTinh.getColumnModel().getColumn(3).setCellEditor(new TableActionCellEditor(event));

    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt() + 10;
            if (x < 0) {
                x = x * -1;
            }
            code = x + "";

        }
    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void clickTT() {
        int row = tblThoucTinh.getSelectedRow();
        maTT = tblThoucTinh.getValueAt(row, 1).toString();
        txtTenThuocTinh.setText(tblThoucTinh.getValueAt(row, 2).toString());
    }

    private void clickSP() {
        int row = tblSanPham.getSelectedRow();
        maSP = tblSanPham.getValueAt(row, 1).toString();
    }

    private SanPham addMaSP() {

        SanPham sp = new SanPham();
        sp.setMa(maSP);
        return sp;
    }

    private void addRowTableSP() {
        model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        spSanPham.setVerticalScrollBar(new ScrollBar());
        ArrayList<SanPham> data = dataSP.SelectSanPham();
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPham sp = data.get(i);
            StatusType trangThai = null;
            if (sp.getSoLuong() >= 20) {
                trangThai = StatusType.CON;
            } else if (sp.getSoLuong() > 0) {
                trangThai = StatusType.SAPHET;
            } else if (sp.getSoLuong() == 0) {
                trangThai = StatusType.HET;
            }
            tblSanPham.addRow(new Object[]{tblSanPham.getRowCount() + 1, sp.getMa(), sp.getTen(), sp.getSoLuong(), trangThai});
        }

    }

    private SanPham addSP() {

        String ten = txtTenSP.getText();
        String ma = maSP;
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
                addRowTableSP();
            } else {
                showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    private void addRowTableTTSP() {
        rdoHang.setSelected(true);
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        jScrollPane3.setVerticalScrollBar(new ScrollBar());
        ArrayList<Hang> data = dataTT.SelectHang();
        for (int i = data.size() - 1; i >= 0; i--) {
            Hang hang = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, hang.getMa(), hang.getTen()});

        }
    }

    private void deletedSP() {
        System.out.println("Sản phẩm");
        SanPham sp = addMaSP();

        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataSP.DeleteSanPham(sp) != null) {
                showMessageBox("Xoa thành công");
                addRowTableSP();

            } else {
                showMessageBox("Xoa thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút Xoa");
        }
        addRowTableSP();
    }

    private Hang addHang() {
        String ten = txtTenThuocTinh.getText();
        String ma = maTT;
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
            Hang sp = new Hang();
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

    private LoaiKhung addKhung() {
        String ten = txtTenThuocTinh.getText();
        String ma = maTT;
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
            LoaiKhung sp = new LoaiKhung();
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

    private ChatLieu addChatLieu() {
        String ten = txtTenThuocTinh.getText();
        String ma = maTT;
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
            ChatLieu sp = new ChatLieu();
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

    private KichThuoc addKichThuoc() {
        String ten = txtTenThuocTinh.getText();
        String ma = maTT;
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
            KichThuoc sp = new KichThuoc();
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

    private MauSac addMauSac() {
        String ten = txtTenThuocTinh.getText();
        String ma = maTT;
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
            MauSac sp = new MauSac();
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

    private void insertTT() {
        if (rdoHang.isSelected() == true) {
            System.out.println("hãng");
            Hang h = addHang();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.InsertHang(h) != null) {
                    showMessageBox("Thêm thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Thêm thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút thêm");
            }

        } else if (rdoKhung.isSelected() == true) {
            System.out.println("khung");
            LoaiKhung h = addKhung();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.InsertKhung(h) != null) {
                    showMessageBox("Thêm thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Thêm thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút thêm");
            }

        } else if (rdoChatLieu.isSelected() == true) {
            System.out.println("chất liệu ");
            ChatLieu h = addChatLieu();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.InsertChatLieu(h) != null) {
                    showMessageBox("Thêm thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Thêm thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút thêm");
            }

        } else if (RdoKichThuoc.isSelected() == true) {
            System.out.println("kích thước ");
            KichThuoc h = addKichThuoc();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.InsertKichThuoc(h) != null) {
                    showMessageBox("Thêm thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Thêm thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút thêm");
            }

        } else if (rdoMau.isSelected() == true) {
            System.out.println("màu ");
            MauSac h = addMauSac();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.InsertMauSac(h) != null) {
                    showMessageBox("Thêm thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Thêm thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút thêm");
            }

        } else {
            showMessageBox("bạn chưa chọn thuộc tính");
        }
        addRowTableTTSP();
    }

    private void deletedTT() {
        if (rdoHang.isSelected() == true) {
            System.out.println("hãng");
            Hang h = addHang();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.DeleteHang(h) != null) {
                    showMessageBox("Xoa thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Xoa thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút Xoa");
            }

        } else if (rdoKhung.isSelected() == true) {
            System.out.println("khung");
            LoaiKhung h = addKhung();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.DeleteKhung(h) != null) {
                    showMessageBox("Xoa thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Xoa thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Xoa nút thêm");
            }

        } else if (rdoChatLieu.isSelected() == true) {
            System.out.println("chất liệu ");
            ChatLieu h = addChatLieu();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.DeleteChatLieu(h) != null) {
                    showMessageBox("Xoa thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Xoa thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Xoa nút thêm");
            }

        } else if (RdoKichThuoc.isSelected() == true) {
            System.out.println("kích thước ");
            KichThuoc h = addKichThuoc();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.DeleteKichThuoc(h) != null) {
                    showMessageBox("Xoa thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Xoa thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút Xoa");
            }

        } else if (rdoMau.isSelected() == true) {
            System.out.println("màu ");
            MauSac h = addMauSac();
            try {
                int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (lc != JOptionPane.YES_OPTION) {
                    return;
                }
                if (dataTT.DeleteMauSac(h) != null) {
                    showMessageBox("Xoa thành công");
                    addRowTableTTSP();
                    txtTenThuocTinh.setText("");

                } else {
                    showMessageBox("Xoa thất bại");
                }
            } catch (Exception e) {
                showMessageBox("Lỗi nút Xoa");
            }

        } else {
            showMessageBox("bạn chưa chọn thuộc tính");
        }
        addRowTableTTSP();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel17 = new javax.swing.JPanel();
        tbSP = new javax.swing.JTabbedPane();
        tdpSP = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        spSanPham = new javax.swing.JScrollPane();
        tblSanPham = new com.MMT_Shop.swing.Table_SP();
        jPanel1 = new javax.swing.JPanel();
        txtTenSP = new com.MMT_Shop.swing.textfield.TextField();
        btnThemSP = new com.MMT_Shop.swing.button.Button();
        tdpSPCT = new javax.swing.JPanel();
        plSPCTTong = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        breadcrumb = new com.MMT_Shop.swing.FormOfFhoice.Breadcrumb();
        addSanPham1 = new com.MMT_Shop.swing.button.Button();
        addSanPham = new com.MMT_Shop.swing.button.Button();
        plSPCT = new javax.swing.JPanel();
        tdbThuocTinh = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        rdoHang = new javax.swing.JRadioButton();
        rdoKhung = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        RdoKichThuoc = new javax.swing.JRadioButton();
        rdoMau = new javax.swing.JRadioButton();
        txtTenThuocTinh = new com.MMT_Shop.swing.textfield.TextField();
        jPanel12 = new javax.swing.JPanel();
        btnLamMoi = new com.MMT_Shop.swing.button.Button();
        btnThem = new com.MMT_Shop.swing.button.Button();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblThoucTinh = new com.MMT_Shop.swing.Table();

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        tbSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSPMouseClicked(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên Sản phẩm ", "Số lượng ", "Trạng thái", "Thao tác"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setGridColor(new java.awt.Color(255, 255, 255));
        tblSanPham.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        spSanPham.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(4).setMinWidth(120);
            tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblSanPham.getColumnModel().getColumn(4).setMaxWidth(120);
            tblSanPham.getColumnModel().getColumn(5).setMinWidth(120);
            tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(120);
            tblSanPham.getColumnModel().getColumn(5).setMaxWidth(120);
        }

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTenSP.setLabelText("Tên sản phẩm");
        txtTenSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenSPMouseClicked(evt);
            }
        });

        btnThemSP.setBackground(new java.awt.Color(172, 182, 229));
        btnThemSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSP.setText("Thêm");
        btnThemSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemSP.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(spSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tdpSPLayout = new javax.swing.GroupLayout(tdpSP);
        tdpSP.setLayout(tdpSPLayout);
        tdpSPLayout.setHorizontalGroup(
            tdpSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tdpSPLayout.setVerticalGroup(
            tdpSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tdpSPLayout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tbSP.addTab("Danh sách sản phẩm ", tdpSP);

        tdpSPCT.setBackground(new java.awt.Color(255, 255, 255));

        breadcrumb.setColor1(new java.awt.Color(172, 182, 229));
        breadcrumb.setColor2(new java.awt.Color(116, 235, 213));
        breadcrumb.setColorSelected(new java.awt.Color(255, 255, 255));

        addSanPham1.setBackground(new java.awt.Color(116, 235, 213));
        addSanPham1.setForeground(new java.awt.Color(245, 245, 245));
        addSanPham1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Qr Code.png"))); // NOI18N
        addSanPham1.setText("Quét mã QR");
        addSanPham1.setRippleColor(new java.awt.Color(255, 255, 255));
        addSanPham1.setShadowColor(new java.awt.Color(116, 235, 213));
        addSanPham1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSanPham1ActionPerformed(evt);
            }
        });

        addSanPham.setBackground(new java.awt.Color(116, 235, 213));
        addSanPham.setForeground(new java.awt.Color(245, 245, 245));
        addSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Add.png"))); // NOI18N
        addSanPham.setText("Thêm sản phẩm");
        addSanPham.setRippleColor(new java.awt.Color(255, 255, 255));
        addSanPham.setShadowColor(new java.awt.Color(116, 235, 213));
        addSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(breadcrumb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addSanPham1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(breadcrumb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(addSanPham1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        plSPCT.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout plSPCTTongLayout = new javax.swing.GroupLayout(plSPCTTong);
        plSPCTTong.setLayout(plSPCTTongLayout);
        plSPCTTongLayout.setHorizontalGroup(
            plSPCTTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plSPCTTongLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(plSPCTTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 1060, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        plSPCTTongLayout.setVerticalGroup(
            plSPCTTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plSPCTTongLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tdpSPCTLayout = new javax.swing.GroupLayout(tdpSPCT);
        tdpSPCT.setLayout(tdpSPCTLayout);
        tdpSPCTLayout.setHorizontalGroup(
            tdpSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plSPCTTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tdpSPCTLayout.setVerticalGroup(
            tdpSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(plSPCTTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tbSP.addTab("Chi tiết sản phẩm ", tdpSPCT);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thiết lập thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel10.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel10PropertyChange(evt);
            }
        });

        buttonGroup1.add(rdoHang);
        rdoHang.setText("Hãng");
        rdoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoHangActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKhung);
        rdoKhung.setText("Loại khung");
        rdoKhung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhungActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setText("Chất liệu ");
        rdoChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChatLieuActionPerformed(evt);
            }
        });

        buttonGroup1.add(RdoKichThuoc);
        RdoKichThuoc.setText("Kích thước ");
        RdoKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdoKichThuocActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoMau);
        rdoMau.setText("Màu sắc");
        rdoMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMauActionPerformed(evt);
            }
        });

        txtTenThuocTinh.setLabelText("Tên thuộc tính");
        txtTenThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTenThuocTinhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(rdoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(rdoKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdoChatLieu, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RdoKichThuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoMau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rdoHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rdoKhung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RdoKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(rdoMau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnLamMoi.setBackground(new java.awt.Color(172, 182, 229));
        btnLamMoi.setForeground(new java.awt.Color(245, 245, 245));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setRippleColor(new java.awt.Color(255, 255, 255));
        btnLamMoi.setShadowColor(new java.awt.Color(172, 182, 229));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(172, 182, 229));
        btnThem.setForeground(new java.awt.Color(245, 245, 245));
        btnThem.setText("Thêm");
        btnThem.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Danh sách thuộc tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblThoucTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã thuộc tính", "Tên thuộc tính", "Thao tác "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThoucTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThoucTinhMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblThoucTinh);
        if (tblThoucTinh.getColumnModel().getColumnCount() > 0) {
            tblThoucTinh.getColumnModel().getColumn(3).setMinWidth(120);
            tblThoucTinh.getColumnModel().getColumn(3).setPreferredWidth(120);
            tblThoucTinh.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tdbThuocTinhLayout = new javax.swing.GroupLayout(tdbThuocTinh);
        tdbThuocTinh.setLayout(tdbThuocTinhLayout);
        tdbThuocTinhLayout.setHorizontalGroup(
            tdbThuocTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tdbThuocTinhLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        tdbThuocTinhLayout.setVerticalGroup(
            tdbThuocTinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tbSP.addTab("Thuộc tính sản phẩm ", tdbThuocTinh);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbSP))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbSP)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoHangActionPerformed
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        ArrayList<Hang> data = dataTT.SelectHang();
        for (int i = data.size() - 1; i >= 0; i--) {
            Hang hang = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, hang.getMa(), hang.getTen()});

        }
    }//GEN-LAST:event_rdoHangActionPerformed

    private void rdoKhungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhungActionPerformed
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        ArrayList<LoaiKhung> data = dataTT.SelectKhung();
        for (int i = data.size() - 1; i >= 0; i--) {
            LoaiKhung lk = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, lk.getMa(), lk.getTen()});

        }
    }//GEN-LAST:event_rdoKhungActionPerformed

    private void rdoChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChatLieuActionPerformed
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        ArrayList<ChatLieu> data = dataTT.SelectChatLieu();
        for (int i = data.size() - 1; i >= 0; i--) {
            ChatLieu lk = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, lk.getMa(), lk.getTen()});

        }
    }//GEN-LAST:event_rdoChatLieuActionPerformed

    private void RdoKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RdoKichThuocActionPerformed
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        ArrayList<KichThuoc> data = dataTT.SelectKichThuoc();
        for (int i = data.size() - 1; i >= 0; i--) {
            KichThuoc lk = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, lk.getMa(), lk.getTen()});

        }
    }//GEN-LAST:event_RdoKichThuocActionPerformed

    private void rdoMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMauActionPerformed
        model = (DefaultTableModel) tblThoucTinh.getModel();
        model.setRowCount(0);
        ArrayList<MauSac> data = dataTT.SelectMauSac();
        for (int i = data.size() - 1; i >= 0; i--) {
            MauSac lk = data.get(i);
            tblThoucTinh.addRow(new Object[]{tblThoucTinh.getRowCount() + 1, lk.getMa(), lk.getTen()});
        }
    }//GEN-LAST:event_rdoMauActionPerformed

    private void tblThoucTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThoucTinhMouseClicked
        clickTT();
    }//GEN-LAST:event_tblThoucTinhMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        clickSP();

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        if (rdoHang.isSelected() == true) {
            maTT = "H" + code;
        } else if (rdoKhung.isSelected() == true) {
            maTT = "LK" + code;
        } else if (rdoChatLieu.isSelected() == true) {
            maTT = "CL" + code;
        } else if (RdoKichThuoc.isSelected() == true) {
            maTT = "KT" + code;
        } else if (rdoMau.isSelected() == true) {
            maTT = "MS" + code;
        } else {
            showMessageBox("bạn chưa chọn thuộc tính");
        }
        txtTenThuocTinh.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insertTT();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtTenThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenThuocTinhMouseClicked
        if (rdoHang.isSelected() == true) {
            maTT = "H" + code;
        } else if (rdoKhung.isSelected() == true) {
            maTT = "LK" + code;
        } else if (rdoChatLieu.isSelected() == true) {
            maTT = "CL" + code;
        } else if (RdoKichThuoc.isSelected() == true) {
            maTT = "KT" + code;
        } else if (rdoMau.isSelected() == true) {
            maTT = "MS" + code;
        } else {
            showMessageBox("bạn chưa chọn thuộc tính");
        }
    }//GEN-LAST:event_txtTenThuocTinhMouseClicked

    private void tbSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSPMouseClicked
        addRowTableTTSP();
        addRowTableSP();
    }//GEN-LAST:event_tbSPMouseClicked

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        insertSP();
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void jPanel10PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel10PropertyChange
        if (rdoHang.isSelected() == true) {
            maTT = "H" + code;
        } else if (rdoKhung.isSelected() == true) {
            maTT = "LK" + code;
        } else if (rdoChatLieu.isSelected() == true) {
            maTT = "CL" + code;
        } else if (RdoKichThuoc.isSelected() == true) {
            maTT = "KT" + code;
        } else if (rdoMau.isSelected() == true) {
            maTT = "MS" + code;
        } else {
            showMessageBox("bạn chưa chọn thuộc tính");
        }
    }//GEN-LAST:event_jPanel10PropertyChange

    private void txtTenSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTenSPMouseClicked
        maSP = "SP" + code;
    }//GEN-LAST:event_txtTenSPMouseClicked

    private void addSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSanPhamActionPerformed
        setForm(new FormAddSanPhamCT());
        breadcrumb.addItem("Thêm sản phẩm");
        FormDanhSachSPCT.luaChon = "%SP%";

    }//GEN-LAST:event_addSanPhamActionPerformed

    private void addSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSanPham1ActionPerformed
        Frame parent = null;
        FormQuetQR fh = new FormQuetQR(parent, true);
        fh.setVisible(true);

    }//GEN-LAST:event_addSanPham1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RdoKichThuoc;
    private com.MMT_Shop.swing.button.Button addSanPham;
    private com.MMT_Shop.swing.button.Button addSanPham1;
    private com.MMT_Shop.swing.FormOfFhoice.Breadcrumb breadcrumb;
    private com.MMT_Shop.swing.button.Button btnLamMoi;
    private com.MMT_Shop.swing.button.Button btnThem;
    private com.MMT_Shop.swing.button.Button btnThemSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel plSPCT;
    private javax.swing.JPanel plSPCTTong;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoHang;
    private javax.swing.JRadioButton rdoKhung;
    private javax.swing.JRadioButton rdoMau;
    private javax.swing.JScrollPane spSanPham;
    private javax.swing.JTabbedPane tbSP;
    private com.MMT_Shop.swing.Table_SP tblSanPham;
    private com.MMT_Shop.swing.Table tblThoucTinh;
    private javax.swing.JPanel tdbThuocTinh;
    private javax.swing.JPanel tdpSP;
    private javax.swing.JPanel tdpSPCT;
    private com.MMT_Shop.swing.textfield.TextField txtTenSP;
    private com.MMT_Shop.swing.textfield.TextField txtTenThuocTinh;
    // End of variables declaration//GEN-END:variables
}
