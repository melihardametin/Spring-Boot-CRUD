document.getElementById("form").addEventListener("submit", function(event) {
    event.preventDefault();
    submitForm();
});

function submitForm() {
    var receiver = document.getElementById("to").value;
    var title = document.getElementById("title").value;
    var message = document.getElementById("message").value;

    var xhr = new XMLHttpRequest();
    console.log("ttttt " + xhr.status);
    var path = window.location.pathname; // Get the HTML path
    var pathParts = path.split("/"); // Split the path into segm
    var userId = pathParts[2];

    xhr.open("POST", "http://localhost:8080/user/" + userId+"/send", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 201) {
                // Request successful
                var response = JSON.parse(xhr.responseText);
                // Handle the response from the server
                console.log("successs " + xhr.status);
                // ...
            } else {
                // Request failed
                console.error("Request failed with status " + xhr.status);

            }
        }
    };

    var data = {
        sender: userId,
        receiver: receiver,
        title: title,
        message: message
    };

    xhr.send(JSON.stringify(data));
}