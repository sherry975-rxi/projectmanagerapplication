import React, { Component } from "react";
import "./OngoingTasks.css";
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';


class FinishedTasks extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            message: ""
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch("users/7/tasks/finished", { method: "get" })
            .then(responseData => {
                console.log(responseData)
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                });
            });
    }

    renderFinishedTasks() {
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
                        <b>Finished Tasks</b>
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
                        <tbody>{this.renderFinishedTasks()}</tbody>
                    </table>
                </div>
            );
        }
    }

}

export default FinishedTasks;
