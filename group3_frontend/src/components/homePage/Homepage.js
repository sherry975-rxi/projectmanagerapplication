import React, { Component } from 'react';
import './Homepage.css';
import AuthService from '../../pages/loginPage/AuthService'
import { connect } from 'react-redux';
import HomepageAdmin from './HomepageAdmin';
import HomepageCollaborator from './HomepageCollaborator'
import HomepageDirector from './HomepageDirector'



class Homepage extends Component {

    constructor(props) {
        super(props);
        this.state = {
            profile: '',
            numberOfGuests: ''

        };
        this.AuthService = new AuthService();

    }


    whichProfileToRender() {
        let options = '';

        if (this.props.profile === 'COLLABORATOR') {
            options = this.renderAsCollaborator();
        } else if (this.props.profile === 'DIRECTOR') {
            options = this.renderAsDirector();

        }
        else if (this.props.profile === 'ADMIN') {
            options = this.renderAsAdmin();

        }


        return (
            <div>
                {options}
            </div>
        )

    }




    renderAsCollaborator() {
        return <HomepageCollaborator />
    }



    renderAsAdmin() {
        return <HomepageAdmin />
    }

    renderAsDirector() {
        return <HomepageDirector />
    }




    render() {
        return (
            this.whichProfileToRender()
        )


    }
}


const mapStateToProps = state => {
    return { profile: state.authenthication.user.userProfile };
};
export default connect(mapStateToProps)(Homepage);

