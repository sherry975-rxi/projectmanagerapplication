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
import { updateProjectTeam } from '../../actions/projectTeamActions';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { updateAvailableUsers } from '../../actions/availableUsersAction';

class AddUserToProject extends Component {
    constructor(props) {
        super(props);
        this.state = {
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
        this.AuthService = new AuthService();
    }

    validateForm() {
        return this.state.costPerEffort > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    dropdownToggle(newValue) {
        this.props.updateAvailableUsers(this.props.project);
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
                email: this.props.availableUsers[event].email
            }
        };

        this.AuthService.fetch(`/projects/${this.props.project}/team`, {
            body: JSON.stringify(userDTO),
            method: 'POST'
        })
            .then(res => {
                if (res.costPerEffort !== 0) {
                    this.props.updateProjectTeam(this.props.project);
                    this.props.updateAvailableUsers(this.props.project);
                    toastr.success('Collaborator was added to Project');
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
                    {this.props.availableUsers.map((projTeamitem, index) => (
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

const mapStateToProps = state => {
    return {
        availableUsers: state.availableUsers.availableUsers
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators(
        { updateProjectTeam, updateAvailableUsers },
        dispatch
    );
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AddUserToProject);
