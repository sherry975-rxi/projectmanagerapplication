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
            </div>
        )
    }
}

export default firstPage;