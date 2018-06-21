import React, { Component } from 'react';
import AuthService from "../loginPage/AuthService";

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

    }

    async removeTaskCollaborator() {

    }

    handleClick = event => {
        console.log("PQP!");
        console.log(this.props.collaborator);
        console.log(this.props.task);
    }

    render() {
        return <button onClick={this.handleClick} className="genericButton" >{this.state.text}</button>;
    }
}
export default RemoveTaskCollaborator;