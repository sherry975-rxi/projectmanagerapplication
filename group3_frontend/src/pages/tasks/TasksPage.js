import React, { Component } from 'react';
import './TasksPage.css';
import axios from 'axios';
import { AddTask } from './AddTask.js';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class TasksPage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    //TODO: Add sort by ascending or descending order to these tables

    async componentDidMount() {
        fetch('users/7/tasks/pending', { method: 'get' })
            .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    tasks: responseData,
                });
            });

    }


    async componentDiMount(){
        fetch('users/7/tasks/finished', { method: 'get'})
            .then((response) => response.json())
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

    renderFinishedTasks(){
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
                
                <h3><b>Finished Tasks</b></h3>
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
                        {this.renderFinishedTasks()}
                    </tbody>
                </table>
            </div>

        );
    }
}

export default TasksPage;