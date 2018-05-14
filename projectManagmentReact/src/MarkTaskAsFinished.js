import React, { Component } from 'react';
import logo from './logo.svg';

import axios from 'axios';


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
                <h3>Mark task as finished</h3>
               
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
