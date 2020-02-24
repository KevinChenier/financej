package ca.etsmtl.log240.auth;

import ca.etsmtl.log240.financej.FinanceJ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class AuthFrame extends JFrame {

    private JPanel pnlLogin;
    private JLabel lblLogin;
    private JTextField txtUsername;
    private JPasswordField passwordField;
    private JButton btnLogin;

    private FinanceJ financeJ;

    private final String[] ADMIN_CREDS = {"admin", "admin"};

    public AuthFrame() {
        super();
        initComponents();
        initFrame();
        financeJ = new FinanceJ();
    }

    /**
     * Initialise current frame
     */
    private void initFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FinanceJ Login");
        setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Initialise components in the Frame.
     */
    private void initComponents() {
        GridLayout borderLayout = new GridLayout(4,1);
        JPanel pnlContainer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        this.pnlLogin = new JPanel();
        this.pnlLogin.setLayout(borderLayout);
        this.lblLogin = new JLabel("LOGIN");
        this.txtUsername = new JTextField("username here");
        this.passwordField = new JPasswordField("password");
        this.btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(200,40));

        lblLogin.setHorizontalAlignment(JLabel.CENTER);
        txtUsername.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setHorizontalAlignment(JPasswordField.CENTER);

        // setup listeners
        initControllers();

        // add fields
        pnlLogin.add(lblLogin);
        pnlLogin.add(txtUsername);
        pnlLogin.add(passwordField);
        pnlLogin.add(btnLogin);

        // add panels
        pnlContainer.add(pnlLogin);
        this.add(pnlContainer);


    }

    /**
     * Init controllers for buttons in this page
     */
    private void initControllers() {
        this.btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String USERNAME_INPUT = txtUsername.getText();
                final String PASSWORD_INPUT = new String(passwordField.getPassword());

                if(isValidAdminCredentials(new String[] {USERNAME_INPUT, PASSWORD_INPUT})) {
                    // valid login
                    JOptionPane.showMessageDialog(null,
                    "Welcome !");
                    dispose();
                    financeJ.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null,
                "Username or password do not match. Please try again!",
                    "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Verify if credentials are valid with admin credentials.
     * @param creds is a String[] {username, password}.
     * @return true if credentials are valid.
     */
    private boolean isValidAdminCredentials(String[] creds) {
        return Arrays.equals(ADMIN_CREDS, creds);
    }
}
