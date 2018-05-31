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
            projects: [],
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

        this.AuthService.fetch(`/projects/${this.AuthService.getUserId()}/myProjects`, { method: "get" })
        .then(responseData =>  {
            this.setState({
                projects: responseData
                
            }
        );
        });
     


    }




    render(){
        return(

        this.state.projects.map(projectItem => {

          const today = momentus(this.state.actualDate)
          const projectStartDay = momentus(projectItem.startdate)
          const projectFinishDay = momentus(projectItem.finishdate)
          var totalDays = projectFinishDay.diff(projectStartDay, 'days');
          if(totalDays<1) {
              totalDays = 1
          }
          var actualDaysLeft= projectFinishDay.diff(today, 'days');
          if(actualDaysLeft < 0) {
              actualDaysLeft = 0
          }
          const difference = actualDaysLeft;
          var mappedPercent = 100 - difference * 100 / totalDays
          if(mappedPercent > 100) {
              mappedPercent = 100
          }
   

        return(

             
              
                    <div className="ProjectGraphContainer">
                        <div className="ProgBarCircleContainer">
                             <ProgBarCircle limit={mappedPercent}/>
                        </div>
                        <table className="ProjectGraphTable">
                        <tbody>
                        <tr>
                                <td className="tdGraphStyleLeft">Project Start Date</td>
                                <td className="tdGraphStyleRight">Project Finish Date</td>
                            </tr>
                            <tr>
                                <td className="tdGraphStyleLeft">{projectStartDay.format('YYYY/MM/D')}</td>
                                <td className="tdGraphStyleRight">{projectFinishDay.format('YYYY/MM/D')}</td>
                            </tr>
                            <tr>
                                    <td> &nbsp;</td>
                                </tr>
                            <tr>
                                <td className="tdGraphStyleLeft"><h2>Project:</h2></td>
                                <td className="tdGraphStyleRight"><h2>{projectItem.name}</h2></td>
                            </tr>
                            <tr>
                                <td className="tdGraphStyleLeft">Project ID:  {projectItem.projectId}</td>
                                <td className="tdGraphStyleRight">Number of days left:</td>
                                <td className="tdGraphStyleRight">{actualDaysLeft}</td>


                            </tr>

                         
                            
                            </tbody>
                        </table>
                        


                    </div>
             

        )
       
    }))

        }
    }


export default ProjectGraph;