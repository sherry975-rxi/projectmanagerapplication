import React, { Component } from 'react';
import { Glyphicon, Button } from 'react-bootstrap';
import './NavBar.css'

class NavBar extends Component {
  state = {};
  
  render() {
    return (
      <nav className="navbar navbar-light navbar-fixed-top">
        <div className="container-fluid">
          <div className="navbar-header">

            <Glyphicon className="menu-icon" glyph="menu-hamburger" onClick={this.props.toogleMenu} />
            <a className="navbar-brand" href="#">appManager</a>
          </div>
          
          <div id="navbar" className="navbar-collapse collapse">
            <ul className="nav navbar-nav navbar-right">
              <li><a href="#">Home</a></li>
              <li><a href="#">About</a></li>
              <li><a href="#">Profile</a></li>
            </ul>
          </div>
        </div>
      </nav>
    );
  }
}
export default NavBar;
