import React, { Component } from 'react';
import { PanelGroup, Panel, Glyphicon } from 'react-bootstrap';
import './AccordionMenuTasks.css';
import * as Constants from '../utils/titleConstants';
import { handleTaskHeaders } from '../utils/handleList';
import MarkTaskAsFinished from './../../pages/tasks/MarkTaskAsFinished';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import CreateRequest from './../../pages/requests/CreateRequest';
import DeleteTask from './../../pages/tasks/DeleteTask';
import AvailableListOfCollaborators from './../../pages/tasks/AvailableListOfCollaborators';
import ActiveTaskTeam from '../../pages/tasks/ActiveTaskTeam';
import AuthService from './../../pages/loginPage/AuthService';
import { getAvailableCollaboratorsForTask } from '../../actions/projectTasksActions';
import { bindActionCreators } from 'redux';
import EditTask from './../../pages/tasks/editTask/EditTask';

class AccordionMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            rotated: false,
            arrow: 'notRotated',
            key: ''
        };
        this.AuthService = new AuthService();
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return Constants.TASKS.map((element, index) => (
            <th key={index}> {element}</th>
        ));
    }

    toggle(key, task) {
        document.getElementById(key).className = this.state.rotated
            ? 'notRotated'
            : 'rotatedArrow';
        this.setState({ rotated: !this.state.rotated, key: key });
        if (!this.state.rotated) {
            this.props.getAvailableCollaboratorsForTask(
                task.project,
                task.taskID
            );
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.key !== '' && prevState.key !== this.state.key) {
            try {
                document.getElementById(prevState.key).className = 'notRotated';
                document.getElementById(this.state.key).className =
                    'rotatedArrow';
            } catch (error) {
                document.getElementById(this.state.key).className = this.state
                    .rotated
                    ? 'notRotated'
                    : 'rotatedArrow';
            }
        }
    }

    renderDeleteTaskButton(element) {
        if (
            element.currentProject.projectManager.email ===
            this.AuthService.getProfile()
                .sub /* ||
            this.props.profile === 'DIRECTOR' */
        ) {
            let authrorizedTaskStates =
                element.state === 'PLANNED' ||
                element.state === 'CREATED' ||
                element.state === 'READY';

            return authrorizedTaskStates === true ? (
                <DeleteTask id={element.taskID} project={element.project} />
            ) : (
                    ''
                );
        } else return <div> </div>;
    }

    renderCreateAssignmentRequestTaskButton(element) {
        if (
            element.currentProject.projectManager.email !==
            this.AuthService.getProfile()
                .sub /* ||
            this.props.profile === 'DIRECTOR' */
        ) {
            return element.state !== 'FINISHED' ? (
                <CreateRequest id={element.taskID} project={element.project} />
            ) : (
                    ''
                );
        } else return <div> </div>;
    }

    renderTaskButtons(element, key) {
        if (this.props.profile === 'COLLABORATOR') {
            return (
                <div align="right">
                    <p />
                    {element.state !== 'FINISHED' ? (
                        <Link
                            to={
                                '/projects/' +
                                element.project +
                                '/tasks/' +
                                element.taskID +
                                '/reports'
                            }
                        >
                            <button className="buttonFinished">View Reports</button>
                        </Link>
                    ) : (
                            ''
                        )}
                    <a className="key">{key++}</a>
                    <p />
                    {element.state !== 'FINISHED' ? (
                        <Link
                            to={
                                '/projects/' +
                                element.project +
                                '/tasks/' +
                                element.taskID +
                                '/createreport'
                            }
                        >
                            <button className="buttonFinished">
                                Create Report
                            </button>
                        </Link>
                    ) : (
                            ''
                        )}
                    <a className="key">{key++}</a>
                    <p />

                    <Link
                        to={
                            '/projects/' +
                            element.project +
                            '/tasks/' +
                            element.taskID +
                            '/dependencies'
                        }
                    >
                        <button className="buttonFinished">
                            View Dependencies
                        </button>
                    </Link>

                    <a className="key">{key++}</a>
                    <p />
                    {element.state !== 'FINISHED' ? (
                        <MarkTaskAsFinished
                            id={element.taskID}
                            project={element.project}
                        />
                    ) : (
                            ''
                        )}
                    <a className="key">{key++}</a>
                    <p />
                    {this.renderCreateAssignmentRequestTaskButton(element)}
                    <a className="key">{key++}</a>
                    <p />
                    {this.renderDeleteTaskButton(element)}
                    <a className="key">{key++}</a>
                    <p />
                    {element.state !== 'FINISHED' ? (
                        <AvailableListOfCollaborators
                            taskId={element.taskID}
                            project={element.project}
                        />
                    ) : (
                            ''
                        )}
                    <a className="key">{key++}</a>
                </div>
            );
        } else {
            return <div align="right"> </div>;
        }
    }

    renderList(list) {
        let key = 0;
        return handleTaskHeaders(list).map((element, index) => (
            <Panel eventKey={key} key={index}>
                <Panel.Heading>
                    <Panel.Title toggle>
                        <div
                            className="taskContent"
                            onClick={() => this.toggle(index, element)}
                        >
                            <table className="table table-content">
                                <thead>
                                    <tr>
                                        <th> {element.taskID} </th>
                                        <th> {element.project} </th>
                                        <th> {element.description} </th>
                                        <th>
                                            {' '}
                                            <b>{element.state}</b>{' '}
                                        </th>
                                        <th> {element.startDate} </th>
                                        <th> {element.finishDate} </th>
                                        <th>
                                            {' '}
                                            <div
                                                id={index}
                                                className="notRotated"
                                            >
                                                <span className="glyphicon glyphicon-chevron-right" />
                                            </div>
                                        </th>
                                        <th className="key">{key++}</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </Panel.Title>
                </Panel.Heading>
                <Panel.Body collapsible>
                    <table className="table table-details">
                        <thead>
                            <tr>
                                <th>

                                    <EditTask task={element} /> &nbsp;

                                    <p>
                                        <b>Estimated Effort:</b> &nbsp;
                                        {element.estimatedTaskEffort}
                                    </p>
                                    <p>
                                        <b>Budget:</b> &nbsp;
                                        {element.taskBudget}
                                    </p>
                                    <p>
                                        <b>Estimated start date:</b> &nbsp;
                                        {element.estimatedTaskStartDate}
                                    </p>
                                    <p>
                                        <b>Estimated finish date:</b> &nbsp;
                                        {element.taskDeadline}
                                    </p>
                                    <p>
                                        <b>Start date:</b> &nbsp;
                                        {element.startDate}
                                    </p>
                                    <br />
                                    <br />
                                </th>
                                <td>
                                    {
                                        <ActiveTaskTeam
                                            id={element.taskID}
                                            task={element}
                                        />
                                    }
                                </td>
                                <th>{this.renderTaskButtons(element, key)}</th>
                            </tr>
                        </thead>
                    </table>
                </Panel.Body>
            </Panel>
        ));
    }

    render() {
        return (
            <PanelGroup
                accordion
                className="accordion-menu-tasks"
                id="accordion-controlled-example"
                activeKey={this.state.activeKey}
                onSelect={this.handleSelect.bind(this)}
            >
                <Panel eventKey="1">
                    <table className="table table-title">
                        <thead>
                            <tr>{this.renderTitles()}</tr>
                        </thead>
                    </table>
                </Panel>
                {this.renderList(this.props.list)}
            </PanelGroup>
        );
    }
}
const mapStateToProps = state => {
    return {
        profile: state.authenthication.user.userProfile
    };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ getAvailableCollaboratorsForTask }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AccordionMenu);
