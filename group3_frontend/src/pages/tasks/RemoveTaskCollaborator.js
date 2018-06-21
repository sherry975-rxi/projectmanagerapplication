import React, { Component } from 'react';
import AuthService from "../loginPage/AuthService";
import {toastr} from "react-redux-toastr";
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {updateAllTasks} from './../../actions/projectTasksActions';

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
            return <Redirect to="myprojects" />;
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
            return <Redirect to="myprojects" />;
        }).catch(err => {
            toastr.error('An error occurred!');
        });


    }

    handleClick = event => {
        if(this.state.text === 'Approve Removal') {
            this.approveRemovalRequest();
        } else if(this.state.text === 'Remove') {
            this.removeTaskCollaborator();
        }
        // console.log("PQP!");
        // console.log(this.props.collaborator);
        // console.log(this.props.task);
    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" >{this.state.text}</button>;
    }
}


const mapDispatchToProps = dispatch => {
    return bindActionCreators({ updateAllTasks }, dispatch);
};
export default connect(
    null,
    mapDispatchToProps
)(RemoveTaskCollaborator);