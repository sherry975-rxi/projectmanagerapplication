import React from "react";
import "./Profile.css";
import AuthService from '../../pages/loginPage/AuthService';
import mainLogo from './profile_logo.png';
import axios from 'axios';



export class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.match
        this.state = {
            user: {},
            message: ""
        };
        this.AuthService = new AuthService();

    }

    async componentDidMount() {
        this.AuthService.fetch(`/users/${this.props.match.params.userID}`, {
            method: "get" })
            .then(responseData => {
                this.setState({
                    user: responseData,
                    message: responseData.status
                });
            });
            

    
        }

        



    render() {
        var user = this.state.user;
        return (
            
            <div className="external-div">
                <h1>Profile</h1>
                <div className="divUserDetails">
                <img  src={mainLogo} alt="fireSpot"/>

                    <h2 className="userDetails">ID:   {user.userID} </h2>
                    <h2 className="userDetails">Name:  {user.name} </h2>
                    <h2 className="userDetails">Phone:  {user.phone} </h2>
                    <h2 className="userDetails">Function:  {user.function} </h2>
                    <h2 className="userDetails">Profile:  {user.userProfile} </h2>
                </div>
           
            </div>
        );
    }
}

export default Profile;
