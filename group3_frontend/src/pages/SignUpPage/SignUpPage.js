import React, { Component } from "react";
import "./SignUpPage.css";
import {
    Button,
    ButtonToolbar,
    FormGroup,
    FormControl,
    ControlLabel,
    Checkbox,
    Alert
} from "react-bootstrap";
import { CountryDropdown, RegionDropdown } from "react-country-region-selector";
import SignUpForm from "../../components/signUpForm/SignUpForm";
import SignUpStepTwo from "../../components/signUpStepTwo/SignUpStepTwo";
import SignUpStepThree from "../../components/signUpStepThree/SignUpStepThree";
import SignUpStepFour from "../../components/signUpStepFour/SignUpStepFour";

const SMSVALIDATION = "smsValidation";
const EMAILVALIDATION = "emailValidation";

class SignUpPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            signupStep: 1
        };
    }

    setStepOneResponse = jsonResponse => {
        jsonResponse.forEach(element => {
            if (
                element.rel === SMSVALIDATION ||
                element.rel === EMAILVALIDATION
            ) {
                console.log({
                    [element.rel]: element.href
                });
                this.setState({
                    [element.rel]: element.href
                });
            }
        });
        this.setState({ signupStep: this.state.signupStep + 1 });
    };

    setStepResponse = response => {
        this.setState(response);
    };

    render() {
        let formStp = <SignUpForm incrementStep={this.setStepOneResponse} />;

        if (this.state.signupStep === 2) {
            const urls = {
                [SMSVALIDATION]: this.state[SMSVALIDATION],
                [EMAILVALIDATION]: this.state[EMAILVALIDATION]
            };
            formStp = (
                <SignUpStepTwo
                    validationUrl={urls}
                    setStepTwo={this.setStepResponse}
                />
            );
        } else if (this.state.signupStep === 3) {
            formStp = (
                <SignUpStepThree
                    setStepThree={this.setStepResponse}
                    validationCodeUrl={this.state.verificationUrl}
                />
            );
        } else if (this.state.signupStep === 4) {
            formStp = <SignUpStepFour />;
        }
        return <div>{formStp}</div>;
    }
}

export default SignUpPage;
