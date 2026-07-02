import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;

public class login {
    public login() {

        // Creating Frame
        JFrame f = new JFrame("Gym Membership And Class Management System");
        f.setLayout(null);
        f.setSize(500, 700);
        f.setResizable(false);
        f.getContentPane().setBackground(Color.decode("#121212")); // Frame background color
        DB.deleteOldClasses();// delete classes scheduled by trainer, that are older than today's date

        // ----------------------------------------------------------------------------------------------------------------
        // Username fields
        JLabel user = new JLabel("Username:");
        user.setBounds(10, 50, 100, 40);
        user.setForeground(Color.decode("#dbdbdb"));
        JTextField usertf = new JTextField();
        usertf.setBounds(10, 90, 460, 40);
        usertf.setBackground(Color.decode("#dbdbdb"));
        f.add(user);
        f.add(usertf);

        // Password fields
        JLabel pass = new JLabel("Password:");
        pass.setBounds(10, 140, 100, 40);
        pass.setForeground(Color.decode("#dbdbdb"));
        JPasswordField passtf = new JPasswordField();
        passtf.setBounds(10, 180, 379, 40);
        passtf.setBackground(Color.decode("#dbdbdb"));
        f.add(pass);
        f.add(passtf);
        // -----------------------------------------------------------------------------------------------------------------
        // Hide or Unhide button
        JButton showOrnoShowbtn = new JButton("Unhide");
        showOrnoShowbtn.setBounds(390, 180, 80, 40);
        showOrnoShowbtn.setFocusPainted(false);
        showOrnoShowbtn.setBorderPainted(false);
        showOrnoShowbtn.setBackground(Color.decode("#f03434"));
        showOrnoShowbtn.setForeground(Color.WHITE);
        f.add(showOrnoShowbtn);

        // Toggle password
        showOrnoShowbtn.addActionListener(new ActionListener() {
            private boolean isHidden = true;

            public void actionPerformed(ActionEvent e) {
                if (isHidden) {
                    passtf.setEchoChar((char) 0); // Show pass //"0 is the null character (\u0000 in Unicode).It tells
                                                  // JPasswordField not to replace characters with a masking character."
                    showOrnoShowbtn.setText("Hide");
                    showOrnoShowbtn.setBackground(Color.decode("#56c82b"));
                } else {
                    passtf.setEchoChar('•'); // Hide pass
                    showOrnoShowbtn.setText("Unhide");
                    showOrnoShowbtn.setBackground(Color.decode("#f03434"));
                }
                isHidden = !isHidden;
            }
        });

        // -----------------------------------------------------------------------------------------------------------------
        // Login Button
        JButton loginbtn = new JButton("Login");
        loginbtn.setBounds(150, 270, 200, 40);
        loginbtn.setBackground(Color.decode("#56c82b"));
        loginbtn.setForeground(Color.WHITE);
        f.add(loginbtn);
        // --------------------------------------------------------
        ActionListener loginValidityChecker = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usertf.getText();
                String password = new String(passtf.getPassword());

                // check if user exists in the db
                DB.LoginData loginData = new DB.LoginData(username, "");
                boolean usernameExists = DB.read(loginData); // check if username exists and fetch password

                if (usernameExists) {
                    // if username exists, check password
                    if (loginData.password.equals(password)) {
                        global.username = username;
                        if (loginData.type == 3) {
                            new member_dashboard(); // Re-direct to member dashboard
                        } else if (loginData.type == 2) {
                            new trainer_dashboard(); // Re-direct to trainer dashboard
                        } else if (loginData.type == 1) {
                            new admin_dashboard();// Re-direct to admin dashboard
                        }
                        f.dispose();
                        // DB.setLoginStatus("logged_in",username,loginData.type);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Password");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username");
                }
            }
        };

        // adding the action listener to login button, username and password textfield
        loginbtn.addActionListener(loginValidityChecker);
        passtf.addActionListener(loginValidityChecker);
        usertf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passtf.requestFocus();
            }
        });
        // ------------------------------------------------------------------------------------------------------------------
        // Register Button
        JButton regbtn = new JButton("Register as Member");
        regbtn.setBounds(150, 360, 200, 40);
        regbtn.setBackground(Color.decode("#56c82b"));
        regbtn.setForeground(Color.WHITE);
        f.add(regbtn);
        // --------------------------------------------------------
        regbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usertf.getText();
                String password = new String(passtf.getPassword());

                DB.LoginData loginData = new DB.LoginData(username, "");

                if (username.isEmpty() || password.isEmpty()) { // condition to stop registering if tfs are empty
                    JOptionPane.showMessageDialog(null, "The username or password can't be empty.");
                    return;
                }

                // check if the username already exists in db
                boolean usernameExists = DB.read(loginData);

                // if username doesnt exist; register
                if (!usernameExists) {
                    DB.insert(3, username, password);
                    DB.insert_member("empty", username, 0, "empty", "empty");
                    global.username = username;
                    new member_dashboard(); // Re-direct to member dashboard
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "The username " + username + " has already been taken.");
                }
            }
        });
        // ------------------------------------------------------------------------------------------------------------------

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ------------------------------------------------------------------------------------------------------------------
        // Logging in automatically if user hasn't previously logged out.
        // String[] login_status = DB.getLoginStatus();
        // if (login_status[0].equals("logged_in")){
        // global.username = login_status[1];
        // int type = Integer.parseInt(login_status[2]);
        // if (type == 1){
        // new admin_dashboard();//Re-direct to admin dashboard
        // }
        // else if (type == 2){
        // new trainer_dashboard(); //Re-direct to trainer dashboard
        // }
        // else if (type == 3){
        // new member_dashboard(); //Re-direct to trainer dashboard
        // }
        // f.dispose();
    }
}
// }
