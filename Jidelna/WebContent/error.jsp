<%--
    JSP stránka pro zachytávání výjimek.
--%>
<%@page import="jidelna.beans.UserBean"%>
<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chyba</title>
    </head>
    <body>
        <h1>Chyba v aplikaci pro rezervaci obědů.</h1>
        Zpráva:
        <%=exception.getMessage() %>
	<% ((UserBean)session.getAttribute("user")).setLoggedIn(false); %>
    </body>
</html>
