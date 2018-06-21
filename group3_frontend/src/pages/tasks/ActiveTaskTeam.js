import React, { Component } from 'react';
import './activeTeam.css';
import AuthService from "../loginPage/AuthService";
import RemoveTaskCollaborator from "./RemoveTaskCollaborator";

class ActiveTaskTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeTeam: []
        }

        this.getActiveTaskTeam = this.getActiveTaskTeam.bind(this);
        this.authService=new AuthService();
    }

    componentDidMount() {
        this.getActiveTaskTeam();
    }

    async getActiveTaskTeam() {
        this.authService.fetch(
            `/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/activeTeam`,
            { method: "GET" }
        ).then(response => {
            this.setState({
                activeTeam: response,
                message: response.error
            })
        });
    }

    removeTaskCollaborator(activeTeamItem) {

        if(this.props.task.currentProject.projectManager.userID.toString() === this.authService.getUserId().toString()) {
            return <td><RemoveTaskCollaborator task={this.props.task} collaborator={activeTeamItem}/></td>;
        }
    }

    ListOfCollabs() {

        if (this.state.message == null) {
            return this.state.activeTeam.map((activeTeamitem, index) => {
                return (
                    <tr className="line" key={index}>
                        <td>{activeTeamitem.taskCollaborator.name}</td>
                        {this.removeTaskCollaborator(activeTeamitem)}
                    </tr>
                );
            });
        } else {
            return <tr />;
        }
    }

    render() {
        return (
            <table className="table table-hover taskteam">
                <thead>
                    <tr>
                        <th>
                            <b> Active Team </b>
                        </th>
                    </tr>
                </thead>
                <tbody>{this.ListOfCollabs()}</tbody>
            </table>
        );
    }
}

export default ActiveTaskTeam;
