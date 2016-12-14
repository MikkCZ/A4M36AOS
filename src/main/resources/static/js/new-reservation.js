$('#reservation').submit(function(e) {
    var form = document.getElementById("reservation");
    var notEmpty = false;
    var updateId = -1;
    var reservationPairs = {};
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            if (fe.id == "id") {
                updateId = fe.value;
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
            httpAsync("PUT", "/reservation/"+updateId, reservationPairs, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        } else {
            httpAsync("POST", "/reservation/", reservationPairs, function (data) {
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
    e.preventDefault();
});
