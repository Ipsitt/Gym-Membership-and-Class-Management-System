import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class admin_dashboard {
    public admin_dashboard() {
        global.searched_username = "";
        JFrame admin_f = new JFrame("Gym Membership And Class Management System");
        admin_f.setResizable(false);
        admin_f.setSize(500, 700);
        admin_f.getContentPane().setBackground(Color.decode("#121212"));
        admin_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
//------------------------------------------------------------------------------------------------------------------
        //Attendance tab
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,50,380,80);
        p2.setLayout(null);
        JLabel account= new JLabel("Member Attendance");
        account.setForeground(white); 
        account.setBounds(10,1,200,50);
        account.setFont(new Font("Arial",Font.BOLD,15));
        p2.add(account);

        //attendance button
        JButton manAcc = new JButton("Attendance");
        manAcc.setBounds(235,40,130,25);
        manAcc.setBackground(green);
        manAcc.setForeground(Color.WHITE);
        p2.add(manAcc);
        // --------------------------------------------------------
        manAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new admin_member_attendance();
                admin_f.dispose();
            }
        });

//------------------------------------------------------------------------------------------------------------------  
        //Create account tab
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,200,380,100);
        p1.setLayout(null);
       
        JLabel plan= new JLabel("Create Account:");
        plan.setFont(new Font("Arial", Font.BOLD, 20));
        plan.setForeground(green); 
        plan.setBounds(10,10,400,50);

        JLabel p1m= new JLabel("Total Members:"+DB.getTotalNumberOfMembers());
        p1m.setForeground(white); 
        p1m.setBounds(10,40,400,50);
        p1.add(p1m);
        JLabel p1t= new JLabel("Total Trainers: "+DB.getTotalNumberOfTrainer());
        p1t.setForeground(white); 
        p1t.setBounds(10,60,400,50);
        p1.add(p1t);

        //get/extend plan button
        JButton manPlan = new JButton("Create");
        manPlan.setBounds(235, 60, 130, 25);
        manPlan.setBackground(green);
        manPlan.setForeground(Color.WHITE);
        p1.add(manPlan);
        p1.add(plan);
        // --------------------------------------------------------
        manPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateAccount();//Redirect to manage plans page
                admin_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //paisa tab
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,370,380,100);
        p3.setLayout(null);
        int no_of_users = DB.getTotalNumberOfAccounts();
        JLabel trainlbl= new JLabel("User Accounts");
        trainlbl.setFont(new Font("Arial",Font.BOLD,20));
        JLabel trainerName= new JLabel("Total User Accounts: "+no_of_users);
        trainlbl.setForeground(green); 
        trainlbl.setBounds(10,10,400,50);
        p3.add(trainlbl);
        trainerName.setForeground(white); 
        trainerName.setBounds(10,40,300,50);
        p3.add(trainerName);

        //manage trainer button
        JButton manTrainer = new JButton("View Accounts");
        manTrainer.setBounds(235,60,130,25);
        manTrainer.setBackground(green);
        manTrainer.setForeground(Color.WHITE);
        p3.add(manTrainer);
        // --------------------------------------------------------
        manTrainer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new admin_view_members();
                admin_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //progress tab
        JPanel p4 = new JPanel();
        p4.setBackground(grey);
        p4.setBounds(50,540,380,100);
        p4.setLayout(null);
        String week = "";//
        String till_month = "";//
        String till_6_months = "";//
        String untill_1kDays = "";//

        String[][] current_rates= DB.getAmounts();
        if (current_rates != null){
            week = "Rs. "+current_rates[0][0];//
            till_month = "Rs. "+current_rates[0][1];//
            till_6_months = "Rs. "+current_rates[0][2];//
            untill_1kDays = "Rs. "+current_rates[0][3];//
        }

        JLabel progresslbl= new JLabel("Payment Rates:");
        progresslbl.setFont(new Font("Arial",Font.BOLD,20));
        progresslbl.setForeground(green); 
        progresslbl.setBounds(10,10,200,50);
        p4.add(progresslbl);        
        JLabel cbw= new JLabel("1-6 Days:   "+week);
        cbw.setForeground(white); 
        cbw.setBounds(10,40,400,50);
        p4.add(cbw);
        JLabel cbft= new JLabel("7-30 Days: "+till_month);
        cbft.setForeground(white); 
        cbft.setBounds(10,60,400,50);
        p4.add(cbft);
        JLabel sixm= new JLabel("31-180 Days: "+till_6_months );
        sixm.setForeground(white); 
        sixm.setBounds(200,40,400,50);
        p4.add(sixm);
        JLabel highest= new JLabel("180+ Days:    "+untill_1kDays);
        highest.setForeground(white); 
        highest.setBounds(200,60,400,50);
        p4.add(highest);

        //manage progress button
        JButton manProgress = new JButton("Update Rates");
        manProgress.setBounds(235,20,130,25);
        //manProgress.setBounds(235,60,130,25);
        manProgress.setBackground(green);
        manProgress.setForeground(Color.WHITE);
        p4.add(manProgress);
        // --------------------------------------------------------
        manProgress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new admin_payment_rates(); //Redirest to payments change page
                admin_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
//Back Button
        JButton back= new JButton("<");
        back.setFont(new Font("Arial", Font.BOLD, 17));
        back.setBounds(0,0,50,50);
        back.setBackground(Color.decode("#121212"));
        back.setForeground(green);
        back.setBorderPainted(false); 
        back.setFocusPainted(false);  
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               // DB.setLoginStatus("logged_out", "Last Logged In Acc- > "+global.username,0);
                admin_f.dispose();
                new login();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Add Panels-----
        admin_f.add(p1);
        admin_f.add(p2);
        admin_f.add(p3);
        admin_f.add(p4);
        admin_f.add(back);
        admin_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin_f.setVisible(true);
    }
}
