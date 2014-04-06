<%-- 
    JSP stránka pro doplnění kreditu.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:useBean id="pageUser" scope="page" class="jidelna.beans.UserBean"/>
<jsp:useBean id="users" scope="application" class="jidelna.beans.UsersBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kredit</title>
    </head>
    <body>
        <%
            pageUser = users.getUsers().get(user.getEmail());            
	    if(request.getParameter("send") != null) {
		users.getUsers().get(pageUser.getEmail()).setCredit(pageUser.getCredit()+Double.parseDouble(request.getParameter("credit")));
                pageUser.setCredit(users.getUsers().get(pageUser.getEmail()).getCredit());
	    }
	    if(request.getParameter("back") != null) {
		response.sendRedirect("homepage.jsp");
	    }
            
            
	%>
        <h1>Doplnění kreditu:</h1>
        Aktuální stav: <jsp:getProperty name="pageUser" property="credit"/>
        <% out.println(pageUser.getCredit()); %>
        <form action="" method="post">
            <label>Zadejte navýšení kreditu(záporná hodnota kredit sníží):</label>
            <input type="text" name="credit"/>
            <input type="submit" name="send" value="Potvrdit"/>
            <input type="submit" name="back" value="Zpět"/>
        </form>
    </body>
</html>
