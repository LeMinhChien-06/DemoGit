package com.MMT_Shop.Dao;

import com.MMT_Shop.EnTiTy.SanPham;
import com.MMT_Shop.EnTiTy.SanPhamCT;
import com.MMT_Shop.utility.DBcontext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SanPhamDAO {

//    public void setForm(JPanel com, JPanel ten) {
//        ten.removeAll();
//        ten.add(com);
//        ten.repaint();
//        ten.revalidate();
//    }

    //Sản phâm
    public ArrayList<SanPham> SelectSanPham() {
        ArrayList<SanPham> data = new ArrayList<>();
        String Selete_All_sql = "select SanPham.id as 'id',ma, ten,sum(CTSP.soLuong) as 'soLuong',\n"
                + "SanPham.Created_At,SanPham.Updated_At,SanPham.Created_By,\n"
                + "SanPham.Updated_By,SanPham.Deleted  from SanPham \n"
                + "left join CTSP on CTSP.sanPhamID=SanPham.id \n"
                + "group by sanPhamID, SanPham.id,ma,ten,SanPham.Created_At,SanPham.Updated_At,SanPham.Created_By,\n"
                + "SanPham.Updated_By,SanPham.Deleted\n";
               
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham h = new SanPham();
                h.setId(rs.getInt("id"));
                h.setMa(rs.getString("ma"));
                h.setTen(rs.getString("ten"));
                h.setSoLuong(rs.getInt("soLuong"));
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

    public Integer InsertSanPham(SanPham h) {
        Integer row = null;
        String Insert_sql = "insert into SanPham(ma,ten,Created_At,Updated_At,Created_By,Updated_By,Deleted)values (?,?,?,?,?,?,?)";
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

    public Integer DeleteSanPham(SanPham h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM SanPham WHERE  ma =?";
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

    public int sLSPTheoma(String ma) {
        int sl = 0;
        String Selete_All_sql = "select soLuong from CTSP where maCTSP like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sl = rs.getInt("soLuong");
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return sl;

    }


    //SP chi tiết 
    public ArrayList<SanPhamCT> SelectSanPhamCT() {
        ArrayList<SanPhamCT> data = new ArrayList<>();
        String Selete_All_sql = "select CTSP.id as 'id', maCTSP,SanPham.ten as'sanPham',\n"
                + "KichThuoc.ten as'kichThuoc',chatLieu.ten as'chatLieu',\n"
                + "MauSac.ten as 'mauSac',HinhAnh.ten as 'hinhAnh',\n"
                + "anhQRID,ThuongHieu.ten as 'thuongHieu',\n"
                + "KhungGiuong.ten as 'loaiKhung',gia,soLuong,trangThai,\n"
                + "CTSP.Created_At,CTSP.Updated_At,CTSP.Created_By,\n"
                + "CTSP.Updated_By,CTSP.Deleted  from CTSP \n"
                + "join SanPham on CTSP.sanPhamID = SanPham.id \n"
                + "join KichThuoc on CTSP.kichThuocID= KichThuoc.id\n"
                + "join ChatLieu on CTSP.chatLieuID= ChatLieu.id \n"
                + "join MauSac on CTSP.mauSacID = MauSac.id \n"
                + "join HinhAnh on CTSP.hinhAnhID = HinhAnh.id\n"
                + "join ThuongHieu on CTSP.thuongHieuID = ThuongHieu.id\n"
                + "join KhungGiuong on CTSP.loaiKhungID = KhungGiuong.id\n";
              
               
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT h = new SanPhamCT();
                h.setId(rs.getInt("id"));
                h.setMaSPCT(rs.getString("maCTSP"));
                h.setTenSP(rs.getString("sanPham"));
                h.setKichThuoc(rs.getString("kichThuoc"));
                h.setChatLieu(rs.getString("chatLieu"));
                h.setMauSac(rs.getString("mauSac"));
                h.setHinhAnh(rs.getString("hinhAnh"));
                h.setAnhQR(rs.getString("anhQRID"));
                h.setThuongHieu(rs.getString("thuongHieu"));
                h.setLoaiKhung(rs.getString("loaiKhung"));
                h.setGia(rs.getFloat("gia"));
                h.setSoLuong(rs.getInt("soLuong"));
                h.setTrangThai(rs.getBoolean("trangThai"));
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

    

    public Integer InsertSanPhamCT(SanPhamCT h) {
        Integer row = null;
        String Insert_sql = "insert into CTSP(sanPhamID ,kichThuocID,chatLieuID,mauSacID,hinhAnhID,anhQRID,thuongHieuID,loaiKhungID,maCTSP,gia,soLuong,trangThai,Created_At,Updated_At,Created_By,Updated_By,Deleted)\n"
                + "values(\n"
                + "(select id from SanPham where ten like ?), \n"
                + "(select id from KichThuoc where ten like ?), \n"
                + "(select id from ChatLieu where ten like ?),\n"
                + "(select id from MauSac where ten like ?),\n"
                + "(select id from HinhAnh where ten like ?),?,\n"
                + "(select id from ThuongHieu where ten like ?),\n"
                + "(select id from KhungGiuong where ten like ?),\n"
                + "?,?,?,?,?,?,?,?,?)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getTenSP());
            ps.setString(2, h.getKichThuoc());
            ps.setString(3, h.getChatLieu());
            ps.setString(4, h.getMauSac());
            ps.setString(5, h.getHinhAnh());
            ps.setString(6, h.getAnhQR());
            ps.setString(7, h.getThuongHieu());
            ps.setString(8, h.getLoaiKhung());
            ps.setString(9, h.getMaSPCT());
            ps.setFloat(10, h.getGia());
            ps.setInt(11, h.getSoLuong());
            ps.setBoolean(12, h.isTrangThai());
            ps.setString(13, h.getCreated_At());
            ps.setString(14, h.getUpdated_At());
            ps.setString(15, h.getCreated_By());
            ps.setString(16, h.getUpdated_By());
            ps.setString(17, h.getDeleted());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer UpDateSPCT(SanPhamCT h) {
        Integer row = null;
        String Insert_sql = "update CTSP set\n"
                + "kichThuocID = (select id from KichThuoc where ten like ?), \n"
                + "chatLieuID = (select id from ChatLieu where ten like ?),\n"
                + "mauSacID = (select id from MauSac where ten like ?),\n"
                + "hinhAnhID = (select id from HinhAnh where ten like ?), anhQRID = ?,\n"
                + "thuongHieuID = (select id from ThuongHieu where ten like ?),\n"
                + "loaiKhungID = (select id from KhungGiuong where ten like ?),\n"
                + "gia = ?, soLuong = ?, trangThai = ?,\n"
                + "Updated_At = ?, Updated_By = ?, Deleted = ?\n"
                + "where maCTSP like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setString(1, h.getKichThuoc());
            ps.setString(2, h.getChatLieu());
            ps.setString(3, h.getMauSac());
            ps.setString(4, h.getHinhAnh());
            ps.setString(5, h.getAnhQR());
            ps.setString(6, h.getThuongHieu());
            ps.setString(7, h.getLoaiKhung());
            ps.setFloat(8, h.getGia());
            ps.setInt(9, h.getSoLuong());
            ps.setBoolean(10, h.isTrangThai());
            ps.setString(11, h.getUpdated_At());
            ps.setString(12, h.getUpdated_By());
            ps.setString(13, h.getDeleted());
            ps.setString(14, h.getMaSPCT());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer UpDateSLSPCT(SanPhamCT h) {
        Integer row = null;
        String Insert_sql = "update CTSP set soLuong = ?\n"
                + "where maCTSP like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setInt(1, h.getSoLuong());
            ps.setString(2, h.getMaSPCT());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }
    public Integer UpDateSLSPCT1(String ma, int sl) {
        Integer row = null;
        String Insert_sql = "update CTSP set soLuong = ?\n"
                + "where maCTSP like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Insert_sql);
            ps.setInt(1, sl);
            ps.setString(2, ma);
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteSanPhamCT(SanPhamCT h) {
        Integer row = null;
        String Delete_sql = "DELETE FROM CTSP WHERE  maCTSP =?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Delete_sql);
            ps.setString(1, h.getMaSPCT());
            row = ps.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public SanPhamCT SelectSanPhamCTTheoMaSPCT(String maSPCT) {
        String Selete_All_sql = "select CTSP.id as 'id', maCTSP,SanPham.ten as'sanPham',\n"
                + "KichThuoc.ten as'kichThuoc',chatLieu.ten as'chatLieu',\n"
                + "MauSac.ten as 'mauSac',HinhAnh.ten as 'hinhAnh',\n"
                + "anhQRID,ThuongHieu.ten as 'thuongHieu',\n"
                + "KhungGiuong.ten as 'loaiKhung',gia,soLuong,trangThai,\n"
                + "CTSP.Created_At,CTSP.Updated_At,CTSP.Created_By,\n"
                + "CTSP.Updated_By,CTSP.Deleted  from CTSP \n"
                + "join SanPham on CTSP.sanPhamID = SanPham.id \n"
                + "join KichThuoc on CTSP.kichThuocID= KichThuoc.id\n"
                + "join ChatLieu on CTSP.chatLieuID= ChatLieu.id \n"
                + "join MauSac on CTSP.mauSacID = MauSac.id \n"
                + "join HinhAnh on CTSP.hinhAnhID = HinhAnh.id\n"
                + "join ThuongHieu on CTSP.thuongHieuID = ThuongHieu.id\n"
                + "join KhungGiuong on CTSP.loaiKhungID = KhungGiuong.id\n"
                + "where maCTSP like ?";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, maSPCT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT h = new SanPhamCT();
                h.setId(rs.getInt("id"));
                h.setMaSPCT(rs.getString("maCTSP"));
                h.setTenSP(rs.getString("sanPham"));
                h.setKichThuoc(rs.getString("kichThuoc"));
                h.setChatLieu(rs.getString("chatLieu"));
                h.setMauSac(rs.getString("mauSac"));
                h.setHinhAnh(rs.getString("hinhAnh"));
                h.setAnhQR(rs.getString("anhQRID"));
                h.setThuongHieu(rs.getString("thuongHieu"));
                h.setLoaiKhung(rs.getString("loaiKhung"));
                h.setGia(rs.getFloat("gia"));
                h.setSoLuong(rs.getInt("soLuong"));
                h.setTrangThai(rs.getBoolean("trangThai"));
                h.setCreated_At(rs.getString("Created_At"));
                h.setUpdated_At(rs.getString("Updated_At"));
                h.setCreated_By(rs.getString("Created_By"));
                h.setUpdated_By(rs.getString("Updated_By"));
                h.setDeleted(rs.getString("Deleted"));
                return h;
            }

        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

    public ArrayList<SanPhamCT> SelectSanPhamCTTheoMaSP(String maSP) {
        ArrayList<SanPhamCT> data = new ArrayList<>();
        String Selete_All_sql = "select CTSP.id as 'id', maCTSP,SanPham.ten as'sanPham',\n"
                + "KichThuoc.ten as'kichThuoc',chatLieu.ten as'chatLieu',\n"
                + "MauSac.ten as 'mauSac',HinhAnh.ten as 'hinhAnh',\n"
                + "anhQRID,ThuongHieu.ten as 'thuongHieu',\n"
                + "KhungGiuong.ten as 'loaiKhung',gia,soLuong,trangThai,\n"
                + "CTSP.Created_At,CTSP.Updated_At,CTSP.Created_By,\n"
                + "CTSP.Updated_By,CTSP.Deleted  from CTSP \n"
                + "join SanPham on CTSP.sanPhamID = SanPham.id \n"
                + "join KichThuoc on CTSP.kichThuocID= KichThuoc.id\n"
                + "join ChatLieu on CTSP.chatLieuID= ChatLieu.id \n"
                + "join MauSac on CTSP.mauSacID = MauSac.id \n"
                + "join HinhAnh on CTSP.hinhAnhID = HinhAnh.id\n"
                + "join ThuongHieu on CTSP.thuongHieuID = ThuongHieu.id\n"
                + "join KhungGiuong on CTSP.loaiKhungID = KhungGiuong.id\n"
                + "where sanPhamID = (select id from SanPham where ma like ?) \n";
      
              
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT h = new SanPhamCT();
                h.setId(rs.getInt("id"));
                h.setMaSPCT(rs.getString("maCTSP"));
                h.setTenSP(rs.getString("sanPham"));
                h.setKichThuoc(rs.getString("kichThuoc"));
                h.setChatLieu(rs.getString("chatLieu"));
                h.setMauSac(rs.getString("mauSac"));
                h.setHinhAnh(rs.getString("hinhAnh"));
                h.setAnhQR(rs.getString("anhQRID"));
                h.setThuongHieu(rs.getString("thuongHieu"));
                h.setLoaiKhung(rs.getString("loaiKhung"));
                h.setGia(rs.getFloat("gia"));
                h.setSoLuong(rs.getInt("soLuong"));
                h.setTrangThai(rs.getBoolean("trangThai"));
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

    public ArrayList<SanPhamCT> SelectSanPhamCTTheoTT(String thuocTinh) {
        ArrayList<SanPhamCT> data = new ArrayList<>();
        String Selete_All_sql = "select  CTSP.id as 'id', maCTSP,SanPham.ten as'sanPham',\n"
                + "KichThuoc.ten as'kichThuoc',chatLieu.ten as'chatLieu',\n"
                + "MauSac.ten as 'mauSac',HinhAnh.ten as 'hinhAnh',\n"
                + "anhQRID,ThuongHieu.ten as 'thuongHieu',\n"
                + "KhungGiuong.ten as 'loaiKhung',gia,soLuong,trangThai,\n"
                + "CTSP.Created_At,CTSP.Updated_At,CTSP.Created_By,\n"
                + "CTSP.Updated_By,CTSP.Deleted  from CTSP \n"
                + "join SanPham on CTSP.sanPhamID = SanPham.id \n"
                + "join KichThuoc on CTSP.kichThuocID= KichThuoc.id\n"
                + "join ChatLieu on CTSP.chatLieuID= ChatLieu.id \n"
                + "join MauSac on CTSP.mauSacID = MauSac.id \n"
                + "join HinhAnh on CTSP.hinhAnhID = HinhAnh.id\n"
                + "join ThuongHieu on CTSP.thuongHieuID = ThuongHieu.id\n"
                + "join KhungGiuong on CTSP.loaiKhungID = KhungGiuong.id\n"
                + "where sanPhamID =(select id from SanPham where ten like ?) \n"
                + "or thuongHieuID = (select id from ThuongHieu where ten like  ?)\n"
                + "or loaiKhungID = (select id from KhungGiuong where ten like  ?)\n"
                + "or chatLieuID = (select id from ChatLieu where ten like ?)\n"
                + "or kichThuocID = (select id from KichThuoc where ten like  ?)\n"
                + "or mauSacID = (select id from MauSac where ten like  ?)\n"
                +"or maCTSP like ?";
              
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(Selete_All_sql);
            ps.setString(1, thuocTinh);
            ps.setString(2, thuocTinh);
            ps.setString(3, thuocTinh);
            ps.setString(4, thuocTinh);
            ps.setString(5, thuocTinh);
            ps.setString(6, thuocTinh);
            ps.setString(7, thuocTinh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamCT h = new SanPhamCT();
                h.setId(rs.getInt("id"));
                h.setMaSPCT(rs.getString("maCTSP"));
                h.setTenSP(rs.getString("sanPham"));
                h.setKichThuoc(rs.getString("kichThuoc"));
                h.setChatLieu(rs.getString("chatLieu"));
                h.setMauSac(rs.getString("mauSac"));
                h.setHinhAnh(rs.getString("hinhAnh"));
                h.setAnhQR(rs.getString("anhQRID"));
                h.setThuongHieu(rs.getString("thuongHieu"));
                h.setLoaiKhung(rs.getString("loaiKhung"));
                h.setGia(rs.getFloat("gia"));
                h.setSoLuong(rs.getInt("soLuong"));
                h.setTrangThai(rs.getBoolean("trangThai"));
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



 
}
