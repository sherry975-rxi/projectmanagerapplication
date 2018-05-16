import React, { Component } from "react";
import axios from "axios";
import "./MarkTaskAsFinished.css";
import { FormGroup, FormControl, ControlLabel } from "react-bootstrap";

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: ""
        };
    }

    handleChange = event => {
        this.setState({ id: event.target.value });
    };

    handleSubmit = async event => {
        event.preventDefault();

        // Value of id is inside of the response const.
        const response = await axios.patch(`projects/2/tasks/${this.state.id}`);
        console.log(response);
        console.log(response.data);
    };

    render() {
        return (
            <div className=" table-striped">
            <h3>
            <b>Mark task as finished</b>
            </h3>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="id" bsSize="large">
                        <ControlLabel>Type Task ID</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.id}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <button
                        className="btn btn-primary" /*onClick={this.userDetail}*/
                    >
                        Finish
                    </button>
                </form>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
