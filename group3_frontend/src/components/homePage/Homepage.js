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
                <tbody className="HomeTable">
                    <tr>

                        <td><UserData></UserData></td>
                        <td className="HomeTableTDLeft"><Weather className="teste"></Weather></td>

                    </tr>
                    <tr>

                        <td className="HomeTableTDBottomLeft" >
                            <div className="ProjUpperContainer">
                            <h1 className="GraphTitle">Active Projects</h1>
                             <ProjGraph />
                            </div>
                        </td>
                        <td className="HomeTableTDBottomLeft">
                             <div className="TaskGraphUpperContainer">
                                <h1 className="GraphTitle">Tasks Deadline</h1>
                                <TaskGraph />
                            </div>
                        </td>
                    </tr> 
                </tbody>

            </div>
        )
    }


}

export default Homepage;