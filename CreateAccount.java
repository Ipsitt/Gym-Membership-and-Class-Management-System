import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccount {

    boolean Valid = false;
    boolean PassIsValid = false;
    boolean UsernameIsValidTrainer = false;
    boolean UsernameIsValidMember = false;
    String username = "";
    String password = "";
    boolean passError = false;
    boolean userError = false;
    String passErrorMessage = "";
    String userErrorMessage = "";

    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white = Color.decode("#dbdbdb");
    Color red = Color.decode("#f03434");
    Color buttongrey = Color.decode("#a6a6a6");

    JButton createMemberButton;
    JButton createTrainerButton;

    public CreateAccount() {
        JFrame frame = new JFrame("Create Account");
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#121212"));
        frame.setLayout(null);

        // Panel for trainer account creation
        JPanel panelTrainer = new JPanel();
        panelTrainer.setBounds(50, 50, 380, 270);
        panelTrainer.setBackground(grey);
        panelTrainer.setLayout(null);
        frame.add(panelTrainer);

        JLabel titleTrainer = new JLabel("Create Trainer Account:");
        titleTrainer.setFont(new Font("Arial", Font.BOLD, 20));
        titleTrainer.setForeground(green);
        titleTrainer.setBounds(30, 20, 250, 50);
        panelTrainer.add(titleTrainer);

        // Username for trainer
        JLabel userLabelTrainer = new JLabel("Username:");
        userLabelTrainer.setForeground(Color.WHITE);
        userLabelTrainer.setBounds(30, 80, 70, 30);
        panelTrainer.add(userLabelTrainer);

        JTextField usernameFieldTrainer = new JTextField();
        usernameFieldTrainer.setBounds(120, 80, 230, 30);
        panelTrainer.add(usernameFieldTrainer);

        // Password for trainer
        JLabel passLabelTrainer = new JLabel("Password:");
        passLabelTrainer.setForeground(Color.WHITE);
        passLabelTrainer.setBounds(30, 130, 70, 30);
        panelTrainer.add(passLabelTrainer);

        JTextField passwordFieldTrainer = new JTextField();
        passwordFieldTrainer.setBounds(120, 130, 230, 30);
        panelTrainer.add(passwordFieldTrainer);

        // Buttons for trainer
        createTrainerButton = new JButton("Create Trainer");
        createTrainerButton.setBounds(120, 200, 150, 40);
        createTrainerButton.setBackground(buttongrey);
        createTrainerButton.setForeground(white);
        panelTrainer.add(createTrainerButton);

        // Panel for member account creation (Bottom Panel)
        JPanel panelMember = new JPanel();
        panelMember.setBounds(50, 350, 380, 220);
        panelMember.setBackground(grey);
        panelMember.setLayout(null);
        frame.add(panelMember);

        JLabel titleMember = new JLabel("Create Member Account:");
        titleMember.setFont(new Font("Arial", Font.BOLD, 20));
        titleMember.setForeground(green);
        titleMember.setBounds(30, 20, 250, 50);
        panelMember.add(titleMember);

        // Username for member
        JLabel userLabelMember = new JLabel("Username:");
        userLabelMember.setForeground(Color.WHITE);
        userLabelMember.setBounds(30, 80, 70, 30);
        panelMember.add(userLabelMember);

        JTextField usernameFieldMember = new JTextField();
        usernameFieldMember.setBounds(120, 80, 230, 30);
        panelMember.add(usernameFieldMember);

        // Buttons for member
        createMemberButton = new JButton("Create Member");
        createMemberButton.setBounds(120, 150, 150, 40);
        createMemberButton.setBackground(buttongrey);
        createMemberButton.setForeground(white);
        panelMember.add(createMemberButton);

        // Back Button
        JButton back = new JButton("<");
        back.setFont(new Font("Arial", Font.BOLD, 17));
        back.setBounds(0, 0, 50, 50);
        back.setBackground(Color.decode("#121212"));
        back.setForeground(green);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new admin_dashboard();
                frame.dispose();
            }
        });
        frame.add(back);

        // Username and Password validation for trainer
        usernameFieldTrainer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String usernameInput = usernameFieldTrainer.getText().trim();
                if (usernameInput.isEmpty()) {
                    UsernameIsValidTrainer = false;
                    userError = true;
                    username = usernameInput;
                    userErrorMessage = "A Username is required.";
                } else {
                    UsernameIsValidTrainer = true;
                    userError = false;
                    username = usernameInput;
                }
                UpdateBtnStateTrainer();
            }
        });

        passwordFieldTrainer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String passFromTF = passwordFieldTrainer.getText();
                if (passFromTF.isEmpty()) {
                    PassIsValid = false;
                    passError = true;
                    password = passFromTF;
                    passErrorMessage = "A Password is required.";
                } else if (passFromTF.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,})")) {
                    PassIsValid = true;
                    passError = false;
                    password = passFromTF;
                } else {
                    PassIsValid = false;
                    passError = true;
                    password = passFromTF;
                    passErrorMessage = "Password must have at least 1 uppercase, 1 lowercase, 1 special character, 1 digit, and 8+ characters.";
                }
                UpdateBtnStateTrainer();
            }
        });

        // Username validation for member
        usernameFieldMember.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String usernameInput = usernameFieldMember.getText().trim();
                if (usernameInput.isEmpty()) {
                    UsernameIsValidMember = false;
                    userError = true;
                    username = usernameInput;
                    userErrorMessage = "A Username is required.";
                } else {
                    UsernameIsValidMember = true;
                    userError = false;
                    username = usernameInput;
                }
                UpdateBtnStateMember();
            }
        });

        // Listeners for Create Account buttons (trainer and member)
        createTrainerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!UsernameIsValidTrainer) {
                    JOptionPane.showMessageDialog(null, userErrorMessage);
                    return;
                }
                if (!PassIsValid) {
                    JOptionPane.showMessageDialog(null, passErrorMessage);
                    return;
                }

                DB.LoginData loginData = new DB.LoginData(username, "");
                boolean usernameExists = DB.read(loginData);

                if (!usernameExists) {
                    DB.insert_trainer_into_login(username, password);
                    DB.insert_trainer(username, "empty", "empty", 0);
                    global.username = username;
                    JOptionPane.showMessageDialog(null, "Your Trainer Account has been successfully created!");
                } else {
                    JOptionPane.showMessageDialog(null, "The username " + username + " is already taken.");
                }
            }
        });

        createMemberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!UsernameIsValidMember) {
                    JOptionPane.showMessageDialog(null, userErrorMessage);
                    return;
                }

                DB.LoginData loginData = new DB.LoginData(username, "");
                boolean usernameExists = DB.read(loginData);

                if (!usernameExists) {
                    DB.insert(3, username, "");
                    DB.insert_member("empty", username, 0, "empty", "empty");
                    global.username = username;
                    new member_dashboard(); // Redirect to member dashboard
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "The username " + username + " is already taken.");
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void UpdateBtnStateTrainer() {
        // Update the Trainer button based on the state of the Trainer username and password
        if (UsernameIsValidTrainer && PassIsValid) {
            createTrainerButton.setBackground(green);
        } else {
            createTrainerButton.setBackground(red);
        }
    }

    private void UpdateBtnStateMember() {
        // Update the Member button based only on the state of the Member username
        if (UsernameIsValidMember) {
            createMemberButton.setBackground(green);
        } else {
            createMemberButton.setBackground(red);
        }
    }
}
