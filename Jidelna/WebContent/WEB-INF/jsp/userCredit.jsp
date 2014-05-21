<%-- 
    JSP stránka pro doplnění kreditu.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jidelna.beans.UserBean" %>
<%@ page import="jidelna.connection.DataRepository" %>
<%@ page import="jidelna.connection.DataRepositoryImpl" %>
<jsp:useBean id="user" scope="session" class="jidelna.beans.UserBean"/>
<jsp:useBean id="pageUser" scope="page" class="jidelna.beans.UserBean"/>
<jsp:include page="header.jsp" />
<div id="container">
<jsp:include page="menu.jsp"/>
<div id="content">
    <%
    DataRepository repository = new DataRepositoryImpl();
    for(UserBean userRepo : repository.getUsers()) {
        if(userRepo.getId() == user.getId()) {
            pageUser = userRepo;
        }
    }
                      
    if(request.getParameter("send") != null) {
        UserBean repo = repository.updateUserCredit(pageUser, Double.parseDouble(request.getParameter("credit")));
        if(repo != null) {
            pageUser = repo;
        }
    } 
    %>
    <div id="credit">
        <h1>Doplnění kreditu:</h1>
        <h3>Aktuální stav: <% out.print(pageUser.getCredit()); %> Kč</h3>
        <form id="creditForm" action="Jidelna" method="post">
            <label>Zadejte navýšení kreditu(záporná hodnota kredit sníží):</label>
            <br><input type="text" name="credit"/>
            <br><input type="submit" name="send" value="Potvrdit"/>        
        </form>
    </div>
</div></div> 
<jsp:include page="footer.jsp"/>
