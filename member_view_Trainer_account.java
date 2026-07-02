import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class member_view_Trainer_account {

    // boolean Valid= false;
    // boolean NameIsValid = false; String name = ""; boolean nameError = false;  String nameErrorMessage = "";
    // boolean PassIsValid = false; String password = ""; boolean passError = false;  String passErrorMessage = "";
    // boolean SpecialityIsValid = false; String user_speciality = ""; boolean specialityError = false;  String specialityErrorMessage = "";
    // boolean PhoneIsValid = false; String phone_no = ""; boolean phoneError = false;  String phoneErrorMessage = "";
    // //boolean SexIsSelected = false; String user_sex = "none"; boolean sexError = false;  String sexErrorMessage = "";
    // boolean dataAlreadyExistsInDB = false; boolean dupError = false;  String duplicateErrorMessage = "";

    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white = Color.decode("#dbdbdb");
    Color red = Color.decode("#f03434");
    Color buttongrey = Color.decode("#a6a6a6");

    // JButton submitButton; String button_text = "Update";

    String[] trainerinfo = DB.getTrainerInfo(global.selected_member);

    public member_view_Trainer_account() {
        JFrame accountinfo_f = new JFrame("Gym Membership And Class Management System");
        accountinfo_f.setResizable(false);
        accountinfo_f.setSize(500, 700);
        accountinfo_f.getContentPane().setBackground(Color.decode("#121212"));
        accountinfo_f.setLayout(null);

        //------------------------------------------------------------------------------------------------------------------
        // First Panel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50, 50, 380, 150);
        p1.setLayout(null);

        //------------------------------------------------------------------------------------------------------------------
        // Username fields
        JLabel user = new JLabel("Full Name:");
        user.setBounds(10, 10, 70, 40);
        user.setForeground(green);
        JLabel usertf = new JLabel(trainerinfo[0]); 
        usertf.setBounds(90, 16, 360, 30);
        usertf.setForeground(white);
        p1.add(user);
        p1.add(usertf);

        // // Password fields
        // JLabel pass = new JLabel("Password:");
        // pass.setBounds(10, 100, 100, 40);
        // pass.setForeground(Color.decode("#dbdbdb"));
        // JLabel passtf = new JLabel();  
        // passtf.setBounds(10, 140, 360, 30);
        // passtf.setBackground(Color.decode("#dbdbdb"));
        // p1.add(pass);
        // p1.add(passtf);

        // Speciality fields
        JLabel speciality = new JLabel("Speciality:");
        speciality.setBounds(10, 50, 100, 40);
        speciality.setForeground(green);
        JLabel specialitytf = new JLabel(trainerinfo[2]); 
        specialitytf.setBounds(90, 56, 360, 30);
        specialitytf.setForeground(white);
        p1.add(speciality);
        p1.add(specialitytf);

        // Contact no
        JLabel phone = new JLabel("Contact No:");
        phone.setBounds(10, 90, 100, 40);
        phone.setForeground(green);
        JLabel phonetf = new JLabel(trainerinfo[3]);  
        phonetf.setBounds(90, 96, 360, 30);
        phonetf.setForeground(white);
        p1.add(phone);
        p1.add(phonetf);

        // // Sex
        // JLabel sex = new JLabel("Sex:");
        // sex.setBounds(10, 360, 100, 40);
        // sex.setForeground(white);

        // JRadioButton maleRB = new JRadioButton("Male"); 
        // maleRB.setBounds(10, 400, 100, 40);
        // maleRB.setBackground(grey);
        // maleRB.setForeground(Color.decode("#dbdbdb"));

        // JRadioButton femaleRB = new JRadioButton("Female");  
        // femaleRB.setBounds(120, 400, 100, 40);
        // femaleRB.setBackground(grey);
        // femaleRB.setForeground(Color.decode("#dbdbdb"));

        // JRadioButton othersRB = new JRadioButton("Other");
        // othersRB.setBounds(230, 400, 100, 40);
        // othersRB.setBackground(grey);
        // othersRB.setForeground(Color.decode("#dbdbdb"));

        // ButtonGroup regchoice = new ButtonGroup();
        // regchoice.add(maleRB);
        // regchoice.add(femaleRB);
        // regchoice.add(othersRB);

        // p1.add(maleRB);
        // p1.add(femaleRB);
        // p1.add(othersRB);
        // p1.add(sex);

        // int money = DB.getTotalPayment(global.username);

        // // Total money
        // JLabel amt = new JLabel("Total Payment: " + money);
        // amt.setBounds(10, 440, 100, 40);
        // amt.setForeground(white);
        // p1.add(amt);

        //----------------------------------------------------------------------------------------------------------------
        // Get trainer info from DB and assign to trainerinfo variables
//         if (trainerinfo[0].equals("empty")) {
//             button_text = "Save";
//             usertf.setText("");
//             passtf.setText(trainerinfo[1]); PassIsValid = true; //old password is obviously valid
//             specialitytf.setText("");
//             phonetf.setText("");
//         }else{//If data already exists in db
//             dataAlreadyExistsInDB = true; //data from last validation are always valid
//             NameIsValid = true; name = trainerinfo[0];
//             PassIsValid = true; password = trainerinfo[1];
//             SpecialityIsValid = true; user_speciality = trainerinfo[2];
//             PhoneIsValid = true; phone_no = trainerinfo[3];
//             // SexIsSelected = true; user_sex = trainerinfo[4];
//             usertf.setText(trainerinfo[0]);
//             passtf.setText(trainerinfo[1]);
//             specialitytf.setText(trainerinfo[2]);
//             phonetf.setText(trainerinfo[3]);
//             // String sexcheck = trainerinfo[4];
//             // if (sexcheck.equals("Male")) {
//             //     maleRB.setSelected(true); 
//             // } else if (sexcheck.equals("Female")) {
//             //     femaleRB.setSelected(true); 
//             // } else if (sexcheck.equals("Other")) {
//             //     othersRB.setSelected(true); 
//             // }
//         }

//         //Submit Button
//         submitButton = new JButton(button_text);
//         submitButton.setBounds(130, 490, 130, 25);
//         submitButton.setBackground(buttongrey);
//         submitButton.setForeground(Color.WHITE);
//         p1.add(submitButton);

//         submitButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent ae){
//                 if (Valid){
//                     DB.updateTrainerInfo(password,name,phone_no,user_speciality);
//                 }
//                 else{
//                     if (nameError){
//                     JOptionPane.showMessageDialog(null, nameErrorMessage);
//                     }
//                     else if(passError){
//                     JOptionPane.showMessageDialog(null, passErrorMessage);
//                     }
//                     else if(specialityError){
//                     JOptionPane.showMessageDialog(null, specialityErrorMessage);
//                     }
//                     else if(phoneError){
//                     JOptionPane.showMessageDialog(null, phoneErrorMessage); 
//                     }
//                     else if(dupError){
//                     JOptionPane.showMessageDialog(null, duplicateErrorMessage); 
//                     }
//                 }
//             }
//         });
//         //----------------------------------------------------------------------------------------------------------------
// //------------------------------------------
//         usertf.addKeyListener(new KeyAdapter(){
//             @Override
//             public void keyReleased(KeyEvent e) {
//                 String nameFromTF = usertf.getText();
//                 if (nameFromTF.isEmpty()){
//                     NameIsValid= false; nameError = true; //Invalid 
//                     name= nameFromTF;
//                     nameErrorMessage = "You need to enter your name.";
//                 } 
//                 else if (nameFromTF.matches("(?i)^[a-z]+(\\s[a-z]+)+$")){ 
//                     NameIsValid= true; 
//                     name= nameFromTF; nameError = false;//Valid
//                 } 
//                 else{
//                     NameIsValid= false; nameError = true;//Invalid
//                     name= nameFromTF;
//                     nameErrorMessage = "You need to enter your fullname.";
//                 }
//                 UpdateBtnState();
//             }
//         });

//         passtf.addKeyListener(new KeyAdapter(){
//             @Override
//             public void keyReleased(KeyEvent e) {
//                 String passFromTF = passtf.getText();
//                 if (passFromTF.isEmpty()){
//                     PassIsValid= false; passError = true;//Invalid
//                     password= passFromTF;
//                     passErrorMessage = "A Password is required to have an account.";
//                 } 
//                 else if (passFromTF.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,})")){ //regex to match ATLEAST ONE lowercase letter, uppercase letter, digit, special character and minimum of 8 length
//                     PassIsValid= true; 
//                     password= passFromTF; passError = false;//Valid
//                 } 
//                 else{
//                     PassIsValid= false; passError = true;//Invalid
//                     password= passFromTF;
//                     passErrorMessage = "Your Password needs to have atleast one uppercase letter, one lowercase letter, one special character, one digit and 8 characters long.";
//                 }
//                 UpdateBtnState();
//             }
//         });

//         specialitytf.addKeyListener(new KeyAdapter(){
//             @Override
//             public void keyReleased(KeyEvent e) {
//                 String specialityFromTF = specialitytf.getText();
//                 if (specialityFromTF.isEmpty()){
//                     SpecialityIsValid= false; specialityError = true;//Invalid
//                     user_speciality= specialityFromTF;
//                     specialityErrorMessage = "Are you homeless?";
//                 } 
//                 else if (specialityFromTF.matches("(?i).*[a-z]{3,}.*")){ //regex to match letters,digits,underscores,comma,dash,period
//                     SpecialityIsValid= true; specialityError = false;
//                     user_speciality= specialityFromTF; //Valid
//                 } 
//                 else{
//                     SpecialityIsValid= false; specialityError = true;//Invalid
//                     specialityErrorMessage = "Invalid speciality.";
//                     user_speciality= specialityFromTF;
//                 }
//                 UpdateBtnState();
//             }
//         });

//         phonetf.addKeyListener(new KeyAdapter(){
//             @Override
//             public void keyReleased(KeyEvent e) {
//                 String contactFromTF = phonetf.getText();
//                 if (contactFromTF.isEmpty()){
//                     PhoneIsValid= false; phoneError = true;//Invalid
//                     phone_no= contactFromTF;
//                     phoneErrorMessage = "You need to have a contact number.";
//                 } 
//                 else if (contactFromTF.matches("^\\d{10}$")){ //regex to match phone number
//                     PhoneIsValid= true;  phoneError = false;
//                     phone_no= contactFromTF; //Valid
//                 } 
//                 else{
//                     PhoneIsValid= false; phoneError = true; //Invalid
//                     phone_no= contactFromTF;
//                     phoneErrorMessage = "Invalid phone number.";
//                 }
//                 UpdateBtnState();
//             }
//         });

        // maleRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent ae){
        //         SexIsSelected = true;  sexError = false;
        //         user_sex = "Male";
        //         UpdateBtnState();
        //     }
        // });
        // femaleRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent ae){
        //         SexIsSelected = true; sexError = false;
        //         user_sex = "Female";
        //         UpdateBtnState();
        //     }
        // });
        // othersRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent ae){
        //         SexIsSelected = true; sexError = false;
        //         user_sex = "Others";
        //         UpdateBtnState();
        //     }
        // });
        // if (user_sex.equals("none")){
        //     sexErrorMessage = "You need to select a sex."; sexError = true;
        //}
//------------------------------------------
        // usertf.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e) {
        //         passtf.requestFocus();
        //         UpdateBtnState();
        //     }
        // });
        // passtf.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e) {
        //         specialitytf.requestFocus();
        //         UpdateBtnState();
        //     }
        // });
        // specialitytf.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e) {
        //         phonetf.requestFocus();
        //         UpdateBtnState();
        //     }
        // });
        // phonetf.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         maleRB.requestFocus(); 
        //         maleRB.setSelected(true);
        //         SexIsSelected = true; 
        //         UpdateBtnState();
        //     }
        // });
        // maleRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         submitButton.requestFocus(); 
        //         UpdateBtnState(); 
        //     }
        // });
        // femaleRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         submitButton.requestFocus();  
        //         UpdateBtnState();
        //     }
        // });
        // othersRB.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         submitButton.requestFocus(); 
        //         UpdateBtnState(); 
        //     }
        // });
        
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
                new member_trainer_request();
                accountinfo_f.dispose();
            }
        });

        //----------------------------------------------------------------------------------------------------------------
        accountinfo_f.add(p1);
        accountinfo_f.add(back);
        accountinfo_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountinfo_f.setVisible(true);
    }
}
//----------------------------------------------------------------------------------------------------------------
//     private void UpdateBtnState() {
//         if (NameIsValid && PassIsValid && SpecialityIsValid && PhoneIsValid){
//             submitButton.setBackground(green);
//             Valid = true;
//         } 
//         else if (!NameIsValid || !PassIsValid || !SpecialityIsValid || !PhoneIsValid){
//             submitButton.setBackground(red);
//             Valid = false;
//         } 
//         else{
//             submitButton.setBackground(buttongrey);
//             Valid = false;
//         }
//         //---------------------------
//         if (dataAlreadyExistsInDB){
//             if (name.equals(trainerinfo[0]) && password.equals(trainerinfo[1]) && user_speciality.equals(trainerinfo[2]) && phone_no.equals(trainerinfo[3])){
//                 submitButton.setBackground(buttongrey);
//                 Valid = false;
//                 dupError = false;
//                 duplicateErrorMessage = "Your user-data is same as the old one.";
//             }
//         }
//     }
// }
