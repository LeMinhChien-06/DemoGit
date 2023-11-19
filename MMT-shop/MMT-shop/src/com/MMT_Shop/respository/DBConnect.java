package com.MMT_Shop.respository;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnect {

    public static String URL = "jdbc:sqlserver://localhost:1433;databaseName=Duan1;encrypt=true;trustServerCertificate=true;";
    public static String USERNAME = "sa";
    public static String PASS = "12345678";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASS);
        } catch (SQLException e) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, e);
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Kết nối thành công");
        } else {
            System.out.println("Kết nối thất bại");
        }
    }

}
