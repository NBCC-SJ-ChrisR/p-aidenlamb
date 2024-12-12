window.onload = function() {
    document.querySelector("#customers").addEventListener("click", startCustLogin);                                                                                                                
    document.querySelector("#employees").addEventListener("click", startEmpLogin);
    document.querySelector("#empSubmit").addEventListener("click", employeeSubmit);
    document.querySelector("#custSwitch").addEventListener("click", switchToCustomer);
    document.querySelector("#custSubmit").addEventListener("click", customerSubmit);
    document.querySelector("#empSwitch").addEventListener("click", switchToEmployee);
};

function startCustLogin() {
    document.querySelector("#customers").classList.add("hidden");
    document.querySelector("#employees").classList.add("hidden");
    document.querySelector("#custContent").classList.remove("hidden");
    document.querySelector("#custContent").classList.add("shown");
}

function startEmpLogin() {
    document.querySelector("#customers").classList.add("hidden");
    document.querySelector("#employees").classList.add("hidden");
    document.querySelector("#empContent").classList.remove("hidden");
    document.querySelector("#empContent").classList.add("shown");
}


function switchToCustomer() {
    document.querySelector("#empContent").classList.remove("shown");
    document.querySelector("#empContent").classList.add("hidden");
    document.querySelector("#custContent").classList.remove("hidden");
    document.querySelector("#custContent").classList.add("shown");
    
}

function employeeSubmit() {
    let username = document.querySelector("#empUserName").value;
    let password = document.querySelector("#empPassword").value;
    let url = "ManageEmployees/employees/" + username;
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
                    alert("Username or Password is incorrect");
                    console.log(resp);
                } else {
                    let info = JSON.parse(resp);
                    if(info.password === password) {
                        moveToEmployeePage();
                    }
                    else {
                        alert("Username or Password is incorrect");
                    }
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
    
}
function customerSubmit() {
    let email = document.querySelector("#custEmail").value;
    let password = document.querySelector("#custPassword").value;
    let url = "ManageCustomers/customers/" + email;
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
                    alert("Email or Password is incorrect");
                    console.log(resp);
                } else {
                    let info = JSON.parse(resp);
                    console.log(info);
                    if(info.password === password) {
                        moveToCustomerPage();
                    }
                    else {
                        alert("Email or Password is incorrect");
                    }
                }
            }
        }
    };
    xmlhttp.open(method, url, true);
    xmlhttp.send();
    
}

function switchToEmployee() {
    document.querySelector("#custContent").classList.remove("shown");
    document.querySelector("#custContent").classList.add("hidden");
    document.querySelector("#empContent").classList.remove("hidden");
    document.querySelector("#empContent").classList.add("shown");
}

function moveToEmployeePage() {
    window.location.href = "employee.html";
}

function moveToCustomerPage() {
    window.location.href = "customer.html";
}



