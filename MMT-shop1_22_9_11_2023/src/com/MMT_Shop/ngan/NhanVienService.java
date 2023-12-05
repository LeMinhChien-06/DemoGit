
package com.MMT_Shop.ngan;


import com.MMT_Shop.utility.DBcontext;
import java.util.ArrayList;
import java.sql.*;
public class NhanVienService {

    public NhanVien selectById(String ma) {
        String sql = "SELECT * FROM NhanVien WHERE ma LIKE '" + ma + "'";
//        System.out.println(sql);
        try {
            Statement statement = new DBcontext().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String MaNV = rs.getString("ma");
                String hoTen = rs.getString("TEN");
                String sdt = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String CMND = rs.getString("CCCD");
                String ngaySinh = rs.getString("NGAYSINH");
                String email = rs.getString("EMAIL");
                String chucVu = rs.getString("vaiTroID");
                boolean trangThai = rs.getBoolean("TrangThai");
                String Create_At = rs.getString("Created_At");
                String Updated_At = rs.getString("Updated_At");
                String Created_By = rs.getString("Created_By");
                String Updated_By = rs.getString("Updated_By");
                String Deleted = rs.getString("Created_At");
                return new NhanVien(id, ma, hoTen, sdt, diaChi, gioiTinh, CMND, ngaySinh, email, chucVu, trangThai, Create_At, Updated_At, Created_By, Updated_By, Deleted);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    public ArrayList<NhanVien> selectAll() {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "select NhanVien.id as 'id',vaiTro.ten as 'vaitro', NhanVien.ma as 'ma', NhanVien.ten as 'ten',\n"
                + "ngaySinh, soDienThoai, email, CCCD, gioiTinh, diaChi, trangThai, password, NhanVien.Created_At,\n"
                + "NhanVien.Updated_At, NhanVien.Created_By,NhanVien.Updated_By,NhanVien.Deleted\n"
                + "from NhanVien left join vaiTro on NhanVien.vaiTroID = vaiTro.id";

        try {
            Statement statement = new DBcontext().getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String MaNV = rs.getString("ma");
                String hoTen = rs.getString("TEN");
                String sdt = rs.getString("soDienThoai");
                String diaChi = rs.getString("diaChi");
                boolean gioiTinh = rs.getBoolean("gioiTinh");
                String CMND = rs.getString("CCCD");
                String ngaySinh = rs.getString("NGAYSINH");
                String email = rs.getString("EMAIL");
                String chucVu = rs.getString("vaiTro");
                boolean trangThai = rs.getBoolean("TrangThai");
                String Create_At = rs.getString("Created_At");
                String Updated_At = rs.getString("Updated_At");
                String Created_By = rs.getString("Created_By");
                String Updated_By = rs.getString("Updated_By");
                String Deleted = rs.getString("Created_At");
                list.add(new NhanVien(id, MaNV, hoTen, sdt, diaChi, gioiTinh, CMND, ngaySinh, email, chucVu, trangThai, Create_At, Updated_At, Created_By, Updated_By, Deleted));
            }
        } catch (Exception e) {
            System.out.println("NHANVIEN SERVICE ERROR SELECT ALL: " + e);
        }
        return list;
    }

//    
    public Integer InsertNhanVien(NhanVien nhanVien) {
        Integer row = null;
        String Insert_sql = "insert into NhanVien(ma,Ten,soDienThoai,ngaySinh,email,CCCD,gioiTinh,diaChi,trangThai\n"
                + ",password,Created_At,Updated_At,Created_By,Updated_By,Deleted,vaiTroID)\n"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,(select id from vaiTro where ten like ?))";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement st = cn.prepareStatement(Insert_sql);

            st.setString(1, nhanVien.getMaNV());
            st.setString(2, nhanVien.getHoTen());
            st.setString(3, nhanVien.getSdt());
            st.setString(4, nhanVien.getNgaySinh());
            st.setString(5, nhanVien.getEmail());
            st.setString(6, nhanVien.getSoCMND());
            st.setBoolean(7, nhanVien.isGioiTinh());
            st.setString(8, nhanVien.getDiaChi());
            st.setBoolean(9, nhanVien.isTrangThai());
            st.setString(10, nhanVien.getMatKhau());
            st.setString(11, nhanVien.getCreated_At());
            st.setString(12, nhanVien.getUpdated_At());
            st.setString(13, nhanVien.getCreated_By());
            st.setString(14, nhanVien.getUpdated_By());
            st.setString(15, nhanVien.getDeleted());
            st.setString(16, nhanVien.ChucVu());

            row = st.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer UpdateNhanVien(NhanVien nhanVien) {
        Integer row = null;
        String Insert_sql = "update NhanVien set Ten=?, soDienThoai=?,diaChi=?,gioiTinh=?,CCCD=?,\n"
                + "email=?,ngaySinh=?,Updated_At=?,Updated_By=?,Deleted =? where ma like ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement st = cn.prepareStatement(Insert_sql);

            st.setString(1, nhanVien.getHoTen());
            st.setString(2, nhanVien.getSdt());
            st.setString(3, nhanVien.getDiaChi());
            st.setBoolean(4, nhanVien.isGioiTinh());
            st.setString(5, nhanVien.getSoCMND());
            st.setString(6, nhanVien.getEmail());
            st.setString(7, nhanVien.getNgaySinh());
            st.setString(8, nhanVien.getUpdated_At());
            st.setString(9, nhanVien.getUpdated_By());
            st.setString(10, nhanVien.getDeleted());
            st.setString(11, nhanVien.getMaNV());

            row = st.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public Integer DeleteNhanVien(NhanVien nhanVien) {
        Integer row = null;
        String Insert_sql = "UPDATE NhanVien SET TrangThai=? Where ma LIKE ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement st = cn.prepareStatement(Insert_sql);

            st.setBoolean(1, nhanVien.isTrangThai());
            st.setString(2, nhanVien.getMaNV());

            row = st.executeUpdate();

        } catch (Exception e) {
            System.out.print(e);
        }
        return row;
    }

    public ArrayList<NhanVien> timKiem(String maNV, String sdt) {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE ma = ? OR soDienThoai =? ";

        try {
            Connection cn = DBcontext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maNV);
            // ps.setString(2, ten);
            ps.setString(2, sdt);
//            ps.setString(4, ngaySinh);
//            ps.setString(5, diaChi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.add(new NhanVien(rs.getInt("ID"), rs.getString("MaNV"), rs.getString("TEN"), rs.getString("SDT"), rs.getString("DiaChi"), rs.getBoolean("GIOITINH"), rs.getString("CMND"), rs.getString("NgaySinh"), rs.getString("Email"), rs.getString("ChucVu"), rs.getBoolean("TrangThai"), rs.getString("Created_At"), rs.getString("Updated_At"), rs.getString("Created_By"), rs.getString("Updated_By"), rs.getString("Deleted")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<NhanVien> timKiemMa(String MANV) {
        ArrayList<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE ma = ?";

        try {
            Connection cn = DBcontext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, MANV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                list.add(new NhanVien(rs.getInt("ID"), rs.getString("ma"), rs.getString("TEN"), rs.getString("soDienThoai"), rs.getString("DiaChi"), rs.getBoolean("GIOITINH"), rs.getString("CCCD"), rs.getString("NgaySinh"), rs.getString("Email"), rs.getString("vaiTroID"), rs.getBoolean("TrangThai"), rs.getString("Created_At"), rs.getString("Updated_At"), rs.getString("Created_By"), rs.getString("Updated_By"), rs.getString("Deleted")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
        return list;
    }

//    xóa form 
    public String deleteForm(String manv) {
        Connection con = new DBcontext().getConnection();
        String sql = "DELETE FROM NhanVien WHERE ma = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, manv);
            int result = st.executeUpdate();
            if (result > 0) {
                return "Xoa Thanh Cong";
            }
            return "Xoa That Bai";
        } catch (Exception e) {
            return "Xoa Loi: " + e;
        }
    }

}
