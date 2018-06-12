import React, { Component } from 'react';
import './SideBar.css';
import { NavLink } from 'react-router-dom';
import SideButton from './SideButton.js';
import Profile from '../navBar/Profile';

class SideBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            profile: ''
        };

    this.setProfile = this.setProfile.bind(this);

    }

    setProfile(profile) {
        if (this.state.profile != profile) {
            this.setState({
                profile: profile
            });
        }
    };

    getCollaboratorOptions() {
        return (<div className="menu">
                    <SideButton to="/myprojects" text="My Projects" />
                    <SideButton to="/tasks" text="My Tasks" />
                </div>);
    }

    getDirectorOptions() {
        return (<div className="menu">
                    <SideButton to="/activeprojects" text="Projects" />
                </div>);
    }

    getAdminOptions() {
        return (<div className="menu">
                    <SideButton to="/users" text="Users" />
                </div>);
    }

    render() {
        let options = '';

        if (this.state.profile == 'COLLABORATOR') {
            options = this.getCollaboratorOptions();
        } else if (this.state.profile == 'DIRECTOR') {
            options = this.getDirectorOptions();
        } else if (this.state.profile == 'ADMIN') {
            options = this.getAdminOptions();
        }

        return (
            <div className={'col-sm-3 col-md-2 sidebar '}>
                <div className="profile">
                    <Profile setProfile={this.setProfile} />
                </div>
                {options}
            </div>
        );
    }
}

export default SideBar;
