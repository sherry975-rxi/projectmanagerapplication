import React from "react";
import "./Profile.css";

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



    render() {
        return (
            <div className="external-div">

                <center><h1 className="switch">appManager</h1></center>
                <center><p className="subTitle">Project Management Application</p></center>

            </div>
        );
    }
}

export default Profile;
