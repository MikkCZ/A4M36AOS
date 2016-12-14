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
    for ( var i = 0; i < form.elements.length; i++ ) {
        var fe = form.elements[i];
        if(fe.id != ""){
            destPairs[fe.id] = fe.value;
        }
        if(fe.value != ""){
            notEmpty = true;
        }
    }
    console.log("Sending object", destPairs);

    if(notEmpty){
        httpAsync("POST", "/destination/", destPairs, function (data) {console.log(data)});
    }
    e.preventDefault();
});