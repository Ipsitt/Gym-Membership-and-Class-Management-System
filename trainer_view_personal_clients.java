import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
//TRAINER PAGE
public class trainer_view_personal_clients {
    boolean Valid = false; //boolean to see if username entered is valid or not
    public trainer_view_personal_clients(){
        JFrame view_personal_clients_f= new JFrame("Gym Membership And Class Management System");
        view_personal_clients_f.setResizable(false);
        view_personal_clients_f.setSize(500, 700);
        view_personal_clients_f.getContentPane().setBackground(Color.decode("#121212"));
        view_personal_clients_f.setLayout(null);

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

        JLabel viewlbl= new JLabel("View:");
        viewlbl.setForeground(green); 
        viewlbl.setBounds(10,60,70,30);
        p1.add(viewlbl);

        JButton profile = new JButton("Profile");
        profile.setBackground(buttongrey);
        profile.setForeground(white);
        profile.setBounds(80,60,135,25);
        p1.add(profile);
        JButton progress = new JButton("Progress");
        progress.setBackground(buttongrey);
        progress.setForeground(white);
        progress.setBounds(225,60,135,25);
        p1.add(progress);

        //Check if username entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String username = tf.getText();
                    if(username.isEmpty()){
                        profile.setBackground(buttongrey); progress.setBackground(buttongrey); usernamelbl.setForeground(green); Valid= false;
                    }
                    else{
                        if(DB.doesMemberExistIn_TMRelation(username)){
                            profile.setBackground(green); progress.setBackground(green); usernamelbl.setForeground(green); Valid= true;
                        }
                        else{
                            profile.setBackground(red); progress.setBackground(red); usernamelbl.setForeground(red); Valid= false;
                        }
                    }
            }
        });

        //Action Listener for both action buttons:
        profile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    global.selected_member = tf.getText();
                    view_personal_clients_f.dispose();
                    new admin_manage_memberships();
                }
            }
        });

        progress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    global.selected_member = tf.getText();
                    view_personal_clients_f.dispose();
                    new trainer_view_progress();
                }
            }
        });

//------------------------------------------------------------------------------------------------------------------
        //Second Panel
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,420);
        p2.setLayout(null);

        JLabel clients_Label= new JLabel("Personal Training Clients:");
        clients_Label.setFont(new Font("Arial",Font.BOLD,20));
        clients_Label.setForeground(green); 
        clients_Label.setBounds(10,5,400,50);
        p2.add(clients_Label);

        //table of pending requets
        String[] title={"Name","Username","Client Since"};
        String[][] data= DB.getPersonalClients(global.username);

        if (data.length == 0) {
            data= new String[][] {{"","",""}};
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
        sp.setBounds(10,60,360,345);

        JTableHeader titlePart = PRtable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        sp.add(titlePart);

        p2.add(sp);

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
                new trainer_dashboard();
                view_personal_clients_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        view_personal_clients_f.add(p1);
        view_personal_clients_f.add(p2);
        view_personal_clients_f.add(back);
        view_personal_clients_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view_personal_clients_f.setVisible(true);

    }
}
