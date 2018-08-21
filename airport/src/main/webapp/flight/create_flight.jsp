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
    <input type="text" name="name"><p/>
    Carrier<br>
    <input type="text" name="carrier"><p/>
    Fare<br>
    <input type="number" name="fare"/><p/>
    Departure date<br>
    <input type="datetime-local" name="departureDate"/><p/>
    Arrival date<br>
    <input type="datetime-local" name="arrivalDate"/><p/>

    <input type="checkbox" name="mealOn" value="true">Meal on<br><br>
    
    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
