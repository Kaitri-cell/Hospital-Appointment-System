import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HospitalAppointment extends JFrame implements ActionListener {

    JTextField nameField, ageField, dateField;
    JComboBox<String> doctorBox;
    JRadioButton maleButton, femaleButton;
    JCheckBox firstVisitBox;
    JButton bookButton, showButton, clearButton;
    JTable table;
    DefaultTableModel model;

    public HospitalAppointment() {

        setTitle("Hospital Appointment System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Form panel
        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Book Appointment"));

        nameField = new JTextField();
        ageField = new JTextField();
        dateField = new JTextField("22-09-2026");

        doctorBox = new JComboBox<>(new String[]{
                "Dr. Sharma",
                "Dr. Gupta",
                "Dr. Mehta"
        });

        maleButton = new JRadioButton("Male", true);
        femaleButton = new JRadioButton("Female");

        ButtonGroup group = new ButtonGroup();
        group.add(maleButton);
        group.add(femaleButton);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);

        firstVisitBox = new JCheckBox("Yes");

        bookButton = new JButton("Book");
        showButton = new JButton("Show Appointments");
        clearButton = new JButton("Clear");

        bookButton.addActionListener(this);
        showButton.addActionListener(this);
        clearButton.addActionListener(this);

        form.add(new JLabel("Patient Name:"));
        form.add(nameField);

        form.add(new JLabel("Age:"));
        form.add(ageField);

        form.add(new JLabel("Gender:"));
        form.add(genderPanel);

        form.add(new JLabel("Doctor:"));
        form.add(doctorBox);

        form.add(new JLabel("Date:"));
        form.add(dateField);

        form.add(new JLabel("First Visit:"));
        form.add(firstVisitBox);

        form.add(bookButton);
        form.add(showButton);

        form.add(clearButton);
        form.add(new JLabel(""));

        // Table
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Age", "Gender", "Doctor", "Date", "First Visit"},
                0
        );

        table = new JTable(model);

        add(form, BorderLayout.WEST);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    // Database connection
    public Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/hospital_db";
        String user = "root";
        String password = "";

        return DriverManager.getConnection(url, user, password);
    }

    // Button events
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == bookButton) {
            bookAppointment();
        }

        if (e.getSource() == showButton) {
            showAppointments();
        }

        if (e.getSource() == clearButton) {
            clearForm();
        }
    }

    public void bookAppointment() {

        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = maleButton.isSelected() ? "Male" : "Female";
        String doctor = doctorBox.getSelectedItem().toString();
        String date = dateField.getText();
        boolean firstVisit = firstVisitBox.isSelected();

        if (name.isEmpty() || ageText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter name and age.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);

            Connection con = connect();

            String sql = "INSERT INTO appointments(name, age, gender, doctor, appointment_date, first_visit) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, doctor);
            ps.setString(5, date);
            ps.setBoolean(6, firstVisit);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Appointment Booked!");

            con.close();

            clearForm();
            showAppointments();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void showAppointments() {

        model.setRowCount(0);

        try {
            Connection con = connect();

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM appointments");

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("doctor"),
                        rs.getString("appointment_date"),
                        rs.getBoolean("first_visit") ? "Yes" : "No"
                });
            }

            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void clearForm() {
        nameField.setText("");
        ageField.setText("");
        maleButton.setSelected(true);
        doctorBox.setSelectedIndex(0);
        dateField.setText("");
        firstVisitBox.setSelected(false);
    }

    public static void main(String[] args) {
        new HospitalAppointment();
    }
}
