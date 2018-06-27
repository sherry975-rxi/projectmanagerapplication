import React, { Component } from 'react';
import {connect} from "react-redux";
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import {bindActionCreators} from "redux";
import {getAllTaskDependencies} from "../../actions/projectTasksActions";
import Error from "../../components/error/error";
import AddDependency from "./AddDependency";
import AuthService from "../loginPage/AuthService";
import RemoveDependency from "./RemoveDependency";

class TaskDependencies extends Component {
    constructor(props) {
        super(props);

        this.state = {
            projectManager: false
        }


        this.authService = new AuthService();
    }

    componentDidMount() {
        this.props.getAllTaskDependencies(this.props.match.params.projectID, this.props.match.params.taskID);

        this.isProjectManager();

    }


    isProjectManager() {
        this.authService.fetch(`/projects/${this.props.match.params.projectID}`,
            { method: 'GET' }
        ).then(response => {
            this.setState({
                projectManager: response.projectManager.email === this.authService.getProfile().sub
            });

        }).catch(error => {
            console.log(error);
            this.setState({
                projectManager: false
            });

        });
    }

    getManagerOptions() {
        if(this.state.projectManager) {
            return (
            <div align="right">
                <AddDependency projectID={this.props.match.params.projectID} taskID={this.props.match.params.taskID} />
                <RemoveDependency projectID={this.props.match.params.projectID} taskID={this.props.match.params.taskID} dependencies={this.props.tasks} />
            </div>);
        } else {
            return <div align="right"></div>;
        }
    }

    renderTasks = () => {
        if (this.props.tasksLoading) {
            return <LoadingComponent />;
        } else if (this.props.error) {
            return <Error message={this.props.error} />;
        } else if (this.props.tasks != null) {
            return <AccordionMenu list={this.props.tasks} />;
        }

    };

    render() {

        console.log(this.state.projectManager);

        return (
            <div>
                {this.getManagerOptions()}

                {this.renderTasks()}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        tasks: state.tasks.tasksDependencies,
        tasksLoading: state.tasks.itemIsLoading,
        error: state.tasks.error
    };
};
const mapDispatchToProps = dispatch =>
    bindActionCreators(
        { getAllTaskDependencies }, dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(TaskDependencies);

