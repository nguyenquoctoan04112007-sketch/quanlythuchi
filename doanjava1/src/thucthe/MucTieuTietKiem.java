package thucthe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class MucTieuTietKiem {
    private int maMucTieu, maNguoiDung;
    private String tenMucTieu, trangThai;   // DANG_THUC_HIEN / HOAN_THANH / HUY_BO
    private BigDecimal soTienMucTieu, soTienHienTai;
    private LocalDate ngayBatDau, ngayKetThuc;

    public MucTieuTietKiem(int maMucTieu, int maNguoiDung, String tenMucTieu, BigDecimal soTienMucTieu,
                           BigDecimal soTienHienTai, LocalDate ngayBatDau, LocalDate ngayKetThuc, String trangThai) {
        this.maMucTieu = maMucTieu; this.maNguoiDung = maNguoiDung; this.tenMucTieu = tenMucTieu;
        this.soTienMucTieu = soTienMucTieu; this.soTienHienTai = soTienHienTai;
        this.ngayBatDau = ngayBatDau; this.ngayKetThuc = ngayKetThuc; this.trangThai = trangThai;
    }

    public BigDecimal tinhPhanTramHoanThanh() {
        return soTienHienTai.multiply(BigDecimal.valueOf(100)).divide(soTienMucTieu, 1, RoundingMode.HALF_UP);
    }

    public int getMaMucTieu() { return maMucTieu; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public String getTenMucTieu() { return tenMucTieu; }
    public BigDecimal getSoTienMucTieu() { return soTienMucTieu; }
    public BigDecimal getSoTienHienTai() { return soTienHienTai; }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public String getTrangThai() { return trangThai; }
}
