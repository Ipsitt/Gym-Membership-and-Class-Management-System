import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class manage_progress {
    boolean BodyWeightIsValid = false; Float member_bw = 0f;
    boolean BodyFatPercantageIsValid = false; Float member_bfp = 0f;
    JButton updatebtn;
    boolean validity = false;
    float bw1 = 0;
    float bfp1 = 0;
    float bw2 = 0;
    float bfp2 = 0;

    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white=(Color.decode("#dbdbdb")); 
    Color red=(Color.decode("#f03434")); 
    Color buttongrey=(Color.decode("#a6a6a6")); 

    public manage_progress(){
        JFrame manageProg_f= new JFrame("Gym Membership And Class Management System");
        manageProg_f.setResizable(false);
        manageProg_f.setSize(500, 700);
        manageProg_f.getContentPane().setBackground(Color.decode("#121212"));
        manageProg_f.setLayout(null);

//------------------------------------------------------------------------------------------------------------------
        //First PAnel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50,50,380,150);
        p1.setLayout(null);

        JLabel bwlbl= new JLabel("Current Body Weight:");
        bwlbl.setForeground(green); 
        bwlbl.setBounds(10,10,200,50);
        p1.add(bwlbl);
        JLabel kglbl= new JLabel("kgs");
        kglbl.setForeground(white); 
        kglbl.setBounds(190,10,200,50);
        p1.add(kglbl);

        JTextField bwtf = new JTextField();
        bwtf.setBackground(white);
        bwtf.setBounds(140,25,45,18);
        p1.add(bwtf);

        JLabel bfplbl= new JLabel("Current Body Fat%:");
        bfplbl.setForeground(green); 
        bfplbl.setBounds(10,50,200,30);
        p1.add(bfplbl);
        JLabel percentlbl= new JLabel("%");
        percentlbl.setForeground(white); 
        percentlbl.setBounds(190,40,200,50);
        p1.add(percentlbl);

        JTextField bfptf = new JTextField();
        bfptf.setBackground(white);
        bfptf.setBounds(140,55,45,18);
        p1.add(bfptf);

        updatebtn = new JButton("Update Progress");
        updatebtn.setBounds(125, 100, 130, 25);
        updatebtn.setBackground(buttongrey);
        updatebtn.setForeground(Color.WHITE);
        p1.add(updatebtn);
//------------------------------------------
        bwtf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String bw = bwtf.getText();
                if (bw.isEmpty()){
                    BodyWeightIsValid= false; //Invalid
                } 
                else if (bw.matches("\\d+(\\.\\d{1,2})?")){
                    BodyWeightIsValid= true; 
                    member_bw= Float.parseFloat(bw); //Valid
                } 
                else{
                    BodyWeightIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });

        bfptf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String bfp = bfptf.getText();
                if (bfp.isEmpty()) {
                    BodyFatPercantageIsValid= false; //Invalid
                } 
                else if (bfp.matches("\\d+(\\.\\d{1,2})?")){
                    BodyFatPercantageIsValid= true; 
                    member_bfp= Float.parseFloat(bfp); //Valid
                } 
                else{
                    BodyFatPercantageIsValid= false; //Invalid
                }
                UpdateBtnState();
            }
        });
