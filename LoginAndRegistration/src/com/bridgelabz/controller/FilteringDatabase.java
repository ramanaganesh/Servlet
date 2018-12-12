package com.bridgelabz.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.*;
import javax.servlet.RequestDispatcher;
import com.bridgelabz.repository.Connecting;

public class FilteringDatabase implements Filter
{

	FilterConfig config;
	public void init(FilterConfig config) throws ServletException {
		this.config=config;
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		int flag=0;
		response.setContentType("text/html");
		PrintWriter printWriter=response.getWriter();
		Connection connection=Connecting.getConnect();
		String email=request.getParameter("emailName");
		String password=request.getParameter("password");
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement("select * from userdetails");
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()!=false)
			{
				String secretKey=config.getServletContext().getInitParameter("key");
			//	System.out.println("Filter");
			//	System.out.println(secretKey+" "+resultSet.getString(3));
		        String decryptedPassword = AES.decrypt(resultSet.getString(3), secretKey) ;
		       // System.out.println(decryptedPassword);
				if(resultSet.getString(2).equals(email) && password.equals(decryptedPassword) && resultSet.getString(4).equals("admin"))
				{
					  RequestDispatcher rd=request.getRequestDispatcher("/AdminPage.jsp");
					  rd.forward(request, response);
					  flag=1;
					  break;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag==0)
		{
			chain.doFilter(request, response);
		}
		
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
