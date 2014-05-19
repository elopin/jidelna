<%-- 
    Document   : menu
    Created on : 18.5.2014, 21:44:51
    Author     : elopin
--%>

<%@page import="jidelna.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
    
    if (request.getParameter("menu") != null) {
        session.removeAttribute("calendar");
        response.sendRedirect("CalendarServlet");
    }
    if (request.getParameter("credit") != null) {
        response.sendRedirect("userCredit.jsp");
    }
    
    if (request.getParameter("edit") != null) {
	session.setAttribute("edit", ((UserBean)session.getAttribute("user")).getId());
	response.sendRedirect("userForm.jsp");
    }
    
    if(request.getParameter("home") != null) {
	response.sendRedirect("homepage.jsp");
    }
	    
%>
<div id="leftMenu">
    <h2>Menu</h2>
    <form action="menu.jsp" method="post">
	<button class="appButton" type="submit" name="menu" >Obědy</button>
        <br><button class="appButton" type="submit" name="credit" >Kredit</button>
	<br><button class="appButton" type="submit" name="edit" >Profil</button>
	<br><button class="appButton" type="submit" name="home" >Domů</button>
    </form>
</div>