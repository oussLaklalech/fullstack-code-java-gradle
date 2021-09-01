'use strict';

/**
 *
 * REACT COMPONENT.
 */
const e = React.createElement;

class Table extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            services: []
        }

        var that = this;
        let servicesRequest = new Request('/service');
        fetch(servicesRequest)
            .then(function (response) {
                return response.json();
            })
            .then(function (serviceList) {
                that.setState({services: serviceList});
                // serviceList.forEach(service => {
                //     var li = document.createElement("li");
                //     li.appendChild(document.createTextNode(service.name + ': ' + service.status));
                //
                //     var removeBtn = document.createElement("BUTTON");
                //     removeBtn.appendChild(document.createTextNode("Remove"));
                //     li.appendChild(removeBtn);
                //
                //     removeBtn.onclick = evt => {
                //         fetch('/service', {
                //             method: 'delete',
                //             headers: {
                //                 'Accept': 'application/json, text/plain, */*',
                //                 'Content-Type': 'application/json'
                //             },
                //             body: JSON.stringify({rowid: service.rowid})
                //         }).then(res => location.reload());
                //     }
                //
                //     listContainer.appendChild(li);
                // });
            });
    }

    /**
     * renderTableHeader.
     * @returns {unknown[]}
     */
    renderTableHeader() {
        let header = [];

        if(this.state.services[0]) {
            header = Object.keys(this.state.services[0]);
        }
        return header.map((key, index) => {
            return <th key={index}>{key.toUpperCase()}</th>
        });
    }

    /**
     * renderTableData.
     * @returns {unknown[]}
     */
    renderTableData() {
        return this.state.services.map((service, index) => {
            const { rowid, name, url, status, createdAt } = service //destructuring
            return (
                <tr key={rowid}>
                    <td>{rowid}</td>
                    <td>{name}</td>
                    <td>{url}</td>
                    <td>{status}</td>
                    <td>{createdAt}</td>
                    <td>
                        <button type="button" onClick={(e) => this.removeItem(rowid, e)} >Delete</button>
                    </td>
                </tr>
            )
        })
    }

    removeItem(id) {
        fetch('/service', {
            method: 'delete',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({rowid: id})
        }).then(res => location.reload());
    }

    /**
     * Render.
     * @returns {JSX.Element}
     */
    render() {
        return (
            <div>
                <h2>List of services</h2>
                <table id='services'>
                    <tbody>
                    <tr>
                        {this.renderTableHeader()}
                        <th> ACTIONS </th>
                    </tr>
                    {this.renderTableData()}
                    </tbody>
                </table>
            </div>
        )
    }
}

const domContainer = document.querySelector('#service_container');
ReactDOM.render(e(Table), domContainer);