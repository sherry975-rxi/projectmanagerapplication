import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import { Redirect } from 'react-router-dom';


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
        ).then(res => {
            console.log(res)
                //If the loggin is sucessfull the user gets redirected to its home page
                if (res.assignmentRequest === true) {
                    toastr.success('Your Request was sucessfull created!');
                     return <Redirect to="/requests" />;
                }
                console.log(this.props.authenticated)
            })
            .catch(err => {
                console.log(err);
                toastr.error('Your Request was not created!');
            });
    };

    

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick}>
                Create Request</button>
            </div>
        );
    }
}

export default CreateRequest;


