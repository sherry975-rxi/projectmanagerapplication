import React from 'react';
import { Route } from 'react-router-dom';
import { connect } from 'react-redux';

import Homepage from "../homePage/Homepage";
import requiresAuth from "./requiresAuth";




export class ProtectedRoute extends React.Component {
    constructor(props) {
        super(props);

    }

    render() {
        if(this.props.permissions.includes(this.props.profile)) {
            return (
                <Route path={this.props.path} component={this.props.component} />
            );
        } else {
            return (
                <Route path={this.props.path} component={requiresAuth(Homepage)} />
            );
        }
    }
}
const mapStateToProps = state => {
    return { profile: state.authenthication.user.userProfile };
};
export default connect(
    mapStateToProps,
    null
)(ProtectedRoute);