import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import logoImage from './images/logo.png';



class UserData extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            userID: "",
            message: "",
            email: "",
            username: "",
            function: "",
            userprofile: "",
            phone: ""
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.setState({
            email: this.AuthService.getProfile().sub
        
        }, () => {
            this.fetchUserData()
        })
    }

    getUserID(){
        return(this.state.userID);
    }

    
    fetchUserData(){
        
        this.AuthService.fetch(`/users/email/` + this.state.email, { method: 'get' })
        .then((responseData) => { this.setState({
            userID:   responseData[0]['userID'],
            username: responseData[0]['name'],
            function: responseData[0]['function'],
            userprofile: responseData[0]['userProfile'],
            phone: responseData[0]['phone']

            
        })
          
        })

    }



    render(){
        console.log(this.state.email);
        console.log(this.state.username);
        console.log(this.state.userID);

        return(
            <div className="UserDataHomepage">
                <img  src={logoImage} alt="logoImage"/>
                <h1 className="HomepageTitle">Welcome</h1>
                <div className="UserDataText">
                    <p>Name: {this.state.username}</p>
                    <p>Function: {this.state.function}</p>
                    <p>Email: {this.state.email}</p>
                    <p>Contact: {this.state.phone}</p>

                </div>
            </div>

        )
    }


}

export default UserData