package View;

import javax.swing.*;
import java.awt.*;

import DAO.UserDAO;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Runnable loginSuccessListener; 

    public LoginView() {
       
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Layout setup
        setLayout(new BorderLayout());

        // Create the input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Create the button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);

        // Add panels to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener for the login button
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

       
        UserDAO userDAO = new UserDAO();
        boolean isValid = userDAO.validateUser(username, password);

        if (isValid) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            if (loginSuccessListener != null) {
                loginSuccessListener.run();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  
    public void setLoginSuccessListener(Runnable listener) {
        this.loginSuccessListener = listener;
    }
}
