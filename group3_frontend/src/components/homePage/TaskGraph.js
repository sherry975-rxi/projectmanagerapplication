import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import Moment from 'react-moment';
import { Line, Circle } from 'rc-progress';



class TaskGraph extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {}
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.setState({
            email: this.AuthService.getProfile().sub
        
        }, () => {
            this.fetchUserTasksData()
        })
    }

    
    fetchUserTasksData(){
        
        this.AuthService.fetch("users/7/tasks/sortedbydeadline", { method: "get" })
            .then(responseData => {
                console.log(responseData)
                this.setState({
                    tasks: responseData,
                });
            });

    }



    render(){
        return this.state.tasks.map(taskItem => {
            return (
                <div className="GrahpReturn">
                  <Line percent="10" strokeWidth="4" strokeColor="#D3D3D3" />

                    <tr className="line">
                        <td><Moment format="YYYY/MM/DD">
                            {taskItem.taskDeadline}
                        </Moment></td>
                        
                    </tr>
                </div>
            );
        });
    }


}

export default TaskGraph