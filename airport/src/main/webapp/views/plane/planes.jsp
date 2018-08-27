<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Planes</title>
</head>
<body>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of planes</h2></caption>
        <tbody id = "planes">
        <tr>
            <th>Id</th>
            <th>Seats count</th>
            <th>Model</th>
            <th>Type</th>
            <th>Plate number</th>
            <th>Delete</th>
            <th>Update</th>
        </tr>
        <c:forEach var="plane" items="${planes}">
            <tr id = "<c:out value="${plane.getPlaneId()}"/>">
                <td><c:out value="${plane.getPlaneId()}"/></td>
                <td><c:out value="${plane.getSeatsCount()}"/></td>
                <td><c:out value="${plane.getModel()}"/></td>
                <td><c:out value="${plane.getType()}"/></td>
                <td><c:out value="${plane.getPlateNumber()}"/></td>
                <td><button type="button" onclick='deletePlane("<c:out value="${plane.getPlaneId()}" />")'>Delete</button></td>
                <td><button type="button" onclick='updatePlane("<c:out value="${plane.getPlaneId()}" />")'>Update</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <caption><button id="addPlane">Add plane</button></caption>
    <caption><div id="addModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <table>
                <tr><td>Seats Count:</td><td><input type = "text" id = "seatsCount"></input></td><td id="vseatsCount" style="color:red"></td></tr>
                <tr><td>Model:</td><td><input type = "text" id = "model"></input></td><td id="vmodel" style="color:red"></td></tr>
                <tr><td>Type:</td><td><input type = "text" id = "type"></input></td><td id="vtype" style="color:red"></td></tr>
                <tr><td>Plate Number:</td><td><input type = "text" id = "plateNumber"></input></td><td id="vplateNumber" style="color:red"></td></tr>
            </table>
            <p><button type="button" onclick='savePlane()' id="saveButton">Save</button></p>
        </div>
    </div></caption>
</div>
</body>
<script type="text/javascript">
    <%@include file="../plane/plane.js"%>
</script>
<style>
    <%@include file="../plane/plane.css"%>
</style>
</html>
