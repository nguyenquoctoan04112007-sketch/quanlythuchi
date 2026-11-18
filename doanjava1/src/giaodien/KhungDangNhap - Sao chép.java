package giaodien;

import dao.NguoiDungDAO;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import thucthe.NguoiDung;
import tienich.MaHoa;
import tienich.PhienDangNhap;

public class KhungDangNhap extends JFrame {
    private final JTextField oTenDangNhap = new JTextField(15);
    private final JPasswordField oMatKhau = new JPasswordField(15);

    public KhungDangNhap() {
        super("Quản lý thu chi - Đăng nhập");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel tieuDe = new JLabel("QUẢN LÝ THU CHI", SwingConstants.CENTER);
        tieuDe.setFont(tieuDe.getFont().deriveFont(Font.BOLD, 20f));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2; p.add(tieuDe, g);
        g.gridwidth = 1;
        g.gridy = 1; p.add(new JLabel("Tên đăng nhập:"), g); g.gridx = 1; p.add(oTenDangNhap, g);
        g.gridx = 0; g.gridy = 2; p.add(new JLabel("Mật khẩu:"), g); g.gridx = 1; p.add(oMatKhau, g);

        JButton nutDangNhap = new JButton("Đăng nhập");
        JButton nutDangKy = new JButton("Đăng ký");
        JPanel khuNut = new JPanel();
        khuNut.add(nutDangNhap);
        khuNut.add(nutDangKy);
        g.gridx = 0; g.gridy = 3; g.gridwidth = 2; p.add(khuNut, g);

        nutDangNhap.addActionListener(sk -> dangNhap());
        nutDangKy.addActionListener(sk -> dangKy());
        getRootPane().setDefaultButton(nutDangNhap);
        setContentPane(p);
        pack();
        setLocationRelativeTo(null);
    }

    private void dangNhap() {
        try {
            NguoiDung nd = NguoiDungDAO.dangNhap(oTenDangNhap.getText().trim(),
                MaHoa.bamSha256(new String(oMatKhau.getPassword())));
            if (nd == null) {
                JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu.");
                return;
            }
            if (!nd.isTrangThai()) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã bị khóa.");
                return;
            }
            PhienDangNhap.maNguoiDung = nd.getMaNguoiDung();
            PhienDangNhap.hoTen = nd.getHoTen();
            new KhungChinh().setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không kết nối được CSDL: " + e.getMessage());
        }
    }

    private void dangKy() {
        JTextField oTen = new JTextField(15), oHoTen = new JTextField(15);
        JTextField oEmail = new JTextField(15), oSdt = new JTextField(15);
        JPasswordField oMk = new JPasswordField(15);
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        p.add(new JLabel("Tên đăng nhập:")); p.add(oTen);
        p.add(new JLabel("Mật khẩu:")); p.add(oMk);
        p.add(new JLabel("Họ tên:")); p.add(oHoTen);
        p.add(new JLabel("Email:")); p.add(oEmail);
        p.add(new JLabel("Số điện thoại:")); p.add(oSdt);
        if (JOptionPane.showConfirmDialog(this, p, "Đăng ký", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            String matKhau = new String(oMk.getPassword());
            if (oTen.getText().isBlank() || matKhau.isEmpty() || oHoTen.getText().isBlank() || oEmail.getText().isBlank()) {
                throw new IllegalArgumentException("Vui lòng nhập đủ thông tin bắt buộc.");
            }
            NguoiDungDAO.dangKy(new NguoiDung(0, oTen.getText().trim(), MaHoa.bamSha256(matKhau),
                oHoTen.getText().trim(), oEmail.getText().trim(), oSdt.getText().trim(), true));
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KhungDangNhap().setVisible(true));
    }
}
