import React, { Component } from "react";
import "./ProjectCost.css";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import { Link } from "react-router-dom";
import AuthService from './../loginPage/AuthService';


class ProjectCost extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            project: {}
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.loadProjectWithCostFromServer();
    }

    // Load users from database
    loadProjectWithCostFromServer() {
        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/cost`, {
            method: "get"
        })
            .then(responseData => {
                this.setState({
                    project: responseData
                });
            });
    }

    // Load users from database
    loadUsersFromServer() {
        this.AuthService.fetch("/projects/2/cost", { method: "get" })
            .then(responseData => {
                this.setState({
                    project: responseData
                });
            });
    }

    renderProjectWithCost() {
        var projectItem = this.state.project;
        return (
            <div>
                <p>
                    <b>Project ID:</b> &nbsp;
                    {projectItem.projectId}
                </p>
                <p>
                    <b>Project Name:</b> &nbsp;
                    {projectItem.name}
                </p>
                <p>
                    <b>Available Cost Calculation Methods:</b> &nbsp;
                    {projectItem.availableCalculationMethods}
                </p>
                <p>
                    <b>Selected Cost Calculation Method:</b> &nbsp;
                    {projectItem.calculationMethod}
                </p>
                <p>
                    <b>Project Cost:</b> &nbsp;
                    {projectItem.projectCost}
                </p>
                <hr />
                <p />

                <Link to={'/selectprojectcostcalculation/' + projectItem.projectId} activeClassName="active">
                    <button className="btn btn-warning" >Change Cost Method</button>
                </Link> &nbsp;

                <p />
                <p />
                <Link to={'/projectdetails/' + projectItem.projectId} activeClassName="active">
                    <button className="btn btn-primary" >Back to Project Details</button>
                </Link> &nbsp;

            </div>
        );
    }

    render() {
        return (
            <div>
                <h1 className="page-header">Project Cost</h1>
                <h3>Info</h3>
                {this.renderProjectWithCost()}
            </div>
        );
    }
}

export default ProjectCost;
