package thucthe;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GiaoDich {
    private int maGiaoDich, maNguoiDung, maVi;
    private Integer maDanhMuc, maViDich;
    private BigDecimal soTien;
    private String loaiGiaoDich, ghiChu;
    private LocalDate ngayGiaoDich;

    private String tenVi, tenDanhMuc, tenViDich;

    public GiaoDich(int maGiaoDich, int maNguoiDung, int maVi, Integer maDanhMuc, BigDecimal soTien,
                    String loaiGiaoDich, Integer maViDich, LocalDate ngayGiaoDich, String ghiChu) {
        this.maGiaoDich = maGiaoDich; this.maNguoiDung = maNguoiDung; this.maVi = maVi;
        this.maDanhMuc = maDanhMuc; this.soTien = soTien; this.loaiGiaoDich = loaiGiaoDich;
        this.maViDich = maViDich; this.ngayGiaoDich = ngayGiaoDich; this.ghiChu = ghiChu;
    }

    public void datTenHienThi(String tenVi, String tenDanhMuc, String tenViDich) {
        this.tenVi = tenVi; this.tenDanhMuc = tenDanhMuc; this.tenViDich = tenViDich;
    }

    public int getMaGiaoDich() { return maGiaoDich; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public int getMaVi() { return maVi; }
    public Integer getMaDanhMuc() { return maDanhMuc; }
    public Integer getMaViDich() { return maViDich; }
    public BigDecimal getSoTien() { return soTien; }
    public String getLoaiGiaoDich() { return loaiGiaoDich; }
    public String getGhiChu() { return ghiChu; }
    public LocalDate getNgayGiaoDich() { return ngayGiaoDich; }
    public String getTenVi() { return tenVi; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public String getTenViDich() { return tenViDich; }
}
