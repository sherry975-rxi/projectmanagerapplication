import React, { Component } from 'react';
import './mediumButton.css';

class MediumButtonSelected extends Component {
    render() {
        return (
            <button
                type="button"
                className="selected"
                href={this.props.href}
            >
                {this.props.text}
            </button>
        );
    }
}

export default MediumButtonSelected;
