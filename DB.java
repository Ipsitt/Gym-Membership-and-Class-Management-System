import java.sql.*;
import javax.swing.*;
import java.text.*;

public class DB {

    //current date
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date()); //ambiguity resolved (Initial problem: Both java.util.Date and java.sql.Date classes exist)
    }


    public static Connection conn;

    public static void setConnection() {
        //Establishing DB Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gmcms", "root", "");
            System.out.println("Database Connected");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------
// //~~~~~~~~~~~~ Functions to remember unser info so they dont have to log in again and again ~~~~~~~~~~~~~~~~~
//     public static String[] getLoginStatus(){
//         String[] info = new String[3];
//         try {
//             Statement st = conn.createStatement();
//             String sql = "SELECT * FROM remember_me"; 
//             ResultSet rs = st.executeQuery(sql);

//             if (rs.next()) {
//                 info[0] = rs.getString("status");
//                 info[1] = rs.getString("username");
//                 info[2] = String.valueOf(rs.getInt("type"));
//             }
//         } catch (Exception e) {
//             JOptionPane.showMessageDialog(null, e);
//         }
//         return info;
//     }
//     //-------------------------------
    // public static void setLoginStatus(String status, String username, int type) {
    //     try {
    //         String sql = "UPDATE remember_me SET status='" +status+ "', username='" +username+"', type=" +type+ " WHERE ipsit='ipsit'";
    //         Statement st = conn.createStatement();
    //         st.executeUpdate(sql);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }
     //---------------------------------------------------------------------------------------------------------------------
    //function to send trainer request
    public static void sendTrainerRequest(String trainer_id){
        try{
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery("SELECT name FROM member WHERE username = '" +global.username+"'");
            String membername = " ";
            if(rs1.next()){
                membername = rs1.getString("name");

            }
            ResultSet rs2 = st.executeQuery("SELECT name FROM trainer WHERE username = '"+trainer_id+"'");
            String trainername = " ";
            if(rs2.next()){
                trainername = rs2.getString("name");

            }
            st.executeUpdate("update trainer_member_relation SET name = '" +membername+"', trainer_id='"+trainer_id+"', trainer='"+trainername+"', date='"+DB.getDate()+"', status='Pending' WHERE member='"+global.username+"'");

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------

    //Function to read data from requests table in db
    //Modified read function from previously written read-login function
    public static class requestTrainerContent {
        public String username;
        public String status;
        public String date;

        public requestTrainerContent(String username, String date, String status) {
            this.username = username;
            this.status = status;
            this.date = date;
        }
    }

    public static boolean read_requestTrainerContent(requestTrainerContent reqT, String username) {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT trainer_id, date, status from trainer_member_relation WHERE member = '" +global.username+ "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                reqT.username = rs.getString("trainer_id");
                reqT.status = rs.getString("status");
                reqT.date = rs.getString("date");
                
                return true; //username exists
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false; //username doesnt exist
    }
       
     //---------------------------------------------------------------------------------------------------------------------
    //Read Function to check the trainer status
    public static String getTrainerStatus() { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT status FROM trainer_member_relation WHERE member = '" +global.username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getString("status");
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return "";
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void insert_trainer_into_login(String username, String password){
    
        try {
            String sql = "INSERT INTO login(type, username, password) VALUES ( 2,'"+username+"','"+ password +"')"; 
            Statement st = conn.createStatement();
            st.execute(sql);
            
            //JOptionPane.showMessageDialog(null, "Registered successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
        


    //----------------------------------------------------------------------------------------------------------------------
    //Delete trainer reuqest
    public static void deleteTrainerRequest(){
        try{
        Statement st= conn.createStatement();
        st.executeUpdate("UPDATE trainer_member_relation SET trainer_id='-', date='-',status='Not Requested'    ");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }    //---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if username exists in attendance table
    public static boolean doesAccountUsernameExist(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM login WHERE username = '" +username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //username exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //username does not exist 
    }
    
    //------------------------------------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if username exists in requests table
    public static boolean doesTrainerUsernameExist(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM trainer WHERE username = '" +username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //username exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //username does not exist 
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------------

    //Insert Function for login db
    public static void insert(int type, String username, String password) {
        try {
            String sql = "INSERT INTO login(type, username, password) VALUES(" + type + ", '" + username + "', '" + password + "')"; 
            Statement st = conn.createStatement();
            st.execute(sql);
            String sql2 = "INSERT INTO member_plan(username, days_remaining, eligibility, total_days, total_payment) VALUES('" + username + "', 0, 0, 0, 0)"; 
            st.execute(sql2);
            String sql3 = "INSERT INTO trainer_member_relation(member, name, trainer, trainer_id, date, status) VALUES('" + username + "', '-','-' ,'-', '-','Not Requested')"; 
            st.execute(sql3);
            //JOptionPane.showMessageDialog(null, "Registered successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Insert Function for member info db
    public static void insert_member(String name, String username, int phone, String address, String sex) {
        try {
            String sql = "INSERT INTO member(username, name, contact, address, sex) VALUES('" + username + "','"+ name +"',"+ phone +",'"+ address +"', '" + sex + "')"; 
            Statement st = conn.createStatement();
            st.execute(sql);
            
            //JOptionPane.showMessageDialog(null, "Registered successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
  //----------------------------------------------------------------------------------------------------------------------------------------------
    //Insert Function for trainer info db
    public static void insert_trainer(String trainer_id, String name, String speciality, int contact) {
        try {
            String sql = "INSERT INTO trainer(username, name, speciality, contact) VALUES('" + trainer_id + "','"+ name +"','"+ speciality +"', '" + contact + "')"; 
            Statement st = conn.createStatement();
            st.execute(sql);
            
            //JOptionPane.showMessageDialog(null, "Registered successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }  

    //----------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------
    // Read Function from member and login
    public static String[] getMemberInfo(String username) {
        String[] info = new String[5]; 

        try {
        
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();

        
            String sql = "SELECT * FROM member WHERE username = '" + username + "'";
            ResultSet rs = st1.executeQuery(sql);

            if (rs.next()) {
                
                info[0] = rs.getString("name");
                info[2] = rs.getString("contact");
                info[3] = rs.getString("address");
                info[4] = rs.getString("sex");
            } else {
                JOptionPane.showMessageDialog(null, "User not found in member!");
                return null; 
            }

        
            String sql2 = "SELECT * FROM login WHERE username = '" + username + "'";
            ResultSet rs2 = st2.executeQuery(sql2);

            if (rs2.next()) {
                info[1] = rs2.getString("password");
            } else {
                JOptionPane.showMessageDialog(null, "User not found in login!");
                return null; 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return info;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Function to update member info
    public static void updateInfo(String name, String contact, String address, String sex) {

        try {
            Statement st = conn.createStatement();
            //Update member table
            try {
                String sql1 = "UPDATE member SET name = '" + name + "', contact = '" + contact + "', address = '" + address + "', sex = '" + sex + "' WHERE username = '" + global.username + "'";
                st.execute(sql1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating member: " + e.getMessage());
            }

            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Read Function for member_plan db for toal days attended
    public static int getTotalDays(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM member_plan WHERE username = '" + username + "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("total_days");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Read Function for member_plan db for toal payment
    public static int getTotalPayment(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM member_plan WHERE username = '" + username + "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("total_payment");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Read Function for member_plan db for days remaining
    public static int getDaysRemaining(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM member_plan WHERE username = '" + username + "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("days_remaining");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    //Read Function from login db
    public static class LoginData {
        public String username;
        public String password;
        public int type;

        public LoginData(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public static boolean read(LoginData logd) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM login WHERE username = '" + logd.username + "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                logd.username = rs.getString("username");
                logd.password = rs.getString("password");
                logd.type = rs.getInt("type");
                return true; //username exists 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //username does not exist 
    }
//--------------------------------------------------------------------------------------------------------------------
     
//Funcation to read all data and return string array from requests table to manage pending requests
    public static String[][] getListOfTrainers() {
        String[][] trainer_list = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from trainer"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            trainer_list = new String[totalRows][2]; //total no of rows in requests table in db //#
            sql = "SELECT username, name FROM trainer ";
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                trainer_list[index][0] = rs.getString("username");
                trainer_list[index][1] = rs.getString("name");
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return trainer_list; 
    }
//---------------------------------------------------------------------------------------------------------------------
  //Funcation to read all data and return string array from requests table to manage pending requests
  public static String[][] getListOfAttendies() {
    String[][] trainer_list = null;

    try {
        Statement st = conn.createStatement();
        String sql = "select COUNT(*) as totalrows from attendance"; 
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        int totalRows = rs.getInt("totalrows");
        trainer_list = new String[totalRows][2]; //total no of rows in requests table in db //#
        sql = "SELECT username, date FROM attendance WHERE date = '"+DB.getDate()+"'";
        rs = st.executeQuery(sql);

        int index = 0; //index for values in string array
        while (rs.next()) {
            trainer_list[index][0] = rs.getString("username");
            trainer_list[index][1] = rs.getString("date");
            index++;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    return trainer_list; 
}
//---------------------------------------------------------------------------------------------------------------------
    //Function to check if username exists and if not then log data for Request Plan Button (manage_plan.java)

    public static class RequestData{
        public int amount;
        public int days;
        public String date = DB.getDate();
        public RequestData(int days, int amount) {
            this.days = days;
            this.amount = amount;
        }
    }
    public static boolean sendRequest(RequestData reqd) { //boolean btw
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM requests WHERE username = '" + global.username + "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                try {//Update query
                    String sql2 = "update requests set days='" + reqd.days + "', amount='" +reqd.amount+ "', date='" + reqd.date + "' WHERE username='" +global.username+"'";
                    st.execute(sql2);
                    DB.acceptMembership(global.username);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

                return true; //username exists 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
            try {//Insert Query
                String sql = "INSERT INTO requests(username, days, amount, date) VALUES('" + global.username + "', '" + reqd.days + "', '"+reqd.amount+"', '" + reqd.date + "')"; 
                Statement st = conn.createStatement();
                st.execute(sql);
                DB.acceptMembership(global.username);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return false; //username does not exist 
    }
//---------------------------------------------------------------------------------------------------------------------

    //Function to read data from requests table in db
    //Modified read function from previously written read-login function
    public static class requestContent {
        public int days;
        public int amount;
        public String date;

        public requestContent(int days, int amount, String date) {
            this.days = days;
            this.amount = amount;
            this.date = date;
        }
    }

    public static boolean read_requestContent(requestContent reqC, String username) {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT days, amount, date from requests WHERE username = '" +username+ "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                reqC.days = rs.getInt("days");
                reqC.amount = rs.getInt("amount");
                reqC.date = rs.getString("date");
                
                return true; //username exists
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false; //username doesnt exist
    }
//---------------------------------------------------------------------------------------------------------------------
    //Function to insert in member progress table
    public static void insertProgress(float bw, float bfp) {
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * from progress WHERE username = '" +global.username+ "' AND date = '" +DB.getDate()+"'"; 
            ResultSet rs = st.executeQuery(sql);
    
            if (rs.next()) {
                // update if sername with current date already exists
                try {
                    String sql2 = "UPDATE progress SET bw = '" +bw+ "', bfp = '" +bfp+ "' WHERE username = '" +global.username+ "' AND date = '" +DB.getDate()+"'"; 
                    st.execute(sql2);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                // if username with current date doesnt exist then: insert new data
                try {
                    String sql3 = "INSERT INTO progress(username, date, bw, bfp) VALUES('" +global.username+ "', '" +DB.getDate()+ "', '" +bw+ "', '" +bfp+ "')"; 
                    st.execute(sql3);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------
        //Read Function from progress db
        public static Float[] readProgress(String username, String date) {
            try {
                Statement st = conn.createStatement();
                String sql = "SELECT bw, bfp from progress WHERE username = '" +username+ "' AND date = '" +date+ "'";
                ResultSet rs = st.executeQuery(sql);
        
                if (rs.next()) {
                    float bw = rs.getFloat("bw");   
                    float bfp = rs.getFloat("bfp"); 
                    System.out.println(date+":- Body Weight:"+bw+"kgs, Body Fat%:"+bfp);
                    return new Float[]{bw, bfp};    
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return null; // Return null if no record is found
        }
//---------------------------------------------------------------------------------------------------------------------
        //Function to read from progress table
        public static String[][] getProgressRecords(String username) {
            String[][] progress_records = null;
    
            try {
                Statement st = conn.createStatement();
                String sql = "SELECT COUNT(*) AS totalrows FROM progress WHERE username='" +username+ "'";
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                int totalRows = rs.getInt("totalrows");
                progress_records = new String[totalRows][3]; //total no of rows in requests table in db //#(Use the same method later to display total pending requests in admin home page)
                sql = "SELECT date, bw, bfp from progress where username='"+username+ "' ORDER BY date DESC";
                rs = st.executeQuery(sql);
    
                int index = 0; //index for values in string array
                while (rs.next()) {
                    progress_records[index][0] = rs.getString("date");
                    progress_records[index][1] = String.valueOf(rs.getFloat("bw"));
                    progress_records[index][2] = String.valueOf(rs.getFloat("bfp"));
                    index++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return progress_records; 
        }        
//---------------------------------------------------------------------------------------------------------------------  
        //Get Trainer Name
        public static String getTrainerName(String username) { 
            try {
                Statement st = conn.createStatement();
                String sql = "SELECT * FROM trainer_member_relation WHERE member = '" +username+ "'";
                ResultSet rs = st.executeQuery(sql);

                if (rs.next()) {
                    return rs.getString("trainer");
                }
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            return "No Trainer";
        }
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//ADMIN FUNCTIONS
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
        //Function to update current member plan rates
        public static void insertNewRates(int A, int B, int C, int D){
            try{
                (conn.createStatement()).executeUpdate("UPDATE payment_rates SET less_than7="+A+", 7_to_30 ="+B+", 31_to_180="+C+", more_than180="+D);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
//---------------------------------------------------------------------------------------------------------------------
            // Function to get the total number of accounts
    public static int getTotalNumberOfAccounts() {
        int totalRows = 0; 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT COUNT(*) AS totalrows FROM login"; 
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                totalRows = rs.getInt("totalrows");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totalRows ;
    }

//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
            // Function to get the total number of accounts
            public static int getTotalNumberOfMembers() {
                int totalRows = 0; 
                try {
                    Statement st = conn.createStatement();
                    String sql = "SELECT COUNT(*) AS totalrows FROM member"; 
                    ResultSet rs = st.executeQuery(sql);
                    if (rs.next()) {
                        totalRows = rs.getInt("totalrows");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                return totalRows ;
            }
        
        //---------------------------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------------------------
            // Function to get the total number of accounts
    public static int getTotalNumberOfTrainer() {
        int totalRows = 0; 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT COUNT(*) AS totalrows FROM trainer"; 
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                totalRows = rs.getInt("totalrows");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totalRows ;
    }

//---------------------------------------------------------------------------------------------------------------------
    //Function to get payment ko paisa haru
    public static String[][] getAmounts(){
        String[][] amounts = null;
        try {
            Statement st = conn.createStatement();
            amounts = new String[1][4]; 
            String sql = "SELECT * from payment_rates";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                amounts[0][0] = String.valueOf(rs.getInt("less_than7"));
                amounts[0][1] = String.valueOf(rs.getInt("7_to_30"));
                amounts[0][2] = String.valueOf(rs.getInt("31_to_180"));
                amounts[0][3] = String.valueOf(rs.getInt("more_than180"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return amounts; 
    }
//---------------------------------------------------------------------------------------------------------------------
    public static boolean checkUsernameFromLoginTable(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM login WHERE username = '" +username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                
                return true; //username exists 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //username does not exist 
    }
//---------------------------------------------------------------------------------------------------------------------
        //Funcation to search usernames in member for admin_view_members.java
        public static String[][] getSearchedFullnames(String u_name) {
            String[][] members_list = null;
    
            try {
                Statement st = conn.createStatement();
                String sql = "select COUNT(*) as totalrows from member where name LIKE '"+u_name+"%' AND name != 'empty'";; 
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                int totalRows = rs.getInt("totalrows");
                members_list = new String[totalRows][2]; //total no of rows in member table in db //#
                sql = "SELECT username, name from member WHERE name LIKE '"+u_name+"%' AND name != 'empty' ORDER BY name ASC";
                rs = st.executeQuery(sql);
    
                int index = 0; //index for values in string array
                while (rs.next()) {
                    members_list[index][0] = rs.getString("username");
                    members_list[index][1] = rs.getString("name");
                    index++;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return members_list; 
        }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // public static void insert_member_attendance() {
    //     try {
    //         Statement st = conn.createStatement();
    //         for (int i = 1; i <= 30; i++) {
    //             String date = String.format("2025-03-%02d", i);
    //             st.executeUpdate("INSERT INTO attendance (username, date) VALUES('trainer', '" + date + "')");
    //         }
    //         st.close(); 
    //     } catch (Exception e) {
    //         JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    //     }
    // }
//-------------------------------------------------------------------------------------------------------------------------

public static void insert_attendees_info(String username) {
    try {
        Statement st = conn.createStatement();
        
        // Query to get days remaining
        String sql1 = "SELECT days_remaining FROM member_plan WHERE username = '" + username + "'";
        ResultSet rs1 = st.executeQuery(sql1);
        int daysRemaining = 0;
        if (rs1.next()) {
            daysRemaining = rs1.getInt("days_remaining");
        }
        rs1.close(); 

       
        String sql2 = "SELECT total_days FROM member_plan WHERE username = '" + username + "'";
        ResultSet rs2 = st.executeQuery(sql2);
        int totalDays = 0;
        if (rs2.next()) {
            totalDays = rs2.getInt("total_days");
        }
        rs2.close(); 

      
        if (daysRemaining == 0) {
            JOptionPane.showMessageDialog(null, "This user's membership has ended, please Extend plan!");
            String disableEligibility = "UPDATE member_plan SET eligibility = 0 WHERE username = '" + username + "'";
            st.executeUpdate(disableEligibility);
        } else {
           
            int updatedDaysRemaining = daysRemaining - 1;
            int updatedTotalDays = totalDays + 1;


            String insertAttendance = "INSERT INTO attendance (username, date) VALUES('" + username + "', '" + DB.getDate() + "')";
            st.executeUpdate(insertAttendance);

            String updatePlan = "UPDATE member_plan SET days_remaining = " + updatedDaysRemaining + 
                                ", total_days = " + updatedTotalDays + " WHERE username = '" + username + "'";
            st.executeUpdate(updatePlan);
        }

        st.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
       
    }
}


//---------------------------------------------------------------------------------------------------------------------
public static boolean checkUsernameForAttendance(String username) { 
    try {
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM attendance WHERE username = '" +username+ "' AND date = '"+DB.getDate()+"'"; 
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            
            return true; //username exists 
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    return false; //username does not exist 
}
//---------------------------------------------------------------------------------------------------------------------

    //Funcation to get number of currently pending pr client requests
    public static int getNumberOfMembershipRequests() {
        int totalRows =0;
        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from requests"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            totalRows = rs.getInt("totalrows");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totalRows; 
    }
    //--------------------------------------------------------------------------------------------------------------------
    //Funcation to read all data and return string array from requests table to manage pending requests
    public static String[][] getPendingRequests() {
        String[][] pending_requests = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from requests"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            pending_requests = new String[totalRows][4]; //total no of rows in requests table in db //#
            sql = "SELECT username, days, amount, date from requests";
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                pending_requests[index][0] = rs.getString("username");
                pending_requests[index][1] = String.valueOf(rs.getInt("days"));
                pending_requests[index][2] = String.valueOf(rs.getInt("amount"));
                pending_requests[index][3] = rs.getString("date");
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pending_requests; 
    }
 
    //---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if username exists in requests table
    public static boolean doesUsernameExist(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM requests WHERE username = '" +username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //username exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //username does not exist 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Function to accept membership request
    public static void acceptMembership(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM requests WHERE username = '" +username+ "'"; 
            ResultSet rs = st.executeQuery(sql);

            int days_requested = 0;
            int amount_requested = 0;
            if (rs.next()) {
                days_requested =  rs.getInt("days");
                amount_requested =  rs.getInt("amount");
            }

            String sql2 = "SELECT * FROM member_plan WHERE username = '" +username+ "'"; 
            ResultSet rs2 = st.executeQuery(sql2);

            int days_remaining = 0;
            int total_payment = 0;
            int total_days=0;
            if (rs2.next()) {
                days_remaining =  rs2.getInt("days_remaining");
                total_payment =  rs2.getInt("total_payment");
                total_days =  rs2.getInt("total_days");
            }

            int final_days = days_requested+days_remaining;
            int final_amount = amount_requested+total_payment;
            int eligibility = 0;
            if (final_days>0){
                eligibility = 1;
            }

            String sql3 = "UPDATE member_plan SET days_remaining = '" +final_days+ "', eligibility = "+eligibility+", total_days = "+total_days+", total_payment = '" +final_amount+ "' WHERE username = '" +username+ "'";
            st.execute(sql3);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------
    //Delete function for requests
    public static void deleteRequest(String username){
        try{
            Statement st= conn.createStatement();
            String sql = "delete from requests where username = '" +username+ "'";
            st.executeUpdate(sql);
        }catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------
//TRAINER FUNCTIONS
//---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if username exists in trainer member relation table
    public static boolean doesMemberExistIn_TMRelation(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM trainer_member_relation WHERE member = '" +username+ "' AND trainer_id = '" +global.username+"' AND status = 'Accepted'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //member exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //member does not exist 
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------
    //Function to update trainer info
    public static void updateTrainerInfo(String password, String name, String contact, String speciality) {

        try {
            Statement st = conn.createStatement();
            //Update member table
            try {
                String sql1 = "UPDATE trainer SET name = '" + name + "', contact = '" + contact + "', speciality = '" + speciality + "' WHERE username = '" + global.username + "'";
                st.execute(sql1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating member: " + e.getMessage());
            }

            //Update login table
            try {
                String sql2 = "UPDATE login SET password = '" + password + "' WHERE username = '" + global.username + "'";
                st.execute(sql2);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error updating password: " + e.getMessage());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
        }
    }


    //----------------------------------------------------------------------------------------------------------------------------------------------
    // Read Function from trainer and login
    public static String[] getTrainerInfo(String username) {
        String[] info = new String[5]; 

        try {
        
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();

        
            String sql = "SELECT * FROM trainer WHERE username = '" + username + "'";
            ResultSet rs = st1.executeQuery(sql);

            if (rs.next()) {
                
                info[0] = rs.getString("name");
                info[2] = rs.getString("speciality");
                info[3] = rs.getString("contact");
                
            } else {
                JOptionPane.showMessageDialog(null, "User not found in trainer!");
                return null; 
            }

        
            String sql2 = "SELECT * FROM login WHERE username = '" + username + "'";
            ResultSet rs2 = st2.executeQuery(sql2);

            if (rs2.next()) {
                info[1] = rs2.getString("password");
            } else {
                JOptionPane.showMessageDialog(null, "User not found in login!");
                return null; 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return info;
    }
//---------------------------------------------------------------------------------------------------------------------
    //Funcation to read all data and return string array from trainer_member_relation table to view personally training clients
    public static String[][] getPersonalClients(String username) {
        String[][] pending_requests = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from trainer_member_relation where trainer_id='"+username+"' AND status = 'Accepted'"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            pending_requests = new String[totalRows][3]; //total no of rows in trainer_member_relation table in db //#
            sql = "SELECT member, name, date from trainer_member_relation where trainer_id='"+username+"' AND status = 'Accepted'"; 
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                pending_requests[index][0] = rs.getString("name");
                pending_requests[index][1] = rs.getString("member");
                pending_requests[index][2] = rs.getString("date");
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pending_requests; 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Funcation to get number of currently training clients
    public static int getNumberOfPersonalClients(String username) {
        int totalRows =0;
        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from trainer_member_relation where trainer_id='"+username+"' AND status = 'Accepted'";  
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            totalRows = rs.getInt("totalrows");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totalRows; 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Funcation to get number of currently pending pr client requests
    public static int getNumberOfPersonalClientsRequests(String username) {
        int totalRows =0;
        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from trainer_member_relation where trainer_id='"+username+"' AND status = 'Pending'"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            totalRows = rs.getInt("totalrows");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return totalRows; 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Funcation to read all data and return string array from trainer_member_relation table to view personally training clients requests
    public static String[][] getPersonalClientsRequests(String username) {
        String[][] pending_requests = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from trainer_member_relation where trainer_id='"+username+"' AND status = 'Pending'"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            pending_requests = new String[totalRows][3]; //total no of rows in trainer_member_relation table in db //#
            sql = "SELECT member, name, date from trainer_member_relation where trainer_id='"+username+"' AND status = 'Pending'"; 
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                pending_requests[index][0] = rs.getString("name");
                pending_requests[index][1] = rs.getString("member");
                pending_requests[index][2] = rs.getString("date");
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return pending_requests; 
    }

//---------------------------------------------------------------------------------------------------------------------
    //Function to insert member to calss
    public static void insertMembertoClass(int class_id){
        try{
            Statement st=conn.createStatement();
            String sql="INSERT into member_class_relation(username, class_id) VALUES('"+global.username+"', '"+class_id+"')";
            st.execute(sql);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------


    //Read Function to check if username exists in trainer member requests table
    public static boolean hasTheMemberRequestsAprTrainer(String username) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM trainer_member_relation WHERE member = '" +username+ "' AND trainer_id = '" +global.username+ "' AND status = 'Pending'"; 
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //member exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //member does not exist 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if class exists in classes table
    public static boolean doesClassExist(String class_id) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM classes WHERE class_id = '" +class_id+ "' AND trainer_id = '" +global.username+ "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //class exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //class does not exist 
    }

    //---------------------------------------------------------------------------------------------------------------------
    //Read Function to check if class exists in classes table
    public static boolean doesClassIDExist(String class_id) { 
        try {
            Statement st = conn.createStatement();
            String sql = "SELECT class_id FROM classes WHERE class_id = '" +class_id+ "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                return true; //class exists 
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return false; //class does not exist 
    }
//---------------------------------------------------------------------------------------------------------------------
    //Reject function for client requests
    public static void deletePRTrequest(String username) { 
        try{
            Statement st= conn.createStatement();
            String sql = "UPDATE trainer_member_relation SET status = 'Rejected' WHERE member = '" +username+ "' AND trainer_id = '" +global.username+ "'"; 
            st.execute(sql);
        }catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------
    //Accept personal client request function 
    public static void acceptPRTrequest(String username){ 
        try{
            Statement st= conn.createStatement();
            String sql = "UPDATE trainer_member_relation SET status = 'Accepted' WHERE member = '" +username+ "' AND trainer_id = '" +global.username+ "'"; 
            st.execute(sql);
        }catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
        }
    }
    
 //---------------------------------------------------------------------------------------------------------------------
//Function to get classesID
    // public static int getTrainerClassID(){
    //     try{
    //         Statement st=conn.createStatement();
    //         String sql="SELECT class_id from classes";
    //         ResultSet rs = st.executeQuery(sql);
    //         if (rs.next()) {
                
    //             return rs.getInt("class_id");
    //         }
    //     }catch(Exception e){
    //         JOptionPane.showMessageDialog(null, e);
    //     }
    //     return 0;
    // }

    public static String[][] getTrainerClassID() {
        String[][] classes = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from classes"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            classes = new String[totalRows][6]; //total no of rows in classes table in db //#
            sql = "SELECT class_id, date from classes ";
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                classes[index][0] = rs.getString("class_id");
                classes[index][1] = rs.getString("date");
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return classes; 
    }
//---------------------------------------------------------------------------------------------------------------------
//trainer_attendance

public static String[][] getAttendedDays() {
    String[][] attendance = null;

    try {
        Statement st = conn.createStatement();
        String sql = "select COUNT(*) as totalrows from attendance WHERE username ='" +global.username+"'"; 
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        int totalRows = rs.getInt("totalrows");
        attendance = new String[totalRows][1]; 
        sql = "SELECT date from attendance WHERE username ='" +global.username+"' ORDER BY date DESC";
        rs = st.executeQuery(sql);

        int index = 0; //index for values in string array
        while (rs.next()) {
            attendance[index][0] = rs.getString("date");
            index++;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
    return attendance; 
}
//---------------------------------------------------------------------------------------------------------------------

//---------------------------------------------------------------------------------------------------------------------
//trainer_attendance

public static int getTotalAttendedDays() {


    try {
        Statement st = conn.createStatement();
        String sql = "select COUNT(*) as totalrows from attendance WHERE username ='" +global.username+"'"; 
        ResultSet rs = st.executeQuery(sql);
        rs.next();
        int totalRows = rs.getInt("totalrows");
        return totalRows;


    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
        return 0;
}
//---------------------------------------------------------------------------------------------------------------------
    //Function to get total number of classes
    public static int getNoOfClasses(){
        try{
            Statement st=conn.createStatement();
            String sql="SELECT class_id from member_class_relation WHERE username='empty'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int number = rs.getInt("class_id");
                int incremented_num = number+1;
                st.execute("UPDATE member_class_relation SET class_id = "+incremented_num+" WHERE username='empty'");
                return incremented_num;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return 0;
    }
//---------------------------------------------------------------------------------------------------------------------
    //Function to insert classes
    public static void insertClass(String class_id, String topic, String date, String startsFrom, String endsAt){
        try{
            Statement st=conn.createStatement();
            String sql="INSERT into classes (class_id, topic, trainer_id, date, starting_time, ending_time, participants) VALUES('"+class_id+"', '"+topic+"', '"+global.username+"', '"+date+"', '"+startsFrom+"', '"+endsAt+"', 0)";
            st.execute(sql);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    
//---------------------------------------------------------------------------------------------------------------------
    //Function to delete class
    public static void deleteClass(String class_id){
        try{
            Statement st=conn.createStatement();
            st.executeUpdate("DELETE FROM classes WHERE class_id = '"+class_id+"'");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
//---------------------------------------------------------------------------------------------------------------------
//Funcation to read all data and return string array from classes table for Panel 3 in trainer_schedule_classes
    public static String[][] getClasses() {
        String[][] classes = null;

        try {
            Statement st = conn.createStatement();
            String sql = "select COUNT(*) as totalrows from classes"; 
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int totalRows = rs.getInt("totalrows");
            classes = new String[totalRows][6]; //total no of rows in classes table in db //#
            sql = "SELECT class_id,topic,date, starting_time, ending_time, participants from classes WHERE trainer_id = '"+global.username+"'";
            rs = st.executeQuery(sql);

            int index = 0; //index for values in string array
            while (rs.next()) {
                classes[index][0] = rs.getString("class_id");
                classes[index][1] = rs.getString("topic");
                classes[index][2] = rs.getString("date");
                classes[index][3] = rs.getString("starting_time");
                classes[index][4] = rs.getString("ending_time");
                classes[index][5] = String.valueOf(rs.getInt("participants"));
                index++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return classes; 
    }
//---------------------------------------------------------------------------------------------------------------------
        //Function to delete classes from previous date
        public static void deleteOldClasses() {
        try{
            Statement st=conn.createStatement();
            st.executeUpdate("DELETE FROM classes WHERE date < CURRENT_DATE");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        }
//---------------------------------------------------------------------------------------------------------------------
        //Funcation to get number of currently pending pr client requests
        public static int getNoOfPersonalClasses() {
            int totalRows =0;
            try {
                Statement st = conn.createStatement();
                String sql = "select COUNT(*) as totalrows from classes where trainer_id='"+global.username+"'"; 
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                totalRows = rs.getInt("totalrows");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return totalRows; 
        }
//---------------------------------------------------------------------------------------------------------------------
    //Function to get class data of specific class
    public static String[][] getClassData(int class_id){
        String[][] classData =null;
        try{
            Statement st=conn.createStatement();
            String sql2 = "select COUNT(*) as totalrows from classes"; 
            ResultSet rs2 = st.executeQuery(sql2);
            rs2.next();
            int totalRows = rs2.getInt("totalrows");
            classData = new String[totalRows][2]; //total no of rows in classes table in db //#
            String sql="SELECT member_class_relation.username, member.name FROM member_class_relation INNER JOIN member ON member_class_relation.username = member.username WHERE member_class_relation.class_id = "+class_id;
            ResultSet rs = st.executeQuery(sql);
            int index = 0; //index for values in string array
            while (rs.next()) {
                classData[index][0] = rs.getString("name");
                classData[index][1] = rs.getString("username");
                index++;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return classData;
    }
//---------------------------------------------------------------------------------------------------------------------
}
