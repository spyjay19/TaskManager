function signUp(){
    const username = document.getElementById("newUsername").value;
    const password = document.getElementById("newPassword").value;

    fetch("http://localhost:8080/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })

        .then(res => res.json())
        .then(data => {
            localStorage.setItem("USER_ID", data.id)
            localStorage.setItem("USERNAME", data.username);

            window.location.href = "app.html"
        })
}
