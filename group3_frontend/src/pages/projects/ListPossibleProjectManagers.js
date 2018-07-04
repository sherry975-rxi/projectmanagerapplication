import React, { Component } from 'react';
import { MenuItem, DropdownButton } from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';

class ListPossibleProjectManagers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            collaborators: []
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.getCollaborators();
    }

    getCollaborators() {
        this.AuthService.fetch(`/users/profiles/COLLABORATOR`, {
            method: 'get'
        }).then(responseData => {
            this.setState({
                collaborators: responseData,
                message: responseData.error
            });
        });
    }

    handleClick(event) {
        this.props.onSelect(this.state.collaborators[event]);
    }

    renderDropdownButton(title, i) {
        return (
            <DropdownButton
                bsStyle={title.toLowerCase()}
                title={title}
                key={i}
                className="projectManagerButton"
                id={`dropdown-basic-${i}`}
            >
                {this.state.collaborators.map((projTeamitem, index) => (
                    <MenuItem
                        eventKey={index}
                        onSelect={this.handleClick.bind(this)}
                    >
                        {' '}
                        {projTeamitem.name}
                    </MenuItem>
                ))}
            </DropdownButton>
        );
    }

    render() {
        return (
            <div className=" table-striped">
                <tbody>
                    {this.renderDropdownButton('Select Project Manager', 0)}
                </tbody>
            </div>
        );
    }
}
export default ListPossibleProjectManagers;
