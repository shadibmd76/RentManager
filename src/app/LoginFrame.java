package app;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    static String adminPassword = "1234";

    public LoginFrame() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel l1 = new JLabel("User Name");
        l1.setBounds(50, 50, 100, 30);
        panel.add(l1);

        JTextField t1 = new JTextField();
        t1.setBounds(150, 50, 180, 30);
        panel.add(t1);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(50, 100, 100, 30);
        panel.add(l2);

        JPasswordField p = new JPasswordField();
        p.setBounds(150, 100, 180, 30);
        panel.add(p);

        JButton b = new JButton("Login");
        b.setBounds(150, 160, 100, 35);
        b.setBackground(new Color(173, 216, 230));
        panel.add(b);

        b.addActionListener(e -> {
            String username = t1.getText();
            String password = new String(p.getPassword());

            if (username.equals("admin") && password.equals(adminPassword)) {
                dispose();
                new DashboardFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Wrong Username or Password");
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
