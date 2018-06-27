import React, { Component } from 'react';
import {connect} from "react-redux";
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { Redirect } from 'react-router-dom';
import {bindActionCreators} from "redux";
import {getAllTaskDependencies} from "../../actions/projectTasksActions";

class TaskDependencies extends Component {
    constructor(props) {
        super(props);

    }

    componentDidMount() {
        this.updateDependencies();
    }

    updateDependencies() {
        console.log(this.props.match.params.projectID);
        console.log(this.props.match.params.taskID);
        this.props.getAllTaskDependencies(this.props.match.params.projectID, this.props.match.params.taskID);
    }


    renderTasks = () => {
        if (this.props.tasksLoading) {
            return <LoadingComponent />;
        } else if (this.props.error != null) {
            return <Redirect to="/login" />;
        }

        if(this.props.tasks == null) {
            return <AccordionMenu list={[]} />;
        } else {
            return <AccordionMenu list={this.props.tasks} />;
        }



    };

    render() {
        return (
            <div>
                <div align="right">
                    {/*Insert Create dependency button here!*/}
                    {/*<CreateTask projectId={this.props.match.params.projectID} />*/}
                </div>
                {console.log(this.props.tasks)}
                {this.renderTasks()}
            </div>
        );
    }

}
const mapStateToProps = state => {
    return {
        tasks: state.tasks.tasksList,
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

