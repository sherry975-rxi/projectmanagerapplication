import React, { Component } from "react";
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from "./MarkTaskAsFinished";
import CreateReport from "../reports/CreateReport";
import Reports from "../reports/Reports"
import { Link } from "react-router-dom";
import MediumButton from './../../components/button/mediumButton';
import AuthService from './../loginPage/AuthService';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx'
import { handleTaskHeaders } from '../../components/utils/handleList'


class Test extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            project: {}
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService()
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        this.refreshPage();
    }

    async refreshPage() {

        this.AuthService.fetch(`/users/${this.AuthService.getUserId()}/tasks/pending`, { method: 'get' })
            .then((responseData) => {
                console.log(responseData);
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                });
            })

    }



    render() {

        if (this.state.message != null) {
            return (<Error message={this.state.message} />)
        }
        else {
            return (
                <AccordionMenu list={this.state.tasks} type='Finished' />
            )
        }
    }
}

export default Test;
