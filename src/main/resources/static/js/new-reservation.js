function deleteReservation(e) {
    var srcElem = e.target || e.srcElement;
    var reservationsId = srcElem.getAttribute('x-id');
    var reservationURL = '/reservation/'+reservationsId;
    var form = document.getElementById("reservation");
    var password = null;
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id == "password") {
            password = fe.value;
        }
    }
    httpAsync("DELETE", reservationURL, null, password, function (data) {console.log(data); window.location.reload()});
}

var buttDel = document.getElementById("deleteReservation");
if (buttDel != null) {
    buttDel.addEventListener("click", deleteReservation, false);
}

$('#reservation').submit(function(e) {
    e.preventDefault();
    var form = document.getElementById("reservation");
    var notEmpty = false;
    var updateId = -1;
    var password = null;
    var reservationPairs = {};
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            if (fe.id == "id") {
                updateId = fe.value;
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
        if (updateId > -1) {
            httpAsync("PUT", "/reservation/"+updateId, reservationPairs, password, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        } else {
            httpAsync("POST", "/reservation/", reservationPairs, null, function (data) {
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
