import React, { Component } from 'react';
import './ProjectCost.css';
import { Button, FormGroup, FormControl, ControlLabel , Alert} from "react-bootstrap";
import { Prompt, Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';


class ProjectCostCalculation extends Component {

    constructor(props) {
        super(props);
        this.match
        this.state = {  
                //projectId: "",
                project: {} ,
                availableMethods : [],
                calculationMethod: "",
                submission : false,
                hideSuccessInfo: "hide-code",
                //res : []
                //projectCost : ""           
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.loadProjectWithCostFromServer();
    }

    // Load users from database
    loadProjectWithCostFromServer() {
        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}`, {
            method: "get"
        })
            .then(responseData => {
                this.setState({
                    project: responseData,
                    availableMethods: responseData.availableCalculationMethods.split(","),
                });
            });
    }

    loadAvailableMethods() {

        //this.state.res = this.state.availableMethodsX.split(",");

        /*  for(var i = 0; i < myArray.length; i++){
             return(
                 <option value={myArray[i]}>
                 {myArray[i]}
                 </option>
             );


         } */


        return this.state.availableMethods.map(option => {
            return (
                <option value={option}>
                    {option}
                </option>
            );
        })

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
            calculationMethod,/*,
        projectCost */} = this.state;

        const projectDTOData = {
            //projectId,
            calculationMethod,
            //result: projectCost
        };

        console.log(projectDTOData);

        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}`, {
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

            this.setState({
                //submission:true,
                hideSuccessInfo: ""
            })
      
        }


    render() {
        return (
            <div>
                <h1 className="page-header">Project Cost</h1>

                <p><b>Project ID:</b> &nbsp;
                {this.props.match.params.projectID}</p>

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

                    {/*             <FormGroup controlId="calculationMethod">
                        <ControlLabel>Type Calculation Method</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.calculationMethod}
                            onChange={this.handleChange}
                        />
                        </FormGroup> */}


                    {/* <FormGroup controlId="projectCost">
                        <ControlLabel>Project Cost</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.projectCost}
                            onChange={this.handleChange}
                        />
                        </FormGroup> */}
                        <FormGroup controlId="calculationMethod">
                            <ControlLabel>
                            To change the Project Cost Calculation Method, please select a method:
                                </ControlLabel>
                            <FormControl
                                value={this.state.calculationMethod}
                                onChange={this.handleChange}
                                componentClass="select"
                                placeholder="select"
                            >
                                <option value="" disabled selected>
                                    Select your option
                                </option>
                            {this.loadAvailableMethods()}
                        </FormControl>

                    </FormGroup>

                    <Button
                        block

                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Apply Calculation Method
                        </Button>
                        {/* {this.props.myJson.projectCost} */}
                      {/*   <Prompt
                        className={this.state.submission}
                        message="Calculation Method Successfully Updated"
                        /> */}
                        <Alert 
                            bsStyle="success" 
                            className={this.state.hideSuccessInfo}>
                            <strong>Calculation Method Successfully Updated! <br/></strong>
                            Check project cost
                        </Alert>
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

