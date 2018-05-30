import React, { Component } from "react";
import "./Homepage.css";
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import logoImage from './images/logo_v2.png';



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
    

        return(
            <div className="UserDataHomepage">
                <img  src={logoImage} alt="logoImage"/>
                <h1 className="HomepageTitle">Welcome</h1>
                <div className="UserDataText">
                    <table className="tableUsernameText">
                    <tbody>
                        <tr className>
                            <td className="tdUserLeft">Name:</td>
                            <td className="tdUserRight">{this.state.username}</td>
                        </tr>
                        <tr>
                            <td className="tdUserLeft"> Function:</td>
                            <td className="tdUserRight">{this.state.function}</td>
                        </tr>
                        <tr>
                            <td className="tdUserLeft">Email:</td>
                            <td className="tdUserRight">{this.state.email}</td>
                        </tr>
                        <tr>
                            <td className="tdUserLeft">Contact:</td>
                            <td className="tdUserRight">{this.state.phone}</td>
                        </tr>
                        </tbody>
                    </table>


                </div>
            </div>

        )
    }


}

export default UserData