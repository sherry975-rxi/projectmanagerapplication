import React, { Component } from "react";
import "./mediumButton.css";


class MediumButton extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <button type="button" className="mediumButton" href={this.props.href}>{this.props.text}</button>
        )
    }
}

export default MediumButton;