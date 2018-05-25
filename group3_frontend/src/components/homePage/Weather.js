import React, { Component } from "react";
import Forecast from "react-forecast";
import "./Homepage.css";



class Weather extends Component{
  render(){
    return (
        <div className="teste" style={{width:600}}>
        <Forecast latitude={41.08} longitude={-8.61}  name='Porto' color='#27c5b1' font="Verdana" units='ca'/>
        </div>
      );
  }
    
}

export default Weather;