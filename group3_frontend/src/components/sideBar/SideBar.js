import React, { Component } from "react";
import "./SideBar.css";
import { NavLink } from "react-router-dom";
import List  from "../list/List.js";

class SideBar extends Component {
    constructor(props) {
        super(props);

        const visibility = this.props.isVisible ? "" : "hide";
        this.state = {
            visibility
        };
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        const visibility = nextProps.isVisible ? "" : "hide";
        const prevVisibility = prevState ? prevState.visibility : undefined;
        if (visibility === prevVisibility) {
            return null;
        }
        return {
            visibility
        };
    }

    render() {
        return (
            <div
                className={"col-sm-3 col-md-2 sidebar " + this.state.visibility}
            >
                <ul className="nav nav-sidebar">
                    <List type='Project'>
                        <li>
                            <NavLink to="/activeprojects" activeClassName="active">
                                Active Projects
                            </NavLink>
                        </li>
                    </List>
                    <List type='Task'>
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
                    <List type='User'>
                    </List>                 
                </ul>
            </div>
        );
    }
}

export default SideBar;
