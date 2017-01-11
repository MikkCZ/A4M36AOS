function deleteDestination(e) {
    var srcElem = e.target || e.srcElement;
    var destinationID = srcElem.getAttribute('x-id');
    var destinationURL = '/destination/'+destinationID;
    httpAsync("DELETE", destinationURL, null, null, null, function (data) {console.log(data); window.location.reload()});
}

httpAsync("GET", "/destination/", null, null, null, function (data) {
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

// $('#destination').submit(function(e) {
//     var form = document.getElementById("destination");
//     var notEmpty = false;
//     var updateId = -1;
//     var destPairs = {};
//     for ( var i = 0; i < form.elements.length; i++ ) {
//         var fe = form.elements[i];
//         if(fe.id != "") {
//             if (fe.id == "id") {
//                 updateId = fe.value;
//             } else {
//                 destPairs[fe.id] = fe.value;
//             }
//         }
//         if(fe.value != ""){
//             notEmpty = true;
//         }
//     }
//     console.log("Sending object", destPairs);
//
//     if(notEmpty){
//         if (updateId > -1) {
//             httpAsync("PUT", "/destination/"+updateId, destPairs, null, null, function (data) {
//                 console.log(data);
//                 for ( var i = 0; i < form.elements.length; i++ ) {
//                     var fe = form.elements[i];
//                     if(fe.value != ""){
//                         fe.value = "";
//                     }
//                 }
//             });
//         } else {
//             httpAsync("POST", "/destination/", destPairs, null, null, function (data) {
//                 console.log(data);
//                 for ( var i = 0; i < form.elements.length; i++ ) {
//                     var fe = form.elements[i];
//                     if(fe.value != ""){
//                         fe.value = "";
//                     }
//                 }
//             });
//         }
//     }
//     e.preventDefault();
// });
