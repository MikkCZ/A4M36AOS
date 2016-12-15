function httpAsync(request, url, requestBody, xpassword, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log("Response status: ",xhr.status)
        if (xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText);
        }
    };
    xhr.open(request, url, true); // true for asynchronous
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // xhr.setRequestHeader("Authorization", "Basic " + window.btoa(encodeURIComponent("admin:admin")));
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
    if (xpassword != null) {
        xhr.setRequestHeader("X-Password", xpassword);
    }
    if (requestBody != null) {
        xhr.send(JSON.stringify(requestBody));
    } else {
        xhr.send(null);
    }
}
