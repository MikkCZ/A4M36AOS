$('#destination').submit(function(e) {
    var form = document.getElementById("destination");
    var notEmpty = false;
    var updateId = -1;
    var destPairs = {};
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != "") {
            if (fe.id == "id") {
                updateId = fe.value;
            } else {
                destPairs[fe.id] = fe.value;
            }
        }
        if(fe.value != ""){
            notEmpty = true;
        }
    }
    console.log("Sending object", destPairs);

    if(notEmpty){
        if (updateId > -1) {
            httpAsync("PUT", "/destination/"+updateId, destPairs, function (data) {
                console.log(data);
                for ( var i = 0; i < form.elements.length; i++ ) {
                    var fe = form.elements[i];
                    if(fe.value != ""){
                        fe.value = "";
                    }
                }
            });
        } else {
            httpAsync("POST", "/destination/", destPairs, function (data) {
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
