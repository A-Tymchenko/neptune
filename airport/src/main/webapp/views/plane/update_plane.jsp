<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Plane</title>
</head>
<body>
<h1>Update Plane Page</h1>
<form method="POST" action="${pageContext.request.contextPath}">
    Owner<br>
    <input type="text" name="seatsCount" value="${plane.getSeatsCount()}"><p/>
    Model<br>
    <input type="text" name="model" value="${plane.getModel()}"><p/>
    Type<br>
    <input type="text" name="type" value="${plane.getType()}"><p/>
    Plate number<br>
    <input type="number" name="plateNumber" value="${plane.getPlateNumber()}"><p/>
    <br>
    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
