package com.MMT_Shop.service;

import com.MMT_Shop.model.HoaDon;
import com.MMT_Shop.model.HoaDonChiTiet;
import com.MMT_Shop.respository.DBConnect;
import java.sql.*;
import java.util.ArrayList;

public class HoaDonService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public ArrayList<HoaDon> getAllHD() {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        sql = "SELECT ID, MaHoaDon, MaNV, TenKH, SDT, TongTien, NgayTao, TrangThai FROM tblHoaDon";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setStt(rs.getInt("ID"));
                hd.setMaHD(rs.getString("MaHoaDon"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setTenKH(rs.getString("TenKH"));
                hd.setSdt(rs.getString("SDT"));
                hd.setTongTien(rs.getString("TongTien"));
                hd.setNgayTao(rs.getString("NgayTao"));
                hd.setTrangThai(rs.getString("TrangThai"));

                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public ArrayList<HoaDon> timKiem(String ID) {
        ArrayList<HoaDon> listTimKiem = new ArrayList<>();
        sql = "SELECT * FROM tblHoaDon WHERE ID = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHoaDon"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setTenKH(rs.getString("TenKH"));
                hd.setSdt(rs.getString("SDT"));
                hd.setTongTien(rs.getString("TongTien"));
                hd.setNgayTao(rs.getString("NgayTao"));
                hd.setTrangThai(rs.getString("TrangThai"));
                hd.setHinhThucTT(rs.getString("HinhThucTT"));
                hd.setDiaChi(rs.getString("DiaChi"));
                hd.setTenNV(rs.getString("TenNV"));
                hd.setNgayTT(rs.getString("NgayThanhToan"));
                hd.setMaGiaoDich(rs.getString("MaGiaoDich"));
                listTimKiem.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTimKiem;
    }

    public ArrayList<HoaDon> timKiemHoaDon(String maHD, String sdt, String tenKH) {
        ArrayList<HoaDon> listTKHD = new ArrayList<>();
        sql = "SELECT * FROM tblHoaDon WHERE MaHoaDon = ? OR SDT = ? OR TenKH = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, maHD);
            ps.setString(2, sdt);
            ps.setString(3, tenKH);
            rs = ps.executeQuery();
            if (rs.next()) {
                listTKHD.add(new HoaDon(rs.getInt("ID"), rs.getString("MaHoaDon"), rs.getString("MaNV"), rs.getString("TenKH"), rs.getString("SDT"), rs.getString("TongTien"),
                        rs.getString("NgayTao"), rs.getString("TrangThai"), rs.getString("HinhThucTT"), rs.getString("DiaChi"), rs.getString("TenNV"), rs.getString("NgayThanhToan"), rs.getString("MaGiaoDich")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTKHD;
    }

    public ArrayList<HoaDon> locHoaDon(String trangThai, String hinhThucTT) {
        ArrayList<HoaDon> listLocHD = new ArrayList<>();
        sql = "SELECT * FROM tblHoaDon WHERE TrangThai = ? AND HinhThucTT = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, trangThai);
            ps.setString(2, hinhThucTT);
            rs = ps.executeQuery();
            if (rs.next()) {
                listLocHD.add(new HoaDon(rs.getInt("ID"), rs.getString("MaHoaDon"), rs.getString("MaNV"), rs.getString("TenKH"), rs.getString("SDT"), rs.getString("TongTien"),
                        rs.getString("NgayTao"), rs.getString("TrangThai"), rs.getString("HinhThucTT"), rs.getString("DiaChi"), rs.getString("TenNV"), rs.getString("NgayThanhToan"), rs.getString("MaGiaoDich")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLocHD;
    }

    public ArrayList<HoaDonChiTiet> lienKetHoaDon(String id) {
        ArrayList<HoaDonChiTiet> listLienKetHD = new ArrayList<>();
        sql = "SELECT * FROM tblHoaDonChiTiet WHERE ID_HD = ?";
        try {
            con = DBConnect.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                listLienKetHD.add(new HoaDonChiTiet(rs.getString("MaCTSP"), rs.getInt("ID_HD"), rs.getString("TenSanPham"), rs.getString("Hang"), rs.getString("Size"), rs.getString("MauSac"), rs.getInt("SoLuong"), rs.getString("Gia"), rs.getString("TongTien")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLienKetHD;
    }
}
