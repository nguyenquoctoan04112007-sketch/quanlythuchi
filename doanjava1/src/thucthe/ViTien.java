package thucthe;

import java.math.BigDecimal;

public class ViTien {
    private int maVi, maNguoiDung;
    private String tenVi, donViTienTe, bieuTuong;
    private BigDecimal soDu;

    public ViTien(int maVi, int maNguoiDung, String tenVi, BigDecimal soDu, String donViTienTe, String bieuTuong) {
        this.maVi = maVi; this.maNguoiDung = maNguoiDung; this.tenVi = tenVi;
        this.soDu = soDu; this.donViTienTe = donViTienTe; this.bieuTuong = bieuTuong;
    }

    public int getMaVi() { return maVi; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public String getTenVi() { return tenVi; }
    public BigDecimal getSoDu() { return soDu; }
    public String getDonViTienTe() { return donViTienTe; }
    public String getBieuTuong() { return bieuTuong; }

    @Override public String toString() { return tenVi; }
}
