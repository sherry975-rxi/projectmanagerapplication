import React, { Component } from "react";

class Error extends Component {

    constructor(props) {
        super(props)
    }
    render() {
        return (
            <div className="alert alert-danger" role="alert">
                <div className="container">
                    <h3><center><b>{this.props.message}</b></center></h3>
                </div>
            </div>
        );
    }
}

export default Error;