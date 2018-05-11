import React, { Component } from 'react';
import './SideBar.css';
import { NavLink } from 'react-router-dom';

class SideBar extends Component {
    constructor(props) {
        super(props);
    }

    componentWillMount() {
        const visibility = this.props.isVisible ? "" : "hide";
        this.setState({
            visibility
        })
        console.log("componentWillMount-----", visibility);
    }

    shouldComponentUpdate(nextProps) {
        console.log("---------shouldComponentUpdate-----------", );
        console.log("actual.......", this.props.isVisible);
        console.log("next.......", nextProps.isVisible);
        console.log("----------------------------------.");
        return nextProps.isVisible !== this.props.isVisible;
    }

    componentDidUpdate() {
        console.log("-----------componentDidUpdate--------------")
        const visibility = this.props.isVisible ? "" : "hide";
        this.setState({
            visibility
        })
        console.log("prop----------", this.props.isVisible);
        console.log("visibility----", visibility);
        console.log("----------------------------------.");
    };

    state = {};
    render() {
        return (
            <div className={"col-sm-3 col-md-2 sidebar " + this.state.visibility}>
                {this.state.visibility}
                <ul className="nav nav-sidebar">
                    <li>
                        <NavLink to="/projects" activeClassName="active">Projects</NavLink>
                    </li>
                    <li>
                        <NavLink to="/tasks" activeClassName="active">Tasks</NavLink>
                    </li>
                    <li>
                        <NavLink to="/users" activeClassName="active">Users</NavLink>
                    </li>
                </ul>
            </div>
        );
    }
}

export default SideBar;