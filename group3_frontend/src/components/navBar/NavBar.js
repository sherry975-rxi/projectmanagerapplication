import React, { Component } from "react";
import { Glyphicon } from "react-bootstrap";
import "./NavBar.css";
import "./Profile.js";
import logo from "./appManager-logo.svg"
import { Link } from 'react-router-dom'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { logout } from '../../authentication/authenticationActions'
import AuthService from '../../pages/loginPage/AuthService';
import { Redirect } from 'react-router-dom';
const Auth = new AuthService();
class NavBar extends Component {

    render() {
        return (
            <nav className="navbar navbar-light navbar-fixed-top">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <div className="Logo-navbar">appManager</div>
                        <Glyphicon
                            className="menu-icon"
                            glyph="menu-hamburger"
                            onClick={this.props.toogleMenu}
                        />

                    </div>

                    <div id="navbar" className="navbar-collapse collapse">
                        <ul className="nav navbar-nav navbar-right">
                            <li>
                                <a className={this.props.logoutButton} onClick={this.props.logout}><Link to="/homePage"> <Glyphicon className="houseIcon" glyph="glyphicon glyphicon-home" /></Link></a>
                            </li>
                            <li>
                                <a className={this.props.logoutButton} onClick={this.props.logout}><Link to="/login"> Logout       <Glyphicon className="userIcon" glyph="glyphicon glyphicon-user" /></Link></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

const mapStateToProps = state => { return ({ logoutButton: state.authenthication.logoutButton }) }
const mapDispatchToProps = dispatch => bindActionCreators({ logout }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(NavBar);
