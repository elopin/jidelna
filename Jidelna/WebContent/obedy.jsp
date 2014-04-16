<%-- 
    Document   : obedy
    Created on : Mar 15, 2014, 9:27:59 PM
    Author     : elopin
--%>

<%@page import="jidelna.beans.UserBean"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="jidelna.beans.DayMenuBean"%>
<%@page import="jidelna.connection.DataRepositoryImpl"%>
<%@page import="jidelna.connection.DataRepository"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:useBean id="menu" scope="page" class="jidelna.beans.DayMenuBean"/>
<jsp:useBean id="newMenu" scope="page" class="jidelna.beans.DayMenuBean"/>
<jsp:useBean id="userMenu" scope="page" class="jidelna.beans.UserMenuBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <%
            Calendar calendar = (Calendar) session.getAttribute("calendar");

            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            
            
            DataRepository repository = new DataRepositoryImpl();

            if (request.getParameter("saveDay") != null) {
        %> 
        <jsp:setProperty name="newMenu" property="*"/>
        <%
                newMenu.setDay(day);
                newMenu.setMonth(month);
                newMenu.setYear(year);

                repository.addMenuDay(newMenu);
            }
            
            if (repository.getDailyMenu(day, month, year) != null) {
                menu.setData(repository.getDailyMenu(day, month, year));
                
                if(request.getParameter("confirm") != null) {
                    int selection = Integer.parseInt(request.getParameter("selection"));
                    
                    UserBean repoUser = repository.getUserById(user.getId());
                    if(repoUser != null) {
                        user = repoUser;
                        userMenu.setUser(user);
                        userMenu.setDayMenu(menu);
                        userMenu.setSelection(selection);
                        repository.addUserMenu(userMenu);
                    }
                }
                
                String menu1 = null;
                String menu2 = null;
                String noMenu = null;
                
                if(repository.getUserMenu(user, menu) != null) {
                    userMenu.setData(repository.getUserMenu(user, menu));
                    if(userMenu.getSelection() == 1) {
                        menu1 = "checked";
                    } else if(userMenu.getSelection() == 2) {
                        menu2 = "checked";
                    } else {
                        noMenu = "checked";
                    }
                    
                } else {
                    noMenu = "checked";
                }
                


        %>
        <label for="menuSelection">Váš aktuální kredit je: <%out.print(user.getCredit());%> Kč</label>
        <form name="menuSelection" action="" method="post">
            <ul class="dottless">
                <li><input type="radio" name="selection" value="1" <% out.print(menu1);%>/><jsp:getProperty name="menu" property="menu1"/> <jsp:getProperty name="menu" property="price1"/>
                <li><input type="radio" name="selection" value="2" <% out.print(menu2);%>/><jsp:getProperty name="menu" property="menu2"/> <jsp:getProperty name="menu" property="price2"/>
                <li><input type="radio" name="selection" value="0" <% out.print(noMenu);%>/>Bez výběru.
                <li><input type="submit" name="confirm" value="Potvrdit výběr"/>
                    <ul>
                        </form>
                        <%            }

                            if (user.isAdmin()) {

                                if (menu == null) {
                                    menu = new DayMenuBean();
                                    menu.setDay(day);
                                    menu.setMonth(month);
                                    menu.setYear(year);
                                }

                                String m1 = "Nové menu 1";
                                String m2 = "Nové menu 2";

                                if (menu.getMenu1() != null) {
                                    m1 = menu.getMenu1();
                                }
                                if (menu.getMenu2() != null) {
                                    m2 = menu.getMenu2();
                                }


                        %>
                        <h2>Zadejte nové menu nebo proveďte změnu stávajícího:</h2>
                        <form action="" method="post">
                            <ul class="dottless">
                                <li><input type="text" name="menu1" value="<% out.print(m1); %>"/>
                                    <input type="text" name="price1" value="<jsp:getProperty name="menu" property="price1"/>"/>
                                <li><input type="text" name="menu2" value="<% out.print(m2); %>"/>
                                    <input type="text" name="price2" value="<jsp:getProperty name="menu" property="price2"/>"/>
                                <li><input type="submit" name="saveDay" value="Uložit menu"/>
                                    <ul>
                                        </form>
                                        <%
                                            }
                                        %>

                                        </body>
                                        </html>
