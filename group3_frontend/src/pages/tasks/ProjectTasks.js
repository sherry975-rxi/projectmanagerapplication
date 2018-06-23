import React, { Component } from 'react';
import FetchTaskButton from '../tasks/FetchTaskButton';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { Redirect } from 'react-router-dom';
import CreateTask from './createTask/CreateTask';
import { get } from 'lodash';

class ProjectTasks extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
        this.renderTasks = this.renderTasks.bind(this);
    }

    //TODO: Add sort by ascending or descending order to these tables

    renderTasks() {
        if (this.props.tasksLoading) {
            return <LoadingComponent />;
        } else if (this.props.error) {
            return <Redirect to="/login" />;
        }

        switch (this.props.filter) {
            case 'all':
                return <AccordionMenu list={this.props.allTasks} />;
            case 'unfinished':
                return <AccordionMenu list={this.props.unfinishedTasks} />;
            case 'finished':
                return <AccordionMenu list={this.props.finishedTasks} />;
            case 'withoutCollaborators':
                return <AccordionMenu list={this.props.standByTasks} />;
            case 'notstarted':
                return <AccordionMenu list={this.props.notStartedTasks} />;
            case 'expired':
                return <AccordionMenu list={this.props.expiredTasks} />;
            case 'cancelled':
                return <AccordionMenu list={this.props.cancelledTasks} />;
            default: {
                return <LoadingComponent />;
            }
        }
    }

    render() {
        return (
            <div>
                <div align="right">
                    <CreateTask projectId={this.props.match.params.projectID} />
                </div>
                <FetchTaskButton
                    projectID={this.props.match.params.projectID}
                />
                {this.renderTasks()}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        filter: state.filterReducer.filterType,
        finishedTasks: get(state, 'tasks.tasksList', []),
        unfinishedTasks: get(state, 'tasks.tasksList', []),
        standByTasks: get(state, 'tasks.tasksList', []),
        notStartedTasks: get(state, 'tasks.tasksList', []),
        expiredTasks: get(state, 'tasks.tasksList', []),
        allTasks: get(state, 'tasks.tasksList', []),
        cancelledTasks: get(state, 'tasks.tasksList', []),
        tasksLoading: get(state, 'tasks.itemIsLoading', []),
        error: get(state, 'tasks.error', [])
    };
};

export default connect(
    mapStateToProps,
    null
)(ProjectTasks);
