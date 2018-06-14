import React, { Component } from 'react';
import './ActiveProjects.css';
import { Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import MediumButton from '../../components/button/mediumButton.jsx';
import ProjectsTable from '../../components/projectsTable/ProjectsTable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {getActiveProjects, getUserProjects} from '../../actions/projectActions';

class ActiveProjects extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
        this.props.getActiveProjects(this.AuthService.getUserId());
    }

    render() {
        if (this.props.errorMessage) {
            return <Error message={this.props.errorMessage} />;
        } else {
            return <ProjectsTable projects={this.props.projects} />;
        }
    }
}

export const mapStateToProps = state => {
    const projects = state.projects.activeProjects || [];
    const errorMessage =
        state.projects.activeProjects && state.projects.activeProjects.error;
    return { projects, errorMessage };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ getActiveProjects }, dispatch);
};

export default  connect(
    mapStateToProps,
    mapDispatchToProps
)(ActiveProjects);
