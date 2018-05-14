import React from 'react';
import './AllUsers.css';

class UsersList extends React.Component {
    constructor(props) {
        super(props);
        //this.userDetail = this.userDetail.bind(this);
        this.state = {
            users: []
        };
    }
   
    componentDidMount() {
    this.loadUsersFromServer();
    }
    
    // Load users from database
    loadUsersFromServer() {
        fetch('users/allUsers',
        { method: 'GET',}) 
        .then((response) => response.json()) 
        .then((responseData) => { 
            this.setState({ 
                users: responseData, 
            }); 
        });     
    } 

    // User Details
    userDetail(user) {
        fetch (user.links.self.href,
        { method: 'GET',})
        .then( 
            res => this.loadUsersFromServer()
        )
        .catch( err => console.error(err))                
    } 

    
    renderUsersList() {
      return (
         <div>
            <UserTable users={this.state.users} userDetail={this.userDetail}/> 
         </div>
      );
    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <h2>Welcome to Project Managment App</h2>
                </div>
                <p className="App-intro">
                    Here follows:
                </p>                
                <h3>List of All Users</h3>   
                {this.renderUsersList()}                        
            </div>
        );
    }
  }
          
  class UserTable extends React.Component {
      constructor(props) {
          super(props);
      }
      
      render() {
      var users = this.props.users.map(user =>
          <User key={user.userID} userData={user} userDetail={this.props.userDetail}/>
      );
      
  
      return (
        <div>
        <table className="table table-striped" >
          <thead>
            <tr>
              <th>ID</th><th>Name</th><th>Email</th><th>Function</th><th>Profile</th><th> </th>
            </tr>
          </thead>
          <tbody>{users}</tbody>
        </table>
        </div>);
    }
  }
  
          
  class User extends React.Component {
      constructor(props) {
          super(props);
          this.userDetail = this.userDetail.bind(this);
      }
  
      userDetail() {
          this.props.userDetail(this.props.userData);
      } 
   
      render() {
          return (
            <tr>
              <td>{this.props.userData.userID}</td>
              <td>{this.props.userData.name}</td>
              <td>{this.props.userData.email}</td>
              <td>{this.props.userData.function}</td>
              <td>{this.props.userData.userProfile}</td>
              <td>
                  <button className="btn btn-danger" /*onClick={this.userDetail}*/>Details</button>
              </td>
            </tr>
          );
      } 
  }
  
  
  
  export default UsersList;