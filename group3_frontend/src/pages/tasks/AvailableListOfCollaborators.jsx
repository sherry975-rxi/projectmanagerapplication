import React, { Component } from 'react';
import { MenuItem, DropdownButton } from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';

class AvailableListOfCollaborators extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projTeam: [],
            projCollab: '',
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getProjTeam();
    }

    showAddCollaboratorButton() {
        if (this.state.projTeam.length == null) {
            return;
        } else {
            return this.renderDropdownButton('Add Collaborator', 0);
        }
    }

    // Load users from database
    getProjTeam() {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${
                this.props.id
            }/collabsAvailableForTask`,
            { method: 'get' }
        ).then(responseData => {
            this.setState({
                projTeam: responseData,
                message: responseData.error
            });
        });
    }

    handleClick(event) {
        const userDTO = {
            email: this.state.projTeam[event].collaborator.email
        };

        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}/addCollab`,
            {
                method: 'post',
                body: JSON.stringify(userDTO)
            }
        )
            .then(res => {
                if (res.taskFinished === false) {
                    toastr.success('Collaborator was added to task');
                    this.getProjTeam();
                    window.location.href = `/projects/${
                        this.props.project
                    }/tasks`;
                }
            })
            .catch(err => {
                toastr.error('Already a Task Collaborator');
            });
    }

    renderDropdownButton(title, i) {
        return (
            <DropdownButton
                className="buttonFinished"
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
            >
                {this.state.projTeam.map((projTeamitem, index) => (
                    <MenuItem
                        eventKey={index}
                        key={index}
                        onSelect={this.handleClick.bind(this)}
                    >
                        {projTeamitem.collaborator.name}
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

export default AvailableListOfCollaborators;
