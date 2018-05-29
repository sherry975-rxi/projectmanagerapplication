import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';

class CreateReport extends Component {
    constructor(props) {
        super(props);
        this.match
        this.state = {
            reportedTime: "",
            taskCollabEmail: "",
            hideSuccessInfo: "hide-code",
            message: ""
        };
        this.AuthService = new AuthService();
    }

    validateForm() {
        return (
            this.state.reportedTime.length > 0 && this.state.email.length > 0
        );
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
        event.preventDefault();
        const { reportedTime, taskCollabEmail } = this.state;

        const reportDTOData = {
            reportedTime,
            taskCollaborator: {
                projCollaborator: {
                    collaborator: {
                        email: taskCollabEmail
                    }
                }
            }
        };

        console.log(reportDTOData);

        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/tasks/${this.props.match.params.taskID}/reports/`,
            {
                body: JSON.stringify(reportDTOData),
                method: "POST"
            }
        )

            .then(function (myJson) {
                console.log(myJson);
            });

            this.setState({
                hideSuccessInfo: ""
            })
    
    };

    render() {
        return (
            <div className=" table-striped">
                <h3>
                    <b>Create Report for {this.props.match.params.taskID} </b>
                </h3>&nbsp;
                   
                <form onSubmit={this.handleSubmit}>
                   
                    <FormGroup controlId="reportedTime" bsSize="large">
                        <ControlLabel>Reported time (hours)</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.reportedTime}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="taskCollabEmail" bsSize="large">
                        <ControlLabel>
                            Type task collaborator email address
                        </ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.taskCollabEmail}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <button
                        className="btn btn-primary" 
                    >
                       Save Report
                    </button>&nbsp;

                     <Alert
                            bsStyle="success"
                            className={this.state.hideSuccessInfo}>
                            <strong>Report successfully saved! <br /></strong>
                        
                      </Alert>
                </form>
            </div>
        );
    }
}

export default CreateReport;