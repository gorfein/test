//import org.apache.log4j.Logger;

//import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import java.sql.DriverManager;
//import java.sql.ResultSet;

/**
 * Created with IntelliJ IDEA.
 * User: Xin
 * Date: 2/04/13
 * Time: 5:58 PM
 * The Torrent Tracker (Phase 2, User Interface)
 */
public class Main {
    //private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] arg){
/*
        System.out.println("abaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".indexOf("aaaadaaaaaaaaaaaaaaaaaaaaaaa"));
        if(true)return;
*/
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/world?"
                            + "user=root&password=c4btr33");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query              where py = 'ni' or py = 'na'
            resultSet = statement
                    .executeQuery("select distinct py from pinyin");
            //writeResultSet(resultSet);
            List<String> pyList = new ArrayList<String>();
            while (resultSet.next()) {
                pyList.add(resultSet.getString("py"));
            }

            int count = 0;
            for(String py : pyList) {
            String findPy = "select * from english_name WHERE name like '" + py + "%'";

            resultSet = statement.executeQuery(findPy);
            while (resultSet.next()) {
                String name = resultSet.getString("name").toLowerCase();
                //if(!name.equals(py))
                name = compare(pyList, py, name);
                if (name != null) System.out.println(name);
                //if(++count%1000 == 0) System.out.println(count + " names processed");
            }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (connect != null) {
                    connect.close();
                }
            } catch (Exception e1) {
            System.err.println(e1.getStackTrace());
            }
        }
    }

    private static String compare(List<String> pyList, String lastPy, String lastName) throws Exception {

        for(String py : pyList) {
            String pyToLookUp = lastPy+py;
            //if(pyToLookUp.equals("nina"))System.out.println(pyToLookUp +"--"+lastName);
            if(lastName.equals(pyToLookUp)) return lastName;
            if(!lastName.contains(pyToLookUp)) return null;
            //if (pyToLookUp.length() >= lastName.length()) return null;
            return compare(pyList, pyToLookUp, lastName);
        }
        return null;
    }
}
