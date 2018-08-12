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
<script>
    var modal = document.getElementById('addModal');
    var btn = document.getElementById("addAirport");
    var span = document.getElementsByClassName("close")[0];
    var airports = getAirports();
    var actionType = "update";
    var updatedAirport = new Object();
    btn.onclick = function() {
        modal.style.display = "block";
        actionType = "addAirport";
    }
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
    function getAirports() {
            let airports = new Array();
                let rows = document.getElementsByTagName("table")[0].rows;
                for (let i = 1; i < rows.length; i++) {
                    let airport = new Object();
                    let cell = rows[i].cells;
                    airport.id = rows[i].id;
                    airport.name = cell[0].innerHTML;
                    airport.num = cell[1].innerHTML;
                    airport.type = cell[2].innerHTML;
                    airport.address = cell[3].innerHTML;
                    airport.terminalCount = cell[4].innerHTML;
                    airports.push(airport);
                }
            return airports;
        }
    function deleteAirport(id){
        document.getElementById(id).innerHTML = "";
        for (let i = 0; i < airports.length; i++) {
            let airport = airports[i]
            if (airport.id == id) {
                airports.splice(i,1);
            }
        }
    }
    function saveAirport() {
        if(actionType == "update"){
            let row = document.getElementById(updatedAirport.id);
            let cell = row.cells;
            let air = new Object();
            air.apId = updatedAirport.id;
            air.apName = updatedAirport.name = cell[0].innerHTML = document.getElementById("name").value;
            air.apNum = updatedAirport.num = cell[1].innerHTML = document.getElementById("num").value;
            air.apType = updatedAirport.type = cell[2].innerHTML = document.getElementById("type").value;
            air.address = updatedAirport.address = cell[3].innerHTML = document.getElementById("address").value;
            air.terminalCount = updatedAirport.terminalCount = cell[4].innerHTML = document.getElementById("terminals").value;
            req("/airport/update", "apId=" + air.apId + "&apName=" + air.apName + "&apNum=" + air.apNum
                + "&apType=" + air.apType + "&address=" + air.address + "&terminalCount=" + air.terminalCount).then(function(response){
                console.log(response);
            });
            for (let i = 0; i < airports.length; i++) {
                        let airport = airports[i]
                        if (airport.id == updatedAirport.id) {
                            airports[i] = updatedAirport;
                        }
                    }
        }
        if(actionType == "addAirport"){
            saveNewAirport();
        }
    }
    function saveNewAirport(){
        let airport = new Object();
        airport.name = document.getElementById("name").value;
        airport.num = document.getElementById("num").value;
        airport.type = document.getElementById("type").value;
        airport.address = document.getElementById("address").value;
        airport.terminalCount = document.getElementById("terminals").value;
        airport.id = Math.random();
        airports.push(airport);
        let row = '<tr id = "' + airport.id + '">' +
                   '<td>' + airport.name + '</td>' +
                   '<td>' + airport.num + '</td>' +
                   '<td>' + airport.type + '</td>' +
                   '<td>' + airport.address + '</td>' +
                   '<td>' + airport.terminalCount + '</td>' +
                   '<td><button type="button" onclick="deleteAirport(' + airport.id + ')">Delete</button></td>' +
                   '<td><button type="button" onclick="updateAirport(' + airport.id + ')">Update</button></td>' +
                   '</tr>'
        document.getElementById("airports").innerHTML = document.getElementById("airports").innerHTML + row;
    }
    function updateAirport(id){
        actionType = "update";
        modal.style.display = "block";
        let row = document.getElementById(id);
        let cell = row.cells;
        updatedAirport.id = id;
        updatedAirport.name = cell[0].innerHTML;
        updatedAirport.num = cell[1].innerHTML;
        updatedAirport.type = cell[2].innerHTML;
        updatedAirport.address = cell[3].innerHTML;
        updatedAirport.terminalCount = cell[4].innerHTML;
        document.getElementById("name").value = updatedAirport.name;
        document.getElementById("num").value = updatedAirport.num;
        document.getElementById("type").value = updatedAirport.type;
        document.getElementById("address").value = updatedAirport.address;
        document.getElementById("terminals").value = updatedAirport.terminalCount;
    }
    function req(url, body)
    {
        return new Promise(function(resolve, reject)
        {
            let req = new XMLHttpRequest();
            req.open('POST', url, true);
            req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            req.onload = function()
            {
                if (req.status == 200)
                {
                    resolve(req.response);
                }
                else
                {
                    reject(Error(req.statusText));
                }
            };
            req.onerror = function()
            {
                reject(Error("Network Error"));
            };
            req.send(body);
        });
    }
</script>
<style>
body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>
</html>
