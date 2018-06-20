import React, { Component } from 'react';
import './AddTask';
import './MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import { Redirect } from 'react-router-dom';
import UserTasksFilter from '../tasks/UserTasksFilter';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import { connect } from 'react-redux';
import LoadingComponent from './../../components/loading/LoadingComponent';


class UserTasks extends Component {
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
            case 'myAll':
                return <AccordionMenu list={this.props.allTasks} />;
            case 'myUnfinished':
                return <AccordionMenu list={this.props.ongoingTasks} />;
            case 'myFinished':
                return <AccordionMenu list={this.props.finishedTasks} />;
            default:
                return <LoadingComponent />;
        }
    }


    render() {
      
            return (
                <div className=" table-striped">
                    <UserTasksFilter userID={this.AuthService.getUserId()} />
                    <h3>
                        <b>My Tasks</b>
                    </h3>
                    &nbsp;
                    {this.renderTasks()}
                </div>
            );
        }
    }


const mapStateToProps = state => {
    return {
        filter: state.userTasksFilter.filterType,
        finishedTasks: state.userTasks.finishedTasks,
        ongoingTasks: state.userTasks.ongoingTasks,
        allTasks: state.userTasks.allTasks
    };
};

export default connect(
    mapStateToProps,
    null
)(UserTasks);
