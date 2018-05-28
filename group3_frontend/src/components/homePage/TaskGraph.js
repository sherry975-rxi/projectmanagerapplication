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
            project: {},
            percent: 0,
            color: '#3FC7FA',
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
        console.log(this.state.actualDate);
        return (
            
            this.state.tasks.map(taskItem => {
                return (
                    <div className="GraphContainer" style={{ margin: 10, width: 300 }}>
                     <Line percent="30" strokeWidth="4" strokeColor={this.state.color} strokeLinecap="square"  percent={this.state.percent} />
                        <tr className="line">
                            <td><Moment format="YYYY/MM/DD">
                                
                                {taskItem.taskDeadline}
                            </Moment></td>
                            
                        </tr>
                        <tr>
                            <td>Task: {taskItem.description}</td>
                        
                        </tr>
                        <tr>
                            <td>Project: {taskItem.project}</td>
                            <td><Moment diff={this.state.actualDate} unit="days">{taskItem.taskDeadline}</Moment></td>

                        </tr>

                    </div>
                );
            })

        )
        
      
    }


}

export default TaskGraph