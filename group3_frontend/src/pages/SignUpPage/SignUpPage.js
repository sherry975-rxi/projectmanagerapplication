import React, { Component } from "react";
import "./signUpPage.css";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class signUpPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: "",
            email: "",
            userFunction: "",
            phone: "",
            password: "",
            securityQuestion: "",
            securityAnswer: "",
            street: "",
            zipCode: "",
            city: "",
            district: "",
            country: "",
            idNumber: ""
        };
    }

    validateForm() {
        return (
            this.state.name.length > 0 &&
            this.state.email.length > 0 &&
            this.state.userFunction.length > 0 &&
            this.state.phone.length > 0 &&
            this.state.password.length > 0 &&
            this.state.securityQuestion.length > 0 &&
            this.state.securityAnswer.length > 0 &&
            this.state.street.length > 0 &&
            this.state.zipCode.length > 0 &&
            this.state.city.length > 0 &&
            this.state.district.length > 0 &&
            this.state.country.length > 0 &&
            this.state.idNumber.length > 0
        );
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
        event.preventDefault();
        const {
            name,
            email,
            userFunction,
            phone,
            password,
            securityQuestion,
            securityAnswer,
            street,
            zipCode,
            city,
            district,
            country,
            idNumber
        } = this.state;

        const signUpData = {
            name,
            email,
            function: userFunction,
            phone,
            password,
            question: securityQuestion,
            answer: securityAnswer,
            street,
            zipCode,
            city,
            district,
            country,
            idNumber
        };

        console.log(signUpData);

        fetch("/account/register", {
            body: JSON.stringify(signUpData),
            headers: {
                "content-type": "application/json"
            },
            method: "POST"
        })
            .then(function(response) {
                return response.json();
            })
            .then(function(jsonResponse) {
                //
                console.log(jsonResponse);
            });
    };

    render() {
        return (
            <div className="SignUp">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="name">
                        <ControlLabel>Name</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="email">
                        <ControlLabel>Email</ControlLabel>
                        <FormControl
                            autoFocus
                            type="email"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="userFunction">
                        <ControlLabel>Function</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.userFunction}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="phone">
                        <ControlLabel>Phone</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.phone}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="street">
                        <ControlLabel>Street</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.street}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="zipCode">
                        <ControlLabel>Zip Code</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.zipCode}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="city">
                        <ControlLabel>City</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.city}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="district">
                        <ControlLabel>District</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.district}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="country">
                        <ControlLabel>Country</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.country}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="password">
                        <ControlLabel>Password</ControlLabel>
                        <FormControl
                            value={this.state.password}
                            onChange={this.handleChange}
                            type="password"
                        />
                    </FormGroup>

                    <FormGroup controlId="securityQuestion">
                        <ControlLabel>Security Question</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.securityQuestion}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="securityAnswer">
                        <ControlLabel>Security Answer</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.securityAnswer}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <FormGroup controlId="idNumber">
                        <ControlLabel>ID Number</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.idNumber}
                            onChange={this.handleChange}
                        />
                    </FormGroup>

                    <Button block disabled={!this.validateForm()} type="submit">
                        Sign Up
                    </Button>
                </form>
            </div>
        );
    }
}

export default signUpPage;
