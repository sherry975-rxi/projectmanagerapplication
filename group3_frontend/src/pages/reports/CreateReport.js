import React, { Component } from 'react';
import {
    Button,
    FormGroup,
    FormControl,
    ControlLabel,
    Alert
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';

class CreateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reportedTime: '',
            taskCollabEmail: '',
            hideSuccessInfo: 'hide-code',
            message: ''
        };
        this.AuthService = new AuthService();
    }

    validateForm() {
        return this.state.reportedTime > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
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

        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/${
                this.props.match.params.taskID
            }/reports/`,
            {
                body: JSON.stringify(reportDTOData),
                method: 'POST'
            }
        ).then(responseData => {
            window.location.href = `/projects/${
                this.props.match.params.projectID
            }/tasks/${this.props.match.params.taskID}/reports/`;
        });

        this.setState({
            hideSuccessInfo: ''
        });
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
                            type="number"
                            pattern="[0-9]*"
                            inputmode="numeric"
                            value={this.state.reportedTime}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <Button
                        block
                        className="btn btn-primary"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Save Report
                    </Button>

                    <Alert
                        bsStyle="success"
                        className={this.state.hideSuccessInfo}
                    >
                        <strong>
                            Report successfully saved! <br />
                        </strong>
                    </Alert>
                </form>
            </div>
        );
    }
}

export default CreateReport;
