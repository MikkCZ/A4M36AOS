function deleteFlight(e) {
    var srcElem = e.target || e.srcElement;
    var flightId = srcElem.getAttribute('x-id');
    var flightURL = '/flight/'+flightId;
    httpAsync("DELETE", flightURL, null, null, function (data) {console.log(data); window.location.reload()});
}

httpAsync("GET", "/flight/", null, null, function (data) {
    data = JSON.parse(data)
    var table = document.getElementById("flights");

    for(var i in data){
        if(data[i]["id"] != undefined){

            // create options buttons
            var buttPut = document.createElement('button');
            buttPut.appendChild(document.createTextNode('Update'));
            buttPut.className = 'btn btn-info right-margin-2em';
            var buttPutLink = document.createElement('a');
            buttPutLink.href = '/flight/new?id='+data[i]["id"];
            buttPutLink.appendChild(buttPut);

            var buttDel = document.createElement('button');
            buttDel.appendChild(document.createTextNode('Delete'));
            buttDel.className = 'btn btn-danger right-margin-2em';
            buttDel.id = 'deleteFlight';
            buttDel.setAttribute("x-id", data[i]["id"]);
            buttDel.addEventListener("click", deleteFlight, false);

            // create row with cells
            var row = table.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);
            var cell6 = row.insertCell(5);
            var cell7 = row.insertCell(6);

            // fill up the cells
            cell1.innerHTML = data[i]["id"];

            // create link to details
            var a = document.createElement('a');
            var linkText = document.createTextNode(data[i].name);
            a.appendChild(linkText);
            a.title = linkText;
            a.href = data[i]["url"];
            cell2.appendChild(a);

            cell3.innerHTML = data[i]["dateOfDeparture"];

            // create link to from
            var fromLink = document.createElement('a');
            var fromLinkText = document.createTextNode(data[i]["from"]);
            fromLink.appendChild(fromLinkText);
            fromLink.title = fromLinkText;
            fromLink.href = "/destination/all";
            cell4.appendChild(fromLink);

            // create link to to
            var toLink = document.createElement('a');
            var toLinkText = document.createTextNode(data[i]["to"]);
            toLink.appendChild(toLinkText);
            toLink.title = toLinkText;
            toLink.href = "/destination/all";
            cell5.appendChild(toLink);

            cell6.innerHTML = data[i]["seats"];
            cell7.appendChild(buttPutLink);
            cell7.appendChild(buttDel);
        }
    }
});
