import React, { Component } from 'react';
import './firstPage.css';
import logo from './top_banner.png';
import brand from './brand.svg';
import Button from '../../components/button/button';

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
                <div>
                    <img src={logo} alt="logo" className="image" href="/" />
                </div>
                <img src={brand} alt="brand" className="switch" href="/" />
            </div>
        );
    }
}

export default firstPage;
