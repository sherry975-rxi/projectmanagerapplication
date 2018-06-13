import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';

class AddTask extends Component {
    constructor(props) {
        super(props);
        this.state = {
            description: "",
            hideSuccessInfo: 'hide-code',
            message: ''
        };
        this.AuthService = new AuthService()
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
        event.preventDefault();
        const { description } = this.state;

        const taskDetails = {
            description
        };

        console.log(taskDetails);

        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/tasks/`, {
            body: JSON.stringify(taskDetails),
            method: "POST"
        }
         ).then(responseData => {
             console.log(responseData);
            window.location.href = `/projects/${this.props.match.params.projectID}/tasks/`;
    });
   
         this.setState({
             hideSuccessInfo: ''
         });
};


    render() {
        return (
            <div className=" table-striped">
                <h3>
                    <b>Add Task in Project ID {this.props.match.params.projectID}</b>
                </h3>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="description" bsSize="large">
                        <ControlLabel>Task Description</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.description}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <button
                        className="btn btn-primary" /*onClick={this.userDetail}*/
                    >
                        Create
                    </button>
                    <Alert
                        bsStyle="success"
                        className={this.state.hideSuccessInfo}
                    >
                        <strong>
                            Task successfully created! <br />
                        </strong>
                    </Alert>
                </form>
            </div>
        );
    }
}

export default AddTask;
