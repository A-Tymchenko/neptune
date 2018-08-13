<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Flight</title>
</head>
<body>
<h1>Create Flight Page</h1>
<form method="POST" action="${pageContext.request.contextPath}">
    Name<br>
    <input type="text" name="name" value="${flight.getName()}"><p/>
    Carrier<br>
    <input type="text" name="carrier" value="${flight.getCarrier()}"><p/>
    Fare<br>
    <input type="number" name="fare" value="${flight.getFare()}"/><p/>
    Departure date<br>
    <input type="datetime-local" name="departureDate value="${flight.getDepartureDate()}"/><p/>
    Arrival date<br>
    <input type="datetime-local" name="arrivalDate" value="${flight.getArrivalDate()}"/><p/>

    <input type="checkbox" name="mealOn" value="${flight.getArrivalDate()}">Meal on<br><br>

    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
