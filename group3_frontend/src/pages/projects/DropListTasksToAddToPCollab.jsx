import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
    updateAllTasks,
    addCollaboratorToTask
} from '../../actions/projectTasksActions';
import { bindActionCreators } from 'redux';
import { MenuItem, DropdownButton } from 'react-bootstrap';
import { get } from 'lodash';

class DropListTasksToAddToPCollab extends Component {
    constructor(props) {
        super(props);

        this.handleClick = this.handleClick.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
    }

    handleClick(event) {
        this.props.updateAllTasks(event.target.value);
    }

    handleSelect = eventKey => {
        const userDTO = {
            email: this.props.email
        };
        this.props.addCollaboratorToTask(
            this.props.projectID,
            this.props.taskList[eventKey].taskID,
            userDTO,
            this.props.tasksFilter
        );
    };

    renderDropdownButton(title, i) {
        const taskList = this.props.taskList || [];
        return (
            <DropdownButton
                className="buttonFinished"
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
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

    render() {
        return <td>{this.renderDropdownButton('add to Task', 0)}</td>;
    }
}

const mapStateToProps = state => {
    return {
        unassignedCollabs: state.collabsWoutTasks.collabs,
        taskList: get(state, 'tasks.tasksList', []),
        tasksFilter: get(state, 'tasks.taskFilter')
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators(
        { updateAllTasks, addCollaboratorToTask },
        dispatch
    );
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(DropListTasksToAddToPCollab);
