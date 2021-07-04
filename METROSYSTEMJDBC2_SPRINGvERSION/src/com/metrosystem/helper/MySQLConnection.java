package com.metrosystem.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Component;
@Component("MySqlConnection")
public class MySQLConnection {
	
public static Connection getConnection() throws SQLException, ClassNotFoundException, IOException {
		
	
	    Properties props = new Properties();   //ading a properties class 
	    FileInputStream fis = new FileInputStream("dbConnection.properties");
	    props.load(fis);
	    String dname = (String) props.get("db.driver");
	    String url = (String) props.get("db.url");
	    String username = (String) props.get("db.username");
	    String password = (String) props.get("db.password");
	    
	    	
		Class.forName(dname);
		//1.2 Connect
		Connection connection=DriverManager.getConnection(url, username, password);
		
		return connection;
	}

}
