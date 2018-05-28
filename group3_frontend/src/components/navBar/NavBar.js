import React, { Component } from "react";
import { Glyphicon } from "react-bootstrap";
import "./NavBar.css";
import "./Profile.js";
import logo from "./appManager-logo.svg"
import withAuth from '../../security/withAuth';
import { Link } from 'react-router-dom'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'
import { logout } from '../../authentication/authenticationActions'
import AuthService from '../../pages/loginPage/AuthService';
const Auth = new AuthService();

class NavBar extends Component {


    render() {
        console.log(this.props.logoutButton)

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
                            <li>
                                <a className={this.props.logoutButton} onClick={this.props.logout}><Link to="/login"> Logout </Link></a>
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
