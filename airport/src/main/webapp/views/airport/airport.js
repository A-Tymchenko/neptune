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
    document.getElementById("name").onkeyup = function (ev) {
        let field = document.getElementById("name").value;
        document.getElementById("vname").innerHTML = "";
        document.getElementById("saveButton").disabled = false;
        if (field.length == 0) {
            document.getElementById("vname").innerHTML = "Name shoud be contains value";
            document.getElementById("saveButton").disabled = true;
            return;
        }
        if (field.search(/^[A-Za-z]+$/)) {
            document.getElementById("vname").innerHTML = "Name shoud be contains only letters";
            document.getElementById("saveButton").disabled = true;
            return;
        }
    }
    document.getElementById("num").onkeyup = function (ev) {
        let field = document.getElementById("num").value;
        document.getElementById("vnum").innerHTML = "";
        document.getElementById("saveButton").disabled = false;
        if (field.length == 0) {
            document.getElementById("vnum").innerHTML = "Num shoud be contains value";
            document.getElementById("saveButton").disabled = true;
            return;
        }
        if (field.search(/^[0-9]+$/)) {
            document.getElementById("vnum").innerHTML = "Num shoud be contains only numbers";
            document.getElementById("saveButton").disabled = true;
            return;
        }
    }
    document.getElementById("type").onkeyup = function (ev) {
        let field = document.getElementById("type").value;
        document.getElementById("vtype").innerHTML = "";
        document.getElementById("saveButton").disabled = false;
        if (field.length == 0) {
            document.getElementById("vtype").innerHTML = "Type shoud be contains value";
            document.getElementById("saveButton").disabled = true;
            return;
        }
        if (field.search(/^[A-Za-z]+$/)) {
            document.getElementById("vtype").innerHTML = "Type shoud be contains only letter";
            document.getElementById("saveButton").disabled = true;
            return;
        }
    }
    document.getElementById("address").onkeyup = function (ev) {
        let field = document.getElementById("address").value;
        document.getElementById("vaddress").innerHTML = "";
        document.getElementById("saveButton").disabled = false;
        if (field.length == 0) {
            document.getElementById("vaddress").innerHTML = "Address shoud be contains value";
            document.getElementById("saveButton").disabled = true;
            return;
        }
        if (field.search(/^[A-Za-z]+$/)) {
            document.getElementById("vaddress").innerHTML = "Address shoud be contains only letter";
            document.getElementById("saveButton").disabled = true;
            return;
        }
    }
    document.getElementById("terminals").onkeyup = function (ev) {
        let field = document.getElementById("terminals").value;
        document.getElementById("vterminals").innerHTML = "";
        document.getElementById("saveButton").disabled = false;
        if (field.length == 0) {
            document.getElementById("vterminals").innerHTML = "Terminals shoud be contains value";
            document.getElementById("saveButton").disabled = true;
            return;
        }
        if (field.search(/^[A-Za-z]+$/)) {
            document.getElementById("vterminals").innerHTML = "Terminals shoud be contains only letter";
            document.getElementById("saveButton").disabled = true;
            return;
        }
    }
    function getAirports() {
            let airports = new Array();
                let rows = document.getElementsByTagName("table")[0].rows;
                for (let i = 1; i < rows.length; i++) {
                    let airport = new Object();
                    let cell = rows[i].cells;
                    airport.apId = rows[i].id;
                    airport.apName = cell[0].innerHTML;
                    airport.apNum = cell[1].innerHTML;
                    airport.apType = cell[2].innerHTML;
                    airport.address = cell[3].innerHTML;
                    airport.terminalCount = cell[4].innerHTML;
                    airports.push(airport);
                }
            return airports;
        }
    function deleteAirport(id){
        document.getElementById(id).innerHTML = "";
        let airport;
        for (let i = 0; i < airports.length; i++) {
            airport = airports[i];
            if (airport.apId == id) {
                airports.splice(i,1);
                break;
            }
        }
        req("/airports", JSON.stringify(airport), "DELETE").then(function (response) {
            console.log(response);
        })
    }
    function saveAirport() {
        modal.style.display = "none";
        if(actionType == "update"){
            updateAirportOnServer();
        }
        if(actionType == "addAirport"){
            saveNewAirport();
        }
    }
    function updateAirportOnServer() {
        let row = document.getElementById(updatedAirport.apId);
        let cell = row.cells;
        let air = new Object();
        air.apId = updatedAirport.apId;
        air.apName = updatedAirport.apName = cell[0].innerHTML = document.getElementById("name").value;
        air.apNum = updatedAirport.apNum = cell[1].innerHTML = document.getElementById("num").value;
        air.apType = updatedAirport.apType = cell[2].innerHTML = document.getElementById("type").value;
        air.address = updatedAirport.address = cell[3].innerHTML = document.getElementById("address").value;
        air.terminalCount = updatedAirport.terminalCount = cell[4].innerHTML = document.getElementById("terminals").value;
        req("/airports", JSON.stringify(air), "PUT").then(function(response){
            console.log(response);
            document.getElementById("name").value = "";
            document.getElementById("num").value = "";
            document.getElementById("type").value = "";
            document.getElementById("address").value = "";
            document.getElementById("terminals").value = "";
        });
        for (let i = 0; i < airports.length; i++) {
            let airport = airports[i]
            if (airport.apId == updatedAirport.apId) {
                airports[i] = updatedAirport;
            }
        }
    }
    function saveNewAirport(){
        let airport = new Object();
        airport.apName = document.getElementById("name").value;
        airport.apNum = parseInt(document.getElementById("num").value);
        airport.apType = document.getElementById("type").value;
        airport.address = document.getElementById("address").value;
        airport.terminalCount = parseInt(document.getElementById("terminals").value);
        req("/airports", JSON.stringify(airport), "POST").then(function(response){
            airport.apId = JSON.parse(response).apId;
                    airports.push(airport);
                    let row = '<tr id = "' + airport.apId + '">' +
                               '<td>' + airport.apName + '</td>' +
                               '<td>' + airport.apNum + '</td>' +
                               '<td>' + airport.apType + '</td>' +
                               '<td>' + airport.address + '</td>' +
                               '<td>' + airport.terminalCount + '</td>' +
                               '<td><button type="button" onclick="deleteAirport(' + airport.apId.toString() + ')">Delete</button></td>' +
                               '<td><button type="button" onclick="updateAirport(' + airport.apId.toString() + ')">Update</button></td>' +
                               '</tr>'
                    document.getElementById("airports").innerHTML = document.getElementById("airports").innerHTML + row;
                    document.getElementById("name").value = "";
                    document.getElementById("num").value = "";
                    document.getElementById("type").value = "";
                    document.getElementById("address").value = "";
                    document.getElementById("terminals").value = "";
        });

    }
    function updateAirport(id){
        actionType = "update";
        modal.style.display = "block";
        let row = document.getElementById(id);
        let cell = row.cells;
        updatedAirport.apId = id;
        updatedAirport.apName = cell[0].innerHTML;
        updatedAirport.apNum = cell[1].innerHTML;
        updatedAirport.apType = cell[2].innerHTML;
        updatedAirport.address = cell[3].innerHTML;
        updatedAirport.terminalCount = cell[4].innerHTML;
        document.getElementById("name").value = updatedAirport.apName;
        document.getElementById("num").value = updatedAirport.apNum;
        document.getElementById("type").value = updatedAirport.apType;
        document.getElementById("address").value = updatedAirport.address;
        document.getElementById("terminals").value = updatedAirport.terminalCount;
    }
    function req(url, body, method)
    {
        return new Promise(function(resolve, reject)
        {
            let req = new XMLHttpRequest();
            req.open(method, url, true);
            req.setRequestHeader("Content-type", "application/json");
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