package giaodien;

import dao.DanhMucDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import thucthe.DanhMuc;
import tienich.PhienDangNhap;

public class TrangDanhMuc extends TrangCoSo {
    private final JTextField oTenDanhMuc = new JTextField(14);
    private final JTextField oBieuTuong = new JTextField(8);
    private final JComboBox<String> hopLoai = new JComboBox<>(new String[]{ "THU", "CHI" });

    public TrangDanhMuc() {
        dungBoCuc(hangNhap(new JLabel("Tên danh mục:"), oTenDanhMuc, new JLabel("Loại:"), hopLoai,
            new JLabel("Biểu tượng:"), oBieuTuong,
            nut("Thêm", this::them), nut("Sửa", this::sua), nut("Xóa", this::xoa)));
        bang.getSelectionModel().addListSelectionListener(sk -> {
            if (bang.getSelectedRow() >= 0) {
                oTenDanhMuc.setText(giaTriO(1));
                hopLoai.setSelectedItem(giaTriO(2));
                oBieuTuong.setText(giaTriO(3));
            }
        });
    }

    @Override protected void taiDuLieu() {
        List<Object[]> hang = new ArrayList<>();
        for (DanhMuc dm : DanhMucDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung)) {
            hang.add(new Object[]{ dm.getMaDanhMuc(), dm.getTenDanhMuc(), dm.getLoaiDanhMuc(), dm.getBieuTuong() });
        }
        hienThi(new String[]{ "Mã", "Tên danh mục", "Loại", "Biểu tượng" }, hang);
    }

    private DanhMuc docTuForm(int maDanhMuc) {
        return new DanhMuc(maDanhMuc, PhienDangNhap.maNguoiDung, batBuocNhap(oTenDanhMuc),
            (String) hopLoai.getSelectedItem(), oBieuTuong.getText().trim());
    }

    private void them() {
        DanhMucDAO.them(docTuForm(0));
        lamMoi();
    }

    private void sua() {
        if (!daChonDong()) return;
        DanhMucDAO.sua(docTuForm(layMaDongChon()));
        lamMoi();
    }

    private void xoa() {
        if (!daChonDong()) return;
        try {
            DanhMucDAO.xoa(layMaDongChon(), PhienDangNhap.maNguoiDung);
        } catch (RuntimeException e) {
            thongBao("Không xóa được (danh mục đang được dùng?): " + e.getMessage());
        }
        lamMoi();
    }
}
