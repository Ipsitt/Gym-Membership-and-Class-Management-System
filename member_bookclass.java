  // getTrainerClassID
    import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;


public class member_bookclass {
    boolean ValidP1 = false; //boolean to see if class_id entered is valid or not for panel 1
    public member_bookclass() {
        
        JFrame member_classes_f = new JFrame("Gym Membership And Class Management System");
        member_classes_f.setResizable(false);
        member_classes_f.setSize(500, 700);
        member_classes_f.getContentPane().setBackground(Color.decode("#121212"));
        member_classes_f.setLayout(null);

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

        JLabel class_id_lbl= new JLabel("Class ID:");
        class_id_lbl.setForeground(green); 
        class_id_lbl.setBounds(10,10,200,50);
        p1.add(class_id_lbl);

        JTextField tf = new JTextField();
        tf.setBackground(white);
        tf.setBounds(80,25,280,20);
        p1.add(tf);

        JButton details = new JButton("Details");
        details.setBackground(buttongrey);
        details.setForeground(white);
        details.setBounds( 225,60,135,25);
        p1.add(details);

        JButton joinClass = new JButton("Join");
        joinClass.setBackground(buttongrey);
        joinClass.setForeground(white);
        joinClass.setBounds(80 ,60,135,25);
        p1.add(joinClass);

                //Check if class_id entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String class_id = tf.getText().trim();
                if (class_id.isEmpty()) {
                    details.setBackground(buttongrey);
                    joinClass.setBackground(buttongrey);
                    class_id_lbl.setForeground(green);
                    ValidP1 = false;
                } else {
                    if (DB.doesClassIDExist(class_id)) {
                        details.setBackground(green);
                        joinClass.setBackground(green);
                        class_id_lbl.setForeground(green);
                        ValidP1 = true;
                    } else {
                        details.setBackground(buttongrey);
                        joinClass.setBackground(buttongrey);
                        class_id_lbl.setForeground(red);
                        ValidP1 = false;
                    }
                }
            }
        });
        

        //Check if class_id entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String class_id = tf.getText().trim();
                if (class_id.isEmpty()) {
                    details.setBackground(buttongrey);
                    class_id_lbl.setForeground(green);
                    ValidP1 = false;
                } else {
                    if (DB.doesClassIDExist(class_id)) {
                        details.setBackground(green);
                        class_id_lbl.setForeground(green);
                        ValidP1 = true;
                    } else {
                        details.setBackground(buttongrey);
                        class_id_lbl.setForeground(red);
                        ValidP1 = false;
                    }
                }
            }
        });
        

        //Action Listener for both action buttons:
        details.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidP1){
                    global.class_id = Integer.parseInt(tf.getText());
                    member_classes_f.dispose();
                    new member_view_class_details();
                }
            }
        });

        //Action Listener for both action buttons:
            joinClass.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ValidP1){
                        member_classes_f.dispose();
                        new member_bookclass();
                        DB.insertMembertoClass(Integer.parseInt(tf.getText()));
                    }
                }
            });

//------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------
        //list of class id panel
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,400);
        p2.setLayout(null);
    
 //table of members in class
        String[] title={"Class ID", "Date"};
        String[][] data= DB.getTrainerClassID();

        if (data.length == 0) {
            data= new String[][] {{"",""}};
        }

        JTable memberTable=new JTable(data,title);
        memberTable.setBackground(grey);
        memberTable.setForeground(white);
        memberTable.setGridColor(green);
        memberTable.setRowHeight(25);
        memberTable.setBorder(BorderFactory.createLineBorder(green,1));
        JScrollPane sp= new JScrollPane(memberTable); //to scroll
        sp.getViewport().setBackground(grey); // to remove white bland thingie if less data
        sp.setBorder(BorderFactory.createEmptyBorder()); 
        sp.setBounds(10, 20, 360, 400);

        JTableHeader titlePart = memberTable.getTableHeader();
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
                //DB.setLoginStatus("logged_out", "Last Logged In Acc- > "+global.username,0);
                member_classes_f.dispose();
                new member_dashboard();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Add Panels-----
        member_classes_f.add(p1);
        member_classes_f.add(p2);
        member_classes_f.add(back);
        member_classes_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        member_classes_f.setVisible(true);
    }
}
