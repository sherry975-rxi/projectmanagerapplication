import React, { Component } from 'react';
import { MenuItem, DropdownButton } from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { addCollaboratorToTask } from '../../actions/projectTasksActions';
import { get } from 'lodash';

class AvailableListOfCollaborators extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projCollab: '',
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
        this.AuthService = new AuthService();
    }

    // THis method attempts to fetch a pending task removal request from the collaborator.
    // If the request is found, it will approve it and remove collaborator
    // If there is an error (request not found), it will instead remove the collaborator
     getRequest(project, taskId, userId) {
        this.AuthService.fetch(
            `/projects/${project}/tasks/${taskId}/requests/user/${userId}`,
            {
                method: "GET"
            }
        ).then(response => {
            if (response.type === 'ASSIGNMENT'){

            this.approveAssignmentRequest(project, taskId,response.taskRequestDbId);
            }
        }).catch(error => {

            //this.removeTaskCollaborator();
        });

    }

     approveAssignmentRequest(project, taskId, requestId) {
        this.AuthService.fetch(
            `/projects/${project}/tasks/${taskId}/requests/${requestId}/approval`,
            {
                method: "PUT"
            }
        ).then(response => {
           // toastr.success('Assignment request approved!');
        }).catch(error => {
           // toastr.error('An error occurred!');
        });
    }

    showAddCollaboratorButton() {
        const collaborators = this.props.collaborators[this.props.taskId];
        if (!collaborators || (collaborators && !collaborators.length)) {
            return;
        } else {
            return this.renderDropdownButton('Add Collaborator', 0);
        }
    }

    handleClick = eventKey => {
        const collaboratorIndex = eventKey;
        const { project, taskId, tasksFilter } = this.props;
        const userDTO = {
            email: this.props.collaborators[this.props.taskId][
                collaboratorIndex
            ].collaborator.email
        };
        const userId = this.props.collaborators[this.props.taskId][
                collaboratorIndex
            ].collaborator.userID
        ;
        this.getRequest(project, taskId, userId);
        this.props.addCollaboratorToTask(project, taskId, userDTO, tasksFilter);

    };

    renderDropdownButton(title, i) {
        const collaborators = this.props.collaborators[this.props.taskId] || [];
        return (
            <DropdownButton
                className="buttonFinished"
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
            >
                {collaborators.map((user, index) => (
                    <MenuItem
                        eventKey={index}
                        key={index}
                        onSelect={this.handleClick}
                    >
                        {user.collaborator.name}
                    </MenuItem>
                ))}
            </DropdownButton>
        );
    }

    render() {
        return (
            <div className=" table-striped">
                {this.showAddCollaboratorButton()}
            </div>
        );
    }
}

export const mapStateToProps = state => {
    const collaborators = get(state, 'tasks.availableCollaboratorsForTask', []);
    return { collaborators, tasksFilter: get(state, 'filterReducer.filterType') };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ addCollaboratorToTask  }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AvailableListOfCollaborators);
