import React, { Component } from 'react';
import './MarkTaskAsFinished.css';
import AuthService from '../loginPage/AuthService';

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleClick = () => {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}`,
            {
                method: 'PATCH'
            }
        ).then(responseData => {
            console.log(responseData);
            this.props.onClick();
        });
    };

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick}>
                    Mark as Finished
                </button>
            </div>
        );
    }
}

export default MarkTaskAsFinished;
