<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="newUser" scope="page" class="jidelna.beans.UserBean"/>
<jsp:useBean id="users" scope="application" class="jidelna.beans.UsersBean"/> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nový strávník</title>
</head>
<body>
<h2>Registrace nového strávníka:</h2>

	<%  if(request.getParameter("register") != null ) {
	%>
		<jsp:setProperty name="newUser" property="*" />	
	<%
			users.getUsers().add(newUser);
			response.sendRedirect("homepage.jsp");
		} 
	%>

<form action="newUserForm.jsp" method="post">
 <table>
  <tr>
  <td><label>E-mail:</label></td><td><input type="text" name="email" /></td>
  </tr>
  <tr>
  <td><label>Heslo:</label></td><td><input type="password" name="password" /></td>
  </tr>
  <tr>
  <td>Administrátor <input type="checkbox" name="admin"/></td><td><input type="submit" name="register" value="Uložit"></td>
  </tr>
  </table>
 </form>

</body>
</html>