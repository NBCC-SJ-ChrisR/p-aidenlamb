let sizes;
let crusts;
let toppings;
let order;
window.onload = function() {
  document.querySelector("#startOrder").addEventListener("click", startOrder);
  document.querySelector("#pizzaOptions").addEventListener("click", selectToppings);
  document.querySelector("#editOrder").addEventListener("click", editOrder);
  document.querySelector("#submitOrder").addEventListener("click", submitOrder);
  document.querySelector("#logout").addEventListener("click", logout);
  getSizes();
};


function loadPage(allSizes, allCrusts, allToppings) {
    sizes = allSizes;
    crusts = allCrusts;
    toppings = allToppings;
    buildPizzaTable(sizes, crusts, toppings);
}

function getSizes() {
    let url = "ManagePizzaSizes/pizzaSizes";
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
                alert("Could not retreive sizes");
                console.log(resp);
            } else {
                let data = JSON.parse(resp);
                getCrusts(data);
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
}

function getCrusts(sizes) {
    let url = "ManagePizzaCrust/pizzaCrust";
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
                alert("Could not retreive crusts");
                console.log(resp);
            } else {
                let data = JSON.parse(resp);
                getToppings(sizes, data);
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
}

function getToppings(sizes, crusts) {
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
                let data = JSON.parse(resp);
                loadPage(sizes, crusts, data);
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
}

function buildPizzaTable(sizes, crusts, toppings) {
    let table = document.querySelector("#create");
    let html = "<tr>";
    html += "<th>Pick A Size</th>";
    html += "<th>Pick A Crust</th>";
    html += "<th>Delivery</th>";
    html += "<th>Quantity</th>";
    html += "</tr>";
    html += "<tr><td><select id='sizeSelect'>";
    for(let i=0; i<sizes.length; i++) {
        html+= "<option value='" + sizes[i].name + "'>" + sizes[i].name + "</option>";
    }
    html += "</td>";
    html += "<td><select id='crustSelect'>";
    for(let i=0; i<crusts.length; i++) {
        html+= "<option value='" + crusts[i].name + "'>" + crusts[i].name + "</option>";
    }
    html += "</td>";
    html += "<td><input id='deliveryInput' type='number' min='0' max='1' value='0' /></td>";
    html += "<td><input id='quantityInput' type='number' min='1' value='1' /></td>";
    
    table.innerHTML = html;
    
    let toppingsTable = document.querySelector("#pizzaOptions");
    html = "<tr>";
    for(let i=0; i<toppings.length; i++) {
        html += "<td>" + toppings[i].name + "</td>";
    }
    html += "</tr>";
    
    toppingsTable.innerHTML = html;
}

function selectToppings(e) {
    if(e.target.classList.contains("highlighted")) {
        e.target.classList.remove("highlighted");
    }
    else {
        e.target.classList.add("highlighted");
    }
}

function startOrder() {
    let price = 0;
    let hst = 0;
    let total = 0;
    let size = document.querySelector("#sizeSelect").value;
    let crust = document.querySelector("#crustSelect").value;
    let tops = document.querySelector("#pizzaOptions").querySelectorAll(".highlighted");
    let delivery = document.querySelector("#deliveryInput").value;
    let quantity = document.querySelector("#quantityInput").value;
    let orderToppings = [];
    for(let i = 0; i<tops.length; i++) {
        orderToppings[i] = tops[i].innerHTML;
    }
    for(let i=0; i<sizes.length; i++) {
        if(sizes[i].name === size) {
            size = sizes[i];
            break;
        }
    }
    for(let i=0; i<crusts.length; i++) {
        if(crusts[i].name === crust) {
            crust = crusts[i];
            break;
        }
    }
    let topObjs = [];
    for(let i=0; i<toppings.length; i++) {
        for(let x = 0; x<orderToppings.length; x++) {
            if(toppings[i].name === orderToppings[x]) {
                topObjs[x] = toppings[i]; 
                price += topObjs[x].price;
            }
        }
        
    }
    
    price += size.price;
    price += crust.price;
    price = price.toFixed(2);
    price = price * Number(quantity);
    hst = price * 0.15;
    hst = hst.toFixed(2);
    total = Number(price) + Number(hst);
    total = total.toFixed(2);
    
    let obj = {
        "size" : size,
        "crust" : crust,
        "toppings" : topObjs,
        "price" : price,
        "hst" : hst,
        "total" : total,
        "delivery" : delivery,
        "quantity" : quantity
    };
    order = obj;
    
    buildOrderTable(obj);
}

