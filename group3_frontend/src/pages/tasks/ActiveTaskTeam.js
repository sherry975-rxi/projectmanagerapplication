import React, { Component } from 'react';
import './activeTeam.css';
import RemoveTaskCollaborator from './RemoveTaskCollaborator';
import AuthService from './../../pages/loginPage/AuthService';

class ActiveTaskTeam extends Component {
    constructor(props) {
        super(props);

        this.authService = new AuthService();
    }

    removeTaskCollaborator = activeTeamItem => {
        if (
            this.props.task.currentProject.projectManager.userID.toString() ===
            this.authService.getUserId().toString()
        ) {
            return (
                <td>
                    <RemoveTaskCollaborator
                        task={this.props.task}
                        collaborator={activeTeamItem}
                        reload={this.getActiveTaskTeam}
                    />
                </td>
            );
        }
    };

    ListOfCollabs() {
        if (this.props.taskTeam.length > 0) {
            return this.props.taskTeam.map((activeTeamitem, index) => {
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
