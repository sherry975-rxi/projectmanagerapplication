import React from 'react';
import ReactDOM from 'react-dom';
import './NavBar.css'

export class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: {}
        }
    }

    componentDidMount() {
             fetch('/users/allUsers',{ method: 'get'}) 
            .then((response) => response.json()) 
            .then((responseData) => { 
                this.setState({ 
                  user: responseData, 
                }); 
            });     
    }

       
    renderUsersList(){
        //return this.state.user.map((userItem) =>{
            var userItem = this.state.user
            return(
                <div>
                <p>User ID: &nbsp;
                {userItem.userId}</p>
                <p> User Name: &nbsp;
                {userItem.name}</p>
                <p> Profile: &nbsp;
                {userItem.Profile}</p>
                <hr/>
                </div>
            )
        //})
    }

    render() {
        return (
            <div>
                <h1 className="page-header">My information</h1>              
                <h3>Info</h3>   
                {this.renderUsersList()}                        
            </div>
        );
    }
}

export default Profile; 

