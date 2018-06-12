import React, { Component } from 'react';
import './smallButton.css';
import { Glyphicon } from 'react-bootstrap';

class SmallButton extends Component {
    render() {
        let pencil = (
            <Glyphicon
                className="pencilIcon"
                glyph="glyphicon glyphicon-pencil"
            />
        );

        return (
            <button
                type="button"
                className="smallButton"
                href={this.props.href}
            >
                {pencil}
                {this.props.text}
            </button>
        );
    }
}

export default SmallButton;
