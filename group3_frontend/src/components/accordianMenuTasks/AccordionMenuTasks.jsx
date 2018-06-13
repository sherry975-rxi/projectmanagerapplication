import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import './AccordionMenuTasks.css';
import * as Constants from '../utils/titleConstants';
import { handleTaskHeaders } from '../utils/handleList';
import MarkTaskAsFinished from './../../pages/tasks/MarkTaskAsFinished';
import { connect } from 'react-redux';
import MediumButton from './../../components/button/mediumButton';
import { Link } from 'react-router-dom';

import TaskTeam1 from './../../pages/tasks/ActiveTaskTeam.1';


class AccordionMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            rotated: false,
            arrow: 'notRotated',
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return Constants.TASKS.map(element => <th> {element}</th>);
    }


    toggle(key) {
        this.setState({ rotated: !this.state.rotated })
        document.getElementById(key).className = this.state.rotated ? 'notRotated' : 'rotatedArrow'
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
                                    <th> <div id={index}><span className="glyphicon glyphicon-chevron-right"
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
                                    <th>
                                        {<TaskTeam1
                                            id={element.taskID}
                                            project={element.project}
                                        />
                                    }
                                </th>
                                <th>
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
                                </th>
                            </tr>
                        </thead>
                    </table>
                </Panel.Body>
            </Panel>
        ));
    }

    // getTeam(task){
    //     // this.setState({
    //     //     team: task.taskTeam
    //     // });

    //     // return this.state.team.map((taskCollaborator) => {
    //     //     return taskCollaborator.projCollaborator.collaborator.name;
    //     // });

    //       for(var i = 0; i < task.taskTeam.length; i++){
    //          return(
    //              <ul value={task.taskTeam[i]}>
    //              {task.taskTeam[i].projCollaborator.collaborator.name}
    //              </ul>
    //          );


    //      }

    // }


    // async loadTaskTeamFromServer(task) {
    //     this.AuthService.fetch(
    //         `/projects/${task.project.projectId}/tasks/${task.taskID}/activeTeam`,
    //         {
    //             method: 'get'
    //         }
    //     ).then((responseData) => { responseData });

    // }

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
