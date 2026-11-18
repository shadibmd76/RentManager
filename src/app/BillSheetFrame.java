package app;

import db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BillSheetFrame extends JFrame {

    String[] months = {"January", "February", "March", "April", "May", "June",
                       "July", "August", "September", "October", "November", "December"};

    JComboBox<String> flatBox = new JComboBox<>();
    JTextField tYear = new JTextField("2026");
    JTextField tPaid = new JTextField();
    DefaultTableModel model;
    JTable table;
    JLabel totalLabel = new JLabel("");
    double rent = 0;

    public BillSheetFrame() {
        setTitle("12-Month Rent Sheet");
        setSize(800, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 248, 252));

        JLabel titleLabel = new JLabel("12-Month Rent Sheet");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(300, 10, 250, 30);
        panel.add(titleLabel);

        JLabel l1 = new JLabel("Flat No:");
        l1.setBounds(30, 55, 60, 25);
        panel.add(l1);

        flatBox.setBounds(90, 55, 100, 25);
        panel.add(flatBox);

        JLabel l2 = new JLabel("Year:");
        l2.setBounds(210, 55, 40, 25);
        panel.add(l2);

        tYear.setBounds(250, 55, 70, 25);
        panel.add(tYear);

        JButton loadButton = new JButton("Show Sheet");
        loadButton.setBounds(340, 52, 120, 30);
        loadButton.setBackground(new Color(173, 216, 230));
        panel.add(loadButton);

        String[] columns = {"Month", "Rent", "Paid", "Due", "Status"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                String status = t.getValueAt(row, 4).toString();
                if (isSelected) {
                    c.setBackground(new Color(190, 205, 230));
                } else if (status.equals("Paid")) {
                    c.setBackground(new Color(200, 245, 200));
                } else if (status.equals("Partial")) {
                    c.setBackground(new Color(255, 245, 190));
                } else {
                    c.setBackground(new Color(255, 215, 215));
                }
                c.setForeground(Color.BLACK);
                return c;
            }
        });

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(30, 100, 720, 310);
        panel.add(sp);

        JLabel l3 = new JLabel("Paid Amount:");
        l3.setBounds(30, 425, 90, 25);
        panel.add(l3);

        tPaid.setBounds(125, 425, 120, 25);
        panel.add(tPaid);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(265, 422, 120, 30);
        updateButton.setBackground(new Color(180, 235, 180));
        panel.add(updateButton);

        totalLabel.setBounds(30, 465, 520, 25);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(totalLabel);

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBounds(580, 465, 170, 35);
        backButton.setBackground(new Color(255, 210, 180));
        panel.add(backButton);

        loadFlats();
        if (flatBox.getItemCount() > 0) {
            loadSheet();
        }

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tPaid.setText(table.getValueAt(row, 2).toString());
            }
        });

        loadButton.addActionListener(e -> loadSheet());

        updateButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Age table theke ekta mash select koro!");
                return;
            }
            try {
                double paid = Double.parseDouble(tPaid.getText());
                String flat = flatBox.getSelectedItem().toString();
                int year = Integer.parseInt(tYear.getText());

                Connection conn = DBConnection.connect();
                String query = "INSERT INTO rent_bills (flat_no, bill_year, bill_month, paid_amount) " +
                               "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE paid_amount=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, flat);
                pst.setInt(2, year);
                pst.setInt(3, row + 1);
                pst.setDouble(4, paid);
                pst.setDouble(5, paid);
                pst.executeUpdate();
                conn.close();

                loadSheet();
                table.setRowSelectionInterval(row, row);
                JOptionPane.showMessageDialog(this, "Rent Updated Successfully!");
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

    void loadFlats() {
        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT flat_no FROM tenants");
            while (rs.next()) {
                flatBox.addItem(rs.getString("flat_no"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void loadSheet() {
        model.setRowCount(0);
        if (flatBox.getSelectedItem() == null) return;

        try {
            String flat = flatBox.getSelectedItem().toString();
            int year = Integer.parseInt(tYear.getText());
            Connection conn = DBConnection.connect();

            PreparedStatement p1 = conn.prepareStatement("SELECT rent FROM tenants WHERE flat_no=?");
            p1.setString(1, flat);
            ResultSet r1 = p1.executeQuery();
            rent = r1.next() ? r1.getDouble("rent") : 0;

            double[] paid = new double[12];
            PreparedStatement p2 = conn.prepareStatement(
                "SELECT bill_month, paid_amount FROM rent_bills WHERE flat_no=? AND bill_year=?");
            p2.setString(1, flat);
            p2.setInt(2, year);
            ResultSet r2 = p2.executeQuery();
            while (r2.next()) {
                paid[r2.getInt("bill_month") - 1] = r2.getDouble("paid_amount");
            }
            conn.close();

            double totalPaid = 0, totalDue = 0;
            for (int i = 0; i < 12; i++) {
                double due = rent - paid[i];
                String status;
                if (rent > 0 && paid[i] >= rent) status = "Paid";
                else if (paid[i] > 0) status = "Partial";
                else status = "Unpaid";

                model.addRow(new Object[]{months[i], rent, paid[i], due, status});
                totalPaid += paid[i];
                totalDue += Math.max(due, 0);
            }
            totalLabel.setText("Total Paid: " + totalPaid + "     Total Due: " + totalDue);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Year thik moto dao!");
        }
    }
}
