import React, { Component, Fragment } from 'react';
import { Link } from 'react-router-dom';
import MediumButton from '../../components/button/mediumButton';
import { Glyphicon } from 'react-bootstrap';
import AuthService from '../../pages/loginPage/AuthService';
import AddUserToProject from '../../pages/projects/AddUserToProject';

class ProjectsTableRow extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();

        this.state = { isOpen: false };
    }

    handleRotate = () => {
        this.setState({ isOpen: !this.state.isOpen });
    };

    getManagerIcon() {
        if (
            this.props.project.projectManagerEmail ===
            this.AuthService.getProfile().sub
        ) {
            return <span className="project-badge">PM</span>;
        } else return <div> </div>;
    }

    getProjectInfo() {
        if (
            this.props.project.projectManagerEmail ===
            this.AuthService.getProfile().sub
        ) {
            return (
                <div>
                    <b>Budget:&nbsp;</b>
                    {this.props.project.projectBudget}
                    <br /> <b>Calculation method:&nbsp;</b>
                    {this.props.project.projectCalculationMethod}
                    <br />
                    {this.props.project.button}
                    <br />
                </div>
            );
        } else return <div> </div>;
    }

    getManagerButtons() {
        if (
            this.props.project.projectManagerEmail ===
            this.AuthService.getProfile().sub
        ) {
            return (
                <div>
                    <p />
                    <Link
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/tasks'
                        }
                        activeClassName="active"
                    >
                        <MediumButton text="View Tasks" />
                    </Link>
                    <p />
                    <Link
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/addtask'
                        }
                        activeClassName="active"
                    >
                        <MediumButton text="Create task" />
                    </Link>
                    <p />
                    <Link
                        to={'/projectcost/' + this.props.project.projectId}
                        activeClassName="active"
                    >
                        <MediumButton text="Calculate Project Cost" />
                    </Link>
                    <p />
                    <Link
                        to={
                            '/selectprojectcostcalculation/' +
                            this.props.project.projectId
                        }
                        activeClassName="active"
                    >
                        <MediumButton text="Change Calculation Method" />
                    </Link>
                    <p />
                    <Link to={'/requests/'} activeClassName="active">
                        <MediumButton text="View Requests" />
                    </Link>
                </div>
            );
        }
    }

    render() {
        return (
            <Fragment>
                <tr className="project-row">
                    <td>
                        <span
                            className={
                                'status-project-icon ' +
                                (this.props.project.projectActive
                                    ? 'active'
                                    : '')
                            }
                        />
                    </td>
                    <td>{this.props.project.projectName}</td>
                    <td id="project-manager-badge-cell">
                        {this.getManagerIcon()}
                    </td>
                    <td>{this.props.project.projectDescription}</td>
                    <td>{this.props.project.projectStatusName}</td>
                    <td className="action-buttons-cell">
                        <span
                            onClick={this.handleRotate}
                            className="open-project-details-button"
                        >
                            <Glyphicon
                                glyph="chevron-right"
                                className={this.state.isOpen ? 'rotate' : ''}
                                id="triangle-button"
                            />
                        </span>
                    </td>
                </tr>
                <tr
                    className={
                        'project-details project-row ' +
                        (this.state.isOpen ? 'open' : 'hide')
                    }
                >
                    <td> </td>
                    <td colSpan="2">
                        <div>
                            <b>Project Manager:&nbsp;</b>
                            {this.props.project.projectManagerName}
                            <br />
                            <b>Start date:&nbsp;</b>
                            {this.props.project.projectStartDate}
                            <br />
                            <b>Finish date:&nbsp;</b>
                            {this.props.project.projectFinishDate}
                            <br />
                        </div>
                    </td>
                    <td>
                        <div>
                            <b>Budget:&nbsp;</b>
                            {this.props.project.projectBudget}
                            <br /> <b>Calculation method:&nbsp;</b>
                            {this.props.project.projectCalculationMethod}
                            <br />
                            {this.props.project.button}
                            <br />
                        </div>
                    </td>
                    <td>{this.getManagerButtons()}
                        <div>
                            <AddUserToProject project={this.props.project.projectId}/>
                        </div>
                    </td>
                </tr>
            </Fragment>
        );
    }
}

export default ProjectsTableRow;
