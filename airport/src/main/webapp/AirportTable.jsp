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
        	<caption><h2>List of airports</h2></caption>
                <tr>
                    <th>Name</th>
                    <th>Number</th>
                    <th>Type</th>
                    <th>Adress</th>
                    <th>Terminal count</th>
                </tr>
        	<c:forEach var="airport" items="${airports}">
        		<tr>
        			<td><c:out value="${airport.apname}" /></td>
        			<td><c:out value="${airport.apnum}" /></td>
        			<td><c:out value="${airport.aptype}" /></td>
        			<td><c:out value="${airport.adress}" /></td>
        			<td><c:out value="${airport.terminalcount}" /></td>
        		</tr>
        	</c:forEach>
        </table>
	</div>
</body>
</html>