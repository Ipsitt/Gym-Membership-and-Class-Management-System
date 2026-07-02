import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class member_account_info {

    boolean Valid= false;
    boolean NameIsValid = false; String name = ""; boolean nameError = false;  String nameErrorMessage = "";
    // boolean PassIsValid = false; String password = ""; boolean passError = false;  String passErrorMessage = "";
    boolean AddressIsValid = false; String user_address = ""; boolean addressError = false;  String addressErrorMessage = "";
    boolean PhoneIsValid = false; String phone_no = ""; boolean phoneError = false;  String phoneErrorMessage = "";
    boolean SexIsSelected = false; String user_sex = "none"; boolean sexError = false;  String sexErrorMessage = "";
    boolean dataAlreadyExistsInDB = false; boolean dupError = false;  String duplicateErrorMessage = "";

    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white = Color.decode("#dbdbdb");
    Color red = Color.decode("#f03434");
    Color buttongrey = Color.decode("#a6a6a6");

    JButton submitButton; String button_text = "Update";

    String[] memberinfo = DB.getMemberInfo(global.username);

    public member_account_info() {
        JFrame accountinfo_f = new JFrame("Gym Membership And Class Management System");
        accountinfo_f.setResizable(false);
        accountinfo_f.setSize(500, 700);
        accountinfo_f.getContentPane().setBackground(Color.decode("#121212"));
        accountinfo_f.setLayout(null);

        //------------------------------------------------------------------------------------------------------------------
        // First Panel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50, 50, 380, 460);
        p1.setLayout(null);

        //------------------------------------------------------------------------------------------------------------------
        // Username fields
        JLabel user = new JLabel("Full Name:");
        user.setBounds(10, 10, 100, 40);
        user.setForeground(Color.decode("#dbdbdb"));
        JTextField usertf = new JTextField(); 
        usertf.setBounds(10, 50, 360, 30);
        usertf.setBackground(Color.decode("#dbdbdb"));
        p1.add(user);
        p1.add(usertf);

        // // Password fields
        // JLabel pass = new JLabel("Password:");
        // pass.setBounds(10, 100, 100, 40);
        // pass.setForeground(Color.decode("#dbdbdb"));
        // JTextField passtf = new JTextField();  
        // passtf.setBounds(10, 140, 360, 30);
        // passtf.setBackground(Color.decode("#dbdbdb"));
        // p1.add(pass);
        // p1.add(passtf);

        // Address fields
        JLabel address = new JLabel("Address:");
        address.setBounds(10, 100, 100, 40);
        address.setForeground(Color.decode("#dbdbdb"));
        JTextField addresstf = new JTextField(); 
        addresstf.setBounds(10, 140, 360, 30);
        addresstf.setBackground(Color.decode("#dbdbdb"));
        p1.add(address);
        p1.add(addresstf);

        // Contact no
        JLabel phone = new JLabel("Contact No:");
        phone.setBounds(10, 190, 100, 40);
        phone.setForeground(Color.decode("#dbdbdb"));
        JTextField phonetf = new JTextField();  
        phonetf.setBounds(10, 230, 360, 30);
        phonetf.setBackground(Color.decode("#dbdbdb"));
        p1.add(phone);
        p1.add(phonetf);

        // Sex
        JLabel sex = new JLabel("Sex:");
        sex.setBounds(10, 270, 100, 40);
        sex.setForeground(white);

        JRadioButton maleRB = new JRadioButton("Male"); 
        maleRB.setBounds(10, 310, 100, 40);
        maleRB.setBackground(grey);
        maleRB.setForeground(Color.decode("#dbdbdb"));

        JRadioButton femaleRB = new JRadioButton("Female");  
        femaleRB.setBounds(120, 310, 100, 40);
        femaleRB.setBackground(grey);
        femaleRB.setForeground(Color.decode("#dbdbdb"));

        JRadioButton othersRB = new JRadioButton("Other");
        othersRB.setBounds(230, 310, 100, 40);
        othersRB.setBackground(grey);
        othersRB.setForeground(Color.decode("#dbdbdb"));

        ButtonGroup regchoice = new ButtonGroup();
        regchoice.add(maleRB);
        regchoice.add(femaleRB);
        regchoice.add(othersRB);

        p1.add(maleRB);
        p1.add(femaleRB);
        p1.add(othersRB);
        p1.add(sex);

        int money = DB.getTotalPayment(global.username);

        // Total money
        JLabel amt = new JLabel("Total Payment: " + money);
        amt.setBounds(10, 350, 100, 40);
        amt.setForeground(white);
        p1.add(amt);

        //----------------------------------------------------------------------------------------------------------------
        // Get member info from DB and assign to memberinfo variables
        if (memberinfo[0].equals("empty")) {
            button_text = "Save";
            usertf.setText("");
            // passtf.setText(memberinfo[1]); PassIsValid = true; password = memberinfo[1];//old password is obviously valid
            addresstf.setText("");
            phonetf.setText("");
        }else{//If data already exists in db
            dataAlreadyExistsInDB = true; //data from last validation are always valid
            NameIsValid = true; name = memberinfo[0];
            // PassIsValid = true; password = memberinfo[1];
            AddressIsValid = true; user_address = memberinfo[3];
            PhoneIsValid = true; phone_no = memberinfo[2];
            SexIsSelected = true; user_sex = memberinfo[4];
            usertf.setText(memberinfo[0]);
            // passtf.setText(memberinfo[1]);
            addresstf.setText(memberinfo[3]);
            phonetf.setText(memberinfo[2]);
            String sexcheck = memberinfo[4];
            if (sexcheck.equals("Male")) {
                maleRB.setSelected(true); 
            } else if (sexcheck.equals("Female")) {
                femaleRB.setSelected(true); 
            } else if (sexcheck.equals("Other")) {
                othersRB.setSelected(true); 
            }
        }

        //Submit Button
        submitButton = new JButton(button_text);
        submitButton.setBounds(130, 400, 130, 25);
        submitButton.setBackground(buttongrey);
        submitButton.setForeground(Color.WHITE);
        p1.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if (Valid){
                    DB.updateInfo(name,phone_no,user_address,user_sex);
                    accountinfo_f.dispose();
                    new member_account_info();
                    JOptionPane.showMessageDialog(null,"Saved.");
                }
                else{
                    if (nameError){
                    JOptionPane.showMessageDialog(null, nameErrorMessage);
                    }
                    // else if(passError){
                    // JOptionPane.showMessageDialog(null, passErrorMessage);
                    // }
                    else if(addressError){
                    JOptionPane.showMessageDialog(null, addressErrorMessage);
                    }
                    else if(phoneError){
                    JOptionPane.showMessageDialog(null, phoneErrorMessage); 
                    }
                    else if(sexError){
                    JOptionPane.showMessageDialog(null, sexErrorMessage); 
                    }
                    else if(dupError){
                    JOptionPane.showMessageDialog(null, duplicateErrorMessage); 
                    }
                }
            }
        });
        //----------------------------------------------------------------------------------------------------------------
