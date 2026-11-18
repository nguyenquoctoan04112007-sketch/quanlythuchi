package giaodien;

import dao.ViTienDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import thucthe.ViTien;
import tienich.PhienDangNhap;

public class TrangViTien extends TrangCoSo {
    private final JTextField oTenVi = new JTextField(12);
    private final JTextField oSoDu = new JTextField("0", 9);
    private final JTextField oDonVi = new JTextField("VNĐ", 5);

    public TrangViTien() {
        dungBoCuc(hangNhap(new JLabel("Tên ví:"), oTenVi, new JLabel("Số dư:"), oSoDu,
            new JLabel("Đơn vị:"), oDonVi,
            nut("Thêm", this::them), nut("Sửa", this::sua), nut("Xóa", this::xoa)));
        bang.getSelectionModel().addListSelectionListener(sk -> {
            if (bang.getSelectedRow() >= 0) {
                oTenVi.setText(giaTriO(1));
                oSoDu.setText(giaTriO(2));
                oDonVi.setText(giaTriO(3));
            }
        });
    }

    @Override protected void taiDuLieu() {
        List<Object[]> hang = new ArrayList<>();
        for (ViTien v : ViTienDAO.layTheoNguoiDung(PhienDangNhap.maNguoiDung)) {
            hang.add(new Object[]{ v.getMaVi(), v.getTenVi(), v.getSoDu(), v.getDonViTienTe() });
        }
        hienThi(new String[]{ "Mã", "Tên ví", "Số dư", "Đơn vị" }, hang);
    }

    private ViTien docTuForm(int maVi) {
        return new ViTien(maVi, PhienDangNhap.maNguoiDung, batBuocNhap(oTenVi),
            docTien(oSoDu.getText()), oDonVi.getText().trim(), null);
    }

    private void them() {
        ViTienDAO.them(docTuForm(0));
        lamMoi();
    }

    private void sua() {
        if (!daChonDong()) return;
        ViTienDAO.sua(docTuForm(layMaDongChon()));
        lamMoi();
    }

    private void xoa() {
        if (!daChonDong()) return;
        if (JOptionPane.showConfirmDialog(this, "Xóa ví này?", "Xác nhận", JOptionPane.YES_NO_OPTION)
                != JOptionPane.YES_OPTION) return;
        try {
            ViTienDAO.xoa(layMaDongChon(), PhienDangNhap.maNguoiDung);
        } catch (RuntimeException e) {
            thongBao("Không xóa được (ví đã có giao dịch?): " + e.getMessage());
        }
        lamMoi();
    }
}
