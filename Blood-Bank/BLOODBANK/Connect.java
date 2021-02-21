// Sql connector for the database 
package BLOODBANK;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect{
    protected static Connection conn=null;
    public static Connection databaseConnection(){
        String url = "jdbc:sqlite:BloodBank.db";
        if(conn==null){
            try{
                conn = DriverManager.getConnection(url);
                
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
        }else{
            return conn;
        }
        return conn;
    }
}