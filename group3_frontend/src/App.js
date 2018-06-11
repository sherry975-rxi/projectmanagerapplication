import React, { Component } from 'react';
import './App.css';
import NavBar from './components/navBar/NavBar';
import Profile from './components/navBar/Profile.js';
import SideBar from './components/sideBar/SideBar';
import { Route, Switch } from 'react-router-dom';
import ActiveProjects from './pages/projects/ActiveProjects';
import MyProjects from './pages/projects/MyProjects';
import FinishedTasks from './pages/tasks/FinishedTasks';
import UsersPage from './pages/users/UsersPage';
import LoginPage from './pages/loginPage/LoginPage';
import SignUpPage from './pages/signUp/SignUpPage';
import firstPage from './pages/firstPage/firstPage';
import Footer from './components/footer/footer';
import ProjectCostCalculation from './pages/projectCost/ProjectCostCalculation';
import ProjectCost from './pages/projectCost/ProjectCost';
import CreateReport from './pages/reports/CreateReport';
import Reports from './pages/reports/Reports';
import AllRequests from './pages/requests/AllRequests';
import AddTask from './pages/tasks/AddTask';
import ProjectDetails from './pages/projects/ProjectDetails';
import OngoingTasks from './pages/tasks/OngoingTasks';
import requiresAuth from './components/authentication/requiresAuth';
import Messages from './components/msgs/Messages';
import HomePage from '../src/components/homePage/Homepage';
import Test from './pages/tasks/Test';

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
                    <Route
                        path="/activeprojects"
                        component={requiresAuth(ActiveProjects)}
                    />
                    <Route
                        path="/myprojects"
                        component={requiresAuth(MyProjects)}
                    />
                    <Route
                        path="/tasks"
                        component={requiresAuth(OngoingTasks)}
                    />
                    <Route
                        path="/finishedtasks"
                        component={requiresAuth(FinishedTasks)}
                    />
                    <Route
                        path="/homepage"
                        component={requiresAuth(HomePage)}
                    />

                    <Route path="/users" component={requiresAuth(UsersPage)} />
                    <Route
                        path="/selectprojectcostcalculation/:projectID"
                        component={requiresAuth(ProjectCostCalculation)}
                    />
                    <Route path="/test" component={Test} />

                    <Route
                        path="/projectcost/:projectID"
                        component={requiresAuth(ProjectCost)}
                    />

                    <Route
                        path="/projects/:projectID/tasks/:taskID/reports"
                        component={Reports}
                    />
                    <Route
                        path="/projects/:projectID/tasks/:taskID/createreport"
                        component={CreateReport}
                    />

                    <Route
                        path="/profile/:userID"
                        component={requiresAuth(Profile)}
                    />
                    <Route
                        path="/requests"
                        component={requiresAuth(AllRequests)}
                    />
                    <Route path="/addtask" component={requiresAuth(AddTask)} />
                    <Route
                        path="/projectdetails/:projectID"
                        component={requiresAuth(ProjectDetails)}
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
