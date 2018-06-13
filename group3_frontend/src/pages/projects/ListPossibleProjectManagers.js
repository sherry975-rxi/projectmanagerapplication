import React, { Component } from 'react';
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';


class ListPossibleProjectManagers extends Component {
    constructor(props) {
        super(props);
        this.match,
            this.state = {
                collaborators: []
            };
        this.AuthService = new AuthService();
    }


    componentDidMount() {
        this.getCollaborators();
    }


    getCollaborators() {
        this.AuthService.fetch(
            `/users/profiles/COLLABORATOR`,
            { method: 'get' }
        ).then(responseData => {
            console.log(responseData)
            this.setState({
                collaborators: responseData,
                message: responseData.error
            });
            console.log(this.state.collaborators)
        });
    }

    handleClick(event) {

        console.log(this.state.collaborators[event].email);
        this.props.onSelect(this.state.collaborators[event]);


    }


    renderDropdownButton(title, i) {
        return (
            <DropdownButton bsStyle={title.toLowerCase()} title={title} key={i} id={`dropdown-basic-${i}`}>
                {this.state.collaborators.map((projTeamitem, index) =>
                    <MenuItem eventKey={index} onSelect={this.handleClick.bind(this)}> {projTeamitem.name}</MenuItem>)}
            </DropdownButton>
        );
    }

    render() {
        return (
            <div className=" table-striped">
                <tbody>{this.renderDropdownButton("Select Project Manager",0)}</tbody>
            </div>
        );
    }


} export default ListPossibleProjectManagers;