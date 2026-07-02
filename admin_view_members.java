import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class admin_view_members {
    boolean Valid = false;
    private JTable PRtable;
    private DefaultTableModel tableModel;
    private JTextField tf;
//-------------------------------------------
    public admin_view_members() {
        JFrame view_member_f = new JFrame("Gym Membership And Class Management System");
        view_member_f.setResizable(false);
        view_member_f.setSize(500, 700);
        view_member_f.getContentPane().setBackground(Color.decode("#121212"));
        view_member_f.setLayout(null);

        Color green = Color.decode("#56c82b");
        Color grey = Color.decode("#2a2a2a");
        Color white = Color.decode("#dbdbdb");
        Color red = Color.decode("#f03434");
        Color buttongrey = Color.decode("#a6a6a6");
//-------------------------------------------
        // First Panel
        JPanel p1 = new JPanel();
        p1.setBackground(grey);
        p1.setBounds(50, 50, 380, 100);
        p1.setLayout(null);

        JLabel usernamelbl = new JLabel("Username:");
        usernamelbl.setForeground(green);
        usernamelbl.setBounds(10, 10, 200, 50);
        p1.add(usernamelbl);

        tf = new JTextField();
        tf.setBackground(white);
        tf.setBounds(80, 25, 280, 20);
        tf.setText(global.searched_username);
        p1.add(tf);

        JLabel actionlbl = new JLabel("Action:");
        actionlbl.setForeground(green);
        actionlbl.setBounds(10, 60, 70, 30);
        p1.add(actionlbl);

        JButton view_btn = new JButton("View");
        view_btn.setBackground(buttongrey);
        view_btn.setForeground(white);
        view_btn.setBounds(80, 60, 280, 25);
        p1.add(view_btn);
        //-------------------------------------------
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (DB.checkUsernameFromLoginTable(tf.getText())){
                    view_btn.setBackground(green); Valid = true;
                }
                else if (tf.getText().equals("")){
                    view_btn.setBackground(buttongrey); Valid = false;
                }
                else{
                    view_btn.setBackground(red); Valid = false;
                }
            }
        });
    //-------------------------------------------
        view_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Valid){
                global.username = tf.getText();
                new member_dashboard();//redirect to member dashboard
                view_member_f.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid Username");
                }
            }
        });
    //-------------------------------------------
//-------------------------------------------
        // Second Panel 
        JPanel p2 = new JPanel();
        p2.setBackground(grey);
        p2.setBounds(50, 200, 380, 415);
        p2.setLayout(null);

        JLabel fullnamelbl = new JLabel("Fullname:");
        fullnamelbl.setForeground(green);
        fullnamelbl.setBounds(10, 10, 200, 50);
        p2.add(fullnamelbl);

        JTextField tf2 = new JTextField();
        tf2.setBackground(white);
        tf2.setBounds(80, 25, 280, 20);
        tf2.setText(global.searched_username);
        p2.add(tf2);
        tf2.requestFocus();

        JLabel pending_req = new JLabel("List of Accounts:");
        pending_req.setFont(new Font("Arial", Font.BOLD, 20));
        pending_req.setForeground(green);
        pending_req.setBounds(10, 51, 400, 50);
        p2.add(pending_req);

        String[] title = {"Username", "Name"};
        tableModel = new DefaultTableModel(DB.getSearchedFullnames(global.searched_username), title);
        PRtable = new JTable(tableModel);
        PRtable.setBackground(grey);
        PRtable.setForeground(white);
        PRtable.setGridColor(green);
        PRtable.setRowHeight(25);
        PRtable.setBorder(BorderFactory.createLineBorder(green, 1));

        JScrollPane sp = new JScrollPane(PRtable);
        sp.getViewport().setBackground(grey);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.setBounds(10, 103, 360, 293);
        p2.add(sp);

        JTableHeader titlePart = PRtable.getTableHeader();
        titlePart.setBackground(grey);
        titlePart.setForeground(green);
        titlePart.setFont(new Font("Arial", Font.BOLD, 12));
        titlePart.setBorder(BorderFactory.createLineBorder(green, 1));
//-------------------------------------------
        tf2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                global.searched_username = tf2.getText();
                updateTableData(global.searched_username);
            }
        });
//-------------------------------------------
        // Back Button
        JButton back = new JButton("<");
        back.setFont(new Font("Arial", Font.BOLD, 17));
        back.setBounds(0, 0, 50, 50);
        back.setBackground(Color.decode("#121212"));
        back.setForeground(green);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new admin_dashboard();
                view_member_f.dispose();
                global.searched_username = "";
            }
        });
//-------------------------------------------
        view_member_f.add(back);
        view_member_f.add(p1);
        view_member_f.add(p2);
        view_member_f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view_member_f.setVisible(true);
        tf2.requestFocusInWindow();
    }
//-------------------------------------------
    //Function to refresh JTable data only WITHOUT refreshing the whole shit
    private void updateTableData(String searchedUsername) {
        String[][] updatedData = DB.getSearchedFullnames(searchedUsername);
        tableModel.setRowCount(0); //clears table
        for (String[] row : updatedData) {
            tableModel.addRow(row); //add updated data
        }
    }
}
