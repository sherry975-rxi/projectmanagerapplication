import React, { Component } from 'react';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";


class ProjectDetails extends Component {

    constructor(props){
        super(props);
        this.state = {
            project: {}        }
    }


    async componentDidMount(){
        fetch('projects/2', {method: 'get'})
        .then((response) => response.json())
            .then((responseData) => {
                this.setState({
                    project: responseData,
                    projectManager: responseData.projectManager.name
                });
            });
    }

    renderProject(){
        var projectWithDetails = this.state.project;
        var projectManager = this.state.projectManager;
            return (
                <tr className="line"> 
                    <td><th>{projectWithDetails.projectId}</th></td>
                    <td>{projectWithDetails.projectStatusName}</td>
                    <td>{projectWithDetails.name}</td>
                    <td>{projectWithDetails.description}</td>
                    <td>{projectManager}</td>
                    <td>{projectWithDetails.effortUnit}</td>
                    <td>{projectWithDetails.budget}</td>
                    <td>{projectWithDetails.startdate}</td>
                    <td>{projectWithDetails.finishdate}</td>
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
                            <th>Project ID</th>
                            <th>Status</th>
                            <th>Project Name</th>
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
