import React from "react";
import "./NavBar.css";

export class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {}
        };
    }

    componentDidMount() {
        fetch("/users/2", { method: "get" })
            .then(response => response.json())
            .then(responseData => {
                this.setState({
                    user: responseData
                });
            });
    }

    renderUsersList() {
        var userItem = this.state.user;
        return (
            <div>
                <p>
                    {" "}
                    Name: &nbsp;
                    {userItem.name}
                </p>
                <p>
                    {" "}
                    Email: &nbsp;
                    {userItem.email}
                </p>
                <p>
                    {" "}
                    Function: &nbsp;
                    {userItem.function}
                </p>
                <hr />
            </div>
        );
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
