import React, { Component } from 'react';
import './activeTeam.css';
import RemoveTaskCollaborator from './RemoveTaskCollaborator';
import AuthService from './../../pages/loginPage/AuthService';
import {connect} from "react-redux";

class ActiveTaskTeam extends Component {
    constructor(props) {
        super(props);
        this.state = {
            activeTeam: []
        }

        this.getActiveTaskTeam = this.getActiveTaskTeam.bind(this);
        this.authService = new AuthService();
    }

    componentDidMount() {
        this.getActiveTaskTeam();
    }

    componentDidUpdate(prevProps) {
        if (this.props.id !== prevProps.id || this.props.task.taskTeam !== prevProps.task.taskTeam) {
            this.getActiveTaskTeam();
        }
    }

    async getActiveTaskTeam() {
        this.authService.fetch(`/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/activeTeam`,
            { method: 'GET' }
            ).then(response => {
                console.log(this.props.task);
                console.log(response)
                this.setState({
                    activeTeam: response,
                    message: response.error
                })
        });
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
                    />
                </td>
            );
        }
    };

    ListOfCollabs() {
        if (this.state.activeTeam.length > 0 && this.state.message == null) {
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
const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType }) }
export default connect(mapStateToProps, null) (ActiveTaskTeam);
