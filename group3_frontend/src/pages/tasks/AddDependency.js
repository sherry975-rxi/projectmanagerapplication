import React, { Component } from 'react';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { createTaskDependency, updateNotStartedTasks } from "../../actions/projectTasksActions";
import { DropdownButton, MenuItem } from "react-bootstrap";
import { toastr } from "react-redux-toastr";


class AddDependency extends Component {
    constructor(props) {
        super(props);

    }

    handleClick = () => {
        console.log(this.props.taskList);
        this.props.updateNotStartedTasks(this.props.projectID);
    }

    handleSelect = eventKey => {

        console.log(this.props.taskList[eventKey].taskID);

        this.props.createTaskDependency(this.props.projectID, this.props.taskID, this.props.taskList[eventKey].taskID, 2);

        if(this.props.error) {
            toastr.success('Dependency added!');
        } else {
            toastr.error('lolnope');
        }

    };

    render() {

        const taskList = this.props.taskList || [];

        return (
            <DropdownButton
                className='genericButton'
                title='Create Dependency'
                key="0"
                id={`dropdown-basic-0`}
                value={this.props.projectID}
                onClick={this.handleClick}
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
        taskList: state.tasks.tasksList,
        error: state.tasks.error
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ updateNotStartedTasks, createTaskDependency }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AddDependency);