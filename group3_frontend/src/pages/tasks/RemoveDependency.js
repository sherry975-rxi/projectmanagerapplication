import React, { Component } from 'react';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { removeTaskDependency } from "../../actions/dependencyActions";
import { DropdownButton, MenuItem } from "react-bootstrap";
import './TaskDependencies.css'


class RemoveDependency extends Component {

    // when selecting a dependency, an action is dispatched that removes it and stores the updated list of dependencies on the redux state
    handleSelect = eventKey => {

        this.props.removeTaskDependency(this.props.projectID, this.props.taskID, this.props.tasks[eventKey].taskID);

    };

    render() {

        const taskList = this.props.tasks || [];

        return (
            <DropdownButton
                className="dependencyGenericButton"
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
        tasks: state.dependencies.tasksDependencies,
        error: state.dependencies.error,
        loading: state.dependencies.itemIsLoading
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ removeTaskDependency }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(RemoveDependency);