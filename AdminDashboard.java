package Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame implements ActionListener {

    private JLabel titleLabel;
    private JButton manageCustomersButton;
    private JButton viewBillsButton;
    private JButton generateBillsButton;
    private JButton logoutButton;

    public AdminDashboard() {
        // Initialize the JFrame
        setTitle("Admin Dashboard - Electricity Billing System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30)); // Use FlowLayout for button arrangement

        // Initialize and add components
        titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel);

        manageCustomersButton = new JButton("Manage Customers");
        manageCustomersButton.setPreferredSize(new Dimension(200, 50));
        manageCustomersButton.addActionListener(this);
        add(manageCustomersButton);

        viewBillsButton = new JButton("View All Bills");
        viewBillsButton.setPreferredSize(new Dimension(200, 50));
        viewBillsButton.addActionListener(this);
        add(viewBillsButton);

        generateBillsButton = new JButton("Generate New Bills");
        generateBillsButton.setPreferredSize(new Dimension(200, 50));
        generateBillsButton.addActionListener(this);
        add(generateBillsButton);

        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.addActionListener(this);
        add(logoutButton);

        //Set visibility of frame.
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == manageCustomersButton) {
            // Open Manage Customers window/functionality
            // Instead of showing a message, open the AddCustomer frame.
            //  JOptionPane.showMessageDialog(this, "Manage Customers functionality will be implemented here.");
            new AddCustomer(); //corrected
            dispose(); // Close the current dashboard.

        } else if (e.getSource() == viewBillsButton) {
            // Open View Bills window/functionality
            JOptionPane.showMessageDialog(this, "View All Bills functionality will be implemented here.");
            // new ViewBillsFrame(); // Example
        } else if (e.getSource() == generateBillsButton) {
            // Open Generate Bills window/functionality
            JOptionPane.showMessageDialog(this, "Generate New Bills functionality will be implemented here.");
            // new GenerateBillsFrame(); // Example
        } else if (e.getSource() == logoutButton) {
            // Perform logout actions (e.g., go back to login screen)
            JOptionPane.showMessageDialog(this, "Logged out successfully.");
            // new Auth.LoginForm(); // Assuming your login form is in the Auth package.  You'd need to create this.
            dispose(); // Close the admin dashboard
            //  System.exit(0); //Or use this to exit the application.
        }
    }

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure GUI updates are done on the Event Dispatch Thread.
        SwingUtilities.invokeLater(AdminDashboard::new); // Corrected call to the constructor.
    }
}
