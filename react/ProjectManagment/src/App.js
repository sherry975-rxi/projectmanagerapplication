import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
        
    constructor(props) {
      super(props);
   
      this.state = {
        greeting: "This is a greeting" 
      }
    } 
 
    componentDidMount() {
        fetch('greet/active').then(function(response) {
               return response.text();
            }).then((text) => {
                  this.setState({greeting: text})
                });
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
        <p>{this.state.greeting}</p>
      </div>
    );
  }
}

export default App; 