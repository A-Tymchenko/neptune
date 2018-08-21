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
           <tbody id = "airports">
                <tr>
                    <th style="display:none;">Id</th>
                    <th>Name</th>
                    <th>Number</th>
                    <th>Type</th>
                    <th>Adress</th>
                    <th>Terminal count</th>
                </tr>
           <c:forEach var="airport" items="${airports}">
              <tr id = "<c:out value="${airport.apId}" />">
                 <td><c:out value="${airport.apName}" /></td>
                 <td><c:out value="${airport.apNum}" /></td>
                 <td><c:out value="${airport.apType}" /></td>
                 <td><c:out value="${airport.address}" /></td>
                 <td><c:out value="${airport.terminalCount}" /></td>
                 <td><button type="button" onclick='deleteAirport("<c:out value="${airport.apId}" />")'>Delete</button></td>
                 <td><button type="button" onclick='updateAirport("<c:out value="${airport.apId}" />")'>Update</button></td>
              </tr>
           </c:forEach>
           </tbody>
        </table>
        <caption><button id="addAirport">Add airport</button></caption>
        <caption><div id="addModal" class="modal">
          <div class="modal-content">
            <span class="close">&times;</span>
            <p>Name: <input type = "text" id = "name"></input></p>
            <p>Number: <input type = "text" id = "num"></input></p>
            <p>Type: <input type = "text" id = "type"></input></p>
            <p>Address: <input type = "text" id = "address"></input></p>
            <p>Terminals: <input type = "text" id = "terminals"></input></p>
            <p><button type="button" onclick='saveAirport()'>Save</button></p>
          </div>
        </div></caption>
   </div>
</body>
<script type="text/javascript">
    <%@include file="../airport/airport.js"%>
</script>
<style>
    <%@include file="../airport/airport.css"%>
</style>
</html>
