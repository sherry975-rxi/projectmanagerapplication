import React, { Component } from 'react';
import './Homepage.css';
import Weather from './Weather';
import Calendar from './Calendar'
import AuthService from '../../pages/loginPage/AuthService'
import { connect } from 'react-redux';
import { toastr } from 'react-redux-toastr';
import Avatar from 'react-user-avatar';


class HomepageAdmin extends Component {

    constructor(props) {
        super(props);
        this.state = {
            profile: '',
            numberOfGuests: '',
            numberOfCollaborators: '',
            numberOfDirectors: '',
            hasShowedMessage: false
            
        };
        this.AuthService = new AuthService();

    }

    async componentDidMount(){
        this.AuthService.fetch(
            `/users/profiles/unassigned`,
            { method: 'get' }
        ).then(responseData => {
            this.setState({
                numberOfGuests: responseData.length,
                

                message: responseData.error
            });
        }
        ).then(this.sendToastr()
    )
        

        this.AuthService.fetch(
            `/users/profiles/director`,
            { method: 'get' }
        ).then(responseData => {
            console.log("Fetch")
            console.log(responseData)
            this.setState({
                numberOfDirectors: responseData.length,
                

                message: responseData.error
            });
        });

        this.AuthService.fetch(
            `/users/profiles/collaborator`,
            { method: 'get' }
        ).then(responseData => {
            console.log("Fetch")
            console.log(responseData)
            this.setState({
                numberOfCollaborators: responseData.length,
                

                message: responseData.error
            });
        });

        
    }

  sendToastr(){


        if(this.state.numberOfGuests > 0 && this.state.hasShowedMessage === false) {
            this.setState({
                hasShowedMessage: true

            })
            
            toastr.warning('There are ' + this.state.numberOfGuests + ' users to verify. Please, update their profile')
        }
    }

 

    render(){

        this.sendToastr();
         

        return(
            <table className="HomepageContainer">
            <tbody className="HomeTable">
                <tr>
                    <td>
                        <Calendar />
                    </td>
                    <td className="HomeTableTDLeft">
                        <Weather className="teste" />
                    </td>
                </tr>
                <tr>
                    <td className="HomeTableTDBottomLeftAdmin">
                        <div className="ProjUpperContainer">
                        <p>teste</p>
                        </div>
                    </td>
                    <td className="HomeTableTDBottomLeft">
                        <div className="UserNumberUpperContainer">
                            <h1 className="GraphTitle">Users in Application</h1>
                           
                            <table>
                                <tbody>
                                    <tr>
                                        <td className="avatarTdOne">  <Avatar size="88" name="Director" color="#CCC"/></td>
                                        <td className="avatarTdTwo"> <Avatar size="88" name="Collaborator" color="#66939e" /> </td>
                                        <td className="avatarTdThree">  <Avatar size="88" name="Unassigned" color="#c44c4c" /></td>
                                    </tr>
                                    <tr>
                                        <td className="avatarTdOne"> 
                                             <p className="numberUsers">{this.state.numberOfDirectors}</p>
                                        </td>
                                        <td className="avatarTdTwo">                                         
                                             <p className="numberUsers">{this.state.numberOfCollaborators}</p>
                                        </td>

                                        <td className="avatarTdThree">
                                            <p className="numberUsers"> {this.state.numberOfGuests}</p>
                                        </td>
                                    </tr>
                                </tbody>

                            </table>
                        


                        </div>
                    </td>
                </tr>
                </tbody>
        </table>
        )


    }


}


export default HomepageAdmin;