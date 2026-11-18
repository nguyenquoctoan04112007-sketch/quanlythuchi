package thucthe;

public class DanhMuc {
    private int maDanhMuc, maNguoiDung;
    private String tenDanhMuc, loaiDanhMuc, bieuTuong;

    public DanhMuc(int maDanhMuc, int maNguoiDung, String tenDanhMuc, String loaiDanhMuc, String bieuTuong) {
        this.maDanhMuc = maDanhMuc; this.maNguoiDung = maNguoiDung; this.tenDanhMuc = tenDanhMuc;
        this.loaiDanhMuc = loaiDanhMuc; this.bieuTuong = bieuTuong;
    }

    public int getMaDanhMuc() { return maDanhMuc; }
    public int getMaNguoiDung() { return maNguoiDung; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public String getLoaiDanhMuc() { return loaiDanhMuc; }
    public String getBieuTuong() { return bieuTuong; }

    @Override public String toString() { return tenDanhMuc; }
}
