<%-- 
    Document   : header
    Created on : 13.4.2014, 15:50:15
    Author     : Lukáš janáček
    
    Hlavička stránek. Obsahuje datum a informaci o přihlášeném uživateli.
    Umožňuje uživateli odhlášení.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
        <title></title>
    </head>
    <body>
        <%
	    if(request.getParameter("logout") != null) {
                user.setLoggedIn(false);
                response.sendRedirect("index.jsp");
	    }		
	%>
        <div id="header">
            <div id="date">
                <label class="infoLabel">Dnešní datum: <% out.print((String)session.getAttribute("currentDay")); %></label>
            </div>
            
            <div id="logout">
                <div class="infoLabel">Přihlášen uživatel: <jsp:getProperty name="user" property="displayName"/></div>
		<form action="header.jsp" method="get">   
		    <button class="appButton" type="submit" name="logout" >Odhlásit</button>
                </form>
            </div>
        </div>
    

