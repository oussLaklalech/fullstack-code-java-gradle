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
            const { rowid, name, url, createdAt } = service
            return (
                <tr key={rowid}>
                    <td>{rowid}</td>
                    <td>{name}</td>
                    <td>{url}</td>
                    <td>{createdAt}</td>
                    <td>
                        <button className="btn btn-primary"  type="button" onClick={(e) => this.removeItem(rowid, e)} >Delete</button>
                        <button data-toggle="modal" data-target="#myModal" type="button" className="btn btn-info edit-btn"
                                onClick={(e) => this.prefilledUpdateForm(service, e)}>Edit</button>
                    </td>
                </tr>
            )
        })
    }

    prefilledUpdateForm(service) {
        document.querySelector('#service-name-update').value = service.name;
        document.querySelector('#service-url-update').value = service.url;
        document.querySelector('#service-rowid-update').value = service.rowid;
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