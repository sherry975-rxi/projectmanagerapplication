import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import './activeProjectTeam.css';
import {connect} from "react-redux";
import {updateProjectTeam} from "../../actions/projectTeamActions";
import {bindActionCreators} from "redux";
import "../../components/button/genericButton.css";

class ActiveProjectTeam extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.props.updateProjectTeam(this.props.project);
    }

    handleClick = event =>{
        fetch(
            `/projects/${this.props.project}/team/${event.target.id}`,
            {
                headers: { Authorization: localStorage.getItem('id_token') },
                method: 'delete' }
        ).then(response => {
                if(response.status === 200) {
                    this.props.updateProjectTeam(this.props.project)
                }
            }

        )
    }

    ListOfCollabs() {
        if (this.props.projectTeam.length > 0) {
            return this.props.projectTeam.map((activeTeamitem, index) => {
                return (
                    <tr className="line" key={index}>
                        <td> {activeTeamitem.collaborator.name}</td>
                        <td>
                            <button onClick={this.handleClick} id={activeTeamitem.projectCollaboratorId} className="genericButton">Remove</button>
                        </td>
                    </tr>
                );
            });
        } else {
            return <tr />;
        }
    }

    render() {
        return (
            <table className="table table-hover projectteam">
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

const mapStateToProps = state => {
    return {
        projectTeam: state.projectTeam.projectTeam
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ updateProjectTeam }, dispatch);
};

export default connect(mapStateToProps, mapDispatchToProps)(ActiveProjectTeam);
