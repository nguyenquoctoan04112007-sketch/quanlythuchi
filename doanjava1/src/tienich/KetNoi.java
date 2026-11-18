package tienich;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class KetNoi {

    private static final String DUONG_DAN =
            "jdbc:sqlserver://127.0.0.1:1433;databaseName=QLthuchi;encrypt=true;trustServerCertificate=true;";
    private static final String TAI_KHOAN = "QLthuchi";
    private static final String MAT_KHAU = "11111";

    public static Connection layKetNoi() throws SQLException {
        return DriverManager.getConnection(DUONG_DAN, TAI_KHOAN, MAT_KHAU);
    }
}
