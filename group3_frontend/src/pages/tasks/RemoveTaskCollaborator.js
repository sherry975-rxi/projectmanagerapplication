import React, { Component } from 'react';
import AuthService from "../loginPage/AuthService";
import {toastr} from "react-redux-toastr";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import { refreshTasksByFilter } from "../../actions/refreshTasksActions";

class RemoveTaskCollaborator extends Component {
    constructor(props) {
        super(props);

        this.authService=new AuthService();
    }

    // THis method attempts to fetch a pending task removal request from the collaborator.
    // If the request is found, it will approve it and remove collaborator
    // If there is an error (request not found), it will instead remove the collaborator
    async getRequest() {
        this.authService.fetch(
            `/projects/${this.props.task.project}/tasks/${this.props.task.taskID}/requests/user/${this.props.collaborator.taskCollaborator.userID}`,
            {
                method: "GET"
            }
        ).then(response => {

            this.approveRemovalRequest();
        }).catch(error => {

            this.removeTaskCollaborator();
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
            this.props.refreshTasksByFilter(this.props.task.project, this.props.filter);
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
            this.props.refreshTasksByFilter(this.props.task.project, this.props.filter);
        }).catch(err => {
            toastr.error('An error occurred!');
        });


    }


    handleClick = event => {

        this.getRequest();

    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" > Remove </button>;
    }
}
const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType }) }
const mapDispatchToProps = dispatch => bindActionCreators({
    refreshTasksByFilter }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(RemoveTaskCollaborator);