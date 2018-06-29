import React, { Component } from 'react';
import { Button, Glyphicon } from 'react-bootstrap';
import './AllRequests.css';
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';

class AllRequests extends Component {
    constructor(props) {
        super(props);
        this.state = {
            requests: [],
            tasks: [],
            message: ''
        };
        this.authService = new AuthService();
        //this.getAllTasks = this.getAllTasks.bind(this);
    }

    /* async getAllTasks() {
        console.log("arranque do fetch");
        console.log(this.props.match.params.projectID);
        this.authService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/all`, 
            {
            method: 'GET' }
        ).then(responseData => {
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                })
        });
            
            console.log(this.state.tasks);
            console.log("fim do fetch");
        
                
        } */
    

   async componentDidMount() {

    /* console.log("arranque do fetch");
    console.log(this.props.match.params.projectID);
    this.authService.fetch(
        `/projects/${this.props.match.params.projectID}/tasks/all`, 
        {
        method: 'GET' }
    ).then(responseData => {
     
            this.setState({
                tasks: responseData,
                message: responseData.error
            })
            console.log(this.state.tasks)
    
        console.log("segundo")

        console.log(this.state.tasks)

        var allRequests = [];
      
        this.state.tasks.map((taskItem ) => {
            console.log("mapeamento")
        this.authService.fetch(`/projects/${this.props.match.params.projectID}/tasks/${taskItem.taskID}/requests`, {
            method: 'get'
        }).then(responseData => {
            console.log("resquests")
            console.log(responseData)
            console.log("fim dos request de cada uma")
            // this.setState({
            //     requests: responseData,
            //     message: responseData.error
            // });
            if(responseData.length >0){
            allRequests.push(responseData);
            console.log("show me the way")
            console.log(allRequests)}
        });
    });
    this.setState({
        requests: allRequests,
    });
}); */
    

    this.authService.fetch(`/projects/${this.props.match.params.projectID}/tasks/allRequests`, {
        method: 'get'
    }).then(responseData => {
        console.log(responseData)
        this.setState({
            requests: responseData,
            message: responseData.error
        });
    });
    
}

    formatDate(date) {
        var moment = require('moment');
        if (date != null) {
            ;
            return moment(date).format("DD/MMM/YYYY");
        } else {
            return '';
        }
    }

    renderRequests() {
        return this.state.requests.map(requestsItem => {
            return (
                <tr className="line">
                    <td>{requestsItem.task.description}</td>
                    <td>{requestsItem.projCollab.collaborator.name}</td>
                    <td>{requestsItem.type}</td>
                    <td>{this.formatDate(requestsItem.approvalDate)}</td>
                    <td>{this.formatDate(requestsItem.rejectDate)}</td>

                    <Button>
                        <Glyphicon className="ok-button" glyph="ok" />
                    </Button>
                    <Button>
                        <Glyphicon className="remove-button" glyph="remove" />
                    </Button>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message !== '') {
            return (
                <div className="Requests">
                    <h3>
                        <b>List of All Requests</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Task</th>
                                <th>Collaborator Name</th>
                                <th>Request Type</th>
                                <th>Approval Date</th>
                                <th>Rejection Date</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderRequests()}</tbody>
                    </table>
                </div>
            );
        } else {
            return <Error message={this.state.message + ' NOT AUTHORIZED!'} />;
        }
    }
}

export default AllRequests;
