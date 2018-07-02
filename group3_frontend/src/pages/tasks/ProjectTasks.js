import React, { Component } from 'react';
import FetchTaskButton from '../tasks/FetchTaskButton';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { Redirect } from 'react-router-dom';
import CreateTask from './createTask/CreateTask';
import { get } from 'lodash';
import ProjectTasksSearch from './ProjectTasksSearch'

class ProjectTasks extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    renderTasks = () => {
        if (this.props.tasksLoading) {
            return <LoadingComponent />;
        } else if (this.props.error) {
            return <Redirect to="/login" />;
        }
        return <AccordionMenu list={this.props.updatedTasks} />;
    };

    render() {
        return (
            <div>
                <div align="right">
                    <CreateTask projectId={this.props.match.params.projectID} />
                </div>
                <FetchTaskButton
                    projectId={this.props.match.params.projectID}
                />
                <h3>
                    <b>Project Tasks</b>
                </h3>
                <ProjectTasksSearch projectId={this.props.match.params.projectID} />
                &nbsp;
                {this.renderTasks()}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        taskFilter: get(state, 'tasks.taskFilter'),
        tasks: get(state, 'tasks.tasksList', []),
        tasksLoading: get(state, 'tasks.itemIsLoading', []),
        error: get(state, 'tasks.error', []),
        updatedTasks: get(state, 'tasks.updatedList', [])
    };
};

export default connect(mapStateToProps, null)(ProjectTasks);
