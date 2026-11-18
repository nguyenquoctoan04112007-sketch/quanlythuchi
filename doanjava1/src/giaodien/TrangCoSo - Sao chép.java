package giaodien;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
public abstract class TrangCoSo extends JPanel {

    public interface HanhDong {
        void thucHien() throws Exception;
    }

    protected final JTable bang = new JTable();
    protected abstract void taiDuLieu();

    public void lamMoi() {
        try {
            taiDuLieu();
        } catch (Exception e) {
            thongBao("Lỗi tải dữ liệu: " + e.getMessage());
        }
    }
    protected void hienThi(String[] cot, List<Object[]> hang) {
        DefaultTableModel moHinh = new DefaultTableModel(cot, 0) {
            @Override public boolean isCellEditable(int dong, int c) { return false; }
        };
        for (Object[] h : hang) moHinh.addRow(h);
        bang.setModel(moHinh);
        bang.setRowHeight(24);
    }

    protected String giaTriO(int cot) {
        int dong = bang.getSelectedRow();
        return dong < 0 ? "" : Objects.toString(bang.getValueAt(dong, cot), "");
    }

    protected int layMaDongChon() {
        int dong = bang.getSelectedRow();
        return dong < 0 ? -1 : Integer.parseInt(bang.getValueAt(dong, 0).toString());
    }

    protected boolean daChonDong() {
        if (layMaDongChon() < 0) {
            thongBao("Hãy chọn một dòng trong bảng.");
            return false;
        }
        return true;
    }

    protected void thongBao(String noiDung) {
        JOptionPane.showMessageDialog(this, noiDung);
    }

    protected String batBuocNhap(JTextField o) {
        String s = o.getText().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Không được để trống ô nhập.");
        return s;
    }

    protected BigDecimal docTien(String chuoi) {
        return new BigDecimal(chuoi.trim().replace(",", ""));
    }

    protected JButton nut(String ten, HanhDong hanhDong) {
        JButton n = new JButton(ten);
        n.addActionListener(suKien -> {
            try {
                hanhDong.thucHien();
            } catch (Exception e) {
                thongBao("Lỗi: " + e.getMessage());
            }
        });
        return n;
    }

    protected JPanel hangNhap(Component... thanhPhan) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        for (Component c : thanhPhan) p.add(c);
        return p;
    }

    protected void dungBoCuc(JPanel khuNhap) {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(new JScrollPane(bang), BorderLayout.CENTER);
        add(khuNhap, BorderLayout.SOUTH);
    }
}