function buildOrderTable(obj) {
    let table = document.querySelector("#currentOrder");
    let html = "<tr>";
    html += "<th>Pizza Size</th><th>Crust Type</th><th>Toppings</th><th>Subtotal</th><th>HST</th><th>Total</th><th>Delivery</th><th>Quantity</th>";
    html += "</tr><tr>";
    html += "<td>" + obj.size.name + "</td>";
    html += "<td>" + obj.crust.name + "</td>";
    html += "<td>";
    for(let i=0; i<obj.toppings.length; i++) {
        if(i !== 0) {
            html += ", " + obj.toppings[i].name;
        }
        else {
            html += obj.toppings[i].name;
        }
    }
    html += "</td>";
    html += "<td>" + "$" + obj.price + "</td>";
    html += "<td>" + "$" + obj.hst + "</td>";
    html += "<td>" + "$" + obj.total + "</td>";
    html += "<td>" + obj.delivery + "</td>";
    html += "<td>" + obj.quantity + "</td>";
    html += "</tr>";
    
    table.innerHTML = html;
    
    table.classList.remove("hidden");
    document.querySelector("#orderLabel").classList.remove("hidden");
    document.querySelector("#submitOrder").classList.remove("hidden");
    document.querySelector("#editOrder").classList.remove("hidden");
}

function editOrder() {
    document.querySelector("#currentOrder").innerHTML = "";
    document.querySelector("#orderLabel").classList.add("hidden");
    document.querySelector("#submitOrder").classList.add("hidden");
    document.querySelector("#editOrder").classList.add("hidden");
}

function submitOrder() {
    let orderObj = {
        "amountPaid" : 0,
        "customer_id" : 1,
        "delivery" : order.delivery,
        "hst" : order.hst,
        "subTotal" : order.price,
        "orderTotal" : order.total,
        "orderStatus" : "PENDING"
    };
    let url = "ManageOrders/orders";
    let method = "POST";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete add request");
            } else {
                let data = JSON.parse(resp);
                createPizza(data);
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(orderObj));
}

function createPizza(data) {
    let pizzaObj = {
        "order_id" : data.order_id,
        "pizzaSize_id" : order.size.pizzaSize_id,
        "pizzaCrust_id" : order.crust.pizzaCrust_id,
        "quantity" : order.quantity,
        "priceEach" : order.price / 2,
        "totalPrice" : order.total
    };
    let url = "ManagePizzas/pizzas";
    let method = "POST";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete add request");
            } else {
                alert("added Successfully");
                let data = JSON.parse(resp);
                for(let i =0; i<order.toppings.length; i++) {
                    createToppingMap(data, i);
                }
                alert("Thank you for your purchase!");
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(pizzaObj));
}

function createToppingMap(data, num) {
    let mapObj = {
        "pizza_id" : data.pizza_id,
        "pizzaTopping_id" : order.toppings[num].pizzaTopping_id
    };
    let url = "ManagePizzaTopping_Maps/pizzaTopping_Maps";
    let method = "POST";
    let xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            let resp = xmlhttp.responseText.trim();
            if (resp.search("ERROR") >= 0 || resp !== "true") {
                alert("could not complete add request");
            } else {
                getSizes();
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send(JSON.stringify(mapObj));
}

function logout() {
    window.location.href = "index.html";
}



