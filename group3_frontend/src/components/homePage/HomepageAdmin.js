import React, { Component } from 'react';
import './Homepage.css';
import Weather from './Weather';
import UserData from './UserData';
import TaskGraph from './TaskGraph.js';
import ProjGraph from './ProjectGraph';
import Calendar from './Calendar'
import AuthService from '../../pages/loginPage/AuthService'
import { connect } from 'react-redux';
import { toastr } from 'react-redux-toastr';


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
         
        console.log("render")
        console.log(this.state.numberOfGuests)

        console.log(this.state.numberOfGuests.length)

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
                    <td className="HomeTableTDBottomLeft">
                        <div className="ProjUpperContainer">
                        </div>
                    </td>
                    <td className="HomeTableTDBottomLeft">
                        <div className="TaskGraphUpperContainer">
                            <h1 className="GraphTitle">Warnings</h1>
                            <p>Warning, there is {this.state.numberOfGuests} users to be verified. Please, check their status</p>
                            
                        
                                

                            <p>{this.state.numberOfCollaborators}</p>
                            <p>{this.state.numberOfDirectors}</p>

                        </div>
                    </td>
                </tr>
                </tbody>
        </table>
        )


    }


}


export default HomepageAdmin;