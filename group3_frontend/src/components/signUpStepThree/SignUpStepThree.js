import React, { Component } from "react";
import {
    Button,
    FormGroup,
    FormControl,
    Alert,
    ControlLabel
} from "react-bootstrap";

class SignUpStepThree extends Component {
    constructor(props) {
        super(props);
        this.state = {
            verificationCode: "",
            hideWrongCode: "hide-code"
        };
    }

    handleCodeSubmit = event => {
        event.preventDefault();
        const verificationCode = this.state.verificationCode;
        fetch(this.props.validationCodeUrl, {
            body: JSON.stringify({ codeToCheck: verificationCode }),
            headers: {
                "content-type": "application/json"
            },
            method: "POST"
        })
            .then(response => response.json())
            .then(jsonResponse => {
                this.props.setStepThree({
                    signupStep: 4
                });
            })
            .catch(error => {
                this.setState({
                    hideWrongCode: ""
                });
                console.error("Verification code is incorrect. Try again");
            });
    };

    validateCodeForm() {
        return this.state.verificationCode.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    stepThreeForm = () => (
        <div className="validationCodeForm">
            <form onSubmit={this.handleCodeSubmit}>
                <FormGroup controlId="verificationCode">
                    <ControlLabel>Validation code</ControlLabel>
                    <FormControl
                        autoFocus
                        type="text"
                        value={this.state.verificationCode}
                        onChange={this.handleChange}
                    />
                </FormGroup>
                <Alert bsStyle="danger" className={this.state.hideWrongCode}>
                    <strong>Invalid validation code!</strong> Try again.
                </Alert>
                <Button block disabled={!this.validateCodeForm} type="submit">
                    Send validation code
                </Button>
            </form>
        </div>
    );

    render() {
        return this.stepThreeForm();
    }
}

export default SignUpStepThree;
