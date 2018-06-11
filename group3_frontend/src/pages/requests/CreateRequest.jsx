import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';

class CreateRequest extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    handleSubmit = event => {
        event.preventDeafault();

        const requestBodyDTO = {
            email: this.AuthService.getProfile().sub
        }
    };

    handleClick = () => {
        console.log(requestBodyDTO)
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}/requests/assignmentRequest`,
            {
                method: 'POST',
                body: JSON.stringify(requestBodyDTO)
            }
        ).then(responseData => {
            console.log(responseData);
            this.props.onClick();
        });
    };

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonAssignmentRequest" onClick={this.handleClick}>
                    Create Assignment Request
                </button>
            </div>
        );
    }
}

export default CreateRequest;


