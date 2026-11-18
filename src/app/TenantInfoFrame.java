package app;

import db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TenantInfoFrame extends JFrame {

    public TenantInfoFrame() {
        setTitle("All Tenants Information");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel titleLabel = new JLabel("Building Tenants List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(250, 15, 250, 30);
        panel.add(titleLabel);

        String[] columns = {"Flat No", "Name", "Phone", "NID", "Monthly Rent"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (isSelected) {
                    c.setBackground(new Color(190, 205, 230));
                } else if (row % 2 == 0) {
                    c.setBackground(new Color(235, 245, 255));
                } else {
                    c.setBackground(Color.WHITE);
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 60, 620, 260);
        panel.add(sp);

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tenants");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("flat_no"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("nid"),
                    rs.getDouble("rent")
                });
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(250, 340, 180, 35);
        backButton.setBackground(new Color(255, 210, 180));
        panel.add(backButton);

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });

        add(panel);
        setVisible(true);
    }
}
