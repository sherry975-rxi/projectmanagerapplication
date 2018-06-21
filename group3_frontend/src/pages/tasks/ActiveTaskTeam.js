import React, { Component } from 'react';
import './activeTeam.css';

class ActiveTaskTeam extends Component {
    ListOfCollabs() {
        if (this.props.taskTeam.length > 0) {
            return this.props.taskTeam.map((activeTeamitem, index) => {
                return (
                    <tr className="line" key={index}>
                        <td>{activeTeamitem.taskCollaborator.name}</td>
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
