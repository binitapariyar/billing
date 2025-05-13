package Admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class AddCustomer extends JFrame {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private Connection con;

    public AddCustomer() {
        // Initialize database connection
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricity_billing", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to the database: " + e.getMessage());
            // Consider exiting the application if the database connection fails.
            System.exit(1);
        }

        setTitle("Add Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(225, 227, 228));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Create and add the AddEmployeeCustomer panel
        UserManagementPanel userManagementPanel = new UserManagementPanel(con); // Pass the connection, Changed to UserManagementPanel
        tabbedPane.addTab("Manage Users", null, userManagementPanel, "Add and Manage Users");

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setVisible(true);
    }

    // Inner class for managing users, changed class name to UserManagementPanel
    public static class UserManagementPanel extends JPanel {
        private JTextField txtUsername, txtName, txtAddress, txtPhone;
        private JButton btnAddUser, btnClear, btnUpdateUser, btnDeleteUser, btnBack;
        private int userIdToUpdateOrDelete = -1;
        private Connection con;  // Use the connection passed in
        private JPanel formPanel;
        private JTable userTable;
        private DefaultTableModel tableModel;
        private boolean isInitialized = false;


        public UserManagementPanel(Connection connection) { // Constructor now takes a Connection, Changed to UserManagementPanel
            this.con = connection; // Use the passed connection

            setLayout(new BorderLayout());

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout(20, 20));
            mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            mainPanel.setBackground(new Color(225, 227, 228));

            formPanel = new JPanel();
            formPanel.setLayout(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createTitledBorder("Add/Update/Delete User"));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 15, 10, 15);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;

            JLabel lblUsername = new JLabel("Username:");
            lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 0;
            formPanel.add(lblUsername, gbc);

            txtUsername = new JTextField();
            txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 0;
            formPanel.add(txtUsername, gbc);

            JLabel lblName = new JLabel("Name:");
            lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 1;
            formPanel.add(lblName, gbc);

            txtName = new JTextField();
            txtName.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 1;
            formPanel.add(txtName, gbc);

            JLabel lblAddress = new JLabel("Address:");
            lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 2;
            formPanel.add(lblAddress, gbc);

            txtAddress = new JTextField();
            txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 2;
            formPanel.add(txtAddress, gbc);

            JLabel lblPhone = new JLabel("Phone:");
            lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 0;
            gbc.gridy = 3;
            formPanel.add(lblPhone, gbc);

            txtPhone = new JTextField();
            txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 16));
            gbc.gridx = 1;
            gbc.gridy = 3;
            formPanel.add(txtPhone, gbc);


            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(Color.WHITE);

            btnAddUser = new JButton("Add User");
            btnAddUser.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnAddUser.setBackground(new Color(30, 144, 255));
            btnAddUser.setForeground(Color.WHITE);

            btnClear = new JButton("Clear");
            btnClear.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnClear.setBackground(new Color(220, 20, 60));
            btnClear.setForeground(Color.WHITE);

            btnUpdateUser = new JButton("Update User");
            btnUpdateUser.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnUpdateUser.setBackground(new Color(255, 165, 0));
            btnUpdateUser.setForeground(Color.WHITE);
            btnUpdateUser.setEnabled(false);

            btnDeleteUser = new JButton("Delete User");
            btnDeleteUser.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnDeleteUser.setBackground(new Color(220, 20, 60));
            btnDeleteUser.setForeground(Color.WHITE);
            btnDeleteUser.setEnabled(false);

            btnBack = new JButton("Back to Dashboard");
            btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnBack.setBackground(new Color(30, 144, 255));
            btnBack.setForeground(Color.WHITE);
            btnBack.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open the AdminDashboard
                    AddCustomer adminDashboard = new AddCustomer();
                    adminDashboard.setVisible(true);
                    // Close the current window
                    Window parentWindow = SwingUtilities.getWindowAncestor((Component) e.getSource());
                    if (parentWindow != null) {
                        parentWindow.dispose();
                    }
                }
            });


            buttonPanel.add(btnAddUser);
            buttonPanel.add(btnClear);
            buttonPanel.add(btnUpdateUser);
            buttonPanel.add(btnDeleteUser);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            formPanel.add(buttonPanel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            formPanel.add(btnBack, gbc);


            mainPanel.add(formPanel, BorderLayout.NORTH);

            // Create table
            tableModel = new DefaultTableModel();
            userTable = new JTable(tableModel);
            tableModel.addColumn("ID");
            tableModel.addColumn("Username");
            tableModel.addColumn("Name");
            tableModel.addColumn("Address");
            tableModel.addColumn("Phone");
            JScrollPane tableScrollPane = new JScrollPane(userTable);
            tableScrollPane.setPreferredSize(new Dimension(600, 300));
            mainPanel.add(tableScrollPane, BorderLayout.CENTER);


            add(mainPanel, BorderLayout.CENTER);

            // Load initial data into the table
            loadUsers();

            // Button Actions
            btnAddUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addUser();
                }
            });
            btnClear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearForm();
                }
            });
            btnUpdateUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateUser();
                }
            });
            btnDeleteUser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteUser();
                }
            });

            // Add mouse listener to the table
            userTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = userTable.getSelectedRow();
                    if (row != -1) {
                        // Get the data from the selected row
                        userIdToUpdateOrDelete = (int) tableModel.getValueAt(row, 0);
                        txtUsername.setText((String) tableModel.getValueAt(row, 1));
                        txtName.setText((String) tableModel.getValueAt(row, 2));
                        txtAddress.setText((String) tableModel.getValueAt(row, 3));
                        txtPhone.setText((String) tableModel.getValueAt(row, 4));

                        // Enable the update and delete buttons
                        btnUpdateUser.setEnabled(true);
                        btnDeleteUser.setEnabled(true);
                        btnAddUser.setEnabled(false);
                    }
                }
            });


            txtUsername.addFocusListener(new java.awt.event.FocusListener() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                }

                public void focusLost(java.awt.event.FocusEvent evt) {
                    if (!txtUsername.getText().isEmpty()) {
                        findUserByUsername();
                    }
                }
            });
        }

        private void addUser() {
            String username = txtUsername.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();


            if (username.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            if (!isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(this, "Phone number must be 10 digits!");
                return;
            }

            try (PreparedStatement pst = con.prepareStatement("INSERT INTO users (username, name, address, phone) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, username);
                pst.setString(2, name);
                pst.setString(3, address);
                pst.setString(4, phone);


                pst.executeUpdate();
                ResultSet generatedKeys = pst.getGeneratedKeys();
                int newUserId = -1;
                if (generatedKeys.next()) {
                    newUserId = generatedKeys.getInt(1);
                }
                JOptionPane.showMessageDialog(this, "User Added Successfully!");
                clearForm();
                loadUsers(); // Reload user data to update the table
                if (newUserId != -1) {
                    // Optionally, update the tableModel directly:
                    Vector<Object> newRow = new Vector<>();
                    newRow.add(newUserId);
                    newRow.add(username);
                    newRow.add(name);
                    newRow.add(address);
                    newRow.add(phone);
                    tableModel.addRow(newRow);
                }


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Add Error: " + ex.getMessage());
            }
        }

        private void clearForm() {
            txtUsername.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtPhone.setText("");
            userIdToUpdateOrDelete = -1;
            btnAddUser.setEnabled(true);
            btnUpdateUser.setEnabled(false);
            btnDeleteUser.setEnabled(false);
        }

        private void updateUser() {
            String username = txtUsername.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();


            if (username.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username, name, address and phone to update.");
                return;
            }
            if (!isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(this, "Phone number must be 10 digits!");
                return;
            }

            if (userIdToUpdateOrDelete == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user by clicking on the table row.");
                return;
            }

            try (PreparedStatement pst = con.prepareStatement("UPDATE users SET username = ?, name = ?, address = ?, phone = ? WHERE id = ?")) {
                pst.setString(1, username);
                pst.setString(2, name);
                pst.setString(3, address);
                pst.setString(4, phone);
                pst.setInt(5, userIdToUpdateOrDelete);
                int rowsUpdated = pst.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!");
                    clearForm();
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "User not found or no changes made.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
            }
        }

        private void deleteUser() {
            String username = txtUsername.getText();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a user from the table.");
                return;
            }
            if (userIdToUpdateOrDelete == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user by clicking on the table row.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            try (PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE id = ?")) {
                pst.setInt(1, userIdToUpdateOrDelete);
                int rowsDeleted = pst.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                    clearForm();
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "User not found or no changes made.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + ex.getMessage());
            }
        }

        private void findUserByUsername() {
            String username = txtUsername.getText().trim();
            if (username.isEmpty()) {
                return; // Do nothing if the username field is empty
            }

            try (PreparedStatement pst = con.prepareStatement("SELECT id, name, address, phone FROM users WHERE username = ?")) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    // Username exists – show a message
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                    userIdToUpdateOrDelete = rs.getInt("id");
                    txtName.setText(rs.getString("name"));
                    txtAddress.setText(rs.getString("address"));
                    txtPhone.setText(rs.getString("phone"));
                    btnUpdateUser.setEnabled(true);
                    btnDeleteUser.setEnabled(true);
                    btnAddUser.setEnabled(false);
                } else {
                    // Username does not exist – do nothing (no message)
                    userIdToUpdateOrDelete = -1;
                    btnAddUser.setEnabled(true);
                    btnUpdateUser.setEnabled(false);
                    btnDeleteUser.setEnabled(false);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        }

        private void loadUsers() {
            tableModel.setRowCount(0); // Clear the table before loading
            try (PreparedStatement pst = con.prepareStatement("SELECT id, username, name, address, phone FROM users");
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("id"));
                    row.add(rs.getString("username"));
                    row.add(rs.getString("name"));
                    row.add(rs.getString("address"));
                    row.add(rs.getString("phone"));
                    tableModel.addRow(row);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage());
            }
        }

        private boolean isValidPhoneNumber(String phoneNumber) {
            return phoneNumber.matches("\\d{10}");
        }
    }
}
