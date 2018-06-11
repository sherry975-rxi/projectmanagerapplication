import React, { Component } from 'react';
import './SignUpPage.css';
import SignUpForm from '../../components/signUpForm/SignUpForm';
import SignUpStepTwo from '../../components/signUpStepTwo/SignUpStepTwo';
import SignUpStepThree from '../../components/signUpStepThree/SignUpStepThree';
import SignUpStepFour from '../../components/signUpStepFour/SignUpStepFour';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { increaseSignUpStepAction } from '../../actions/signUpActions';

const SMSVALIDATION = 'smsValidation';
const EMAILVALIDATION = 'emailValidation';

class SignUpPage extends Component {
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
        const payload = { signUpStep: this.props.signUpStep + 1 };
        this.props.increaseSignUpStepAction(payload);
    };

    setStepResponse = response => {
        this.setState({ verificationUrl: response.verificationUrl });
        const payload = { signUpStep: response.signUpStep };
        this.props.increaseSignUpStepAction(payload);
    };

    render() {
        const signUpStep = this.props.signUpStep;
        console.log('signUpStep', signUpStep);
        let formStp = <SignUpForm incrementStep={this.setStepOneResponse} />;

        if (signUpStep === 2) {
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
        } else if (signUpStep === 3) {
            formStp = (
                <SignUpStepThree
                    setStepThree={this.setStepResponse}
                    validationCodeUrl={this.state.verificationUrl}
                />
            );
        } else if (signUpStep === 4) {
            formStp = <SignUpStepFour />;
        }
        return (
            <div>
                {signUpStep}
                {formStp}
            </div>
        );
    }
}

export const mapStateToProps = state => {
    const { signUpStep } = state.signUp;
    return {
        signUpStep
    };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ increaseSignUpStepAction }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SignUpPage);
