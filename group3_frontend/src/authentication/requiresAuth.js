import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import AuthService from './../pages/loginPage/AuthService';
import { submit } from './authenticationActions'
import { bindActionCreators } from 'redux'
const Auth = new AuthService();

export default function requiresAuth(Component) {

    class AuthenticatedComponent extends React.Component {

        displayLogoutButton() {
            this.props.submit()
        }

        render() {

            if (!Auth.loggedIn()) {
                return (<Redirect to='/login' />)
            }

            else {
                return (

                    <div className="authenticated">
                        <Component {...this.props} />
                        {this.displayLogoutButton()}
                    </div>
                );
            }
        }
    }

    const mapDispatchToProps = dispatch => bindActionCreators({ submit }, dispatch)
    return connect(null, mapDispatchToProps)(AuthenticatedComponent);

}


