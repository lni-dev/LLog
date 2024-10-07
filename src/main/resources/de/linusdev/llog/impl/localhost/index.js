window.addEventListener('load', function () {

    // Create WebSocket connection.
    const socket = new WebSocket("ws://" + window.location.host + "/llog/websocket");

    // Connection opened
    socket.addEventListener("open", (event) => {
        console.log("Hello Server!");
    });

    // Listen for messages
    socket.addEventListener("message", (event) => {
        console.log("Message from server ", event.data);
    });

});