package app;

import javax.swing.*;
import java.awt.*;

public class LogoutFrame extends JFrame {

    public LogoutFrame() {
        setTitle("Logout / Exit");
        setSize(420, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel msgLabel = new JLabel("Thank you for using Building Management System!");
        msgLabel.setBounds(45, 40, 350, 30);
        panel.add(msgLabel);

        JButton cancelButton = new JButton("Cancel (Exit)");
        cancelButton.setBounds(45, 100, 140, 35);
        cancelButton.setBackground(new Color(255, 200, 200));
        panel.add(cancelButton);

        JButton logoutButton = new JButton("Login Page");
        logoutButton.setBounds(215, 100, 140, 35);
        logoutButton.setBackground(new Color(173, 216, 230));
        panel.add(logoutButton);

        cancelButton.addActionListener(e -> System.exit(0));

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        add(panel);
        setVisible(true);
    }
}
