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
            <th>Departure date</th>
            <th>Arrival date</th>
            <th>Fare</th>
            <th>Meal on</th>
            <th>Delete</th>
            <th>Update</th>
        </tr>
        <c:forEach var="flight" items="${flights}">
            <tr>
                <td><c:out value="${flight.getFlId()}"/></td>
                <td><c:out value="${flight.getName()}"/></td>
                <td><c:out value="${flight.getCarrier()}"/></td>
                <td><c:out value="${flight.getDepartureDate()}"/></td>
                <td><c:out value="${flight.getArrivalDate()}"/></td>
                <td><c:out value="${flight.getFare()}"/></td>
                <td><c:out value="${flight.getMealOn()}"/></td>
                <td>
                    <form method="post" action="/flight/delete?id=${flight.getFlId()}" >
                        <input type="submit" align="center" value="delete">
                    </form>
                </td>
                <td><a href="/flight/update?id=${flight.getFlId()}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/flight/create">Create</a>
</div>
</body>
</html>
