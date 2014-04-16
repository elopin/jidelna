<%@page import="jidelna.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="userForm" scope="page" class="jidelna.beans.UserBean"/>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Profil uživatele</title>
    </head>
    <body>
        <h2>Profil uživatele: </h2>

        <%

            if (request.getParameter("back") != null) {
                response.sendRedirect((String)session.getAttribute("lastURI"));
            }

            DataRepository repository = new DataRepositoryImpl();

            String idUser = request.getParameter("edit");
            if (idUser != null) {
                UserBean repo = repository.getUserById(Integer.parseInt(idUser));
                if(repo != null) {
                    userForm.setData(repo);
                }
            }
            
            String idUserForm = request.getParameter("save");
            if (idUserForm != null) {
                userForm.setId(Integer.parseInt(idUserForm));

                boolean emailCheck = false;
                
                if (userForm.getId() == 0) {
                    String email = request.getParameter("email");
                    if (email == null) {
                            %> <label style="color: red">Email musí být vyplněn!</label> <%
                    } else if (repository.getUserByEmail(email) != null) {
                            %> <label style="color: red">Uživatel s uvedeným e-mailem již existuje!</label> <%
                    } else {
                            emailCheck = true;
                    }
                } else {
                    emailCheck = true;
                }
                
            if (emailCheck) {
        %>
        <jsp:setProperty name="userForm" property="email" param="email"/>
        <jsp:setProperty name="userForm" property="name" param="name"/>
        <jsp:setProperty name="userForm" property="surname" param="surname"/>
        <jsp:setProperty name="userForm" property="admin" param="admin"/>
        <jsp:setProperty name="userForm" property="password" param="password"/>
        <%
                
                
            if (!request.getParameter("password").equals("")) {
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");

                if (!password.equals(confirmPassword)) {
                    %> <label style="color: red">Neplatné potvrzení hesla!</label> <%
                } else {
                    repository.addUser(userForm);
                    userForm.setData(repository.getUserByEmail(userForm.getEmail()));
                }

            } else {
                if (userForm.getId() == 0) {
                    %> <label style="color: red">Vyplňte a potvrďte heslo!</label> <%
                } else {
                    repository.addUser(userForm);
                    userForm.setData(repository.getUserByEmail(userForm.getEmail()));
                }
            }
        }
            }
        %>

        <form action="userForm.jsp" method="post">
            <table>
                <tr>
                    <%
                        String readOnly = null;
                        if (userForm.getId() > 0) {
                            readOnly = "readOnly";
                        }
                    %>
                    <td><label>E-mail:</label></td><td><input type="text" name="email" value="<% out.print(userForm.getEmail()); %>" <% out.print(readOnly); %>/></td>
                </tr>
                <tr>
                    <td><label>Jméno:</label></td><td><input type="text" name="name" value="<% out.print(userForm.getName()); %>"/></td>
                </tr>
                <tr>
                    <td><label>Příjmení</label></td><td><input type="text" name="surname" value="<% out.print(userForm.getSurname()); %>"/></td>
                </tr>
                <tr>
                    <td><label>Heslo:</label></td><td><input type="password" name="password" value=""/></td>
                </tr>
                <tr>
                    <td><label>Potvrdit heslo:</label></td><td><input type="password" name="confirmPassword" value=""/></td>
                </tr>

                <% 
                        String checked = null;   
                        if (userForm.isAdmin()) {
                            checked = "checked";
                        }
    
                        if (user.isAdmin()) { %>
                <tr><td>Administrátor <input type="checkbox" name="admin" <% out.print(checked); %>/></td><td></td></tr>
                        <% } %>
                <tr>
                    <td><button type="submit" name="save" value="<% out.print(userForm.getId());%>">Uložit</button></td><td><input type="submit" name="back" value="Zpět"></td>
                </tr>
            </table>
        </form>

    </body>
</html>