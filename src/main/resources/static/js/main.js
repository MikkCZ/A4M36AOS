
function httpGetAsync(theUrl, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
            callback(xhr.responseText);
        }
    };

    xhr.open("GET", theUrl, true); // true for asynchronous
    xhr.setRequestHeader("Accept", "application/json");
    // xhr.setRequestHeader("Authorization", "Basic " + window.btoa(encodeURIComponent("admin:admin")));
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
    xhr.send(null);
}

httpGetAsync("/destination/", function (data) {
    console.log(data);

    data = {
        "1": {"ID": 1,"Name": "Praha","Latitude": 160,"Longitude": 15},
        "2": {"ID": 1,"Name": "Berlin","Latitude": 0,"Longitude": -10},
        "3": {"ID": 1,"Name": "London","Latitude": -13,"Longitude": 90}
    };

    var table = document.getElementById("destinations");
});