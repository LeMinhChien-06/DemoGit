package com.MMT_Shop.model;

import javax.swing.Icon;

public class Model_Card_Thong_Tin {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVales() {
        return vales;
    }

    public void setVales(String vales) {
        this.vales = vales;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    public Model_Card_Thong_Tin(Icon icon, String title, String vales, String desription) {
        this.icon = icon;
        this.title = title;
        this.vales = vales;
        this.desription = desription;
    }

    public Model_Card_Thong_Tin() {
    }

    private Icon icon;
    private String title;
    private String vales;
    private String desription;
}
