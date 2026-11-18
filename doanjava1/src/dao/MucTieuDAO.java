package dao;

import java.sql.Date;
import java.util.List;
import thucthe.MucTieuTietKiem;
import tienich.TruyVan;

public class MucTieuDAO {

    public static List<MucTieuTietKiem> layTheoNguoiDung(int maNguoiDung) {
        return TruyVan.layDanhSach(
            "SELECT ma_muc_tieu, ten_muc_tieu, so_tien_muc_tieu, so_tien_hien_tai, ngay_bat_dau, ngay_ket_thuc, trang_thai " +
            "FROM MucTieuTietKiem WHERE ma_nguoi_dung=? ORDER BY ngay_ket_thuc",
            kq -> new MucTieuTietKiem(kq.getInt(1), maNguoiDung, kq.getString(2), kq.getBigDecimal(3),
                kq.getBigDecimal(4), kq.getDate(5).toLocalDate(), kq.getDate(6).toLocalDate(), kq.getString(7)),
            maNguoiDung);
    }

    public static void them(MucTieuTietKiem mt) {
        TruyVan.capNhat("INSERT INTO MucTieuTietKiem(ma_nguoi_dung, ten_muc_tieu, so_tien_muc_tieu, so_tien_hien_tai, " +
                        "ngay_bat_dau, ngay_ket_thuc, trang_thai) VALUES(?,?,?,?,?,?,?)",
            mt.getMaNguoiDung(), mt.getTenMucTieu(), mt.getSoTienMucTieu(), mt.getSoTienHienTai(),
            Date.valueOf(mt.getNgayBatDau()), Date.valueOf(mt.getNgayKetThuc()), mt.getTrangThai());
    }

    public static void sua(MucTieuTietKiem mt) {
        TruyVan.capNhat("UPDATE MucTieuTietKiem SET ten_muc_tieu=?, so_tien_muc_tieu=?, so_tien_hien_tai=?, " +
                        "ngay_bat_dau=?, ngay_ket_thuc=?, trang_thai=? WHERE ma_muc_tieu=? AND ma_nguoi_dung=?",
            mt.getTenMucTieu(), mt.getSoTienMucTieu(), mt.getSoTienHienTai(),
            Date.valueOf(mt.getNgayBatDau()), Date.valueOf(mt.getNgayKetThuc()), mt.getTrangThai(),
            mt.getMaMucTieu(), mt.getMaNguoiDung());
    }

    public static void xoa(int maMucTieu, int maNguoiDung) {
        TruyVan.capNhat("DELETE FROM MucTieuTietKiem WHERE ma_muc_tieu=? AND ma_nguoi_dung=?", maMucTieu, maNguoiDung);
    }
}
