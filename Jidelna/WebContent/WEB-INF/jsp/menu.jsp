<%-- 
    Document   : menu
    Created on : 18.5.2014, 21:44:51
    Author     : Lukáš Janáček

    Komponenta přestavující levé menu.
--%>

<%@page import="jidelna.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="leftMenu">
    <h2>Menu</h2>
    <form action="Jidelna" method="post">
	<button class="appButton" type="submit" name="menu" >Obědy</button>
        <br><button class="appButton" type="submit" name="credit" >Kredit</button>
	<br><button class="appButton" type="submit" name="editProfil" >Profil</button>
	<br><button class="appButton" type="submit" name="home" >Domů</button>
    </form>
</div>