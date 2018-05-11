import React, { Component } from 'react';
import './sidebar.css';

class Sidebar extends Component {

    toggleSidebar() {
        document.getElementById("sidebar").classList.toggle('active');
      }

    render() {

        return (
                <div id="sidebar">
                    <ul>
                        <li className="profile">Profile</li>
                        <li className="projects">Projects</li>
                        <li className="tasks">Tasks</li>
                    </ul>
                </div>
        );
    }
}


export default Sidebar;
