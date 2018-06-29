import React, { Component } from 'react';
import { Form, Modal, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap'
import '../../../components/button/genericButton.css'
import 'react-dates/initialize';
import { DateRangePicker, SingleDatePicker, isInclusivelyAfterDay, isInclusivelyBeforeDay } from 'react-dates';
import 'react-dates/lib/css/_datepicker.css';
import './EditTask.css'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import '../../../components/loading/LoadingComponent.css'
import { toastr } from 'react-redux-toastr';
import AuthService from '../../loginPage/AuthService';
import { formatDate } from '../../../components/utils/handleList'
import { getProjectTasksByFilter } from '../../../actions/projectTasksActions'
import moment from 'moment'



class EditTask extends Component {

    constructor(props, context) {
        super(props, context)
        this.state = {
            show: false,
            focusedInput: '',
            confirmCreation: false,
            description: '',
            taskID: '',
            estimatedStartDate: '',
            deadline: '',
            estimatedBudget: '',
            estimatedEffort: '',
            startDate: ''

        };
        this.AuthService = new AuthService();
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleClose() {
        this.setState({ show: false, confirmCreation: false });
    }

    handleDescriptionChange = (event) => {
        let input = event.target.value;
        let taskDescription = input.replace(/[^a-z0-9 ]/gi, '');
        this.setState({ description: taskDescription })
    }

    handleEffortChange = (event) => {
        event.preventDefault();
        let input = event.target.value;
        let newEffort = input.replace(/[^?\d+(\d{1,2})?$]/gi, '');
        this.setState({ estimatedEffort: newEffort })
    }

    handleBudgetChange = (event) => {
        event.preventDefault();
        let input = event.target.value;
        let newBudget = input.replace(/[^?\d+(\d{1,2})?$]/gi, '');
        this.setState({ estimatedBudget: newBudget })
    }

    isOutsideRange = (day) => {
        console.log(this.props.task.currentProject.startdate)

        if (this.props.task.currentProject.startdate === undefined || this.props.task.currentProject.finishdate === undefined)
            return false;

        else {
            return !isInclusivelyAfterDay(day, moment(this.props.task.currentProject.startdate)) || !isInclusivelyBeforeDay(day, moment(this.props.task.currentProject.finishdate))
        }

    }

    handleConfirmation = () => {

        let taskToEdit = {
            taskID: this.props.task.taskID,
            description: this.state.description,
            estimatedTaskStartDate: this.state.estimatedStartDate,
            taskDeadline: this.state.deadline,
            estimatedTaskEffort: this.state.estimatedEffort,
            taskBudget: this.state.estimatedBudget,
            startDate: this.state.startDate
        }

        this.AuthService.fetchRaw(`/projects/2/tasks/editTask`, {
            method: 'PATCH',
            body: JSON.stringify(taskToEdit)
        })
            .then(data => {
                if (data.status === 200)
                    toastr.success('Task:' + this.props.task.taskID + ' successfully edited');
                else
                    toastr.error('Something went wrong! Task not updated')
                this.props.getProjectTasksByFilter(this.props.task.project, this.props.filter)
                return data;
            }).catch((error) => {
                toastr.error('Something went wrong! Task not updated')
            });

        this.setState({
            show: false,
            confirmCreation: false,
            description: '',
            taskID: '',
            estimatedStartDate: '',
            deadline: '',
            estimatedBudget: '',
            estimatedEffort: '',
            startDate: ''
        })
    }


    displayConfirmation() {
        let edited = <Glyphicon className="edited" glyph="glyphicon glyphicon-pencil" />

        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title><Glyphicon className="pencilGreen" glyph="glyphicon glyphicon-edit" /> Edit Task </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.description ? <p><b>Description:</b> {this.state.description}{edited}</p> : <p><b>Description:</b>{this.props.task.description}</p>}

                    {this.state.estimatedStartDate ? <p><b>Estimated Start Date:</b> {this.state.estimatedStartDate.format('DD/MM/YYYY').toString()}{edited}</p> : <p><b>Estimated Start Date:</b>{this.props.task.estimatedTaskStartDate}</p>}

                    {this.state.deadline ? <p><b>Deadline:</b> {this.state.deadline.format('DD/MM/YYYY').toString()}{edited}</p> : <p><b>Deadline:</b>{this.props.task.taskDeadline}</p>}

                    {this.state.estimatedBudget ? <p><b>Estimated task Budget:</b> {this.state.estimatedBudget}{edited}</p> : <p><b>Estimated task Budget:</b>{this.props.task.taskBudget}</p>}

                    {this.state.estimatedEffort ? <p><b>Estimated task Effort:</b> {this.state.estimatedEffort}{edited}</p> : <p><b>Estimated task Effort:</b>{this.props.task.estimatedTaskEffort}</p>}

                    {this.state.startDate ? <p><b>Start Date:</b> {this.state.startDate.format('DD/MM/YYYY').toString()}{edited}</p> : <p><b>Deadline:</b>{this.props.task.startDate}</p>}

                </Modal.Body>
                <Modal.Footer>
                    <span><button className="cancelButton" onClick={() => this.setState({ confirmCreation: false })}>Cancel</button></span><span><button className="genericButton" onClick={this.handleConfirmation.bind(this)}>Confirm</button> </span>
                </Modal.Footer>
            </div>)
    }

    renderTaskEditFields() {
        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title><Glyphicon className="pencilGreen" glyph="glyphicon glyphicon-edit" /> Edit Task </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form >
                        <FormGroup bsSize="large">
                            <ControlLabel>Task Description : {this.props.task.description}</ControlLabel>
                            <FormControl id="descriptionForm"
                                autoFocus
                                type="text"
                                value={this.state.description}
                                onChange={this.handleDescriptionChange}
                                placeholder="Enter new description"
                            />
                        </FormGroup>
                        <FormGroup bsSize="large">
                            <ControlLabel>Estimated Dates: {formatDate(this.props.task.estimatedTaskStartDate)} | {formatDate(this.props.task.taskDeadline)}</ControlLabel>
                            <div className="calendar">
                                <DateRangePicker
                                    isOutsideRange={this.isOutsideRange.bind(this)}
                                    startDatePlaceholderText='Estimated Start'
                                    endDatePlaceholderText='Deadline'
                                    endDateId='endDate1'
                                    startDateId='startDate1'
                                    startDate={this.state.estimatedStartDate ? this.state.estimatedStartDate : null}
                                    endDate={this.state.deadline ? this.state.deadline : null}
                                    onDatesChange={({ startDate, endDate }) => this.setState({ estimatedStartDate: startDate, deadline: endDate })}
                                    focusedInput={this.state.focusedInput ? this.state.focusedInput : null}
                                    onFocusChange={focusedInput => this.setState({ focusedInput })}
                                    showDefaultInputIcon
                                    inputIconPosition="after"
                                />
                            </div>
                        </FormGroup>
                        <div className="budgetAndEffort">
                            <FormGroup bsSize="large">
                                <ControlLabel>Estimated Budget: {this.props.task.taskBudget}</ControlLabel>
                                <FormControl id="taskBudget"
                                    autoFocus
                                    type="text"
                                    pattern="[0-9]*"
                                    value={this.state.estimatedBudget}
                                    onChange={this.handleBudgetChange}
                                    placeholder="Enter new budget"
                                />
                            </FormGroup>
                            <FormGroup bsSize="large">
                                <ControlLabel>Estimated Effort: {this.props.task.estimatedTaskEffort}</ControlLabel>
                                <FormControl id="taskEffort"
                                    autoFocus
                                    type="text"
                                    pattern="[0-9]*"
                                    value={this.state.estimatedEffort}
                                    onChange={this.handleEffortChange}
                                    placeholder="Enter new effort"
                                />
                            </FormGroup>
                        </div>

                        <FormGroup bsSize="large">
                            <ControlLabel>Start Date: {formatDate(this.props.task.startDate)}</ControlLabel>
                            <div className="calendar">
                                <SingleDatePicker
                                    isOutsideRange={this.isOutsideRange.bind(this)}
                                    date={this.state.startDate ? this.state.startDate : null}
                                    openDirection="up"
                                    daySize={30}
                                    verticalSpacing={2}
                                    onDateChange={date => {
                                        this.setState({ startDate: date });
                                    }}
                                    focused={this.state.focused}
                                    numberOfMonths={1}
                                    placeholder="Date"
                                    onFocusChange={({ focused }) => {
                                        this.setState({ focused });
                                    }}
                                />
                            </div>
                        </FormGroup>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <button className="genericButton" onClick={() => this.setState({ confirmCreation: true })}>Edit Task</button>
                </Modal.Footer>
            </div>
        )
    }

    render() {

        let toRender = this.renderTaskEditFields()
        if (this.state.confirmCreation) {
            toRender = this.displayConfirmation()
        }

        return (
            <div>
                <span onClick={this.handleShow.bind(this)}> <Glyphicon className="pencil" glyph="glyphicon glyphicon-edit" /></ span>

                <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
                    {toRender}
                </Modal>
            </div>
        )

    }
}

const mapStateToProps = state => {
    return {
        filter: state.filterReducer.filterType
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            getProjectTasksByFilter
        },
        dispatch
    );

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(EditTask);
