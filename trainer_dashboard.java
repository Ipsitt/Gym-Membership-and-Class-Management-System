import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class trainer_dashboard {
    public trainer_dashboard() {
        
        JFrame trainer_f = new JFrame("Gym Membership And Class Management System");
        trainer_f.setResizable(false);
        trainer_f.setSize(500, 700);
        trainer_f.getContentPane().setBackground(Color.decode("#121212"));
        trainer_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
//------------------------------------------------------------------------------------------------------------------
        //trainer account info
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,50,380,80);
        p2.setLayout(null);
        JLabel account= new JLabel(global.username);
        account.setForeground(white); 
        account.setBounds(10,1,200,50);
        account.setFont(new Font("Arial",Font.BOLD,15));
        p2.add(account);

        //manage account button
        JButton manAcc = new JButton("Manage Account");
        manAcc.setBounds(235,45,130,25);
        manAcc.setBackground(green);
        manAcc.setForeground(Color.WHITE);
        p2.add(manAcc);

        JButton TrainerAttendance = new JButton("Attendance");
        TrainerAttendance.setBounds(235,10,130,25);
        TrainerAttendance.setBackground(green);
        TrainerAttendance.setForeground(Color.WHITE);
        p2.add(TrainerAttendance);

        int total_days = DB.getTotalAttendedDays();
        JLabel attendedDays= new JLabel("Days Attended: "+total_days);
        attendedDays.setForeground(white); 
        attendedDays.setBounds(10,30,200,50);
        p2.add(attendedDays);

        // --------------------------------------------------------
        manAcc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // JOptionPane.showMessageDialog(null, "Redirect to manage account frame");
               new trainer_account_info();//Redirect to trainer info page
               trainer_f.dispose();
            }
        });
            // --------------------------------------------------------
            TrainerAttendance.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   // JOptionPane.showMessageDialog(null, "Redirect to manage account frame");
                   new trainer_attendance();//Redirect to trainer info page
                   trainer_f.dispose();
                }
            });
        

//------------------------------------------------------------------------------------------------------------------        
        //currently training clients
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,200,380,100);
        p1.setLayout(null);
        Integer no_of_clients = DB.getNumberOfPersonalClients(global.username);
        JLabel plan= new JLabel("Currently Training Clients:");
        plan.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel trainClients= new JLabel(no_of_clients+" Clients");
        plan.setForeground(green); 
        plan.setBounds(10,10,400,50);
        p1.add(plan);
        trainClients.setForeground(white); 
        trainClients.setBounds(10,40,200,50);
        p1.add(trainClients);

        //get/extend plan button
        JButton manPlan = new JButton("View Clients");
        manPlan.setBounds(235, 60, 130, 25);
        manPlan.setBackground(green);
        manPlan.setForeground(Color.WHITE);
        p1.add(manPlan);
        // --------------------------------------------------------
        manPlan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new trainer_view_personal_clients();//Redirect to manage plans page
                trainer_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Currently Pending Requests
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,370,380,100);
        p3.setLayout(null);
        int no_of_req = DB.getNumberOfPersonalClientsRequests(global.username);
        JLabel trainlbl= new JLabel("Currently Pending Requests:");
        trainlbl.setFont(new Font("Arial",Font.BOLD,20));
        JLabel trainerName= new JLabel(no_of_req+" Personal Training Requests");
        trainlbl.setForeground(green); 
        trainlbl.setBounds(10,10,400,50);
        p3.add(trainlbl);
        trainerName.setForeground(white); 
        trainerName.setBounds(10,40,300,50);
        p3.add(trainerName);

        //manage trainer button
        JButton manTrainer = new JButton("View Requests");
        manTrainer.setBounds(235,60,130,25);
        manTrainer.setBackground(green);
        manTrainer.setForeground(Color.WHITE);
        p3.add(manTrainer);
        // --------------------------------------------------------
        manTrainer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new trainer_view_personal_client_requests();
                trainer_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //class tab
        JPanel p4 = new JPanel();
        p4.setBackground(grey);
        p4.setBounds(50,540,380,100);
        p4.setLayout(null);
        JLabel classlbl= new JLabel("Classes:");
        classlbl.setFont(new Font("Arial",Font.BOLD,20));
        classlbl.setForeground(green); 
        classlbl.setBounds(10,10,200,50);
        p4.add(classlbl);        
        JLabel no_of_classes= new JLabel("No. of classes: "+DB.getNoOfPersonalClasses());
        no_of_classes.setForeground(white); 
        no_of_classes.setBounds(10,40,300,50);
        p4.add(no_of_classes);

        //manage progress button
        JButton schClass = new JButton("Schedule Class");
        schClass.setBounds(235,20,130,25);
        schClass.setBackground(green);
        schClass.setForeground(Color.WHITE);
        p4.add(schClass);
        // --------------------------------------------------------
        schClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new trainer_schedule_classes(); 
                trainer_f.dispose();
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
                trainer_f.dispose();
                new login();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Add Panels-----
        trainer_f.add(p1);
        trainer_f.add(p2);
        trainer_f.add(p3);
        trainer_f.add(p4);
        trainer_f.add(back);
        trainer_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        trainer_f.setVisible(true);
    }
}
