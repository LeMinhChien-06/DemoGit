package com.MMT_Shop.Lam;

import java.time.LocalDate;

public class Voucher {

    private int id;
    private String ten;
    private String code;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    private int soLuong;
    private double phanTramGiam;
    private double giaTriHoaDonToiThieu;
    private double soTienGiamToiDa;
    private String created_At;
    private String updated_At;
    private String created_By;
    private String updated_By;

    public Voucher() {
    }

    public Voucher(int id, String ten, String code, LocalDate startDate, LocalDate endDate, String status, int soLuong, double phanTramGiam, double giaTriHoaDonToiThieu, double soTienGiamToiDa, String created_At, String updated_At, String created_By, String updated_By) {
        this.id = id;
        this.ten = ten;
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.soLuong = soLuong;
        this.phanTramGiam = phanTramGiam;
        this.giaTriHoaDonToiThieu = giaTriHoaDonToiThieu;
        this.soTienGiamToiDa = soTienGiamToiDa;
        this.created_At = created_At;
        this.updated_At = updated_At;
        this.created_By = created_By;
        this.updated_By = updated_By;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public double getGiaTriHoaDonToiThieu() {
        return giaTriHoaDonToiThieu;
    }

    public void setGiaTriHoaDonToiThieu(double giaTriHoaDonToiThieu) {
        this.giaTriHoaDonToiThieu = giaTriHoaDonToiThieu;
    }

    public double getSoTienGiamToiDa() {
        return soTienGiamToiDa;
    }

    public void setSoTienGiamToiDa(double soTienGiamToiDa) {
        this.soTienGiamToiDa = soTienGiamToiDa;
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

}
