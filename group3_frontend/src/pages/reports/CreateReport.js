import React, {Component} from 'react';

import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";


class CreateReport extends Component {

    constructor(props) {
        super(props);
        this.state = {
                projectId: "",
                taskID: "",  
                reportedTime: "",
                taskCollabEmail: "",       
        };
    }

    validateForm() {
        return this.state.reportedTime.length > 0
            && this.state.email.length > 0
    }

    handleChange = event => {
        this.setState({
          [event.target.id]: event.target.value
        });
      }

      handleSubmit = event => {
        event.preventDefault();
        const { 
            projectId,
            taskID,
            reportedTime,
            taskCollabEmail} = this.state;


        const reportDTOData = {
            reportedTime,
            taskCollaborator : {
                projCollaborator : {
                    collaborator: {
                        email : taskCollabEmail
                    }
                }
            }
        };

        console.log(reportDTOData);

        fetch('/projects/' + this.state.projectId + '/tasks/' + this.state.taskID + '/reports/', {
            body: JSON.stringify(reportDTOData),
            headers: {
              'content-type': 'application/json'
            },
            method: 'POST'
          }).then(function (response) {
            return response.json();
          })
            .then(function (myJson) {
              console.log(myJson);
            });
      
        }


    render() {
        return (
            <div>
                <h1 className="page-header">Report</h1>

                <h3>Insert info to create Report:</h3>

                    <form onSubmit={this.handleSubmit}>

                        <FormGroup controlId="projectId">
                        <ControlLabel>Type Project ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.projectId}
                            onChange={this.handleChange}
                        />
                        </FormGroup>

                        <FormGroup controlId="taskID">
                        <ControlLabel>Type Task ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.taskID}
                            onChange={this.handleChange}
                        />
                        </FormGroup>

                        <FormGroup controlId="reportedTime">
                        <ControlLabel>Type reported time</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.reportedTime}
                            onChange={this.handleChange}
                        />
                        </FormGroup>

                        <FormGroup controlId="taskCollabEmail">
                        <ControlLabel>Type task collaborator email address</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.taskCollabEmail}
                            onChange={this.handleChange}
                        />
                        </FormGroup>

                        <Button
                            block

                            //disabled={!this.validateForm()}
                            type="submit"
                            >
                            create report
                        </Button>
                        {/* {this.props.myJson.projectCost} */}
                    </form>
            </div>
        )
    }
}

export default CreateReport;

