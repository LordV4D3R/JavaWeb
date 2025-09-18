<%-- 
    Document   : login
    Created on : Apr 26, 2025, 8:58:20 AM
    Author     : Computing Fundamental - HCM Campus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h2>Login page</h2>
        <form action="MainController" method="post">
            
            <p>
                <label>Username</label> 
                <input type="text" name="userID"/>
            </p>
            
            <p>
                <label>Password</label>
                <input type="password" name="password"/>
            </p>
            
            <p>
                <input type="submit" name="action" value="Login"/>
            </p>
        </form>
    </body>
</html>
