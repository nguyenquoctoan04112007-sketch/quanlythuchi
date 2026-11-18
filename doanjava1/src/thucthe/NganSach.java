package thucthe;

import java.math.BigDecimal;

public class NganSach {
    private int maNganSach, maNguoiDung, maDanhMuc, thang, nam;
    private BigDecimal soTienHanMuc;
    // Thông tin để hiển thị
    private String tenDanhMuc;
    private BigDecimal daChi = BigDecimal.ZERO;

    public NganSach(int maNganSach, int maNguoiDung, int maDanhMuc, BigDecimal soTienHanMuc, int thang, int nam) {
        this.maNganSach = maNganSach; this.maNguoiDung = maNguoiDung; this.maDanhMuc = maDanhMuc;
        this.soTienHanMuc = soTienHanMuc; this.thang = thang; this.nam = nam;
    }

    public void datThongTinHienThi(String tenDanhMuc, BigDecimal daChi) {
        this.tenDanhMuc = tenDanhMuc; this.daChi = daChi;
    }

    public int getMaNganSach() { return maNganSach; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public int getMaDanhMuc() { return maDanhMuc; }
    public BigDecimal getSoTienHanMuc() { return soTienHanMuc; }
    public int getThang() { return thang; }
    public int getNam() { return nam; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public BigDecimal getDaChi() { return daChi; }
    public BigDecimal getConLai() { return soTienHanMuc.subtract(daChi); }
}
