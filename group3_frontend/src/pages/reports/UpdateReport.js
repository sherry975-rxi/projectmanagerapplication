import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";
import AuthService from './../loginPage/AuthService';
import "./UpdateReport.css";
import { submit, dispatchError } from '../../authentication/authenticationActions';

class UpdateReport extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projectId: "",
            taskID: "",
            reportId: "",
            reportedTime: "",
            taskCollabEmail: "",
            hideSuccessInfo: "hide-code",
            message: ""
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.AuthService = new AuthService()
    }

    validateForm() {
        return (
            this.state.reportedTime.length > 0
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

                this.setState({
                hideSuccessInfo: ""
            })
          
    };

    render() {
        return (
            <div className="table-striped">
                <label className="title"></label>      
               
                <form onSubmit={this.handleSubmit}>   
                <td className="tdSubmit">  
                
                    <FormGroup controlId="reportedTime">
                        <ControlLabel>Reported time to update</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.reportedTime}
                            onChange={this.handleChange}
                        />                         
                    </FormGroup>
                    </td>
                     <td className="tdButton">
                     <Button
                        block
                        className="btn btn-primary"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                       Update
                    </Button>                 
                                   
                    </td>
                       <Alert
                            bsStyle="success"
                            className={this.state.hideSuccessInfo}>
                            <strong>Report successfully updated! <br /></strong>
                        
                      </Alert>

                </form>   
                                
                                 
               
            </div>
        );
    }
}

export default UpdateReport;

