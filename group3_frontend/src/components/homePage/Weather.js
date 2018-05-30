import React, { Component } from "react";
import Forecast from "react-forecast";
import "./Homepage.css";



class Weather extends Component{
  render(){
    return (
        <div className="WeatherDiv">
        <Forecast latitude={41.08} longitude={-8.61}  name='Porto' color='#2b4153' font="Verdana" units='ca'/>
        </div>
      );
  }
    
}

export default Weather;