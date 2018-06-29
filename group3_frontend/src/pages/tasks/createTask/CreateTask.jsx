import React, { Component } from 'react';
import { Form, Modal, FormGroup, ControlLabel, FormControl } from 'react-bootstrap'
import '../../../components/button/genericButton.css'
import './CreateTask.css'
import 'react-dates/initialize';
import { DateRangePicker, isInclusivelyAfterDay, isInclusivelyBeforeDay } from 'react-dates';
import 'react-dates/lib/css/_datepicker.css';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { createTask, deleteTaskCreated } from '../../../actions/createTaskActions'
import {
    updateAllTasks,
    updateNotStartedTasks, updateUnfinishedTasks
} from '../../../actions/projectTasksActions';
import '../../../components/loading/LoadingComponent.css'
import { toastr } from 'react-redux-toastr';
import AuthService from '../../loginPage/AuthService';
import moment from 'moment'


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
        this.AuthService = new AuthService();
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

    isOutsideRange = (day) => {

        if (this.props.project.projectStartDate === undefined || this.props.project.projectFinishDate === undefined)
            return false;

        else {
            return !isInclusivelyAfterDay(day, moment(this.props.project.projectStartDate)) || !isInclusivelyBeforeDay(day, moment(this.props.project.projectFinishDate))
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

        this.props.deleteTaskCreated(this.props.projectId, this.props.task.taskID)

    }

    confirmCreation() {
        this.setState({ show: false, confirmCreation: false });

        switch (this.props.filter) {
            case 'notstarted':
                this.props.updateNotStartedTasks(this.props.projectId)
                break
            case 'unfinished':
                this.props.updateUnfinishedTasks(this.props.projectId)
                break
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
                        isOutsideRange={this.isOutsideRange.bind(this)}
                        startDatePlaceholderText='Estimated Start'
                        endDatePlaceholderText='Deadline'
                        endDateId='endDate1'
                        startDateId='startDate1'
                        startDate={this.state.expectedStart ? this.state.expectedStart : null}
                        endDate={this.state.deadline ? this.state.expectedStart : null}
                        onDatesChange={({ startDate, endDate }) => this.setState({ expectedStart: startDate, deadline: endDate })}
                        focusedInput={this.state.focusedInput ? this.state.focusedInput : null}
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

        console.log(this.props.project)

        let isProjectManager = 'noButton'

        if (this.props.project !== undefined) {

            isProjectManager = this.props.project.projectManagerEmail === this.AuthService.getProfile().sub ? 'genericButton' : 'noButton'
        }

        var toRender = this.renderTaskCreationProcess()

        if (this.state.confirmCreation) {
            toRender = this.displayConfirmation()
        }

        return (

            <div>
                <button className={isProjectManager} onClick={this.handleShow.bind(this)}>
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
        beingCreated: state.createTask.beingCreated,
        project: state.projects.project
    };
};
const mapDispatchToProps = dispatch => bindActionCreators({ createTask, deleteTaskCreated, updateAllTasks, updateNotStartedTasks, updateUnfinishedTasks }, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(CreateTask);
