var path = window.location.pathname; // Get the HTML path
var pathParts = path.split("/"); // Split the path into segments

// Assuming the user ID is the second segment in the path
var userId = pathParts[2];


var xhr = new XMLHttpRequest();
xhr.open("POST", "http://localhost:8080/user/" + userId, true);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE) {
        console.log(xhr.status);
        if (xhr.status === 200) {
            var messages = JSON.parse(xhr.responseText);
            //renderMessages(messages);
            console.log(xhr.responseText);

            let placeholder = document.querySelector("#data-output");
            let out = "";
            for (let product of messages) {
                out += `<tr>
                                    <td>${product.sender}</td>
                                    <td>${product.title}</td>
                                    <td>${product.message}</td>
                                    <td>${product.date}</td>
                             </tr>
                        `;
            }
            placeholder.innerHTML = out;


        } else {
            console.error("Request failed with status " + xhr.status);
        }
    }

};xhr.send();