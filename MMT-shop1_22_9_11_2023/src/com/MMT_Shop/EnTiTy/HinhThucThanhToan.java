package com.MMT_Shop.EnTiTy;

public class HinhThucThanhToan {

    private int id;
    private String maHD;
    private int idHinhthuc;
    private String maHT;
    private float tienMat;
    private float tienCK;
    private float tienTL;
    private String created_At;
    private String updated_At;
    private String created_By;
    private String updated_By;
    private String Deleted;

    public HinhThucThanhToan() {
    }

    public HinhThucThanhToan(int id, String maHD, int idHinhthuc, String maHT, float tienMat, float tienCK, float tienTL, String created_At, String updated_At, String created_By, String updated_By, String Deleted) {
        this.id = id;
        this.maHD = maHD;
        this.idHinhthuc = idHinhthuc;
        this.maHT = maHT;
        this.tienMat = tienMat;
        this.tienCK = tienCK;
        this.tienTL = tienTL;
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

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public int getIdHinhthuc() {
        return idHinhthuc;
    }

    public void setIdHinhthuc(int idHinhthuc) {
        this.idHinhthuc = idHinhthuc;
    }

    public String getMaHT() {
        return maHT;
    }

    public void setMaHT(String maHT) {
        this.maHT = maHT;
    }

    public float getTienMat() {
        return tienMat;
    }

    public void setTienMat(float tienMat) {
        this.tienMat = tienMat;
    }

    public float getTienCK() {
        return tienCK;
    }

    public void setTienCK(float tienCK) {
        this.tienCK = tienCK;
    }

    public float getTienTL() {
        return tienTL;
    }

    public void setTienTL(float tienTL) {
        this.tienTL = tienTL;
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
