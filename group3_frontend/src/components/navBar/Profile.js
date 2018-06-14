import React from 'react';
import './Profile.css';
import AuthService from '../../pages/loginPage/AuthService';
import mainLogo from './profile_logo.png';
import { authorize } from '../../actions/authenticationActions';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

export class Profile extends React.Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.AuthService.fetch(`/users/${this.AuthService.getUserId()}`, {
            method: 'get'
        }).then(responseData => {
            this.setState({
                message: responseData.error
            });
            if(this.state.message == null ) {
                this.props.authorize(responseData);
            }
        });
    }

    render() {
        var user = this.props.user;

        return (
            <div className="external-div">
                <center>
                    <img
                        className="profilePic"
                        src={mainLogo}
                        border="2"
                        alt="fireSpot"
                    />
                </center>
                <center>
                    <ul className="profileDetails">
                        <li className="name">{user.name}</li>
                        <li className="role">{user.userProfile}</li>
                    </ul>
                </center>
            </div>
        );
    }
}
const mapStateToProps = state => {
    return { user: state.authenthication.user };
};
const mapDispatchToProps = dispatch => bindActionCreators({ authorize }, dispatch)
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Profile);
