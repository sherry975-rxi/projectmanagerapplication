import React, { Component } from "react";
import axios from "axios";
import "./MarkTaskAsFinished.css";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import AuthService from '../loginPage/AuthService';

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
        this.handleClick = this.handleClick.bind(this);
    }


    async handleClick(event) {

            this.AuthService.fetch(`/projects/2/tasks/${this.props.id}`, {

                method: "PATCH"
            }).then((responseData) => {
                console.log(responseData);
                this.props.onClick();
            });

            // .then(function (myJson) {
            //   console.log(myJson);
            //   //window.location.reload(true);
            //
            // })

            //     .then(res => {
            //         //If sucessfull the user gets redirected to its home page
            //         if (res.status === 200) {
            //             console.log("TEST");
            //         }
            //     }).catch(err => {
            //     alert(err);
            // });


    };

    

    render() {
        return (
            <div className=" table-striped">
               <button className="btn btn-primary" onClick={this.handleClick}>
                   Mark Finished
               </button>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
