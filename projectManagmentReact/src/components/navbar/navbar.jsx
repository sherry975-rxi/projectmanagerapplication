import React, { Component } from 'react';
import './navbar.css';
import Sidebar from './sidebar';

class Navbar extends Component {

  onClick() {
    this.refs.Sidebar.toggleSidebar();
  }

  render() {

    return (
        <nav>
          <div className="nav-wrapper">
            <a href="#" className="brand-logo center"><b>mySwitch </b></a>
            <div className="toggle-btn">
              <a href="#" onClick={this.onClick.bind(this)}>
                <span></span>
                <span></span>
                <span></span>
              </a>
            </div>
            <ul id="nav-mobile" className="right hide-on-med-and-down">
              <li><a href="sass.html">Home</a></li>
              <li><a href="badges.html">Contacts</a></li>
              <li><a href="collapsible.html">About</a></li>
            </ul>
          </div>
          <Sidebar ref="Sidebar"/> 
        </nav>
    );
  }
}


export default Navbar;

