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
        req("/airport/delete", "apId="+id).then(function (response) {
            console.log("airport: " + id + " deleted successfuly");
        })
    }
    function saveAirport() {
        modal.style.display = "none";
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
                console.log("airport: " + air.apId + " updated successfuly");
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
        req("/airport/create", "apName=" + airport.name + "&apNum=" + airport.num
            + "&apType=" + airport.type + "&address=" + airport.address + "&terminalCount=" + airport.terminalCount).then(function(response){
            airport.id = response.slice(response.indexOf("id") + 3, response.indexOf(" created"))
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
        });

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