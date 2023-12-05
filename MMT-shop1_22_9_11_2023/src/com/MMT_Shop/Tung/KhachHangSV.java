/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MMT_Shop.Tung;

import com.MMT_Shop.EnTiTy.SanPhamCT;
import com.MMT_Shop.utility.DBcontext;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Tung
 */
public class KhachHangSV {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public ArrayList<KhachHang> selectkh(String makh) {
        ArrayList<KhachHang> data = new ArrayList<>();
        String Selete_All_sql = "SELECT * FROM KhachHang where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, makh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang h = new KhachHang();
                h.setMaKH(rs.getString("ma"));
                h.setTenKH(rs.getString("ten"));
                h.setDiaChi(rs.getString("diaChi"));
                h.setSDT(rs.getString("soDienThoai"));
                h.setEmail(rs.getString("email"));
                h.setGioiTinh(rs.getBoolean("gioitinh"));
                h.setNgaySinh(rs.getString("ngaySinh"));
                h.setCreated_At(rs.getString("Created_At"));
                h.setUpdated_At(rs.getString("Updated_At"));
                h.setCreated_By(rs.getString("Created_By"));
                h.setUpdated_By(rs.getString("Updated_By"));
                h.setDeleted(rs.getString("Deleted"));
                data.add(h);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }

    public ArrayList<KhachHang> timKiem(String makh ) {
        ArrayList<KhachHang> data = new ArrayList<>();
        String Selete_All_sql = "select * from KhachHang where ma like ? or ten like ? or soDienThoai like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, makh);
            ps.setString(2, makh);
            ps.setString(3, makh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang h = new KhachHang();
                h.setMaKH(rs.getString("ma"));
                h.setTenKH(rs.getString("ten"));
                h.setDiaChi(rs.getString("diaChi"));
                h.setSDT(rs.getString("soDienThoai"));
                h.setEmail(rs.getString("email"));
                h.setGioiTinh(rs.getBoolean("gioitinh"));
                h.setNgaySinh(rs.getString("ngaySinh"));
                h.setCreated_At(rs.getString("Created_At"));
                h.setUpdated_At(rs.getString("Updated_At"));
                h.setCreated_By(rs.getString("Created_By"));
                h.setUpdated_By(rs.getString("Updated_By"));
                h.setDeleted(rs.getString("Deleted"));
                data.add(h);
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }

    public ArrayList<KhachHang> selectAll() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang order by Created_At desc";
        try {
            Statement statement = new DBcontext().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {

                String ma = rs.getString("ma");
                String ten = rs.getString("ten");
                boolean gt = rs.getBoolean("gioitinh");
                String ns = rs.getString("ngaySinh");
                String sdt = rs.getString("soDienThoai");
                String email = rs.getString("email");
                String diachi = rs.getString("diaChi");
                list.add(new KhachHang(ma, ten, gt, ns, sdt, email, diachi));
            }
        } catch (Exception e) {
            System.out.println("NHANVIEN SERVICE ERROR SELECT ALL: " + e);
        }
        return list;
    }

    public Integer themKH(KhachHang kh) {

        Integer row = null;
        String Delete_sql = "INSERT INTO KhachHang (ma, ten, gioiTinh,NgaySinh,soDienThoai,Email,DiaChi,Created_At,Updated_At,Created_By,Updated_By,Deleted)Values (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setBoolean(3, kh.isGioiTinh());
            ps.setString(4, kh.getNgaySinh());
            ps.setString(5, kh.getSDT());
            ps.setString(6, kh.getEmail());
            ps.setString(7, kh.getDiaChi());
            ps.setString(8, kh.getCreated_At());
            ps.setString(9, kh.getUpdated_At());
            ps.setString(10, kh.getCreated_By());
            ps.setString(11, kh.getUpdated_By());
            ps.setString(12, kh.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer UpdatedKH(KhachHang kh) {
        Integer row = null;
        String Delete_sql = "update KhachHang set ten = ?, diaChi=?,email=?,soDienThoai=?,ngaySinh=?,gioiTinh=?, Updated_At=?,Updated_By=? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getDiaChi());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getSDT());
            ps.setString(5, kh.getNgaySinh());
            ps.setBoolean(6, kh.isGioiTinh());
            ps.setString(7, kh.getUpdated_At());
            ps.setString(8, kh.getUpdated_By());
            ps.setString(9, kh.getMaKH());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteKH(KhachHang kh) {
        Integer row = null;
        String Delete_sql = "DELETE KhachHang WHERE ma = ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, kh.getMaKH());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }
}
