import React, { Component } from 'react';
import axios from 'axios';
import './ActiveProjects.css';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";


class ActiveProjects extends Component {

    constructor(props) {
        super(props);
        this.state = {
            projects: []
        }
    }

    async componentDidMount() {

        const request = await axios.get('projects/active');
        const teste = request.data;
        this.setState({ projects : teste });
                   
    }

    renderProjects(){
        return this.state.projects.map((projectItem) =>{
            return(
                
                <tr className="line">
                <td>{projectItem.projectId}</td>
                <td>{projectItem.name}</td>
                <td>{projectItem.description}</td>
                <td>{projectItem.projectManager.name}</td>
                <td>{projectItem.projectManager.email}</td>
                <button className="btn btn-primary" /*onClick={this.userDetail}*/>Details</button>
                </tr>
                
            )
        })
    }

    render() {
        return (
            <div className="ActiveProjects">
                <h3><b>List of Active Projects</b></h3>
                <table className="table table-hover">
                    <thead>
                        <tr>
                            <th>Project ID</th>
                            <th>Name</th>
                            <th>Description Date</th>
                            <th>Project Maganer email</th>
                            <th>Project Maganer name</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.renderProjects()}   
                    </tbody>
                </table>
            </div>
        );
    }
}

export default ActiveProjects;
