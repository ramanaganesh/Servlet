package com.bridgelabz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.repository.Connecting;

public class HomePage extends HttpServlet 
{

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,  IOException 
	  {
		response.setContentType("text/plain");
		PrintWriter printWriter=response.getWriter();
			Connection connection=Connecting.getConnect();
			PreparedStatement preparedStatement;
			try {
				preparedStatement = connection.prepareStatement("select * from userdetails");
				ResultSet resultSet=preparedStatement.executeQuery();
				
				printWriter.write("WELCOME   TO   OUR    HOME   PAGE");
				printWriter.println();
				printWriter.println("name         email");
				while(resultSet.next())
				{
					printWriter.println();
					if(resultSet.getString(4).equals("user"))
					printWriter.write(resultSet.getString(1)+"        "+resultSet.getString(2));
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
	  }
	 public void doPost(HttpServletRequest request , 
			    HttpServletResponse response)
			    throws ServletException , IOException {
			        doGet(request,response);
			  }
}
