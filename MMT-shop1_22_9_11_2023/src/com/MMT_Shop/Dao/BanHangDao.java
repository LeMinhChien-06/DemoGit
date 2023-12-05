package com.MMT_Shop.Dao;

import com.MMT_Shop.Chien.HoaDon;
import com.MMT_Shop.Chien.HoaDonChiTiet;
import com.MMT_Shop.EnTiTy.HinhThucThanhToan;
import com.MMT_Shop.Lam.Voucher;
import com.MMT_Shop.utility.DBcontext;
import java.util.ArrayList;
import java.sql.*;

public class BanHangDao {

    public ArrayList<HoaDon> selectALLHD() {
        ArrayList<HoaDon> data = new ArrayList<>();
        String sql = "SELECT HoaDon.id as 'idHD',HoaDon.hinhThuc as 'hinhThuc',  HoaDon.ma as 'maHD', NhanVien.ma as 'maNV', NhanVien.Ten as 'tenNV',  \n"
                + " KhachHang.ma as 'maKH',tenKhachHang,Voucher.ma as 'maVoucher',HoaDon.trangThai as 'trangThaiHD', \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi as 'diaChi',HoaDon.soDienThoai as 'SDT',tongTien, \n"
                + "SUM(HoaDonChiTiet.soLuong) as 'soLuong',\n"
                + "HoaDon.Created_At as 'Created_At',HoaDon.Updated_At as 'Updated_At', HoaDon.Created_By as 'Created_By',  \n"
                + "HoaDon.Updated_By as 'Updated_By', HoaDon.Deleted as 'Deleted' FROM HoaDon  \n"
                + "left join NhanVien on HoaDon.NhanVienID = NhanVien.id   \n"
                + "left join Voucher on HoaDon.VoucherID = Voucher.id   \n"
                + "left join KhachHang on HoaDon.khacHangID = KhachHang.id  \n"
                + "left join HoaDonChiTiet on HoaDon.id = HoaDonChiTiet.hoaDonID\n"
                + "where HoaDon.trangThai like N'Chưa thanh toán'\n"
                + "group by  HoaDon.id ,  HoaDon.ma , NhanVien.ma , NhanVien.Ten,  \n"
                + "KhachHang.ma ,tenKhachHang,Voucher.ma ,HoaDon.trangThai , \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi ,HoaDon.soDienThoai,tongTien, HoaDon.Created_At \n"
                + ",HoaDon.Updated_At, HoaDon.Created_By ,  HoaDon.Updated_By , HoaDon.Deleted,HoaDon.hinhThuc";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("idHD"));
                hd.setMaHD(rs.getString("maHD"));
                hd.setMaNV(rs.getString("maNV"));
                hd.setTenNV(rs.getString("tenNV"));
                hd.setMaKH(rs.getString("maKH"));
                hd.setTenKH(rs.getString("tenKhachHang"));
                hd.setVoucher(rs.getString("maVoucher"));
                hd.setTrangThai(rs.getString("trangThaiHD"));
                hd.setNgayGiaoDich(rs.getString("ngayGiaoDich"));
                hd.setNgayHoanThanh(rs.getString("ngayHoanThanh"));
                hd.setDiaChi(rs.getString("diaChi"));
                hd.setSdt(rs.getString("SDT"));
                hd.setSoLuongSP(rs.getInt("soLuong"));
                hd.setTongTien(rs.getFloat("tongTien"));
                hd.setHinhThucMua(rs.getString("hinhThuc"));
                hd.setCreated_At(rs.getString("Created_At"));
                hd.setUpdated_At(rs.getString("Updated_At"));
                hd.setCreated_By(rs.getString("Created_By"));
                hd.setUpdated_By(rs.getString("Updated_By"));
                hd.setDeleted(rs.getString("Deleted"));
                data.add(hd);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }

    public ArrayList<HoaDon> HDTheoMa(String maHD) {
        ArrayList<HoaDon> data = new ArrayList<>();
        String sql = "SELECT HoaDon.id as 'idHD',  HoaDon.ma as 'maHD', NhanVien.ma as 'maNV', NhanVien.Ten as 'tenNV',  \n"
                + "KhachHang.ma as 'maKH',tenKhachHang,Voucher.ma as 'maVoucher',HoaDon.trangThai as 'trangThaiHD', \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi as 'diaChi',HoaDon.soDienThoai as 'SDT',tongTien, \n"
                + "SUM(HoaDonChiTiet.soLuong) as 'soLuong',CTHTThanhToan.hinhThucThanhToanID as 'hinhThucThanhToan',\n"
                + "CTHTThanhToan.soTienMat as 'tienMat', CTHTThanhToan.soTienChuyenKhoan as 'chuyenKhoan', CTHTThanhToan.soTienTraLai as 'traLai',\n"
                + "HoaDon.Created_At as 'Created_At',HoaDon.Updated_At as 'Updated_At', HoaDon.Created_By as 'Created_By',  \n"
                + "HoaDon.Updated_By as 'Updated_By', HoaDon.Deleted as 'Deleted' FROM HoaDon  \n"
                + "left join NhanVien on HoaDon.NhanVienID = NhanVien.id\n"
                + "left join Voucher on HoaDon.VoucherID = Voucher.id\n"
                + "left join KhachHang on HoaDon.khacHangID = KhachHang.id  \n"
                + "left join HoaDonChiTiet on HoaDon.id = HoaDonChiTiet.hoaDonID\n"
                + "left join CTHTThanhToan on CTHTThanhToan.hoaDonID = HoaDon.id\n"
                + "where HoaDon.ma like ?\n"
                + "group by  HoaDon.id ,  HoaDon.ma , NhanVien.ma , NhanVien.Ten,  \n"
                + "KhachHang.ma ,tenKhachHang,Voucher.ma ,HoaDon.trangThai , \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi ,HoaDon.soDienThoai,tongTien, HoaDon.Created_At,\n"
                + "HoaDon.Updated_At, HoaDon.Created_By ,  HoaDon.Updated_By , HoaDon.Deleted, \n"
                + "CTHTThanhToan.hinhThucThanhToanID ,CTHTThanhToan.soTienMat , CTHTThanhToan.soTienChuyenKhoan ,\n"
                + "CTHTThanhToan.soTienTraLai ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("idHD"));
                hd.setMaHD(rs.getString("maHD"));
                hd.setMaNV(rs.getString("maNV"));
                hd.setTenNV(rs.getString("tenNV"));
                hd.setMaKH(rs.getString("maKH"));
                hd.setTenKH(rs.getString("tenKhachHang"));
                hd.setVoucher(rs.getString("maVoucher"));
                hd.setTrangThai(rs.getString("trangThaiHD"));
                hd.setNgayGiaoDich(rs.getString("ngayGiaoDich"));
                hd.setNgayHoanThanh(rs.getString("ngayHoanThanh"));
                hd.setDiaChi(rs.getString("diaChi"));
                hd.setSdt(rs.getString("SDT"));
                hd.setSoLuongSP(rs.getInt("soLuong"));
                hd.setTongTien(rs.getFloat("tongTien"));
                hd.setTienMat(rs.getFloat("tienMat"));
                hd.setTienMat(rs.getFloat("tienMat"));
                hd.setHtThanhToan(rs.getInt("hinhThucThanhToan"));
                hd.setTraLai(rs.getFloat("traLai"));
                hd.setCreated_At(rs.getString("Created_At"));
                hd.setUpdated_At(rs.getString("Updated_At"));
                hd.setCreated_By(rs.getString("Created_By"));
                hd.setUpdated_By(rs.getString("Updated_By"));
                hd.setDeleted(rs.getString("Deleted"));
                data.add(hd);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }

    public ArrayList<HoaDonChiTiet> selectALLHDCT(String maHD) {
        ArrayList<HoaDonChiTiet> data = new ArrayList<>();
        String sql = "select HoaDonChiTiet.id as 'id', HoaDonChiTiet.ma as 'maHDCT',CTSP.maCTSP as 'maCTSP',  \n"
                + "SanPham.ten as'tensp',ThuongHieu.ten as 'hang',MauSac.ten as 'mau', kichThuoc.ten as 'kichThuoc',\n"
                + "KhungGiuong.ten as 'loaiKhung', ChatLieu.ten as 'ChatLieu',\n"
                + "HoaDonChiTiet.soLuong as 'soluong', HoaDonChiTiet.gia as 'gia',HoaDonChiTiet.thanhTien as 'thanhTien'  \n"
                + "from HoaDonChiTiet   \n"
                + "left join HoaDon on HoaDon.id= HoaDonChiTiet.hoaDonID  \n"
                + "left join CTSP on CTSP.id = HoaDonChiTiet.chiTietSanPhamID   \n"
                + "left join SanPham on sanPham.id = CTSP.sanPhamID  \n"
                + "left join ThuongHieu on ThuongHieu.id = CTSP.thuongHieuID   \n"
                + "left join MauSac on MauSac.id = CTSP.mauSacID  \n"
                + "left join KichThuoc on KichThuoc.id = CTSP.kichThuocID\n"
                + "left join KhungGiuong on KhungGiuong.id = CTSP.loaiKhungID\n"
                + "left join ChatLieu on ChatLieu.id = CTSP.chatLieuID\n"
                + "where HoaDon.ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setTenSP(rs.getString("tensp"));
                hdct.setMaHDCT(rs.getString("maHDCT"));
                hdct.setMaCTSP(rs.getString("maCTSP"));
                hdct.setHang(rs.getString("hang"));
                hdct.setMauSac(rs.getString("mau"));
                hdct.setSoLuong(rs.getInt("soluong"));
                hdct.setGia(rs.getFloat("gia"));
                hdct.setThanhTien(rs.getFloat("thanhTien"));
                hdct.setKichThuoc(rs.getString("kichThuoc"));
                hdct.setChatLieu(rs.getString("ChatLieu"));
                hdct.setLoaiKhung(rs.getString("loaiKhung"));
                data.add(hdct);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }

    public Integer InsertHDCT(HoaDonChiTiet h) {
        Integer row = null;
        String Insert_sql = "insert into HoaDonChiTiet(hoaDonID,chiTietSanPhamID,ma,gia,soLuong,thanhTien,Created_At,Updated_At,Created_By,Updated_By,Deleted)\n"
                + "values ((select id from HoaDon where ma like ?),\n"
                + "(select id from CTSP where maCTSP like ?),\n"
                + "?,?,?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaHD());
            ps.setString(2, h.getMaCTSP());
            ps.setString(3, h.getMaHDCT());
            ps.setFloat(4, h.getGia());
            ps.setInt(5, h.getSoLuong());
            ps.setFloat(6, h.getThanhTien());
            ps.setString(7, h.getCreated_At());
            ps.setString(8, h.getUpdated_At());
            ps.setString(9, h.getCreated_By());
            ps.setString(10, h.getUpdated_By());
            ps.setString(11, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer updateHDCT(HoaDonChiTiet h) {
        Integer row = null;
        String Insert_sql = "update HoaDonChiTiet set soLuong= ?, thanhTien  = ? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setInt(1, h.getSoLuong());
            ps.setFloat(2, h.getThanhTien());
            ps.setString(3, h.getMaHDCT());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer deletedHDCT(HoaDonChiTiet h) {
        Integer row = null;
        String Insert_sql = "delete HoaDonChiTiet where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaHDCT());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer deletedHDCT1(String ma) {
        Integer row = null;
        String Insert_sql = "delete HoaDonChiTiet where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, ma);
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer deletedHD() {
        Integer row = null;
        String Insert_sql = "delete HoaDon where trangThai like N'Chưa thanh toán'";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer InsertHD(HoaDon h) {
        Integer row = null;
        String Insert_sql = "insert into HoaDon (ma,nhanVienID,tenKhachHang,trangThai,Created_At,Updated_At,Created_By,Updated_By,Deleted,tongTien,hinhThuc,diaChi)\n"
                + "values (?,(select id from NhanVien where ma like ?),?,?,?,?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaHD());
            ps.setString(2, h.getMaNV());
            ps.setString(3, h.getTenKH());
            ps.setString(4, h.getTrangThai());
            ps.setString(5, h.getCreated_At());
            ps.setString(6, h.getUpdated_At());
            ps.setString(7, h.getCreated_By());
            ps.setString(8, h.getUpdated_By());
            ps.setString(9, h.getDeleted());
            ps.setFloat(10, h.getTongTien());
            ps.setString(11, h.getHinhThucMua());
            ps.setString(12, h.getDiaChi());

            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public ArrayList<Voucher> vcTheoMa(String mavc) {
        ArrayList<Voucher> list = new ArrayList<>();
        String sql = "select *from Voucher where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, mavc);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Voucher t = new Voucher();
                t.setCode(rs.getString("ma"));
                t.setTen(rs.getString("ten"));
                t.setStartDate(rs.getDate("ngayBatDau").toLocalDate());
                t.setEndDate(rs.getDate("ngayKetThuc").toLocalDate());
                t.setSoLuong(rs.getInt("SoLuong"));
                t.setStatus(rs.getString("trangThai"));
                t.setPhanTramGiam(rs.getDouble("phanTramGiam"));
                t.setGiaTriHoaDonToiThieu(rs.getDouble("giaTriHoaDonToiThieu"));
                t.setSoTienGiamToiDa(rs.getDouble("soTienGiamToiDa"));
                t.setCreated_At(rs.getString("created_At"));
                t.setUpdated_At(rs.getString("updated_At"));
                t.setCreated_By(rs.getString("created_By"));
                t.setUpdated_By(rs.getString("updated_By"));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return list;
    }

    public Integer upDateHD(HoaDon h) {
        Integer row = null;
        String Insert_sql = "update HoaDon set khacHangID = (select id  from KhachHang where ma like ?),\n"
                + "VoucherID = (select id from Voucher where ma like ?),soDienThoai = ?,\n"
                + "ngayHoanThanh = ?,ngayGiaoDich = ?,tongTien = ?,trangThai =?,tenKhachHang =? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaKH());
            ps.setString(2, h.getVoucher());
            ps.setString(3, h.getSdt());
            ps.setString(4, h.getNgayHoanThanh());
            ps.setString(5, h.getNgayGiaoDich());
            ps.setFloat(6, h.getTongTien());
            ps.setString(7, h.getTrangThai());
            ps.setString(8, h.getTenKH());
            ps.setString(9, h.getMaHD());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer upDateHDDatHang(HoaDon h) {
        Integer row = null;
        String Insert_sql = "update HoaDon set khacHangID = (select id from KhachHang where ma like ?),\n"
                + "VoucherID =(select id from Voucher where ma like ?),\n"
                + "soDienThoai =?,tenKhachHang =?,SDTNguoiShip =?,\n"
                + "tenNguoiShip =?,tongTien =?,trangThai =?,diaChi =? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaKH());
            ps.setString(2, h.getVoucher());
            ps.setString(3, h.getSdt());
            ps.setString(4, h.getTenKH());
            ps.setString(5, h.getTenNGuoiShip());
            ps.setString(6, h.getSdtNguoiShip());
            ps.setFloat(7, h.getTongTien());
            ps.setString(8, h.getTrangThai());
            ps.setString(9, h.getDiaChi());
            ps.setString(10, h.getMaHD());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer InsertHT(HinhThucThanhToan h) {
        Integer row = null;
        String Insert_sql = "insert into CTHTThanhToan(hoaDonID,hinhThucThanhToanID,ma,soTienMat,soTienChuyenKhoan,\n"
                + "soTienTraLai,Created_At,Updated_At,Created_By,Updated_By,Deleted)\n"
                + "values ((select id From HoaDon where ma like ?),?,?,?,?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMaHD());
            ps.setInt(2, h.getIdHinhthuc());
            ps.setString(3, h.getMaHT());
            ps.setFloat(4, h.getTienMat());
            ps.setFloat(5, h.getTienCK());
            ps.setFloat(6, h.getTienTL());
            ps.setString(7, h.getCreated_At());
            ps.setString(8, h.getUpdated_At());
            ps.setString(9, h.getCreated_By());
            ps.setString(10, h.getUpdated_By());
            ps.setString(11, h.getDeleted());

            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

}
