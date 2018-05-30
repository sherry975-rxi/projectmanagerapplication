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
        this.setState({
            email: this.AuthService.getProfile().sub        
        }, () => {
            this.fetchUserData()        
        })

    
        
    }

     
    async fetchUserData(){

        this.AuthService.fetch(`/users/email/` + this.state.email, { method: 'get' })
        .then((responseData) => { this.setState({
            userID:   responseData[0]['userID'],
            
        }, () => {
            this.fetchTasks()        
        })
          
        })
    }

    async fetchTasks(){

        this.AuthService.fetch("users/" + this.state.userID + "/tasks/sortedbydeadline", { method: "get" })
        .then(responseData => {
            this.setState({
                tasks: responseData,
            });
        });
    }

  
   



    render(){

        console.log(this.state.userID)

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
                    </div>
                );
            })

        )
        
      
    }


}

export default TaskGraph