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
            project: {}
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
                message: responseData.error
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
                >
                    <MediumButton text="Change Method" />
                </Link>
                &nbsp;
                <p />
                <p />
                <Link to={'/myprojects'}>
                    <MediumButton text="Back" />
                </Link>
                &nbsp;
            </div>
        );
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message + ' NOT AUTHORIZED'} />;
        } else {
            return (
                <div>
                    <h1 className="page-header">Project Cost</h1>
                    <h3>Info</h3>
                    {this.renderProjectWithCost()}
                </div>
            );
        }
    }
}

export default ProjectCost;
