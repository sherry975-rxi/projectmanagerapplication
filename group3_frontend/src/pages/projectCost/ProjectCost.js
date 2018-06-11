import React, { Component } from 'react';
import './ProjectCost.css';
import { Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import MediumButton from '../../components/button/mediumButton.jsx';

class ProjectCost extends Component {
    constructor(props) {
        super(props);
        this.state = {
            project: {},
            message: ''
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/cost`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                project: responseData,
                message: responseData.status
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
                <Link
                    to={
                        '/selectprojectcostcalculation/' + projectItem.projectId
                    }
                    activeClassName="active"
                >
                    <MediumButton text="Change Cost Method" />
                </Link>
                &nbsp;
                <p />
                <p />
                <Link
                    to={'/projectdetails/' + projectItem.projectId}
                    activeClassName="active"
                >
                    <MediumButton text="Back to Project Details" />
                </Link>
                &nbsp;
            </div>
        );
    }

    render() {
        if (this.state.message === '' || this.state.message === '2') {
            return (
                <div>
                    <h1 className="page-header">Project Cost</h1>
                    <h3>Info</h3>
                    {this.renderProjectWithCost()}
                </div>
            );
        } else {
            return <Error message={this.state.message + ' NOT AUTHORIZED'} />;
        }
    }
}

export default ProjectCost;
