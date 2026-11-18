package app;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("House Rent & Utility Management System");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel titleLabel = new JLabel("Dashboard Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(150, 20, 200, 30);
        panel.add(titleLabel);

        JButton b1 = new JButton("1. Tenant All Info");
        b1.setBounds(90, 70, 260, 40);
        b1.setBackground(new Color(173, 216, 230));
        panel.add(b1);

        JButton b2 = new JButton("2. Insert & Update Tenant");
        b2.setBounds(90, 130, 260, 40);
        b2.setBackground(new Color(180, 235, 180));
        panel.add(b2);

        JButton b3 = new JButton("3. 12-Month Rent Sheet");
        b3.setBounds(90, 190, 260, 40);
        b3.setBackground(new Color(255, 245, 190));
        panel.add(b3);

        JButton b4 = new JButton("4. Logout");
        b4.setBounds(90, 250, 260, 40);
        b4.setBackground(new Color(255, 210, 180));
        panel.add(b4);

        b1.addActionListener(e -> {
            dispose();
            new TenantInfoFrame();
        });

        b2.addActionListener(e -> {
            dispose();
            new UpdateTenantFrame();
        });

        b3.addActionListener(e -> {
            dispose();
            new BillSheetFrame();
        });

        b4.addActionListener(e -> {
            dispose();
            new LogoutFrame();
        });

        add(panel);
        setVisible(true);
    }
}
