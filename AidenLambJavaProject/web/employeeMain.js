let orders;
let selected = null;
window.onload = function() {
    document.querySelector("#orders").addEventListener("click", showOrders);
    document.querySelector("#toppings").addEventListener("click", showToppings);
    document.querySelector("#filled").addEventListener("click", showFilled);
    document.querySelector("#pending").addEventListener("click", showPending);
    document.querySelector("#ordersTable").addEventListener("click", handleOrderRowClick);
    document.querySelector("#toppingsTable").addEventListener("click", handleToppingRowClick);
    document.querySelector("#fillButton").addEventListener("click", fillStatus);
    document.querySelector("#deleteTopping").addEventListener("click", deleteTopping);
    document.querySelector("#addTopping").addEventListener("click", showAddPanel);
    document.querySelector("#toppingCancelButton").addEventListener("click", cancelAdd);
    document.querySelector("#toppingDoneButton").addEventListener("click", addTopping);
    document.querySelector("#logout").addEventListener("click", logout);
    swapAddPanel();
};


function showOrders() {
    let url = "ManageOrders/orders";
    let method = "GET";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (xmlhttp.status === 500) {
                let err = JSON.parse(resp);
                alert(err.message);
            } else if (xmlhttp.status === 200) {
                if (resp.search("ERROR") >= 0) {
                    alert("Could not retreive orders");
                    console.log(resp);
                } else {
                    orders = JSON.parse(resp);
                    console.log(orders);
                    buildOrderTable(orders);
                    swapSections("orders");
                    document.querySelector("#fillButton").setAttribute("disabled", "");
                    hideAddPanel();
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
}

function showToppings() {
    let url = "ManagePizzaToppings/pizzaToppings";
    let method = "GET";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (xmlhttp.status === 500) {
                let err = JSON.parse(resp);
                alert(err.message);
            } else if (xmlhttp.status === 200) {
                if (resp.search("ERROR") >= 0) {
                    alert("Could not retreive toppings");
                    console.log(resp);
                } else {
                    toppings = JSON.parse(resp);
                    console.log(toppings);
                    buildToppingTable(toppings);
                    swapSections("toppings");
                    hideAddPanel();
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
}

function buildToppingTable(data) {
    let table = document.querySelector("#toppingsTable");
    let html = "<tr>";
    html += "<th>ID</th><th>Date Created</th><th>Employee Who Added</th><th>Active</th><th>Name</th><th>Price</th>";
    html += "</tr>";
    for(let i=0; i<data.length; i++) {
        let cur = data[i];
        html += "<tr>";
        html += "<td>" + cur.pizzaTopping_id + "</td>";
        html += "<td>" + cur.createDate + "</td>";
        html += "<td>" + cur.empAddedBy + "</td>";
        html += "<td>" + cur.isActive + "</td>";
        html += "<td>" + cur.name + "</td>";
        html += "<td>" + cur.price + "</td>";
        html += "</tr>";
    }
    table.innerHTML = html;
}

function deleteTopping() {
    let tds = selected.querySelectorAll("td");
    let id = tds[0].innerHTML;
    console.log(id);
    let url = "ManagePizzaToppings/pizzaToppings/" + id;
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete delete request");
            } else {
                alert("delete request completed successfully");
                showToppings();
            }
        }
    };
    xmlhttp.open("DELETE", url, true); // "DELETE" is the action, "url" is the entity
    xmlhttp.send();
}

function addTopping() {
    let name = document.querySelector("#toppingName").value;
    //Will change this if there is time
    let employeeId = 1;
    let active = document.querySelector("#isActive").value;
    let price = document.querySelector("#toppingPrice").value;
    
    let obj = {
        "empAddedBy" : employeeId,
        "isActive" : active,
        "name" : name,
        "price" : price
    };
    
    let url = "ManagePizzaToppings/pizzaToppings";
    let method = "POST";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete add request");
            } else {
                alert("add request completed successfully");
                showToppings();
                hideAddPanel();
                clearAddPanel();
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(obj));
    }


function showAddPanel() {
    document.querySelector("#showToppings").classList.add("hidden");
    swapAddPanel();
    
}

