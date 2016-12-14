function httpAsync(request, url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            callback(xhr.responseText);
        }
    };
    xhr.open(request, url, true); // true for asynchronous
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // xhr.setRequestHeader("Authorization", "Basic " + window.btoa(encodeURIComponent("admin:admin")));
    xhr.setRequestHeader("Authorization", "Basic YWRtaW46YWRtaW4=");
    xhr.send(null);
}

function deleteDestination(e) {
    var destinationID = e.srcElement.getAttribute('x-id');
    var destinationURL = '/destination/'+destinationID;
    httpAsync("DELETE", destinationURL, function (data) {console.log(data); window.location.reload()});
}



httpAsync("GET", "/destination/", function (data) {
    data = JSON.parse(data)
    var table = document.getElementById("destinations");

    for(var i in data){
        if(data[i]["id"] != undefined){
            // create link to details
            var a = document.createElement('a');
            var linkText = document.createTextNode(data[i].name);
            a.appendChild(linkText);
            a.title = linkText;
            a.href = data[i]["url"];

            // create options buttons
            var buttPut = document.createElement('button');
            buttPut.appendChild(document.createTextNode('Update'));
            buttPut.className = 'btn btn-info right-margin-2em';
            var buttPutLink = document.createElement('a');
            buttPutLink.href = '/destination/new?id='+data[i]["id"];
            buttPutLink.appendChild(buttPut);

            var buttDel = document.createElement('button');
            buttDel.appendChild(document.createTextNode('Delete'));
            buttDel.className = 'btn btn-danger right-margin-2em';
            buttDel.id = 'deleteDestination';
            buttDel.setAttribute("x-id", data[i]["id"]);
            buttDel.addEventListener("click", deleteDestination, false);

            // create row with cells
            var row = table.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);

            // fill up the cells
            cell1.innerHTML = data[i]["id"];
            cell2.appendChild(a);
            cell3.innerHTML = data[i]["latitude"];
            cell4.innerHTML = data[i]["longitude"];
            cell5.appendChild(buttPutLink);
            cell5.appendChild(buttDel);
        }
    }
});
