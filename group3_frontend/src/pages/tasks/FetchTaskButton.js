import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css';
import './dist/FetchTask.css';
import {
    updateAllTasks,
    updateFinishedTasks,
    updateNotStartedTasks,
    updateStandByTasks,
    updateOngoingTasks
} from './../../actions/projectTasksActions';

class FetchTaskButton extends Component {
    constructor(props) {
        super(props);

        this.state = {
            activeKey: '1'
        };
    }

    handleChange(event, key) {
        switch (key) {
            case '1':
                return this.props.updateAllTasks(this.props.projectID);
            case '2':
                return this.props.updateOngoingTasks(this.props.projectID);
            case '3':
                return this.props.updateFinishedTasks(this.props.projectID);
            case '4':
                return (
                    <div>
                        {this.props.updateNotStartedTasks(this.props.projectID)}
                    </div>
                );
            case '5':
                return (
                    <div>
                        {this.props.updateStandByTasks(this.props.projectID)}
                    </div>
                );
            default:
                return;
        }
    }

    async componentDidMount() {
        this.props.updateAllTasks(this.props.projectID);
    }

    render() {
        return (
            <div className="buttonWrapper">
                <div className="switch-toggle switch-ios">
                    <input
                        id="alltasks"
                        name="view3"
                        type="radio"
                        onChange={e => this.handleChange(e, '1')}
                    />
                    <label className="buttonFont" htmlFor="alltasks">
                        All Tasks
                    </label>

                    <input
                        id="onGoing"
                        name="view3"
                        type="radio"
                        onChange={e => this.handleChange(e, '2')}
                    />
                    <label className="buttonFont" htmlFor="onGoing">
                        On Going
                    </label>

                    <input
                        id="finished"
                        name="view3"
                        type="radio"
                        onChange={e => this.handleChange(e, '3')}
                    />
                    <label className="buttonFont" htmlFor="finished">
                        Finished
                    </label>

                    <input
                        id="notStarted"
                        name="view3"
                        type="radio"
                        onChange={e => this.handleChange(e, '4')}
                    />
                    <label className="buttonFont" htmlFor="notStarted">
                        Not Started
                    </label>

                    <input
                        id="standBy"
                        name="view3"
                        type="radio"
                        onChange={e => this.handleChange(e, '5')}
                    />
                    <label className="buttonFont" htmlFor="standBy">
                        Stand By
                    </label>
                </div>
            </div>
        );
    }
}

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            updateAllTasks,
            updateFinishedTasks,
            updateNotStartedTasks,
            updateStandByTasks,
            updateOngoingTasks
        },
        dispatch
    );
export default connect(
    null,
    mapDispatchToProps
)(FetchTaskButton);
