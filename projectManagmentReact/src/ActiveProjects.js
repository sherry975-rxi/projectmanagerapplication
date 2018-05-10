import React, { Component } from 'react';
import logo from './logo.svg';
import './ActiveProjects.css';
import axios from 'axios';

class ActiveProjects extends Component {

    constructor(props) {
        super(props);
        this.state = {
            projects: []
        }
    }

    async componentDidMount() {

        const request = await axios.get('projects/active');
        const teste = request.data;
        this.setState({ projects : teste });
                   
    }

    renderProjects(){
        return this.state.projects.map((projectItem) =>{
            return(
                <div>
                <p>{projectItem.projectId}</p>
                <p>{projectItem.projectManager.email}</p>
                <p>{projectItem.projectManager.name}</p>
                <p>{projectItem.name}</p>
                <p>{projectItem.description}</p>
                <hr/>
                </div>
            )
        })
    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h2>Welcome to React</h2>
                </div>
                <p className="App-intro">
                    To get started, edit <code>src/App.js</code> and save to reload!
                </p>
                <h3>List of Active Projects</h3>
                {this.renderProjects()}              
                
                size: {this.state.projects.length}
                
            </div>
        );
    }
}

export default ActiveProjects;
