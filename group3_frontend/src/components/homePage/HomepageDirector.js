import React, { Component } from 'react';
import './Homepage.css';
import Weather from './Weather';
import Calendar from './Calendar'
import ProjGraph from './ProjectGraphDirector';


class HomepageDirector extends Component {

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
                    
                </tr>
            </tbody>
          </table>
        )


    }

  
}

export default HomepageDirector;