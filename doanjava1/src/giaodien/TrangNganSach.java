package giaodien;

import dao.DanhMucDAO;
import dao.NganSachDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import thucthe.DanhMuc;
import thucthe.NganSach;
import tienich.PhienDangNhap;

public class TrangNganSach extends TrangCoSo {
    private final JComboBox<DanhMuc> hopDanhMuc = new JComboBox<>();
    private final JTextField oHanMuc = new JTextField(10);
    private final JSpinner oThang = new JSpinner(new SpinnerNumberModel(LocalDate.now().getMonthValue(), 1, 12, 1));
    private final JTextField oNam = new JTextField(String.valueOf(LocalDate.now().getYear()), 5);

    public TrangNganSach() {
        dungBoCuc(hangNhap(new JLabel("Danh mục chi:"), hopDanhMuc, new JLabel("Hạn mức:"), oHanMuc,
            new JLabel("Tháng:"), oThang, new JLabel("Năm:"), oNam,
            nut("Thêm", this::them), nut("Sửa hạn mức", this::sua), nut("Xóa", this::xoa)));
        bang.getSelectionModel().addListSelectionListener(sk -> {
            if (bang.getSelectedRow() >= 0) oHanMuc.setText(giaTriO(2));
        });
    }

    @Override protected void taiDuLieu() {
        List<DanhMuc> danhMucChi = DanhMucDAO.layTheoLoai(PhienDangNhap.maNguoiDung, "CHI");
        hopDanhMuc.setModel(new DefaultComboBoxModel<>(danhMucChi.toArray(new DanhMuc[0])));

        List<Object[]> hang = new ArrayList<>();
        for (NganSach n : NganSachDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung)) {
            hang.add(new Object[]{ n.getMaNganSach(), n.getTenDanhMuc(), n.getSoTienHanMuc(),
                n.getThang(), n.getNam(), n.getDaChi(), n.getConLai() });
        }
        hienThi(new String[]{ "Mã", "Danh mục", "Hạn mức", "Tháng", "Năm", "Đã chi", "Còn lại" }, hang);
    }

    private void them() {
        DanhMuc dm = (DanhMuc) hopDanhMuc.getSelectedItem();
        if (dm == null) { thongBao("Chưa có danh mục CHI."); return; }
        NganSachDAO.them(new NganSach(0, PhienDangNhap.maNguoiDung, dm.getMaDanhMuc(),
            docTien(oHanMuc.getText()), (Integer) oThang.getValue(), Integer.parseInt(oNam.getText().trim())));
        lamMoi();
    }

    private void sua() {
        if (!daChonDong()) return;
        NganSachDAO.suaHanMuc(layMaDongChon(), docTien(oHanMuc.getText()), PhienDangNhap.maNguoiDung);
        lamMoi();
    }

    private void xoa() {
        if (!daChonDong()) return;
        NganSachDAO.xoa(layMaDongChon(), PhienDangNhap.maNguoiDung);
        lamMoi();
    }
}
