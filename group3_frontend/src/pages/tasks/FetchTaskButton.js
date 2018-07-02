import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css';
import './dist/FetchTask.css';
import {
    changeTaskFilter,
    getProjectTasksByFilter
} from './../../actions/projectTasksActions';
import { TASKS_FILTER } from '../../constants/TasksConstants';
import { get } from 'lodash';

class FetchTaskButton extends Component {
    constructor(props) {
        super(props);
        this.props.getProjectTasksByFilter(
            this.props.projectId,
            TASKS_FILTER.ALL_TASKS
        );
    }

    handleChange = event => {
        const taskFilter = event.target.id;
        this.props.changeTaskFilter(taskFilter);
        this.props.getProjectTasksByFilter(this.props.projectId, taskFilter);
    };

    render() {
        return (




            <div className="buttonWrapper">

                <div className="switch-toggle switch-candy">
                    <input id={TASKS_FILTER.ALL_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        name="tasksFilter"
                        htmlFor={TASKS_FILTER.ALL_TASKS}
                    >
                        All Tasks
                    </label>

                    <input id={TASKS_FILTER.UNFINISHED_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.UNFINISHED_TASKS}
                    >
                        Unfinished
                    </label>

                    <input
                        id={TASKS_FILTER.FINISHED_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.FINISHED_TASKS}
                    >
                        Finished
                </label>

                    <input
                        id={TASKS_FILTER.NOTSTARTED_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.NOTSTARTED_TASKS}
                    >
                        Not Started</label>

                    <input
                        id={TASKS_FILTER.STANDBY_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.STANDBY_TASKS}
                    >
                        Stand By
            </label>

                    <input
                        id={TASKS_FILTER.EXPIRED_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.EXPIRED_TASKS}
                    >
                        Expired
            </label>


                    <input
                        id={TASKS_FILTER.CANCELLED_TASKS} name="view3" type="radio" onChange={this.handleChange} />
                    <label
                        className="buttonFont"
                        htmlFor={TASKS_FILTER.CANCELLED_TASKS}
                    >
                        Cancelled
            </label>

                    <a></a>
                </div>



            </div>


        );
    }
}

const mapStateToProps = state => {
    return {
        taskFilter: get(state, 'tasks.taskFilter', [])
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            changeTaskFilter,
            getProjectTasksByFilter
        },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(FetchTaskButton);
