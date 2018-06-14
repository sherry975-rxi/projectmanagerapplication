import React, { Component } from 'react';
import './OngoingTasks.css';
import './AddTask';
import './MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from './MarkTaskAsFinished';
import { Link } from 'react-router-dom';
import MediumButton from './../../components/button/mediumButton';
import UserTasksFilter from '../tasks/UserTasksFilter';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import { connect } from 'react-redux';

class OngoingTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {},
            externalData: null
        };
        this.AuthService = new AuthService();
    }

    //TODO: Add sort by ascending or descending order to these tables

    componentDidMount() {
        this.refreshPage();
    }

    renderTasks() {
        switch (this.props.filter) {
            case 'all':
                return <AccordionMenu list={this.props.allTasks} />;
            case 'unfinished':
                return <AccordionMenu list={this.props.ongoingTasks} />;
            case 'finished':
                return <AccordionMenu list={this.props.finishedTasks} />;
            case 'notstarted':
                return <AccordionMenu list={this.props.notStartedTasks} />;
        }
    }

    refreshPage = () => {
        this.AuthService.fetch(
            `/users/${this.AuthService.getUserId()}/tasks/pending`,
            { method: 'get' }
        ).then(responseData => {
            this.setState({
                tasks: responseData,
                message: responseData.error
            });
        });
    };

    renderOngoingTasks() {
        return this.state.tasks.map((taskItem, index) => {
            return (
                <tr className="line" key={index}>
                    <td>{taskItem.taskID}</td>
                    <td>{taskItem.project}</td>
                    <td>{taskItem.description}</td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {taskItem.startDate}
                        </Moment>
                    </td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {taskItem.taskDeadline}
                        </Moment>
                    </td>
                    <td>
                        <MarkTaskAsFinished
                            id={taskItem.taskID}
                            project={taskItem.project}
                            onClick={this.refreshPage}
                        />
                    </td>
                    <td>
                        <Link
                            to={
                                '/projects/' +
                                taskItem.project +
                                '/tasks/' +
                                taskItem.taskID +
                                '/createreport'
                            }
                        >
                            <MediumButton text="Create Report" />
                        </Link>
                    </td>
                    <td>
                        <Link
                            to={
                                '/projects/' +
                                taskItem.project +
                                '/tasks/' +
                                taskItem.taskID +
                                '/reports'
                            }
                        >
                            <MediumButton text="View Reports" />
                        </Link>
                    </td>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />;
        } else {
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
}

const mapStateToProps = state => {
    return {
        filter: state.filterReducer.filterType,
        finishedTasks: state.projectTasks.finishedTasks,
        ongoingTasks: state.projectTasks.ongoingTasks,
        notStartedTasks: state.projectTasks.notStartedTasks,
        allTasks: state.projectTasks.allTasks
    };
};

export default connect(
    mapStateToProps,
    null
)(OngoingTasks);
