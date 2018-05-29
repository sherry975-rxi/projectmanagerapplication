import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import AuthService from './../pages/loginPage/AuthService';
const Auth = new AuthService();

export default function requiresAuth(Component) {

    class AuthenticatedComponent extends React.Component {

        render() {

            if (!Auth.loggedIn()) {
                return (<Redirect to='/login' />)
            }

            else {
                return (

                    <div className="authenticated">
                        <Component {...this.props} />
                    </div>
                );
            }
        }
    }
    return AuthenticatedComponent;

}


