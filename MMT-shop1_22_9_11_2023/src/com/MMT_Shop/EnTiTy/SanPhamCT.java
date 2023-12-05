package com.MMT_Shop.EnTiTy;

public class SanPhamCT {
    private int id;
    private String maSPCT;
    private String tenSP;
    private String kichThuoc;
    private String chatLieu; 
    private String mauSac;
    private String hinhAnh;
    private String anhQR;
    private String thuongHieu;
    private String loaiKhung;
    private float gia;
    private int soLuong;
    private boolean trangThai;
    private String created_At;
    private String updated_At;
    private String created_By;
    private String updated_By;
    private String Deleted;

    public SanPhamCT() {
    }

    public SanPhamCT(int id, String maSPCT, String tenSP, String kichThuoc, String chatLieu, String mauSac, String hinhAnh, String anhQR, String thuongHieu, String loaiKhung, float gia, int soLuong, boolean trangThai, String created_At, String updated_At, String created_By, String updated_By, String Deleted) {
        this.id = id;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.kichThuoc = kichThuoc;
        this.chatLieu = chatLieu;
        this.mauSac = mauSac;
        this.hinhAnh = hinhAnh;
        this.anhQR = anhQR;
        this.thuongHieu = thuongHieu;
        this.loaiKhung = loaiKhung;
        this.gia = gia;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.created_At = created_At;
        this.updated_At = updated_At;
        this.created_By = created_By;
        this.updated_By = updated_By;
        this.Deleted = Deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getAnhQR() {
        return anhQR;
    }

    public void setAnhQR(String anhQR) {
        this.anhQR = anhQR;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public String getLoaiKhung() {
        return loaiKhung;
    }

    public void setLoaiKhung(String loaiKhung) {
        this.loaiKhung = loaiKhung;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getCreated_At() {
        return created_At;
    }

    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }

    public String getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public String getUpdated_By() {
        return updated_By;
    }

    public void setUpdated_By(String updated_By) {
        this.updated_By = updated_By;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String Deleted) {
        this.Deleted = Deleted;
    }

   
}
