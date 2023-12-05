package com.MMT_Shop.Dao;

import com.MMT_Shop.EnTiTy.Logine;
import com.MMT_Shop.utility.DBcontext;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginDAO {

    public Logine selectById(String ma) {
        String sql = "SELECT * FROM NhanVien WHERE ma LIKE '" + ma + "'";
//        System.out.println(sql);
        try {
            Statement statement = new DBcontext().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Logine lg = new Logine();
                lg.setId(rs.getInt("ID"));
                lg.setMaNV(rs.getString("ma"));
                lg.setHoTen(rs.getString("TEN"));
                lg.setSdt(rs.getString("soDienThoai"));
                lg.setDiaChi(rs.getString("diaChi"));
                lg.setGioiTinh(rs.getBoolean("gioiTinh"));
                lg.setSoCMND(rs.getString("CCCD"));
                lg.setNgaySinh(rs.getString("NGAYSINH"));
                lg.setEmail(rs.getString("EMAIL"));
                lg.setChucVu(rs.getString("vaiTroID"));
                lg.setMatKhau(rs.getString("password"));
                lg.setTrangThai(rs.getBoolean("TrangThai"));
                return lg;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
 public static Logine lg = null;
}
