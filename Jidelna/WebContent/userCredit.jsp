<%-- 
    JSP stránka pro doplnění kreditu.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kredit</title>
    </head>
    <body>
        <%
			if(request.getParameter("send") != null) {
				user.setCredit(user.getCredit()+Double.parseDouble(request.getParameter("credit")));
				
			}
			if(request.getParameter("back") != null) {
				response.sendRedirect("homepage.jsp");
			}
	%>
        <h1>Doplnění kreditu:</h1>
        Aktuální stav: <jsp:getProperty name="user" property="credit"/>
        <form action="" method="post">
            <label>Zadejte navýšení kreditu(záporná hodnota kredit sníží):</label>
            <input type="text" name="credit"/>
            <input type="submit" name="send" value="Potvrdit"/>
            <input type="submit" name="back" value="Zpět"/>
        </form>
    </body>
</html>
