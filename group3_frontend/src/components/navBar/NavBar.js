import React, { Component } from 'react';
import { Glyphicon } from 'react-bootstrap';
import './NavBar.css';
import './Profile.js';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { logout } from '../../actions/authenticationActions';
import AuthService from '../../pages/loginPage/AuthService';

const Auth = new AuthService();
class NavBar extends Component {
    render() {
        const navbar = Auth.loggedIn() ? 'navbar' : 'greenBar';
        const navBarSpecifications = navbar + ' navbar-light navbar-fixed-top';
        const logo = 'Logo-' + navbar;

        return (
            <nav className={navBarSpecifications}>
                <div className="container-fluid">
                    <div className="navbar-header">
                        <div className={logo}>appManager</div>
                        <Glyphicon
                            className="menu-icon"
                            glyph="menu-hamburger"
                            onClick={this.props.toogleMenu}
                        />
                    </div>

                    <div id={navbar} className="navbar-collapse collapse">
                        <ul className="nav navbar-nav navbar-right">
                            <li>
                                <Link
                                    className={this.props.logoutButton}
                                    to="/homepage"
                                >
                                    <Glyphicon
                                        className="houseIcon"
                                        glyph="glyphicon glyphicon-home"
                                    />
                                </Link>
                            </li>
                            <li>
                                <Link
                                    className={this.props.logoutButton}
                                    onClick={this.props.logout}
                                    to="/login"
                                >
                                    Logout
                                    <Glyphicon
                                        className="userIcon"
                                        glyph="glyphicon glyphicon-user"
                                    />
                                </Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        );
    }
}

const mapStateToProps = state => {
    return { logoutButton: state.authenthication.logoutButton };
};
const mapDispatchToProps = dispatch => bindActionCreators({ logout }, dispatch);
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(NavBar);
