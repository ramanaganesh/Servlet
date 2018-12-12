package com.bridgelabz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.crypto.Cipher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.bridgelabz.repository.Connecting;

public class LoginPage extends HttpServlet
{
	/**
	 * 
	 */

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,  IOException 
	{
		response.setContentType("text/html");
        int flag=0;
        PrintWriter writer = response.getWriter();  
        String emailName=request.getParameter("emailName");
        String password=request.getParameter("password");
       // System.out.println(emailName.length()+ " " +password.length());
        try
        {
       		Connection connection=Connecting.getConnect();
        	PreparedStatement preparedStatement=connection.prepareStatement("select * from userdetails");
			ResultSet resultSet=preparedStatement.executeQuery();
			if(emailName.length()!=0 && password.length()!=0)
			{
				while(resultSet.next()!=false)
				{
					ServletContext context=getServletContext();
			        String secretKey=context.getInitParameter("key"); 
			     //   System.out.println("login");
			      //  System.out.println(secretKey+" "+resultSet.getString(3));
			        String decryptedPassword = AES.decrypt(resultSet.getString(3), secretKey) ;
					
					if(emailName.equals(resultSet.getString(2)) && password.equals(decryptedPassword))
					{
						writer.write("you are authorized one to access database");
						RequestDispatcher dispatcher=getServletConfig().getServletContext().getRequestDispatcher("/HomePage.jsp");
		
						dispatcher.forward(request,response);
					

						flag=1;
						break;
					}
				
				}
				resultSet=preparedStatement.executeQuery();
			if(resultSet.next()==false)
			{
				writer.write("You are the fisrt user click register link to register");
				flag=1;
				request.getRequestDispatcher("login.html").include(request, response);
				
			}
			}
	       
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         if(flag==0)
		{
			//		System.out.println(flag);
		        	writer.print("Sorry,you are not authorized one to access database please register!");
		        	request.getRequestDispatcher("login.html").include(request, response);
		        
		   
		}
       // System.out.println(emailName.length()+" "+password.length());
       
	  }
	public void doPost(HttpServletRequest request , 
		    HttpServletResponse response)
		    throws ServletException , IOException {
		        doGet(request,response);
		  }
	
}
