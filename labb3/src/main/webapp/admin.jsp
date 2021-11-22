<%-- 
    Document   : admin
    Created on : 12 nov. 2020, 10:54:57
    Author     : erikb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin</title>
    </head>
    <body>
        <h1>Add quiz question:</h1>
        
        <form action="AdminServlet" method="POST">
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add id</label><input type="text" name="id"></input>
        </div>
            
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add question: </label><input type="text" name="question"></input>
        </div>
        
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add option: </label><input type="text" name="option1"></input>
        </div>
        
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add option: </label><input type="text" name="option2"></input>
        </div>
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add option: </label><input type="text" name="option3"></input>
        </div>
        <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add option: </label><input type="text" name="option4"></input>
        </div>
            
         <div style="margin-bottom: 5px; margin-top: 0px;">
            <label>Add answer: </label><input type="text" name="answer"></input>
        </div>
            
        <input type="submit" value="add question">
        </form>
        
    </body>
</html>
