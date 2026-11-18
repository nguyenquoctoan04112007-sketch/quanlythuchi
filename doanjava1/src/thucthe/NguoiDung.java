package thucthe;

public class NguoiDung {
    private int maNguoiDung;
    private String tenDangNhap, matKhau, hoTen, thuDienTu, soDienThoai;
    private boolean trangThai;

    public NguoiDung(int maNguoiDung, String tenDangNhap, String matKhau, String hoTen,
                     String thuDienTu, String soDienThoai, boolean trangThai) {
        this.maNguoiDung = maNguoiDung; this.tenDangNhap = tenDangNhap; this.matKhau = matKhau;
        this.hoTen = hoTen; this.thuDienTu = thuDienTu; this.soDienThoai = soDienThoai; this.trangThai = trangThai;
    }

    public int getMaNguoiDung() { return maNguoiDung; }
    public String getTenDangNhap() { return tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public String getHoTen() { return hoTen; }
    public String getThuDienTu() { return thuDienTu; }
    public String getSoDienThoai() { return soDienThoai; }
    public boolean isTrangThai() { return trangThai; }
}
