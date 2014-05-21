<%-- 
    Document   : removeUser
    Created on : 16.4.2014, 20:54:17
    Author     : Lukáš Janáček

    Stránka s požadavkem o potvrzení smazání uživatele.
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
        DataRepository repository = new DataRepositoryImpl();
            
        String idUser = (String)session.getAttribute("remove");
        if(idUser != null) {
            removedUser.setData(repository.getUserById(Integer.parseInt(idUser)));
	}
	    
        String yes = request.getParameter("yesRemove");
        if(yes != null) {     
            removedUser.setId(Integer.parseInt(yes));
            repository.deleteUser(removedUser);
	    session.setAttribute("redirect", "WEB-INF/jsp/homepage.jsp");
	    response.sendRedirect("Jidelna");
        } else {        
    %>      <h2>Opravdu chcete smazat uživatele : <% out.print(removedUser.getDisplayName()); %>?</h2>
            <form action="Jidelna" method="post">  
                <button class="appButton" type="submit" name="yesRemove" value="<% out.print(removedUser.getId()); %>">Odstranit</button>
            </form>
    <%  } %>
</div></div>
<jsp:include page="footer.jsp"/>
