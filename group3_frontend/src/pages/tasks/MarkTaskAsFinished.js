import React, { Component } from 'react';
import axios from 'axios';
import './MarkTaskAsFinished.css';
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";



class MarkTaskAsFinished extends Component {

    constructor(props) {
        super(props);
        this.state = {  
                id: ''           
        }
    }

    handleChange = event => {
        this.setState({ id: event.target.value });
      }

    handleSubmit = async event => {
        event.preventDefault();
      
        // Value of id is inside of the response const.
        const response = await axios.patch(`projects/2/tasks/${this.state.id}`);
        console.log(response);
        console.log(response.data);
    };

    render() {
        return (
            <div className="App"> 
                <h1 className="page-header">Mark task as finished</h1>                 
               
                <form onSubmit={this.handleSubmit}>
                    <label>
                        Please type task ID:
                        <input type="text" name="id" onChange={this.handleChange} />
                    </label>
                    <button type="submit">Finish</button>
                </form>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
