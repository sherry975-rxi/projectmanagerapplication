import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import Moment from 'react-moment';
import ProgBar from './ProgBar';
import momentus from 'moment';




class TaskGraph extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {},
            percent: 0,
            actualDate: new Date(),
            userID: '',

        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch(`/users/${this.AuthService.getUserId()}/tasks/sortedbydeadline`, {
            method: "get"
        })
            .then(responseData => {
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                });
            });
    }

  

  


    render(){


        return (

            
            this.state.tasks.map(taskItem => {
                const today = momentus(this.state.actualDate)
                const taskDeadline = momentus(taskItem.taskDeadline)
                var difference = taskDeadline.diff(today, 'days');
                var deadlineIsOver = difference
                
             
                
                if(difference < 0) {
                    difference = 100;
                    deadlineIsOver = "0"
                }

                return (
                    
                    <div className="GraphContainer">
                        <ProgBar limit={difference}/>
                        <table>
                        <tbody>
                        <tr className="line">
                            <td className="tdGraphStyle">Task Deadline:<Moment format="YYYY/MM/DD">
                                
                               {taskItem.taskDeadline} 
                            </Moment></td>
                            <td className="tdGraphStyleEnd">Days left until deadline:  {deadlineIsOver}</td>

                        </tr>
                        <tr>
                            <td className="tdGraphStyle">Description: {taskItem.description}</td>
                        
                        </tr>
                        <tr>
                            <td className="tdGraphStyle">Project ID: {taskItem.project}  </td>
                            
                        </tr>
                        </tbody>
                        </table>
                    </div>
                );
            })

        )
        
      
    }


}

export default TaskGraph