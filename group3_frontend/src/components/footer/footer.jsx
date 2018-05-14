import React, { Component } from 'react';
import './footer.css';
import logo_isep from './logo_isep.png';
import porto_techHub from './porto_techHub.svg';
import switch_icon from './switch_icon.svg';

class Footer extends Component {

  render() {
    return (
      <div className="footer">
        
        <a href="https://www.isep.ipp.pt">
          <img className="logo_isep" src={logo_isep} alt="logo_isep"width="150" onMouseOver=".aa"/>
        </a>
        
        <a href="https://portotechhub.com">
          <img className="porto_techHub" src={porto_techHub} alt="porto_techHub"width="100"/>
        </a>
        
        <a href="http://www.portotechhub.com/switch/">
          <img className="switch_icon" src={switch_icon} alt="switch"width="120"/>
        </a>
      </div>
      
    );
  }
}

export default Footer;