import React, { Component } from 'react';
import {
    MenuItem,
    DropdownButton,
    FormGroup,
    FormControl,
    ControlLabel
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import { Redirect } from 'react-router-dom';

class AddUserToProject extends Component {
    constructor(props) {
        super(props);
        this.match,
            (this.state = {
                costPerEffort: '',
                projTeam: [],
                projCollab: '',
                submission: false,
                hideSuccessInfo: 'hide-code'
            });
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getProjTeam();
    }

    validateForm() {
        return this.state.costPerEffort > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    // Load users from database
    getProjTeam() {
        this.AuthService.fetch(
            `/projects/${this.props.project}/team/usersAvailable`,
            { method: 'get' }
        ).then(responseData => {
            console.log(responseData);
            this.setState({
                projTeam: responseData,
                message: responseData.error
            });
            console.log(this.state.projTeam);
            console.log('hghghghgh');
        });
    }

    handleClick(event) {
        console.log(this.state.projTeam[event].email);
        const costPerEffort = this.state.costPerEffort;

        const userDTO = {
            costPerEffort,
            project: {
                projectId: 2
            },
            collaborator: {
                email: this.state.projTeam[event].email
            }
        };

        this.AuthService.fetch(`/projects/${this.props.project}/team`, {
            method: 'post',
            body: JSON.stringify(userDTO)
        })
            .then(res => {
                console.log(res);
                console.log('seeee');
                if (res.costPerEffort !== 0) {
                    toastr.success('Collaborator was added to Project');
                    this.getProjTeam();
                    return (
                        <Redirect to="/projects/${this.props.project}/tasks/${this.props.id}/addCollab" />
                    );
                }
            })
            .catch(err => {
                console.log(err);
                toastr.error('Already a Project Collaborator');
            });
    }

    renderDropdownButton(title, i) {
        return (
            <DropdownButton
                bsStyle={title.toLowerCase()}
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
                className="mediumButton"
            >
                <MenuItem eventKey="XF9NAKamas">
                    <FormGroup controlId="costPerEffort" bsSize="small">
                        <ControlLabel>Collaborator Cost</ControlLabel>
                        <FormControl
                            autoFocus
                            type="number"
                            pattern="[0-9]*"
                            inputmode="numeric"
                            value={this.state.costPerEffort}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                </MenuItem>
                <MenuItem divider />
                {/* {this.state.projTeam.map((projTeamitem, index) => (
                    <MenuItem
                        disabled={!this.validateForm()}
                        eventKey={index}
                        onSelect={this.handleClick.bind(this)}
                    >
                        {' '}
                        {projTeamitem.name}
                    </MenuItem>
                ))} */}
            </DropdownButton>
        );
    }

    render() {
        return (
            <div className=" table-striped">
                <tbody>
                    {this.renderDropdownButton('Add Collaborator', 0)}
                </tbody>
            </div>
        );
    }
}

export default AddUserToProject;
