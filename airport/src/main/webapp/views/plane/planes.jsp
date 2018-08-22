<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Planes</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of flights</h2></caption>
            <th>Id</th>
            <th>Owner</th>
            <th>Model</th>
            <th>Type</th>
            <th>Plate number</th>
            <th>Delete</th>
            <th>Update</th>
        </tr>
        <c:forEach var="plane" items="${planes}">
            <tr>
                <td><c:out value="${plane.getPlaneId()}"/></td>
                <td><c:out value="${plane.getOwner()}"/></td>
                <td><c:out value="${plane.getModel()}"/></td>
                <td><c:out value="${plane.getType()}"/></td>
                <td><c:out value="${plane.getPlateNumber()}"/></td>
                <td>
                    <form method="post" action="/plane/delete?id=${plane.getPlaneId()}" >
                        <input type="submit" align="center" value="delete">
                    </form>
                </td>
                <td><a href="/plane/update?id=${plane.getPlaneId()}">Update</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="/plane/create">Create</a>
</div>
</body>
</html>
