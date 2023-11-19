package com.MMT_Shop.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChuyenDoi {

    public static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

    public static String layNgayString(Date ngay) {
        return formatDate.format(ngay);
    }

    public static java.util.Date layNgayDate(String ngay) {
        try {
            return formatDate.parse(ngay);
        } catch (Exception e) {
            System.out.println("lỗi chuyển đổi String sang ngày");
            //System.out.print(e);
            return null;
        }
    }
}
