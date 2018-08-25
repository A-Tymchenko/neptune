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
        <tbody id = "flights">
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
            <tr id = "<c:out value="${flight.getFlId()}" />">
                <td><c:out value="${flight.getFlId()}"/></td>
                <td><c:out value="${flight.getName()}"/></td>
                <td><c:out value="${flight.getCarrier()}"/></td>
                <td><c:out value="${flight.getDepartureDate()}"/></td>
                <td><c:out value="${flight.getArrivalDate()}"/></td>
                <td><c:out value="${flight.getFare()}"/></td>
                <td><c:out value="${flight.getMealOn()}"/></td>
                <td><button type="button" onclick='deleteFlight("<c:out value="${flight.getFlId()}" />")'>Delete</button></td>
                <td><button type="button" onclick='updateFlight("<c:out value="${flight.getFlId()}" />")'>Update</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <caption><button id="addFlight">Add flight</button></caption>
    <caption><div id="addModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <table>
                <tr><td>Name:</td><td><input type = "text" id = "name"></input></td><td id="vname" style="color:red"></td></tr>
                <tr><td>Carrier:</td><td><input type = "text" id = "carrier"></input></td><td id="vcarrier" style="color:red"></td></tr>
                <tr><td>Departure Date:</td><td><input type = "datetime-local" id = "departureDate"></input></td><td id="vdepartureDate" style="color:red"></td></tr>
                <tr><td>Arrival Date:</td><td><input type = "datetime-local" id = "arrivalDate"></input></td><td id="varrivalDate" style="color:red"></td></tr>
                <tr><td>Fare:</td><td><input type = "text" id = "fare"></input></td><td id="vfare" style="color:red"></td></tr>
                <tr><td>Meal On:</td><td><input type = "text" id = "mealOn"></input></td><td id="vmealOn" style="color:red"></td></tr>
            </table>
            <p><button type="button" onclick='saveFlight()' id="saveButton">Save</button></p>
        </div>
    </div></caption>
</div>
</body>
<script type="text/javascript">
    <%@include file="../flight/flight.js"%>
</script>
<style>
    <%@include file="../flight/flight.css"%>
</style>
</html>
