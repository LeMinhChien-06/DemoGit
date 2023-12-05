
package com.MMT_Shop.Tung;

import java.text.SimpleDateFormat;

public class KhachHang {

    private String maKH;
    private String tenKH;
    private boolean gioiTinh;
    private String ngaySinh;
    private String SDT;
    private String email;
    private String diaChi;
    private String Created_At;
    private String Updated_At;
    private String Created_By;
    private String Updated_By;
    private String Deleted;
            
    public KhachHang() {
        
    }

    public KhachHang(String maKH, String tenKH, boolean gioiTinh, String ngaySinh, String SDT, String email, String diaChi, String Created_At, String Updated_At, String Created_By, String Updated_By, String Deleted) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.email = email;
        this.diaChi = diaChi;
        this.Created_At = Created_At;
        this.Updated_At = Updated_At;
        this.Created_By = Created_By;
        this.Updated_By = Updated_By;
        this.Deleted = Deleted;
    }

    public KhachHang(String maKH, String tenKH, boolean gioiTinh, String ngaySinh, String SDT, String email, String diaChi) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.email = email;
        this.diaChi = diaChi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getCreated_At() {
        return Created_At;
    }

    public void setCreated_At(String Created_At) {
        this.Created_At = Created_At;
    }

    public String getUpdated_At() {
        return Updated_At;
    }

    public void setUpdated_At(String Updated_At) {
        this.Updated_At = Updated_At;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String Created_By) {
        this.Created_By = Created_By;
    }

    public String getUpdated_By() {
        return Updated_By;
    }

    public void setUpdated_By(String Updated_By) {
        this.Updated_By = Updated_By;
    }

    public String getDeleted() {
        return Deleted;
    }

    public void setDeleted(String Deleted) {
        this.Deleted = Deleted;
    }
    
    }

   