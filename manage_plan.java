import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class manage_plan {

    Integer amount = 0; //declaring it here fixed error #E1
    boolean buttonIsGreen=false;
    Integer week = 0;//
    Integer till_month = 0;//
    Integer till_6_months = 0;//
    Integer untill_1kDays = 0;//

    public manage_plan(){
        JFrame plan_f = new JFrame("Gym Membership And Class Management System");
        plan_f.setResizable(false);
        plan_f.setSize(500, 700);
        plan_f.getContentPane().setBackground(Color.decode("#121212"));
        plan_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white=(Color.decode("#dbdbdb")); 
        Color red=(Color.decode("#f03434")); 
        Color buttongrey=(Color.decode("#a6a6a6")); 

//------------------------------------------------------------------------------------------------------------------

        //current plan
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,50,380,100);
        p1.setLayout(null);

        Integer daysRemaining = DB.getDaysRemaining(global.username);

        JLabel plan= new JLabel("Current Plan:");
        plan.setFont(new Font("Arial",Font.BOLD,20));
        JLabel planDays= new JLabel(daysRemaining+" Days Remaining");
        plan.setForeground(green); 
        plan.setBounds(10,10,200,50);
        p1.add(plan);
        planDays.setForeground(white); 
        planDays.setBounds(10,40,200,50);
        p1.add(planDays);

//------------------------------------------------------------------------------------------------------------------
        //Third panel -> table of pending request
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,450,380,150);
        p3.setLayout(null);

        JLabel pending_req= new JLabel("Recently Added Plan:");
        pending_req.setFont(new Font("Arial",Font.BOLD,20));
        pending_req.setForeground(green); 
        pending_req.setBounds(10,10,400,50);
        p3.add(pending_req);

        DB.requestContent requestContent = new DB.requestContent(0, 0, "");
        boolean hasRequest = DB.read_requestContent(requestContent, global.username);

    String reqORupdate = "Create";//Button text for button on second panel
        //creating table
        String[][] data;
        if (hasRequest) {
            reqORupdate = "Update";
            data= new String[][]{{String.valueOf(requestContent.days),String.valueOf(requestContent.amount),String.valueOf(requestContent.date)}};
        }
        else{
            data = new String[][]{{"","",""}};
            reqORupdate = "Create";
        }

        String[] title={"Days","Amount","Date"};
        JTable dt=new JTable(data,title);
        dt.setBounds(10,95,360,20);
        dt.setBackground(grey);
        dt.setForeground(white);
        dt.setGridColor(green);
        dt.setRowHeight(25);
        dt.setBorder(BorderFactory.createLineBorder(green,1));
        p3.add(dt);

        JTableHeader titlePart = dt.getTableHeader();
        titlePart.setBounds(10,75,360,20);
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial",Font.BOLD,12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        p3.add(titlePart);
//------------------------------------------------------------------------------------------------------------------
        //new plan
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,200,380,200);
        p2.setLayout(null);
        
        JLabel eXplan= new JLabel("Extend Plan:");
        eXplan.setFont(new Font("Arial",Font.BOLD,20));
        eXplan.setForeground(green); 
        eXplan.setBounds(10,10,200,50);
        p2.add(eXplan);

        JTextField extendDays = new JTextField();
        extendDays.setBounds(103,70,70,20);
        p2.add(extendDays);

        JLabel days = new JLabel("Days");
        days.setForeground(buttongrey);
        days.setBounds(180,70,30,20);
        p2.add(days);
        JLabel equalsTo = new JLabel("=");
        equalsTo.setForeground(white);
        equalsTo.setBounds(213,70,15,20);
        p2.add(equalsTo);

        JLabel money = new JLabel("Rs 0");
        money.setForeground(buttongrey);
        money.setBounds(228,70,80,20);
        p2.add(money);

        JButton requestbtn = new JButton(reqORupdate+" Plan");
        requestbtn.setBounds(125, 120, 130, 25);
        requestbtn.setBackground(buttongrey);
        requestbtn.setForeground(Color.WHITE);
        p2.add(requestbtn);

        String[][] current_rates= DB.getAmounts();
        if (current_rates != null){
            week = Integer.parseInt(current_rates[0][0]);//
            till_month = Integer.parseInt(current_rates[0][1]);//
            till_6_months = Integer.parseInt(current_rates[0][2]);//
            untill_1kDays = Integer.parseInt(current_rates[0][3]);//
        }

        //Integer amount = 0; //this gave error #E1
        extendDays.addKeyListener(new KeyAdapter() {//To decide amount to pay with respected to days requested
            @Override
            public void keyReleased(KeyEvent e) {
                if ((extendDays.getText()).matches("\\d+")) { 
                    Integer daysNeeded = Integer.parseInt(extendDays.getText());
                    Integer interest;
                    if (daysNeeded < 7){
                        interest = week;
                        requestbtn.setBackground(green); days.setForeground(green); money.setForeground(green); buttonIsGreen=true; //Valid
                    }
                    else if (daysNeeded >= 7 && daysNeeded <= 30){
                        interest = till_month;
                        requestbtn.setBackground(green); days.setForeground(green); money.setForeground(green); buttonIsGreen=true; //Valid
                    }
                    else if (daysNeeded >= 30 && daysNeeded <= 180){
                        interest = till_6_months;
                        requestbtn.setBackground(green); days.setForeground(green); money.setForeground(green); buttonIsGreen=true; //Valid
                    }
                    else if (daysNeeded >= 180 && daysNeeded <= 1000){
                        interest = untill_1kDays;
                        requestbtn.setBackground(green); days.setForeground(green); money.setForeground(green); buttonIsGreen=true; //Valid
                    }
                    else{
                        interest = untill_1kDays;
                        requestbtn.setBackground(red); days.setForeground(red); money.setForeground(red); buttonIsGreen=false; //invalid 
                    }
                    amount = daysNeeded*interest;
                    money.setText("Rs "+amount);
                }
                else if (extendDays.getText().equals("")) {
                    requestbtn.setBackground(buttongrey); days.setForeground(buttongrey); money.setForeground(buttongrey); buttonIsGreen=false; //invalid 
                    money.setText("Rs 0");
                }
                else{
                    requestbtn.setBackground(red); days.setForeground(red); money.setForeground(red); buttonIsGreen=false; //invalid 
                }
            }
        });


        //Send request Button action listener
        requestbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (buttonIsGreen){

                    int daysEntered = Integer.parseInt(extendDays.getText());
                    try {
                    DB.RequestData requestData = new DB.RequestData(daysEntered, amount);

                    boolean isUpdated = DB.sendRequest(requestData);

                        if (isUpdated) {
                            new manage_plan();
                            plan_f.dispose();
                        } else {
                            new manage_plan();
                            plan_f.dispose();
                        }
                    }catch
                        (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                        }
                }

            }
        });
//------------------------------------------------------------------------------------------------------------------
        // https://stackoverflow.com/questions/16157120/setfocuspainted-used-for-jbuttons
        // https://coderanch.com/t/335489/java/JButton-Border

        //Back Button
        JButton back = new JButton("<");
        back.setFont(new Font("Arial", Font.BOLD, 17));
        back.setBounds(0,0,50,50);
        back.setBackground(Color.decode("#121212"));
        back.setForeground(green);
        back.setBorderPainted(false); 
        back.setFocusPainted(false);  
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new member_dashboard();
                plan_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        plan_f.add(p1);
        plan_f.add(p2);
        plan_f.add(p3);
        plan_f.add(back);
        plan_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plan_f.setVisible(true);
    } 
}
