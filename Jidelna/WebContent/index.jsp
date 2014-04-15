<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jidelna.security.SecurityService" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="loginUser" scope="page" class="jidelna.beans.UserBean"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>Přihlášení</title>
</head>
<body>


	<%
                
		if (request.getParameter("login") != null) {	
	%>
		<jsp:setProperty name="loginUser" property="*"/>
                
	 <%
                        DataRepository repository = new DataRepositoryImpl();
                        SecurityService security = new SecurityService();
	 		loginUser.setValid(security.authenticate(repository.getPasswordHashByEmail(loginUser.getEmail()), loginUser.getPassword()));
			
	 
			if(!loginUser.isValid()) {	
	 %>
	 			<label style="color: red">Neplatné přihlašovací údaje!</label>
	 <%
	 		} else {
                                loginUser.setData(repository.getUserByEmail(loginUser.getEmail()));
	 			loginUser.setLoggedIn(true);
                                session.setAttribute("user", loginUser);
	 			response.sendRedirect("homepage.jsp");
	 		}
		
	 	
	 	}
	
		
	 %>

 <form action="" method="post">
 <table>
  <tr>
  <td><label>E-mail:</label></td><td><input type="text" name="email" value="elopin@seznam.cz"/></td>
  </tr>
  <tr>
  <td><label>Heslo:</label></td><td><input type="password" name="password" value="admin"/></td>
  </tr>
  <tr>
  <td/><td><input type="submit" name="login" value="Přihlásit"></td>
  </tr>
  </table>
 </form>
</body>
</html>