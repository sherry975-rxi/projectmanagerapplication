import React, { Component } from "react";
import "./firstPage.css";
import { NavLink } from "react-router-dom";

class firstPage extends Component {
    state = {};
    render() {
        return (
            <div className="Bar">
                <div className="Logo">
                    <h1 className="switch">appManager</h1>
                    <p className="subTitle">Project Management Application</p>
                </div>
                <div className="SignUp">
                    <NavLink to="/signup">
                        <button type="button" class="btn btn-primary btn-lg">
                            Sign Up
                        </button>
                    </NavLink>
                </div>
                <div className="LogIn">
                    <NavLink className="btn btn-primary btn-lg" to="/login">
                        Log In
                    </NavLink>
                </div>
            </div>
        );
    }
}

export default firstPage;
