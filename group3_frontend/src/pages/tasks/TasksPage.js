import React, { Component } from 'react';
import './TasksPage.css';
import axios from 'axios';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import AuthService from '../loginPage/AuthService';

class TasksPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
        this.Auth = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        this.Auth.fetch('http://localhost:8080/users/7/tasks/pending', { method: 'get' })
            .then((responseData) => {
                this.setState({
                    tasks: responseData,
                });
            });

    }

    renderOngoingTasks() {
        return this.state.tasks.map((taskItem) => {
            return (
                <tr className="line">
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.description}</td>
                    <td>{taskItem.startDate}</td>
                    <td>{taskItem.taskDeadline}</td>
                    <td><a href="#"><i class="glyphicon glyphicon-plus"></i></a></td>
                </tr>
            )
        })
    }

    handleChange = event => {
        this.setState({ id: event.target.value });
    }

    handleSubmit = async event => {
        event.preventDefault();

        // Value of id is inside of the response const.
        const response = await axios.patch(`projects/2/tasks/${this.state.id}`);
        console.log(response);
        console.log(response.data);
    };

    render() {
        return (

            <div className=" table-striped">
                <h3><b>Ongoing Tasks</b></h3>
                <table className="table table-hover">

                    <thead>
                        <tr>
                            <th>Task ID</th>
                            <th>Description</th>
                            <th>Start Date</th>
                            <th>Estimated Finish Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.renderOngoingTasks()}
                    </tbody>
                </table>
                <form onSubmit={this.handleSubmit}>
                    <button className="btn btn-primary">Add Task</button>

                    <FormGroup controlId="id" bsSize="large">

                    </FormGroup>
                </form>
            </div>

        );
    }
}

export default TasksPage;