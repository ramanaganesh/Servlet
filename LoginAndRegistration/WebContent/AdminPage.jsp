
<html>
<body> 
<h1>ADMIN     PAGE</h1> 
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%  
String name=request.getParameter("emailName");  
/* out.print("welcome "+name+" admin");  */
Class.forName("com.mysql.jdbc.Driver");
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet","root","root");
PreparedStatement preparedStatement=connection.prepareStatement("select * from userdetails");
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
	if(name.equals(resultSet.getString(2)))
	{
		out.print("welcome "+resultSet.getString(1)+" admin"); 
	}
		
}

%>  

</body>
</html>