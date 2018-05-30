import React, { Component } from "react";
import "./SideBar.css";
import { NavLink } from "react-router-dom";
import List from "../list/List.js";
import Profile from '../navBar/Profile'

class SideBar extends Component {
    constructor(props) {
        super(props);
     
    }

   

    render() {



        return (
            <div
                className={"col-sm-3 col-md-2 sidebar " }>
                <div className="profile">
                    <Profile />
                </div>
                <div>
                    <ul className="menu">
                        <List className="Projects" type='Projects' onClick={this.toggleVisibility}>

                            <li>
                                <NavLink to="/activeprojects" activeClassName="active">
                                    Active Projects
                            </NavLink>
                            </li>
                        </List>
                        <List className="Project" type='Task' onClick={this.toggleVisibility}>
                            <li>
                                <NavLink to="/addtask" activeClassName="active">
                                    Add task
                            </NavLink>
                            </li>
                            <li>
                                <NavLink to="/tasks/7" activeClassName="active">
                                    Ongoing tasks
                            </NavLink>
                            </li>
                            <li>
                                <NavLink to="/finishedtasks" activeClassName="active">
                                    Finished tasks
                            </NavLink>
                            </li>
                        </List>


                    </ul>
                </div>



                {/*<ul>
                    <List className="Project" type='Project' onClick={this.toggleVisibility}>
                        <li>
                            <NavLink to="/activeprojects" activeClassName="active">
                                Active Projects
                            </NavLink>
                        </li>
                    </List>
                    <List className="Task" type='Task'>
                        <li>
                            <NavLink to="/addtask" activeClassName="active">
                                Add task
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/tasks/7" activeClassName="active">
                                Ongoing tasks
                            </NavLink>
                        </li>
                        <li>
                            <NavLink to="/finishedtasks" activeClassName="active">
                                Finished tasks
                            </NavLink>
                        </li>
                    </List>
                    <List className="User" type='User'>
                    </List>
                </ul> */}
            </div>
        );
    }
}

export default SideBar;
