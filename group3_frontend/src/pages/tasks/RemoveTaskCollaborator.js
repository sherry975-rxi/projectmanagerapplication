import React, { Component } from 'react';
import AuthService from "../loginPage/AuthService";
import {toastr} from "react-redux-toastr";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import { refreshTasksByFilter } from "../../actions/refreshTasksActions";

class RemoveTaskCollaborator extends Component {
    constructor(props) {
        super(props);
        this.state = {
            text: 'Find request'
        };

        this.getRequest = this.getRequest.bind(this);
        this.authService=new AuthService();
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

        switch(this.state.text) {
            case 'Find request':
                this.getRequest();
                break;
            case 'Approve Removal':
                this.approveRemovalRequest();
                break;
            case 'Remove':
                this.removeTaskCollaborator();
                break;

        }

    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" >{this.state.text}</button>;
    }
}
const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType }) }
const mapDispatchToProps = dispatch => bindActionCreators({
    refreshTasksByFilter }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(RemoveTaskCollaborator);