<%-- 
    JSP stránka pro doplnění kreditu.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jidelna.beans.UserBean" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:useBean id="pageUser" scope="page" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kredit</title>
    </head>
    <body>
        <%
            DataRepository repository = new DataRepositoryImpl();
            for(UserBean userRepo : repository.getUsers()) {
                if(userRepo.getId() == user.getId()) {
                    pageUser = userRepo;
                }
            }
                      
	    if(request.getParameter("send") != null) {
		pageUser = repository.updateUserCredit(pageUser, Double.parseDouble(request.getParameter("credit")));
	    }
	    if(request.getParameter("back") != null) {
		response.sendRedirect("homepage.jsp");
	    }
            
            
	%>
        <h1>Doplnění kreditu:</h1>
        Aktuální stav: <% out.println(pageUser.getCredit()); %>
        <form action="" method="post">
            <label>Zadejte navýšení kreditu(záporná hodnota kredit sníží):</label>
            <input type="text" name="credit"/>
            <input type="submit" name="send" value="Potvrdit"/>
            <input type="submit" name="back" value="Zpět"/>
        </form>
    </body>
</html>
