import React, { Component } from 'react';
import './OngoingTasks.css';
import AuthService from '../loginPage/AuthService';
import './activeTeam.css'


class ActiveTaskTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeTeam: [],
        };

        //this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getTaskTeam();
    }

    async getTaskTeam() {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}/activeTeam`,

            { method: 'GET' }
        ).then(responseData => {
            this.setState({
                activeTeam: responseData,
                message: responseData.error
            });
        });
    }

    ListOfCollabs() {
        if (this.state.activeTeam.length > 0) {
            return this.state.activeTeam.map((activeTeamitem, index) => {
                return (
                    <tr className="line" key={index}>
                        <td> {activeTeamitem.projCollaborator.collaborator.name}</td> <th>{activeTeamitem.projCollaborator.collaborator.email}</th>
                    </tr >
                );
            });
        }
        else {
            return ('')
        }
    }

    render() {
        return (
            <table className="table table-hover team">
                <thead>
                    <tr>
                        <th>Active Team:</th>
                    </tr>
                </thead>
                <tbody>{this.ListOfCollabs()}</tbody>
            </table>
        );
    }
}

export default ActiveTaskTeam;
