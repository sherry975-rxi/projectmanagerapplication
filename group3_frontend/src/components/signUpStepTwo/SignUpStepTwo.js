import React, { Component } from 'react';
import { Button, ButtonToolbar } from 'react-bootstrap';

const SMSVALIDATION = 'smsValidation';
const EMAILVALIDATION = 'emailValidation';

class SignUpStepTwo extends Component {
    render() {
        return this.stepTwoValidation();
    }

    stepTwoValidation = (title, i) => (
        <div className="stepTwoValidation">
            <div className="chooseValidationMethodText">
                To continue with sign up process, you will receive a validation
                code by SMS or email. Please, choose which one:
            </div>
            <ButtonToolbar className="buttonValidationToolbar">
                <Button
                    id="smsValidation"
                    onClick={this.handleSignUpValidation}
                >
                    SMS
                </Button>
                <Button
                    id="emailValidation"
                    onClick={this.handleSignUpValidation}
                >
                    Email
                </Button>
            </ButtonToolbar>
        </div>
    );

    handleSignUpValidation = event => {
        const validationType = event.target.id;

        if (
            validationType === SMSVALIDATION ||
            validationType === EMAILVALIDATION
        ) {
            return fetch(this.props.validationUrl[validationType])
                .then(response => {
                    return response.json();
                })
                .then(jsonResponse => {
                    this.props.setStepTwo({
                        signUpStep: 3,
                        verificationUrl: jsonResponse.href
                    });
                });
        }
        return;
    };
}

export default SignUpStepTwo;
