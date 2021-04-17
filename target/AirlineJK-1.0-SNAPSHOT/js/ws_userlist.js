

var ws = new WebSocket("ws://localhost:8088/AirlineJK/flight");

ws.onmessage = function(event) {
    let obj = JSON.parse(event.data)
    console.log(obj)
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