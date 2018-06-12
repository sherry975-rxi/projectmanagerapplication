import React, { Component } from 'react';
import './ProjectCost.css';
import {
    Button,
    FormGroup,
    FormControl,
    ControlLabel,
    Alert,
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';
import MediumButton from '../../components/button/mediumButton.jsx';
import props from '../../components/msgs/Messages';

class AvailableListOfCollaborators extends Component {
    constructor(props) {
        super(props);
        this.match,
        this.state = {
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

    // Load users from database
     getProjTeam() {
        this.AuthService.fetch(
            `/projects/2/activeTeam`,
            { method: 'get' }
        ).then(responseData => {
            console.log(responseData)
            this.setState({
                projTeam: responseData,
                message: responseData.error
            });
            console.log(this.state.projTeam)
            console.log('hghghghgh')
        });
    }

handleClick(event) {
    
    console.log(this.state.projTeam[event].collaborator.email)

    const userDTO = {
        
        email: this.state.projTeam[event].collaborator.email}

        this.AuthService.fetch(
            `/projects/2/tasks/WP1.T01/addCollab`,
            { method: 'post',
        body: JSON.stringify(userDTO)}
        ).then(responseData => {
            console.log(responseData)
               
            });
}

    renderDropdownButton(title, i) {
        return (
          <DropdownButton bsStyle={title.toLowerCase()} title={title} key={i} id={`dropdown-basic-${i}`}>
          {this.state.projTeam.map((projTeamitem, index) => 
          <MenuItem eventKey={index} onSelect={this.handleClick.bind(this)}> {projTeamitem.collaborator.name}</MenuItem>)}
          </DropdownButton>
        );
      }

    render() {
            return (
                <div className=" table-striped">  
                        <tbody>{this.renderDropdownButton("Add Collaborator",0)}</tbody>
                </div>
            );
        }
    }


export default AvailableListOfCollaborators;
