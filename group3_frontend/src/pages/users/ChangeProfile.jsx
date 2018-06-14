import React, { Component } from 'react';
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import AuthService from './../loginPage/AuthService';
import { toastr } from 'react-redux-toastr';
import './ChangeProfile.css'

class ChangeProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            submission: false,
            hideSuccessInfo: 'hide-code'
        };
        this.AuthService = new AuthService();
        this.handleClick = this.handleClick.bind(this);
    }

handleClick(event) {

    let profile;

    if (event == 1 ){
       profile = "COLLABORATOR"
    } if (event == 2) {
        profile = "DIRECTOR"
    } if (event == 3){
        profile = "ADMIN"
    }
    console.log(event);

    const email = this.props.email;

    const profileDTO = {
    
        email: email,
        userProfile: profile
    }
console.log(profileDTO)
        this.AuthService.fetch( 
            `/users/profiles`,
            { method: 'PATCH',
        body: JSON.stringify(profileDTO)}
        ).then(res => {
            console.log(res)
            console.log("seeee")
                 if (res.userProfile === profile) {
                        toastr.success('Profile was changed');
                       window.location.href = `/usersMngr`
                    }
                })
                .catch(err => {
                    console.log(err);
                    toastr.error('Profile was not changed');
                });
}

    renderDropdownButton(title, i) {
        return (
          <DropdownButton className="buttonFinished" bsStyle={title.toLowerCase()} title={title} key={i} id={`dropdown-basic-${i}`}>
              <MenuItem eventKey={1} onSelect={this.handleClick}> {'collaborator'}</MenuItem>
              <MenuItem eventKey={2} onSelect={this.handleClick}> {'director'}</MenuItem>
              <MenuItem eventKey={3} onSelect={this.handleClick}> {'administrator'}</MenuItem>
          </DropdownButton>
        );
      }
      

    render() {
            return (
                <div className=" table-striped">  
                        <tbody>{this.renderDropdownButton("Change",0)}</tbody>
                </div>
            );
        }
    }


export default ChangeProfile;
