import React, { Component } from 'react';
import './MarkTaskAsFinished.css';
import AuthService from '../loginPage/AuthService';
import { updateFinishedTasks, updateAllTasks, updateStandByTasks, updateNotStartedTasks, updateUnfinishedTasks } from './../../actions/projectTasksActions';
import { updateMyAllTasks, updateMyFinishedTasks, updateMyOngoingTasks } from './../../actions/userTasksActions';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

class MarkTaskAsFinished extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleClick = () => {
        this.AuthService.fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}`,
            {
                method: 'PATCH'
            }
        ).then(responseData => {
            this.updateTasks();
        });

    };

    updateTasks() {

        if (this.props.filter === 'all')
            this.props.updateAllTasks(this.props.project)
        else if (this.props.filter === 'finished')
            this.props.updateFinishedTasks(this.props.project)
        else if (this.props.filter === 'unfinished')
            this.props.updateUnfinishedTasks(this.props.project)
        else if (this.props.filter === 'notStarted')
            this.props.updateNotStartedTasks(this.props.project)
        else if (this.props.filter === 'myAll')
            this.props.updateMyAllTasks(this.AuthService.getUserId())
        else if (this.props.filter === 'myFinished')
            this.props.updateMyFinishedTasks(this.AuthService.getUserId())
        else if (this.props.filter === 'myUnfinished')
            this.props.updateMyOngoingTasks(this.AuthService.getUserId())
        else
            this.props.updateStandByTasks(this.props.project)
    }

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick.bind(this)}>
                    Finish
                </button>
            </div>
        );
    }
}

const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType }) }
const mapDispatchToProps = dispatch => bindActionCreators({ 
    updateFinishedTasks, 
    updateAllTasks, 
    updateStandByTasks, 
    updateNotStartedTasks, 
    updateUnfinishedTasks, 
    updateMyAllTasks, 
    updateMyFinishedTasks, 
    updateMyOngoingTasks }, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(MarkTaskAsFinished);

