import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class trainer_schedule_classes {
    boolean Valid = false; //boolean to see if class_id entered is valid or not for panel 2
    boolean ValidP1 = false; //boolean to see if class_id entered is valid or not for panel 1
    String startsFrom = "0";
    String endsAt = "0";
    String date1= "0";
    String year = "0";
    String day = "0";
    String sHr = "0";
    String sMin = "0";
    String eHr = "0";
    String eMin = "0";
    String month = "0";
    JButton scheduleButton = new JButton("Schedule");
    Color green = Color.decode("#56c82b");
    Color grey = Color.decode("#2a2a2a");
    Color white=(Color.decode("#dbdbdb")); 
    Color red=(Color.decode("#f03434")); 
    Color buttongrey=(Color.decode("#a6a6a6")); 
    JTextField tf2 = new JTextField();

    public trainer_schedule_classes(){
        JFrame schedule_classes_f= new JFrame("Gym Membership And Class Management System");
        schedule_classes_f.setResizable(false);
        schedule_classes_f.setSize(500, 700);
        schedule_classes_f.getContentPane().setBackground(Color.decode("#121212"));
        schedule_classes_f.setLayout(null);

        Random random = new Random();
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
        details.setBounds(80,60,135,25);
        p1.add(details);
        JButton delete = new JButton("Delete");
        delete.setBackground(buttongrey);
        delete.setForeground(white);
        delete.setBounds(225,60,135,25);
        p1.add(delete);

        //Check if class_id entered exists in db
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String class_id = tf.getText();
                    if(class_id.isEmpty()){
                        details.setBackground(buttongrey); delete.setBackground(buttongrey); class_id_lbl.setForeground(green); ValidP1= false;
                    }
                    else{
                        if(DB.doesClassExist(class_id)){
                            details.setBackground(green); delete.setBackground(red); class_id_lbl.setForeground(green); ValidP1= true;
                        }
                        else{
                            details.setBackground(buttongrey); delete.setBackground(buttongrey); class_id_lbl.setForeground(red); ValidP1= false;
                        }
                    }
            }
        });

        //Action Listener for both action buttons:
        details.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidP1){
                    global.class_id = Integer.parseInt(tf.getText());
                    schedule_classes_f.dispose();
                    new trainer_view_cmembers();
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidP1){
                    String class_id = tf.getText();
                    DB.deleteClass(class_id);
                    schedule_classes_f.dispose();
                    new trainer_schedule_classes();
                }
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Second panel to create class
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50, 200, 380, 242);
        p2.setLayout(null);

        // Title label
        JLabel title = new JLabel("Schedule Class:");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(green);
        title.setBounds(10, 5, 400, 50);
        p2.add(title);

        tf2.setBackground(white);
        tf2.setBounds(80,64,280,20);
        p2.add(tf2);

        //Topic label
        JLabel topiclbl = new JLabel("Topic:");
        topiclbl.setBounds(10,60,90,25);
        topiclbl.setForeground(green);
        p2.add(topiclbl);

        //Date label
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setBounds(10,100,90,25);
        dateLabel.setForeground(green);
        p2.add(dateLabel);

        //Year ComboBox
        String[] years = new String[101];
        for (int i = 0; i < 101; i++) {
            years[i] = String.valueOf(i + 2024);
        }
        JComboBox<String> yearCB = new JComboBox<>(years);
        yearCB.setBounds(80,100,55,25);
        p2.add(yearCB);

        //Month ComboBox
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        JComboBox<String> monthCB = new JComboBox<>(months);
        monthCB.setBounds(140,100,50,25);
        p2.add(monthCB);

        //Day ComboBox
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        JComboBox<String> dayCB = new JComboBox<>(days);
        dayCB.setBounds(195,100,40,25);
        p2.add(dayCB);

        //Time label
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setBounds(10,140,40,25);
        timeLabel.setForeground(green);
        p2.add(timeLabel);

        //Start Time Hour ComboBox
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }
        JComboBox<String> startHourCB = new JComboBox<>(hours);
        startHourCB.setBounds(80,140,40,25);
        p2.add(startHourCB);

        //: label
        JLabel colon2Label = new JLabel(":");
        colon2Label.setBounds(125, 140, 30, 25);
        colon2Label.setForeground(white);
        p2.add(colon2Label);

        //Start Time Minute ComboBox
        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        JComboBox<String> startMinuteCB = new JComboBox<>(minutes);
        startMinuteCB.setBounds(135,140,40,25);
        p2.add(startMinuteCB);

        //to label
        JLabel toLabel = new JLabel("to");
        toLabel.setBounds(185, 140, 30, 25);
        toLabel.setForeground(white);
        p2.add(toLabel);

        //End Time Hour ComboBox
        JComboBox<String> endHourCB = new JComboBox<>(hours);
        endHourCB.setBounds(207,140,40,25);
        p2.add(endHourCB);

        //: label
        JLabel colon1Label = new JLabel(":");
        colon1Label.setBounds(252, 140, 30, 25);
        colon1Label.setForeground(white);
        p2.add(colon1Label);

        //End Time Minute ComboBox
        JComboBox<String> endMinuteCB = new JComboBox<>(minutes);
        endMinuteCB.setBounds(262,140,40,25);
        p2.add(endMinuteCB);

        //Schedule button
        scheduleButton.setBounds(125, 192,130,25);
        scheduleButton.setBackground(buttongrey);
        scheduleButton.setForeground(Color.WHITE);
        p2.add(scheduleButton);
