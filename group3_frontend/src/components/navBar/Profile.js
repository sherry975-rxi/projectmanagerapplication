import React from 'react';
import './Profile.css';
import AuthService from '../../pages/loginPage/AuthService';
import mainLogo from './profile_logo.png';
export class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {},
            message: ''
        };
        this.AuthService = new AuthService();
    }
    async componentDidMount() {
        this.AuthService.fetch(`/users/${this.AuthService.getUserId()}`, {
            method: 'get'
        }).then(responseData => {
            this.setState({
                user: responseData,
                message: responseData.status
            });
        });
    }
    render() {
        var user = this.state.user;
        this.props.setProfile(this.state.user.userProfile);
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
export default Profile;
