import React, { Component, Fragment } from 'react';
import { Link } from 'react-router-dom';
import { Glyphicon, MenuItem, DropdownButton } from 'react-bootstrap';
import AddUserToProject from '../../pages/projects/AddUserToProject';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { projectTableDetailsToogle } from '../../actions/metaActions';
import ItemsButton from './itemsButton';
import ActiveProjectTeam from '../../pages/projects/ActiveProjectTeam';
import SelectCalculationMethods from '../../pages/projectCost/SelectCalculationMethods';
import { updateUnassignedProjCollabs } from '../../actions/projCollabsWoutTasksActions';

class ProjectsTableRow extends Component {
    handleRotate = () => {
        const index =
            this.props.index === this.props.openIndex
                ? undefined
                : this.props.index;
        const payload = { index: index };
        this.props.projectTableDetailsToogle(payload);
    };

    getManagerIcon() {
        if (this.props.project.projectManagerEmail === this.props.email) {
            return <span className="project-badge">PM</span>;
        } else return <div> </div>;
    }

    // If the user is project manager or director, they can view the project's budget and report cost calculation method
    getProjectInfo() {
        if (
            this.props.project.projectManagerEmail === this.props.email ||
            this.props.profile === 'DIRECTOR'
        ) {
            return (
                <div>
                    <strong>Budget:&nbsp;</strong>
                    {this.props.project.projectBudget}
                    <br />{' '}
                    <strong>Report Cost Calculation method:&nbsp;</strong>
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

    changeCalculationMethod() {
        if (this.props.profile === 'DIRECTOR') {
            return <SelectCalculationMethods project={this.props.project} />;
        } else return <div> </div>;
    }

    getActiveProjectTeam() {
        if (
            this.props.project.projectManagerEmail === this.props.email ||
            this.props.profile === 'DIRECTOR'
        ) {
            return <ActiveProjectTeam project={this.props.project.projectId} />;
        } else return <div> </div>;
    }

    // if the user is project manager, they can see a button to add a collaborator to the project
    addCollabToProjectButton() {
        if (this.props.project.projectManagerEmail === this.props.email) {
            return <AddUserToProject project={this.props.project.projectId} />;
        }
    }

    handleClick(){
        this.props.updateUnassignedProjCollabs(this.props.project.projectId)
    }

    // as collaborator or director, the user can only see the project's tasks. As Project manager, they can create tasks and change
    // cost calculation methods
    renderDropdownButton(title, i) {
        if (this.props.project.projectManagerEmail === this.props.email) {
            return (
                <DropdownButton
                    className="option"
                    title={title}
                    key={i}
                    id={`dropdown-basic-${i}`}
                >
                    <Link
                        className="items-menu"
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/tasks'
                        }
                    >
                        <ItemsButton text="View tasks" />
                    </Link>

                     <Link
                        className="items-menu"
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/UnassignedProjCollab'
                        } onClick = {this.handleClick.bind(this)}
                    >
                        <ItemsButton text="Unassigned" />
                    </Link>

                    <Link
                        className="items-menu"
                        to={
                            '/projects/' +
                            this.props.project.projectId +
                            '/addtask'
                        }
                    >
                        <ItemsButton text="Create task" />
                    </Link>

                    <Link
                        className="items-menu"
                        to={'/projectcost/' + this.props.project.projectId}
                    >
                        <ItemsButton text="Project Cost" />
                    </Link>

                    <Link
                        className="items-menu"
                        to={
                            '/selectprojectcostcalculation/' +
                            this.props.project.projectId
                        }
                    >
                        <ItemsButton text="Change Calculation Method" />
                    </Link>

                    <Link className="items-menu" to={'/requests/'}>
                        <ItemsButton text="View Requests" />
                    </Link>
                </DropdownButton>
            );
        } else if (
            this.props.profile === 'COLLABORATOR' ||
            this.props.profile === 'DIRECTOR'
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
                        >
                            <ItemsButton text="View tasks" />
                        </Link>
                    </MenuItem>
                </DropdownButton>
            );
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
                    <td>{this.getActiveProjectTeam()}</td>
                    <td colSpan="2">
                        <div align="center">{this.renderDropdownButton('Options', 0)}</div>
                        <div align="center">{this.addCollabToProjectButton()}</div>
                        <div align="center">{this.changeCalculationMethod()}</div>
                    </td>
                </tr>
            </Fragment>
        );
    }
}

const mapStateToProps = state => {
    return {
        profile: state.authenthication.user.userProfile,
        email: state.authenthication.user.email
    };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ projectTableDetailsToogle, updateUnassignedProjCollabs }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ProjectsTableRow);
