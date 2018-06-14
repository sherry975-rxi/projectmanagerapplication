import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import './activeTeam.css'


class ActiveProjectTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeTeam: [],
        };

        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getActiveTaskTeam();
    }

    async getActiveTaskTeam() {
        this.AuthService.fetch(
            `/projects/${this.props.project}/activeTeam`,

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
                        <td> {activeTeamitem.collaborator.name}</td>
                        <td> {activeTeamitem.costPerEffort}</td>
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
                        <th> Active Team </th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Cost per Effort:</th>
                    </tr>
                </thead>
                <tbody>{this.ListOfCollabs()}</tbody>
            </table>
        );
    }
}

export default ActiveProjectTeam;
