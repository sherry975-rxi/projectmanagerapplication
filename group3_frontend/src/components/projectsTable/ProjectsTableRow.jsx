import React, { Component, Fragment } from 'react';
import { Link } from 'react-router-dom';
import {
    Glyphicon,
    MenuItem,
    DropdownButton,
    Button,
    DropdownMenu
} from 'react-bootstrap';
import AddUserToProject from '../../pages/projects/AddUserToProject';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { projectTableDetailsToogle } from '../../actions/metaActions';
import ItemsButton from './itemsButton';
import ActiveProjectTeam from '../../pages/projects/ActiveProjectTeam';

class ProjectsTableRow extends Component {
    constructor(props) {
        super(props);
    }

    handleRotate = () => {
        const index =
            this.props.index === this.props.openIndex
                ? undefined
                : this.props.index;
        const payload = { index: index };
        this.props.projectTableDetailsToogle(payload);
    };

    getManagerIcon() {
        if (
            this.props.project.projectManagerEmail ===
            this.props.email
        ) {
            return <span className="project-badge">PM</span>;
        } else return <div> </div>;
    }

    // If the user is project manager or director, they can view the project's budget and report cost calculation method
    getProjectInfo() {
        if ((this.props.project.projectManagerEmail === this.props.email) ||
            (this.props.profile === "DIRECTOR")
        ) {
            return (
                <div>
                    <strong>Budget:&nbsp;</strong>
                    {this.props.project.projectBudget}
                    <br /> <strong>Report Cost Calculation method:&nbsp;</strong>
                    {this.props.project.projectCalculationMethod}
                    <br /> <strong>Avaliable Calculation methods:&nbsp;</strong>
                    {this.props.project.projectAvaliableCalculationMethods}
                    <br />
                    {this.props.project.button}
                    <br />
                </div>
            );
        } else return <div> </div>;
    }


    getActiveProjectTeam() {
        if ((this.props.project.projectManagerEmail === this.props.email) ||
            (this.props.profile === "DIRECTOR")
        ) {
            return <ActiveProjectTeam project={this.props.project.projectId} />;
        } else 
        return <div> </div>;
    }


    // if the user is project manager, they can see a button to add a collaborator to the project
    addCollabToProjectButton() {
        if (
            this.props.project.projectManagerEmail ===
            this.props.email
        ) {
            return <AddUserToProject project={this.props.project.projectId} />;
        }
    }

    // as collaborator or director, the user can only see the project's tasks. As Project manager, they can create tasks and change
    // cost calculation methods
    renderDropdownButton(title, i) {
        if (
            this.props.project.projectManagerEmail ===
            this.props.email
        ) {
            return (
                <DropdownButton
                    className="option"
                    bsStyle={title.toLowerCase()}
                    title={title}
                    key={i}
                    id={`dropdown-basic-${i}`}
                >
                    <MenuItem className="items-menu" onClick={this.toggle}>
                        <Link
                            to={
                                '/projects/' +
                                this.props.project.projectId +
                                '/tasks'
                            }
                            activeClassName="active"
                        >
                            <ItemsButton text="View tasks" />
                        </Link>
                    </MenuItem>

                    <MenuItem className="items-menu" onClick={this.toggle}>
                        <Link
                            to={
                                '/projects/' +
                                this.props.project.projectId +
                                '/addtask'
                            }
                            activeClassName="active"
                        >
                            <ItemsButton text="Create task" />
                        </Link>
                    </MenuItem>

                    <MenuItem className="items-menu" onClick={this.toggle}>
                        <Link
                            to={'/projectcost/' + this.props.project.projectId}
                            activeClassName="active"
                        >
                            <ItemsButton text="Project Cost" />
                        </Link>
                    </MenuItem>

                    <MenuItem className="items-menu" onClick={this.toggle}>
                        <Link
                            to={
                                '/selectprojectcostcalculation/' +
                                this.props.project.projectId
                            }
                            activeClassName="active"
                        >
                            <ItemsButton text="Change Calculation Method" />
                        </Link>
                    </MenuItem>

                    <MenuItem className="items-menu" onClick={this.toggle}>
                        <Link to={'/requests/'}>
                            <ItemsButton text="View Requests" />
                        </Link>
                    </MenuItem>
                </DropdownButton>
            );
        } else if ((this.props.profile === "COLLABORATOR") || (this.props.profile === "DIRECTOR") ) {
            return (
            <DropdownButton
                className="option"
                bsStyle={title.toLowerCase()}
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
            >
                <MenuItem className="items-menu" onClick={this.toggle}>
                    <Link
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/tasks'
                        }
                        activeClassName="active"
                    >
                        <ItemsButton text="View tasks" />
                    </Link>
                </MenuItem>
            </DropdownButton>);
        }
    }

    render() {
        const isOpen = this.props.openIndex === this.props.index;

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
                                className={isOpen ? 'rotate' : ''}
                                id="triangle-button"
                            />
                        </span>
                    </td>
                </tr>
                <tr
                    className={
                        'project-details project-row ' +
                        (isOpen ? 'open' : 'hide')
                    }
                >
                    <td colSpan="2">
                        <div className="project-details">
                            <strong>Project Manager:&nbsp;</strong>
                            {this.props.project.projectManagerName}
                            <br />
                            <strong>Start date:&nbsp;</strong>
                            {this.props.project.projectStartDate}
                            <br />
                            <strong>Finish date:&nbsp;</strong>
                            {this.props.project.projectFinishDate}
                            <br />
                        </div>
                    </td>
                    <td colSpan="1">{this.getProjectInfo()}</td>
                    <td >
                            {this.getActiveProjectTeam()}
                    </td>
                    <td colSpan="2">
                        <p>{this.renderDropdownButton('Options', 0)}
                        </p>
                        {this.addCollabToProjectButton()}
                    </td>
                </tr>
            </Fragment>
        );
    }
}

const mapStateToProps = state => {
    return { profile: state.authenthication.user.userProfile,
             email: state.authenthication.user.email };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ projectTableDetailsToogle }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ProjectsTableRow);
