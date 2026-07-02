import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class admin_payment_rates {
    boolean PriceAIsValid = true; Integer PriceA = 0;
    boolean PriceBIsValid = true; Integer PriceB = 0;
    boolean PriceCIsValid = true; Integer PriceC = 0;
    boolean PriceDIsValid = true; Integer PriceD = 0;
    JButton updatebtn;
    boolean validity = false;

    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white=(Color.decode("#dbdbdb")); 
    Color red=(Color.decode("#f03434")); 
    Color buttongrey=(Color.decode("#a6a6a6")); 

    public admin_payment_rates(){
        JFrame change_rate_f= new JFrame("Gym Membership And Class Management System");
        change_rate_f.setResizable(false);
        change_rate_f.setSize(500, 700);
        change_rate_f.getContentPane().setBackground(Color.decode("#121212"));
        change_rate_f.setLayout(null);

//------------------------------------------------------------------------------------------------------------------
        //First PAnel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,50,380,250);
        p1.setLayout(null);

        JLabel pending_req = new JLabel("Current Rates:");
        pending_req.setFont(new Font("Arial", Font.BOLD, 20));
        pending_req.setForeground(green);
        pending_req.setBounds(10, 5, 400, 50);
        p1.add(pending_req);

        String week = "";//
        String till_month = "";//
        String till_6_months = "";//
        String untill_1kDays = "";//

        String[][] current_rates= DB.getAmounts();
        if (current_rates != null){
            week = current_rates[0][0];//
            till_month = current_rates[0][1];//
            till_6_months = current_rates[0][2];//
            untill_1kDays = current_rates[0][3];//


        // 1 - 6 Days
        JLabel lbl7 = new JLabel("1 - 6 Days:");
        lbl7.setForeground(green);
        lbl7.setBounds(10, 55, 120, 25);
        p1.add(lbl7);

        JLabel rsLbl1 = new JLabel("Rs.");
        rsLbl1.setForeground(white);
        rsLbl1.setBounds(100, 55, 30, 25);
        p1.add(rsLbl1);

        JTextField tf1 = new JTextField(week);
        tf1.setBackground(white);
        tf1.setBounds(125, 55, 230, 25);
        p1.add(tf1);

        // 7 - 30 Days
        JLabel lbl30 = new JLabel("7 - 30 Days:");
        lbl30.setForeground(green);
        lbl30.setBounds(10, 90, 120, 25);
        p1.add(lbl30);

        JLabel rsLbl2 = new JLabel("Rs.");
        rsLbl2.setForeground(white);
        rsLbl2.setBounds(100, 90, 30, 25);
        p1.add(rsLbl2);

        JTextField tf2 = new JTextField(till_month);
        tf2.setBackground(white);
        tf2.setBounds(125, 90, 230, 25);
        p1.add(tf2);

        // 30 - 180 Days
        JLabel lbl180 = new JLabel("30 - 180 Days:");
        lbl180.setForeground(green);
        lbl180.setBounds(10, 125, 120, 25);
        p1.add(lbl180);

        JLabel rsLbl3 = new JLabel("Rs.");
        rsLbl3.setForeground(white);
        rsLbl3.setBounds(100, 125, 30, 25);
        p1.add(rsLbl3);

        JTextField tf3 = new JTextField(till_6_months);
        tf3.setBackground(white);
        tf3.setBounds(125, 125, 230, 25);
        p1.add(tf3);

        // 180+ Days
        JLabel lbl1000 = new JLabel("180+ Days:");
        lbl1000.setForeground(green);
        lbl1000.setBounds(10, 160, 120, 25);
        p1.add(lbl1000);

        JLabel rsLbl4 = new JLabel("Rs.");
        rsLbl4.setForeground(white);
        rsLbl4.setBounds(100, 160, 30, 25);
        p1.add(rsLbl4);

        JTextField tf4 = new JTextField(untill_1kDays);
        tf4.setBackground(white);
        tf4.setBounds(125, 160, 230, 25);
        p1.add(tf4);

        //-----------------------
        updatebtn = new JButton("Update Rates");
        updatebtn.setBounds(125, 205, 130, 25);
        updatebtn.setBackground(buttongrey);
        updatebtn.setForeground(Color.WHITE);
        p1.add(updatebtn);
//------------------------------------------
        tf1.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String pA = tf1.getText();
                if (pA.isEmpty()){
                    PriceAIsValid= false; //Invalid
                } 
                else if (pA.matches("^\\d+$")){
                    PriceAIsValid= true; 
                    PriceA = Integer.parseInt(pA);
                } 
                else{
                    PriceAIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });

        tf2.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String pB = tf2.getText();
                if (pB.isEmpty()){
                    PriceBIsValid= false; //Invalid
                } 
                else if (pB.matches("^\\d+$")){
                    PriceBIsValid= true; 
                    PriceB = Integer.parseInt(pB);
                } 
                else{
                    PriceBIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });

        tf3.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String pC = tf3.getText();
                if (pC.isEmpty()){
                    PriceCIsValid= false; //Invalid
                } 
                else if (pC.matches("^\\d+$")){
                    PriceCIsValid= true; 
                    PriceC = Integer.parseInt(pC);
                } 
                else{
                    PriceCIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });

        tf4.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e){
                String pD = tf4.getText();
                if (pD.isEmpty()){
                    PriceDIsValid= false; //Invalid
                } 
                else if (pD.matches("^\\d+$")){
                    PriceDIsValid= true; 
                    PriceD = Integer.parseInt(pD);
                } 
                else{
                    PriceDIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });
//------------------------------------------
        updatebtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(PriceAIsValid && PriceBIsValid && PriceCIsValid && PriceDIsValid){
                    PriceA = Integer.parseInt(tf1.getText());
                    PriceB = Integer.parseInt(tf2.getText());
                    PriceC = Integer.parseInt(tf3.getText());
                    PriceD = Integer.parseInt(tf4.getText());
                    DB.insertNewRates(PriceA,PriceB,PriceC,PriceD);
                    change_rate_f.dispose();
                    new admin_payment_rates();
                }
            }
        });
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
                change_rate_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        change_rate_f.add(p1);
        change_rate_f.add(back);
        change_rate_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        change_rate_f.setVisible(true);
    }

    }
//------------------------------------------------------------------------------------------------------------------
    private void UpdateBtnState(){
        if (PriceAIsValid && PriceBIsValid && PriceCIsValid && PriceDIsValid){
            updatebtn.setBackground(green);
        } 
        else if (!PriceAIsValid || !PriceBIsValid || !PriceCIsValid || !PriceDIsValid){
            updatebtn.setBackground(red);
        } 
        else{
            updatebtn.setBackground(buttongrey);
        }
    }
}

