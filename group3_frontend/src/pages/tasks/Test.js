import React, { Component } from 'react';
import Error from './../../components/error/error';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import ProjectsTable from '../../components/projectsTable/ProjectsTable';

class Test extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            message: ''
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        this.refreshPage();
    }

    async refreshPage() {
        console.log('blvbpsdjf');
        this.AuthService.fetch(
            `/projects/${this.AuthService.getUserId()}/myProjects`,
            { method: 'get' }
        ).then(responseData => {
            console.log(responseData);
            this.setState({
                projects: responseData,
                message: responseData.error
            });
        });
    }

    render() {
        console.log(this.state.projects);
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return <ProjectsTable projects={this.state.projects} />;
        }
    }
}

const mapStateToProps = state => {
    return { filter: state.filterReducer.filterType };
};
export default connect(
    mapStateToProps,
    null
)(Test);
