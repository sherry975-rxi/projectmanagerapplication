import React, { Component } from 'react';
import './MyProjects.css';
import { Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import MediumButton from './../../components/button/mediumButton';

class MyProjects extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            message: ''
        };

        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.AuthService.fetch(
            `/projects/${this.AuthService.getUserId()}/myProjects`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                projects: responseData,
                message: responseData.error
            });
        });
    }

    getManagerButton(pmEmail, projId) {
        let buttons = '';
        if (pmEmail == this.AuthService.getProfile().sub) {
            buttons = (
                <td>
                    <Link to={'/projectdetails/' + projId}>
                        <MediumButton text="Edit" />
                    </Link>
                </td>
            );
        }
        return buttons;
    }

    renderProjects() {
        return this.state.projects.map((projectItem, index) => {
            return (
                <tr className="line" key={index}>
                    <td>{projectItem.projectId}</td>
                    <td>{projectItem.name}</td>
                    <td>{projectItem.description}</td>
                    <td>{projectItem.projectManager.name}</td>
                    <td>{projectItem.projectManager.email}</td>
                    <td>
                        <Link to={'/projectdetails/' + projectItem.projectId}>
                            <MediumButton text="Details" />
                        </Link>
                    </td>
                    {this.getManagerButton(
                        projectItem.projectManager.email,
                        projectItem.projectId
                    )}
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return (
                <div className="ActiveProjects">
                    <h3>
                        <b>Active Projects</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Name</th>
                                <th>Description Date</th>
                                <th>Project Manager Name</th>
                                <th>Project Manager Email</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderProjects()}</tbody>
                    </table>
                </div>
            );
        }
    }
}

export default MyProjects;
