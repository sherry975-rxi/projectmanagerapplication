import React, { Component } from 'react';
import './main.css';

class Main extends Component {

  constructor(props) {
    super(props)
  }


  render() {

    return (

        <div className="Bar">
          <div className="Logo">
            <h1 className="switch">{this.props.title}</h1>
            <p className="subTitle">{this.props.subTitle}</p>
          </div>


            <div className="leftBar"></div>
            <div className="rightBar"></div>

        </div>

    
    );
  }
}

export default Main;