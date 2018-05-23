import React, { Component } from "react";
import axios from "axios";
import "./MarkTaskAsFinished.css";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import AuthService from '../loginPage/AuthService';

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);
        this.state = {
            message: "Mark as Finished"
        };
        this.AuthService = new AuthService();
        this.askConfirmation = this.askConfirmation.bind(this);
    }

    askConfirmation() {
        this.setState({
            message: "Click again to finish!"
        });
    }

    handleClick = async event => {

        if(this.state.message === "Mark as Finished") {
            this.askConfirmation();
        } else {

            this.AuthService.fetch(`/projects/2/tasks/${this.props.id}`, {

                method: "PATCH"
            })
            //.then(function (myJson) {
            //   console.log(myJson);
            //})
                .then(res => {
                    //If sucessfull the user gets redirected to its home page
                    if (res.status == 200)
                        this.props.update;

                }).catch(err => {
                alert(err);
            });
        }
    };

    

    render() {
        return (
            <div className=" table-striped">
               <button className="btn btn-primary" onClick={this.handleClick}>
                   {this.state.message}
               </button>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
