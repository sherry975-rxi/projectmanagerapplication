import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css';
import './dist/FetchTask.css';
import {
    updateMyAllTasks,
    updateMyFinishedTasks,
    updateMyOngoingTasks
} from './../../actions/userTasksActions';

class UserTasksFilter extends Component {
    constructor(props) {
        super(props);

        this.state = {
            activeKey: '1'
        };
    }

    handleChange(key) {
        switch (key) {
            case '1':
                return this.props.updateMyAllTasks(this.props.userID);
            case '2':
                return this.props.updateMyOngoingTasks(this.props.userID);
            case '3':
                return this.props.updateMyFinishedTasks(this.props.userID);
            default:
                return;
        }
    }

    async componentDidMount() {
        this.props.updateMyAllTasks(this.props.userID);
    }

    render() {
        return (
            <div className="buttonWrapper">
                <div className="switch-toggle switch-ios">
                    <input
                        id="alltasks"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('1')}
                    />
                    <label className="buttonFont" htmlFor="alltasks">
                        All Tasks
                    </label>

                    <input
                        id="onGoing"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('2')}
                    />
                    <label className="buttonFont" htmlFor="onGoing">
                        On Going
                    </label>

                    <input
                        id="finished"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('3')}
                    />
                    <label className="buttonFont" htmlFor="finished">
                        Finished
                    </label>
                </div>
            </div>
        );
    }
}

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        { updateMyAllTasks, updateMyFinishedTasks, updateMyOngoingTasks },
        dispatch
    );
export default connect(
    null,
    mapDispatchToProps
)(UserTasksFilter);
