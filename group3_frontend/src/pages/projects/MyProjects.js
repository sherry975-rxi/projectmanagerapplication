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
    
    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return <ProjectsTable projects={this.state.projects} />;
        }
    }
}

export default MyProjects;
