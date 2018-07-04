import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import { Redirect } from 'react-router-dom';
import { Modal } from 'react-bootstrap'


class CreateAssignmentRequest extends Component {
    constructor(props) {
        super(props);
        this.state = {
            shouldRender: true,
            isActiveInTask: false,
            request: {},
            tasks: {}
        };
        this.AuthService = new AuthService();

    }

    async componentDidMount() {


        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}/requests/user/${this.AuthService.getUserId()}`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                request: responseData
            });
            if (responseData.type === 'REMOVAL'){
                this.setState({
                    shouldRender: false
                })
            }
            if (responseData.error !== null) {
                this.setState({
                    shouldRender: false
                })
            }
        }).catch(err => {
        });

        this.getActiveTaskTeam();

    }

    async getActiveTaskTeam() {
        this.AuthService.fetch(`/projects/${this.props.project}/tasks/${this.props.id}/activeTeam`,
            { method: 'GET' }
        ).then(response => {
            this.setState({
                activeTeam: response,
                message: response.error,
            });

            try{
            this.state.activeTeam.map((collab, index) => {

                if (collab.projCollaborator.collaborator.userID == this.AuthService.getUserId()) {
                    this.setState({
                        isActiveInTask: true
                    })
                }

                return collab
            })
        } catch (error) {}
        });
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
                        shouldRender: false,
                        show : false

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

    displayConfirmation() {
        return (
            < div >
                <Modal.Header closeButton>
                    <Modal.Title> Create Request</Modal.Title>
                </Modal.Header>
                <Modal.Body>

                    {<p><b>Do you want to create a request to be assigned to task {this.props.id}?</b> </p> }

                </Modal.Body>
                <Modal.Footer>
                    <span><button className="cancelButton" onClick={() => this.setState({ show: false })}>Cancel</button></span><span><button className="genericButton" onClick={this.handleClick.bind(this)}>Confirm</button> </span>
                </Modal.Footer>
            </div>)
    }

    handleClose() {
        this.setState({ show: false });
    }
 
    handleShow() {
        this.setState({ show: true });
    }


    render() {

        if (!this.state.isActiveInTask) {
            if (this.state.shouldRender) {
                return (
                    <div>
                         <button className="genericButton" onClick={this.handleShow.bind(this)}>
                            Request Assign
                        </button>
                       <Modal show={this.state.show} onHide={this.handleClose.bind(this)} style={{position: "fixed",}}>
                            {this.displayConfirmation()}
                        </Modal>
                    </div>
                );
            } else {
                return (
                    <div className=" table-striped">
                        <button className="buttonFinishedRequestCreated" onClick={this.handleAlreadyCreatedRequestClick}>
                            Awaiting response
                        </button>
                    </div>
                );
            }
        } else {
            return null
        }
    }
}


export default CreateAssignmentRequest;
