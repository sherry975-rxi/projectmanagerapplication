import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel, Checkbox, Button } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';
import ListPossibleProjectManagers from './ListPossibleProjectManagers';

class CreateProject extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            projectManager: { name: "None Selected!"},
            budget: ""
        };
        this.AuthService = new AuthService();
        this.selectManager = this.selectManager.bind(this);
    }


    validateForm() {
        return (this.state.name.length > 0 &&
            this.state.description.length > 0 &&
            this.state.budget > 0 &&
            this.state.projectManager.email != null);
    }


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
        budget} = this.state;

        const projectDTO = {name,
            description,
            projectManager,
            budget};

        this.AuthService.fetch(
            `/projects/`,
            {
                body: JSON.stringify(projectDTO),
                method: 'POST'
            }
        ).then(responseData => {
            console.log(responseData);
            window.location.href = `/activeprojects`;
        });


    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };


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
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="description" bsSize="large">
                        <ControlLabel>Description</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.description}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <p> Project Manager ({this.state.projectManager.name})  </p>
                    <ListPossibleProjectManagers onSelect={this.selectManager}/>

                    <br />

                    <FormGroup controlId="budget" bsSize="large">
                        <ControlLabel>Budget</ControlLabel>
                        <FormControl
                            autoFocus
                            type="number"
                            pattern="[0-9]*"
                            inputmode="numeric"
                            value={this.state.budget}
                            onChange={this.handleChange}
                        />
                    </FormGroup>


                    <Button block className="btn btn-primary" disabled={!this.validateForm()} type="submit" >
                        Create Project
                    </Button>
                </form>
            </div>
        );
    }


}

export default CreateProject;