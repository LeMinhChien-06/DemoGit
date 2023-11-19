package com.MMT_Shop.model;

public class HoaDon {

    private int stt;
    private String maHD;
    private String maNV;
    private String tenKH;
    private String sdt;
    private String tongTien;
    private String ngayTao;
    private String trangThai;
    private String hinhThucTT;
    private String diaChi;
    private String tenNV;
    private String ngayTT;
    private String maGiaoDich;

    public HoaDon() {
    }

    public HoaDon(int stt, String maHD, String maNV, String tenKH, String sdt, String tongTien, String ngayTao, String trangThai, String hinhThucTT, String diaChi, String tenNV, String ngayTT, String maGiaoDich) {
        this.stt = stt;
        this.maHD = maHD;
        this.maNV = maNV;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.tongTien = tongTien;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
        this.hinhThucTT = hinhThucTT;
        this.diaChi = diaChi;
        this.tenNV = tenNV;
        this.ngayTT = ngayTT;
        this.maGiaoDich = maGiaoDich;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhThucTT() {
        return hinhThucTT;
    }

    public void setHinhThucTT(String hinhThucTT) {
        this.hinhThucTT = hinhThucTT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNgayTT() {
        return ngayTT;
    }

    public void setNgayTT(String ngayTT) {
        this.ngayTT = ngayTT;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

}
