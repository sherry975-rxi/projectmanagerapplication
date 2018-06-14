import React, { Component } from 'react';
import { PanelGroup, Panel, DropdownButton, MenuItem } from 'react-bootstrap';
import './AccordionMenuTasks.css';
import * as Constants from '../utils/titleConstants';
import { handleTaskHeaders } from '../utils/handleList';
import MarkTaskAsFinished from './../../pages/tasks/MarkTaskAsFinished';
import { connect } from 'react-redux';
import MediumButton from './../../components/button/mediumButton';
import { Link } from 'react-router-dom';
import TaskTeam1 from '../../pages/tasks/ActiveTaskTeam';
import CreateRequest from './../../pages/requests/CreateRequest';
import DeleteTask from './../../pages/tasks/DeleteTask';
import AvailableListOfCollaborators from './../../pages/tasks/AvailableListOfCollaborators';
import ItemsButton from '../projectsTable/itemsButton.jsx';


class AccordionMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            rotated: false,
            arrow: 'notRotated',
            key: ''
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return Constants.TASKS.map(element => <th> {element}</th>);
    }


    toggle(key) {
        document.getElementById(key).className = this.state.rotated ? 'notRotated' : 'rotatedArrow'
        this.setState({ rotated: !this.state.rotated, key: key })

    }

    componentDidUpdate(prevProps, prevState) {

        if (prevState.key !== '' && prevState.key != this.state.key) {
            try {
                document.getElementById(prevState.key).className = 'notRotated'
                document.getElementById(this.state.key).className = 'rotatedArrow'
            }
            catch (error) {
                document.getElementById(this.state.key).className = this.state.rotated ? 'notRotated' : 'rotatedArrow'
            }
        }
    }

    renderList(list) {
        let key = 0;
        return (

            handleTaskHeaders(list).map((element, index) =>
                <Panel eventKey={key}>
                    <Panel.Heading>
                        <Panel.Title toggle><div className="taskContent" onClick={() => this.toggle(index)}> <table className="table table-content">
                            <thead>
                                <tr>
                                    <th> {element.taskID} </th>
                                    <th> {element.project} </th>
                                    <th> {element.description} </th>
                                    <th> <b>{element.state}</b> </th>
                                    <th> {element.startDate} </th>
                                    <th> {element.finishDate} </th>
                                    <th> <div id={index} className="notRotated"><span className="glyphicon glyphicon-chevron-right"
                                    /> </div></th>
                                    <a className="key">{key++}</a>

                                </tr>
                            </thead>
                        </table></div></Panel.Title>
                    </Panel.Heading>
                    <Panel.Body collapsible>
                        <table className="table table-details">
                            <thead>
                                <tr>
                                    <th>
                                        <p>
                                            <b>Creation date:</b> &nbsp;
                                                {element.creationDate}
                                        </p>
                                        <p>
                                            <b>Finish date:</b> &nbsp;
                                            {element.finishDate}
                                        </p>
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
                                            <b>Cancel date:</b> &nbsp;
                                            {element.cancelDate}
                                        </p>

                                    </th>
                                    <td>
                                        {<TaskTeam1
                                            id={element.taskID}
                                            project={element.project}
                                        />
                                        }
                                    </td>
                                    <th>
                                        <div align="right">
                                            {' '}
                                            <p />
                                            {element.state != 'FINISHED' ? (
                                                <MarkTaskAsFinished
                                                    id={element.taskID}
                                                    project={element.project}
                                                />
                                            ) : (
                                                    ''
                                                )}
                                            <a className="key">{key++}</a>
                                            <p />
                                            {element.state != 'FINISHED' ? (
                                                <CreateRequest
                                                    id={element.taskID}
                                                    project={element.project}
                                                />
                                            ) : (
                                                    ''
                                                )}
                                            <a className="key">{key++}</a>
                                            <p />
                                            {element.state != 'FINISHED' ? (
                                                <DeleteTask
                                                    id={element.taskID}
                                                    project={element.project}
                                                />
                                            ) : (
                                                    ''
                                                )}
                                            <a className="key">{key++}</a>
                                            <p />
                                            {element.state != 'FINISHED' ? (
                                                <AvailableListOfCollaborators
                                                    id={element.taskID}
                                                    project={element.project}
                                                />
                                            ) : (
                                                    ''
                                                )}
                                            <a className="key">{key++}</a>
                                        </div>
                                    </th>
                                </tr>
                            </thead>
                        </table>
                    </Panel.Body>
                </Panel>
            ));
    }

    renderDropdownButton(title, i) {

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

    }


    render() {
        return (
            <PanelGroup
                accordion
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
const mapStateToProps = state => { return ({ updated: state.projectTasks.tasksUpdated }) }
export default connect(mapStateToProps, null)(AccordionMenu)
