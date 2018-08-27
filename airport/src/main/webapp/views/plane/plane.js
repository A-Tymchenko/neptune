var modal = document.getElementById('addModal');
var btn = document.getElementById("addPlane");
var span = document.getElementsByClassName("close")[0];
var planes = getPlanes();
var actionType = "update";
var updatedPlane = new Object();
btn.onclick = function() {
    modal.style.display = "block";
    actionType = "addPlane";
}
span.onclick = function() {
    modal.style.display = "none";
}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
document.getElementById("seatsCount").onkeyup = function (ev) {
    let field = document.getElementById("seatsCount").value;
    document.getElementById("vseatsCount").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vseatsCount").innerHTML = "Seats count shoud be contains numbers";
        document.getElementById("saveButton").disabled = true;
        return;
    }
    if (field.search(/^[0-9]+$/)) {
        document.getElementById("vseatsCount").innerHTML = "Seats count shoud be contains only numbers";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
document.getElementById("model").onkeyup = function (ev) {
    let field = document.getElementById("model").value;
    document.getElementById("vmodel").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vmodel").innerHTML = "Model shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
    if (field.search(/^[A-Za-z]+$/)) {
        document.getElementById("vmodel").innerHTML = "Model shoud be contains only letters";
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
document.getElementById("plateNumber").onkeyup = function (ev) {
    let field = document.getElementById("plateNumber").value;
    document.getElementById("vplateNumber").innerHTML = "";
    document.getElementById("saveButton").disabled = false;
    if (field.length == 0) {
        document.getElementById("vplateNumber").innerHTML = "Plate Number shoud be contains value";
        document.getElementById("saveButton").disabled = true;
        return;
    }
    if (field.search(/^[0-9]+$/)) {
        document.getElementById("vplateNumber").innerHTML = "Plate Number shoud be contains only numbers";
        document.getElementById("saveButton").disabled = true;
        return;
    }
}
function getPlanes() {
    let planes = new Array();
    let rows = document.getElementsByTagName("table")[0].rows;
    for (let i = 1; i < rows.length; i++) {
        let plane = new Object();
        let cell = rows[i].cells;
        plane.planeId = cell[0].innerHTML;
        plane.seatsCount = cell[1].innerHTML;
        plane.model = cell[2].innerHTML;
        plane.type = cell[3].innerHTML;
        plane.plateNumber = cell[4].innerHTML;
        planes.push(plane);
    }
    return planes;
}
function deletePlane(id){
    document.getElementById(id).innerHTML = "";
    let plane;
    for (let i = 0; i < planes.length; i++) {
        plane = planes[i];
        if (plane.planeId == id) {
            planes.splice(i,1);
            break;
        }
    }
    req("/planes", JSON.stringify(plane), "DELETE").then(function (response) {
        console.log(response);
    })
}
function savePlane() {
    modal.style.display = "none";
    if(actionType == "update"){
        updatePlaneOnServer();
    }
    if(actionType == "addPlane"){
        saveNewPlane();
    }
}
function updatePlaneOnServer() {
    let row = document.getElementById(updatedPlane.planeId);
    let cell = row.cells;
    let pln = new Object();
    pln.planeId = updatedPlane.planeId;
    pln.seatsCount = updatedPlane.seatsCount = cell[1].innerHTML = document.getElementById("seatsCount").value;
    pln.model = updatedPlane.model = cell[2].innerHTML = document.getElementById("model").value;
    pln.type = updatedPlane.type = cell[3].innerHTML = document.getElementById("type").value;
    pln.plateNumber = updatedPlane.plateNumber = cell[4].innerHTML = document.getElementById("plateNumber").value;
    req("/planes", JSON.stringify(pln), "PUT").then(function(response){
        console.log(response);
    });
    for (let i = 0; i < planes.length; i++) {
        let plane = planes[i]
        if (plane.planeId == updatedPlane.planeId) {
            planes[i] = updatedPlane;
        }
    }
}
function saveNewPlane(){
    let plane = new Object();
    plane.seatsCount = document.getElementById("seatsCount").value;
    plane.model = document.getElementById("model").value;
    plane.type = document.getElementById("type").value;
    plane.plateNumber = document.getElementById("plateNumber").value;
    req("/planes", JSON.stringify(plane), "POST").then(function(response){
        plane.planeId = JSON.parse(response).planeId;
        planes.push(plane);
        let row = '<tr id = "' + plane.planeId + '">' +
            '<td>' + plane.planeId + '</td>' +
            '<td>' + plane.seatsCount + '</td>' +
            '<td>' + plane.model + '</td>' +
            '<td>' + plane.type + '</td>' +
            '<td>' + plane.plateNumber + '</td>' +
            '<td><button type="button" onclick="deletePlane(' + plane.planeId.toString() + ')">Delete</button></td>' +
            '<td><button type="button" onclick="updatePlane(' + plane.planeId.toString() + ')">Update</button></td>' +
            '</tr>'
        document.getElementById("planes").innerHTML = document.getElementById("planes").innerHTML + row;
    });

}
function updatePlane(id){
    actionType = "update";
    modal.style.display = "block";
    let row = document.getElementById(id);
    let cell = row.cells;
    updatedPlane.planeId = id;
    updatedPlane.seatsCount = cell[1].innerHTML;
    updatedPlane.model = cell[2].innerHTML;
    updatedPlane.type = cell[3].innerHTML;
    updatedPlane.plateNumber = cell[4].innerHTML;
    document.getElementById("seatsCount").value = updatedPlane.seatsCount;
    document.getElementById("model").value = updatedPlane.model;
    document.getElementById("type").value = updatedPlane.type;
    document.getElementById("plateNumber").value = updatedPlane.plateNumber;
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