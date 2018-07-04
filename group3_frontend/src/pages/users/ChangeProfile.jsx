import React, { Component } from 'react';
import { MenuItem, DropdownButton } from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import './ChangeProfile.css';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {
    updateAllUsers,
    updateEmail,
    updateCollaborators,
    updateDirector,
    updateAdministrator,
    updateVisitors
} from './../../actions/UserActions';

class ChangeProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
        this.AuthService = new AuthService();
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick(event) {
        let profile;

        if (event === 1) {
            profile = 'COLLABORATOR';
        }
        if (event === 2) {
            profile = 'DIRECTOR';
        }
        if (event === 3) {
            profile = 'ADMIN';
        }
        const email = this.props.email;

        const profileDTO = {
            email: email,
            userProfile: profile
        };
        this.AuthService.fetch(`/users/profiles`, {
            method: 'PATCH',
            body: JSON.stringify(profileDTO)
        })
            .then(res => {
                if (res.userProfile === profile) {
                    toastr.success('Profile was changed');
                    //window.location.href = `/usersMngr`;
                    this.updateUsers();
                }
            })
            .catch(err => {
                toastr.error('Profile was not changed');
            });
    }

    renderDropdownButton(title, i) {
        return (
            <DropdownButton
                className="buttonFinished"
                bsStyle={title.toLowerCase()}
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
            >
                <MenuItem eventKey={1} onSelect={this.handleClick}>
                    {'collaborator'}
                </MenuItem>
                <MenuItem eventKey={2} onSelect={this.handleClick}>
                    {'director'}
                </MenuItem>
                <MenuItem eventKey={3} onSelect={this.handleClick}>
                    {'administrator'}
                </MenuItem>
            </DropdownButton>
        );
    }

    updateUsers(){

        if (this.props.filter === 'all')
        this.props.updateAllUsers()
    else if (this.props.filter === 'email')
        this.props.updateEmail(this.props.email)
    else if (this.props.filter === 'collaborators')
        this.props.updateCollaborators()
    else if (this.props.filter === 'directors')
        this.props.updateDirector()
    else if (this.props.filter === 'administrators')
        this.props.updateAdministrator()
    else if (this.props.filter === 'visitors')
        this.props.updateVisitors() 


    }

    render() {
        return (
            <div className=" table-striped">
                <tbody>{this.renderDropdownButton('Change', 0)}</tbody>
            </div>
        );
    }
}

const mapStateToProps = state => { return ({ filter: state.usersFilter.filterType }) }
const mapDispatchToProps = dispatch => bindActionCreators({ 
    updateAllUsers,
    updateEmail,
    updateCollaborators,
    updateDirector,
    updateAdministrator,
    updateVisitors }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(ChangeProfile);

