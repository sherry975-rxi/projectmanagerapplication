import React, { Component } from 'react';
import { Form, Modal, Popover, Tooltip, Button, FormGroup, ControlLabel, FormControl } from 'react-bootstrap'
import '../../../components/button/genericButton.css'
import './CreateTask.css'
import 'react-dates/initialize';
import { DateRangePicker, SingleDatePicker, DayPickerRangeController } from 'react-dates';
import 'react-dates/lib/css/_datepicker.css';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { createTask, deleteTaskCreated } from '../../../actions/createTaskActions'
import {
    updateAllTasks,
    updateNotStartedTasks, updateUnfinishedTasks
} from '../../../actions/projectTasksActions';
import LoadingComponent from './../../../components/loading/LoadingComponent';
import '../../../components/loading/LoadingComponent.css'
import { toastr } from 'react-redux-toastr';


class CreateTask extends Component {

    constructor(props, context) {
        super(props, context)
        this.state = {
            show: false,
            description: '',
            expectedStart: '',
            deadline: '',
            dependency: '',
            withDetails: false,
            focusedInput: '',
            confirmCreation: false
        };
    }

    handleClose() {
        this.setState({ show: false, confirmCreation: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleChange = (event) => {
        let input = event.target.value;
        let taskDescription = input.replace(/[^a-z0-9 ]/gi, '');
        this.setState({ description: taskDescription })
    }

    createWithDetails() {
        this.setState({ withDetails: !this.state.withDetails })
        if (this.state.withDetails === false) {
            this.setState({ expectedStart: '', deadline: '' })
        }
    }


    displayConfirmation() {

        if (this.props.beingCreated)
            return (
                <div>
                    <Modal.Header >
                        <Modal.Title>Confirm Task Creation</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="loadingTaskWrapper">
                            <center><div class="loadingTask"></div> </center></div>
                    </Modal.Body>
                    <Modal.Footer>
                    </Modal.Footer>
                </div>
            )

        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Task Creation</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p><b>Description:</b> {this.props.task.description} </p>
                    <p><b>Task ID:</b> {this.props.task.taskID} </p>
                    <p><b>Estimated Start Date:</b> {this.props.task.estimatedTaskStartDate} </p>
                    <p><b>Estimated Deadline:</b> {this.props.task.taskDeadline} </p>
                    <p><b>State:</b> {this.props.task.currentState} </p>
                    <p><b>Project:</b> {this.props.projectId} </p>
                </Modal.Body>
                <Modal.Footer>
                    <span><button className="cancelButton" onClick={this.cancelCreation.bind(this)}>Cancel</button></span><span><button className="genericButton" onClick={this.confirmCreation.bind(this)}>Confirm</button> </span>
                </Modal.Footer>
            </div>
        )
    }

    cancelCreation = () => {
        this.setState({ confirmCreation: false })
        console.log(this.props.projectId)
        this.props.deleteTaskCreated(this.props.projectId, this.props.task.taskID)

    }

    confirmCreation() {
        this.setState({ show: false, confirmCreation: false });

        switch (this.props.filter) {
            case 'notstarted':
                this.props.updateNotStartedTasks(this.props.projectId)
            case 'unfinished':
                this.props.updateUnfinishedTasks(this.props.projectId)
            default:
                this.props.updateAllTasks(this.props.projectId)
        }
        toastr.success('Task successfully created');
    }


    createTaskAndDisplayConfirmation() {

        this.setState({ confirmCreation: !this.state.confirmCreation })

        let taskDetails = ''
        if (this.state.withDetails)
            taskDetails = {
                description: this.state.description,
                estimatedTaskStartDate: this.state.expectedStart,
                taskDeadline: this.state.deadline
            };
        else {
            taskDetails = {
                description: this.state.description
            };
        }

        this.props.createTask(taskDetails, this.props.projectId)
    }

    renderDetails() {
        return (
            <FormGroup bsSize="large">
                <ControlLabel>Estimated Dates</ControlLabel>
                <div className="calendar">
                    <DateRangePicker
                        startDatePlaceholderText='Estimated Start'
                        endDatePlaceholderText='Deadline'
                        endDateId='endDate1'
                        startDateId='startDate1'
                        showClearDate
                        startDate={this.state.expectedStart}
                        endDate={this.state.deadline}
                        onDatesChange={({ startDate, endDate }) => this.setState({ expectedStart: startDate, deadline: endDate })}
                        placeholder="dasdad"
                        focusedInput={this.state.focusedInput}
                        onFocusChange={focusedInput => this.setState({ focusedInput })}
                        showDefaultInputIcon
                        inputIconPosition="after"
                    />
                </div>
            </FormGroup>

        )
    }

    renderTaskCreationProcess() {

        let details = !this.state.withDetails ? '+details' : 'less'

        return (
            <div>

                <Modal.Header closeButton>
                    <Modal.Title>Create Task</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form >
                        <FormGroup bsSize="large">
                            <ControlLabel>Task Description</ControlLabel>
                            <FormControl id="descriptionForm"
                                autoFocus
                                type="text"
                                value={this.state.description}
                                onChange={this.handleChange}
                                placeholder="Enter description"
                            />
                        </FormGroup>
                    </Form>
                    {this.state.withDetails ? this.renderDetails() : null}
                </Modal.Body>
                <Modal.Footer>
                    <div className="moreDetails" align="right"><p onClick={this.createWithDetails.bind(this)}>{details}</p></div>
                    <button className="genericButton" onClick={this.createTaskAndDisplayConfirmation.bind(this)}>Create</button>
                </Modal.Footer>
            </div>
        )
    }

    render() {

        var toRender = this.renderTaskCreationProcess()

        if (this.state.confirmCreation) {
            toRender = this.displayConfirmation()
        }

        return (

            <div>
                <button className="genericButton" onClick={this.handleShow.bind(this)}>
                    Create Task
        </button>

                <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
                    {toRender}
                </Modal>
            </div>

        )
    }

}
const mapStateToProps = state => {
    return {
        taskCreated: state.createTask.taskCreated,
        filter: state.filterReducer.filterType,
        task: state.createTask.task,
        beingCreated: state.createTask.beingCreated
    };
};
const mapDispatchToProps = dispatch => bindActionCreators({ createTask, deleteTaskCreated, updateAllTasks, updateNotStartedTasks, updateUnfinishedTasks }, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(CreateTask);
