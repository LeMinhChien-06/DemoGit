/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MMT_Shop.ngan;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class NhanVien {

    private int Id;
    private String MaNV;
    private String hoTen;
    private String Sdt;
    private String diaChi;
    private boolean gioiTinh;
    private String soCMND;
    private String ngaySinh;
    private String Email;
    private String matKhau;
    private String nhapLaiMK;
    private String chucVu;
    private boolean trangThai;
    private String Created_At;
    private String Updated_At;
    private String Created_By;
    private String Updated_By;
    private String Deleted;
  
  

    public NhanVien() {
    }

    public NhanVien(int Id, String MaNV, String hoTen, String Sdt, String diaChi, boolean gioiTinh, String soCMND, String ngaySinh, String Email, String chucVu, boolean trangThai, String Created_At, String Updated_At, String Created_By, String Updated_By, String Deleted) {
        this.Id = Id;
        this.MaNV = MaNV;
        this.hoTen = hoTen;
        this.Sdt = Sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.soCMND = soCMND;
        this.ngaySinh = ngaySinh;
        this.Email = Email;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.Created_At = Created_At;
        this.Updated_At = Updated_At;
        this.Created_By = Created_By;
        this.Updated_By = Updated_By;
        this.Deleted = Deleted;
    }

    
    public NhanVien(int Id, String MaNV, String hoTen, String Sdt, String diaChi, boolean gioiTinh, String soCMND, String ngaySinh, String Email, String matKhau, String nhapLaiMK, String chucVu, boolean trangThai, String Created_At, String Updated_At, String Created_By, String Updated_By, String Deleted) {
        this.Id = Id;
        this.MaNV = MaNV;
        this.hoTen = hoTen;
        this.Sdt = Sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.soCMND = soCMND;
        this.ngaySinh = ngaySinh;
        this.Email = Email;
        this.matKhau = matKhau;
        this.nhapLaiMK = nhapLaiMK;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.Created_At = Created_At;
        this.Updated_At = Updated_At;
        this.Created_By = Created_By;
        this.Updated_By = Updated_By;
        this.Deleted = Deleted;
    }

   
    
   
     
    
public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getNhapLaiMK() {
        return nhapLaiMK;
    }

    public void setNhapLaiMK(String nhapLaiMK) {
        this.nhapLaiMK = nhapLaiMK;
    }

    public String ChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
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
