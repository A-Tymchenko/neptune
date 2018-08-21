<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Plane</title>
</head>
<body>
<h1>Create Plane Page</h1>
<form method="POST" action="${pageContext.request.contextPath}">
    Owner<br>
    <input type="text" name="owner"><p/>
    Model<br>
    <input type="text" name="model"><p/>
    Type<br>
    <input type="text" name="type"><p/>
    Plate number<br>
    <input type="number" name="plateNumber"><p/>
    <br>
    <input type="submit" value="Submit" name="submitButton">
</form>
</body>
</html>
