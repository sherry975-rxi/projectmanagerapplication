import React, { Component } from 'react';
import './itemsButton.css';

class ItemsButton extends Component {
    render() {
        return (
            <button
                type="button"
                className="itemsButton"
                href={this.props.href}
            >
                {this.props.text}
            </button>
        );
    }
}

export default ItemsButton;
