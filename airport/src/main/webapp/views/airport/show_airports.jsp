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
      <table border="1" cellpadding="5" >
           <caption><h2>List of airports</h2></caption>
           <tbody id = "airports">
                <tr>
                    <th style="display:none;">Id</th>
                    <th>Name</th>
                    <th>Number</th>
                    <th>Type</th>
                    <th>Address</th>
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
              <table>
                  <tr><td>Name:</td><td><input type = "text" id = "name"></input></td><td id="vname" style="color:red"></td></tr>
                  <tr><td>Number:</td><td><input type = "text" id = "num"></input></td><td id="vnum" style="color:red"></td></tr>
                  <tr><td>Type:</td><td><input type = "text" id = "type"></input></td><td id="vtype" style="color:red"></td></tr>
                  <tr><td>Address:</td><td><input type = "text" id = "address"></input></td><td id="vaddress" style="color:red"></td></tr>
                  <tr><td>Terminals:</td><td><input type = "text" id = "terminals"></input></td><td id="vterminals" style="color:red"></td></tr>
              </table>
              <p><button type="button" onclick='saveAirport()' id="saveButton">Save</button></p>
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
