import React, {Component} from 'react';
import './ProjectCost.css';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import {Prompt, Link} from 'react-router-dom';


class ProjectCostCalculation extends Component {

    constructor(props) {
        super(props);
        this.match
        this.state = {  
                //projectId: "",
                calculationMethod: "",
                submission : false
                //projectCost : ""           
        };
    }


    //state = {}
    validateForm() {
        return this.state.calculationMethod.length > 0
    }

    handleChange = event => {
        this.setState({
          [event.target.id]: event.target.value
        });
      }

      handleSubmit = event => {
        event.preventDefault();
        const { 
            //projectId,
            calculationMethod/*,
        projectCost */} = this.state;

          const projectDTOData = {
            //projectId,
            calculationMethod,
            //result: projectCost
        };

        console.log(projectDTOData);

        fetch(`/projects/${this.props.match.params.projectID}`, {
            body: JSON.stringify(projectDTOData),
            headers: {
              'content-type': 'application/json'
            },
            method: 'PATCH'
          }).then(function (response) {
            return response.json();
          })
            .then(function (myJson) {
              console.log(myJson);
            });

            this.setState({submission:true})
      
        }


    render() {
        return (
            <div>
                <h1 className="page-header">Project Cost</h1>

                <p><b>Project ID:</b> &nbsp;
                {this.props.match.params.projectID}</p>

                <h3>To change the Project Cost Calculation Method, please write the following informations:</h3>

                    <form onSubmit={this.handleSubmit}>

                       {/*  <FormGroup controlId="projectId">
                        <ControlLabel>Type Project ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.projectId}
                            onChange={this.handleChange}
                        />
                        </FormGroup> */}

                        <FormGroup controlId="calculationMethod">
                        <ControlLabel>Type Calculation Method</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.calculationMethod}
                            onChange={this.handleChange}
                        />
                        </FormGroup>

                        {/* <FormGroup controlId="projectCost">
                        <ControlLabel>Project Cost</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.projectCost}
                            onChange={this.handleChange}
                        />
                        </FormGroup> */}
                        <Button
                            block

                            disabled={!this.validateForm()}
                            type="submit"
                            >
                            Apply Calculation Method
                        </Button>
                        {/* {this.props.myJson.projectCost} */}
                        <Prompt
                        when={this.state.submission}
                        message="Calculation Method Successfully Updated"
                        />
                         <p/>
                         <p/>

                        <Link to={'/projectcost/'+ this.props.match.params.projectID} activeClassName="active"> 
                            <button className="btn btn-info" >Calculate Project Cost</button>
                        </Link> &nbsp;
                        <p/>
                        <p/>
                        <Link to={'/projectdetails/'+ this.props.match.params.projectID} activeClassName="active"> 
                            <button className="btn btn-primary" >Back to Project Details</button>
                        </Link> &nbsp;


                    </form>
            </div>
        )
    }
}

export default ProjectCostCalculation;

