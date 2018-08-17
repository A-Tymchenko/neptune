<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Flight</title>
</head>
<body>
<h1>Create Flight Page</h1>
<form method="POST" action="${pageContext.request.contextPath}">
    Ticket Number<br>
    <input type="text" name="ticketNumber"><p/>
    Passenger Name<br>
    <input type="text" name="passengerName"><p/>
    Document<br>
    <input type="text" name="document"><p/>
    Selling Date<br>
    <input type="datetime-local" name="sellingDate"><p/>
    <br>
    
    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
