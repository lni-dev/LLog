window.addEventListener('load', function () {

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", window.location.origin + "/llog/websocket", false ); // false for synchronous request
    xmlHttp.send( null );

    // Create WebSocket connection.
    const socket = new WebSocket("ws://localhost:" + xmlHttp.responseText);

    // Connection opened
    socket.addEventListener("open", (event) => {
        console.log("Hello Server!");
    });

    // Listen for messages
    socket.addEventListener("message", (event) => {
        console.log("Message from server ", event.data);
    });

});