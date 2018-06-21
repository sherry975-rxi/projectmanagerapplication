import React, { Component } from 'react';
import './MarkTaskAsFinished.css';
import AuthService from '../loginPage/AuthService';
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {updateAllTasks, updateUnfinishedTasks, updateNotStartedTasks} from "../../actions/projectTasksActions";

class DeleteTask extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleClick = () => {
        fetch(
            `/projects/${this.props.project}/tasks/${this.props.id}`,
            {
                headers: { Authorization: localStorage.getItem('id_token') },
                method: 'DELETE'
            }
            ).then(response => {
                if(response.status === 202) {
                    switch (this.props.filter) {
                        case 'unfinished':
                            this.props.updateUnfinishedTasks(this.props.project)
                            break;
                        case 'notstarted':
                            this.props.updateNotStartedTasks(this.props.project)
                            break;
                        default:
                            this.props.updateAllTasks(this.props.project)
                            break;
                    }
                }
            }

        )
    }

    

    render() {
        return (
            <div className=" table-striped">
                <button className="buttonFinished" onClick={this.handleClick}>
                    Delete
                </button>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        filter: state.filterReducer.filterType,
    }
}

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ updateAllTasks, updateUnfinishedTasks, updateNotStartedTasks }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(DeleteTask);
