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
        this.AuthService.fetch(`/users/7`, {
            method: "get"
        })
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

                <center> <img className="profilePic" src={mainLogo} border="2" alt="fireSpot" /></center>
                <center><ul className="profileDetails">
                    <li className="name">{user.name}</li>
                    <li className="role">{user.userProfile}</li>
                </ul></center>


            </div>
        );
    }
}

export default Profile;
