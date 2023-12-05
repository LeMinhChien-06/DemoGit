package com.MMT_Shop.Chien;

import com.MMT_Shop.utility.DBcontext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HoaDonService {

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
                + "where HoaDon.trangThai not like N'Chưa thanh toán'\n"
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
        String sql = "SELECT HoaDon.id as 'idHD',  HoaDon.ma as 'maHD', NhanVien.ma as 'maNV', NhanVien.Ten as 'tenNV',   \n"
                + "                 KhachHang.ma as 'maKH',tenKhachHang,Voucher.ma as 'maVoucher',HoaDon.trangThai as 'trangThaiHD',  \n"
                + "                 ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi as 'diaChi',HoaDon.soDienThoai as 'SDT',tongTien,  \n"
                + "                 SUM(HoaDonChiTiet.soLuong) as 'soLuong',CTHTThanhToan.hinhThucThanhToanID as 'hinhThucThanhToan', \n"
                + "                 CTHTThanhToan.soTienMat as 'tienMat', CTHTThanhToan.soTienChuyenKhoan as 'chuyenKhoan', CTHTThanhToan.soTienTraLai as 'traLai', \n"
                + "				tenNguoiShip,SDTNguoiShip,\n"
                + "                 HoaDon.Created_At as 'Created_At',HoaDon.Updated_At as 'Updated_At', HoaDon.Created_By as 'Created_By',   \n"
                + "                 HoaDon.Updated_By as 'Updated_By', HoaDon.Deleted as 'Deleted' FROM HoaDon   \n"
                + "                 left join NhanVien on HoaDon.NhanVienID = NhanVien.id \n"
                + "                 left join Voucher on HoaDon.VoucherID = Voucher.id \n"
                + "                 left join KhachHang on HoaDon.khacHangID = KhachHang.id   \n"
                + "                 left join HoaDonChiTiet on HoaDon.id = HoaDonChiTiet.hoaDonID \n"
                + "                 left join CTHTThanhToan on CTHTThanhToan.hoaDonID = HoaDon.id \n"
                + "				 where HoaDon.ma like ?\n"
                + "                 group by  HoaDon.id ,  HoaDon.ma , NhanVien.ma , NhanVien.Ten,   \n"
                + "                 KhachHang.ma ,tenKhachHang,Voucher.ma ,HoaDon.trangThai ,  \n"
                + "                 ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi ,HoaDon.soDienThoai,tongTien, HoaDon.Created_At, \n"
                + "                 HoaDon.Updated_At, HoaDon.Created_By ,  HoaDon.Updated_By , HoaDon.Deleted,  \n"
                + "                 CTHTThanhToan.hinhThucThanhToanID ,CTHTThanhToan.soTienMat , CTHTThanhToan.soTienChuyenKhoan , \n"
                + "                 CTHTThanhToan.soTienTraLai,tenNguoiShip,SDTNguoiShip";
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
                hd.setSdtNguoiShip(rs.getString("SDTNguoiShip"));
                hd.setTenNGuoiShip(rs.getString("tenNguoiShip"));
                hd.setSoLuongSP(rs.getInt("soLuong"));
                hd.setTongTien(rs.getFloat("tongTien"));
                //hd.setHinhThucMua(rs.getString("hinhThuc"));
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

    public ArrayList<HoaDon> timKiemTheoNgay(String ngayBD, String ngayKT) {
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
                + "where HoaDon.Created_At BETWEEN ? and ? and  HoaDon.trangThai not like N'Chưa thanh toán' \n"
                + "group by  HoaDon.id ,  HoaDon.ma , NhanVien.ma , NhanVien.Ten,  \n"
                + "KhachHang.ma ,tenKhachHang,Voucher.ma ,HoaDon.trangThai , \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi ,HoaDon.soDienThoai,tongTien, HoaDon.Created_At,\n"
                + "HoaDon.Updated_At, HoaDon.Created_By ,  HoaDon.Updated_By , HoaDon.Deleted, \n"
                + "CTHTThanhToan.hinhThucThanhToanID ,CTHTThanhToan.soTienMat , CTHTThanhToan.soTienChuyenKhoan ,\n"
                + "CTHTThanhToan.soTienTraLai ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, ngayBD);
            ps.setString(2, ngayKT);
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

    public ArrayList<HoaDon> timKiemTheoMinMax(String min, String max) {
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
                + "where tongTien BETWEEN ? and ? and  HoaDon.trangThai not like N'Chưa thanh toán' \n"
                + "group by  HoaDon.id ,  HoaDon.ma , NhanVien.ma , NhanVien.Ten,  \n"
                + "KhachHang.ma ,tenKhachHang,Voucher.ma ,HoaDon.trangThai , \n"
                + "ngayGiaoDich,ngayHoanThanh,HoaDon.diaChi ,HoaDon.soDienThoai,tongTien, HoaDon.Created_At,\n"
                + "HoaDon.Updated_At, HoaDon.Created_By ,  HoaDon.Updated_By , HoaDon.Deleted, \n"
                + "CTHTThanhToan.hinhThucThanhToanID ,CTHTThanhToan.soTienMat , CTHTThanhToan.soTienChuyenKhoan ,\n"
                + "CTHTThanhToan.soTienTraLai ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, min);
            ps.setString(2, max);
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

    public Integer upDateHD(String ma, String trangThai) {
        Integer row = null;
        String Insert_sql = "update HoaDon set trangThai = ? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, trangThai);
            ps.setString(2, ma);
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

}
