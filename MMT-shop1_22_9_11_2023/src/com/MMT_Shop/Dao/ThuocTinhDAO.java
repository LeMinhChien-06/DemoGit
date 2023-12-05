package com.MMT_Shop.Dao;

import com.MMT_Shop.EnTiTy.ChatLieu;
import com.MMT_Shop.EnTiTy.Hang;
import com.MMT_Shop.EnTiTy.HinhAnh;
import com.MMT_Shop.EnTiTy.KichThuoc;
import com.MMT_Shop.EnTiTy.LoaiKhung;
import com.MMT_Shop.EnTiTy.MauSac;
import com.MMT_Shop.utility.DBcontext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ThuocTinhDAO {


    //hãng
    public ArrayList<Hang> SelectHang() {
        ArrayList<Hang> data = new ArrayList<>();
        String Selete_All_sql = "SELECT* FROM ThuongHieu ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Hang h = new Hang();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
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

    public Integer InsertHang(Hang h) {
        Integer row = null;
        String Insert_sql = "insert into ThuongHieu(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteHang(Hang h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM ThuongHieu WHERE  ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMa());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    //khung
    public ArrayList<LoaiKhung> SelectKhung() {
        ArrayList<LoaiKhung> data = new ArrayList<>();
        String Selete_All_sql = "SELECT* FROM KhungGiuong ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoaiKhung h = new LoaiKhung();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
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

    public Integer InsertKhung(LoaiKhung h) {
        Integer row = null;
        String Insert_sql = "insert into KhungGiuong(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteKhung(LoaiKhung h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM KhungGiuong WHERE  ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMa());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    //chất liệu
    public ArrayList<ChatLieu> SelectChatLieu() {
        ArrayList<ChatLieu> data = new ArrayList<>();
        String Selete_All_sql = "SELECT* FROM ChatLieu ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatLieu h = new ChatLieu();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
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

    public Integer InsertChatLieu(ChatLieu h) {
        Integer row = null;
        String Insert_sql = "insert into ChatLieu(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteChatLieu(ChatLieu h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM ChatLieu WHERE  ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMa());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    //kích thước
    public ArrayList<KichThuoc> SelectKichThuoc() {
        ArrayList<KichThuoc> data = new ArrayList<>();
        String Selete_All_sql = "SELECT* FROM KichThuoc ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KichThuoc h = new KichThuoc();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
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

    public Integer InsertKichThuoc(KichThuoc h) {
        Integer row = null;
        String Insert_sql = "insert into KichThuoc(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteKichThuoc(KichThuoc h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM KichThuoc WHERE  ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMa());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    //màu sắc
    public ArrayList<MauSac> SelectMauSac() {
        ArrayList<MauSac> data = new ArrayList<>();
        String Selete_All_sql = "SELECT* FROM MauSac ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MauSac h = new MauSac();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
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

    public Integer InsertMauSac(MauSac h) {
        Integer row = null;
        String Insert_sql = "insert into MauSac(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteMauSac(MauSac h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM MauSac WHERE  ma =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMa());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }
    
    //Hình ảnh 
    public ArrayList<HinhAnh> SelectHinhAnh(String ten) {
        ArrayList<HinhAnh> data = new ArrayList<>();
        String Selete_All_sql = "select * from HinhAnh where ten like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, ten);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HinhAnh h = new HinhAnh();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
                h.setCreated_At(rs.getString("Created_At"));
                h.setUpdated_At(rs.getString("Updated_At"));
                h.setCreated_By(rs.getString("Created_By"));
                h.setUpdated_By(rs.getString("Updated_By"));
                h.setDeleted(rs.getString("Deleted"));
                data.add(h);
            }
              System.out.println(data.size());

        } catch (Exception e) {
            System.out.print(e);
        }
        return data;
    }
    public Integer InsertHinhAnh(HinhAnh h) {
        Integer row = null;
        String Insert_sql = "insert into HinhAnh(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getMa());
            ps.setString(2, h.getTen());
            ps.setString(3, h.getCreated_At());
            ps.setString(4, h.getUpdated_At());
            ps.setString(5, h.getCreated_By());
            ps.setString(6, h.getUpdated_By());
            ps.setString(7, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }
}