//------------------------------------------
        updatebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(BodyWeightIsValid && BodyFatPercantageIsValid){
                    DB.insertProgress(member_bw,member_bfp);
                    manageProg_f.dispose();
                    new manage_progress();
                }
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Second panel to compare progress
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50,240,380,190);
        p2.setLayout(null);

        JLabel pending_req = new JLabel("Compare Statistics:");
        pending_req.setFont(new Font("Arial", Font.BOLD, 20));
        pending_req.setForeground(green);
        pending_req.setBounds(10,5,400,50);
        p2.add(pending_req);

        //Compate button
        JButton compare = new JButton ("Compare");
        compare.setBounds(30,60,90,25);
        compare.setForeground(Color.WHITE);
        compare.setBackground(buttongrey);
        p2.add(compare);

        //Year bombobox
        String[] years= new String[101];
        for (int i=1; i<=101; i++){
            years[i-1] = String.valueOf(i+2023);
        }
        JComboBox<String> yearCB = new JComboBox<>(years);
        yearCB.setBounds(130,60,55,25);
        p2.add(yearCB);

        //month comboBox
        String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        JComboBox<String> monthCB = new JComboBox<>(months);
        monthCB.setBounds(190,60,50,25);
        p2.add(monthCB);

        //day comboBox
        String[] days= new String[31];
        for (int i=1; i<=31; i++){
            days[i-1] = String.valueOf(i);
        }
        JComboBox<String> dayCB = new JComboBox<>(days);
        dayCB.setBounds(245,60,40,25);
        p2.add(dayCB);

        //metrics text
        JLabel metrics = new JLabel("'s metrics");
        metrics.setBounds(290,60,100,25);
        metrics.setForeground(white);
        p2.add(metrics);

        //with text
        JLabel with = new JLabel("with");
        with.setBounds(97,100,90,25);
        with.setForeground(white);
        p2.add(with);

        //Year2 bombobox
        JComboBox<String> yearCB2 = new JComboBox<>(years);
        yearCB2.setBounds(130,100,55,25);
        p2.add(yearCB2);

        //month2 comboBox
        JComboBox<String> monthCB2 = new JComboBox<>(months);
        monthCB2.setBounds(190,100,50,25);
        p2.add(monthCB2);

        //day2 comboBox
        JComboBox<String> dayCB2 = new JComboBox<>(days);
        dayCB2.setBounds(245,100,40,25);
        p2.add(dayCB2);

        //metrics2 text
        JLabel metrics2 = new JLabel("'s metrics");
        metrics2.setBounds(290,100,100,25);
        metrics2.setForeground(white);
        p2.add(metrics2);
//--------------------------------------------------------------
        JLabel bwComparison = new JLabel("");
        bwComparison.setBounds(30,140,300,25);
        bwComparison.setForeground(green);
        p2.add(bwComparison);

        JLabel bfpComparison = new JLabel("");
        bfpComparison.setBounds(30,160,300,25);
        bfpComparison.setForeground(green);
        p2.add(bfpComparison);
//------------------------------------------
        ActionListener CBchecker = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        //======================================================================
                        String month;
                        if ((String) monthCB.getSelectedItem() == "Jan"){
                            month = "01";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Feb"){
                            month = "02";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Mar"){
                            month = "03";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Apr"){
                            month = "04";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "May"){
                            month = "05";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Jun"){
                            month = "06";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Jul"){
                            month = "07";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Aug"){
                            month = "08";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Sep"){
                            month = "09";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Oct"){
                            month = "10";
                        } 
                        else if ((String) monthCB.getSelectedItem() == "Nov"){
                            month = "11";
                        } 
                        else{
                            month = "12";
                        }

                        String month2;
                        if ((String) monthCB2.getSelectedItem() == "Jan"){
                            month2 = "01";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Feb"){
                            month2 = "02";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Mar"){
                            month2 = "03";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Apr"){
                            month2 = "04";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "May"){
                            month2 = "05";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Jun"){
                            month2 = "06";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Jul"){
                            month2 = "07";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Aug"){
                            month2 = "08";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Sep"){
                            month2 = "09";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Oct"){
                            month2 = "10";
                        } 
                        else if ((String) monthCB2.getSelectedItem() == "Nov"){
                            month2 = "11";
                        } 
                        else{
                            month2 = "12";
                        }
                        //============================================================
                String year = (String) yearCB.getSelectedItem();
                String day = String.format("%02d", Integer.parseInt((String) dayCB.getSelectedItem()));
                String date1 = year + "-" + month + "-" + day;

                String year2 = (String) yearCB2.getSelectedItem();
                String day2 = String.format("%02d", Integer.parseInt((String) dayCB2.getSelectedItem()));
                String date2 = year2 + "-" + month2 + "-" + day2;

                if (year != null && month != null && day != null && year2 != null && month2 != null && day2 != null) {
                    Float[] progressData1 = DB.readProgress(global.username, date1);
                    Float[] progressData2 = DB.readProgress(global.username, date2);

                    if (progressData1 != null && progressData2 != null){
                        bw1 = progressData1[0];
                        bfp1 = progressData1[1];
                        bw2 = progressData2[0];
                        bfp2 = progressData2[1];
                        compare.setBackground(green); validity = true;// Valid
                    } 
                    else{
                        compare.setBackground(red); validity = false;// Invalid
                    }
                }
            }
        };
        yearCB.addActionListener(CBchecker);
        monthCB.addActionListener(CBchecker);
        dayCB.addActionListener(CBchecker);
        yearCB2.addActionListener(CBchecker);
        monthCB2.addActionListener(CBchecker);
        dayCB2.addActionListener(CBchecker);
