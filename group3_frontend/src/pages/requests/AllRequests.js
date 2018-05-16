import React, { Component } from 'react';
import axios from 'axios';
import { Button, FormGroup, FormControl, ControlLabel, Glyphicon } from "react-bootstrap";
import './AllRequests.css';

class AllRequests extends Component {

    constructor(props) {
        super(props);
        this.state = {
            requests: []
        }
    }

    async componentDidMount() {

        const requests = await axios.get('/projects/2/tasks/WP1.T01/requests');
        const teste = requests.data;
        this.setState({ requests : teste });
                   
    }

    renderRequests(){
        return this.state.requests.map((requestsItem) =>{
            return(
                
                <tr className="line">
                <td>{requestsItem.task.description}</td>
                <td>{requestsItem.projCollab.collaborator.name}</td>
                <td>{requestsItem.type}</td>
                
                <Button>
                    <Glyphicon className="ok-button" glyph="ok" />
                </Button>
                <Button>
                    <Glyphicon className="remove-button" glyph="remove" />
                </Button>
                </tr>
                
            )
        })
    }

    render() {
        return (
            <div className="Requests">
                <h3><b>List of All Requests</b></h3>
                <table className="table table-hover">
                    <thead>
                        <tr>
                            <th>Task</th>
                            <th>Collaborator Name</th>
                            <th>Request Type</th>
                        </tr>
                    </thead>
                    <tbody>
                    {this.renderRequests()}   
                    </tbody>
                </table>
            </div>
        );
    }
}

export default AllRequests;
