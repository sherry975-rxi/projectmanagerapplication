import React, { Component } from 'react';
import './OngoingTasks.css';
import './AddTask';
import './MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from './MarkTaskAsFinished';
import { Link } from 'react-router-dom';
import MediumButton from './../../components/button/mediumButton';

class OngoingTasks extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            project: {}
        };

        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    componentDidMount() {
        this.refreshPage();
    }

    refreshPage = () => {
        this.AuthService.fetch(
            `/users/${this.AuthService.getUserId()}/tasks/pending`,
            { method: 'get' }
        ).then(responseData => {
            this.setState({
                tasks: responseData,
                message: responseData.error
            });
        });
    };

    renderOngoingTasks() {
        return this.state.tasks.map((taskItem, index) => {
            return (
                <tr className="line" key={index}>
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.project}</td>
                    <td>{taskItem.description}</td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {taskItem.startDate}
                        </Moment>
                    </td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {taskItem.taskDeadline}
                        </Moment>
                    </td>
                    <td>
                        <MarkTaskAsFinished
                            id={taskItem.taskID}
                            project={taskItem.project}
                            onClick={this.refreshPage}
                        />
                    </td>
                    <td>
                        <Link
                            to={
                                '/projects/' +
                                taskItem.project +
                                '/tasks/' +
                                taskItem.taskID +
                                '/createreport'
                            }
                        >
                            <MediumButton text="Create Report" />
                        </Link>
                    </td>
                    <td>
                        <Link
                            to={
                                '/projects/' +
                                taskItem.project +
                                '/tasks/' +
                                taskItem.taskID +
                                '/reports'
                            }
                        >
                            <MediumButton text="View Reports" />
                        </Link>
                    </td>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return (
                <div className=" table-striped">
                    <h3>
                        <b>Ongoing Tasks</b>
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
                        <tbody>{this.renderOngoingTasks()}</tbody>
                    </table>
                    <Link to={'/addtask/'}>
                        <MediumButton text="Add Task" />
                    </Link>
                    &nbsp;
                </div>
            );
        }
    }
}

export default OngoingTasks;
