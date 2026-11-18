package app;

import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTenantFrame extends JFrame {

    public UpdateTenantFrame() {
        setTitle("Insert & Update Tenant Info");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel l1 = new JLabel("Flat No (e.g. 1A):");
        l1.setBounds(50, 30, 120, 25);
        panel.add(l1);

        JTextField tFlat = new JTextField();
        tFlat.setBounds(180, 30, 180, 25);
        panel.add(tFlat);

        JLabel l2 = new JLabel("Tenant Name:");
        l2.setBounds(50, 80, 120, 25);
        panel.add(l2);

        JTextField tName = new JTextField();
        tName.setBounds(180, 80, 180, 25);
        panel.add(tName);

        JLabel l3 = new JLabel("Phone Number:");
        l3.setBounds(50, 130, 120, 25);
        panel.add(l3);

        JTextField tPhone = new JTextField();
        tPhone.setBounds(180, 130, 180, 25);
        panel.add(tPhone);

        JLabel l4 = new JLabel("NID Number:");
        l4.setBounds(50, 180, 120, 25);
        panel.add(l4);

        JTextField tNid = new JTextField();
        tNid.setBounds(180, 180, 180, 25);
        panel.add(tNid);

        JLabel l5 = new JLabel("Monthly Rent:");
        l5.setBounds(50, 230, 120, 25);
        panel.add(l5);

        JTextField tRent = new JTextField();
        tRent.setBounds(180, 230, 180, 25);
        panel.add(tRent);

        JButton saveButton = new JButton("Save / Update");
        saveButton.setBounds(180, 290, 180, 35);
        saveButton.setBackground(new Color(180, 235, 180));
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 290, 100, 35);
        backButton.setBackground(new Color(255, 210, 180));
        panel.add(backButton);

        saveButton.addActionListener(e -> {
            String flat = tFlat.getText();
            String name = tName.getText();
            String phone = tPhone.getText();
            String nid = tNid.getText();
            String rentStr = tRent.getText();

            if (flat.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Flat No and Name cannot be empty!");
                return;
            }

            try {
                double rent = Double.parseDouble(rentStr);
                Connection conn = DBConnection.connect();

                String query = "INSERT INTO tenants (flat_no, name, phone, nid, rent) VALUES (?, ?, ?, ?, ?) " +
                               "ON DUPLICATE KEY UPDATE name=?, phone=?, nid=?, rent=?";

                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, flat);
                pst.setString(2, name);
                pst.setString(3, phone);
                pst.setString(4, nid);
                pst.setDouble(5, rent);
                pst.setString(6, name);
                pst.setString(7, phone);
                pst.setString(8, nid);
                pst.setDouble(9, rent);

                pst.executeUpdate();
                conn.close();

                JOptionPane.showMessageDialog(this, "Tenant Information Saved Successfully!");
                dispose();
                new DashboardFrame();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error in input data!");
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });

        add(panel);
        setVisible(true);
    }
}
