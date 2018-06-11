import React, { Component } from 'react';
import './OngoingTasks.css';
import './AddTask';
import './MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import Error from './../../components/error/error';
import AccordionMenuProjects from './../../components/accordianMenuProjects/AccordionMenuProjects';

class Test extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: []
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        this.refreshPage();
    }

    async refreshPage() {
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
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return <AccordionMenuProjects list={this.state.projects} />;
        }
    }
}

export default Test;
