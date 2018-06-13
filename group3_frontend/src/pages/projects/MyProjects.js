import React, { Component } from 'react';
import './MyProjects.css';
import { Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import MediumButton from './../../components/button/mediumButton';
import ProjectsTable from '../../components/projectsTable/ProjectsTable';

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
        if (pmEmail === this.AuthService.getProfile().sub) {
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

    //Create task button will be added to the accordion menu when implemented

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
                    <td>
                        <Link
                            to={
                                '/projects/' +
                                projectItem.projectId +
                                '/addtask'
                            }
                        >
                            <MediumButton text="Create task" />
                        </Link>
                    </td>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return <ProjectsTable projects={this.state.projects} />;
        }
    }
}

export default MyProjects;
