<%-- 
    Document   : main
    Created on : Apr 1, 2013, 10:27:30 PM
    Author     : bek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <p><%= ((String) request.getAttribute("name"))%></p>
    </body>
</html>
