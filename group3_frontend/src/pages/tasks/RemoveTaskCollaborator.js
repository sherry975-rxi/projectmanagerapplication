import React, { Component } from 'react';
import AuthService from "../loginPage/AuthService";
import {toastr} from "react-redux-toastr";
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {updateAllTasks} from './../../actions/projectTasksActions';
import {updateMyAllTasks, updateMyFinishedTasks, updateMyOngoingTasks} from "../../actions/userTasksActions";
import {
    updateFinishedTasks, updateNotStartedTasks, updateStandByTasks,
    updateUnfinishedTasks, updateExpiredTasks, getProjectTasksByFilter
} from "../../actions/projectTasksActions";

class RemoveTaskCollaborator extends Component {
    constructor(props) {
        super(props);
        this.state = {
            text: 'Loading...'
        };

        this.getRequest = this.getRequest.bind(this);
        this.authService=new AuthService();
    }

    componentDidMount() {
        this.getRequest();
    }

    async getRequest() {
        this.authService.fetch(
            `/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/requests/user/${this.props.collaborator.taskCollaborator.userID}`,
            {
                method: "GET"
            }
        ).then(response => {
            this.setState({
                request: response,
                text: 'Approve Removal'
            });
        }).catch(error => {
            this.setState({
                text: 'Remove'
            });
        });

    }

    async approveRemovalRequest() {
        this.authService.fetch(
            `/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/requests/${this.props.request.taskRequestDbId}/approval`,
            {
                method: "PUT"
            }
        ).then(response => {
            toastr.success('Removal request approved!');
            this.updateTasks();
            this.props.updateTeam();
        }).catch(error => {
            toastr.error('An error occurred!');
        });
    }

    async removeTaskCollaborator() {
        this.authService.fetch(
            `/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/removeCollab`,
            {
                body: JSON.stringify(this.props.collaborator),
                method: 'PATCH'
            }
        ).then(responseData => {
            toastr.success('Collaborator Successfully removed!');
            this.updateTasks();
            this.props.updateTeam();
        }).catch(err => {
            toastr.error('An error occurred!');
        });


    }

    updateTasks() {
        console.log(this.props.filter);
        switch (this.props.filter) {

            case 'all':
                this.props.updateAllTasks(this.props.task.project);
                break;
            case 'finished':
                this.props.updateFinishedTasks(this.props.task.project);
                break;
            case 'unfinished':
                this.props.updateUnfinishedTasks(this.props.task.project);
                break;
            case 'notStarted':
                this.props.updateNotStartedTasks(this.props.task.project);
                break;
            case 'withoutCollaborators':
                this.props.updateStandByTasks(this.props.task.project);
                break;
            case 'expired':
                this.props.updateExpiredTasks(this.props.task.project);
                break;
            case 'myAll':
                this.props.updateMyAllTasks(this.AuthService.getUserId());
                break;
            case 'myFinished':
                this.props.updateMyFinishedTasks(this.AuthService.getUserId());
                break;
            case 'myUnfinished':
                this.props.updateMyOngoingTasks(this.AuthService.getUserId());
                break;
        }

    }

    handleClick = event => {
        if(this.state.text === 'Approve Removal') {
            this.approveRemovalRequest();
        } else if(this.state.text === 'Remove') {
            this.removeTaskCollaborator();
        }
    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" >{this.state.text}</button>;
    }
}
const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType }) }
const mapDispatchToProps = dispatch => bindActionCreators({
    getProjectTasksByFilter,
    updateFinishedTasks,
    updateAllTasks,
    updateStandByTasks,
    updateNotStartedTasks,
    updateUnfinishedTasks,
    updateExpiredTasks,
    updateMyAllTasks,
    updateMyFinishedTasks,
    updateMyOngoingTasks }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(RemoveTaskCollaborator);