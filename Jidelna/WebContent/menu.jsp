<%-- 
    Document   : menu
    Created on : 18.5.2014, 21:44:51
    Author     : elopin
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% if (request.getParameter("menu") != null) {
                session.removeAttribute("calendar");
                response.sendRedirect("CalendarServlet");
            }
            if (request.getParameter("credit") != null) {
                response.sendRedirect("userCredit.jsp");
            }
%>
<div id="leftMenu">
    <h2>Menu</h2>
    <form action="menu.jsp" method="post">
            <input type="submit" name="menu" value="Obědy"/>
            <br><input type="submit" name="credit" value="Kredit"/>
    </form>
</div>