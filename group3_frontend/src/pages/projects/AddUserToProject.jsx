import React, { Component } from 'react';
import {
    MenuItem,
    Dropdown,
    FormGroup,
    FormControl,
    ControlLabel
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';

class AddUserToProject extends Component {
    constructor(props) {
        super(props);
        this.state = {
            costPerEffort: '',
            projTeam: [],
            projCollab: '',
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
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
            this.setState({
                projTeam: responseData,
                message: responseData.error
            });
        });
    }

    dropdownToggle(newValue) {
        if (this._forceOpen) {
            this.setState({ menuOpen: true });
            this._forceOpen = false;
        } else {
            this.setState({ menuOpen: newValue });
        }
    }

    menuItemClickedThatShouldntCloseDropdown = () => {
        this._forceOpen = true;
    };

    handleClick(event) {
        const costPerEffort = this.state.costPerEffort;
        const projectId = this.props.project;

        const userDTO = {
            costPerEffort,
            project: {
                projectId: projectId
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
                if (res.costPerEffort !== 0) {
                    toastr.success('Collaborator was added to Project');
                    this.getProjTeam();
                    window.location.href = `/myprojects`;
                }
            })
            .catch(err => {
                toastr.error('Already a Project Collaborator');
            });
    }

    renderDropdownButton(title, i) {
        return (
            <Dropdown
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
                open={this.state.menuOpen}
                onToggle={val => this.dropdownToggle(val)}
            >
                <Dropdown.Toggle className="option">{title}</Dropdown.Toggle>
                <Dropdown.Menu className="super-colors">
                    <MenuItem
                        eventKey="XF9NAKamas"
                        onClick={this.menuItemClickedThatShouldntCloseDropdown}
                    >
                        <FormGroup controlId="costPerEffort" bsSize="small">
                            <ControlLabel>Collaborator Cost</ControlLabel>
                            <FormControl
                                autoFocus
                                type="number"
                                pattern="[0-9]*"
                                inputMode="numeric"
                                onClick={this.onClickT}
                                value={this.state.costPerEffort}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                    </MenuItem>
                    <MenuItem divider />
                    {this.state.projTeam.map((projTeamitem, index) => (
                        <MenuItem
                            disabled={!this.validateForm()}
                            eventKey={index}
                            key={index}
                            onSelect={this.handleClick.bind(this)}
                        >
                            {projTeamitem.name}
                        </MenuItem>
                    ))}
                </Dropdown.Menu>
            </Dropdown>
        );
    }

    render() {
        return this.renderDropdownButton('Add Collaborator', 0);
    }
}

export default AddUserToProject;
