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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Odstranění uživatele</title>
    </head>
    <body>
        <%
            if(!user.isAdmin()) {
                
                System.out.println("ADMIN: "+user.isAdmin());
                response.sendRedirect("homepage.jsp");
            }
            
            if(request.getParameter("back") != null) {
                response.sendRedirect("homepage.jsp");
            }
            
            DataRepository repository = new DataRepositoryImpl();
            
            String yes = request.getParameter("yes");
            
            System.out.println("EHE: "+yes);
            
            if(yes != null) {
                
                removedUser.setId(Integer.parseInt(yes));
                repository.deleteUser(removedUser);
                response.sendRedirect("homepage.jsp");
            }
            
            String idUser = request.getParameter("remove");
            if(idUser != null) {
                removedUser.setData(repository.getUserById(Integer.parseInt(idUser)));
                
                
                
                %><h2>Opravdu chcete smazat uživatele : <% out.print(removedUser.getDisplayName()); %>?</h2>
                <form action="removeUser.jsp" method="post">
                    <table>
                        <tr>
                            <td><button type="submit" name="yes" value="<% out.print(removedUser.getId()); %>">Ano</button></td>
                            <td><button type="submit" name="back" value="back">Ne</button></td>
                        </tr>
                    </table>
                </form>
         <%       
            }
        %>
    </body>
</html>
