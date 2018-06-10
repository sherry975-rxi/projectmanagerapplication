import React, { Component } from 'react';
import './UsersPage.css';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import axios from 'axios';

class UsersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            message: ''
        };
        this.AuthService = new AuthService();
    }

    async componentdidMount() {
        axios
            .get('/users/allUsers', {
                method: 'get'
            })
            .then(responseData => {
                this.setState({
                    users: responseData,
                    message: responseData.status
                });
            });
    }

    render() {
        if (this.state.message === '') {
            return <Error message={this.state.message} />;
        } else {
            return <UserTable users={this.state.users} />;
        }
    }
}

class UserTable extends React.Component {
    render() {
        var users = this.props.users.map(userData => {
            return (
                <tr>
                    <td>{userData.userID}</td>
                    <td>{userData.name}</td>
                    <td>{userData.email}</td>
                    <td>{userData.function}</td>
                    <td>{userData.userProfile}</td>
                    <td>
                        {userData.systemUserStateActive ? 'Active' : 'Disabled'}
                    </td>
                    <td>
                        <button
                            className="btn btn-primary" /*onClick={this.userDetail}*/
                        >
                            Details
                        </button>
                    </td>
                </tr>
            );
        });

        return (
            <div className=" table-striped">
                <h3>
                    <b>All Users</b>
                </h3>
                <table className="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Function</th>
                            <th>Profile</th>
                            <th>Status</th>
                            <th> </th>
                        </tr>
                    </thead>
                    <tbody>{users}</tbody>
                </table>
            </div>
        );
    }
}

export default UsersPage;
