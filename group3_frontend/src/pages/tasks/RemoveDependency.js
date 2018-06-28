import React, { Component } from 'react';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { removeTaskDependency } from "../../actions/projectTasksActions";
import { DropdownButton, MenuItem } from "react-bootstrap";
import { toastr } from "react-redux-toastr";
import './TaskDependencies.css'


class RemoveDependency extends Component {
    constructor(props) {
        super(props);

    }


    handleSelect = eventKey => {

        this.props.removeTaskDependency(this.props.projectID, this.props.taskID, this.props.taskList[eventKey].taskID);

        if(this.props.error) {
            toastr.success('Dependency removed!');
        } else {
            toastr.error('lolnope');
        }

    };

    

    render() {

        const taskList = this.props.dependencies || [];

        return (
            <DropdownButton
                className="genericButton"
                title='Remove Dependency'
                key="0"
                id={`dropdown-basic-0`}
                value={this.props.projectID}
            >
                {taskList.map((listItem, index) => (
                    <MenuItem
                        eventKey={index}
                        key={index}
                        onSelect={this.handleSelect}
                    >
                        {listItem.taskID}
                    </MenuItem>
                ))}
            </DropdownButton>
        );
    }
}

const mapStateToProps = state => {
    return {
        error: state.tasks.error,
        loading: state.tasks.loading
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ removeTaskDependency }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(RemoveDependency);