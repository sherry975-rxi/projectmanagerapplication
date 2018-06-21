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


    ListOfCollabs() {

        if (this.state.message == null) {
            return this.state.activeTeam.map((activeTeamitem, index) => {
                return (
                    <tr className="line" key={index}>
                        <td>{activeTeamitem.taskCollaborator.name}</td>
                        <td><RemoveTaskCollaborator task={this.props.task} collaborator={activeTeamitem}/></td>
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
