import React, { Component } from 'react';
import './TasksPage.css';
import axios from 'axios';

class FinishedTasks extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    
    async componentDidMount(){
        fetch('users/7/tasks/finished', { method: 'get'})
            .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    tasks: responseData,
                });
            });
     }  
    
    renderFinishedTasks() {
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

export default FinishedTasks;


    
    
    
