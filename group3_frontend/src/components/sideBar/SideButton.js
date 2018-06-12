import React from 'react';
import './SideButton.css';
import { NavLink } from 'react-router-dom';

class SideButton extends React.Component {


    render() {
        let icon = (
            <div className="icon" align="right">
                <span className="glyphicon glyphicon-chevron-right" />
            </div>
        );


        return (
                <div className="Item">
                    <NavLink to={this.props.to}>
                        <button className="ItemButton" >{this.props.text} </button>
                    </NavLink>
                    {icon}
                </div>
        );

    }
}

export default SideButton;
