
package com.MMT_Shop.EnTiTy;

public class LoaiKhung {
     private int id;
    private String ma;
    private String ten;
    private String created_At;
    private String updated_At;
    private String created_By;
    private String updated_By;
    private String Deleted;

    public LoaiKhung() {
    }

    public LoaiKhung(int id, String ma, String ten, String created_At, String updated_At, String created_By, String updated_By, String Deleted) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
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

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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
