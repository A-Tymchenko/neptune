<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Ticket</title>
</head>
<body>
<h1>Update Ticket Page</h1>
<form method="POST" action="${pageContext.request.contextPath}">
    Ticket Number<br>
    <input type="text" name="ticketNumber" value="${ticket.ticketNumber}"><p/>
    Passenger Name<br>
    <input type="text" name="passengerName" value="${ticket.passengerName}"><p/>
    Document<br>
    <input type="text" name="document" value="${ticket.document}"/><p/>
    Selling Date<br>
    <input type="datetime-local" name="sellingDate" value="${ticket.sellingDate}"/><p/>
    <br>

    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
