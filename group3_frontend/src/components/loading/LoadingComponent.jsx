import React, { Component } from 'react';
import './LoadingComponent.css';

class LoadingComponent extends Component {
    render() {
        return (
            <div className="container" >
                <center><div className="loader"></div> </center>
            </div >
        );
    }
}

export default LoadingComponent;