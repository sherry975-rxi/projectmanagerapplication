import React, { Component } from 'react';
import {
    Button,
    FormGroup,
    FormControl,
    ControlLabel,
    Checkbox,
    Alert
} from 'react-bootstrap';
import { CountryDropdown, RegionDropdown } from 'react-country-region-selector';

class SignUpForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            userFunction: '',
            phone: '',
            password: '',
            securityQuestion: '',
            securityAnswer: '',
            street: '',
            zipCode: '',
            city: '',
            district: '',
            country: '',
            idNumber: '',
            aceptTerms: false,
            hideExistingEmail: 'hide-email'
        };
    }

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

        fetch('/account/register', {
            body: JSON.stringify(signUpData),
            headers: {
                'content-type': 'application/json'
            },
            method: 'POST'
        })
            .then(response => {
                return response.json();
            })
            .then(jsonResponse => {
                this.props.incrementStep(jsonResponse);
            })
            .catch(error => {
                this.setState({
                    hideExistingEmail: ''
                });
            });
    };

    stepOneForm = () => (
        <div className="SignUp">
            <form onSubmit={this.handleSubmit}>
            <table className="SignUpTable">
                <tr>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="name" className="formField">
                        <ControlLabel>Name</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                        </FormGroup>
                    </td>

                    <td className="tdSignUpForm">
                        <FormGroup controlId="email" className="formField">
                        <ControlLabel>Email</ControlLabel>
                        <FormControl
                            type="email"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                        </FormGroup>
                    </td>
                </tr>

                <tr>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="userFunction" className="formField">
                        <ControlLabel>Function</ControlLabel>
                        <FormControl
                            type="text"
                            value={this.state.userFunction}
                            onChange={this.handleChange}
                        />
                        </FormGroup>
                    </td>

                    <td className="tdSignUpForm">
                        <FormGroup controlId="phone" className="formField">
                        <ControlLabel>Phone</ControlLabel>
                        <FormControl
                            type="text"
                            value={this.state.phone}
                            onChange={this.handleChange}
                        />
                        </FormGroup>
                    </td>
                </tr>

                <tr>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="Country" className="formField">
                        <ControlLabel>Country</ControlLabel>

                        <CountryDropdown
                            classes="formFieldDropDown form-control"
                            value={this.state.country}
                            onChange={val => this.selectCountry(val)}
                            componentClass="select"
                            placeholder="select"
                        />
                         </FormGroup>
                    </td>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="District" className="formField">
                            <ControlLabel>District</ControlLabel>

                            <RegionDropdown
                                classes="formFieldDropDown form-control"
                                country={this.state.country}
                                value={this.state.district}
                                onChange={val => this.selectRegion(val)}
                            />
                         </FormGroup>
                    </td>
                </tr>

                <tr>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="city" className="formField">
                            <ControlLabel>City</ControlLabel>
                            <FormControl
                                type="text"
                                value={this.state.city}
                                onChange={this.handleChange}
                            />
                         </FormGroup>
                    </td>
                    <td className="tdSignUpForm">
                         <FormGroup controlId="street" className="formField">
                            <ControlLabel>Street</ControlLabel>
                            <FormControl
                                type="text"
                                value={this.state.street}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                    </td>
                </tr>

                <tr>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="zipCode" className="formField">
                            <ControlLabel>Zip Code</ControlLabel>
                            <FormControl
                                type="text"
                                value={this.state.zipCode}
                                onChange={this.handleChange}
                            />
                         </FormGroup>

                    </td>
                    <td className="tdSignUpForm">
                        <FormGroup controlId="password" className="formField">
                            <ControlLabel>Password</ControlLabel>
                            <FormControl
                                value={this.state.password}
                                onChange={this.handleChange}
                                type="password"
                            />
                      </FormGroup>
                    </td>
                </tr>

                <tr>
                    <td className="tdSignUpForm">
                    <FormGroup controlId="securityQuestion" className="formField">
                        <ControlLabel>Security Question</ControlLabel>
                        <FormControl
                            value={this.state.securityQuestion}
                            onChange={this.handleChange}
                            componentClass="select"
                            placeholder="select"
                        >
                            <option value="" disabled defaultValue="">
                                Select your option
                            </option>
                            <option value="pet">
                                What is the name of your first pet?
                            </option>
                            <option value="school">
                                What elementary school did you attend?
                            </option>
                            <option value="honeymoon">
                                Where did you go for your honeymoon?
                            </option>
                        </FormControl>
                     </FormGroup>
                    </td>
                    <td className="tdSignUpForm">
                        
                    <FormGroup controlId="securityAnswer" className="formField">
                        <ControlLabel>Security Answer</ControlLabel>
                        <FormControl
                            type="text"
                            value={this.state.securityAnswer}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    </td>
                </tr>
                <tr>
                    <td className="tdSignUpForm">
                        
                        <FormGroup controlId="idNumber" className="formField">
                            <ControlLabel>Company collaborator ID</ControlLabel>
                            <FormControl
                                type="text"
                                value={this.state.idNumber}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                    </td>
                    <td className="tdSignUpForm">
                    </td>
                </tr>
                
            </table>
              
            
                <div className="termsAndConditions">
                    By using this application, you agree to be bound by, and to
                    comply with these <b>Terms and Conditions</b>. To proceed
                    with registration you must accept access conditions.
                </div>

                <Checkbox
                    className="termsCheckbox"
                    onChange={this.handleChangeCheckboxTerms}
                >
                    I agree to the Terms and Conditions.
                </Checkbox>

                <Alert
                    bsStyle="danger"
                    className={this.state.hideExistingEmail}
                >
                    <strong>This email address already exists!</strong> Try
                    again.
                </Alert>

                <Button
                    block
                    disabled={!this.validateForm() || this.codeCheckbox}
                    type="submit"
                >
                    Sign Up
                </Button>
            </form>
        </div>
    );

    selectCountry(val) {
        this.setState({ country: val });
    }

    selectRegion(val) {
        this.setState({ district: val });
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
            this.state.idNumber.length > 0 &&
            this.state.aceptTerms
        );
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleChangeCheckboxTerms = event => {
        this.setState({
            aceptTerms: event.target.checked
        });
    };

    render() {
        return this.stepOneForm();
    }
}

export default SignUpForm;
