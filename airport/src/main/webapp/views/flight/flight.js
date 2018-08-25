var modal = document.getElementById('addModal');
var btn = document.getElementById("addFlight");
var span = document.getElementsByClassName("close")[0];
var flights = getFlights();
var actionType = "update";
var updatedFlight = new Object();
btn.onclick = function() {
    modal.style.display = "block";
    actionType = "addFlight";
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
document.getElementById("carrier").onkeyup = function (ev) {
    let field = document.getElementById("carrier").value;
    document.getElementById("vcarrier").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vcarrier").innerHTML = "Carrier shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
    if (field.search(/^[A-Za-z]+$/)) {
        document.getElementById("vcarrier").innerHTML = "Carrier shoud be contains only letters";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
document.getElementById("departureDate").onkeyup = function (ev) {
    let field = document.getElementById("departureDate").value;
    document.getElementById("vdepartureDate").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vdepartureDate").innerHTML = "Departure Date shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
document.getElementById("arrivalDate").onkeyup = function (ev) {
    let field = document.getElementById("arrivalDate").value;
    document.getElementById("varrivalDate").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("varrivalDate").innerHTML = "Arrival Date shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
document.getElementById("fare").onkeyup = function (ev) {
    let field = document.getElementById("fare").value;
    document.getElementById("vfare").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vfare").innerHTML = "Fare shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
    if (field.search(/^[0-9]+$/)) {
        document.getElementById("vfare").innerHTML = "Fare shoud be contains only numbers";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
function getFlights() {
    let flights = new Array();
    let rows = document.getElementsByTagName("table")[0].rows;
    for (let i = 1; i < rows.length; i++) {
        let flight = new Object();
        let cell = rows[i].cells;
        flight.flId = cell[0].innerHTML;
        flight.name = cell[1].innerHTML;
        flight.carrier = cell[2].innerHTML;
        flight.departureDate = cell[3].innerHTML;
        flight.arrivalDate = cell[4].innerHTML;
        flight.fare = cell[5].innerHTML;
        flight.mealOn = cell[6].innerHTML;
        flights.push(flight);
    }
    return flights;
}
function deleteFlight(id){
    document.getElementById(id).innerHTML = "";
    let flight;
    for (let i = 0; i < flights.length; i++) {
        flight = flights[i];
        if (flight.apId == id) {
            flights.splice(i,1);
            break;
        }
    }
    req("/flights", JSON.stringify(flight), "DELETE").then(function (response) {
        console.log(response);
    })
}
function saveFlight() {
    modal.style.display = "none";
    if(actionType == "update"){
        updateFlightOnServer();
    }
    if(actionType == "addFlight"){
        saveNewFlight();
    }
}
function updateFlightOnServer() {
    let row = document.getElementById(updatedFlight.flId);
    let cell = row.cells;
    let flt = new Object();
    flt.flId = updatedFlight.flId;
    flt.name = updatedFlight.name = cell[1].innerHTML = document.getElementById("name").value;
    flt.carrier = updatedFlight.carrier = cell[2].innerHTML = document.getElementById("carrier").value;
    flt.departureDate = updatedFlight.departureDate = cell[3].innerHTML = document.getElementById("departureDate").value;
    flt.arrivalDate = updatedFlight.arrivalDate = cell[4].innerHTML = document.getElementById("arrivalDate").value;
    flt.fare = updatedFlight.fare = cell[5].innerHTML = document.getElementById("fare").value;
    flt.mealOn = updatedFlight.mealOn = cell[6].innerHTML = document.getElementById("mealOn").value;
    req("/flights", JSON.stringify(flt), "PUT").then(function(response){
        console.log(response);
    });
    for (let i = 0; i < flights.length; i++) {
        let flight = flights[i]
        if (flight.flId == updatedFlight.flId) {
            flights[i] = updatedFlight;
        }
    }
}
function saveNewFlight(){
    let flight = new Object();
    flight.name = document.getElementById("name").value;
    flight.carrier = document.getElementById("carrier").value;
    flight.departureDate = document.getElementById("departureDate").value;
    flight.arrivalDate = document.getElementById("arrivalDate").value;
    flight.fare = parseInt(document.getElementById("fare").value);
    flight.mealOn = document.getElementById("mealOn").value;
    req("/flight", JSON.stringify(flight), "POST").then(function(response){
        flight.flId = JSON.parse(response).flId;
        airports.push(flight);
        let row = '<tr id = "' + flight.flId + '">' +
            '<td>' + flight.name + '</td>' +
            '<td>' + flight.carrier + '</td>' +
            '<td>' + flight.departureDate + '</td>' +
            '<td>' + flight.arrivalDate + '</td>' +
            '<td>' + flight.fare + '</td>' +
            '<td>' + flight.mealOn + '</td>' +
            '<td><button type="button" onclick="deleteFlight(' + flight.flId.toString() + ')">Delete</button></td>' +
            '<td><button type="button" onclick="updateFlight(' + flight.flId.toString() + ')">Update</button></td>' +
            '</tr>'
        document.getElementById("flights").innerHTML = document.getElementById("flights").innerHTML + row;
    });

}
function updateFlight(id){
    actionType = "update";
    modal.style.display = "block";
    let row = document.getElementById(id);
    let cell = row.cells;
    updatedFlight.flId = id;
    updatedFlight.name = cell[1].innerHTML;
    updatedFlight.carrier = cell[2].innerHTML;
    updatedFlight.departureDate = cell[3].innerHTML;
    updatedFlight.arrivalDate = cell[4].innerHTML;
    updatedFlight.fare = cell[5].innerHTML;
    updatedFlight.mealOn = cell[6].innerHTML;
    document.getElementById("name").value = updatedFlight.name;
    document.getElementById("carrier").value = updatedFlight.carrier;
    document.getElementById("departureDate").value = updatedFlight.departureDate;
    document.getElementById("arrivalDate").value = updatedFlight.arrivalDate;
    document.getElementById("fare").value = updatedFlight.fare;
    document.getElementById("mealOn").value = updatedFlight.mealOn;
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