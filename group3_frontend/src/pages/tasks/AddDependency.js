import React, { Component } from 'react';
import { connect } from "react-redux";
import { bindActionCreators } from "redux";
import {createTaskDependency, getPossibleTaskDependencies } from "../../actions/dependencyActions";
import {ControlLabel, DropdownButton, FormControl, FormGroup, MenuItem} from "react-bootstrap";
import './TaskDependencies.css'


class AddDependency extends Component {
    constructor(props) {
        super(props);

        this.state = {
            open: false,
            daysToPostpone: 0
        }

    }

    // when creating a dependency, the child task's estimated start date should be delayed a given number of
    // days after the parent's expected deadline
    validateForm() {
        return this.state.daysToPostpone > 0;
    }

    // when opening the Add Dependency dropdown button, it dispatches an action to fetch all possible task dependencies for that task
    handleClick = () => {
        if(!this.state.open) {
            this.props.getPossibleTaskDependencies(this.props.projectID, this.props.taskID);
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




    // when selecting a task with a positive "postpone" value, an action is dispatched that creates the new dependency
    // when successful, redux state the updated list of dependencies and refreshes the display
    handleSelect = eventKey => {

        this.props.createTaskDependency(this.props.projectID, this.props.taskID, this.props.taskList[eventKey].taskID, this.state.daysToPostpone);

        this.setState({
            open: false
        })


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
        childTask: state.dependencies.childTask,
        taskList: state.dependencies.possibleDependencies,
        error: state.dependencies.error,
        loading: state.dependencies.itemIsLoading
    };
};

const mapDispatchToProps = dispatch => {
    return bindActionCreators({ createTaskDependency, getPossibleTaskDependencies }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(AddDependency);