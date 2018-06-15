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
            window.location.href = `/projects/${
                this.props.project
            }/tasks`;
    });
};

    

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick}>
                    Delete
                </button>
            </div>
        );
    }
}

export default DeleteTask;
