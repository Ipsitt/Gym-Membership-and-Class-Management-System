
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.*;

public class trainer_view_cmembers {
    public trainer_view_cmembers() {
        
        JFrame class_members_Frame = new JFrame("Gym Membership And Class Management System");
        class_members_Frame.setResizable(false);
        class_members_Frame.setSize(500, 700);
        class_members_Frame.getContentPane().setBackground(Color.decode("#121212"));
        class_members_Frame.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
//------------------------------------------------------------------------------------------------------------------
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,50,380,560);
        p1.setLayout(null);

        JLabel topiclbl = new JLabel("Topic:");
        topiclbl.setForeground(green); 
        topiclbl.setBounds(10,10,200,50);
        p1.add(topiclbl);
        JLabel topic = new JLabel("aaaaaaaaa");
        topic.setForeground(white); 
        topic.setBounds(55,10,315,50);
        p1.add(topic);

        JLabel startslbl = new JLabel("Starts From:");
        startslbl.setForeground(green); 
        startslbl.setBounds(10,40,200,50);
        p1.add(startslbl);
        JLabel start_time = new JLabel("12:12");
        start_time.setForeground(white); 
        start_time.setBounds(87,40,315,50);
        p1.add(start_time);

        JLabel endlbl = new JLabel("Ends At:");
        endlbl.setForeground(green); 
        endlbl.setBounds(283,40,200,50);
        p1.add(endlbl);
        JLabel end_time = new JLabel("12:12");
        end_time.setForeground(white); 
        end_time.setBounds(338,40,315,50);
        p1.add(end_time);

        JLabel datelbl = new JLabel("Date:");
        datelbl.setForeground(green); 
        datelbl.setBounds(10,70,200,50);
        p1.add(datelbl);
        JLabel date = new JLabel("aaaaaaaaa");
        date.setForeground(white); 
        date.setBounds(55,70,315,50);
        p1.add(date);

        JLabel Participants = new JLabel("Participants:");
        Participants.setForeground(green); 
        Participants.setBounds(10,100,200,50);
        p1.add(Participants);

        //table of members in class
        String[] title={"Name", "Username"};
        String[][] data= DB.getClassData(global.class_id);

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
        sp.setBounds(10, 150, 360, 400);

        JTableHeader titlePart = memberTable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        sp.add(titlePart);

        p1.add(sp);
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
                class_members_Frame.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        class_members_Frame.add(p1);
        class_members_Frame.add(back);
        class_members_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        class_members_Frame.setVisible(true);
        }
}