<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>    
<jsp:useBean id="users" scope="application" class="jidelna.beans.UsersBean"/> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<%
			if(request.getParameter("logout") != null) {
				user.setLoggedIn(false);
				response.sendRedirect("/Jidelna");
			}
			if(request.getParameter("menu") != null) {
				response.sendRedirect("/Jidelna/CalendarServlet");
			}
	%>
			
			
	
			Přihlášen uživatel: <jsp:getProperty name="user" property="email"/>
			<form action="" method="post">
				<input type="submit" name="logout" value="Odhlásit"/>
			</form>
			<form action="" method="post">
				<input type="submit" name="menu" value="Obědy"/>
			</form>
			<form action="" method="post">
				<input type="submit" name="credit" value="Kredit"/>
			</form>
			
			
	<% 
			if(user.isAdmin()) { 
	%>
			
			
			<br>Seznam strávníků:<br>
			<ul>
	<% 
			for(int i = 0; i < users.getUsers().size(); i++) {
				out.println("<li>"+users.getUsers().get(i).getEmail());
			} 
			
	%>
			</ul>
			<form action="newUserForm.jsp" method="post">
			<input type="submit" name="newUser" value="Přidat strávníka"/>
			</form>
	<%
			}
	%>
	
  
</body>
</html>