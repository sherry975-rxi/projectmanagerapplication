import React, { Component } from 'react';
import logo from './logo.svg';
import './ActiveProjects.css';



class MarkTaskAsFinished extends Component {

    constructor(props) {
        super(props);
        this.state = {
            task: []
        }
    }


    componentDidMount() {
        this.loadStudentsFromServer();
    }

    // Load students from database
    loadStudentsFromServer() {
        fetch('projects/2/tasks/WP1.T01',{
            method: 'patch',
        })
            .then((response) => response.json())
            .then((batatas) => {
                this.setState({
                    task: batatas,
                });
            });
    }

    renderTask(){
        return this.state.task.map((taskItem) =>{
            return(
                <div>
                <p>{taskItem.description}</p>
                <p>{taskItem.currentState}</p>
                    <p>{taskItem.creationDate}</p>
                    <p>{taskItem.startDate}</p>
                    <p>{taskItem.finishDate}</p>
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
                <h3>Task that was marked as finished</h3>
                {this.renderTask()}
                
                size: {this.state.task.length}
                
            </div>
        );
    }
}

export default MarkTaskAsFinished;
