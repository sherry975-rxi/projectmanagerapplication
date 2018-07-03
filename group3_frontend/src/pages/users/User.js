import React, { Component } from 'react';
import Error from './../../components/error/error';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenuUsers from '../../components/AccordianMenuUser/AccordionMenuUsers.jsx';
import UserFilter from '../users/UserFilter';
import UploadUsersFile from '../../components/UploadUserFile/UploadUsersFile'
import {
    finishUpdate
} from "../../actions/UserActions";
import {bindActionCreators} from "redux";

class User extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            externalData: null,
            check: false
        };
        this.AuthService = new AuthService();
        this.renderUsers = this.renderUsers.bind(this);

    }

    updateUsers(){
        if(this.props.updated===true){
            this.renderUsers();
            this.forceUpdate();
            this.props.finishUpdate();
        }
    }

    //TODO: Add sort by ascending or descending order to these tables

    renderUsers() {
        switch (this.props.filter) {
            case 'all':
                return <AccordionMenuUsers list={this.props.allUsers} />;

            case 'email':
                return <AccordionMenuUsers list={this.props.emailUsers} />;

            case 'collaborators':
                return <AccordionMenuUsers list={this.props.allCollaborators}/>;

            case 'directors':
                return <AccordionMenuUsers list={this.props.allDirector} />;

            case 'administrators':
                return <AccordionMenuUsers list={this.props.allAdministrator} />;

            case 'searchUsers':
                return <AccordionMenuUsers list={this.props.searchList} />;
                
            case 'visitors':
                return <AccordionMenuUsers list={this.props.allVisitors} />;

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
                    <UploadUsersFile onChange={this.updateUsers()}/>
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
        updated: state.usersFilter.update,
        allAdministrator: state.users.allAdministrator,
        allDirector: state.users.allDirector,
        allCollaborators: state.users.allCollaborators,
        emailUsers: state.users.emailUsers,
        allUsers: state.users.allUsers,
        searchList: state.users.searchList,
        allVisitors: state.users.allVisitors
    };
};

const mapDispatchToProps = dispatch => bindActionCreators({ finishUpdate }, dispatch)

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(User);
