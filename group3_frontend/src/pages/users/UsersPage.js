import React, { Component } from "react";
import "./UsersPage.css";

class UsersPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: []
        };
    }

    componentDidMount() {
        fetch("users/allUsers", { method: "GET" })
            .then(response => response.json())
            .then(responseData => {
                this.setState({
                    users: responseData
                });
            });
    }

    renderUsersList() {
        return (
            <div>
                <UserTable users={this.state.users} />
            </div>
        );
    }

    render() {
        return (
            <div>
                <h1 className="page-header">Users</h1>
                {this.renderUsersList()}
            </div>
        );
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
                        {userData.systemUserStateActive ? "Active" : "Disabled"}
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
                    <b>List of All Users</b>
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
