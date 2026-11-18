package giaodien;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import tienich.PhienDangNhap;

public class KhungChinh extends JFrame {

    public KhungChinh() {
        super("Quản lý thu chi - " + PhienDangNhap.hoTen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        TrangCoSo[] cacTrang = { new TrangViTien(), new TrangDanhMuc(), new TrangGiaoDich(),
                                 new TrangNganSach(), new TrangMucTieu() };
        String[] tenTab = { "Ví tiền", "Danh mục", "Giao dịch", "Ngân sách", "Mục tiêu tiết kiệm" };

        JTabbedPane cacTab = new JTabbedPane();
        for (int i = 0; i < cacTrang.length; i++) cacTab.addTab(tenTab[i], cacTrang[i]);
        cacTab.addChangeListener(sk -> ((TrangCoSo) cacTab.getSelectedComponent()).lamMoi());
        cacTrang[0].lamMoi();

        JButton nutDangXuat = new JButton("Đăng xuất");
        nutDangXuat.addActionListener(sk -> {
            dispose();
            new KhungDangNhap().setVisible(true);
        });
        JPanel phanDau = new JPanel(new BorderLayout());
        phanDau.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        phanDau.add(new JLabel("Xin chào, " + PhienDangNhap.hoTen), BorderLayout.WEST);
        phanDau.add(nutDangXuat, BorderLayout.EAST);

        add(phanDau, BorderLayout.NORTH);
        add(cacTab, BorderLayout.CENTER);
    }
}
