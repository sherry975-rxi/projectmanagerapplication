import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class CreateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: "",
            taskID: "",
            reportedTime: "",
            taskCollabEmail: ""
        };
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

        fetch(
            "/projects/" +
                this.state.projectId +
                "/tasks/" +
                this.state.taskID +
                "/reports/",
            {
                body: JSON.stringify(reportDTOData),
                headers: {
                    "content-type": "application/json"
                },
                method: "POST"
            }
        )
            .then(function(response) {
                return response.json();
            })
            .then(function(myJson) {
                console.log(myJson);
            });
    };

    render() {
        return (
            <div className=" table-striped">
                <h3>
                <b>Create Report</b>
                </h3>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="projectId" bsSize="large">
                        <ControlLabel>Type Project ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.projectId}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="taskID" bsSize="large">
                        <ControlLabel>Type Task ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.taskID}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="reportedTime" bsSize="large">
                        <ControlLabel>Type reported time</ControlLabel>
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
                        className="btn btn-primary" /*onClick={this.userDetail}*/
                    >
                        Create Report
                    </button>
                </form>
            </div>
        );
    }
}

export default CreateReport;
