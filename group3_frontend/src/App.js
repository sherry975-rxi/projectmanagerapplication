import React, { Component } from "react";
import "./App.css";
import NavBar from "./components/navBar/NavBar";
import Profile from "./components/navBar/Profile.js";
import SideBar from "./components/sideBar/SideBar";
import { Grid, Jumbotron, Button } from "react-bootstrap";
import { Route, Switch } from "react-router-dom";
import ProjectsPage from "./pages/projects/ProjectsPage";
import ActiveProjects from "./pages/projects/ActiveProjects";
import TasksPage from "./pages/tasks/TasksPage";
import FinishedTasks from "./pages/tasks/FinishedTasks";
import MarkTaskAsFinished from "./pages/tasks/MarkTaskAsFinished";
import UsersPage from "./pages/users/UsersPage";
import LoginPage from "./pages/loginPage/LoginPage";

import firstPage from "./pages/firstPage/firstPage";
import Footer from "./components/footer/footer";
import ProjectCostCalculation from "./pages/Cost/ProjectCostCalculation";
import ProjectCost from "./pages/Cost/ProjectCost";
import CreateReport from "./pages/reports/CreateReport";
import UpdateReport from "./pages/reports/UpdateReport";
import signUpPage from "./pages/signUpPage/SignUpPage";

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
                    <Route path="/projects" component={ProjectsPage} />
                    <Route path="/activeprojects" component={ActiveProjects} />
                    <Route path="/tasks" component={TasksPage} />
                    <Route path="/finishedtasks" component={FinishedTasks} />
                    <Route path="/marktaskfinished" component={MarkTaskAsFinished} />
                    <Route path="/users" component={UsersPage} />
                    <Route path="/selectprojectcostcalculation" component={ProjectCostCalculation}/>
                    <Route path="/projectcost" component={ProjectCost} />
                    <Route path="/createreport" component={CreateReport} />
                    <Route path="/updatereport" component={UpdateReport} />
                    <Route path="/profile" component={Profile} />
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
                    <Route exact path="/" component={firstPage} />
                        <Route exact path="/login" component={LoginPage} />
                        <Route exact path="/signup" component={signUpPage} />
                        <Route exact path="/users/{this.userID}" component={Profile} />
                        <Route component={this.pages} />
                    </Switch>
                </div>
                <Footer />
            </div>
        );
    }
}

export default App;
