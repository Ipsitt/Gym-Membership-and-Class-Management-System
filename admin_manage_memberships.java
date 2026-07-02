import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;
//ADMIN PAGE
public class admin_manage_memberships {
    boolean Valid = false; //boolean to see if username entered is valid or not
    public admin_manage_memberships(){
        JFrame manageMem_f= new JFrame("Gym Membership And Class Management System");
        manageMem_f.setResizable(false);
        manageMem_f.setSize(500, 700);
        manageMem_f.getContentPane().setBackground(Color.decode("#121212"));
        manageMem_f.setLayout(null);

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

        JButton approve = new JButton("Approve");
        approve.setBackground(buttongrey);
        approve.setForeground(white);
        approve.setBounds(80,60,135,25);
        p1.add(approve);
        JButton disapprove = new JButton("Dispprove");
        disapprove.setBackground(buttongrey);
        disapprove.setForeground(white);
        disapprove.setBounds(225,60,135,25);
        p1.add(disapprove);

        //Check if username entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String username = tf.getText();
                    if(username.isEmpty()){
                        approve.setBackground(buttongrey); disapprove.setBackground(buttongrey); usernamelbl.setForeground(green); Valid= false;
                    }
                    else{
                        if(DB.doesUsernameExist(username)){
                            approve.setBackground(green); disapprove.setBackground(red); usernamelbl.setForeground(green); Valid= true;
                        }
                        else{
                            approve.setBackground(buttongrey); disapprove.setBackground(buttongrey); usernamelbl.setForeground(red); Valid= false;
                        }
                    }
            }
        });

        //Action Listener for both action buttons:
        approve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    String username = tf.getText();
                    DB.acceptMembership(username);
                    DB.deleteRequest(username);
                    manageMem_f.dispose();
                    new admin_manage_memberships();
                }
            }
        });

        disapprove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                    String username = tf.getText();
                    DB.deleteRequest(username);
                    manageMem_f.dispose();
                    new admin_manage_memberships();
                }
            }
        });

//------------------------------------------------------------------------------------------------------------------
        //Second Panel
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,420);
        p2.setLayout(null);

        JLabel pending_req= new JLabel("Pending Membership Request:");
        pending_req.setFont(new Font("Arial",Font.BOLD,20));
        pending_req.setForeground(green); 
        pending_req.setBounds(10,5,400,50);
        p2.add(pending_req);

        //table of pending requets
        String[] title={"Username","Days","Amount","Date"};
        String[][] data= DB.getPendingRequests();

        if (data.length == 0) {
            data= new String[][] {{"","","",""}};
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
                new admin_dashboard();
                manageMem_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        manageMem_f.add(p1);
        manageMem_f.add(p2);
        manageMem_f.add(back);
        manageMem_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manageMem_f.setVisible(true);

    }
}
