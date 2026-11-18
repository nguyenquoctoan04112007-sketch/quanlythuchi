package giaodien;

import dao.DanhMucDAO;
import dao.GiaoDichDAO;
import dao.ViTienDAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import thucthe.DanhMuc;
import thucthe.GiaoDich;
import thucthe.ViTien;
import tienich.PhienDangNhap;

public class TrangGiaoDich extends TrangCoSo {
    private final JComboBox<String> hopLoai = new JComboBox<>(new String[]{ "THU", "CHI", "CHUYEN_TIEN" });
    private final JComboBox<ViTien> hopVi = new JComboBox<>();
    private final JComboBox<ViTien> hopViDich = new JComboBox<>();
    private final JComboBox<DanhMuc> hopDanhMuc = new JComboBox<>();
    private final JTextField oSoTien = new JTextField(9);
    private final JTextField oNgay = new JTextField(LocalDate.now().toString(), 9);
    private final JTextField oGhiChu = new JTextField(16);

    public TrangGiaoDich() {
        JPanel khuNhap = new JPanel();
        khuNhap.setLayout(new BoxLayout(khuNhap, BoxLayout.Y_AXIS));
        khuNhap.add(hangNhap(new JLabel("Loại:"), hopLoai, new JLabel("Ví:"), hopVi,
            new JLabel("Ví đích:"), hopViDich, new JLabel("Danh mục:"), hopDanhMuc));
        khuNhap.add(hangNhap(new JLabel("Số tiền:"), oSoTien, new JLabel("Ngày (yyyy-MM-dd):"), oNgay,
            new JLabel("Ghi chú:"), oGhiChu,
            nut("Thêm giao dịch", this::them), nut("Xóa giao dịch", this::xoa)));
        dungBoCuc(khuNhap);
        hopLoai.addActionListener(sk -> doiLoai());
    }
    private void doiLoai() {
        boolean chuyenTien = "CHUYEN_TIEN".equals(hopLoai.getSelectedItem());
        hopDanhMuc.setEnabled(!chuyenTien);
        hopViDich.setEnabled(chuyenTien);
        if (!chuyenTien) {
            List<DanhMuc> ds = DanhMucDAO.layTheoLoai(PhienDangNhap.maNguoiDung, (String) hopLoai.getSelectedItem());
            hopDanhMuc.setModel(new DefaultComboBoxModel<>(ds.toArray(new DanhMuc[0])));
        }
    }

    @Override protected void taiDuLieu() {
        ViTien[] cacVi = ViTienDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung).toArray(new ViTien[0]);
        hopVi.setModel(new DefaultComboBoxModel<>(cacVi));
        hopViDich.setModel(new DefaultComboBoxModel<>(cacVi));
        doiLoai();

        List<Object[]> hang = new ArrayList<>();
        for (GiaoDich g : GiaoDichDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung)) {
            hang.add(new Object[]{ g.getMaGiaoDich(), g.getNgayGiaoDich().toString(), g.getLoaiGiaoDich(),
                g.getTenVi(), g.getTenDanhMuc(), g.getSoTien(), g.getTenViDich(), g.getGhiChu() });
        }
        hienThi(new String[]{ "Mã", "Ngày", "Loại", "Ví", "Danh mục", "Số tiền", "Ví đích", "Ghi chú" }, hang);
    }

    private void them() {
        ViTien vi = (ViTien) hopVi.getSelectedItem();
        if (vi == null) { thongBao("Bạn chưa có ví nào."); return; }

        String loai = (String) hopLoai.getSelectedItem();
        BigDecimal soTien = docTien(oSoTien.getText());
        if (soTien.signum() <= 0) throw new IllegalArgumentException("Số tiền phải lớn hơn 0.");

        Integer maDanhMuc = null, maViDich = null;
        if ("CHUYEN_TIEN".equals(loai)) {
            ViTien dich = (ViTien) hopViDich.getSelectedItem();
            if (dich == null || dich.getMaVi() == vi.getMaVi()) {
                thongBao("Hãy chọn ví đích khác ví nguồn.");
                return;
            }
            maViDich = dich.getMaVi();
        } else {
            DanhMuc dm = (DanhMuc) hopDanhMuc.getSelectedItem();
            if (dm == null) { thongBao("Chưa có danh mục " + loai + ". Hãy tạo ở tab Danh mục."); return; }
            maDanhMuc = dm.getMaDanhMuc();
        }

        GiaoDich g = new GiaoDich(0, PhienDangNhap.maNguoiDung, vi.getMaVi(), maDanhMuc, soTien, loai,
            maViDich, LocalDate.parse(oNgay.getText().trim()), oGhiChu.getText().trim());
        GiaoDichDAO.them(g);
        oSoTien.setText("");
        oGhiChu.setText("");
        lamMoi();
    }

    private void xoa() {
        if (!daChonDong()) return;
        GiaoDichDAO.xoa(layMaDongChon(), PhienDangNhap.maNguoiDung);
        lamMoi();
    }
}
