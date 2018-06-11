import React, { Component } from "react";
import "./OngoingTasks.css";
import "./AddTask";
import "./MarkTaskAsFinished";
import "../reports/Reports";
import axios from 'axios';
import decode from 'jwt-decode';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from "./MarkTaskAsFinished";
import CreateReport from "../reports/CreateReport";
import Reports from "../reports/Reports"
import { Link } from "react-router-dom";
import MediumButton from './../../components/button/mediumButton';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';


class Test extends Component {
    constructor(props) {
        super(props);
        this.match;
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
                <AccordionMenu list={this.state.tasks} />
            )
        }
    }
}

export default Test;
