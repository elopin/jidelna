<%-- 
    Document   : header
    Created on : 13.4.2014, 15:50:15
    Author     : elopin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
        <title></title>
    </head>
    <body>
        <%
	    if(request.getParameter("logout") != null) {
                user.setLoggedIn(false);
                response.sendRedirect("index.jsp");
	    }
			
	%>
        <div id="header">
            <div id="date">
                <label>Dnešní datum: <% out.print((String)session.getAttribute("currentDay")); %></label>
            </div>
            
            <div id="logout">
                <div id="nameLabel">Přihlášen uživatel: <jsp:getProperty name="user" property="displayName"/></div>
            <form action="header.jsp" method="post">
		<input type="submit" name="logout" value="Odhlásit"/>
            </form>
            <form action="userForm.jsp" method="post">
                <button type="submit" name="edit" value="<jsp:getProperty name="user" property="id"/>">Profil</button>
            </form>
            </div>
        </div>
    </body>
</html>
