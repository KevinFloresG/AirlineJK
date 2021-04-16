

var ws = new WebSocket("ws://localhost:8088/AirlineJK/userList");

ws.onmessage = function(event) {
    console.log(event.data);
};

function send() {
    var json = JSON.stringify({
        tyype:"post",
        content:{
            name:"Kevin FFF"
        }
    });

    ws.send(json);
}