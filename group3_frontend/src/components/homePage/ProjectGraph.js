import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import Moment from 'react-moment';
import ProgBarCircle from './ProgBarCircle';
import momentus from 'moment';



class ProjectGraph extends Component{
    


    constructor(props) {
        super(props);
        this.match;
        this.state = {
            projects: {},
            projectId: "",
            projectStartDate: "",
            projectFinishDate: "",
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
            this.fetchUserProjects()        
        })
          
        })
    }

     fetchUserProjects(){

        this.AuthService.fetch("projects/2", { method: "get" })
        .then(responseData => {
            console.log(responseData)
            this.setState({
                projectId: responseData['projectId'],
                projectStartDate: responseData['startdate'],
                projectFinishDate: responseData['finishdate']

            });
        });

    }



    render(){
          console.log(this.state.projects)
          const today = momentus(this.state.actualDate)
          const projectStartDay = momentus(this.state.projectStartDate)
          const projectFinishDay = momentus(this.state.projectFinishDate)
          const totalDays = projectFinishDay.diff(projectStartDay, 'days');
          const actualDaysLeft= projectFinishDay.diff(today, 'days');
          const difference = actualDaysLeft;
          const mappedPercent = 100 - difference * 100 / totalDays


        return(

             
              
                    <div className="ProjectGraphContainer">
                    <h1>Project Closest do Deadline</h1>
                        <div className="ProgBarCircleContainer">
                             <ProgBarCircle limit={mappedPercent}/>
                        </div>
                        <table className="ProjectGraphTable">
                            <tr>
                                <td><h1>Project: {this.state.projectId}</h1></td>
                            </tr>
                            <tr>
                                <td className="tdGraphStyleLeft">Project Start Date</td>
                                <td className="tdGraphStyleRight">Project Finish Date</td>
                            </tr>
                            <tr>
                                <td className="tdGraphStyleLeft">{projectStartDay.format('YYYY/MM/D')}</td>
                                <td className="tdGraphStyleRight">{projectFinishDay.format('YYYY/MM/D')}</td>
                            </tr>
                            <br />

                            <tr>
                                <td className="tdGraphStyleLeft">Number of days left:</td>
                            </tr>
                            <tr>
                            <td className="tdGraphStyleLeft">{actualDaysLeft}</td>

                            </tr>
                            

                        </table>
                        


                    </div>
             

        )
       
    }

}


export default ProjectGraph