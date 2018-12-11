package com.bridgelabz.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connecting 
{
static public Connection getConnect()
{
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","root");
		if(connection!=null)
		{
			return connection;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
}
