function deleteReservation(e) {
    var srcElem = e.target || e.srcElement;
    var reservationId = srcElem.getAttribute('x-id');
    var reservationURL = '/reservation/'+reservationId;
    var form = document.getElementById("reservation");
    var password = null;
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id == "password") {
            password = fe.value;
        }
    }
    httpAsync("DELETE", reservationURL, null, password, null, function (data) {console.log(data); window.location.reload()});
}

var buttDel = document.getElementById("deleteReservation");
if (buttDel != null) {
    buttDel.addEventListener("click", deleteReservation, false);
}

$('#reservation').submit(function(e) {
    e.preventDefault();
    var form = document.getElementById("reservation");
    var notEmpty = false;
    var cancelId = -1;
    var password = null;
    var reservationPairs = {};
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            if (fe.id == "id") {
                cancelId = fe.value;
            } else if (fe.id == "password") {
                password = fe.value;
            } else {
                reservationPairs[fe.id] = fe.value;
            }
        }
        if(fe.value != ""){
            notEmpty = true;
        }
    }
    console.log("Sending object", reservationPairs);

    if(notEmpty){
        if (cancelId > -1) {
            reservationPairs["state"] = "CANCELLED";
            httpAsync("PUT", "/reservation/"+cancelId, reservationPairs, password, null, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        } else {
            httpAsync("POST", "/reservation/", reservationPairs, null, null, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        }
    }
});
