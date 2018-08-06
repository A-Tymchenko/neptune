<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Flights</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of flights</h2></caption>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Carrier</th>
            <th>Duration</th>
            <th>Departure date</th>
            <th>Arrival date</th>
            <th>Fare</th>
            <th>Meal on</th>
        </tr>
        <c:forEach var="flight" items="${flights}">
            <tr>
                <td><c:out value="${flight.getFlId()}"/></td>
                <td><c:out value="${flight.getName()}"/></td>
                <td><c:out value="${flight.getCarrier()}"/></td>
                <td><c:out value="${flight.getDuration()}"/></td>
                <td><c:out value="${flight.getDepartureDate()}"/></td>
                <td><c:out value="${flight.getArrivalDate()}"/></td>
                <td><c:out value="${flight.getFare()}"/></td>
                <td><c:out value="${flight.getMealOn()}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
