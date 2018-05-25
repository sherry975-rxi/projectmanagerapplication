import React, { Component } from "react";
import Graph from "./OnGoingTasksGraph.js";
import "./Homepage.css";

import Weather from "./Weather"


class Homepage extends Component{

    render(){
        return(
            <div className="HomepageContainer">

            <Graph className="navbar-dark"></Graph>
            <Weather className="teste"></Weather>

            </div>
        )
    }


}

export default Homepage;