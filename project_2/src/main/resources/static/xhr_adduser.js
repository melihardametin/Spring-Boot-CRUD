

document.getElementById("form").addEventListener("submit", function(event) {
    event.preventDefault();
    submitForm();
});

function submitForm() {
    var username = document.getElementById("username").value;
    var name = document.getElementById("name").value;
    var surname = document.getElementById("surname").value;
    var date = document.getElementById("date").value;
    var gender = document.getElementById("gender").value;
    var mail = document.getElementById("mail").value;
    var city = document.getElementById("city").value;
    var password = document.getElementById("password").value;
    var admin = document.getElementById("admin").value;

    var xhr = new XMLHttpRequest();
    var path = window.location.pathname; // Get the HTML path
    var pathParts = path.split("/"); // Split the path into segments

    // Assuming the user ID is the second segment in the path
    var userId = pathParts[2];
    console.log("evett " + xhr.status);

    xhr.open("POST", "http://localhost:8080/adminuser/" + userId+"/send", true);
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
        username: username,
        name: name,
        surname : surname,
        date : date,
        gender : gender,
        mail : mail,
        city : city,
        password : password,
        admin : admin
    };

    xhr.send(JSON.stringify(data));
}