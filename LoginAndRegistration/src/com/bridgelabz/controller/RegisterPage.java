package com.bridgelabz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.repository.Connecting;

public class RegisterPage extends HttpServlet
{
	 public void doPost(HttpServletRequest request , HttpServletResponse response)throws ServletException , IOException 
	 {
		 response.setContentType("text/html");
		 int flag=0;
		 PrintWriter out = response.getWriter();  
		 
		 	String name = request.getParameter("userName");
	        String emailId = request.getParameter("emailId");
	        String password = request.getParameter("password");
	        String role=request.getParameter("role");
	        
			Connection con=Connecting.getConnect();	
				
	           
	 
				try {
					PreparedStatement ps1=con.prepareStatement("select * from userdetails");
					ResultSet resultSet=ps1.executeQuery();
					
						while(resultSet.next()!=false)
						{
							if(emailId.equals(resultSet.getString(2)))
							{
								out.write("Sorry,Account exist try again with another Email-Id or log-in");
								request.getRequestDispatcher("Registration.html").include(request, response);
								flag=1;
								
							}
						}
					
					if(flag==0)
					{
						PreparedStatement ps = con.prepareStatement("insert into userdetails values(?,?,?,?)");
						ps.setString(1, name);
						ps.setString(2, emailId);
						ps.setString(3, password);
						ps.setString(4,role);
						int i = ps.executeUpdate();
						if (i > 0)
						{
							out.print("You are successfully registered...");
							out.println("<html><body bgcolor='yellow'>Login?<a href='login.html'>login</a></body></html>");
						}
					}
				
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
				out.close();
	    
	 }
}
