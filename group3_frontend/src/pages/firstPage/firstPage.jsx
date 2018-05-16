import React, { Component } from "react";
import "./firstPage.css";
import { NavLink } from "react-router-dom";
import background from "./background.jpg"
import logo from "./appManager-logo.svg"

class firstPage extends Component {
    state = {};
    render() {
        return (
            <div className="Bar">
                <div className="Logo">
                    <img src={logo} alt="logo-main" className="logo-main"/>
                    
                </div>
                <div className="SignUp">
                    <NavLink to="/signup">
                        <button type="button" class="btn btn-primary btn-lg">
                            Create account
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
