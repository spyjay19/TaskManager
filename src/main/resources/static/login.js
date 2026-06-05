function logIn(){
    const username = document.getElementById("username");
    const password = document.getElementById("password");

    fetch(`https://taskmanager-1-rt1r.onrender.com`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username.value,
            password: password.value
        })
    })

        .then(async res => {
            const statusLabel = document.getElementById("statusLabel");

            if (res.status === 401){
                statusLabel.style.color = "red";
                statusLabel.textContent = "Incorrect password, try again."
                return;
            }
            else if (res.status === 404){
                statusLabel.style.color = "red";
                statusLabel.textContent = "Incorrect username, try again."
                return;
            }

            const data = await res.json();

            statusLabel.style.color = "green";
            statusLabel.textContent = "Log in successful!"

            localStorage.setItem("USER_ID", data.id);
            localStorage.setItem("USERNAME", data.username);

            window.location.href = "app.html";
        })
}