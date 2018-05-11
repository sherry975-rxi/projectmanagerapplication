import React, { Component } from 'react';
import './App.css';
import NavBar from './components/navBar/NavBar'
import SideBar from './components/sideBar/SideBar'
import { Grid, Jumbotron, Button } from 'react-bootstrap';
import { Route, Switch } from 'react-router-dom'
import ProjectsPage from './pages/projects/ProjectsPage';
import TasksPage from './pages/tasks/TasksPage';
import UsersPage from './pages/users/UsersPage';
import LoginPage from './pages/loginPage/LoginPage';
import SignUpPage from './pages/SignUpPage/SignUpPage';


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isVisible: true
    };
  }

  toogleMenu = () => {
    console.log("here")
    this.setState({
      isVisible: !this.state.isVisible
    });
  }

  pages = () => (
    <div className="row">
      <SideBar isVisible={this.state.isVisible} />
      <div className="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <Switch>
          <Route exact path="/" component={ProjectsPage} />
          <Route path="/projects" component={ProjectsPage} />
          <Route path="/tasks" component={TasksPage} />
          <Route path="/users" component={UsersPage} />
        </Switch>
      </div>
    </div>
  );




  render() {
    return (
      <div>
        <NavBar toogleMenu={this.toogleMenu} />
        <div className="container-fluid">
          <Switch>
            <Route exact path="/login" component={LoginPage} />
            <Route exact path="/signup" component={SignUpPage} />
            <Route component={this.pages} />
          </Switch>

        </div>
      </div>
    );
  }
}

export default App;