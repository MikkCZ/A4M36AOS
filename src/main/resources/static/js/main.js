function httpAsync(request, url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr.responseText);
            callback(xhr.responseText);
        }
    };
    xhr.open(request, url, true); // true for asynchronous
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // xhr.setRequestHeader("Authorization", "Basic " + window.btoa(encodeURIComponent("admin:admin")));
    xhr.setRequestHeader("Authorization", "Basic admin:admin");
    xhr.send(null);
}



httpAsync("GET", "/destination/", function (data) {
    console.log(data);

    data = [
        {"id":1,"name":"Berlin","url":"/destination/1","latitude":0.0,"longitude":0.0},
        {"id":2,"name":"London","url":"/destination/2","latitude":0.0,"longitude":0.0},
        {"id":3,"name":"Praha","url":"/destination/3","latitude":0.0,"longitude":0.0}
    ];
    var table = document.getElementById("destinations");

    for(var i in data){
        var a = document.createElement('a');
        var linkText = document.createTextNode(data[i].name);
        a.appendChild(linkText);
        a.title = linkText;
        a.href = data[i]["url"];

        var row = table.insertRow();
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        cell1.innerHTML = data[i]["id"];
        cell2.appendChild(a);
        cell3.innerHTML = data[i]["latitude"];
        cell4.innerHTML = data[i]["longitude"];
    }
});
