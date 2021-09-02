'use strict';

$(".alert").hide();
fetchListOfRequestResponse();

/**
 * Fetch the list of responses and add to HTML list.
 */
function fetchListOfRequestResponse() {
    const listContainer = document.querySelector('#response-list');
    listContainer.innerHTML = "";

    let servicesRequest = new Request('/request-response');
    fetch(servicesRequest)
        .then(function (response) {
            return response.json();
        })
        .then(function (responseList) {
            responseList.forEach(response => {
                let li = document.createElement("li");
                li.appendChild(document.createTextNode('[' + response.status + '] ' + response.url));
                li.classList.add('list-group-item');
                listContainer.appendChild(li);
            });
        });
}

/**
 * Call every 10 seconds.
 */
setInterval(fetchListOfRequestResponse, 10000);

/**
 * On click on update.
 *
 * @type {Element}
 */
const updateButton = document.querySelector('#put-service-update');
updateButton.onclick = evt => {
    let serviceRowid = document.querySelector('#service-rowid-update').value;
    let serviceName = document.querySelector('#service-name-update').value;
    let serviceUrl = document.querySelector('#service-url-update').value;

    fetch('/service', {
        method: 'put',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({rowid: parseInt(serviceRowid), name: serviceName, url: serviceUrl})
    }).then(res => {
        if(res.status < 400) {
            $('#myModal').modal('hide');
            showAlert('#alert-success', 'The service has been edited successfully');
            location.reload();
        } else {
            showAlert('#alert-danger', 'Error occurred while trying to add a new service');
        }
    }).catch(err => {
        showAlert('#alert-danger', 'Error occurred while trying to edit the service');
    });
}

/**
 * Show Alert.
 * @param id
 * @param message
 */
function showAlert(id, message) {
    document.querySelector(id).innerHTML = '';
    document.querySelector(id).appendChild(document.createTextNode(message));
    $(id).show();
    setTimeout(() => $(id).hide(), 3000);
}