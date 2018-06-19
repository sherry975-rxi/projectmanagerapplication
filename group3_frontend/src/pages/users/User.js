import React, { Component } from 'react';
import Error from './../../components/error/error';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenuUsers from '../../components/AccordianMenuUser/AccordionMenuUsers.jsx';
import UserFilter from '../users/UserFilter';

class User extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            externalData: null
        };
        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    renderUsers() {
        switch (this.props.filter) {
            case 'all':
                return <AccordionMenuUsers list={this.props.allUsers} />;
            case 'email':
                return <AccordionMenuUsers list={this.props.emailUsers} />;
            case 'collaborators':
                return (
                    <AccordionMenuUsers list={this.props.allCollaborators} />
                );
            case 'directors':
                return <AccordionMenuUsers list={this.props.allDirector} />;
            case 'administrators':
                return (
                    <AccordionMenuUsers list={this.props.allAdministrator} />
                );
            default:
                return;
        }
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
            return (
                <div>
                    <UserFilter />
                    {this.renderUsers()}
                </div>
            );
        }
    }
}

const mapStateToProps = state => {
    return {
        filter: state.usersFilter.filterType,
        allAdministrator: state.users.allAdministrator,
        allDirector: state.users.allDirector,
        allCollaborators: state.users.allCollaborators,
        emailUsers: state.users.emailUsers,
        allUsers: state.users.allUsers
    };
};

export default connect(
    mapStateToProps,
    null
)(User);
