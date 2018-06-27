import React, { Component } from 'react';
import {
    Form,
    Modal,
    Button,
    FormGroup,
    FormControl,
    ControlLabel,
    Alert
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import  './CreateReport.css';

class CreateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            reportedTime: '',
            taskCollabEmail: '',
            hideSuccessInfo: 'hide-code',
            message: ''
        };
        this.AuthService = new AuthService();
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
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


    renderReportCreationProcess() {
        return (
            <div>
                <Modal.Header closeButton>  
                <Modal.Title>Create Report
                <h5 >Task {this.props.taskID}</h5>
                </Modal.Title>

                </Modal.Header>
                    <Modal.Body>
                        <Form >
                            <FormGroup controlId="reportedTime" bsSize="large">                     
                                <ControlLabel></ControlLabel>
                                <FormControl 
                                autoFocus
                                type="number"
                                pattern="[0-9]*"
                                placeholder="Enter reported time (hours)"
                                inputmode="numeric"
                                value={this.state.reportedTime}
                                onChange={this.handleChange}
                            />
                            </FormGroup>  
                        </Form>
                    </Modal.Body>
                <Modal.Footer>
                    <button
                        block
                        className="genericButton"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Save Report
                    </button>   
</Modal.Footer>
            </div>
        )
    }

    render() {
        return (
            <div>
            <button className="genericButton" onClick={this.handleShow.bind(this)}>
                Create Report 
            </button>

            <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
            {this.renderReportCreationProcess()}
            </Modal>
        </div>
        );
    }
}

export default CreateReport;
