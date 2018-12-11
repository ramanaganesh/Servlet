package com.bridgelabz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
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
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,  IOException 
	  {
		response.setContentType("text/html");
        int flag=0;
        PrintWriter writer = response.getWriter();  
        String emailName=request.getParameter("emailName");
        String password=request.getParameter("password");
        try
        {
       		Connection connection=Connecting.getConnect();
        	PreparedStatement preparedStatement=connection.prepareStatement("select * from userdetails");
			ResultSet resultSet=preparedStatement.executeQuery();
			if(emailName!=null && password!=null)
			{
				while(resultSet.next()!=false)
				{
					if(emailName.equals(resultSet.getString(2)) && password.equals(resultSet.getString(3)))
					{
						writer.write("you are authorized one to access database");
					
						RequestDispatcher dispatcher=getServletConfig().getServletContext().getRequestDispatcher("/welcomePage");
		
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
       // System.out.println(emailName.length()+" "+password.length());
        if(flag==0 && emailName.length()!=0 && password.length()!=0 )
        {
        	writer.print("Sorry,you are not authorized one to access database please register!");
        	request.getRequestDispatcher("login.html").include(request, response);
        }
        else
        {
        	writer.print("Please, fill the field first");
        	request.getRequestDispatcher("login.html").include(request, response);
        }
	  }
}
