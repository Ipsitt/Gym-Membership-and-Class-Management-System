import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;


public class member_trainer_request {
    boolean Valid = false; //boolean to see if username entered is valid or not
    boolean valid_cancel_req_btn = false;
    public member_trainer_request(){
        JFrame manageTrainer_f= new JFrame("Gym Membership And Class Management System");
        manageTrainer_f.setResizable(false);
        manageTrainer_f.setSize(500, 700);
        manageTrainer_f.getContentPane().setBackground(Color.decode("#121212"));
        manageTrainer_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
        Color red=(Color.decode("#f03434")); 
        Color buttongrey=(Color.decode("#a6a6a6")); 

//------------------------------------------------------------------------------------------------------------------
        //First PAnel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,50,380,100);
        p1.setLayout(null);

        JLabel usernamelbl= new JLabel("Username:");
        usernamelbl.setForeground(green); 
        usernamelbl.setBounds(10,10,200,50);
        p1.add(usernamelbl);

        JTextField tf = new JTextField();
        tf.setBackground(white);
        tf.setBounds(80,25,280,20);
        p1.add(tf);

        JLabel actionlbl= new JLabel("Action:");
        actionlbl.setForeground(green); 
        actionlbl.setBounds(10,60,70,30);
        p1.add(actionlbl);

        JButton request = new JButton("Send Request");
        request.setBackground(buttongrey);
        request.setForeground(white);
        request.setBounds(80,60,135,25);
        p1.add(request);
        JButton profile = new JButton("View Profile");
        profile.setBackground(buttongrey);
        profile.setForeground(white);
        profile.setBounds(225,60,135,25);
        p1.add(profile);

        //Check if username entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String username = tf.getText();
                    if(username.isEmpty()){
                        request.setBackground(buttongrey); profile.setBackground(buttongrey); usernamelbl.setForeground(green); Valid= false;
                    }
                    else{
                        if(DB.doesTrainerUsernameExist(username)){
                            request.setBackground(green); profile.setBackground(green); usernamelbl.setForeground(green); Valid= true;
                        }
                        else{
                            request.setBackground(buttongrey); profile.setBackground(buttongrey); usernamelbl.setForeground(red); Valid= false;
                        }
                    }
            }
        });

        //Action Listener for both action buttons:
        request.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    if(DB.getTrainerStatus().equals("Accepted")){
                        JOptionPane.showMessageDialog(null,"You already have a personal trainer.");
                    }
                    else{
                    String username = tf.getText();
                    DB.sendTrainerRequest(username);
                    manageTrainer_f.dispose();
                    new member_trainer_request();
                    }
                }
            }
        });

        profile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    global.selected_member = tf.getText();
                    manageTrainer_f.dispose();
                    new member_view_Trainer_account();
                }
            }
        });

//------------------------------------------------------------------------------------------------------------------
        //Second Panel
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,200);
        p2.setLayout(null);

        JLabel pending_req= new JLabel("List of Trainers:");
        pending_req.setFont(new Font("Arial",Font.BOLD,20));
        pending_req.setForeground(green); 
        pending_req.setBounds(10,5,400,50);
        p2.add(pending_req);

        //table of pending requets
        String[] title={"Username","Name"};
        String[][] data= DB.getListOfTrainers();

        if (data.length == 0) {
            data= new String[][] {{"",""}};
        }
        
        JTable PRtable=new JTable(data,title);
        //PRtable.setBounds(10,95,360,20);
        PRtable.setBackground(grey);
        PRtable.setForeground(white);
        PRtable.setGridColor(green);
        //dt.setFont(new Font("Arial", Font.PLAIN, 12));
        PRtable.setRowHeight(25);
        PRtable.setBorder(BorderFactory.createLineBorder(green,1));
        JScrollPane sp= new JScrollPane(PRtable); //to scroll
        sp.getViewport().setBackground(grey); // to remove white bland thingie if less data
        sp.setBorder(BorderFactory.createEmptyBorder()); 
        sp.setBounds(10,60,360,119);

        JTableHeader titlePart = PRtable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        sp.add(titlePart);

        p2.add(sp);

//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
        //Third panel -> table of pending request
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,450,380,150);
        p3.setLayout(null);

        JLabel sending_req= new JLabel("Pending Request:");
        sending_req.setFont(new Font("Arial",Font.BOLD,20));
        sending_req.setForeground(green); 
        sending_req.setBounds(10,10,200,50);
        p3.add(sending_req);

        DB.requestTrainerContent requestTrainerContent = new DB.requestTrainerContent("","","");
        boolean hasRequest = DB.read_requestTrainerContent(requestTrainerContent, global.username);

    //String reqORupdate = "Send";//Button text for button on second panel
        //creating table
        String[][] data1;
        if (hasRequest) {
            //reqORupdate = "Update";
            data1= new String[][]{{String.valueOf(requestTrainerContent.username),String.valueOf(requestTrainerContent.date),String.valueOf(requestTrainerContent.status)}};
        }
        else{
            data1 = new String[][]{{"","",""}};
            //reqORupdate = "Send";
        }
        JButton cr = new JButton("Cancel Request");
        cr.setBounds(235,25, 135, 25);
        cr.setBackground(buttongrey);
        cr.setForeground(white);
        if (!DB.getTrainerStatus().equals("Accepted") && !DB.getTrainerStatus().equals("Not Requested")) {
            cr.setBackground(red);
            valid_cancel_req_btn = true;

        }
        

        cr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(valid_cancel_req_btn){
                    DB.deleteTrainerRequest();
                    new member_trainer_request();
                    manageTrainer_f.dispose();
                }
            }
        });
        

        String[] title1={"Username","Date","Status"};
        JTable dt=new JTable(data1,title1);
        dt.setBounds(10,95,360,20);
        dt.setBackground(grey);
        dt.setForeground(white);
        dt.setGridColor(green);
        dt.setRowHeight(25);
        dt.setBorder(BorderFactory.createLineBorder(green,1));
        p3.add(dt);

        JTableHeader titlePart1 = dt.getTableHeader();
        titlePart1.setBounds(10,75,360,20);
        titlePart1.setBackground(grey);
        titlePart1.setForeground(green);
        titlePart1.setFont(new Font("Arial",Font.BOLD,12));
        titlePart1.setBorder(BorderFactory.createLineBorder(green,1));
        p3.add(titlePart1);
        p3.add(cr);
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
                new member_dashboard();
                manageTrainer_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        manageTrainer_f.add(p1);
        manageTrainer_f.add(p2);
        manageTrainer_f.add(p3);
        manageTrainer_f.add(back);
        manageTrainer_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manageTrainer_f.setVisible(true);

    }
}
