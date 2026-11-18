package dao;

import java.math.BigDecimal;
import java.util.List;
import thucthe.NganSach;
import tienich.TruyVan;

public class NganSachDAO {
    public static List<NganSach> layTheoNguoiDung(int maNguoiDung) {
        return TruyVan.layDanhSach(
            "SELECT n.ma_ngan_sach, n.ma_danh_muc, n.so_tien_han_muc, n.thang, n.nam, d.ten_danh_muc, x.da_chi " +
            "FROM NganSach n JOIN DanhMuc d ON n.ma_danh_muc = d.ma_danh_muc " +
            "CROSS APPLY (SELECT ISNULL(SUM(g.so_tien), 0) AS da_chi FROM GiaoDich g " +
            "   WHERE g.ma_danh_muc = n.ma_danh_muc AND g.loai_giao_dich = N'CHI' " +
            "   AND MONTH(g.ngay_giao_dich) = n.thang AND YEAR(g.ngay_giao_dich) = n.nam) x " +
            "WHERE n.ma_nguoi_dung=? ORDER BY n.nam DESC, n.thang DESC",
            kq -> {
                NganSach ns = new NganSach(kq.getInt(1), maNguoiDung, kq.getInt(2), kq.getBigDecimal(3),
                                           kq.getInt(4), kq.getInt(5));
                ns.datThongTinHienThi(kq.getString(6), kq.getBigDecimal(7));
                return ns;
            }, maNguoiDung);
    }

    public static void them(NganSach ns) {
        TruyVan.capNhat("INSERT INTO NganSach(ma_nguoi_dung, ma_danh_muc, so_tien_han_muc, thang, nam) VALUES(?,?,?,?,?)",
            ns.getMaNguoiDung(), ns.getMaDanhMuc(), ns.getSoTienHanMuc(), ns.getThang(), ns.getNam());
    }

    public static void suaHanMuc(int maNganSach, BigDecimal hanMucMoi, int maNguoiDung) {
        TruyVan.capNhat("UPDATE NganSach SET so_tien_han_muc=? WHERE ma_ngan_sach=? AND ma_nguoi_dung=?",
            hanMucMoi, maNganSach, maNguoiDung);
    }

    public static void xoa(int maNganSach, int maNguoiDung) {
        TruyVan.capNhat("DELETE FROM NganSach WHERE ma_ngan_sach=? AND ma_nguoi_dung=?", maNganSach, maNguoiDung);
    }
}
