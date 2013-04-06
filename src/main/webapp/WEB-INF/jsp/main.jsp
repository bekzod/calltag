<%-- 
    Document   : main
    Created on : Apr 1, 2013, 10:27:30 PM
    Author     : bek
--%>
<%@page import="com.calltag.model.User"%>
<% User user = (User)request.getAttribute("user"); %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p><%= user.getName()%></p>
        <p><%= user.getTwitterAccountName()%></p>
        <p><%= user.getProfilePictureUrl()%></p>
        <p><%= user.getIsCallEnabled()%></p>
        <p><%= user.getIsTextEnabled()%></p>
    </body>
</html>
