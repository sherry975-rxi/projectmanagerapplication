import React, { Component } from 'react';
import './OngoingTasks.css';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';


class ProjectAllTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {}
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.refreshPage();
    }

    async refreshPage()  {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/all`,
            { method: 'GET' }
        ).then(responseData => {
            this.setState({
                tasks: responseData,
                message: responseData.error
            });
        });
    }

    render() {

        if (this.state.message != null) {
            return (<Error message={this.state.message} />)
        }
        else {
            return (
                <AccordionMenu list={this.state.tasks} />
            )
        }
    }
}

export default ProjectAllTasks;
