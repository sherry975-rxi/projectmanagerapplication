import React, { Component } from "react";
import "./SideBar.css";
import { NavLink } from "react-router-dom";

class SideBar extends Component {
    constructor(props) {
        super(props);
    }

    componentWillMount() {
        const visibility = this.props.isVisible ? "" : "hide";
        this.setState({
            visibility
        });
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
                    <li>
                        <NavLink to="/projects" activeClassName="active">
                            Projects
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/activeprojects" activeClassName="active">
                            Active Projects
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/tasks" activeClassName="active">
                            Ongoing Tasks
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/finishedtasks" activeClassName="active">
                            Finished Tasks
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/addtask" activeClassName="active">
                            Add task
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/marktaskfinished" activeClassName="active">
                            Mark task as finished
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/users" activeClassName="active">
                            Users
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="/projectcost" activeClassName="active">
                            Project Cost
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            to="/selectprojectcostcalculation"
                            activeClassName="active"
                        >
                            Select Calculation Method<br />for Project Cost
                        </NavLink>
                        </li>
                        <li>   
                        <NavLink to="/createreport" activeClassName="active">
                            Create Report
                        </NavLink>
                    
                    </li>
                    <li>   
                        <NavLink to="/updatereport" activeClassName="active">
                            Update Report
                        </NavLink>
                    
                    </li>
                </ul>
            </div>
        );
    }
}

export default SideBar;
