import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import Moment from 'react-moment';
import { Line, Circle } from 'rc-progress';
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
            color: '#000000',
            trailWidth: 0.4,
            actualDate: new Date()
        };
        this.AuthService = new AuthService();
        this.increase = this.increase.bind(this);
    }

    async componentDidMount() {
        this.setState({
            email: this.AuthService.getProfile().sub
        
        }, () => {
            this.fetchUserTasksData()
        })

        this.increase();
        
    }

  

      increase() {
        const percent = this.state.percent + 1;
        const colorMap = ['#3FC7FA', '#85D262', '#FE8C6A'];
        if (percent >= 30) {
          clearTimeout(this.tm);
          return;
        }
        this.setState(
            { percent
            });
        this.tm = setTimeout(this.increase, 10);
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
        return (
            
            
            this.state.tasks.map(taskItem => {
                const today = momentus(this.state.actualDate)
                const taskDeadline = momentus(taskItem.taskDeadline)
                var difference = taskDeadline.diff(today, 'days');
             
                console.log(difference)
                console.log(taskDeadline)
                if(difference < 0) {
                    difference = 100;
                }
                console.log(today.diff.taskDeadline)

                return (
                    
                    <div className="GraphContainer" style={{ margin: 10, width: 300 }}>
                        <ProgBar strokeColor="#2db7f5" limit={difference} />
                        <tr className="line">
                            <td><Moment format="YYYY/MM/DD">
                                
                                {taskItem.taskDeadline}
                            </Moment></td>
                            
                        </tr>
                        <tr>
                            <td>Task: {taskItem.description}</td>
                        
                        </tr>
                        <tr>
                            <td>Project: {taskItem.project}  </td>
                            <td>{difference}</td>
                            
                        </tr>
                    </div>
                );
            })

        )
        
      
    }


}

export default TaskGraph