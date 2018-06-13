import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel, Checkbox } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';
import ListPossibleProjectManagers from './ListPossibleProjectManagers';

class CreateProject extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            manager: { name: "None Selected!"},
            budget: ""
        };
        this.AuthService = new AuthService();
        this.selectManager = this.selectManager.bind(this);
    }


    selectManager(event) {
        this.setState({
            manager: event
        })
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
                    <p> Project Manager ({this.state.manager.name})  </p>
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

                    <FormGroup controlId="calculationMethods">
                        <Checkbox checked inline>Initial Collaborator Cost</Checkbox>{' '}
                        <Checkbox checked inline>Final Collaborator Cost</Checkbox>{' '}
                        <Checkbox checked inline>Average Collaborator Cost</Checkbox>
                    </FormGroup>

                    <button
                        className="btn btn-primary" /*onClick={this.userDetail}*/
                    >
                        Create
                    </button>
                </form>
            </div>
        );
    }


}

export default CreateProject;