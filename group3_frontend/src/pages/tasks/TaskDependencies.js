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
import './TaskDependencies.css'
import { formatDate } from '../../components/utils/handleList'

class TaskDependencies extends Component {
    constructor(props) {
        super(props);
        this.authService = new AuthService();

    }

    // after mounting the component, an action is dispatched to fetch all dependencies of the chosen task
    // as well as confirmation of the logged in user's permissions in the project
    componentDidMount() {
        this.props.reloadTask(this.props.match.params.projectID, this.props.match.params.taskID);
    }

    // this method fetches the selected task and compares its project manager against the logged in user
    isProjectManager() {

        if (this.props.childTask != null)
            return this.props.childTask.project.projectManager.email === this.authService.getProfile().sub;
        else
            return false;
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
        if (this.props.childTask != null) {
            return (

                <table className="taskDependencies">
                    <thead>
                        <tr>
                            <th>
                                <b>
                                    Task ID
                                </b>
                            </th>
                            <th>
                                <b>
                                    Description
                                </b>
                            </th>
                            <th>
                                <b>
                                    State
                                </b>
                            </th>
                            <th>
                                <b>
                                    Estimated Start Date
                                </b>
                            </th>
                            <th>
                                <b>
                                    Deadline
                                </b>
                            </th>
                            <th>
                                <b>
                                    Start Date
                                </b>
                            </th>

                        </tr>
                        <tr>
                            <th>
                                {this.props.childTask.taskID}

                            </th>
                            <th>
                                {this.props.childTask.description}

                            </th>
                            <th>
                                {this.props.childTask.currentState}

                            </th>
                            <th>
                                {formatDate(this.props.childTask.estimatedTaskStartDate)}


                            </th>
                            <th>
                                {formatDate(this.props.childTask.taskDeadline)}

                            </th>
                            <th>
                                {formatDate(this.props.childTask.finishDate)}

                            </th>

                        </tr>

                    </thead>
                </table >

            );
        }
    }

    render() {

        return (
            <div>
                <h3 id="projectTasksTitle">
                    <b>Create Dependency</b>
                </h3>
                <p id="taskToCreateDependency">
                    <b>Task info</b>
                </p>
                {this.childTaskDetails()}
                {this.getDependencyButtons()}
                <p id="taskToCreateDependency">
                    <b>Dependencies</b>
                </p>
                {this.renderDependencies()}
            </div>
        );

    }

}
const mapStateToProps = state => {
    return {
        childTask: state.dependencies.childTask,
        childTaskLoading: state.dependencies.itemIsLoading,
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