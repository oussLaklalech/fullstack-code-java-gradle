'use strict';


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
 * Call each 5 seconds.
 */
setInterval(fetchListOfRequestResponse, 5000);

const saveButton = document.querySelector('#post-service');
saveButton.onclick = evt => {
    let serviceName = document.querySelector('#service-name').value;
    let serviceUrl = document.querySelector('#service-url').value;

    fetch('/service', {
        method: 'post',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: serviceName, url: serviceUrl})
    }).then(res => location.reload());
}

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
        body: JSON.stringify({rowid: serviceRowid, name: serviceName, url: serviceUrl})
    }).then(res => console.log(res));
}

