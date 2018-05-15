import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import { Link } from "react-router-dom";

class AddTask extends React.Component {
        
    constructor(props) {
        super(props);
        this.state = {
                description: "",
                projectId: "" 
                                    
        };
    }

    handleChange = event => {
        this.setState({
          [event.target.id]: event.target.value
        });
      }

  handleSubmit = event => {
    event.preventDefault();
    const { 
        description,
        projectId} = this.state;

    const taskDetails = {
        description,
        projectId
        };

    console.log(taskDetails);

  fetch('/projects/' + this.state.projectId + '/tasks/', { 
      body: JSON.stringify(taskDetails),
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
        <div className="Add task">
            <h3 className="page-header"><b>Add task:</b></h3>
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
                
                <FormGroup controlId="projectId" bsSize="large">
                <ControlLabel>Project ID</ControlLabel>
                <FormControl    
                    autoFocus
                    type="text"
                    value={this.state.projectId}
                    onChange={this.handleChange}
                />
                </FormGroup>
               
               <button className="btn btn-primary" /*onClick={this.userDetail}*/>Create</button>
                </form>
            </div>
        )
    }
}

export default AddTask;
