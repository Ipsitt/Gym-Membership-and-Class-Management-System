import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class member_dashboard {
    public member_dashboard() {
        
        JFrame member_f = new JFrame("Gym Membership And Class Management System");
        member_f.setResizable(false);
        member_f.setSize(500, 700);
        member_f.getContentPane().setBackground(Color.decode("#121212"));
        member_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
//------------------------------------------------------------------------------------------------------------------
        //account info
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,50,380,80);
        p2.setLayout(null);
        JLabel account= new JLabel(global.username);
        account.setForeground(white); 
        account.setBounds(10,1,200,50);
        account.setFont(new Font("Arial",Font.BOLD,15));
        p2.add(account);

        int total_days = DB.getTotalAttendedDays();
        JLabel attendedDays= new JLabel("Days Attended: "+total_days);
        attendedDays.setForeground(white); 
        attendedDays.setBounds(10,30,200,50);
        p2.add(attendedDays);

        //manage account button
        JButton manAcc = new JButton("Manage Account");
        manAcc.setBounds(235,45,130,25);
        manAcc.setBackground(green);
        manAcc.setForeground(Color.WHITE);
        p2.add(manAcc);

        JButton MemberAttendance = new JButton("Attendance");
        MemberAttendance.setBounds(235,10,130,25);
        MemberAttendance.setBackground(green);
        MemberAttendance.setForeground(Color.WHITE);
        p2.add(MemberAttendance);
        // --------------------------------------------------------
        manAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new member_account_info();
                member_f.dispose();
            }
        });

        MemberAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // JOptionPane.showMessageDialog(null, "Redirect to manage account frame");
               new member_attendance();//Redirect to member attendace page
               member_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------        
        //current plan
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,200,380,100);
        p1.setLayout(null);
        Integer daysRemaining = DB.getDaysRemaining(global.username);
        JLabel plan= new JLabel("Current Plan:");
        plan.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel planDays= new JLabel(daysRemaining+" Days Remaining");
        plan.setForeground(green); 
        plan.setBounds(10,10,200,50);
        p1.add(plan);
        planDays.setForeground(white); 
        planDays.setBounds(10,40,200,50);
        p1.add(planDays);

        //get/extend plan button
        JButton manPlan = new JButton("Manage Plan");
        manPlan.setBounds(235, 60, 130, 25);
        manPlan.setBackground(green);
        manPlan.setForeground(Color.WHITE);
        p1.add(manPlan);
        // --------------------------------------------------------
        manPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new manage_plan();//Redirect to manage plans page
                member_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //trainer tab
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,370,380,100);
        p3.setLayout(null);
        String trainer = DB.getTrainerName(global.username);
        if (!DB.getTrainerStatus().equals("Accepted")){
            trainer = "-";
        }
        JLabel trainlbl= new JLabel("Current Trainer:");
        trainlbl.setFont(new Font("Arial",Font.BOLD,20));
        JLabel trainerName= new JLabel(trainer);
        trainlbl.setForeground(green); 
        trainlbl.setBounds(10,10,200,50);
        p3.add(trainlbl);
        trainerName.setForeground(white); 
        trainerName.setBounds(10,40,200,50);
        p3.add(trainerName);

          //manage classes button
          JButton trainerclass = new JButton("Book Classes");
          trainerclass.setBounds(235,20,130,25);
          trainerclass.setBackground(green);
          trainerclass.setForeground(Color.WHITE);
          p3.add(trainerclass);

           // --------------------------------------------------------
        trainerclass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              //  JOptionPane.showMessageDialog(null, "Redirect to manage Trainer frame");
              new member_bookclass();//Redirect to manage plans page
              member_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------

        //manage trainer button
        JButton manTrainer = new JButton("Manage Trainer");
        manTrainer.setBounds(235,60,130,25);
        manTrainer.setBackground(green);
        manTrainer.setForeground(Color.WHITE);
        p3.add(manTrainer);
        // --------------------------------------------------------
        manTrainer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              //  JOptionPane.showMessageDialog(null, "Redirect to manage Trainer frame");
              new member_trainer_request();//Redirect to manage plans page
              member_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //progress tab
        JPanel p4 = new JPanel();
        p4.setBackground(grey);
        p4.setBounds(50,540,380,100);
        p4.setLayout(null);
        String bw = "-";
        String bfp = "-";

        String[][] current_metrics= DB.getProgressRecords(global.username);
        if (current_metrics != null && current_metrics.length > 0) {
            bw = current_metrics[0][1]+" kgs";
            bfp = current_metrics[0][2]+ "%";
        }

        JLabel progresslbl= new JLabel("Current Statistics:");
        progresslbl.setFont(new Font("Arial",Font.BOLD,20));
        progresslbl.setForeground(green); 
        progresslbl.setBounds(10,10,200,50);
        p4.add(progresslbl);        
        JLabel cbw= new JLabel("Current Bodyweight: "+bw);
        cbw.setForeground(white); 
        cbw.setBounds(10,40,400,50);
        p4.add(cbw);
        JLabel cbft= new JLabel("Current Body Fat%: "+bfp);
        cbft.setForeground(white); 
        cbft.setBounds(10,60,400,50);
        p4.add(cbft);

        //manage progress button
        JButton manProgress = new JButton("Update Progress");
        manProgress.setBounds(235,20,130,25);
        //manProgress.setBounds(235,60,130,25);
        manProgress.setBackground(green);
        manProgress.setForeground(Color.WHITE);
        p4.add(manProgress);
        // --------------------------------------------------------
        manProgress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new manage_progress(); //Redirest to update progress page
                member_f.dispose();
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
                //DB.setLoginStatus("logged_out", "Last Logged In Acc- > "+global.username,0);
                member_f.dispose();
                new login();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Add Panels-----
        member_f.add(p1);
        member_f.add(p2);
        member_f.add(p3);
        member_f.add(p4);
        member_f.add(back);
        member_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        member_f.setVisible(true);
    }
}
