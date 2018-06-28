import React, { Component } from 'react';
import './Homepage.css';
import Weather from './Weather';
import TaskGraph from './TaskGraph.js';
import ProjGraph from './ProjectGraph';
import Calendar from './Calendar'



class HomepageCollaborator extends Component {


        
    render(){

        return(
            <table className="HomepageContainer">
            <tbody className="HomeTable">
                <tr>
                    <td className="HomeTableTDTopLeft">
                        <Calendar />
                    </td>
                    <td className="HomeTableTDLeft">
                        <Weather className="teste" />
                    </td>
                </tr>
                <tr>
                    <td className="HomeTableTDBottomLeft">
                        <div className="ProjUpperContainer">
                            <h1 className="GraphTitle">Active Projects</h1>
                            <ProjGraph />
                        </div>
                    </td>
                    <td className="HomeTableTDBottomLeft">
                        <div className="TaskGraphUpperContainer">
                            <h1 className="GraphTitle">Your active Tasks</h1>
                            <TaskGraph />
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    );
    }

    }




export default HomepageCollaborator;