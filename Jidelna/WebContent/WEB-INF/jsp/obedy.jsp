<%-- 
    Document   : obedy
    Created on : Mar 15, 2014, 9:27:59 PM
    Author     : Lukáš Janáček

    Komponenta představující formuláře pro výběr oběda z menu a vytvoření
    nového menu.
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

    <%
        Calendar calendar = (Calendar) session.getAttribute("calendar");
        Locale locale = new Locale("cs", "CZ");
	
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
   
        DataRepository repository = new DataRepositoryImpl();
 
        UserBean userBean = repository.getUserById(user.getId());
        if (userBean != null) {
            user.setData(userBean);
        }
            
        if (repository.getDailyMenu(day, month, year) != null) {
            menu.setData(repository.getDailyMenu(day, month, year));
     
            session.setAttribute("dailyMenu", menu);
                                   
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
        <div id="obedy">
	    <h3>Váš aktuální kredit je: <%out.print(user.getCredit());%> Kč</h3>
	    <label>Menu dne: <% out.print(day+" "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)+" " + calendar.get(Calendar.YEAR)); %></label>
            <form id="obed" name="menuSelection" action="" method="post">
                <ul class="dottless">
                    <li><input type="radio" name="selection" value="1" <% out.print(menu1);%>/>
			<label><jsp:getProperty name="menu" property="menu1"/> <jsp:getProperty name="menu" property="price1"/> Kč</label>
                    <li><input type="radio" name="selection" value="2" <% out.print(menu2);%>/>
			<label><jsp:getProperty name="menu" property="menu2"/> <jsp:getProperty name="menu" property="price2"/> Kč</label>
                    <li><input type="radio" name="selection" value="0" <% out.print(noMenu);%>/>
			<label>Bez výběru.</label>
                    <li><input type="submit" name="confirm" value="Potvrdit výběr"/>
                <ul>
            </form>
	</div>
        <%  }

            if (user.isAdmin()) {

                if (menu == null) {
                    menu = new DayMenuBean();  
                }
                                
                menu.setDay(day);
                menu.setMonth(month);
                menu.setYear(year);
                session.setAttribute("newDayMenu", menu);
                                
                String m1 = "Nové menu 1";
                String m2 = "Nové menu 2";

                if (menu.getMenu1() != null) {
                    m1 = menu.getMenu1();
                }
                if (menu.getMenu2() != null) {
                    m2 = menu.getMenu2();
                }

        %>  <div id="newMenu">  
                <h2>Zadejte nové menu nebo proveďte změnu stávajícího:</h2>
		<label>Menu na den: <% out.print(day+" "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)+" " + calendar.get(Calendar.YEAR)); %></label>
                <form id="newMenuForm" action="" method="post">
                    <ul class="dottless">
                        <li><input type="text" name="menu1" value="<% out.print(m1); %>"/>
                            <input class="number" type="text" name="price1" value="<jsp:getProperty name="menu" property="price1"/>"/>
		            <label>Kč</label>
                        <li><input type="text" name="menu2" value="<% out.print(m2); %>"/>
                            <input class="number" type="text" name="price2" value="<jsp:getProperty name="menu" property="price2"/>"/>
			    <label>Kč</label>
                        <li><input type="submit" name="saveDay" value="Uložit menu"/>
                    <ul>
                </form>
	    </div>		
        <% } %> 
	