//------------------------------------------

        compare.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (validity){
                    //~~Calculate increase and decrease in those factors and make variables and all-~~
                    Float dbw = 0f;
                    if(bw1>bw2){
                        dbw=bw1-bw2;
                        bwComparison.setText("Body Weight: " +dbw+ " kgs decrease");
                    }
                    else if(bw2>bw1){
                        dbw=bw2-bw1;
                        bwComparison.setText("Body Weight: " +dbw+ " kgs increase");
                    }
                    else{
                        bwComparison.setText("Body Weight: " +dbw+ " kgs difference");
                    }

                    Float dbfp=0f;
                    if(bfp1>bfp2 ){
                        dbfp=bfp1-bfp2 ;
                        bfpComparison.setText("Body Fat%: " +dbfp+ "% decrease");
                    }
                    else if(bfp2>bfp1){
                        dbfp=bfp2-bfp1;
                        bfpComparison.setText("Body Fat%: " +dbfp+ "% increase");
                    }
                    else{
                        bfpComparison.setText("Body Fat%: " +dbfp+ "% difference");
                    }
                    //~~
                }
            }
        });

//------------------------------------------------------------------------------------------------------------------
        //Third Panel
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,470,380,140);
        p3.setLayout(null);

        JLabel progress_record = new JLabel("Previously Recorded Body Metrics:");
        progress_record.setFont(new Font("Arial", Font.BOLD, 20));
        progress_record.setForeground(green);
        progress_record.setBounds(10,5,400,50);
        p3.add(progress_record);

        //Table of previous records
        String[] title={"Date","Body Weight","Body Fat %"};
        String[][] data= DB.getProgressRecords(global.username);

        if (data.length == 0) {
            data= new String[][] {{"","",""}};
        }
        
        JTable PRtable=new JTable(data,title);
        //PRtable.setBounds(10,95,360,20);
        PRtable.setBackground(grey);
        PRtable.setForeground(white);
        PRtable.setGridColor(green);
        //dt.setFont(new Font("Arial", Font.PLAIN, 12));
        PRtable.setRowHeight(20);
        PRtable.setBorder(BorderFactory.createLineBorder(green,1));
        JScrollPane sp= new JScrollPane(PRtable); //to scroll
        sp.getViewport().setBackground(grey); // to remove white bland thingie if less data
        sp.setBorder(BorderFactory.createEmptyBorder()); 
        sp.setBounds(10,50,360,79);

        JTableHeader titlePart = PRtable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green,1));
        sp.add(titlePart);

        p3.add(sp);
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
                manageProg_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        manageProg_f.add(p1);
        manageProg_f.add(p2);
        manageProg_f.add(p3);
        manageProg_f.add(back);
        manageProg_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manageProg_f.setVisible(true);

    }
//------------------------------------------------------------------------------------------------------------------
    private void UpdateBtnState() {//fixed button state issue with extra method
        if (BodyWeightIsValid && BodyFatPercantageIsValid){
            updatebtn.setBackground(green);
        } 
        else if (!BodyWeightIsValid || !BodyFatPercantageIsValid){
            updatebtn.setBackground(red);
        } 
        else{
            updatebtn.setBackground(buttongrey);
        }
    }
}
