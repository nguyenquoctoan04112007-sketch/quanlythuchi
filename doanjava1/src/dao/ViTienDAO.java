package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import thucthe.ViTien;
import tienich.TruyVan;

public class ViTienDAO {

    public static List<ViTien> layTheoNguoiDung(int maNguoiDung) {
        return TruyVan.layDanhSach(
            "SELECT ma_vi, ma_nguoi_dung, ten_vi, so_du, don_vi_tien_te, bieu_tuong FROM ViTien " +
            "WHERE ma_nguoi_dung=? ORDER BY ma_vi",
            kq -> new ViTien(kq.getInt(1), kq.getInt(2), kq.getString(3), kq.getBigDecimal(4),
                             kq.getString(5), kq.getString(6)),
            maNguoiDung);
    }

    public static void them(ViTien vi) {
        TruyVan.capNhat("INSERT INTO ViTien(ma_nguoi_dung, ten_vi, so_du, don_vi_tien_te, bieu_tuong) VALUES(?,?,?,?,?)",
            vi.getMaNguoiDung(), vi.getTenVi(), vi.getSoDu(), vi.getDonViTienTe(), vi.getBieuTuong());
    }

    public static void sua(ViTien vi) {
        TruyVan.capNhat("UPDATE ViTien SET ten_vi=?, so_du=?, don_vi_tien_te=? WHERE ma_vi=? AND ma_nguoi_dung=?",
            vi.getTenVi(), vi.getSoDu(), vi.getDonViTienTe(), vi.getMaVi(), vi.getMaNguoiDung());
    }

    public static void xoa(int maVi, int maNguoiDung) {
        TruyVan.capNhat("DELETE FROM ViTien WHERE ma_vi=? AND ma_nguoi_dung=?", maVi, maNguoiDung);
    }

    // ===== Hàm dùng chung kết nối với giao dịch (transaction) =====

    public static BigDecimal laySoDu(Connection kn, int maVi) throws SQLException {
        try (PreparedStatement cl = kn.prepareStatement("SELECT so_du FROM ViTien WHERE ma_vi=?")) {
            cl.setInt(1, maVi);
            try (ResultSet kq = cl.executeQuery()) {
                kq.next();
                return kq.getBigDecimal(1);
            }
        }
    }

    public static void doiSoDu(Connection kn, int maVi, BigDecimal chenhLech) throws SQLException {
        try (PreparedStatement cl = kn.prepareStatement("UPDATE ViTien SET so_du = so_du + ? WHERE ma_vi=?")) {
            cl.setBigDecimal(1, chenhLech);
            cl.setInt(2, maVi);
            cl.executeUpdate();
        }
    }
}
