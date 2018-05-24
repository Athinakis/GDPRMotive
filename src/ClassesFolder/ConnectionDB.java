/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesFolder;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author isosoft2
 */
public class ConnectionDB {
    
 

    
    public static DataSource getMySqlDataSource(){
       MysqlDataSource datasource =new MysqlDataSource();
        

        //data source properties
        
        datasource.setServerName("localhost");
        datasource.setPortNumber(3306);
        datasource.setDatabaseName("registeredusers");
        datasource.setUser("root");
        datasource.setPassword("root");
        
        return  datasource;
    }
    public boolean  LoginVerification(String username,String password) throws SQLException{
    
        
        boolean verified=false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String Password=null;
        try{
            conn = getMySqlDataSource().getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Password FROM users1 WHERE Username='"+username+"'");
            while(rs.next())
                Password =rs.getString("Password");
                if(Password.equals(password))
                   verified=true; 
        }catch(SQLException ex){
        System.out.println("oups");
        System.out.println(verified);
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println(verified);
        System.out.println(Password);
        return verified;
    }
    
    public void RegisterNewUser(String username,String password)  
    {
        String Username=null;
        String Password=null;
        PreparedStatement stmt=null;
        
        Connection conn=null;
        try{
            Username=username;
            Password=password;
            conn=getMySqlDataSource().getConnection();
            String query="INSERT INTO users1"+"(Username,Password)VALUES"+"(?,?)";
            stmt=conn.prepareStatement(query);
            stmt.setString(1,Username);
            stmt.setString(2,Password);
            String showString=stmt.toString();
            System.out.println(showString);
            stmt.executeUpdate();
        
        }catch(SQLException ex){
            System.out.println("oups problem on register");
        }
        try {
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("statement not closed");
        }
        try {
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("conn not closed");
        }
        
        
        
    }




}
