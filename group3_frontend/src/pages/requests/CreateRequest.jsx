import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import { Redirect } from 'react-router-dom';

class CreateRequest extends Component {
    constructor(props) {
        super(props);
        this.state = {
            shouldRender: true,
            request: {}
        };
        this.AuthService = new AuthService();

    }

    componentDidMount() {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}/requests/user/${this.AuthService.getUserId()}`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                request: responseData
            });
            if(JSON.stringify(this.state.request).length > 0){
                this.setState({
                    shouldRender: false
                })
            }
        }).catch(err => {
        });;
    }



      

   
    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value,
        });
    };

    handleClick = () => {
        const requestBodyDTO = {
            email: this.AuthService.getProfile().sub
        };

        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${
                this.props.id
            }/requests/assignmentRequest`,
            {
                method: 'POST',
                body: JSON.stringify(requestBodyDTO)
            }
        )
            .then(res => {
                //If the loggin is sucessfull the user gets redirected to its home page
                if (res.assignmentRequest === true) {
                    toastr.success('Your Request was sucessfull created!');
                    this.setState({
                        shouldRender: false
                    })
                    
                    return <Redirect to="/requests" />;
                }
            })
            .catch(err => {
                toastr.error('Your Request was not created!');
            });
    };

    handleAlreadyCreatedRequestClick = () => {
        toastr.warning('Your Request was already created. Please wait for the Project Manager response.');
    }


    

    render() {
        if(this.state.shouldRender){
            return (
                <div className=" table-striped">
                    <button className="buttonFinished" onClick={this.handleClick}>
                        Create Request
                    </button>
                </div>
            );
        }
        else{
            return (
                <div className=" table-striped">
                    <button className="buttonFinishedRequestCreated"  onClick={this.handleAlreadyCreatedRequestClick}>
                        Awaiting response
                    </button>
                </div>
            );
        }
       
    }
}

var divStyle = {
    background: "#eee",
    
  };

export default CreateRequest;
