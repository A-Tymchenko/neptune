<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tickets</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of tickets</h2></caption>
        <tr>
            <th>Id</th>
            <th>Ticket Number</th>
            <th>Passenger Name</th>
            <th>Document</th>
            <th>Selling Date</th>
        </tr>
        <c:forEach var="ticket" items="${tickets}">
            <tr>
                <td><c:out value="${ticket.ticketId}"/></td>
                <td><c:out value="${ticket.ticketNumber}"/></td>
                <td><c:out value="${ticket.passengerName}"/></td>
                <td><c:out value="${ticket.document}"/></td>
                <td><c:out value="${ticket.sellingDate}"/></td>
                <td>
                    <form method="post" action="/ticket/delete?id=${ticket.ticketId}" >
                        <input type="submit" align="center" value="delete">
                    </form>
                </td>
                <td><a href="/ticket/update?id=${ticket.ticketId}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/ticket/create">Create</a>
</div>
</body>
</html>
