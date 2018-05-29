import React, { Component } from "react";
import "./App.css";
import NavBar from "./components/navBar/NavBar";
import Profile from "./components/navBar/Profile.js";
import SideBar from "./components/sideBar/SideBar";
import { Route, Switch, Redirect, Router } from "react-router-dom";
import ActiveProjects from "./pages/projects/ActiveProjects";
import FinishedTasks from "./pages/tasks/FinishedTasks";
import MarkTaskAsFinished from "./pages/tasks/MarkTaskAsFinished";
import UsersPage from "./pages/users/UsersPage";
import LoginPage from "./pages/loginPage/LoginPage";
import SignUpPage from "./pages/SignUpPage/SignUpPage";
import firstPage from "./pages/firstPage/firstPage";
import Footer from "./components/footer/footer";
import ProjectCostCalculation from "./pages/Cost/ProjectCostCalculation";
import ProjectCost from "./pages/Cost/ProjectCost";
import CreateReport from "./pages/reports/CreateReport";
import Reports from "./pages/reports/Reports";
import AllRequests from "./pages/requests/AllRequests";
import UpdateReport from "./pages/reports/UpdateReport";
import AddTask from "./pages/tasks/AddTask";
import ProjectDetails from "./pages/projects/ProjectDetails";
import OngoingTasks from "./pages/tasks/OngoingTasks";
import requiresAuth from './authentication/requiresAuth'
import Messages from './components/msgs/Messages'
import HomePage from "../src/components/homePage/Homepage";


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
                    <Route path="/activeprojects" component={requiresAuth(ActiveProjects)} />
                    <Route path="/tasks/:userID" component={requiresAuth(OngoingTasks)} />
                    <Route path="/finishedtasks" component={requiresAuth(FinishedTasks)} />
                    <Route path="/homepage" component={requiresAuth(HomePage)} />

                    <Route path="/users" component={requiresAuth(UsersPage)} />
                    <Route
                        path="/selectprojectcostcalculation/:projectID"
                        component={requiresAuth(ProjectCostCalculation)}
                    />

                    <Route
                        path="/projectcost/:projectID"
                        component={requiresAuth(ProjectCost)}
                    />
                   
                    <Route path="/projects/:projectID/tasks/:taskID/reports" component={Reports} />
                    <Route path="/projects/:projectID/tasks/:taskID/createreport" component={CreateReport} />

                    <Route path="/profile/:userID" component={requiresAuth(Profile)} />
                    <Route path="/requests" component={requiresAuth(AllRequests)} />
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
