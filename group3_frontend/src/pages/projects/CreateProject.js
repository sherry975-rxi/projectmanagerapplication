import React, { Component } from "react";
import './CreateProject.css';
import { FormGroup, FormControl, ControlLabel, Button, Radio } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';
import ListPossibleProjectManagers from './ListPossibleProjectManagers';
import {toastr} from "react-redux-toastr";
import { Redirect } from 'react-router-dom';

class CreateProject extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            projectManager: { name: "None Selected!"},
            budget: "",
            effortUnit: "HOURS",
            awaitingResponse: false
        };
        this.AuthService = new AuthService();
        this.selectManager = this.selectManager.bind(this);
    }


    validateForm() {
        return (this.state.name.length > 0 &&
            this.state.description.length > 0 &&
            this.state.budget > 0 &&
            this.state.projectManager.email != null &&
            !this.state.awaitingResponse);
    }

    // This method handles changes for all text boxes: Name, description and budget
    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleEffortSelection = event => {
        this.setState({
           effortUnit: event.target.value
        });
    }

    // this method is passed on to the child component ListPossibleProjectManagers
    selectManager(event) {
        this.setState({
            projectManager: event
        })
    }

    handleSubmit = event => {
        event.preventDefault();

        const {name,
        description,
        projectManager,
        budget,
        effortUnit} = this.state;

        const projectDTO = {name,
            description,
            projectManager,
            budget,
            effortUnit};

        this.setState({
            awaitingResponse:true
        });

        this.AuthService.fetch(
            `/projects/`,
            {
                body: JSON.stringify(projectDTO),
                method: 'POST'
            }
        ).then(responseData => {

            toastr.success('Project Created Successfully!');
            setTimeout(function() {
                window.location.href = "/activeprojects";
            }, 1000);


        }).catch(err => {
            console.log(err);
            toastr.error('An error occurred!');
            this.setState({
                awaitingResponse: false
            });
        });


    }


    render() {

        return (
            <div className=" table-striped">
                <h3>
                    <b>Create project</b>
                </h3>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="name" bsSize="large">
                        <ControlLabel>Project Name</ControlLabel>
                        <FormControl
                            className="textForm"
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="description" bsSize="large">
                        <ControlLabel>Description</ControlLabel>
                        <FormControl
                            className="textForm"
                            autoFocus
                            type="text"
                            value={this.state.description}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="budget" bsSize="large">
                        <ControlLabel>Budget</ControlLabel>
                        <FormControl
                            className="textForm"
                            autoFocus
                            type="number"
                            pattern="[0-9]*"
                            inputmode="numeric"
                            value={this.state.budget}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="effortUnit">
                        <ControlLabel>Effort Unit</ControlLabel>
                        <Radio value="HOURS" checked={this.state.effortUnit === "HOURS"}
                               onChange={this.handleEffortSelection}>
                            Hours
                        </Radio>{' '}
                        <Radio value="PM" checked={this.state.effortUnit === "PM"}
                            onChange={this.handleEffortSelection}>
                            Person/Month
                        </Radio>
                    </FormGroup>

                    <ControlLabel> Project Manager ({this.state.projectManager.name})  </ControlLabel>
                    <br />
                    <ListPossibleProjectManagers onSelect={this.selectManager}/>

                    <br />


                    <Button block className="btn btn-primary" disabled={!this.validateForm()} type="submit" >
                        Create Project
                    </Button>
                </form>
            </div>
        );
    }


}

export default CreateProject;