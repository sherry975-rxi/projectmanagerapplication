import React, { Component } from "react";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';
import "./CreateReport.css";

class UpdateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: "",
            taskID: "",
            reportId: "",
            reportedTime: "",
            taskCollabEmail: ""
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.AuthService = new AuthService()
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

    async handleSubmit(event) {
        event.preventDefault();
        const reportedTime = this.state.reportedTime;

        const reportDTOData = {
            reportedTime,
            taskCollaborator: {
                projCollaborator: {
                    collaborator: {
                        email: this.AuthService.getProfile().sub
                    }
                }
            }
        };

        console.log(reportDTOData);

        this.AuthService.fetch(`/projects/${this.props.projId}/tasks/${this.props.taskId}/reports/
                ${this.props.reportId}/update/`,
            {
                body: JSON.stringify(reportDTOData),
                method: "PUT"
            }
        )
            .then((responseData) => {
                console.log(responseData);
                this.props.onSubmit();
            });


    };

    render() {
        return (
            <div className=" table-striped">
                <form onSubmit={this.handleSubmit}>
                    <button
                        className="btn btn-primary" /*onClick={this.userDetail}*/
                    >
                        Update Reported time:
                    </button>
                    <FormGroup controlId="reportedTime">
                        <ControlLabel></ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.reportedTime}
                            onChange={this.handleChange}
                        />
                    </FormGroup>


                </form>
            </div>
        );
    }
}

export default UpdateReport;
