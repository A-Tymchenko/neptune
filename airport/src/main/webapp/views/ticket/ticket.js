var modal = document.getElementById('addModal');
var btn = document.getElementById("addTicket");
var span = document.getElementsByClassName("close")[0];
var tickets = getTickets();
var actionType = "update";
var updatedTicket = new Object();
btn.onclick = function() {
    modal.style.display = "block";
    actionType = "addTicket";
}
span.onclick = function() {
    modal.style.display = "none";
}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
function getTickets() {
    let tickets = new Array();
    let rows = document.getElementsByTagName("table")[0].rows;
    for (let i = 1; i < rows.length; i++) {
        let ticket = new Object();
        let cell = rows[i].cells;
        ticket.ticketId = rows[i].id;
        ticket.ticketNumber = cell[0].innerHTML;
        ticket.passengerName = cell[1].innerHTML;
        ticket.document = cell[2].innerHTML;
        ticket.sellingDate = cell[3].innerHTML;
        tickets.push(ticket);
    }
    return tickets;
}
function deleteTicket(id){
    document.getElementById(id).innerHTML = "";
    let ticket;
    for (let i = 0; i < tickets.length; i++) {
        ticket = tickets[i];
        if (ticket.ticketId == id) {
            tickets.splice(i,1);
            break;
        }
    }
    req("/tickets", JSON.stringify(ticket), "DELETE").then(function (response) {
        console.log(response);
    })
}
function saveTicket() {
    modal.style.display = "none";
    if(actionType == "update"){
        updateTicketOnServer();
    }
    if(actionType == "addTicket"){
        saveNewTicket();
    }
}
function updateTicketOnServer() {
    let row = document.getElementById(updatedTicket.ticketId);
    let cell = row.cells;
    let tic = new Object();
    tic.ticketId = updatedTicket.ticketId;
    tic.ticketNumber = updatedTicket.ticketNumber = cell[0].innerHTML = document.getElementById("ticketNumber").value;
    tic.passengerName = updatedTicket.passengerName = cell[1].innerHTML = document.getElementById("passengerName").value;
    tic.document = updatedTicket.document = cell[2].innerHTML = document.getElementById("document").value;
    tic.sellingDate = updatedTicket.sellingDate = cell[3].innerHTML = document.getElementById("sellingDate").value;
    req("/tickets", JSON.stringify(tic), "PUT").then(function(response){
        console.log(response);
        document.getElementById("ticketNumber").value = "";
        document.getElementById("passengerName").value = "";
        document.getElementById("document").value = "";
        document.getElementById("sellingDate").value = "";
    });
    for (let i = 0; i < tickets.length; i++) {
        let ticket = tickets[i]
        if (ticket.id == updatedTicket.ticketId) {
            tickets[i] = updatedTicket;
        }
    }
}
function saveNewTicket(){
    let ticket = new Object();
    ticket.ticketNumber = document.getElementById("ticketNumber").value;
    ticket.passengerName = document.getElementById("passengerName").value;
    ticket.document = document.getElementById("document").value;
    ticket.sellingDate = document.getElementById("sellingDate").value;
    req("/tickets", JSON.stringify(ticket), "POST").then(function(response){
        ticket.ticketId = JSON.parse(response).ticketId;
        tickets.push(ticket);
        let row = '<tr id = "' + ticket.ticketId + '">' +
            '<td>' + ticket.ticketNumber + '</td>' +
            '<td>' + ticket.passengerName + '</td>' +
            '<td>' + ticket.document + '</td>' +
            '<td>' + ticket.sellingDate + '</td>' +
            '<td><button type="button" onclick="deleteTicket(' + ticket.ticketId + ')">Delete</button></td>' +
            '<td><button type="button" onclick="updateTicket(' + ticket.ticketId + ')">Update</button></td>' +
            '</tr>'
        document.getElementById("tickets").innerHTML = document.getElementById("tickets").innerHTML + row;
        document.getElementById("ticketNumber").value = "";
        document.getElementById("passengerName").value = "";
        document.getElementById("document").value = "";
        document.getElementById("sellingDate").value = "";
    });

}
function updateTicket(id){
    actionType = "update";
    modal.style.display = "block";
    let row = document.getElementById(id);
    let cell = row.cells;
    updatedTicket.ticketId = id;
    updatedTicket.ticketNumber = cell[0].innerHTML;
    updatedTicket.passengerName = cell[1].innerHTML;
    updatedTicket.document = cell[2].innerHTML;
    updatedTicket.sellingDate = cell[3].innerHTML;
    document.getElementById("ticketNumber").value = updatedTicket.ticketNumber;
    document.getElementById("passengerName").value = updatedTicket.passengerName;
    document.getElementById("document").value = updatedTicket.document;
    document.getElementById("sellingDate").value = updatedTicket.sellingDate;
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