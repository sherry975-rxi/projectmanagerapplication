import React, { Component } from 'react';
import { PanelGroup, Panel } from 'react-bootstrap';
import './AccordionMenuTasks.css';
import * as Constants from '../utils/titleConstants';
import SmallButton from '../button/smallButton.jsx';
import { handleTaskHeaders } from '../utils/handleList';
import MarkTaskAsFinished from './../../pages/tasks/MarkTaskAsFinished';
import AuthService from './../../pages/loginPage/AuthService';


class AccordionMenu extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            type: 'Ongoing'
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return Constants.TASKS.map(element => <th> {element}</th>);
    }

    static getDerivedStateFromProps(props, prevState) {
        let newState = { type: props }
        return newState ? props : prevState
    }

    renderList(list, type) {
        console.log(this.state.type)

        let key = 0;

        return (
            handleTaskHeaders(list).map((element) =>
                <Panel eventKey={key}>
                    <Panel.Heading>
                        <Panel.Title toggle><div className="taskContent"> <table className="table table-content">
                            <thead>
                                <tr>
                                    <th> {element.taskID} </th>
                                    <th> {element.project} </th>
                                    <th> {element.description} </th>
                                    <th> <b>{element.state}</b> </th>
                                    <th> {element.startDate} </th>
                                    <th> {element.state != 'FINISHED' ? <MarkTaskAsFinished
                                        id={element.taskID}
                                        project={element.project}
                                    /> : ''}
                                        <a className="key">{key++}</a>
                                        {console.log(key)}
                                    </th>
                                </tr>
                            </thead>
                        </table></div></Panel.Title>
                    </Panel.Heading>
                    <Panel.Body collapsible>
                        <div className="bodyContent">

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
                            <p>
                                <b>Team:</b> &nbsp;
                                {/* {this.getTeam(element)} */}
                                {/* {this.loadTaskTeamFromServer(element)} */}
                                {console.log("Teste")}
                                {console.log(element.taskTeam)}
                            </p>
                        </div>
                    </Panel.Body>
                </Panel>
            )
        )

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

export default AccordionMenu;
