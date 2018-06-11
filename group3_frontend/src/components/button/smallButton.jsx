import React, { Component } from "react";
import "./smallButton.css";
import { Glyphicon } from 'react-bootstrap';


class SmallButton extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        let pencil =
            <Glyphicon className="pencilIcon" glyph="glyphicon glyphicon-pencil" />

        return (
            <button type="button" className="smallButton" href={this.props.href}>{pencil}{this.props.text}</button>

        )
    }
}

export default SmallButton;