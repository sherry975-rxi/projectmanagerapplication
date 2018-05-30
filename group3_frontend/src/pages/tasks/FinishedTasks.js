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
            project: {}
        };

        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch("users/"+this.AuthService.getUserId()+"/tasks/finished", { method: "get" })
            .then(responseData => {
                console.log(responseData)
                this.setState({
                    tasks: responseData,
                });
            });
    }

    renderFinishedTasks() {

        return this.state.tasks.map(taskItem => {
            return (
                <tr className="line">
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.project}</td>
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
        try {
            return (
                <div className=" table-striped">
                    <h3>
                        <b>Finished Tasks</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Task ID</th>
                                <th>Project ID</th>
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
        catch (error) {
            return (<Error message="NOT AUTHORIZED!" />)
        }
    }
}


export default FinishedTasks;
