package giaodien;

import dao.MucTieuDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import thucthe.MucTieuTietKiem;
import tienich.PhienDangNhap;

public class TrangMucTieu extends TrangCoSo {
    private final JTextField oTenMucTieu = new JTextField(12);
    private final JTextField oSoTienMucTieu = new JTextField(9);
    private final JTextField oSoTienHienTai = new JTextField("0", 9);
    private final JTextField oNgayBatDau = new JTextField(LocalDate.now().toString(), 9);
    private final JTextField oNgayKetThuc = new JTextField(LocalDate.now().plusMonths(6).toString(), 9);
    private final JComboBox<String> hopTrangThai =
        new JComboBox<>(new String[]{ "DANG_THUC_HIEN", "HOAN_THANH", "HUY_BO" });

    public TrangMucTieu() {
        JPanel khuNhap = new JPanel();
        khuNhap.setLayout(new BoxLayout(khuNhap, BoxLayout.Y_AXIS));
        khuNhap.add(hangNhap(new JLabel("Tên mục tiêu:"), oTenMucTieu,
            new JLabel("Số tiền mục tiêu:"), oSoTienMucTieu, new JLabel("Hiện tại:"), oSoTienHienTai));
        khuNhap.add(hangNhap(new JLabel("Bắt đầu:"), oNgayBatDau, new JLabel("Kết thúc:"), oNgayKetThuc,
            new JLabel("Trạng thái:"), hopTrangThai,
            nut("Thêm", this::them), nut("Sửa", this::sua), nut("Xóa", this::xoa)));
        dungBoCuc(khuNhap);
        bang.getSelectionModel().addListSelectionListener(sk -> {
            if (bang.getSelectedRow() >= 0) {
                oTenMucTieu.setText(giaTriO(1));
                oSoTienMucTieu.setText(giaTriO(2));
                oSoTienHienTai.setText(giaTriO(3));
                oNgayBatDau.setText(giaTriO(5));
                oNgayKetThuc.setText(giaTriO(6));
                hopTrangThai.setSelectedItem(giaTriO(7));
            }
        });
    }

    @Override protected void taiDuLieu() {
        List<Object[]> hang = new ArrayList<>();
        for (MucTieuTietKiem m : MucTieuDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung)) {
            hang.add(new Object[]{ m.getMaMucTieu(), m.getTenMucTieu(), m.getSoTienMucTieu(), m.getSoTienHienTai(),
                m.tinhPhanTramHoanThanh(), m.getNgayBatDau().toString(), m.getNgayKetThuc().toString(), m.getTrangThai() });
        }
        hienThi(new String[]{ "Mã", "Mục tiêu", "Số tiền mục tiêu", "Hiện tại", "% hoàn thành",
                              "Bắt đầu", "Kết thúc", "Trạng thái" }, hang);
    }

    private MucTieuTietKiem docTuForm(int maMucTieu) {
        LocalDate batDau = LocalDate.parse(oNgayBatDau.getText().trim());
        LocalDate ketThuc = LocalDate.parse(oNgayKetThuc.getText().trim());
        if (ketThuc.isBefore(batDau)) throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu.");
        return new MucTieuTietKiem(maMucTieu, PhienDangNhap.maNguoiDung, batBuocNhap(oTenMucTieu),
            docTien(oSoTienMucTieu.getText()), docTien(oSoTienHienTai.getText()),
            batDau, ketThuc, (String) hopTrangThai.getSelectedItem());
    }

    private void them() {
        MucTieuDAO.them(docTuForm(0));
        lamMoi();
    }

    private void sua() {
        if (!daChonDong()) return;
        MucTieuDAO.sua(docTuForm(layMaDongChon()));
        lamMoi();
    }

    private void xoa() {
        if (!daChonDong()) return;
        MucTieuDAO.xoa(layMaDongChon(), PhienDangNhap.maNguoiDung);
        lamMoi();
    }
}
