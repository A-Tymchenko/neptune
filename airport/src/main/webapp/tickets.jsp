<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Users List</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of tickets</h2></caption>
        <tbody id = "tickets">
        <tr>
            <th style="display:none;">Id</th>
            <th>Ticket Number</th>
            <th>Passenger Name</th>
            <th>Document</th>
            <th>Selling Date</th>
        </tr>
        <c:forEach var="ticket" items="${tickets}">
            <tr id = "<c:out value="${ticket.ticketId}" />">
                <td><c:out value="${ticket.ticketNumber}" /></td>
                <td><c:out value="${ticket.passengerName}" /></td>
                <td><c:out value="${ticket.document}" /></td>
                <td><c:out value="${ticket.sellingDate}" /></td>
                <td><button type="button" onclick='deleteTicket("<c:out value="${ticket.ticketId}" />")'>Delete</button></td>
                <td><button type="button" onclick='updateTicket("<c:out value="${ticket.ticketId}" />")'>Update</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <caption><button id="addTicket">Add ticket</button></caption>
    <caption><div id="addModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <p>Ticket Number: <input type = "text" id = "ticketNumber"></input></p>
            <p>Passenger Name: <input type = "text" id = "passengerName"></input></p>
            <p>Documente: <input type = "text" id = "document"></input></p>
            <p>Selling Date: <input type="datetime-local" id = "sellingDate"></input></p>
            <p><button type="button" onclick='saveTicket()'>Save</button></p>
        </div>
    </div></caption>
</div>
</body>
<script type="text/javascript">
    <%@include file="ticket/ticket.js"%>
</script>
<style>
    <%@include file="airport/airport.css"%>
</style>
</html>