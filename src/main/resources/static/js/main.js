function httpAsync(request, url, requestBody, xpassword, fps, callback) {
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

    if (xpassword) {
        xhr.setRequestHeader("X-Password", xpassword);
    }

    if (fps){
        if (fps["filter"]){
            xhr.setRequestHeader("X-Filter", fps["filter"]);
        }
        if (fps["order"]){
            xhr.setRequestHeader("X-Order", fps["order"]);
        }
        if (fps["base"] && fps["offset"]){
            xhr.setRequestHeader("X-Base", fps["base"]);
            xhr.setRequestHeader("X-Offset", fps["offset"]);
        }
        if (fps["count"]){
            xhr.setRequestHeader("X-Count-records", fps["count"]);
        }
    }

    if (requestBody != null) {
        xhr.send(JSON.stringify(requestBody));
    } else {
        xhr.send(null);
    }
}
