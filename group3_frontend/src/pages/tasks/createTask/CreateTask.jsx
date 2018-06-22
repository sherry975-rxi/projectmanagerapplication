import React, { Component } from 'react';
import { Form, Modal, Popover, Tooltip, Button, FormGroup, ControlLabel, FormControl } from 'react-bootstrap'
import '../../../components/button/genericButton.css'
import './CreateTask.css'
import 'react-dates/initialize';
import { DateRangePicker, SingleDatePicker, DayPickerRangeController } from 'react-dates';
import 'react-dates/lib/css/_datepicker.css';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { createTask } from '../../../actions/createTaskActions'

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
            focusedInput: ''
        };
    }

    handleClose() {
        this.setState({ show: false });
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleChange = (event) => {
        let input = event.target.value;
        let taskDescription = input.replace(/[^a-z0-9]/gi, '');
        this.setState({ description: taskDescription })
    }

    createWithDetails() {
        this.setState({ withDetails: !this.state.withDetails })
    }

    createTask() {

        let taskDetails = {
            description: this.state.description
        };

        console.log(taskDetails)
        this.props.createTask(taskDetails, this.props.projectId)
    }

    renderDetails() {
        return (

            <FormGroup bsSize="large">
                <ControlLabel>Expected Dates</ControlLabel>
                <div className="calendar">
                    <DateRangePicker
                        endDateId='endDate1'
                        startDateId='startDate1'
                        startDate={this.state.estimatedStart}
                        showClearDate
                        endDate={this.state.deadline}
                        onDatesChange={({ startDate, endDate }) => this.setState({ estimatedStart: startDate, deadline: endDate })}
                        placeholder="dasdad"
                        focusedInput={this.state.focusedInput}
                        onFocusChange={focusedInput => this.setState({ focusedInput })}
                        showDefaultInputIcon
                        inputIconPosition="after"
                    />
                </div>
                {this.renderTaskDependencies()}
            </FormGroup>

        )
    }

    renderTaskDependencies() {
        return (
            <FormGroup bsSize="large">
                <ControlLabel>Create Task Dependency</ControlLabel>
                <FormControl componentClass="select" placeholder="Select">
                    <option value="other">...</option>
                </FormControl>
            </FormGroup>
        )
    }


    render() {

        console.log(this.state.expectedStart)

        let details = !this.state.withDetails ? '+details' : 'less'

        return (
            <div>
                <button className="genericButton" onClick={this.handleShow.bind(this)}>
                    Create Task
            </button>

                <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
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
                        <button className="genericButton" onClick={this.createTask.bind(this)}>Create</button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ createTask }, dispatch);

export default connect(null, mapDispatchToProps)(CreateTask);
