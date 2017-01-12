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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/airline-service');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});


//
// var wsocket;
// var serviceLocation = "ws://localhost:8000/websockets";
//
// function onMessageReceived(evt) {
//     var msg = evt.data;
//     var clients_label = document.getElementById("clients_count");
//     clients_label.innerHTML = msg;
// }
//
// function connectToServer() {
//     wsocket = new WebSocket(serviceLocation);
//     wsocket.onmessage = onMessageReceived;
// }
//
// $(document).ready(function() {
//     connectToServer();
// });