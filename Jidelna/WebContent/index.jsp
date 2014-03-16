<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:useBean id="users" scope="application" class="jidelna.beans.UsersBean"/>
<jsp:useBean id="menus" scope="application" class="jidelna.beans.DaysMenuBean"/>
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
		<jsp:setProperty name="user" property="*"/>
	 <%
	 		user.setValid(user.checkCredentials(users));
			
	 
			if(!user.isValid()) {	
	 %>
	 			<label style="color: red">Neplatné přihlašovací údaje!</label>
	 <%
	 		} else {
	 			user.setLoggedIn(true);
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
  <td><label>Heslo:</label></td><td><input type="password" name="password" value="jidelna"/></td>
  </tr>
  <tr>
  <td/><td><input type="submit" name="login" value="Přihlásit"></td>
  </tr>
  </table>
 </form>
</body>
</html>