import React, { Component } from 'react';
import FetchTaskButton from '../tasks/FetchTaskButton';
import { connect } from 'react-redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import LoadingComponent from './../../components/loading/LoadingComponent';
import { Redirect } from 'react-router-dom';

class ProjectTasks extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
        this.renderTasks = this.renderTasks.bind(this);
    }

    //TODO: Add sort by ascending or descending order to these tables

    renderTasks() {

        if (this.props.tasksLoading) {
            return (<LoadingComponent />)
        }

        else if (this.props.error) {
            return <Redirect to="/login" />;
        }

        switch (this.props.filter) {
            case 'all':
                return <AccordionMenu list={this.props.allTasks} />;
            case 'unfinished':
                return <AccordionMenu list={this.props.ongoingTasks} />;
            case 'finished':
                return <AccordionMenu list={this.props.finishedTasks} />;
            case 'withoutCollaborators':
                return <AccordionMenu list={this.props.standByTasks} />;
            case 'notstarted':
                return <AccordionMenu list={this.props.notStartedTasks} />;
            case 'expired':
                return <AccordionMenu list={this.props.expiredTasks} />;
            default: {
                return <LoadingComponent />;
            }
        }
    }

    render() {

        return (
            <div>
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
        finishedTasks: state.projectTasks.finishedTasks,
        ongoingTasks: state.projectTasks.ongoingTasks,
        standByTasks: state.projectTasks.wihoutCollab,
        notStartedTasks: state.projectTasks.notStartedTasks,
        expiredTasks: state.projectTasks.expiredTasks,
        allTasks: state.projectTasks.allTasks,
        tasksLoading: state.projectTasks.itemIsLoading,
        error: state.projectTasks.error
    };
};

export default connect(
    mapStateToProps,
    null
)(ProjectTasks);
