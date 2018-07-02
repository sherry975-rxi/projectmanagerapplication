import React, { Component } from 'react';
import { connect } from "react-redux";
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { bindActionCreators } from "redux";
import { getAllTaskDependencies, reloadTask } from "../../actions/dependencyActions";
import Error from "../../components/error/error";
import AddDependency from "./AddDependency";
import AuthService from "../loginPage/AuthService";
import RemoveDependency from "./RemoveDependency";


class TaskDependencies extends Component {
    constructor(props) {
        super(props);

        this.props.reloadTask(this.props.match.params.projectID, this.props.match.params.taskID);

        this.authService = new AuthService();
    }

    // after mounting the component, an action is dispatched to fetch all dependencies of the chosen task
    // as well as confirmation of the logged in user's permissions in the project
    componentDidMount() {

        this.props.getAllTaskDependencies(this.props.match.params.projectID, this.props.match.params.taskID);

    }

    // this method fetches the selected task and compares its project manager against the logged in user
    isProjectManager() {

        return this.props.childTask.project.projectManager.email === this.authService.getProfile().sub;

    }

    // when the logged in user is the project manager, this method renders both buttons to add and remove dependency
    getDependencyButtons() {
        if (this.isProjectManager() && this.props.childTask.startDate == null) {
            return (
                <div align="right">
                    <AddDependency projectID={this.props.match.params.projectID} taskID={this.props.match.params.taskID} />
                    {' '}
                    <RemoveDependency projectID={this.props.match.params.projectID} taskID={this.props.match.params.taskID} />
                </div>);
        } else {
            return <div align="right"></div>;
        }
    }

    renderDependencies = () => {
        if (this.props.tasksLoading) {
            return <LoadingComponent />;
        } else if (this.props.error) {
            return <Error message={this.props.error} />;
        } else if (this.props.tasks != null) {
            return <AccordionMenu list={this.props.tasks} />;
        }

    };

    childTaskDetails = () => {
        console.log(this.props.childTask);
        if (this.props.childTask != null) {
            return (
                <table>
                    <thead>
                        <tr>
                            <th>

                                <p>
                                    <b>Task ID:</b> &nbsp;
                                {this.props.childTask.taskID}
                                </p>
                                <p>
                                    <b>Description:</b> &nbsp;
                                {this.props.childTask.description}
                                </p>
                                <p>
                                    <b>State:</b> &nbsp;
                                {this.props.childTask.currentState}
                                </p>

                                <p>
                                    <b>Estimated Start Date:</b> &nbsp;
                                {this.props.childTask.estimatedTaskStartDate}
                                </p>
                                <p>
                                    <b>Estimated Deadline:</b> &nbsp;
                                {this.props.childTask.taskDeadline}
                                </p>
                                <p>
                                    <b>Start Date:</b> &nbsp;
                                {this.props.childTask.startDate}
                                </p>
                                <p>
                                    <b>Finish Date:</b> &nbsp;
                                {this.props.childTask.finishDate}
                                </p>

                                <br />

                            </th>

                            {this.getDependencyButtons()}

                        </tr>
                    </thead>
                </table>
            );
        }
    }

    render() {

        return (
            <div>
                {this.childTaskDetails()}
                {this.renderDependencies()}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        childTask: state.dependencies.childTask,
        tasks: state.dependencies.tasksDependencies,
        tasksLoading: state.dependencies.itemIsLoading,
        error: state.dependencies.error
    };
};
const mapDispatchToProps = dispatch =>
    bindActionCreators(
        { getAllTaskDependencies, reloadTask }, dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TaskDependencies);

