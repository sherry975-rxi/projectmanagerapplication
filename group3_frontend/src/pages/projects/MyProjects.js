import React, { Component } from 'react';
import './MyProjects.css';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import ProjectsTable from '../../components/projectsTable/ProjectsTable';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { getUserProjects } from '../../actions/projectActions';

class MyProjects extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
        this.props.getUserProjects(this.AuthService.getUserId());
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
    const projects = state.projects.userProjects || [];
    const errorMessage =
        state.projects.userProjects && state.projects.userProjects.error;
    return { projects, errorMessage };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ getUserProjects }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MyProjects);
