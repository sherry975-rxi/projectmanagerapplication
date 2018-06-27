import React, { Component } from 'react';
import {connect} from "react-redux";
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { Redirect } from 'react-router-dom';
import {bindActionCreators} from "redux";
import {getAllTaskDependencies} from "../../actions/projectTasksActions";
import Error from "../../components/error/error";
import AddDependency from "./AddDependency";

class TaskDependencies extends Component {
    constructor(props) {
        super(props);

    }

    componentDidMount() {
        this.updateDependencies();
    }

    updateDependencies() {
        this.props.getAllTaskDependencies(this.props.match.params.projectID, this.props.match.params.taskID);
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
        return (
            <div>
                <div align="right">
                    <AddDependency projectID={this.props.match.params.projectID} taskID={this.props.match.params.taskID} />
                </div>

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

