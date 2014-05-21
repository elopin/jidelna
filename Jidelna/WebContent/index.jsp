<%-- 
    Document   : index
    Created on : 18.5.2014, 21:44:51
    Author     : Lukáš Janáček

    Stránka s úvodním formulářem pro přihlášení. Obsahuje kontrolu databáze, která
    v případě chybějící tabulky v databázi nebo účtu administrátora spustí obnovu.
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
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
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
<title>Přihlášení</title>
</head>
<body>
<div>
    <h1>Rezervace obědů ve školní jídelně.</h1> 
    <%        
    if (request.getParameter("login") != null) {	
    %>	<jsp:setProperty name="loginUser" property="*"/> <%
	
        Locale locale = new Locale("cs", "CZ");
        Calendar calendar = Calendar.getInstance(locale);
        StringBuilder date = new StringBuilder();
	
        date.append(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale));
        date.append(" ");
        date.append(calendar.get(Calendar.DAY_OF_MONTH));
        date.append(".");
        date.append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
        date.append(" ");
        date.append(calendar.get(Calendar.YEAR));
        session.setAttribute("currentDay", date.toString());
                    
        DataRepository repository = new DataRepositoryImpl();
	repository.checkDatabase();
	
        SecurityService security = new SecurityService();
	loginUser.setValid(security.authenticate(repository.getPasswordHashByEmail(loginUser.getEmail()), loginUser.getPassword()));
			
	if(!loginUser.isValid()) {	                 
	%>  <label style="color: red">Neplatné přihlašovací údaje!</label> <%
	} else {
            loginUser.setData(repository.getUserByEmail(loginUser.getEmail()));
	    loginUser.setLoggedIn(true);
            session.setAttribute("user", loginUser);
	    request.getRequestDispatcher("WEB-INF/jsp/homepage.jsp").forward(request, response);
	}
    }
    %>
    <div id="login">
        <form id="loginForm" action="" method="post">
            <table>
                <tr>
                    <td><label>E-mail:</label></td>
		    <td><input type="text" name="email" value="admin"/></td>
                </tr>
                <tr>
                    <td><label>Heslo:</label></td>
		    <td><input type="password" name="password" value="administrator"/></td>
                </tr>
            </table>
                <input type="submit" name="login" value="Přihlásit">
        </form>
    </div>
</div>
<jsp:include page="WEB-INF/jsp/footer.jsp"/>