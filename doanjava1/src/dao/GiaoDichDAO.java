package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import thucthe.GiaoDich;
import tienich.KetNoi;
import tienich.TruyVan;

public class GiaoDichDAO {

    public static List<GiaoDich> layTheoNguoiDung(int maNguoiDung) {
        return TruyVan.layDanhSach(
            "SELECT g.ma_giao_dich, g.ma_vi, g.ma_danh_muc, g.so_tien, g.loai_giao_dich, g.ma_vi_dich, " +
            "g.ngay_giao_dich, g.ghi_chu, v.ten_vi, d.ten_danh_muc, vd.ten_vi " +
            "FROM GiaoDich g JOIN ViTien v ON g.ma_vi = v.ma_vi " +
            "LEFT JOIN DanhMuc d ON g.ma_danh_muc = d.ma_danh_muc " +
            "LEFT JOIN ViTien vd ON g.ma_vi_dich = vd.ma_vi " +
            "WHERE g.ma_nguoi_dung=? ORDER BY g.ngay_giao_dich DESC, g.ma_giao_dich DESC",
            kq -> {
                GiaoDich gd = new GiaoDich(kq.getInt(1), maNguoiDung, kq.getInt(2), (Integer) kq.getObject(3),
                    kq.getBigDecimal(4), kq.getString(5), (Integer) kq.getObject(6),
                    kq.getTimestamp(7).toLocalDateTime().toLocalDate(), kq.getString(8));
                gd.datTenHienThi(kq.getString(9), kq.getString(10), kq.getString(11));
                return gd;
            }, maNguoiDung);
    }
    public static void them(GiaoDich gd) {
        try (Connection kn = KetNoi.layKetNoi()) {
            kn.setAutoCommit(false);
            try {
                if (!"THU".equals(gd.getLoaiGiaoDich())) {
                    BigDecimal soDu = ViTienDAO.laySoDu(kn, gd.getMaVi());
                    if (soDu.compareTo(gd.getSoTien()) < 0) {
                        throw new IllegalStateException("Số dư ví không đủ.");
                    }
                }
                try (PreparedStatement cl = kn.prepareStatement(
                        "INSERT INTO GiaoDich(ma_nguoi_dung, ma_vi, ma_danh_muc, so_tien, loai_giao_dich, " +
                        "ma_vi_dich, ngay_giao_dich, ghi_chu) VALUES(?,?,?,?,?,?,?,?)")) {
                    cl.setInt(1, gd.getMaNguoiDung());
                    cl.setInt(2, gd.getMaVi());
                    cl.setObject(3, gd.getMaDanhMuc());
                    cl.setBigDecimal(4, gd.getSoTien());
                    cl.setString(5, gd.getLoaiGiaoDich());
                    cl.setObject(6, gd.getMaViDich());
                    cl.setTimestamp(7, Timestamp.valueOf(gd.getNgayGiaoDich().atStartOfDay()));
                    cl.setString(8, gd.getGhiChu());
                    cl.executeUpdate();
                }
                apDungSoDu(kn, gd.getLoaiGiaoDich(), gd.getMaVi(), gd.getMaViDich(), gd.getSoTien(), 1);
                kn.commit();
            } catch (Exception e) {
                kn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public static void xoa(int maGiaoDich, int maNguoiDung) {
        try (Connection kn = KetNoi.layKetNoi()) {
            kn.setAutoCommit(false);
            try {
                int maVi;
                String loai;
                BigDecimal soTien;
                Integer maViDich;
                try (PreparedStatement cl = kn.prepareStatement(
                        "SELECT ma_vi, loai_giao_dich, so_tien, ma_vi_dich FROM GiaoDich " +
                        "WHERE ma_giao_dich=? AND ma_nguoi_dung=?")) {
                    cl.setInt(1, maGiaoDich);
                    cl.setInt(2, maNguoiDung);
                    try (ResultSet kq = cl.executeQuery()) {
                        if (!kq.next()) throw new IllegalStateException("Không tìm thấy giao dịch.");
                        maVi = kq.getInt(1);
                        loai = kq.getString(2);
                        soTien = kq.getBigDecimal(3);
                        maViDich = (Integer) kq.getObject(4);
                    }
                }
                try (PreparedStatement cl = kn.prepareStatement("DELETE FROM GiaoDich WHERE ma_giao_dich=?")) {
                    cl.setInt(1, maGiaoDich);
                    cl.executeUpdate();
                }
                apDungSoDu(kn, loai, maVi, maViDich, soTien, -1);
                kn.commit();
            } catch (Exception e) {
                kn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    private static void apDungSoDu(Connection kn, String loai, int maVi, Integer maViDich,
                                   BigDecimal soTien, int chieu) throws SQLException {
        BigDecimal d = soTien.multiply(BigDecimal.valueOf(chieu));
        if ("THU".equals(loai)) {
            ViTienDAO.doiSoDu(kn, maVi, d);
        } else if ("CHI".equals(loai)) {
            ViTienDAO.doiSoDu(kn, maVi, d.negate());
        } else {
            ViTienDAO.doiSoDu(kn, maVi, d.negate());
            ViTienDAO.doiSoDu(kn, maViDich, d);
        }
    }
}
