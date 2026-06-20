function signUp() {
    const username = document.getElementById("newUsername").value;
    const password = document.getElementById("newPassword").value;

    fetch("https://taskmanager-1-rt1r.onrender.com/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    })

        .then(res => {
            if (!res.ok) {
                throw new Error(`Sign up failed: ${res.status}`);
            }
            return res.json();
        })
        .then(data => {
            localStorage.setItem("USER_ID", data.id)
            localStorage.setItem("USERNAME", data.username);
            window.location.href = "app.html"
        })
        .catch(err => console.error("SIGNUP ERROR:", err));
}
