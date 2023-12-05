package com.MMT_Shop.form;

import com.MMT_Shop.Chien.HoaDon;
import com.MMT_Shop.Chien.HoaDonChiTiet;
import com.MMT_Shop.Dao.BanHangDao;
import com.MMT_Shop.Dao.SanPhamDAO;
import com.MMT_Shop.Dao.ThuocTinhDAO;
import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.HinhThucThanhToan;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.EnTiTy.SanPham;
import com.MMT_Shop.EnTiTy.SanPhamCT;
import com.MMT_Shop.Dao.VoucherDAO;
import com.MMT_Shop.Lam.Voucher;
import com.MMT_Shop.Tung.KhachHang;
import com.MMT_Shop.Tung.KhachHangSV;
import com.MMT_Shop.form.banHang.FormQuetQRBH;
import com.MMT_Shop.form.banHang.view_Khach_Hang;
import com.MMT_Shop.form.banHang.view_Nhan_Vien;
import com.MMT_Shop.ngan.NhanVien;
import com.MMT_Shop.ngan.NhanVienService;
import com.MMT_Shop.swing.ScrollBar;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.awt.Frame;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class Form_Ban_Hang extends javax.swing.JPanel implements Runnable, ThreadFactory {

    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<SanPhamCT> data = new ArrayList<>();
    private ArrayList<HoaDon> listHD = new ArrayList<>();
    private ArrayList<KhachHang> listKH = new ArrayList<>();
    private ArrayList<NhanVien> listNV = new ArrayList<>();
    private ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
    private final SanPhamDAO dataSP = new SanPhamDAO();
    private final BanHangDao dataBH = new BanHangDao();
    private final KhachHangSV dataKH = new KhachHangSV();
    private final ThuocTinhDAO dataTT = new ThuocTinhDAO();
    private final NhanVienService dataNV = new NhanVienService();
    private final VoucherDAO dataVC = new VoucherDAO();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    String timKiem;
    String maHD;
    String soLuong;
    String soLuongTon;
    String code;
    String maHDCTMoi;
    String maHDMoi;
    public static String maSPCT;
    String gia;
    String soLuongDaMua;
    String soLuongThayDoi;
    String thanhTien;
    float tongTien;
    float tongTienBD;
    String maVC;
    String maKH;
    String maHT;
    String diaChi;

    public Form_Ban_Hang() {
        initComponents();
        inti();

    }

    private void inti() {
        if (cbxPT.getSelectedIndex() == 0) {
            txtTienCk.setEditable(false);
        }
        if (cbxPT1.getSelectedIndex() == 0) {
            txtTienCk1.setEditable(false);
        }
        addRowTableSPCT();
        addRowTableHD();
        // initWebcam();
        loadCBX();
        Timer timer = new Timer();
        timer.schedule(new DeletedHDTask(), getNextExecutionTime(), 1000 * 60 * 60 * 24);
    }

    private Date getNextExecutionTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 17); // Thiết lập giờ là 6 giờ sáng
        calendar.set(Calendar.MINUTE, 47); // thiết lập phút 
        calendar.set(Calendar.SECOND, 0);

        // Nếu thời điểm đã qua, thì chuyển sang ngày tiếp theo
        if (calendar.getTime().compareTo(new Date()) < 0) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return calendar.getTime();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Thread newThread(Runnable r) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private class DeletedHDTask extends TimerTask {

        @Override
        public void run() {
            dataBH.deletedHD();
            addRowTableHD();
            addRowTableHDCT(maHD);
        }
    }

    private void loadCBX() {
        fillComboBoxSP();
        fillComboBoxCL();
        fillComboBoxH();
        fillComboBoxKT();
        fillComboBoxLK();
        fillComboBoxMS();
        timKiem();
    }

    private void taoMa() {
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt() + 10;
            if (x < 0) {
                x = x * -1;
            }
            code = x + "";
            maHDMoi = "HD" + code;
            maHDCTMoi = "HDCT" + code;
            maHT = "HT" + code;
        }
    }

    private void showMessageBox(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void addRowTableSPCT() {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        spSPCT.setVerticalScrollBar(new ScrollBar());
        data = dataSP.SelectSanPhamCT();
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPhamCT sp = data.get(i);
            tblSPCT.addRow(new Object[]{tblSPCT.getRowCount() + 1, sp.getMaSPCT(), sp.getTenSP(),
                sp.getThuongHieu(), sp.getLoaiKhung(), sp.getChatLieu(), sp.getKichThuoc(),
                sp.getMauSac(), sp.getGia(), sp.getSoLuong()});
        }

    }

    private void addRowTableHD() {
        model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        spHD.setVerticalScrollBar(new ScrollBar());
        listHD = dataBH.selectALLHD();
        for (int i = listHD.size() - 1; i >= 0; i--) {
            HoaDon hd = listHD.get(i);
            tblHoaDon.addRow(new Object[]{tblHoaDon.getRowCount() + 1, hd.getMaHD(),
                hd.getCreated_At().substring(0, 10), hd.getMaNV(), hd.getTenKH(), hd.getSoLuongSP(), hd.getTrangThai()});
        }

    }

    public void addRowTableHDCT(String maHD) {
        model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0);
        spHDCT.setVerticalScrollBar(new ScrollBar());
        listHDCT = dataBH.selectALLHDCT(maHD);
        for (int i = listHDCT.size() - 1; i >= 0; i--) {
            HoaDonChiTiet hdct = listHDCT.get(i);
            double tt = hdct.getThanhTien();
            tongTien += tt;
            tongTienBD += tt;
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

    private void clickHD() {
        int row = tblHoaDon.getSelectedRow();
        maHD = tblHoaDon.getValueAt(row, 1).toString();
        addRowTableHDCT(maHD);
        loatData(maHD);

        fillComboBoxVC();
    }

    private HoaDonChiTiet addHDCT() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setMaHDCT(maHDCTMoi);
        hdct.setMaHD(maHD);
        hdct.setMaCTSP(maSPCT);
        hdct.setSoLuong(Integer.valueOf(soLuong));
        hdct.setGia(Float.valueOf(gia));
        float thanhTien = Integer.valueOf(soLuong) * Float.valueOf(gia);
        hdct.setThanhTien(thanhTien);
        hdct.setCreated_At(At);
        hdct.setUpdated_At(At);
        hdct.setCreated_By(mnv);
        hdct.setUpdated_By(mnv);
        hdct.setDeleted(mnv);
        return hdct;
    }

    private SanPhamCT getFormSPCT() {

        SanPhamCT sp = new SanPhamCT();
        sp.setMaSPCT(maSPCT);
        sp.setSoLuong(Integer.valueOf(soLuongTon));
        return sp;

    }

    private void upDatedSPCT() {
        //System.out.println("Sản phẩm");
        SanPhamCT sp = getFormSPCT();
        try {
            if (dataSP.UpDateSLSPCT(sp) != null) {
            } else {
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút sửa");
        }
    }

    void insertHDCT() {
        HoaDonChiTiet hd = addHDCT();
        try {
            if (dataBH.InsertHDCT(hd) != null) {
                showMessageBox("Thêm thành công");
                addRowTableHDCT(maHD);
                upDatedSPCT();
                addRowTableSPCT();
            } else {
                showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    private HoaDonChiTiet getFormHDCT() {
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setMaHDCT(maHDCTMoi);
        hdct.setSoLuong(Integer.valueOf(soLuongThayDoi));
        hdct.setThanhTien(Float.valueOf(thanhTien));
        return hdct;
    }

    private void upDatedHDCT() {
        //System.out.println("Sản phẩm");
        HoaDonChiTiet hd = getFormHDCT();
        try {
            if (dataBH.updateHDCT(hd) != null) {
                upDatedSPCT();
                addRowTableSPCT();
                addRowTableHDCT(maHD);

            } else {
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút sửa");
        }
    }

    private void deleteHDCT() {
        HoaDonChiTiet sp = getFormHDCT();
        try {
            if (dataBH.deletedHDCT(sp) != null) {
                upDatedSPCT();
                addRowTableSPCT();
                addRowTableHDCT(maHD);
            } else {
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút sửa");
        }
    }

    private HoaDon addHD() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        String hinhThucMua;
        if (tpHinhThuc.getSelectedIndex() == 0) {
            hinhThucMua = "Tại Quầy";
        } else {
            hinhThucMua = "Đặt hàng";
        }
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        HoaDon hd = new HoaDon();
        hd.setMaNV(mnv);
        hd.setMaHD(maHDMoi);
        hd.setHinhThucMua(hinhThucMua);
        hd.setTongTien(0);
        hd.setDiaChi("");
        hd.setTenKH("Khách hàng mua lẻ");
        hd.setTrangThai("Chưa thanh toán");
        hd.setCreated_At(At);
        hd.setUpdated_At(At);
        hd.setCreated_By(mnv);
        hd.setUpdated_By(mnv);
        hd.setDeleted(mnv);
        return hd;
    }

    private void insertHD() {
        HoaDon hd = addHD();
        try {
//            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
//            if (lc != JOptionPane.YES_OPTION) {
//                return;
//            }
            if (dataBH.InsertHD(hd) != null) {
                showMessageBox("Thêm thành công");
                addRowTableHD();
                addQRCode();
            } else {
                showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    void loadKH() {
        listKH = dataKH.selectkh(view_Khach_Hang.maKH);
        for (KhachHang kh : listKH) {

            txtSDT.setText(kh.getSDT());
            txtTenKH.setText(kh.getTenKH());
            txtSDT1.setText(kh.getSDT());
            txtTenKH1.setText(kh.getTenKH());
            txtDiachi.setText(kh.getDiaChi());
            maKH = kh.getMaKH();
        }

    }

    void loadNV() {
        NhanVien nv = new NhanVien();
        nv = dataNV.selectById(view_Nhan_Vien.maNV);
        txtSDTNS.setText(nv.getSdt());
        txtTenNS.setText(nv.getHoTen());

    }

    private void timKiem() {
        model = (DefaultTableModel) tblSPCT.getModel();
        model.setRowCount(0);
        spSPCT.setVerticalScrollBar(new ScrollBar());
        ArrayList<SanPhamCT> data = dataSP.SelectSanPhamCTTheoTT(timKiem);
        for (int i = data.size() - 1; i >= 0; i--) {
            SanPhamCT sp = data.get(i);
            tblSPCT.addRow(new Object[]{tblSPCT.getRowCount() + 1, sp.getMaSPCT(), sp.getTenSP(),
                sp.getThuongHieu(), sp.getLoaiKhung(), sp.getChatLieu(), sp.getKichThuoc(),
                sp.getMauSac(), sp.getGia(), sp.getSoLuong()});
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

    void fillComboBoxVC() {
        DefaultComboBoxModel modelCBX = (DefaultComboBoxModel) cbxVC.getModel();
        DefaultComboBoxModel modelCBX1 = (DefaultComboBoxModel) cbxVC1.getModel();
        modelCBX.removeAllElements();
        modelCBX1.removeAllElements();
        // modelCBX.addElement("Tất cả");
        ArrayList<Voucher> data = dataVC.selectALL();
        for (int i = data.size() - 1; i >= 0; i--) {
            Voucher tt = data.get(i);
            if (tt.getStatus().equals("Đang diễn ra") && tt.getGiaTriHoaDonToiThieu() < Float.valueOf(tongTien)) {
                modelCBX.addElement(tt.getCode());
            }
            if (tt.getStatus().equals("Đang diễn ra") && tt.getGiaTriHoaDonToiThieu() < Float.valueOf(tongTien)) {
                modelCBX1.addElement(tt.getCode());
            }
        }

    }

    private void loatData(String maHD) {
        ArrayList<HoaDon> data = dataBH.HDTheoMa(maHD);
        for (HoaDon hd : data) {
            txtTenKH.setText(hd.getTenKH());
            txtTenKH1.setText(hd.getTenKH());
            txtMaHD.setText(hd.getMaHD());
            txtMaHD1.setText(hd.getMaHD());
            txtTenNV.setText(hd.getTenNV());
            txtTenNV1.setText(hd.getTenNV());
            txtTongTien.setText(String.valueOf(tongTien));
            txtTongTien1.setText(String.valueOf(tongTienBD));
            txtNgayTao.setText(hd.getCreated_At().substring(0, 10));
        }

    }

    private void giamGia() {
        ArrayList<Voucher> giamGia = dataBH.vcTheoMa(maVC);
        Voucher vc = giamGia.get(0);
        float soTienGiamTD = tongTien * Float.valueOf((float) (vc.getPhanTramGiam() / 100));
        if (soTienGiamTD > vc.getSoTienGiamToiDa()) {
            txtSoTienGiam.setText(String.valueOf(vc.getSoTienGiamToiDa()));
            txtTongTien.setText(String.valueOf(tongTien - vc.getSoTienGiamToiDa()));

        } else {
            txtSoTienGiam.setText(String.valueOf(soTienGiamTD));
            txtTongTien.setText(String.valueOf(tongTien - soTienGiamTD));
        }
        float soTienGiamTD1 = tongTienBD * Float.valueOf((float) (vc.getPhanTramGiam() / 100));
        if (soTienGiamTD1 > vc.getSoTienGiamToiDa()) {
            txtSoTienGiam1.setText(String.valueOf(vc.getSoTienGiamToiDa()));
        } else {
            txtSoTienGiam1.setText(String.valueOf(soTienGiamTD));
        }

    }

    private HoaDon thanhToan() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(maHD);
        System.out.println(maHD);
        hd.setTongTien(tongTien);
        hd.setSdt(txtSDT.getText());
        hd.setMaKH(maKH);
        hd.setTenKH(txtTenKH.getText());
        hd.setVoucher(cbxVC.getSelectedItem().toString().trim());
        hd.setTrangThai("Đã thanh toán.");
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        hd.setNgayHoanThanh(At);
        hd.setNgayGiaoDich(At);
        return hd;
    }

    private HoaDon vanChuyen() {
        HoaDon hd = new HoaDon();
        hd.setMaHD(maHD);
        hd.setTongTien(Float.valueOf(txtThanhToan.getText()));
        hd.setSdt(txtSDT1.getText());
        hd.setMaKH(maKH);
        hd.setTenKH(txtTenKH1.getText());
        hd.setVoucher(cbxVC1.getSelectedItem().toString().trim());
        hd.setTrangThai("Đang vận chuyển.");
        hd.setTenNGuoiShip(txtTenNS.getText());
        hd.setSdtNguoiShip(txtSDTNS.getText());
        hd.setDiaChi(txtDiachi.getText());
        return hd;
    }

    private HinhThucThanhToan hinhThuc() {

        HinhThucThanhToan ht = new HinhThucThanhToan();
        ht.setMaHD(maHD);
        if (cbxPT.getSelectedIndex() == 0) {
            ht.setIdHinhthuc(cbxPT.getSelectedIndex() + 4);
        } else if (cbxPT.getSelectedIndex() == 1) {
            ht.setIdHinhthuc(cbxPT.getSelectedIndex());
        } else {
            ht.setIdHinhthuc(cbxPT.getSelectedIndex());
        }
        ht.setMaHT(maHT);
        ht.setTienMat(Float.valueOf(txtTienKhachDua.getText()));
        ht.setTienCK(Float.valueOf(txtTienCk.getText()));
        ht.setTienTL(Float.valueOf(txtTienTraLai.getText()));
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        ht.setCreated_At(At);
        ht.setUpdated_At(At);
        ht.setCreated_By(mnv);
        ht.setUpdated_By(mnv);
        ht.setDeleted(mnv);
        return ht;

    }

    private HinhThucThanhToan hinhThuc1() {

        HinhThucThanhToan ht = new HinhThucThanhToan();
        ht.setMaHD(maHD);
        if (cbxPT1.getSelectedIndex() == 0) {
            ht.setIdHinhthuc(cbxPT1.getSelectedIndex() + 4);
        } else if (cbxPT1.getSelectedIndex() == 1) {
            ht.setIdHinhthuc(cbxPT1.getSelectedIndex());
        } else {
            ht.setIdHinhthuc(cbxPT1.getSelectedIndex());
        }
        ht.setMaHT(maHT);
        ht.setTienMat(Float.valueOf(txtTienKhachDua1.getText()));
        ht.setTienCK(Float.valueOf(txtTienCk1.getText()));
        ht.setTienTL(Float.valueOf(txtTienTraLai1.getText()));
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String At = format.format(now);
        //về sau có đăng nhập thì lấy mã nv đang đăng ngập
        String mnv = "NV01";
        ht.setCreated_At(At);
        ht.setUpdated_At(At);
        ht.setCreated_By(mnv);
        ht.setUpdated_By(mnv);
        ht.setDeleted(mnv);
        return ht;

    }

    private void UPdateHD() {
        HoaDon ht = thanhToan();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thanh toán", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }

            if (dataBH.upDateHD(ht) != null) {
                showMessageBox("Thanh toán thành công");
                insertHT(hinhThuc());
                addRowTableHD();
                DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
                model.setRowCount(0);
            } else {
                showMessageBox("Thanh toán thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thanh toán ");
        }
    }

    private void vanChuyenHD() {
        HoaDon ht = vanChuyen();
        try {
            int lc = JOptionPane.showConfirmDialog(this, "Vận chuyển đơn hàng", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (lc != JOptionPane.YES_OPTION) {
                return;
            }
            if (dataBH.upDateHDDatHang(ht) != null) {
                showMessageBox("Đơn hàng đã được chuyển");
                insertHT(hinhThuc1());
                addRowTableHD();
                DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
                model.setRowCount(0);
            } else {
                showMessageBox("thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút vận chuyển ");
        }
    }

    private void insertHT(HinhThucThanhToan a) {
        HinhThucThanhToan ht = a;
        try {
//            int lc = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm", "Thông báo", JOptionPane.YES_NO_OPTION);
//            if (lc != JOptionPane.YES_OPTION) {
//                return;
//            }
            if (dataBH.InsertHT(ht) != null) {
                //showMessageBox("Thêm thành công");
                addRowTableHD();
            } else {
                //showMessageBox("Thêm thất bại");
            }
        } catch (Exception e) {
            showMessageBox("Lỗi nút thêm");
        }
    }

    void xoaTheoSL() {
        String input = JOptionPane.showInputDialog("Nhập số lượng mới:", soLuongDaMua);
        if (input != null) {
            try {
                soLuong = input;
            } catch (NumberFormatException ex) {
                // JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                System.out.print(ex);
            }
            soLuongTon = String.valueOf(dataSP.sLSPTheoma(maSPCT) + Integer.valueOf(soLuong));
            soLuongThayDoi = String.valueOf(Integer.valueOf(soLuongDaMua) - Integer.valueOf(soLuong));
            int row = tblHDCT.getSelectedRow();
            thanhTien = String.valueOf(Integer.valueOf(soLuongThayDoi) * Integer.valueOf(tblHDCT.getValueAt(row, 10).toString()));

            if (Integer.valueOf(soLuong) < 0 || Integer.valueOf(soLuong) > Integer.valueOf(soLuongDaMua)) {
                showMessageBox("Số lượng đã vượt quá");
            } else if (Integer.valueOf(soLuong) == Integer.valueOf(soLuongDaMua)) {
                deleteHDCT();
            } else {
                upDatedHDCT();

            }
        }

    }

    void clear() {
        txtSDT.setText("");
        txtSDT1.setText("");
        txtTenKH.setText("");
        txtTenKH1.setText("");
        txtTenNS.setText("");
        txtSDTNS.setText("");
        txtMaHD.setText("");
        txtMaHD1.setText("");
        txtTenNV.setText("");
        cbxVC.removeAllItems();
        cbxVC1.removeAllItems();
        txtSoTienGiam.setText("");
        txtSoTienGiam1.setText("");
        txtNgayTao.setText("");
        txtTongTien.setText("");
        txtTongTien1.setText("");
        txtTienKhachDua.setText("");
        txtTienKhachDua1.setText("");
        txtTienCk.setText("");
        txtTienCk1.setText("");
        txtTienTraLai.setText("");
        txtTienTraLai1.setText("");
        txtDiachi.setText("");
        txtPhiShips.setText("");
        txtThanhToan.setText("");
    }

    private void addQRCode() {
        try {
            ByteArrayOutputStream out = QRCode.from(maHDMoi)
                    .to(ImageType.PNG).stream();
            String f_name = maHDMoi;
            String Path_name = "G:\\DU_AN_1\\MMT-shop1_22_9_11_2023\\src\\com\\MMT_Shop\\icon\\QRHD\\";
            FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
            fout.write(out.toByteArray());
            fout.flush();
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    void mua() {
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng", "thồng báo", 3);
        if (input != null) {
            try {
                int newQuantity = Integer.parseInt(input);
                soLuong = input;
            } catch (NumberFormatException ex) {
                // JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                System.out.print(ex);
            }
            int row = tblSPCT.getSelectedRow();
            int soLuongBD = Integer.valueOf(tblSPCT.getValueAt(row, 9).toString());
            System.out.println(soLuongBD);
            soLuongTon = String.valueOf(soLuongBD - Integer.valueOf(soLuong));
            if (Integer.valueOf(soLuong) < 0 || Integer.valueOf(soLuong) > Integer.valueOf(soLuongBD)) {
                showMessageBox("Số lượng đã vượt quá");
            } else {
                boolean check = false;
                for (HoaDonChiTiet hd : listHDCT) {
                    for (SanPhamCT sp : data) {
                        if (hd.getMaCTSP().equals(sp.getMaSPCT())) {
                            soLuongThayDoi = String.valueOf(hd.getSoLuong() + Integer.valueOf(soLuong));
                            thanhTien = String.valueOf(Integer.valueOf(soLuongThayDoi) * hd.getGia());
                            maHDCTMoi = hd.getMaHDCT();
                            tongTien = 0;
                            upDatedHDCT();
                            addRowTableHD();
                            loatData(maHD);
                            check = true;
                            break;
                        }
                    }
                    if (check) {
                        break;
                    }
                }
                if (!check) {
                    insertHDCT();
                    addRowTableHD();
                    loatData(maHD);

                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        spHD = new javax.swing.JScrollPane();
        tblHoaDon = new com.MMT_Shop.swing.Table();
        jPanel6 = new javax.swing.JPanel();
        btnThem3 = new com.MMT_Shop.swing.button.Button();
        jPanel4 = new javax.swing.JPanel();
        spHDCT = new javax.swing.JScrollPane();
        tblHDCT = new com.MMT_Shop.swing.Table();
        btnXoaSP = new com.MMT_Shop.swing.button.Button();
        jPanel5 = new javax.swing.JPanel();
        spSPCT = new javax.swing.JScrollPane();
        tblSPCT = new com.MMT_Shop.swing.Table();
        txtTimKiem = new com.MMT_Shop.swing.textfield.TextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        cbxTenSP = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel12 = new javax.swing.JPanel();
        cbxHang = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel13 = new javax.swing.JPanel();
        cbxLoaiKhung = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel14 = new javax.swing.JPanel();
        cbxChatLieu = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel15 = new javax.swing.JPanel();
        cbxKichThuoc = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel16 = new javax.swing.JPanel();
        cbxMauSac = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        btnXoaSP1 = new com.MMT_Shop.swing.button.Button();
        tpHinhThuc = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtSDT = new com.MMT_Shop.swing.textfield.TextField();
        txtTenKH = new com.MMT_Shop.swing.textfield.TextField();
        btnChoKH = new com.MMT_Shop.swing.button.Button();
        txtMaHD = new com.MMT_Shop.swing.textfield.TextField();
        txtTenNV = new com.MMT_Shop.swing.textfield.TextField();
        txtNgayTao = new com.MMT_Shop.swing.textfield.TextField();
        txtTongTien = new com.MMT_Shop.swing.textfield.TextField();
        cbxPT = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        txtTienKhachDua = new com.MMT_Shop.swing.textfield.TextField();
        txtTienCk = new com.MMT_Shop.swing.textfield.TextField();
        txtTienTraLai = new com.MMT_Shop.swing.textfield.TextField();
        btnThem4 = new com.MMT_Shop.swing.button.Button();
        addThanhToan = new com.MMT_Shop.swing.button.Button();
        txtSoTienGiam = new com.MMT_Shop.swing.textfield.TextField();
        cbxVC = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txtSDT1 = new com.MMT_Shop.swing.textfield.TextField();
        txtTenKH1 = new com.MMT_Shop.swing.textfield.TextField();
        btnChoKH1 = new com.MMT_Shop.swing.button.Button();
        txtSDTNS = new com.MMT_Shop.swing.textfield.TextField();
        txtTenNS = new com.MMT_Shop.swing.textfield.TextField();
        btnChoNShip = new com.MMT_Shop.swing.button.Button();
        txtMaHD1 = new com.MMT_Shop.swing.textfield.TextField();
        txtTenNV1 = new com.MMT_Shop.swing.textfield.TextField();
        txtTongTien1 = new com.MMT_Shop.swing.textfield.TextField();
        txtThanhToan = new com.MMT_Shop.swing.textfield.TextField();
        cbxPT1 = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        txtTienKhachDua1 = new com.MMT_Shop.swing.textfield.TextField();
        txtTienCk1 = new com.MMT_Shop.swing.textfield.TextField();
        txtTienTraLai1 = new com.MMT_Shop.swing.textfield.TextField();
        btnThem5 = new com.MMT_Shop.swing.button.Button();
        addVanChuyen = new com.MMT_Shop.swing.button.Button();
        txtSoTienGiam1 = new com.MMT_Shop.swing.textfield.TextField();
        txtPhiShips = new com.MMT_Shop.swing.textfield.TextField();
        cbxVC1 = new com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion();
        txtDiachi = new com.MMT_Shop.swing.textfield.TextField();

        setPreferredSize(new java.awt.Dimension(1000, 650));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hóa đơn chờ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Ngày tạo  ", "NV tạo ", "Khách hàng", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        spHD.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(40);
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(40);
            tblHoaDon.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblHoaDon.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnThem3.setBackground(new java.awt.Color(172, 182, 229));
        btnThem3.setForeground(new java.awt.Color(245, 245, 245));
        btnThem3.setText("Tạo đơn hàng");
        btnThem3.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem3.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnThem3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(btnThem3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spHD, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spHD, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

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
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDCTMouseClicked(evt);
            }
        });
        spHDCT.setViewportView(tblHDCT);
        if (tblHDCT.getColumnModel().getColumnCount() > 0) {
            tblHDCT.getColumnModel().getColumn(1).setMinWidth(0);
            tblHDCT.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblHDCT.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        btnXoaSP.setBackground(new java.awt.Color(172, 182, 229));
        btnXoaSP.setForeground(new java.awt.Color(245, 245, 245));
        btnXoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Delete.png"))); // NOI18N
        btnXoaSP.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXoaSP.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(spHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnXoaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(spHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP chi tiết", "Tên SP", "Hãng ", "Loại khung", "Chất liệu", "Kích thước", "Màu sắc", "Giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSPCTMousePressed(evt);
            }
        });
        spSPCT.setViewportView(tblSPCT);

        txtTimKiem.setLabelText("Tìm kiếm sản phẩm");
        txtTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiemMouseClicked(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 5));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Tên sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel11.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel11.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel11.setLayout(new java.awt.BorderLayout());

        cbxTenSP.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxTenSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTenSPItemStateChanged(evt);
            }
        });
        jPanel11.add(cbxTenSP, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel11);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Hãng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel12.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel12.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel12.setLayout(new java.awt.BorderLayout());

        cbxHang.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxHangItemStateChanged(evt);
            }
        });
        jPanel12.add(cbxHang, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel12);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Loại khung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel13.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel13.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel13.setLayout(new java.awt.BorderLayout());

        cbxLoaiKhung.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxLoaiKhung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoaiKhungItemStateChanged(evt);
            }
        });
        jPanel13.add(cbxLoaiKhung, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel13);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Chất liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel14.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel14.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel14.setLayout(new java.awt.BorderLayout());

        cbxChatLieu.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxChatLieuItemStateChanged(evt);
            }
        });
        jPanel14.add(cbxChatLieu, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel14);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Kích thước", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel15.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel15.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        cbxKichThuoc.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxKichThuoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKichThuocItemStateChanged(evt);
            }
        });
        jPanel15.add(cbxKichThuoc, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel15);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Màu sắc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel16.setMinimumSize(new java.awt.Dimension(190, 55));
        jPanel16.setPreferredSize(new java.awt.Dimension(95, 50));
        jPanel16.setLayout(new java.awt.BorderLayout());

        cbxMauSac.setPreferredSize(new java.awt.Dimension(30, 30));
        cbxMauSac.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxMauSacItemStateChanged(evt);
            }
        });
        jPanel16.add(cbxMauSac, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel16);

        btnXoaSP1.setBackground(new java.awt.Color(172, 182, 229));
        btnXoaSP1.setForeground(new java.awt.Color(245, 245, 245));
        btnXoaSP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Qr Code.png"))); // NOI18N
        btnXoaSP1.setRippleColor(new java.awt.Color(255, 255, 255));
        btnXoaSP1.setShadowColor(new java.awt.Color(172, 182, 229));
        btnXoaSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSP1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(spSPCT)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spSPCT, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
        );

        tpHinhThuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin khách hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11))); // NOI18N

        txtSDT.setLabelText("Số điện thoại ");

        txtTenKH.setEditable(false);
        txtTenKH.setBackground(new java.awt.Color(255, 255, 255));
        txtTenKH.setLabelText("Tên khách hàng");

        btnChoKH.setBackground(new java.awt.Color(172, 182, 229));
        btnChoKH.setForeground(new java.awt.Color(245, 245, 245));
        btnChoKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Choose.png"))); // NOI18N
        btnChoKH.setRippleColor(new java.awt.Color(255, 255, 255));
        btnChoKH.setShadowColor(new java.awt.Color(172, 182, 229));
        btnChoKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnChoKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );

        txtMaHD.setEditable(false);
        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setLabelText("Mã hóa đơn");

        txtTenNV.setEditable(false);
        txtTenNV.setBackground(new java.awt.Color(255, 255, 255));
        txtTenNV.setLabelText("Tên nhân viên");

        txtNgayTao.setEditable(false);
        txtNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayTao.setLabelText("Ngày tạo ");

        txtTongTien.setEditable(false);
        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setLabelText("Tổng tiền");

        cbxPT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cả hai", "Tiền mặt ", "Chuyển khoản" }));
        cbxPT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPTItemStateChanged(evt);
            }
        });
        cbxPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPTActionPerformed(evt);
            }
        });

        txtTienKhachDua.setLabelText("Tiền khách đưa");
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachDuaKeyTyped(evt);
            }
        });

        txtTienCk.setLabelText("Tiền khách chuyển khoản ");
        txtTienCk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCkKeyReleased(evt);
            }
        });

        txtTienTraLai.setEditable(false);
        txtTienTraLai.setBackground(new java.awt.Color(255, 255, 255));
        txtTienTraLai.setLabelText("Tiền trả lại");

        btnThem4.setBackground(new java.awt.Color(172, 182, 229));
        btnThem4.setForeground(new java.awt.Color(245, 245, 245));
        btnThem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Clear Formatting.png"))); // NOI18N
        btnThem4.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem4.setShadowColor(new java.awt.Color(172, 182, 229));
        btnThem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem4ActionPerformed(evt);
            }
        });

        addThanhToan.setBackground(new java.awt.Color(116, 235, 213));
        addThanhToan.setForeground(new java.awt.Color(245, 245, 245));
        addThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Cash Register_2.png"))); // NOI18N
        addThanhToan.setText("Thanh toán");
        addThanhToan.setRippleColor(new java.awt.Color(255, 255, 255));
        addThanhToan.setShadowColor(new java.awt.Color(116, 235, 213));
        addThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addThanhToanActionPerformed(evt);
            }
        });

        txtSoTienGiam.setEditable(false);
        txtSoTienGiam.setBackground(new java.awt.Color(255, 255, 255));
        txtSoTienGiam.setLabelText("Số tiền giảm");

        cbxVC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxVCItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 328, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThem4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtTienCk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienKhachDua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxPT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(cbxVC, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
                            .addComponent(txtTenNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTienTraLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSoTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxVC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienCk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tpHinhThuc.addTab("Tại quầy", jPanel3);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Thông tin khách hàng và người ships", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 11))); // NOI18N

        txtSDT1.setLabelText("Số điện thoại ");

        txtTenKH1.setEditable(false);
        txtTenKH1.setBackground(new java.awt.Color(255, 255, 255));
        txtTenKH1.setLabelText("Tên khách hàng");

        btnChoKH1.setBackground(new java.awt.Color(172, 182, 229));
        btnChoKH1.setForeground(new java.awt.Color(245, 245, 245));
        btnChoKH1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Choose.png"))); // NOI18N
        btnChoKH1.setRippleColor(new java.awt.Color(255, 255, 255));
        btnChoKH1.setShadowColor(new java.awt.Color(172, 182, 229));
        btnChoKH1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoKH1ActionPerformed(evt);
            }
        });

        txtSDTNS.setLabelText("Số điện thoại ");

        txtTenNS.setEditable(false);
        txtTenNS.setBackground(new java.awt.Color(255, 255, 255));
        txtTenNS.setLabelText("Tên người ships");

        btnChoNShip.setBackground(new java.awt.Color(172, 182, 229));
        btnChoNShip.setForeground(new java.awt.Color(245, 245, 245));
        btnChoNShip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Choose.png"))); // NOI18N
        btnChoNShip.setRippleColor(new java.awt.Color(255, 255, 255));
        btnChoNShip.setShadowColor(new java.awt.Color(172, 182, 229));
        btnChoNShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoNShipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenKH1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(txtTenNS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSDT1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(txtSDTNS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChoKH1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChoNShip, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSDT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTenKH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnChoKH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTenNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSDTNS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnChoNShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        txtMaHD1.setEditable(false);
        txtMaHD1.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD1.setLabelText("Mã hóa đơn");

        txtTenNV1.setEditable(false);
        txtTenNV1.setBackground(new java.awt.Color(255, 255, 255));
        txtTenNV1.setLabelText("Tên nhân viên");

        txtTongTien1.setEditable(false);
        txtTongTien1.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien1.setLabelText("Tổng tiền");

        txtThanhToan.setEditable(false);
        txtThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        txtThanhToan.setLabelText("Thanh Toán ");

        cbxPT1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "cả hai", "Tiền mặt ", "Chuyển khoản" }));
        cbxPT1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPT1ItemStateChanged(evt);
            }
        });
        cbxPT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPT1ActionPerformed(evt);
            }
        });

        txtTienKhachDua1.setLabelText("Tiền khách đưa");
        txtTienKhachDua1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachDua1KeyTyped(evt);
            }
        });

        txtTienCk1.setEditable(false);
        txtTienCk1.setBackground(new java.awt.Color(255, 255, 255));
        txtTienCk1.setLabelText("Tiền khách chuyển khoản ");
        txtTienCk1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienCk1KeyReleased(evt);
            }
        });

        txtTienTraLai1.setEditable(false);
        txtTienTraLai1.setBackground(new java.awt.Color(255, 255, 255));
        txtTienTraLai1.setLabelText("Tiền trả lại");

        btnThem5.setBackground(new java.awt.Color(172, 182, 229));
        btnThem5.setForeground(new java.awt.Color(245, 245, 245));
        btnThem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Clear Formatting.png"))); // NOI18N
        btnThem5.setRippleColor(new java.awt.Color(255, 255, 255));
        btnThem5.setShadowColor(new java.awt.Color(172, 182, 229));

        addVanChuyen.setBackground(new java.awt.Color(116, 235, 213));
        addVanChuyen.setForeground(new java.awt.Color(245, 245, 245));
        addVanChuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/MMT_Shop/icon/Delivery Minibus.png"))); // NOI18N
        addVanChuyen.setRippleColor(new java.awt.Color(255, 255, 255));
        addVanChuyen.setShadowColor(new java.awt.Color(116, 235, 213));
        addVanChuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVanChuyenActionPerformed(evt);
            }
        });

        txtSoTienGiam1.setEditable(false);
        txtSoTienGiam1.setBackground(new java.awt.Color(255, 255, 255));
        txtSoTienGiam1.setLabelText("Số tiền giảm");

        txtPhiShips.setLabelText("Phí vân chuyển ");
        txtPhiShips.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhiShipsKeyTyped(evt);
            }
        });

        cbxVC1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxVC1ItemStateChanged(evt);
            }
        });

        txtDiachi.setLabelText("Địa chỉ");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTenNV1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                        .addComponent(txtMaHD1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cbxPT1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienKhachDua1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTienCk1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDiachi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel17Layout.createSequentialGroup()
                            .addComponent(cbxVC1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtSoTienGiam1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(txtPhiShips, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTongTien1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                            .addComponent(btnThem5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(addVanChuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(txtTienTraLai1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaHD1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTenNV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtTongTien1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoTienGiam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxVC1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtPhiShips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cbxPT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTienKhachDua1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtTienCk1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtTienTraLai1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addVanChuyen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tpHinhThuc.addTab("Đặt hàng", jPanel17);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tpHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(tpHinhThuc)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        tongTien = 0;
        tongTienBD = 0;
        clickHD();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem3ActionPerformed
        taoMa();
        addRowTableHD();
        if (tblHoaDon.getRowCount() <= 5) {
            insertHD();
        } else {
            JOptionPane.showMessageDialog(this, "số hóa đơn trờ đã đạt giới hạn");
        }
    }//GEN-LAST:event_btnThem3ActionPerformed

    private void tblHDCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMouseClicked
        int row = tblHDCT.getSelectedRow();
        maHDCTMoi = tblHDCT.getValueAt(row, 1).toString();
        soLuongDaMua = tblHDCT.getValueAt(row, 10).toString();
        maSPCT = tblHDCT.getValueAt(row, 2).toString();


    }//GEN-LAST:event_tblHDCTMouseClicked

    private void btnXoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSPActionPerformed
        xoaTheoSL();
        addRowTableHD();
    }//GEN-LAST:event_btnXoaSPActionPerformed

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        taoMa();
        int row = tblSPCT.getSelectedRow();
        maSPCT = tblSPCT.getValueAt(row, 1).toString();
        gia = tblSPCT.getValueAt(row, 8).toString();
    }//GEN-LAST:event_tblSPCTMouseClicked

    private void tblSPCTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMousePressed
        if (evt.getClickCount() == 2) {
            mua();
        }
    }//GEN-LAST:event_tblSPCTMousePressed

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
        addRowTableSPCT();
    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        DefaultTableModel ob = (DefaultTableModel) tblSPCT.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(ob);
        tblSPCT.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtTimKiem.getText()));
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cbxTenSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTenSPItemStateChanged
        timKiem = cbxTenSP.getSelectedItem().toString();
        timKiem();
    }//GEN-LAST:event_cbxTenSPItemStateChanged

    private void cbxHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxHangItemStateChanged
        timKiem = cbxHang.getSelectedItem().toString();
        timKiem();
    }//GEN-LAST:event_cbxHangItemStateChanged

    private void cbxLoaiKhungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoaiKhungItemStateChanged
        timKiem = cbxLoaiKhung.getSelectedItem().toString();
        timKiem();
    }//GEN-LAST:event_cbxLoaiKhungItemStateChanged

    private void cbxChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxChatLieuItemStateChanged
        timKiem = cbxChatLieu.getSelectedItem().toString();
        timKiem();
    }//GEN-LAST:event_cbxChatLieuItemStateChanged

    private void cbxKichThuocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKichThuocItemStateChanged
        timKiem = cbxKichThuoc.getSelectedItem().toString();
        timKiem();
    }//GEN-LAST:event_cbxKichThuocItemStateChanged

    private void cbxMauSacItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxMauSacItemStateChanged
        timKiem = cbxMauSac.getSelectedItem().toString();
        timKiem();
        timKiem = "%SP%";
    }//GEN-LAST:event_cbxMauSacItemStateChanged

    private void btnChoKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoKHActionPerformed
        Frame parent = null;
        view_Khach_Hang fh = new view_Khach_Hang(parent, true);
        fh.setVisible(true);
        loadKH();
    }//GEN-LAST:event_btnChoKHActionPerformed

    private void cbxPTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPTItemStateChanged

    }//GEN-LAST:event_cbxPTItemStateChanged

    private void cbxPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPTActionPerformed
        if (cbxPT.getSelectedIndex() == 0) {
            txtTienKhachDua.setEditable(true);
            txtTienCk.setEditable(false);
        } else if (cbxPT.getSelectedIndex() == 1) {
            txtTienKhachDua.setEditable(true);
            txtTienCk.setEditable(false);
        } else if (cbxPT.getSelectedIndex() == 2) {
            txtTienKhachDua.setEditable(false);
            txtTienCk.setEditable(true);
        }

    }//GEN-LAST:event_cbxPTActionPerformed

    private void txtTienKhachDuaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDuaKeyTyped
        if (cbxPT.getSelectedIndex() == 0) {
            String input = txtTienKhachDua.getText();
            if (input != null) {
                try {
                    try {
                        float tienMat = Float.valueOf(input);
                        float tienCK = tongTien - tienMat;
                        if (tienMat > tongTien) {
                            txtTienCk.setText(String.valueOf(0));
                        } else {
                            txtTienCk.setText(String.valueOf(tienCK));
                        }
                        if (Float.valueOf(txtTienCk.getText()) + tienMat > tongTien) {
                            float tienTra = (Float.valueOf(txtTienCk.getText()) + tienMat) - tongTien;
                            txtTienTraLai.setText(String.valueOf(tienTra));
                        } else {
                            txtTienTraLai.setText(String.valueOf(0));
                        }
                    } catch (NumberFormatException ex) {
                        System.out.print(ex);
                    }
                } catch (Exception e) {
                    System.out.print(e);
                }

            }
        } else if (cbxPT.getSelectedIndex() == 1) {
            String input = txtTienKhachDua.getText();
            if (input != null) {
                try {
                    float tienMat = Float.valueOf(input);
                    txtTienCk.setText("0");
                    if (Float.valueOf(txtTienCk.getText()) + tienMat > tongTien) {
                        float tienTra = (Float.valueOf(txtTienCk.getText()) + tienMat) - tongTien;
                        txtTienTraLai.setText(String.valueOf(tienTra));
                    } else {
                        txtTienTraLai.setText(String.valueOf(0));
                    }
                } catch (NumberFormatException ex) {
                    System.out.print(ex);
                }
            }
        }


    }//GEN-LAST:event_txtTienKhachDuaKeyTyped

    private void addThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addThanhToanActionPerformed
        UPdateHD();
        taoMa();
        clear();
    }//GEN-LAST:event_addThanhToanActionPerformed

    private void cbxVCItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxVCItemStateChanged
        try {
            maVC = cbxVC.getSelectedItem().toString();
        } catch (Exception e) {
        }
        giamGia();
    }//GEN-LAST:event_cbxVCItemStateChanged

    private void btnChoKH1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoKH1ActionPerformed
        Frame parent = null;
        view_Khach_Hang fh = new view_Khach_Hang(parent, true);
        fh.setVisible(true);
        loadKH();
    }//GEN-LAST:event_btnChoKH1ActionPerformed

    private void btnChoNShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoNShipActionPerformed
        Frame parent = null;
        view_Nhan_Vien fh = new view_Nhan_Vien(parent, true);
        fh.setVisible(true);
        loadNV();
    }//GEN-LAST:event_btnChoNShipActionPerformed

    private void cbxPT1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPT1ItemStateChanged

    }//GEN-LAST:event_cbxPT1ItemStateChanged

    private void txtTienKhachDua1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachDua1KeyTyped
        if (cbxPT1.getSelectedIndex() == 0) {
            String input = txtTienKhachDua1.getText();
            if (input != null) {
                try {
                    float tienMat = Float.valueOf(input);
                    float tienCK = Float.valueOf(txtThanhToan.getText()) - tienMat;
                    if (tienMat > Float.valueOf(txtThanhToan.getText())) {
                        txtTienCk1.setText(String.valueOf(0));
                    } else {
                        txtTienCk1.setText(String.valueOf(tienCK));
                    }
                    if (Float.valueOf(txtTienCk1.getText()) + tienMat > Float.valueOf(txtThanhToan.getText())) {
                        float tienTra = (Float.valueOf(txtTienCk1.getText()) + tienMat) - Float.valueOf(txtThanhToan.getText());
                        txtTienTraLai1.setText(String.valueOf(tienTra));
                    } else {
                        txtTienTraLai1.setText(String.valueOf(0));
                    }
                } catch (NumberFormatException ex) {
                    System.out.print(ex);
                }
            }
        } else if (cbxPT1.getSelectedIndex() == 1) {
            String input = txtTienKhachDua1.getText();
            if (input != null) {
                try {
                    float tienMat = Float.valueOf(input);
                    txtTienCk1.setText("0");
                    if (Float.valueOf(txtTienCk1.getText()) + tienMat > Float.valueOf(txtThanhToan.getText())) {
                        float tienTra = (Float.valueOf(txtTienCk1.getText()) + tienMat) - Float.valueOf(txtThanhToan.getText());
                        txtTienTraLai1.setText(String.valueOf(tienTra));
                    } else {
                        txtTienTraLai1.setText(String.valueOf(0));
                    }
                } catch (NumberFormatException ex) {
                    System.out.print(ex);
                }
            }
        }
    }//GEN-LAST:event_txtTienKhachDua1KeyTyped

    private void addVanChuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVanChuyenActionPerformed
        vanChuyenHD();
        taoMa();
        clear();
    }//GEN-LAST:event_addVanChuyenActionPerformed

    private void txtPhiShipsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhiShipsKeyTyped
        String input = txtPhiShips.getText();
        if (input != null) {
            try {
                float thanhToan = Float.valueOf(tongTienBD) + Float.valueOf(input) - Float.valueOf(txtSoTienGiam1.getText());
                txtThanhToan.setText(String.valueOf(thanhToan));
            } catch (NumberFormatException ex) {
                System.out.print(ex);
            }
        }
    }//GEN-LAST:event_txtPhiShipsKeyTyped

    private void txtTienCkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCkKeyReleased
        if (cbxPT.getSelectedIndex() == 2) {
            String input = txtTienCk.getText();
            if (input != null) {
                try {
                    float tienMat = Float.valueOf(input);
                    txtTienKhachDua.setText("0");
                    if (Float.valueOf(txtTienCk.getText()) + tienMat > tongTien) {
                        float tienTra = (Float.valueOf(txtTienCk.getText()) + tienMat) - tongTien;
                        txtTienTraLai.setText(String.valueOf(tienTra));
                    } else {
                        txtTienTraLai.setText(String.valueOf(0));
                    }
                } catch (NumberFormatException ex) {
                    System.out.print(ex);
                }
            }
        }
    }//GEN-LAST:event_txtTienCkKeyReleased

    private void cbxPT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPT1ActionPerformed
        if (cbxPT1.getSelectedIndex() == 0) {
            txtTienKhachDua1.setEditable(true);
            txtTienCk1.setEditable(false);
        } else if (cbxPT1.getSelectedIndex() == 1) {
            txtTienKhachDua1.setEditable(true);
            txtTienCk1.setEditable(false);
        } else if (cbxPT1.getSelectedIndex() == 2) {
            txtTienKhachDua1.setEditable(false);
            txtTienCk1.setEditable(true);
        }
    }//GEN-LAST:event_cbxPT1ActionPerformed

    private void txtTienCk1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienCk1KeyReleased
        if (cbxPT1.getSelectedIndex() == 2) {
            String input = txtTienCk1.getText();
            if (input != null) {
                try {
                    float tienMat = Float.valueOf(input);
                    txtTienKhachDua1.setText("0");
                    if (Float.valueOf(txtTienCk1.getText()) + tienMat > Float.valueOf(txtThanhToan.getText())) {
                        float tienTra = (Float.valueOf(txtTienCk1.getText()) + tienMat) - Float.valueOf(txtThanhToan.getText());
                        txtTienTraLai1.setText(String.valueOf(tienTra));
                    } else {
                        txtTienTraLai1.setText(String.valueOf(0));
                    }
                } catch (NumberFormatException ex) {
                    System.out.print(ex);
                }
            }
        }
    }//GEN-LAST:event_txtTienCk1KeyReleased

    private void cbxVC1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxVC1ItemStateChanged
        try {
            maVC = cbxVC.getSelectedItem().toString();
        } catch (Exception e) {
        }
        giamGia();
    }//GEN-LAST:event_cbxVC1ItemStateChanged

    private void btnThem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem4ActionPerformed
        clear();
    }//GEN-LAST:event_btnThem4ActionPerformed

    private void btnXoaSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSP1ActionPerformed
        Frame parent = null;
        FormQuetQRBH fh = new FormQuetQRBH(parent, true);
        fh.setVisible(true);
        if(maSPCT==null){
        return;
        }
        taoMa();
        String input = JOptionPane.showInputDialog(this, "Nhập số lượng", "thồng báo", 3);
        if (input != null) {
            try {
                int newQuantity = Integer.parseInt(input);
                soLuong = input;
            } catch (NumberFormatException ex) {
                // JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                System.out.print(ex);
            }
            SanPhamCT data = dataSP.SelectSanPhamCTTheoMaSPCT(maSPCT);
            gia = String.valueOf(data.getGia());
            int soLuongBD = data.getSoLuong();
            soLuongTon = String.valueOf(soLuongBD - Integer.valueOf(soLuong));
            if (Integer.valueOf(soLuong) < 0 || Integer.valueOf(soLuong) > Integer.valueOf(soLuongBD)) {
                showMessageBox("Số lượng đã vượt quá");
            } else {
                insertHDCT();
                addRowTableHD();
                loatData(maHD);
            }
        }
    }//GEN-LAST:event_btnXoaSP1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.MMT_Shop.swing.button.Button addThanhToan;
    private com.MMT_Shop.swing.button.Button addVanChuyen;
    private com.MMT_Shop.swing.button.Button btnChoKH;
    private com.MMT_Shop.swing.button.Button btnChoKH1;
    private com.MMT_Shop.swing.button.Button btnChoNShip;
    private com.MMT_Shop.swing.button.Button btnThem3;
    private com.MMT_Shop.swing.button.Button btnThem4;
    private com.MMT_Shop.swing.button.Button btnThem5;
    private com.MMT_Shop.swing.button.Button btnXoaSP;
    private com.MMT_Shop.swing.button.Button btnXoaSP1;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxChatLieu;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxHang;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxKichThuoc;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxLoaiKhung;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxMauSac;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxPT;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxPT1;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxTenSP;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxVC;
    private com.MMT_Shop.swing.combo_suggestion.ComboBoxSuggestion cbxVC1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane spHD;
    private javax.swing.JScrollPane spHDCT;
    private javax.swing.JScrollPane spSPCT;
    private com.MMT_Shop.swing.Table tblHDCT;
    private com.MMT_Shop.swing.Table tblHoaDon;
    private com.MMT_Shop.swing.Table tblSPCT;
    private javax.swing.JTabbedPane tpHinhThuc;
    private com.MMT_Shop.swing.textfield.TextField txtDiachi;
    private com.MMT_Shop.swing.textfield.TextField txtMaHD;
    private com.MMT_Shop.swing.textfield.TextField txtMaHD1;
    private com.MMT_Shop.swing.textfield.TextField txtNgayTao;
    private com.MMT_Shop.swing.textfield.TextField txtPhiShips;
    private com.MMT_Shop.swing.textfield.TextField txtSDT;
    private com.MMT_Shop.swing.textfield.TextField txtSDT1;
    private com.MMT_Shop.swing.textfield.TextField txtSDTNS;
    private com.MMT_Shop.swing.textfield.TextField txtSoTienGiam;
    private com.MMT_Shop.swing.textfield.TextField txtSoTienGiam1;
    private com.MMT_Shop.swing.textfield.TextField txtTenKH;
    private com.MMT_Shop.swing.textfield.TextField txtTenKH1;
    private com.MMT_Shop.swing.textfield.TextField txtTenNS;
    private com.MMT_Shop.swing.textfield.TextField txtTenNV;
    private com.MMT_Shop.swing.textfield.TextField txtTenNV1;
    private com.MMT_Shop.swing.textfield.TextField txtThanhToan;
    private com.MMT_Shop.swing.textfield.TextField txtTienCk;
    private com.MMT_Shop.swing.textfield.TextField txtTienCk1;
    private com.MMT_Shop.swing.textfield.TextField txtTienKhachDua;
    private com.MMT_Shop.swing.textfield.TextField txtTienKhachDua1;
    private com.MMT_Shop.swing.textfield.TextField txtTienTraLai;
    private com.MMT_Shop.swing.textfield.TextField txtTienTraLai1;
    private com.MMT_Shop.swing.textfield.TextField txtTimKiem;
    private com.MMT_Shop.swing.textfield.TextField txtTongTien;
    private com.MMT_Shop.swing.textfield.TextField txtTongTien1;
    // End of variables declaration//GEN-END:variables

}
