/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.MMT_Shop.ngan;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class Model_Form {
    public static SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy-MM-dd");
    public  static String layNgayString(Date ngay){
        return formatDate1.format(ngay);
    }
    public static java.util.Date layNgayDate(String ngay) {
        try {
            return formatDate1.parse(ngay);
        } catch (Exception e) {
            System.out.println("lỗi chuyển đổi String sang ngày");
            //System.out.print(e);
            return null;
        }
    }
}
