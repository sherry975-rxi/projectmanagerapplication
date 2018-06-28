import React, { Component } from 'react';
import {
    Form,
    Modal,
    FormGroup,
    FormControl
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import './CreateReport.css';

class CreateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            reportedTime: '',
            taskCollabEmail: '',
            hideSuccessInfo: 'hide-code',
            message: '',
            canRenderResponse: '',
            canRenderButton: false
        };
        this.AuthService = new AuthService();
    }


    async componentDidMount() {
        this.AuthService.fetchRaw(
            `/projects/${this.AuthService.getUserId()}/tasks/${this.props.taskID}/isCollabInTask/${this.AuthService.getProfile().sub}`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                canRenderResponse: responseData.status,
                hasFetched: true
            });
        }).catch(err => {

        });
    };


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
            `/projects/${this.props.projectID}/tasks/${
            this.props.taskID
            }/reports/`,
            {
                body: JSON.stringify(reportDTOData),
                method: 'POST'
            }
        ).then(responseData => {
            window.location.href = `/projects/${
                this.props.projectID
                }/tasks/${this.props.taskID}/reports/`;
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
                            <FormControl
                                autoFocus
                                type="number"
                                pattern="[0-9]*"
                                placeholder="Enter reported time (hours)"
                                inputmode="numeric"
                                value={this.state.reportedTime}
                                onChange={this.handleChange} />
                        </FormGroup>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <button
                        onClick={this.handleSubmit}
                        block
                        className="genericButton"
                        disabled={!this.validateForm()}
                        type="submit">
                        Save Report
                    </button>
                </Modal.Footer>
            </div>
        )
    }



    render() {
        const showButton = { display: 'block' }
        const hideButton = { display: 'none' }
        var style = ''

        if (this.state.canRenderResponse === 200) {
            style = showButton
        }
        else {
            style = hideButton;
        }

        return (
            <div>
                <button className="genericButton" onClick={this.handleShow.bind(this)} style={style}>
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
