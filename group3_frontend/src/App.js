import React, { Component } from 'react';
import './App.css';
import NavBar from './components/navBar/NavBar';
import Profile from './components/navBar/Profile.js';
import SideBar from './components/sideBar/SideBar';
import { Route, Switch } from 'react-router-dom';
import requiresAuth from './components/authentication/requiresAuth';
import ProtectedRoute from './components/authentication/ProtectedRoute';

import ActiveProjects from './pages/projects/ActiveProjects';
import MyProjects from './pages/projects/MyProjects';
import LoginPage from './pages/loginPage/LoginPage';
import SignUpPage from './pages/signUp/SignUpPage';
import firstPage from './pages/firstPage/firstPage';
import Footer from './components/footer/footer';
import CreateReport from './pages/reports/CreateReport';
import Reports from './pages/reports/Reports';
import ProjectDetails from './pages/projects/ProjectDetails';
import UserTasks from './pages/tasks/UserTasks';
import Messages from './components/msgs/Messages';
import HomePage from '../src/components/homePage/Homepage';
import Test from './pages/tasks/Test';
import ProjectTasks from './pages/tasks/ProjectTasks';
import CreateProject from './pages/projects/CreateProject';
import AvailableListOfCollaborators from './pages/tasks/AvailableListOfCollaborators';
import DeleteTask from './pages/tasks/DeleteTask';
import AddUserToProject from './pages/projects/AddUserToProject';
import User from './pages/users/User';
import ChangeProfile from './pages/users/ChangeProfile';
import ListOfProjCollabWoutTasks from './pages/projects/ListOfProjCollabWoutTasks';
import TaskDependencies from './pages/tasks/TaskDependencies';
import Requests from './pages/requests/Requests';


class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isVisible: false
        };
    }

    toogleMenu = () => {
        this.setState({
            isVisible: !this.state.isVisible
        });
    };

    pages = () => (
        <div className="row">
            <SideBar isVisible={this.state.isVisible} />
            <div className="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
                <Switch>
                    <ProtectedRoute
                        path="/projects/:projectID/UnassignedProjCollab"
                        component={requiresAuth(ListOfProjCollabWoutTasks)}
                        permissions={['COLLABORATOR']}
                    />

                    <ProtectedRoute
                        path="/activeprojects"
                        component={requiresAuth(ActiveProjects)}
                        permissions={['DIRECTOR']}
                    />

                    <ProtectedRoute
                        path="/createproject"
                        component={requiresAuth(CreateProject)}
                        permissions={['DIRECTOR']}
                    />

                    <ProtectedRoute
                        path="/myprojects"
                        component={requiresAuth(MyProjects)}
                        permissions={['COLLABORATOR']}
                    />

                    <ProtectedRoute
                        path="/tasks"
                        component={requiresAuth(UserTasks)}
                        permissions={['COLLABORATOR']}
                    />

                    <Route
                        path="/homepage"
                        component={requiresAuth(HomePage)}
                    />

                    <ProtectedRoute
                        path="/projects/:projectID/tasks/:taskID/reports"
                        component={requiresAuth(Reports)}
                        permissions={['COLLABORATOR']}
                    />

                    <ProtectedRoute
                        path="/projects/:projectID/tasks/:taskID/dependencies"
                        component={requiresAuth(TaskDependencies)}
                        permissions={['COLLABORATOR']}
                    />

                    <ProtectedRoute
                        path="/projects/:projectID/tasks/:taskID/createreport"
                        component={requiresAuth(CreateReport)}
                        permissions={['COLLABORATOR']}
                    />

                    <ProtectedRoute
                        path="/projects/:projectID/tasks"
                        component={requiresAuth(ProjectTasks)}
                        permissions={['COLLABORATOR']}
                    />

                    <Route
                        path="/profile/:userID"
                        component={requiresAuth(Profile)}
                    />


                    <ProtectedRoute
                        path="/projectdetails/:projectID"
                        component={requiresAuth(ProjectDetails)}
                        permissions={['COLLABORATOR', 'DIRECTOR']}
                    />

                    <ProtectedRoute
                        path="/projects/:projectID/activeTeam"
                        component={requiresAuth(AvailableListOfCollaborators)}
                        permissions={['COLLABORATOR']}
                    />
                    <ProtectedRoute
                        path="/projects/:projectID/tasks/:taskID/delete"
                        component={requiresAuth(DeleteTask)}
                        permissions={['COLLABORATOR']}
                    />
                    <ProtectedRoute
                        exact path="/projects/:projectID/addColabToProject"
                        component={requiresAuth(AddUserToProject)}
                        permissions={['COLLABORATOR']}
                    />
                    <ProtectedRoute
                        exact path="/usersMngr"
                        component={requiresAuth(User)}
                        permissions={['ADMIN']}
                    />
                    <ProtectedRoute
                        exact path="/updateProfile"
                        component={requiresAuth(ChangeProfile)}
                        permissions={['ADMIN']}
                    />
                    <ProtectedRoute
                        exact path="/projects/:projectID/requests"
                        component={requiresAuth(Requests)}
                        permissions={['COLLABORATOR']}
                    />

                </Switch>
            </div>
        </div>
    );

    render() {
        return (
            <div className="body">
                <NavBar toogleMenu={this.toogleMenu} />
                <Messages />
                <div className="container-fluid">
                    <Switch>
                        <Route exact path="/" component={firstPage} />
                        <Route exact path="/login" component={LoginPage} />
                        <Route exact path="/signup" component={SignUpPage} />
                        <Route
                            exact
                            path="/users/{this.userID}"
                            component={requiresAuth(Profile)}
                        />
                        <Route component={this.pages} />
                    </Switch>
                </div>
                <Footer />
            </div>
        );
    }
}

export default App;
