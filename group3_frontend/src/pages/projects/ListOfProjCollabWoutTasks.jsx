import React, { Component } from 'react';
import AuthService from './../loginPage/AuthService';



class ListOfProjCollabWoutTasks extends Component {

        constructor(props) {
            super(props);
            console.log("1")
            this.state = {
                unassignedCollabs: []
            };
    
            this.AuthService = new AuthService();
        }
    
        componentDidMount() {
            this.getUnassignedProjCollabs();
        }
    
        async getUnassignedProjCollabs() {
            this.AuthService.fetch(
                `/projects/2/tasks/WP1.T01/collabsAvailableForTask`,
                { method: 'GET' }
            ).then(responseData => {
                console.log(responseData)
                this.setState({
                    unassignedCollabs: responseData,
                    message: responseData.error
                });
            });
        }
    
        ListOfCollabs() {
            console.log("4")
            if (this.state.unassignedCollabs.length > 0) {
                return this.state.unassignedCollabs.map((unassignedCollabsitem, index) => {
                    return (
                        <tr className="line" key={index}>
                            <td> {unassignedCollabsitem.collaborator.name}</td>
                            <td> {unassignedCollabsitem.collaborator.email}</td>
                            <td> {unassignedCollabsitem.costPerEffort}</td>
                        </tr>
                    );
                });
            } else {
                return <tr />;
            }
        }
    
        render() {
            return (
                <table className="table-striped">
                    <thead>
                        <tr>
                            <th>
                                <b> Unassigned Project Collabs </b>
                            </th>
                        </tr>
                    </thead>
                    <tbody>{this.ListOfCollabs()}</tbody>
                    
                </table>
            );
        }
    }

export default ListOfProjCollabWoutTasks;

