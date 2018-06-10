import React, { Component } from 'react';
import { Button, Glyphicon } from 'react-bootstrap';
import './AllRequests.css';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';

class AllRequests extends Component {
    constructor(props) {
        super(props);
        this.state = {
            requests: [],
            message: ''
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch('/projects/2/tasks/WP1.T01/requests', {
            method: 'get'
        }).then(responseData => {
            this.setState({
                requests: responseData,
                message: responseData.error
            });
        });
    }

    renderRequests() {
        return this.state.requests.map(requestsItem => {
            return (
                <tr className="line">
                    <td>{requestsItem.task.description}</td>
                    <td>{requestsItem.projCollab.collaborator.name}</td>
                    <td>{requestsItem.type}</td>

                    <Button>
                        <Glyphicon className="ok-button" glyph="ok" />
                    </Button>
                    <Button>
                        <Glyphicon className="remove-button" glyph="remove" />
                    </Button>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message !== '') {
            return (
                <div className="Requests">
                    <h3>
                        <b>List of All Requests</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Task</th>
                                <th>Collaborator Name</th>
                                <th>Request Type</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderRequests()}</tbody>
                    </table>
                </div>
            );
        } else {
            return <Error message={this.state.message + ' NOT AUTHORIZED!'} />;
        }
    }
}

export default AllRequests;
