
function payReservation(e) {
    var srcElem = e.target || e.srcElement;
    var reservationsId = srcElem.getAttribute('x-id');
    var reservationURL = '/reservation/'+reservationsId+'/payment';
    httpAsync("POST", reservationURL, null, null, null, function (data) {console.log(data); window.location.reload()});
}

$('#crossing').submit(function(e) {
    e.preventDefault();
    var form = document.getElementById("crossing");

    var auth = [];
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            auth.push(fe.value);
        }
    }
    console.log("Auth object", auth);

    if((auth[0] === "admin" && auth[1] === "admin") || (auth[0] === "manager" && auth[1] === "manager")){
        window.location.replace("/reservation/all");
    } else {
        window.location.replace("/reservation/pass");
    }
});

$('#passing').submit(function(e) {
    e.preventDefault();
    var form = document.getElementById("passing");

    var auth = [];
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            auth.push(fe.value);
        }
    }
    console.log("Pass object", auth);

    httpAsync("GET", "/reservation/"+auth[0], null, auth[1], null, function (data) {
        // console.log(data)
        data = JSON.parse(data);
        var table = document.getElementById("reserv");

        if(data["id"] != undefined){

            // create options buttons
            var buttPut = document.createElement('button');
            buttPut.appendChild(document.createTextNode('Update'));
            buttPut.className = 'btn btn-info right-margin-2em';
            var buttPutLink = document.createElement('a');
            buttPutLink.href = '/reservation/new?id='+data["id"];
            buttPutLink.appendChild(buttPut);

            var buttPay = document.createElement('button');
            buttPay.appendChild(document.createTextNode('Pay'));
            buttPay.className = 'btn btn-info right-margin-2em';
            buttPay.id = 'payReservation';
            buttPay.setAttribute("x-id", data["id"]);
            buttPay.addEventListener("click", payReservation, false);

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
            var linkText = document.createTextNode(data["id"]);
            a.appendChild(linkText);
            a.title = linkText;
            a.href = data["id"];
            cell1.appendChild(a);

            cell2.innerHTML = data["seats"];

            cell3.innerHTML = data["password"];

            // create link to from
            var flightLink = document.createElement('a');
            var flightLinkText = document.createTextNode(data["flight"]);
            flightLink.appendChild(flightLinkText);
            flightLink.title = flightLinkText;
            flightLink.href = "/flight/all";
            cell4.appendChild(flightLink);

            cell5.innerHTML = data["state"];
            cell6.innerHTML = data["created"];
            cell7.appendChild(buttPutLink);
            cell7.appendChild(buttPay);
        }
    });

});