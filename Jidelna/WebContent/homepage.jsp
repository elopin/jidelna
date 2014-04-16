<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="jidelna.beans.UserBean" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hlavní strana</title>
    </head>
    <body>

        <%

            if (request.getParameter("menu") != null) {
                session.removeAttribute("calendar");
                response.sendRedirect("CalendarServlet");
            }
            if (request.getParameter("credit") != null) {
                response.sendRedirect("userCredit.jsp");
            }
            if(request.getParameter("edit") != null) {
                
                request.getRequestDispatcher("userForm.jsp").forward(request, response);
            }
            if(request.getParameter("remove") != null) {
                request.getRequestDispatcher("removeUser.jsp").forward(request, response);
            }
        %>
        <form action="" method="post">
            <input type="submit" name="menu" value="Obědy"/>
            <input type="submit" name="credit" value="Kredit"/>
        </form>


        <%
            if (user.isAdmin()) {
        %>


        <br>Seznam strávníků:<br>


        <%
            DataRepository repository = new DataRepositoryImpl();
        %>
        <form action="" method="post"> 
            <table>
                <%
                    for (UserBean u : repository.getUsers()) {
                %>

                <tr>
                    <td><label><% out.print(u.getDisplayName()); %></label></td>
                    <td><button type="submit" name="edit" value="<% out.print(u.getId()); %>">Editovat</button></td>
                    <td><button type="submit" name="remove" value="<% out.print(u.getId()); %>">Odstranit</button></td>
                </tr>

                <%
                    }
                %>
            </table>
        </form>
        <form action="userForm.jsp" method="post">
            <input type="submit" name="newUser" value="Přidat strávníka"/>
        </form>
        <%            }
        %>


    </body>
</html>