import React, { Component } from "react";
import "./firstPage.css";
import { NavLink } from "react-router-dom";
import background from "./background.jpg";
import logo from "./top_banner.png";
import brand from "./brand.svg";
import animatedLogo from "./Logo.gif";

import Button from "../../components/button/button";

class firstPage extends Component {
    state = {};
    render() {
        return (
            <div className="Bar">
                <img
                    className="LogoStartPage"
                    src={animatedLogo}
                    alt="animated gif"
                />

                <div className="ButtonsStartPage">
                    <table className="tableButtom">
                        <tr className="spacer">
                            <th className="spacer">
                                <a href="/signup">
                                    <Button text="REGISTER" />
                                </a>
                            </th>
                            <th className="spacer">
                                <a href="/login">
                                    <Button text="LOGIN" />
                                </a>
                            </th>
                        </tr>
                    </table>
                </div>
            </div>
        );
    }
}

export default firstPage;
