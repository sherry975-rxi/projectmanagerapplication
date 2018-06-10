import React, { Component } from 'react';
import './button.css';

class Button extends Component {
    render() {
        return (
            <button type="button" className="button" href={this.props.href}>
                {this.props.text}
            </button>
        );
    }
}

export default Button;
