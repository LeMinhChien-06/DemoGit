package com.MMT_Shop.Dao;

import com.MMT_Shop.Lam.Voucher;
import com.MMT_Shop.utility.DBcontext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class VoucherDAO {

    public ArrayList<Voucher> selectALL() {
        ArrayList<Voucher> list = new ArrayList<>();
        String sql = "select * from Voucher ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
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

    public Integer updatVC(String code, String Status) {
        Integer row = null;
        String sql = " update Voucher set trangThai=? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, Status);
            ps.setString(2, code);
            row = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;

    }

    public Integer adddata(Voucher t) {
        Integer row = null;
        String sql = "INSERT INTO Voucher(ma,ten, ngayBatDau, ngayKetThuc, soLuong, trangThai, phanTramGiam, giaTriHoaDonToiThieu, soTienGiamToiDa, Created_At, Updated_At, Created_By, Updated_By) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, t.getCode());
            ps.setString(2, t.getTen());
            ps.setString(3, t.getStartDate().toString());
            ps.setString(4, t.getEndDate().toString());
            ps.setInt(5, t.getSoLuong());
            ps.setString(6, t.getStatus());
            ps.setDouble(7, t.getPhanTramGiam());
            ps.setDouble(8, t.getGiaTriHoaDonToiThieu());
            ps.setDouble(9, t.getSoTienGiamToiDa());
            ps.setString(10, t.getCreated_At());
            ps.setString(11, t.getUpdated_At());
            ps.setString(12, t.getCreated_By());
            ps.setString(13, t.getUpdated_By());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteData(String ma) {
        Integer row = null;
        String sql = "delete Voucher \n"
                + "where ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, ma);
            row = pstm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }
//    public Integer updatedata(Vocher t) {
//        Integer row = null;
//        String sql = " UPDATE Voucher set ten=?, ngayBatDau=?, ngayKetThuc =? , soLuong =? , trangThai = ?, phanTramGiam = ?, giaTriHoaDonToiThieu = ?, soTienGiamToiDa = ?, Created_At = ?, Updated_At = ?, Created_By = ?, Updated_By = ? where ma like ?";
//        Connection cn = DBcontext.getConnection();
//        try {
//            PreparedStatement ps = cn.prepareStatement(sql);
//            ps.setString(1, t.getTen());
//            ps.setString(2, t.getNgayBatDau());
//            ps.setString(3, t.getNgayKetThuc());
//            ps.setInt(4, t.getSoLuong());
//            ps.setString(5, t.getTrangThai());
//            ps.setDouble(6, t.getPhanTramGiam());
//            ps.setDouble(7, t.getGiaTriHoaDonToiThieu());
//            ps.setDouble(8, t.getSoTienGiamToiDa());
//            ps.setString(9, t.getCreated_At());
//            ps.setString(10, t.getUpdated_At());
//            ps.setString(11, t.getCreated_By());
//            ps.setString(12, t.getUpdated_By());
//            ps.setString(13, t.getMa());
//            row = ps.executeUpdate();
//
//            System.out.println("Thêm dữ liệu thành công");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return row;
//    }
}
