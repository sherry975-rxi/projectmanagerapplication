import React, { Component } from "react";
import axios from "axios";
import "./MarkTaskAsFinished.css";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import AuthService from '../loginPage/AuthService';
import MediumButton from './../../components/button/mediumButton';

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
        this.handleClick = this.handleClick.bind(this);
    }


    async handleClick(event) {

        this.AuthService.fetch(`/projects/${this.props.project}/tasks/${this.props.id}`, {

            method: "PATCH"
        }).then((responseData) => {
            console.log(responseData);
            this.props.onClick();
        });

    };



    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick}>
                    Mark as Finished
               </button>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
