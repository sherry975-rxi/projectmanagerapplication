import React, { Component } from "react";
import "./OngoingTasks.css";
import "./AddTask";
import "./MarkTaskAsFinished";
import axios from 'axios';
import decode from 'jwt-decode';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from "./MarkTaskAsFinished"; 
import { Link } from "react-router-dom";


class OngoingTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            empty: "",
            project: {}

        };
        this.AuthService = new AuthService()
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        this.AuthService.fetch(`/users/${this.props.match.params.userID}/tasks/pending`, { method: 'get' })
            .then((responseData) => {
                console.log(responseData)
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                });
            })
    }

    renderOngoingTasks() {

        return this.state.tasks.map(taskItem => {
            return (
                <tr className="line">
                    <td>{taskItem.project}</td>
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.description}</td>
                    <td><Moment format="YYYY/MM/DD">
                        {taskItem.startDate}
                    </Moment></td>
                    <td><Moment format="YYYY/MM/DD">
                        {taskItem.taskDeadline}
                    </Moment></td>
                    
                    <td>
                        <MarkTaskAsFinished id={taskItem.taskID} />
                    </td>
                    <Link
                        to={"/createreport/" + this.state.tasks.taskID}
                        activeClassName="active"
                    >
                        <button className="btn btn-warning">
                            Create Report
                    </button>
                    </Link>{" "}
                    &nbsp;
                </tr>
            );
        });
    }


    render() {


        if (this.state.message != null) {
            return (<Error message={this.state.message} />)
        }
        else {
            return (
                <div className=" table-striped">
                    <h3>
                        <b>Ongoing Tasks</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Task ID</th>
                                <th>Description</th>
                                <th>Start Date</th>
                                <th>Estimated Finish Date</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderOngoingTasks()}</tbody>
                    </table>
                    <a href="/addTask" className="btn btn-primary" role="button">
                        Add task
                     </a>
                </div>)
        }
    }
}

export default OngoingTasks;
