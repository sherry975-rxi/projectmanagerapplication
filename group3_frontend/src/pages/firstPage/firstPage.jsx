import React, { Component } from 'react';
import './firstPage.css';
import Button from '../../components/button/button';
import macs from './appWebLogo.png'

class firstPage extends Component {
    render() {
        return (
            <div>
                <div className="Bar">
                    <div className="Logo">
                        <center>
                            <p className="switch">appManager</p>
                        </center>
                        <center>
                            <p className="subTitle">
                                Project Management Application
                            </p>
                        </center>
                    </div>

                    <div className="ButtonsStartPage">
                        <center><img src={macs} className="macs" alt="mcs" /></center>
                        <table className="tableButtom">
                            <tbody>
                                <tr className="spacer">
                                    <th className="spacer">
                                        <a href="/signUp">
                                            <Button text="REGISTER" />
                                        </a>
                                    </th>
                                    <th className="spacer">
                                        <a href="/login">
                                            <Button text="LOGIN" />
                                        </a>
                                    </th>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }
}

export default firstPage;
