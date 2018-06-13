import React, { Component } from 'react';
import './MarkTaskAsFinished.css';
import AuthService from '../loginPage/AuthService';

class DeleteTask extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleClick = () => {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}`,
            {
                method: 'DELETE'
            }
        ).then(responseData => {
            console.log(responseData);
        });
    };

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonDeleted" onClick={this.handleClick}>
                    Delete
                </button>
            </div>
        );
    }
}

export default DeleteTask;
