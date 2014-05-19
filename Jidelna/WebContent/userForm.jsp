<%@page import="jidelna.beans.UserBean"%>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="formBean" scope="page" class="jidelna.beans.UserBean"/>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp"/>

<div id="container">
<jsp:include page="menu.jsp" />
<div id="content">	
        <%
            DataRepository repository = new DataRepositoryImpl();

            Integer idUser = (Integer)session.getAttribute("edit");
            if (idUser != null) {
                UserBean repo = repository.getUserById(idUser);
                if(repo != null) {
                    formBean.setData(repo);
                }
		session.removeAttribute("edit");
            }
	    
            String idUserForm = request.getParameter("save");
            if (idUserForm != null) {
                formBean.setId(Integer.parseInt(idUserForm));

                boolean emailCheck = false;
                
                if (formBean.getId() == 0) {
                    String email = request.getParameter("email");
                    if (email.equals("")) {
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
        <jsp:setProperty name="formBean" property="email" param="email"/>
        <jsp:setProperty name="formBean" property="name" param="name"/>
        <jsp:setProperty name="formBean" property="surname" param="surname"/>
        <jsp:setProperty name="formBean" property="admin" param="admin"/>
        <jsp:setProperty name="formBean" property="password" param="password"/>
        <%
                
                
            if (!request.getParameter("password").equals("")) {
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");

		if(password.length() < 8) {
		    %> <label style="color: red">Heslo musí mít minimálně 8 znaků!</label> <%
		} else if (!password.equals(confirmPassword)) {
                    %> <label style="color: red">Neplatné potvrzení hesla!</label> <%
                } else {
                    repository.addUser(formBean);
                    formBean.setData(repository.getUserByEmail(formBean.getEmail()));
                }

            } else {
                if (formBean.getId() == 0) {
                    %> <label style="color: red">Vyplňte a potvrďte heslo!</label> <%
                } else {
                    repository.addUser(formBean);
                    formBean.setData(repository.getUserByEmail(formBean.getEmail()));
                }
            }
        }
        }
        %>
	<div id="userProfil">
	<h2>Profil uživatele: </h2>
        <form  id="userForm" action="userForm.jsp" method="post">
            <table>
                <tr>
                    <%
                        String email = formBean.getEmail();
                        if(email == null) {
                            email = "";
                        }
                        String name = formBean.getName();
                        if(name == null) {
                            name = "";
                        }
                        String surname = formBean.getSurname();
                        if(surname == null) {
                            surname = "";
                        }
            
                        String readOnly = null;
                        if (formBean.getId() > 0) {
                            readOnly = "readOnly";
                        }
                    %>
                    <td><label>E-mail:</label></td><td><input type="text" name="email" value="<% out.print(email); %>" <% out.print(readOnly); %>/></td>
                </tr>
                <tr>
                    <td><label>Jméno:</label></td><td><input type="text" name="name" value="<% out.print(name); %>"/></td>
                </tr>
                <tr>
                    <td><label>Příjmení</label></td><td><input type="text" name="surname" value="<% out.print(surname); %>"/></td>
                </tr>
                <tr>
                    <td><label>Heslo:</label></td><td><input type="password" name="password" value=""/></td>
                </tr>
                <tr>
                    <td><label>Potvrdit heslo:</label></td><td><input type="password" name="confirmPassword" value=""/></td>
                </tr>

                <% 
                        String checked = null;   
                        if (formBean.isAdmin()) {
                            checked = "checked";
                        }
    
                        if (user.isAdmin()) { %>
                <tr><td>Administrátor <input type="checkbox" name="admin" <% out.print(checked); %>/></td><td></td></tr>
                        <% } %>
                <tr>
                    <td><button class="appButton" type="submit" name="save" value="<% out.print(formBean.getId());%>">Uložit</button></td>
                </tr>
            </table>
        </form>
	</div></div></div>
<jsp:include page="footer.jsp"/>