import React, { Component } from 'react';
import { PanelGroup, Panel, Button, Glyphicon } from 'react-bootstrap';
import './AccordionMenuRequests.css'
import * as Constants from '../utils/titleConstants';
import { connect } from 'react-redux';
import { handleRequestsHeaders } from '../../components/utils/handleList';
import ChangeProfile from '../../pages/users/ChangeProfile';

class AccordionMenuRequests extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1',
            rotated: false,
            arrow: 'notRotated'
        };
    }

    handleSelect(activeKey) {
        this.setState({ activeKey });
    }

    renderTitles() {
        return Constants.REQUESTS.map(element => <th> {element}</th>);
    }

    toggle(key) {
        this.setState({ rotated: !this.state.rotated });
        document.getElementById(key).className = this.state.rotated
            ? 'notRotated'
            : 'rotatedArrow';
    }

    renderList(list) {
        let key = 0;

        if(list.length > 0){

        return handleRequestsHeaders(list).map((element, index) => (
            <Panel eventKey={key}>
                <Panel.Heading>
                    <Panel.Title toggle>
                        <div
                            className="taskContent"
                            onClick={() => this.toggle(index)}
                        >
                            <table className="table table-content">
                                <thead>
                                    <tr>
                                        <th> {element.taskID} </th>
                                        <th> {element.taskDescription} </th>
                                        <th> {element.collaborator} </th>
                                        <th>
                                            <b>{element.type}</b>
                                        </th>
                                        <th> {element.approvalDate} </th>
                                        <th> {element.rejectDate} </th>
                                        {/* <th>
                                            {element[4].toString()
                                                ? 'Active'
                                                : 'Not Active'}
                                        </th> 
                                        <th>
                                            <div
                                                id={index}
                                                className="notRotated"
                                            >
                                                <span className="glyphicon glyphicon-chevron-right" />
                                            </div>
                                        </th> */}
                                        <th>
                                        <Button>
                                            <Glyphicon className="ok-button" glyph="ok" />
                                        </Button> &nbsp;
                                        <Button>
                                            <Glyphicon className="remove-button" glyph="remove" />
                                        </Button>
                                        </th>
                                        <a className="key">{key++}</a>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </Panel.Title>
                </Panel.Heading>
                {/* <Panel.Body collapsible>
                    <table className="table table-details">
                        <thead>
                            <tr>
                                <th>
                                    <p>
                                        <b>Name:</b> &nbsp;
                                        {element[0]}
                                    </p>
                                    <p>
                                        <b>E-mail:</b> &nbsp;
                                        {element[1]}
                                    </p>
                                    <p>
                                        <b>Profile:</b> &nbsp;
                                        {element[2]}
                                    </p>
                                    <p>
                                        <b>Function:</b> &nbsp;
                                        {element[3]}
                                    </p>
                                    <p>
                                        <b>Status:</b> &nbsp;
                                        {element[4].toString()
                                            ? 'Active'
                                            : 'Not Active'}
                                    </p>
                                </th>
                                <th>
                                    <div align="right">
                                        <p>
                                            <ChangeProfile email={element[1]} />
                                        </p>
                                    </div>
                                </th>
                            </tr>
                        </thead>
                    </table>
                </Panel.Body> */}
            </Panel>
        ));
    }
    else return <div/>
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
const mapStateToProps = state => {
    return { updated: state.users.usersUpdated };
};
export default connect(
    mapStateToProps,
    null
)(AccordionMenuRequests);
