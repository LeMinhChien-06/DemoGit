package com.MMT_Shop.model;

public class HoaDonChiTiet {

    private String maCTSP;
    private int idHD;
    private String tenSP;
    private String hang;
    private String size;
    private String mauSac;
    private int soLuong;
    private String gia;
    private String tongTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maCTSP, int idHD, String tenSP, String hang, String size, String mauSac, int soLuong, String gia, String tongTien) {
        this.maCTSP = maCTSP;
        this.idHD = idHD;
        this.tenSP = tenSP;
        this.hang = hang;
        this.size = size;
        this.mauSac = mauSac;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tongTien = tongTien;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public int getIdHD() {
        return idHD;
    }

    public void setIdHD(int idHD) {
        this.idHD = idHD;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

}
