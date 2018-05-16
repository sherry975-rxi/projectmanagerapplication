import React, { Component } from "react";
import "./App.css";
import NavBar from "./components/navBar/NavBar";
import Profile from "./components/navBar/Profile.js";
import SideBar from "./components/sideBar/SideBar";
import { Route, Switch } from "react-router-dom";
import ActiveProjects from "./pages/projects/ActiveProjects";
import FinishedTasks from "./pages/tasks/FinishedTasks";
import MarkTaskAsFinished from "./pages/tasks/MarkTaskAsFinished";
import UsersPage from "./pages/users/UsersPage";
import LoginPage from "./pages/loginPage/LoginPage";
import SignUpPage from "./pages/signUpPage/SignUpPage";
import firstPage from "./pages/firstPage/firstPage";
import Footer from "./components/footer/footer";
import ProjectCostCalculation from "./pages/Cost/ProjectCostCalculation";
import ProjectCost from "./pages/Cost/ProjectCost";
import CreateReport from "./pages/reports/CreateReport";
import signUpPage from "./pages/signUpPage/SignUpPage";
import AllRequests from "./pages/requests/AllRequests";
import UpdateReport from "./pages/reports/UpdateReport";
import AddTask from "./pages/tasks/AddTask";
import ProjectDetails from "./pages/projects/ProjectDetails";
import OngoingTasks from "./pages/tasks/OngoingTasks";

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
                    <Route path="/activeprojects" component={ActiveProjects} />
                    <Route path="/tasks/:userID" component={OngoingTasks} />
                    <Route path="/finishedtasks" component={FinishedTasks} />
                    <Route
                        path="/marktaskfinished/"
                        component={MarkTaskAsFinished}
                    />
                    <Route path="/users" component={UsersPage} />
                    <Route
                        path="/selectprojectcostcalculation/:projectID"
                        component={ProjectCostCalculation}
                    />
                    <Route
                        path="/projectcost/:projectID"
                        component={ProjectCost}
                    />
                    <Route path="/createreport" component={CreateReport} />
                    <Route path="/updatereport" component={UpdateReport} />
                    <Route path="/profile" component={Profile} />
                    <Route path="/requests" component={AllRequests} />
                    <Route path="/addtask" component={AddTask} />
                    <Route
                        path="/projectdetails/:projectID"
                        component={ProjectDetails}
                    />
                </Switch>
            </div>
        </div>
    );

    render() {
        return (
            <div className="body">
                <NavBar toogleMenu={this.toogleMenu} />
                <div className="container-fluid">
                    <Switch>
                        <Route exact path="/" component={firstPage} />
                        <Route exact path="/login" component={LoginPage} />
                        <Route exact path="/signup" component={SignUpPage} />
                        <Route
                            exact
                            path="/users/{this.userID}"
                            component={Profile}
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
