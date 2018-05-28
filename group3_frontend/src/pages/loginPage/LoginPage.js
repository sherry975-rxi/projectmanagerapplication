import React, { Component } from "react";
import "./LoginPage.css";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import axios from 'axios';
import decode from 'jwt-decode';
import AuthService from './AuthService';
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { submit, login } from '../../authentication/authenticationActions'

class LoginPage extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            email: "",
            password: ""
        };
        this.Auth = new AuthService();
    }

    validateForm() {
        return this.state.email.length > 0 && this.state.password.length > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });


    };

    //On submit this method calls the login method from the Auth Service
    handleSubmit = async event => {
        event.preventDefault();
        const values = this.state

        this.Auth.login(this.state.email, this.state.password)
            .then(res => {
                //If the loggin is sucessfull the user gets redirected to its home page
                if (res.status == 200) {
                    this.props.submit()
                    this.Auth.fetch(`/users/email/` + this.state.email, { method: 'get' })
                        .then((responseData) => {
                            this.props.history.replace('/profile/' + responseData[0]['userID'])
                        })
                }

            })
            .catch(err => {
                alert(err);
            })
    };

    render() {
        return (
            <div className="Login">
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="email" bsSize="large">
                        <ControlLabel>Email</ControlLabel>
                        <FormControl
                            autoFocus
                            type="email"
                            value={this.state.email}

                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="password" bsSize="large">
                        <ControlLabel>Password</ControlLabel>
                        <FormControl
                            value={this.state.password}
                            onChange={this.handleChange}
                            type="password"
                        />
                    </FormGroup>
                    <Button
                        block
                        bsSize="large"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Login
                    </Button>
                </form>
            </div>
        );
    }
}

const mapStateToProps = state => ({ authenticated: state.authenthication.authenticated })
const mapDispatchToProps = dispatch => bindActionCreators({ submit, login }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);
