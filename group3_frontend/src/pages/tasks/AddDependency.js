import React, { Component } from 'react';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import { createTaskDependency, updateNotStartedTasks } from "../../actions/projectTasksActions";
import {ControlLabel, DropdownButton, FormControl, FormGroup, MenuItem} from "react-bootstrap";
import { toastr } from "react-redux-toastr";
import './TaskDependencies.css'


class AddDependency extends Component {
    constructor(props) {
        super(props);

        this.state = {
            open: false,
            daysToPostpone: 0
        }

    }

    validateForm() {
        return this.state.daysToPostpone > 0;
    }

    handleClick = () => {
        if(!this.state.open) {
            console.log(this.props.taskList);
            this.props.updateNotStartedTasks(this.props.projectID);
            this.setState({
                open: true
            })
        } else {
            this.setState({
                open: false
            })
        }

    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };




    handleSelect = eventKey => {

        console.log(this.props.taskList[eventKey].taskID);

        this.props.createTaskDependency(this.props.projectID, this.props.taskID, this.props.taskList[eventKey].taskID, this.state.daysToPostpone);

        if(this.props.error) {
            toastr.success('Dependency added!');
            this.setState({
                open: false
            })
        } else {
            toastr.error('lolnope');
        }

    };

    render() {

        const taskList = this.props.taskList || [];

        return (
            <DropdownButton
                className="dependencyButton"
                title='Create Dependency'
                key="0"
                id={`dropdown-basic-0`}
                value={this.props.projectID}
                onClick={this.handleClick}
                open={this.state.open}
            >
                <MenuItem
                    eventKey="w0t"
                >
                    <FormGroup controlId="daysToPostpone" bsSize="small">
                        <ControlLabel>Days To Postpone</ControlLabel>
                        <FormControl
                            autoFocus
                            type="number"
                            pattern="[0-9]*"
                            inputMode="numeric"
                            value={this.state.daysToPostpone}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                </MenuItem>
                {taskList.map((listItem, index) => (
                    <MenuItem
                        disabled={!this.validateForm()}
                        eventKey={index}
                        key={index}
                        onSelect={this.handleSelect}
                    >
                        {listItem.taskID} - {listItem.description}
                    </MenuItem>
                ))}
            </DropdownButton>
        );
    }
}

const mapStateToProps = state => {
    return {
        taskList: state.tasks.tasksList,
        error: state.tasks.error,
        loading: state.tasks.loading
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ updateNotStartedTasks, createTaskDependency }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AddDependency);