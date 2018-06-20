import React from 'react';
import './Profile.css';
import AuthService from '../../pages/loginPage/AuthService';
import { authorize } from '../../actions/authenticationActions';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import womanProfile from './profile_logo.png'
import manProfile from './profile_logo_man.png'

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
            if (this.state.message == null) {
                this.props.authorize(responseData);
            }
        });
    }

    getImage() {
        if (this.AuthService.getUserId() === 7 ||
            this.AuthService.getUserId() === 6 ||
            this.AuthService.getUserId() === 4 ||
            this.AuthService.getUserId() === 5 ||
            this.AuthService.getUserId() === 1) {
            return (womanProfile)
        }
        else {
            return (manProfile)
        }

    }

    render() {
        var user = this.props.user;
        let image = this.getImage();

        return (
            <div className="external-div">
                <center>
                    <img
                        className="profilePic"
                        src={image}
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
