function httpAsync(request, url, requestBody, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function(e) {
        console.log("XHR status:", xhr.status)

        if (xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText);
        }
    };

    xhr.open(request, url, true); // true for asynchronous
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // xhr.setRequestHeader("Authorization", "Basic " + window.btoa(encodeURIComponent("admin:admin")));
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
    xhr.send(JSON.stringify(requestBody));
}

var destPairs = {};

$('#destination').submit(function(e) {
    var form = document.getElementById("destination");
    var notEmpty = false;
    var updateId = -1;
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