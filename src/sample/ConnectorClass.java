package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectorClass {
    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel","root","");
            System.out.println("Connected");
            return connection;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
