import React, {Component} from 'react';
import './ProjectCost.css';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class ProjectCost extends Component {

    constructor(props) {
        super(props);
        this.state = {
            project: {}
        }
    }

      componentDidMount() {
        this.loadUsersFromServer();
      }
      
      // Load users from database
      loadUsersFromServer() {
          fetch('/projects/2/cost',{ method: 'get'}) 
          .then((response) => response.json()) 
          .then((responseData) => { 
              this.setState({ 
                project: responseData, 
              }); 
          });     
      }


    renderUsers(){
        //return this.state.project.map((projectItem) =>{
            var projectItem = this.state.project
            return(
                <div>
                <p><b>Project ID:</b> &nbsp;
                {projectItem.projectId}</p>
                <p><b>Project Name:</b> &nbsp;
                {projectItem.name}</p>
                <p><b>Available Cost Calculation Methods:</b> &nbsp;
                {projectItem.availableCalculationMethods}</p>
                <p><b>Selected Cost Calculation Method:</b> &nbsp;
                {projectItem.calculationMethod}</p>
                <p><b>Project Cost:</b> &nbsp;
                {projectItem.projectCost}</p>
                <hr/>
                </div>
            )
        //})
    }

    render() {
        return (
            <div>
                <h1 className="page-header">Project Cost</h1>              
                <h3>Info</h3>   
                {this.renderUsers()}                        
            </div>
        );
    }
}

export default ProjectCost;