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

    handleClick = () => {
        const requestBodyDTO = {
            email: this.AuthService.getProfile().sub
        }    

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
                Request task assignment</button>
            </div>
        );
    }
}

export default CreateRequest;


