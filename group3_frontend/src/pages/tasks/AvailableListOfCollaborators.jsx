import React, { Component } from 'react';
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import { Redirect } from 'react-router-dom';


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
            `/projects/${this.props.project}/tasks/${this.props.id}/collabsAvailableForTask`,
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
            `/projects/${this.props.project}/tasks/${this.props.id}/addCollab`,
            { method: 'post',
        body: JSON.stringify(userDTO)}
        ).then(res => {
            console.log(res)
            console.log("seeee")
                 if (res.taskFinished === false) {
                        toastr.success('Collaborator was added to task');
                        this.getProjTeam();
                        window.location.href = `/projects/${this.props.project}/tasks`;
                    }
                })
                .catch(err => {
                    console.log(err);
                    toastr.error('Already a Task Collaborator');
                });
}



    renderDropdownButton(title, i) {
        return (
          <DropdownButton className="buttonFinished" bsStyle={title.toLowerCase()} title={title} key={i} id={`dropdown-basic-${i}`}>
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
