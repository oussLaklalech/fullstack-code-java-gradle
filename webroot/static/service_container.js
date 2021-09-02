'use strict';

/**
 *
 * REACT COMPONENT.
 */
const e = React.createElement;

/**
 * Table Component.
 */
class ServiceContainer extends React.Component {
    /**
     * Constructor.
     * @param props
     */
    constructor(props) {
        super(props);
        this.state = {
            services: []
        }

        this.fetchTheListOfServices();
    }

    /**
     * renderTableHeader.
     * @returns {unknown[]}
     */
    renderTableHeader() {
        let header = [];

        if (this.state.services[0]) {
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
            const {rowid, name, url, createdAt} = service
            return (
                <tr key={rowid}>
                    <td>{rowid}</td>
                    <td>{name}</td>
                    <td>{url}</td>
                    <td>{createdAt}</td>
                    <td>
                        <button className="btn btn-primary" type="button"
                                onClick={(e) => this.removeItem(rowid, e)}>Delete
                        </button>
                        <button data-toggle="modal" data-target="#myModal" type="button"
                                className="btn btn-info edit-btn"
                                onClick={(e) => this.prefilledUpdateForm(service, e)}>Edit
                        </button>
                    </td>
                </tr>
            )
        })
    }

    /**
     * Fill the update form.
     * @param service
     */
    prefilledUpdateForm(service) {
        document.querySelector('#service-name-update').value = service.name;
        document.querySelector('#service-url-update').value = service.url;
        document.querySelector('#service-rowid-update').value = service.rowid;
    }

    /**
     * Remove service.
     * @param id
     */
    removeItem(id) {
        let that = this;
        fetch('/service', {
            method: 'delete',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({rowid: id})
        }).then(res => {
            if(res.status < 400) {
                this.fetchTheListOfServices();
                showAlert('#alert-success', 'The service has been removed successfully');
            } else {
                showAlert('#alert-danger', 'Error occurred while trying to remove the service');
            }

        }).catch(err => {
            showAlert('#alert-danger', 'Error occurred while trying to remove the service');
        });
    }

    /**
     * Remove service.
     * @param id
     */
    saveItem(id) {
        let serviceName = document.querySelector('#service-name').value;
        let serviceUrl = document.querySelector('#service-url').value;

        let that = this;
        fetch('/service', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name: serviceName, url: serviceUrl})
        }).then(res => {
            if(res.status < 400) {
                // refresh list services
                this.fetchTheListOfServices();
                showAlert('#alert-success', 'The service has been added successfully');
            } else {
                showAlert('#alert-danger', 'Error occurred while trying to add a new service');
            }

        }).catch(err => {
            showAlert('#alert-danger', 'Error occurred while trying to add a new service');
        });
    }


    /**
     * Get list of all services.
     *
     * @param that
     */
    fetchTheListOfServices() {
        let that = this;
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
                        <th> ACTIONS</th>
                    </tr>
                    {this.renderTableData()}
                    </tbody>
                </table>
                <div className="row">
                    <div className="col-md-8">
                        <h2>Create a new service</h2>
                        <div id="create-service">
                            <label htmlFor="service-name">Name</label>
                            <input className="form-control" type="text" id="service-name"/>
                            <label htmlFor="service-url">URL</label>
                            <input className="form-control" type="text" id="service-url" name="furl"/>

                            <button className="btn btn-info" id="post-service"
                                    onClick={(e) => this.saveItem(e)}>Save
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}
const domContainer = document.querySelector('#service_container');
ReactDOM.render(e(ServiceContainer), domContainer);