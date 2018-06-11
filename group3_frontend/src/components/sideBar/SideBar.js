import React, { Component } from 'react';
import './SideBar.css';
import { NavLink } from 'react-router-dom';
import List from '../list/List.js';
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
        if (this.state.profile !== profile) {
            this.setState({
                profile: profile
            });
        }
    };

    getCollaboratorOptions() {
        return (<ul className="menu">
                    <List>
                        <NavLink to="/myprojects" activeClassName="active">
                            My Projects
                        </NavLink>
                    </List>
                    <List>
                        <NavLink to="/tasks" activeClassName="active" >
                            My Tasks
                        </NavLink>
                    </List>
                </ul>);
    }

    getDirectorOptions() {
        return (<ul className="menu">
                    <List>
                        <NavLink to="/activeprojects" activeClassName="active">
                             Projects
                        </NavLink>
                    </List>
                </ul>);
    }

    getAdminOptions() {
        return (<ul className="menu">
                    <List>
                        <NavLink to="/users" activeClassName="active">
                            Users
                        </NavLink>
                    </List>
                </ul>);
    }

    render() {
        var options = '';

        if (this.state.profile === 'COLLABORATOR') {
            options = this.getCollaboratorOptions();
        } else if (this.state.profile === 'DIRECTOR') {
            options = this.getDirectorOptions();
        } else if (this.state.profile === 'ADMIN') {
            options = this.getAdminOptions();
        }

        return (
            <div className={'col-sm-3 col-md-2 sidebar '}>
                <div className="profile">
                    <Profile setProfile={this.setProfile} />
                </div>
                <div>
                    {options}
                </div>
            </div>
        );
    }
}

export default SideBar;
