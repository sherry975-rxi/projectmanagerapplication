import React, { Component } from "react";
import Graph from "./OnGoingTasksGraph.js";
import "./Homepage.css";
import Weather from "./Weather";
import UserData from "./UserData";
import TaskGraph from './TaskGraph.js';
import AuthService from '../../pages/loginPage/AuthService.js'
import axios from 'axios';
import ProjGraph from './ProjectGraph';


class Homepage extends Component{



    



    render()

    {
       


        return(
            <div className="HomepageContainer">
                
                <UserData></UserData>
                <Weather className="teste"></Weather>
                <ProjGraph />


                <div className="TaskGraphUpperContainer">
                    <h1 className="GraphTitle">Tasks Deadline</h1>
                    <TaskGraph />
                </div>

            </div>
        )
    }


}

export default Homepage;