//------------------------------------------
        ActionListener CBchecker = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                        //======================================================================
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

                        //============================================================
                year = (String) yearCB.getSelectedItem();
                day = String.format("%02d", Integer.parseInt((String) dayCB.getSelectedItem()));
                date1 = year + "-" + month + "-" + day;

                sHr = (String) startHourCB.getSelectedItem();
                sMin = (String) startMinuteCB.getSelectedItem();
                eHr = (String) endHourCB.getSelectedItem();
                eMin = (String) endMinuteCB.getSelectedItem();
                    buttonState();
                startsFrom = sHr+":"+sMin;
                endsAt = eHr+":"+eMin;

            }
        };
        yearCB.addActionListener(CBchecker);
        monthCB.addActionListener(CBchecker);
        startHourCB.addActionListener(CBchecker);
        startMinuteCB.addActionListener(CBchecker);
        dayCB.addActionListener(CBchecker);
        endHourCB.addActionListener(CBchecker);
        endMinuteCB.addActionListener(CBchecker);
        tf2.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent e) {
                String topictxt = tf2.getText();
                if (topictxt.isEmpty()){
                    Valid= false;
                    buttonState();
                    scheduleButton.setBackground(buttongrey);
                }
                else{
                    buttonState();
                }
            }
        });
        

        scheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                if (Valid){
                int rand = 1000 + random.nextInt(9000);
                String class_id = ""+(DB.getNoOfClasses())+rand;
                DB.insertClass(class_id, tf2.getText(), date1, startsFrom, endsAt);
                schedule_classes_f.dispose();
                new trainer_schedule_classes();
                }
            }
        });
//------------------------------------------------------------------------------------------------------------------
        //Third Panel
        JPanel p3 = new JPanel();
        p3.setBackground(grey);
        p3.setBounds(50,492,380,145);
        p3.setLayout(null);

        JLabel class_list= new JLabel("Scheduled Classes:");
        class_list.setFont(new Font("Arial",Font.BOLD,20));
        class_list.setForeground(green); 
        class_list.setBounds(10,5,400,50);
        p3.add(class_list);

        //table of pending requets
        String[] titleh={"Class ID","Title","Date","Starts","Ends","Participants"};
        String[][] data= DB.getClasses();

        if (data.length == 0) {
            data= new String[][] {{"","","","","",""}};
        }
        
        JTable classTable=new JTable(data,titleh);
        classTable.setBackground(grey);
        classTable.setForeground(white);
        classTable.setGridColor(green);
        classTable.setRowHeight(25);
        classTable.setBorder(BorderFactory.createLineBorder(green,1));
        JScrollPane sp= new JScrollPane(classTable); //to scroll
        sp.getViewport().setBackground(grey); // to remove white bland thingie if less data
        sp.setBorder(BorderFactory.createEmptyBorder()); 
        sp.setBounds(10, 60, 360, 68);

        JTableHeader titlePart = classTable.getTableHeader();
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
                new trainer_dashboard();
                schedule_classes_f.dispose();
            }
        });
//------------------------------------------------------------------------------------------------------------------
        schedule_classes_f.add(p1);
        schedule_classes_f.add(p2);
        schedule_classes_f.add(p3);
        schedule_classes_f.add(back);
        schedule_classes_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        schedule_classes_f.setVisible(true);
    }
    
    //------------------------------------------------------------
    private void buttonState(){
        if (year != null && month != null && day != null && sHr != null && sMin != null && eHr != null && eMin != null && !tf2.getText().isEmpty()) {
            
            LocalDate today = LocalDate.now();
            LocalDate selectedDate = LocalDate.parse("2020-01-01");
            try{
            selectedDate = LocalDate.parse(year + "-" + month + "-" + day, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }catch(Exception e){
            }
            if (!selectedDate.isAfter(today)) {
                Valid = false;
                scheduleButton.setBackground(red);
                return;
            }
            
            if (Integer.parseInt(eHr)<Integer.parseInt(sHr)){
                Valid = false;
                scheduleButton.setBackground(red);
            }
            else if(eHr.equals(sHr)){
                if(Integer.parseInt(eMin)<Integer.parseInt(sMin)){
                    Valid = false;
                    scheduleButton.setBackground(red);
                }
                else if(Integer.parseInt(sMin)==Integer.parseInt(eMin)){
                    Valid = false;
                    scheduleButton.setBackground(red);
                }
                else{
                    Valid = true;
                    scheduleButton.setBackground(green);
                }
            }
            else{
                Valid = true;
                scheduleButton.setBackground(green);
            }
        }
    }
}