function hideAddPanel() {
    if(!document.querySelector("#AddToppingPanel").classList.contains("hidden"))
    document.querySelector("#AddToppingPanel").classList.add("hidden");
}
function clearAddPanel() {
    document.querySelector("#toppingName").value = "";
    document.querySelector("#isActive").value = "";
    document.querySelector("#toppingPrice").value = "";
}

function cancelAdd() {
    hideAddPanel();
    clearAddPanel();
}

function buildOrderTable(data) {
    let table = document.querySelector("#ordersTable");
    let html = "<tr>";
    html += "<th>ID</th><th>Amount Paid</th><th>Customer</th><th>Delivery</th><th>Delivery Date</th><th>HST</th><th>Order Placed Date</th><th>Order Status</th><th>Order Total</th><th>Sub Total</th>";
    html += "</tr>";
    for(let i=0; i<data.length; i++) {
        let cur = data[i];
        html += "<tr>";
        html += "<td>" + cur.order_id + "</td>";
        html += "<td>" + cur.amountPaid + "</td>";
        html += "<td>" + cur.customer_id + "</td>";
        html += "<td>" + cur.delivery + "</td>";
        html += "<td>" + cur.deliveryDate + "</td>";
        html += "<td>" + cur.hst + "</td>";
        html += "<td>" + cur.orderPlacedDate + "</td>";
        html += "<td>" + cur.orderStatus + "</td>";
        html += "<td>" + cur.orderTotal + "</td>";
        html += "<td>" + cur.subTotal + "</td>";
        html += "</tr>";
    }
    table.innerHTML = html;
}

function showFilled() {
    let newOrders = [];
    if(orders !== null) {
        for(let i=0; i<orders.length; i++) {
            if(orders[i].orderStatus === "FILLED") {
                newOrders.push(orders[i]);
            }
        }
    }
    buildOrderTable(newOrders);
    
}

function showPending() {
    let newOrders = [];
    if(orders !== null) {
        for(let i=0; i<orders.length; i++) {
            if(orders[i].orderStatus === "PENDING") {
                newOrders.push(orders[i]);
            }
        }
    }
    buildOrderTable(newOrders);
}

function fillStatus() {
    let tds= selected.querySelectorAll("td");
    let orderId = Number(tds[0].innerHTML);
    let orderStatus = tds[7].innerHTML;
    if(orderStatus !== "FILLED") {
        let obj = {
            "orderStatus": "FILLED",
            "order_id" : orderId
        };
        let url = "ManageOrders/orders/" + orderId;
        let method = "PUT";
        let xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete UPDATE request");
            } else {
                alert("UPDATE request completed successfully");
                showOrders();
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(obj));
    }
    else {
        alert("ERROR: Order has already been filled");
    }
}
function clearSelections() {
    let trs = document.querySelectorAll("tr");
    for (let i = 0; i < trs.length; i++) {
        trs[i].classList.remove("highlighted");
    }
    selected = null;
}

function handleOrderRowClick(e) {
    
    clearSelections();
    e.target.parentElement.classList.add("highlighted");
    selected = e.target.parentElement;

    document.querySelector("#fillButton").removeAttribute("disabled");
}

function handleToppingRowClick(e) {
    
    clearSelections();
    e.target.parentElement.classList.add("highlighted");
    selected = e.target.parentElement;
    document.querySelector("#deleteTopping").removeAttribute("disabled");
}


function swapAddPanel() {
    let toppingAdd = document.getElementById("AddToppingPanel");
    
    if(!toppingAdd.classList.contains("hidden")) {
        toppingAdd.classList.add("hidden");
    }
    else {
        toppingAdd.classList.remove("hidden");
    }
}

function swapSections(section) {
    let orders = document.querySelector("#showOrders");
    let toppings = document.querySelector("#showToppings");
    
    if(section === "orders") {
        orders.classList.remove("hidden");
        if(!toppings.classList.contains("hidden")) {
            toppings.classList.add("hidden");
        }
    }
    else {
        toppings.classList.remove("hidden");
        if(!orders.classList.contains("hidden")) {
            orders.classList.add("hidden");
        }
    }
}

function logout() {
    window.location.href = "index.html";
}


