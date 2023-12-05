package com.MMT_Shop.model;

public class Model_Button {

    private String name;
    private ButtonType type;

    public Model_Button() {
    }

    public Model_Button(String name, ButtonType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }

    public static enum ButtonType {
        Button
    }

}
