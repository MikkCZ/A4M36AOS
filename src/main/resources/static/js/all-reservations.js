function deleteReservation(e) {
    var srcElem = e.target || e.srcElement;
    var reservationsId = srcElem.getAttribute('x-id');
    var reservationURL = '/reservation/'+reservationsId;
    httpAsync("DELETE", reservationURL, null, function (data) {console.log(data); window.location.reload()});
}

function payReservation(e) {
    var srcElem = e.target || e.srcElement;
    var reservationsId = srcElem.getAttribute('x-id');
    var reservationURL = '/reservation/'+reservationsId+'/payment';
    httpAsync("POST", reservationURL, null, function (data) {console.log(data); window.location.reload()});
}

httpAsync("GET", "/reservation/", null, function (data) {
    data = JSON.parse(data)
    var table = document.getElementById("reservations");

    for(var i in data){
        if(data[i]["id"] != undefined){

            // create options buttons
            var buttPut = document.createElement('button');
            buttPut.appendChild(document.createTextNode('Update'));
            buttPut.className = 'btn btn-info right-margin-2em';
            var buttPutLink = document.createElement('a');
            buttPutLink.href = '/reservation/new?id='+data[i]["id"];
            buttPutLink.appendChild(buttPut);

            var buttPay = document.createElement('button');
            buttPay.appendChild(document.createTextNode('Pay'));
            buttPay.className = 'btn btn-info right-margin-2em';
            buttPay.id = 'payReservation';
            buttPay.setAttribute("x-id", data[i]["id"]);
            buttPay.addEventListener("click", payReservation, false);

            var buttDel = document.createElement('button');
            buttDel.appendChild(document.createTextNode('Delete'));
            buttDel.className = 'btn btn-danger right-margin-2em';
            buttDel.id = 'deleteReservation';
            buttDel.setAttribute("x-id", data[i]["id"]);
            buttDel.addEventListener("click", deleteReservation, false);

            // create row with cells
            var row = table.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);
            var cell6 = row.insertCell(5);
            var cell7 = row.insertCell(6);

            // fill up the cells
            // create link to details
            var a = document.createElement('a');
            var linkText = document.createTextNode(data[i]["id"]);
            a.appendChild(linkText);
            a.title = linkText;
            a.href = data[i]["id"];
            cell1.appendChild(a);

            cell2.innerHTML = data[i]["seats"];

            cell3.innerHTML = data[i]["password"];

            // create link to from
            var flightLink = document.createElement('a');
            var flightLinkText = document.createTextNode(data[i]["flight"]);
            flightLink.appendChild(flightLinkText);
            flightLink.title = flightLinkText;
            flightLink.href = "/flight/all";
            cell4.appendChild(flightLink);

            cell5.innerHTML = data[i]["state"];
            cell6.innerHTML = data[i]["created"];
            cell7.appendChild(buttPutLink);
            cell7.appendChild(buttPay);
            cell7.appendChild(buttDel);
        }
    }
});
