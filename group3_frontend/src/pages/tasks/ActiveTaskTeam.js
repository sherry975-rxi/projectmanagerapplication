import React, { Component } from 'react';
import './OngoingTasks.css';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';


class TaskTeam extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            activeTeam: [],
            project: {}
        };

        //this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getTaskTeam();
    }

    async getTaskTeam()  {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/${this.props.match.params.taskID}/activeTeam`,
            { method: 'GET' }
        ).then(responseData => {
            this.setState({
                activeTeam: responseData,
                message: responseData.error
            });
        });
    }

    ListOfCollabs() {
       if (this.state.activeTeam.length > 0){
        return this.state.activeTeam.map((activeTeamitem, index) => {
            return (
                <tr className="line" key={index}>
                    {/* <td>{activeTeamitem}</td> */}
                    <td>{activeTeamitem.projCollaborator.collaborator.name}</td>
                </tr>
            );
        });
    }
    else {
        return ('')
    }
    }

    render() {
        return (
            <div className=" table-striped">
                <h3>
                    <b>Active Task Team</b>
                </h3>
                <table className="table table-hover">
                    <thead>
                        <tr>
                            {/* <th>Collab email</th> */}
                            <th>Collab name</th>
                        </tr>
                    </thead>
                    <tbody>{this.ListOfCollabs()}</tbody>
                </table> 
            </div>
        );
    }
}

export default TaskTeam;
