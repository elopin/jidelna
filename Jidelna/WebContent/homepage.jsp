<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="jidelna.beans.UserBean" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp" />



<div id="container">
<jsp:include page="menu.jsp" />

<div id="content"> 
	<h1>Rezervace obědů</h1>
        <%
            if(request.getParameter("newUser") != null) {
                response.sendRedirect("userForm.jsp");
            } else if(request.getParameter("edit") != null) {
		session.setAttribute("edit", Integer.parseInt(request.getParameter("edit")));
                response.sendRedirect("userForm.jsp");
            } else if (request.getParameter("remove") != null) {
		session.setAttribute("remove", request.getParameter("remove"));
                response.sendRedirect("removeUser.jsp");
            }
       
            if (user.isAdmin()) {
		DataRepository repository = new DataRepositoryImpl();
        %>

	<div id="homepage">
        <h2>Seznam strávníků:</h2>

        <form id="userList" action="" method="post"> 
            <table id="userListTable">
                <% for (UserBean u : repository.getUsers()) { %>
		
                <tr>
                    <td><label><% out.print(u.getDisplayName()); %></label></td>
                    <td><button class="appButton" type="submit" name="edit" value="<% out.print(u.getId()); %>">Editovat</button></td>
                    <td><button class="appButton" type="submit" name="remove" value="<% out.print(u.getId()); %>">Odstranit</button></td>
                </tr>

                <% } %>
            </table>
	    <input type="submit" name="newUser" value="Přidat strávníka"/>
        </form>
        
	</div>
        <% } %>
</div>	
</div>	
<jsp:include page="footer.jsp"/>