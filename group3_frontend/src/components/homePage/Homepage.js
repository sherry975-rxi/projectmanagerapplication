import React, { Component } from "react";
import Graph from "./OnGoingTasksGraph.js";
import "./Homepage.css";
import Weather from "./Weather";
import UserData from "./UserData";
import TaskGraph from './TaskGraph.js';


class Homepage extends Component{

    render()

    {

        const userId = UserData.getUserID;




        return(
            <div className="HomepageContainer">
                <UserData></UserData>
                 {/* <Graph></Graph> */}
                <Weather className="teste"></Weather>
                <div className="TaskGraphUpperContainer">
                    <h1>Tasks Deadline</h1>
                    <TaskGraph />
                </div>
            </div>
        )
    }


}

export default Homepage;