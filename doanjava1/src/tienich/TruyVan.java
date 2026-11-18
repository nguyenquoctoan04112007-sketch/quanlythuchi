package tienich;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TruyVan {
    public interface AnhXa<T> {
        T anh(ResultSet kq) throws SQLException;
    }

    private static void ganThamSo(PreparedStatement cauLenh, Object... thamSo) throws SQLException {
        for (int i = 0; i < thamSo.length; i++) {
            cauLenh.setObject(i + 1, thamSo[i]);
        }
    }

    public static <T> List<T> layDanhSach(String sql, AnhXa<T> anhXa, Object... thamSo) {
        List<T> danhSach = new ArrayList<>();
        try (Connection kn = KetNoi.layKetNoi(); PreparedStatement cl = kn.prepareStatement(sql)) {
            ganThamSo(cl, thamSo);
            try (ResultSet kq = cl.executeQuery()) {
                while (kq.next()) {
                    danhSach.add(anhXa.anh(kq));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return danhSach;
    }

    public static int capNhat(String sql, Object... thamSo) {
        try (Connection kn = KetNoi.layKetNoi(); PreparedStatement cl = kn.prepareStatement(sql)) {
            ganThamSo(cl, thamSo);
            return cl.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
