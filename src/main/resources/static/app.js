let USER_ID = localStorage.getItem("USER_ID");
const API_URL = `https://taskmanager-1-rt1r.onrender.com`;

window.onload = () => {
    USER_ID = localStorage.getItem("USER_ID");
    const username = localStorage.getItem("USERNAME");

    if (!USER_ID || USER_ID === "null") {
        window.location.href = "login.html";
        return;
    }

    document.getElementById("WelcomeLabel").textContent = `Welcome back to the task manager: ${username}!`;
    document.getElementById("due-date").value = new Date().toISOString().split("T")[0];

    getTasks();
};

function renderTasks(tasks) {
    const list = document.getElementById("taskList");
    list.innerHTML = ""

    if (tasks.length === 0){
        list.innerHTML =" <p>No tasks found</p>";
    }

    tasks.forEach(task => {
        const li = createTaskElement(task)

        list.appendChild(li)
    })
}

function createTaskElement(task) {
    const li = document.createElement("li");
    li.classList.add("task-card");

    li.innerHTML = `
                         <strong>${task.title}</strong>
                         <span style="color: ${task.completed ? 'green' : 'orange'}">
                         [${task.completed ? "Completed" : "Incomplete"}]
                         </span>
                         <div></div>
                         ${task.description}
                         <div></div>
                         Created: ${new Date(task.createdAt).toLocaleString()}
                         <div></div>
                         Last updated: ${new Date(task.updatedAt).toLocaleString()}
                         <div></div>
                         <button id = "completeBtn" style="display: ${task.completed ? 'none' : 'inline'}" onclick="completeTask(${task.id})">Complete</button>
                         <button id = "incompleteBtn" style="display: ${task.completed ? 'inline' : 'none'}" onclick="incompleteTask(${task.id})">Incomplete</button>
                         <button style="color: red" onclick="confirmDelete(${task.id}, this)">Delete</button>
                         <button onclick="editTask(${task.id}, this)">Edit</button>
                         <div></div>
                         <strong>Due Date: ${(task.dueDate)}</strong>
                        `;

    return li;
}

function applyFilters(task){
    const taskStatusFilter = document.getElementById('filters').value;
    const taskDueDateFilter = document.getElementById('dueDateFilters').value;
    const today = new Date().toISOString().split("T")[0];

    if (taskStatusFilter === "completed" && !task.completed) {
        return;
    }

    if (taskStatusFilter === "incomplete" && task.completed) {
        return;
    }

    if (taskDueDateFilter === "upcoming" && task.dueDate <= today) {
        return;
    }
    if (taskDueDateFilter === "today" && task.dueDate !== today) {
        return;
    }
    if (taskDueDateFilter === "overdue" && (task.dueDate >= today || task.completed)) {
        return;
    }

    return task;
}

function getTasks() {
    USER_ID = localStorage.getItem("USER_ID")

    console.log(USER_ID, "App")

    if (!USER_ID || USER_ID === "null"){
        console.log("No user id found.");
        return;
    }

    fetch(`${API_URL}/user/${USER_ID}`)
        .then(res => res.json())
        .then(data => {
            const filteredTasks = data.filter(task => applyFilters(task));

            renderTasks(filteredTasks);
        });
}

function addTask() {
    const input = document.getElementById("taskInput");
    const descriptionInput = document.getElementById("descriptionInput");
    const dueDateInput = document.getElementById("due-date");
    USER_ID = localStorage.getItem("USER_ID")

    fetch(`${API_URL}/user/${USER_ID}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: input.value,
            completed: false,
            description: descriptionInput.value,
            dueDate: dueDateInput.value
        })
    })
        .then(() => {
            input.value = "";
            descriptionInput.value = "";

            getTasks();
        }).catch(err => console.error("CREATE ERROR:", err));
}

function deleteTask(id) {
    fetch(`${API_URL}/${id}`, {
        method: "DELETE"
    }).then(() => {
        getTasks();
    });
}

function confirmDelete(id, button) {
    const li = button.parentElement;

    if (li.querySelector(".confirm-btn")) return;

    const confirmBtn = document.createElement("button");
    confirmBtn.textContent = "Confirm";
    confirmBtn.style.color = "red";
    confirmBtn.classList.add("confirm-btn");

    const cancelBtn = document.createElement("button");
    cancelBtn.textContent = "Cancel";
    cancelBtn.style.color = "black";
    cancelBtn.classList.add("cancel-btn");

    const div = document.createElement("div")
    div.appendChild(confirmBtn);
    div.appendChild(cancelBtn)

    confirmBtn.onclick = () => deleteTask(id);
    cancelBtn.onclick = () => cancelDelete(div, confirmBtn, cancelBtn);

    li.appendChild(div);
}

function cancelDelete(div, confirmBtn, cancelBtn) {
    div.removeChild(confirmBtn);
    div.removeChild(cancelBtn)
}

function completeTask(id) {
    fetch(`${API_URL}/${id}/complete`, {
        method: "PUT"
    }).then(() => {
        getTasks();
    })
}

function incompleteTask(id) {
    fetch(`${API_URL}/${id}/incomplete`, {
        method: "PUT"
    }).then(() => {
        getTasks();
    })
}

function editTask(id, editButton) {
    const li = editButton.parentElement;

    const taskName = document.createElement("input");
    taskName.placeholder = "Enter task name...";
    taskName.classList.add("updatedTaskName-text");

    const taskDescription = document.createElement("input");
    taskDescription.placeholder = "Enter task description...";
    taskDescription.classList.add("updatedTaskDescription-text");

    const taskDueDate = document.createElement("input")
    taskDueDate.type = "date"
    taskDueDate.value = new Date().toISOString().split("T")[0];
    taskDueDate.classList.add("updatedTaskDueDate-date")

    const saveBtn = document.createElement("button");
    saveBtn.textContent = "Save";
    saveBtn.style.color = "green";
    saveBtn.classList.add("save-btn");

    const cancelBtn = document.createElement("button");
    cancelBtn.textContent = "Cancel";
    cancelBtn.style.color = "black";
    cancelBtn.classList.add("cancel-btn");

    const div = document.createElement("div")
    div.appendChild(taskName);
    div.appendChild(taskDescription)
    div.appendChild(taskDueDate)

    li.appendChild(div);

    const div2 = document.createElement("div")
    div2.appendChild(saveBtn);
    div2.appendChild(cancelBtn)

    li.appendChild(div2);

    saveBtn.onclick = () => {
        fetch(`${API_URL}/${id}`, {
            method: "GET",
        })
            .then(() => {
                console.log("Retrieved");
            })

        fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title: taskName.value,
                completed: false,
                description: taskDescription.value,
                dueDate: taskDueDate.value
            })
        })
            .then(() => {
                getTasks();
            });
    };

    cancelBtn.onclick = () => {
        li.removeChild(div);
        li.removeChild(div2);
    };
}

function LogOut(){
    USER_ID = null;

    localStorage.removeItem("USER_ID");
    localStorage.removeItem("USERNAME");

    window.location.href = "index.html";
}