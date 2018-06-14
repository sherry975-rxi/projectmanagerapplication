import React, { Component } from 'react';
import './SideBar.css';
import { NavLink } from 'react-router-dom';
import SideButton from './SideButton.js';
import Profile from '../navBar/Profile';
import { connect } from 'react-redux';

class SideBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: ''
        };

    }


    getCollaboratorOptions() {
        return (<div className="menu">
                    <SideButton to="/myprojects" text="My Projects" />
                    <SideButton to="/tasks" text="My Tasks" />
                </div>);
    }

    getDirectorOptions() {
        return (<div className="menu">
                    <SideButton to="/activeprojects" text="Projects" />
                    <SideButton to="/createproject" text="Create Project" />
                </div>);
    }

    getAdminOptions() {
        return (<div className="menu">
                    <SideButton to="/usersMngr" text="Users" />
                </div>);
    }

    render() {
        let options = '';

        if (this.props.profile === 'COLLABORATOR') {
            options = this.getCollaboratorOptions();
        } else if (this.props.profile === 'DIRECTOR') {
            options = this.getDirectorOptions();
        } else if (this.props.profile === 'ADMIN') {
            options = this.getAdminOptions();
        }

        return (
            <div className={'col-sm-3 col-md-2 sidebar '}>
                <div className="profile">
                    <Profile />
                </div>
                {options}
            </div>
        );
    }
}
const mapStateToProps = state => {
    return { profile: state.authenthication.user.userProfile };
};
export default connect(
    mapStateToProps,
)(SideBar);
