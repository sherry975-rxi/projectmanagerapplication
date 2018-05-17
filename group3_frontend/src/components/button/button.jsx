import React, { Component } from "react";
import "./button.css";


class Button extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <button type="button" className="button" >{this.props.text}</button>
        )
    }
}

export default Button;