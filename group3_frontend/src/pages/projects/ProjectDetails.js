import React, { Component } from 'react';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";


class ProjectDetails extends Component {
    

    constructor(props){
        super(props);
        this.match
        this.state = {
            project: {},
            projectStartDate: "",
            projectFinishDate: "",
            dateSplit: []
                }
    }


    async componentDidMount(){

        fetch(`/projects/${this.props.match.params.projectID}`, {method: 'get'})
        .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    project: responseData,
                    projectManager: responseData.projectManager.name,
                    projectStartDate: responseData.startdate,
                    projectFinishDate: responseData.finishdate

                });
            });
    }


    getProjectYear(dateToParse){
        var date = JSON.stringify(dateToParse);
        var year = date.substring(1,5);

        return year

    }

    getProjectMonth(dateToParse){
        var date = JSON.stringify(dateToParse);
        var month = date.substring(6,8);

        return month

    }

    getProjectDay(dateToParse){
        var date = JSON.stringify(dateToParse);
        var day = date.substring(9,11);

        return day

    }

    getProjectHour(dateToParse){
        var date = JSON.stringify(dateToParse);
        var hour = date.substring(12,14);
        
        return hour

    }

    getProjectMinutes(dateToParse){
        var date = JSON.stringify(dateToParse);
        var minutes = date.substring(15,17);
        
        return minutes

    }



    renderProject(){
        var projectWithDetails = this.state.project;
        var projectManagerX = this.state.projectManager;

        var startDate = "";

        if(this.state.projectStartDate != null){

            const startYear = this.getProjectYear(this.state.projectStartDate);
            const startMonth = this.getProjectMonth(this.state.projectStartDate);
            const startDay = this.getProjectDay(this.state.projectStartDate);
            const startHour = this.getProjectHour(this.state.projectStartDate);
            const startMinute = this.getProjectMinutes(this.state.projectStartDate);
            startDate = startYear + "-" + startMonth + "-" + startDay + "   " + startHour + ":" + startMinute;
        }

    
        var finishDate = "";

        if(this.state.projectFinishDate != null){

            const finishYear = this.getProjectYear(this.state.projectFinishDate);
            const finishMonth = this.getProjectMonth(this.state.projectFinishDate);
            const finishDay = this.getProjectDay(this.state.projectFinishDate);
            const finishHour = this.getProjectHour(this.state.projectFinishDate);
            const finishMinute = this.getProjectMinutes(this.state.projectFinishDate);
            finishDate = finishYear + "-" + finishMonth + "-" + finishDay + "   " + finishHour + ":" + finishMinute;

        }
        




       

            return (
                <tr className="line"> 
                    <td><th>{projectWithDetails.projectId}</th></td>
                    <td>{projectWithDetails.projectStatusName}</td>
                    <td>{projectWithDetails.nname}</td>
                    <td>{projectWithDetails.description}</td>
                    <td>{projectManagerX}</td>
                    <td>{projectWithDetails.effortUnit}</td>
                    <td>{projectWithDetails.budget}</td>
                    <td>{startDate}</td>

                    <td>{finishDate}</td>
                    <td>{projectWithDetails.calculationMethod}</td>
                    <td>{projectWithDetails.availableCalculationMethods}</td>
                    </tr>
            )
        }
    


    render() {
        return (

            <div className=" table-striped">
                <h3><b>Project Details</b></h3>
                <table className="table table-hover">
             
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Status</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Project Manager</th>
                            <th>Effort Unit</th>
                            <th>Budget</th>
                            <th>Start Date</th>
                            <th>Finish Date</th>
                            <th>Calculation Method</th>
                            <th>Available Calculation Methods</th>
                        </tr>

                    </thead>
                    <tbody>
                        {this.renderProject()}
                    </tbody>
                    </table>
                    
                    </div>

        );
    }

}


export default ProjectDetails;
