import React, { Component } from "react";
import { NavLink } from "react-router-dom";

class SignUpStepFour extends Component {
    state = {};
    render() {
        return (
            <div className="stepFourConfirmation">
                <div className="sucessRegistrationText">
                    Registration completed successfully!
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

export default SignUpStepFour;
