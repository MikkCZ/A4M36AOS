$('#flight').submit(function(e) {
    var form = document.getElementById("flight");
    var notEmpty = false;
    var updateId = -1;
    var flightPairs = {};
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            if (fe.id == "id") {
                updateId = fe.value;
            } else {
                flightPairs[fe.id] = fe.value;
            }
        }
        if(fe.value != ""){
            notEmpty = true;
        }
    }
    console.log("Sending object", flightPairs);

    if(notEmpty){
        if (updateId > -1) {
            httpAsync("PUT", "/flight/"+updateId, flightPairs, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        } else {
            httpAsync("POST", "/flight/", flightPairs, function (data) {
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
