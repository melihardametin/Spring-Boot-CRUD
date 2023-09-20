if (localStorage.getItem('token')) {
    // Delete the item
    localStorage.removeItem('token');
}


document.getElementById("form").addEventListener("submit", function(event) {
    event.preventDefault();
    submitForm();
});



function submitForm() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/login", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 202) {
                var response = xhr.responseText;
                console.log("success " + xhr.status);
                console.log(response);
                localStorage.setItem("token", response);
                window.location.href = "http://localhost:8080/user";
            } else if (xhr.status === 200) {
                var response = xhr.responseText;
                console.log("successadmin " + xhr.status);
                console.log(response);
                localStorage.setItem("token", response);
                window.location.href = "http://localhost:8080/adminuser";
            } else if (xhr.status === 401) {
                console.error("Wrong password " + xhr.status);
                var x = document.getElementById("result");
                x.innerHTML = "Wrong password";
            } else if (xhr.status === 404) {
                console.error("No such user " + xhr.status);
                var x = document.getElementById("result");
                x.innerHTML = "No such user";
            } else {
                console.error("Request failed with status " + xhr.status);
            }
        }
    };

    var data = {
        username: username,
        password: password
    };

    xhr.send(JSON.stringify(data));
}
