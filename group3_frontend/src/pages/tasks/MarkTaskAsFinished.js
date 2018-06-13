import React, { Component } from 'react';
import './MarkTaskAsFinished.css';
import AuthService from '../loginPage/AuthService';
import { updateFinishedTasks } from './../../actions/projectTasksActions';
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
            this.props.updateFinishedTasks(this.props.project, this.props.id)

        });

    };

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

const mapDispatchToProps = dispatch => bindActionCreators({ updateFinishedTasks }, dispatch)
export default connect(null, mapDispatchToProps)(MarkTaskAsFinished);
