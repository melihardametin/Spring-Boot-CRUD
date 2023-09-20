

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

    var xhr = new XMLHttpRequest();
    console.log("evett " + xhr.status);
    xhr.open("POST", "http://localhost:8080/register", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 201) {
                // Request successful
                var response = JSON.parse(xhr.responseText);
                // Handle the response from the server
                console.log("successs " + xhr.status);
                window.location.href = "http://localhost:8080/login";
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
        password : password
    };

    xhr.send(JSON.stringify(data));
}