'use strict';

const listContainer = document.querySelector('#service-list');
let servicesRequest = new Request('/service');
fetch(servicesRequest)
    .then(function (response) {
        return response.json();
    })
    .then(function (serviceList) {
        serviceList.forEach(service => {
            var li = document.createElement("li");
            li.appendChild(document.createTextNode(service.name + ': ' + service.status));

            var removeBtn = document.createElement("BUTTON");
            removeBtn.appendChild(document.createTextNode("Remove"));
            li.appendChild(removeBtn);

            removeBtn.onclick = evt => {
                fetch('/service', {
                    method: 'delete',
                    headers: {
                        'Accept': 'application/json, text/plain, */*',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({rowid: service.rowid})
                }).then(res => location.reload());
            }

            listContainer.appendChild(li);
        });
    });

const saveButton = document.querySelector('#post-service');
saveButton.onclick = evt => {
    let serviceName = document.querySelector('#service-name').value;
    let serviceUrl = document.querySelector('#service-url').value;
    let serviceStatus = document.querySelector('#service-status').value;

    fetch('/service', {
        method: 'post',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: serviceName, url: serviceUrl, status: serviceStatus})
    }).then(res => location.reload());
}