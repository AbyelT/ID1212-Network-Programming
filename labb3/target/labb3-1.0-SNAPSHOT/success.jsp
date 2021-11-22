<jsp:useBean class="bean.Quiz" id="qz" scope="session"></jsp:useBean>
<!DOCTYPE html>

<html>
    <head><title>success.jsp</title></head>
    <body>
       
    <p>You completed the quiz! You got <%=qz.getPoints()%> point(s)</p>
    <form action="/labb3/QuizServlet" method="POST">
        <input type="submit" name="newGame" value="newGame">
    </form>
   
    </body>
    
</html>
