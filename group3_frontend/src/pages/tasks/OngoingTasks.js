import React, { Component } from "react";
import "./OngoingTasks.css";
import "./AddTask";
import "./MarkTaskAsFinished";
import axios from 'axios';
import decode from 'jwt-decode';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';


class OngoingTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],

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
                });
            })
    }

    renderOngoingTasks() {

        return this.state.tasks.map(taskItem => {
            return (
                <tr className="line">
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.description}</td>
                    <td><Moment format="YYYY/MM/DD">
                        {taskItem.startDate}
                    </Moment></td>
                    <td><Moment format="YYYY/MM/DD">
                        {taskItem.taskDeadline}
                    </Moment></td>
                    <td>
                        <a href="#">
                            <i class="glyphicon glyphicon-plus" />
                        </a>
                    </td>
                    <td>
                        <a href="/marktaskfinished" className="btn btn-primary" role="button">
                            Mark finish
                    </a>
                    </td>
                </tr>
            );
        });
    }


    render() {

        try {
            return (
                <div className=" table-striped">
                    <h3>
                        <b>Ongoing Tasks</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
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
        catch (error) {
            return (<Error message="NOT AUTHORIZED!" />)
        }
    }
}

export default OngoingTasks;
