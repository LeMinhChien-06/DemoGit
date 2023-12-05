package com.MMT_Shop.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ChuyenDoi {

    // public static SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    public static String layNgayString(Date ngay) {
        return formatDate.format(ngay);
    }

    public static Date layNgayDate(String ngay) {
        try {
            return formatDate.parse(ngay);
        } catch (Exception e) {
            System.out.println("lỗi chuyển đổi String sang ngày");
            //System.out.print(e);
            return null;
        }
    }

    public static LocalDate layNgayLocalDate(Date date) {
        LocalDate a;
        try {
          return  a = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            System.out.println("lỗi chuyển đổi Date sang LocalDate");
            return null;
        }

    }
}
