import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;


public class admin_member_attendance {
    boolean Valid = false; //boolean to see if username entered is valid or not
    boolean valid_cancel_req_btn = false;
    public admin_member_attendance(){
        JFrame manageAttendance_f= new JFrame("Gym Membership And Class Management System");
        manageAttendance_f.setResizable(false);
        manageAttendance_f.setSize(500, 700);
        manageAttendance_f.getContentPane().setBackground(Color.decode("#121212"));
        manageAttendance_f.setLayout(null);

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

        JButton attendancerequest = new JButton("Present");
        attendancerequest.setBackground(buttongrey);
        attendancerequest.setForeground(white);
        attendancerequest.setBounds(80,60,280,25);
        p1.add(attendancerequest);
        // JButton profile = new JButton("View Profile");
        // profile.setBackground(buttongrey);
        // profile.setForeground(white);
        // profile.setBounds(225,60,135,25);
        // p1.add(profile);

        //Check if username entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String username = tf.getText();
                    if(username.isEmpty()){
                        attendancerequest.setBackground(buttongrey); usernamelbl.setForeground(green); Valid= false;
                    }
                    else{
                        if(DB.doesAccountUsernameExist(username)){
                            attendancerequest.setBackground(green); usernamelbl.setForeground(green); Valid= true;
                        }
                        else{
                            attendancerequest.setBackground(buttongrey); usernamelbl.setForeground(red); Valid= false;
                        }
                    }
            }
        });

        //Action Listener for both action buttons:
        attendancerequest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    if(DB.checkUsernameForAttendance(tf.getText())){
                        JOptionPane.showMessageDialog(null,"This user has already marked present");
                    }
                    else {
                        DB.insert_attendees_info(tf.getText());
                        
                        manageAttendance_f.dispose();
                        new admin_member_attendance();
                    }


                }
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Second Panel
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,400);
        p2.setLayout(null);

        JLabel pending_req= new JLabel("Todays Attendees:");
        pending_req.setFont(new Font("Arial",Font.BOLD,20));
        pending_req.setForeground(green); 
        pending_req.setBounds(10,5,400,50);
        p2.add(pending_req);

        //table of pending requets
        String[] title={"Username","Date"};
        String[][] data= DB.getListOfAttendies();

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
        sp.setBounds(10,60,360,319);

        JTableHeader titlePart = PRtable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        sp.add(titlePart);

        p2.add(sp);

//------------------------------------------------------------------------------------------------------------------
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
                new admin_dashboard();
                manageAttendance_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        manageAttendance_f.add(p1);
        manageAttendance_f.add(p2);
        // manageAttendance_f.add(p3);
        manageAttendance_f.add(back);
        manageAttendance_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manageAttendance_f.setVisible(true);

    }
}