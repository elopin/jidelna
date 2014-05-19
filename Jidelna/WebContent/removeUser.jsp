<%-- 
    Document   : removeUser
    Created on : 16.4.2014, 20:54:17
    Author     : elopin
--%>

<%@page import="jidelna.connection.DataRepositoryImpl"%>
<%@page import="jidelna.connection.DataRepository"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="removedUser" scope="page" class="jidelna.beans.UserBean"/>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>

<jsp:include page="header.jsp"/>
<div id="container">
<jsp:include page="menu.jsp"/>    
<div id="content">

        <%
            if(!user.isAdmin()) {
                response.sendRedirect("homepage.jsp");
            }
            
            DataRepository repository = new DataRepositoryImpl();
            
	    String idUser = (String)session.getAttribute("remove");
	    if(idUser != null) {
                removedUser.setData(repository.getUserById(Integer.parseInt(idUser)));
	    }
	    
            String yes = request.getParameter("yes");
            if(yes != null) {
                
                removedUser.setId(Integer.parseInt(yes));
                repository.deleteUser(removedUser);
                response.sendRedirect("homepage.jsp");
            } else {
                
                %><h2>Opravdu chcete smazat uživatele : <% out.print(removedUser.getDisplayName()); %>?</h2>
                <form action="removeUser.jsp" method="post">  
                    <button class="appButton" type="submit" name="yes" value="<% out.print(removedUser.getId()); %>">Odstranit</button>
                </form>
         <% } %>
</div></div>
<jsp:include page="footer.jsp"/>
