  // getTrainerClassID
  import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
  
  import java.awt.*;
  import java.awt.event.*;
  
  
  public class trainer_attendance{
      boolean ValidP1 = false; //boolean to see if class_id entered is valid or not for panel 1
      public trainer_attendance() {
          
          JFrame Trainer_attendance = new JFrame("Gym Membership And Class Management System");
         Trainer_attendance.setResizable(false);
         Trainer_attendance.setSize(500, 700);
         Trainer_attendance.getContentPane().setBackground(Color.decode("#121212"));
         Trainer_attendance.setLayout(null);
  
          Color green = Color.decode("#56c82b");
          Color grey = Color.decode("#2a2a2a");
          Color white=(Color.decode("#dbdbdb")); 
          //Color red=(Color.decode("#f03434")); 
          //Color buttongrey=(Color.decode("#a6a6a6")); 


  
  //------------------------------------------------------------------------------------------------------------------


          //list of attended dates
          JPanel p2 = new JPanel();
          p2.setBackground(grey);
          p2.setBounds(50,50,380,550);
          p2.setLayout(null);
      
   //trainer attended dates
          String[] title={"Attented Dates"};
          String[][] data= DB.getAttendedDays();
  
          if (data.length == 0) {
              data= new String[][] {{""}};
          }
  
          JTable memberTable=new JTable(data,title);
          memberTable.setBackground(grey);
          memberTable.setForeground(white);
          memberTable.setGridColor(green);
          memberTable.setRowHeight(25);
          memberTable.setBorder(BorderFactory.createLineBorder(green,1));

          DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();// to allign the date in middle
          centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
          memberTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

          JScrollPane sp= new JScrollPane(memberTable); //to scroll
          sp.getViewport().setBackground(grey); // to remove white bland thingie if less data
          sp.setBorder(BorderFactory.createEmptyBorder()); 
          sp.setBounds(10, 20, 360, 519);
  
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
                 // DB.setLoginStatus("logged_out", "Last Logged In Acc- > "+global.username,0);
                 Trainer_attendance.dispose();
                  new trainer_dashboard();
              }
          });
  //------------------------------------------------------------------------------------------------------------------
          //Add Panels-----
        //   Trainer_attendance.add(p1);
         Trainer_attendance.add(p2);
         Trainer_attendance.add(back);
         Trainer_attendance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         Trainer_attendance.setVisible(true);
      }
  }
  