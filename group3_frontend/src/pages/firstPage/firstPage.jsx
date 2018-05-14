import React, { Component } from 'react';
import './firstPage.css';

class firstPage extends Component {
    state = {}
    render() {
        return (
            <div className="Bar">
                <div className="Logo">
                    <h1 className="switch">appManager</h1>
                    <p className="subTitle">Project Management Application</p>
                </div>
                <div className="Login">
                    <button type="button" class="btn btn-primary btn-lg">REGISTER</button>
                </div>
            </div>
        )
    }
}

export default firstPage;