//------------------------------------------
        usertf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String nameFromTF = usertf.getText();
                if (nameFromTF.isEmpty()){
                    NameIsValid= false; nameError = true; //Invalid 
                    name= nameFromTF;
                    nameErrorMessage = "You need to enter your name.";
                } 
                else if (nameFromTF.matches("(?i)^[a-z]+(\\s[a-z]+)+$")){ 
                    NameIsValid= true; 
                    name= nameFromTF; nameError = false;//Valid
                } 
                else{
                    NameIsValid= false; nameError = true;//Invalid
                    name= nameFromTF;
                    nameErrorMessage = "You need to enter your fullname.";
                }
                UpdateBtnState();
            }
        });

        // passtf.addKeyListener(new KeyAdapter(){
        //     @Override
        //     public void keyReleased(KeyEvent e) {
        //         String passFromTF = passtf.getText();
        //         if (passFromTF.isEmpty()){
        //             PassIsValid= false; passError = true;//Invalid
        //             password= passFromTF;
        //             passErrorMessage = "A Password is required to have an account.";
        //         } 
        //         else if (passFromTF.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,})")){ //regex to match ATLEAST ONE lowercase letter, uppercase letter, digit, special character and minimum of 8 length
        //             PassIsValid= true; 
        //             password= passFromTF; passError = false;//Valid
        //         } 
        //         else{
        //             PassIsValid= false; passError = true;//Invalid
        //             password= passFromTF;
        //             passErrorMessage = "Your Password needs to have atleast one uppercase letter, one lowercase letter, one special character, one digit and 8 characters long.";
        //         }
        //         UpdateBtnState();
        //     }
        // });

        addresstf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String addressFromTF = addresstf.getText();
                if (addressFromTF.isEmpty()){
                    AddressIsValid= false; addressError = true;//Invalid
                    user_address= addressFromTF;
                    addressErrorMessage = "Are you homeless?";
                } 
                else if (addressFromTF.matches("(?i).*[a-z]{3,}.*")){ //regex to match letters,digits,underscores,comma,dash,period
                    AddressIsValid= true; addressError = false;
                    user_address= addressFromTF; //Valid
                } 
                else{
                    AddressIsValid= false; addressError = true;//Invalid
                    addressErrorMessage = "Invalid address.";
                    user_address= addressFromTF;
                }
                UpdateBtnState();
            }
        });

        phonetf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String contactFromTF = phonetf.getText();
                if (contactFromTF.isEmpty()){
                    PhoneIsValid= false; phoneError = true;//Invalid
                    phone_no= contactFromTF;
                    phoneErrorMessage = "You need to have a contact number.";
                } 
                else if (contactFromTF.matches("^\\d{10}$")){ //regex to match phone number
                    PhoneIsValid= true;  phoneError = false;
                    phone_no= contactFromTF; //Valid
                } 
                else{
                    PhoneIsValid= false; phoneError = true; //Invalid
                    phone_no= contactFromTF;
                    phoneErrorMessage = "Invalid phone number.";
                }
                UpdateBtnState();
            }
        });

        maleRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                SexIsSelected = true;  sexError = false;
                user_sex = "Male";
                UpdateBtnState();
            }
        });
        femaleRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                SexIsSelected = true; sexError = false;
                user_sex = "Female";
                UpdateBtnState();
            }
        });
        othersRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                SexIsSelected = true; sexError = false;
                user_sex = "Others";
                UpdateBtnState();
            }
        });
        if (user_sex.equals("none")){
            sexErrorMessage = "You need to select a sex."; sexError = true;
        }
