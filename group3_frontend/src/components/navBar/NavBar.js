import React, { Component } from "react";
import { Glyphicon } from "react-bootstrap";
import "./NavBar.css";
import "./Profile.js";
import logo from "./appManager-logo.svg"

class NavBar extends Component {
    state = {};

    render() {
        return (
            <nav className="navbar navbar-light navbar-fixed-top">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <Glyphicon
                            className="menu-icon"
                            glyph="menu-hamburger"
                            onClick={this.props.toogleMenu}
                        />
                        <div className="Logo-navbar">appManager</div>

                    </div>

                    <div id="navbar" className="navbar-collapse collapse">
                        <ul className="nav navbar-nav navbar-right">
                            <li>
                                <a href="/">Home</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}
export default NavBar;
