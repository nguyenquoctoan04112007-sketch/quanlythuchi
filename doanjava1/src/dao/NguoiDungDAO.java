package dao;

import java.util.List;
import thucthe.NguoiDung;
import tienich.TruyVan;

public class NguoiDungDAO {

    public static NguoiDung dangNhap(String tenDangNhap, String matKhauDaMaHoa) {
        List<NguoiDung> ds = TruyVan.layDanhSach(
            "SELECT ma_nguoi_dung, ten_dang_nhap, ho_ten, thu_dien_tu, so_dien_thoai, trang_thai " +
            "FROM NguoiDung WHERE ten_dang_nhap=? AND mat_khau=?",
            kq -> new NguoiDung(kq.getInt(1), kq.getString(2), null, kq.getString(3),
                                kq.getString(4), kq.getString(5), kq.getBoolean(6)),
            tenDangNhap, matKhauDaMaHoa);
        return ds.isEmpty() ? null : ds.get(0);
    }

    public static void dangKy(NguoiDung nd) {
        TruyVan.capNhat("INSERT INTO NguoiDung(ten_dang_nhap, mat_khau, ho_ten, thu_dien_tu, so_dien_thoai) VALUES(?,?,?,?,?)",
            nd.getTenDangNhap(), nd.getMatKhau(), nd.getHoTen(), nd.getThuDienTu(), nd.getSoDienThoai());
        TruyVan.capNhat("IF NOT EXISTS (SELECT 1 FROM VaiTro WHERE ten_vai_tro=N'USER') " +
                        "INSERT INTO VaiTro(ten_vai_tro) VALUES(N'USER')");
        TruyVan.capNhat("INSERT INTO NguoiDung_VaiTro(ma_nguoi_dung, ma_vai_tro) " +
                        "SELECT n.ma_nguoi_dung, r.ma_vai_tro FROM NguoiDung n, VaiTro r " +
                        "WHERE n.ten_dang_nhap=? AND r.ten_vai_tro=N'USER'", nd.getTenDangNhap());
        TruyVan.capNhat("INSERT INTO ViTien(ma_nguoi_dung, ten_vi) " +
                        "SELECT ma_nguoi_dung, N'Tiền mặt' FROM NguoiDung WHERE ten_dang_nhap=?", nd.getTenDangNhap());
    }
}