//------------------------------------------
        usertf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // passtf.requestFocus();
                UpdateBtnState();
            }
        });
        // passtf.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e) {
        //         addresstf.requestFocus();
        //         UpdateBtnState();
        //     }
        // });
        addresstf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                phonetf.requestFocus();
                UpdateBtnState();
            }
        });
        phonetf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maleRB.requestFocus(); 
                maleRB.setSelected(true);
                SexIsSelected = true; 
                UpdateBtnState();
            }
        });
        maleRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitButton.requestFocus(); 
                UpdateBtnState(); 
            }
        });
        femaleRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitButton.requestFocus();  
                UpdateBtnState();
            }
        });
        othersRB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitButton.requestFocus(); 
                UpdateBtnState(); 
            }
        });
        
        //----------------------------------------------------------------------------------------------------------------
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
                new member_dashboard();
                accountinfo_f.dispose();
            }
        });

        //----------------------------------------------------------------------------------------------------------------
        accountinfo_f.add(p1);
        accountinfo_f.add(back);
        accountinfo_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountinfo_f.setVisible(true);
    }
//----------------------------------------------------------------------------------------------------------------
    private void UpdateBtnState() {
        if (NameIsValid  && AddressIsValid && PhoneIsValid && SexIsSelected){
            submitButton.setBackground(green);
            Valid = true;
        } 
        else if (!NameIsValid  || !AddressIsValid || !PhoneIsValid || !SexIsSelected){
            submitButton.setBackground(red);
            Valid = false;
        } 
        else{
            submitButton.setBackground(buttongrey);
            Valid = false;
        }
        //---------------------------
        if (dataAlreadyExistsInDB){
            if (name.equals(memberinfo[0])  && user_address.equals(memberinfo[3]) && phone_no.equals(memberinfo[2]) && user_sex.equals(memberinfo[4])){
                submitButton.setBackground(buttongrey);
                Valid = false;
                dupError = false;
                duplicateErrorMessage = "Your user-data is same as the old one.";
            }
        }
    }
}
