package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import thucthe.DanhMuc;
import tienich.TruyVan;

public class DanhMucDAO {

    private static final String CAU_CHON =
        "SELECT ma_danh_muc, ma_nguoi_dung, ten_danh_muc, loai_danh_muc, bieu_tuong FROM DanhMuc ";

    private static DanhMuc anh(ResultSet kq) throws SQLException {
        return new DanhMuc(kq.getInt(1), kq.getInt(2), kq.getString(3), kq.getString(4), kq.getString(5));
    }

    public static List<DanhMuc> layTheoNguoiDung(int maNguoiDung) {
        return TruyVan.layDanhSach(CAU_CHON + "WHERE ma_nguoi_dung=? ORDER BY loai_danh_muc, ten_danh_muc",
            DanhMucDAO::anh, maNguoiDung);
    }


    public static List<DanhMuc> layTheoLoai(int maNguoiDung, String loai) {
        return TruyVan.layDanhSach(CAU_CHON + "WHERE ma_nguoi_dung=? AND loai_danh_muc=? ORDER BY ten_danh_muc",
            DanhMucDAO::anh, maNguoiDung, loai);
    }

    public static void them(DanhMuc dm) {
        TruyVan.capNhat("INSERT INTO DanhMuc(ma_nguoi_dung, ten_danh_muc, loai_danh_muc, bieu_tuong) VALUES(?,?,?,?)",
            dm.getMaNguoiDung(), dm.getTenDanhMuc(), dm.getLoaiDanhMuc(), dm.getBieuTuong());
    }

    public static void sua(DanhMuc dm) {
        TruyVan.capNhat("UPDATE DanhMuc SET ten_danh_muc=?, loai_danh_muc=?, bieu_tuong=? WHERE ma_danh_muc=? AND ma_nguoi_dung=?",
            dm.getTenDanhMuc(), dm.getLoaiDanhMuc(), dm.getBieuTuong(), dm.getMaDanhMuc(), dm.getMaNguoiDung());
    }

    public static void xoa(int maDanhMuc, int maNguoiDung) {
        TruyVan.capNhat("DELETE FROM DanhMuc WHERE ma_danh_muc=? AND ma_nguoi_dung=?", maDanhMuc, maNguoiDung);
    }
}
