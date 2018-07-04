import React, { Component } from 'react';
import { Form, Modal, FormGroup, ControlLabel, FormControl, Glyphicon } from 'react-bootstrap'
import '../../../components/button/genericButton.css'
import 'react-dates/initialize';
import { DateRangePicker, } from 'react-dates';
import 'react-dates/lib/css/_datepicker.css';
import './EditProject.css'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import '../../../components/loading/LoadingComponent.css'
import { toastr } from 'react-redux-toastr';
import AuthService from '../../loginPage/AuthService';
import { formatDate } from '../../../components/utils/handleList'
import { refreshProjects } from "../../../actions/projectActions";



class EditProject extends Component {

    constructor(props, context) {
        super(props, context)
        this.state = {
            show: false,
            focusedInput: '',
            confirmCreation: false,
            name: '',
            description: '',
            startDate: '',
            finishDate: '',
            estimatedBudget: ''

        };
        this.AuthService = new AuthService();
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleClose() {
        this.setState({ show: false, confirmCreation: false });
    }

    handleNameChange = (event) => {
        let input = event.target.value;
        let projName = input.replace(/[^a-z0-9 ]/gi, '');
        this.setState({ name: projName })
    }

    handleDescriptionChange = (event) => {
        let input = event.target.value;
        let projDescription = input.replace(/[^a-z0-9 ]/gi, '');
        this.setState({ description: projDescription })
    }

    handleBudgetChange = (event) => {
        event.preventDefault();
        let input = event.target.value;
        let newBudget = input.replace(/[^?\d+(\d{1,2})?$]/gi, '');
        this.setState({ estimatedBudget: newBudget })
    }


    handleConfirmation = () => {

        let projToEdit = {
            name: this.state.name,
            description: this.state.description,
            startdate: this.state.startDate,
            finishdate: this.state.finishDate,
            budget: this.state.estimatedBudget
        }

        this.AuthService.fetchRaw(`/projects/${this.props.project.projectId}`, {
            method: 'PATCH',
            body: JSON.stringify(projToEdit)
        })
            .then(data => {
                if (data.status === 200) {
                    toastr.success('Project:' + this.props.project.projectId + ' successfully edited');
                    this.props.refreshProjects(this.props.profile, this.AuthService.getUserId())
                } else {
                    toastr.error('Something went wrong! Project not updated')
                }
                return data;
            }).catch((error) => {
                toastr.error('Something went wrong! Project not updated')
            });

        this.setState({
            show: false,
            confirmCreation: false,
            name: '',
            description: '',
            startDate: '',
            finishDate: '',
            estimatedBudget: ''
        })
    }

    


    displayConfirmation() {
        let edited = <Glyphicon className="edited" glyph="glyphicon glyphicon-pencil" />

        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title><Glyphicon className="pencilGreen" glyph="glyphicon glyphicon-edit" /> Edit Project </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.name ? <p><b>Name:</b> {this.state.name}{edited}</p> : <p><b>Description:</b>{this.props.project.projectName}</p>}

                    {this.state.description ? <p><b>Description:</b> {this.state.description}{edited}</p> : <p><b>Description:</b>{this.props.project.projectDescription}</p>}

                    {this.state.startDate ? <p><b>Start Date:</b> {this.state.startDate.format('DD/MM/YYYY').toString()}{edited}</p> : <p><b>Start Date:</b>{this.props.project.projectStartDate}</p>}

                    {this.state.finishDate ? <p><b>Deadline:</b> {this.state.finishDate.format('DD/MM/YYYY').toString()}{edited}</p> : <p><b>Finish Date:</b>{this.props.project.projectFinishDate}</p>}

                    {this.state.estimatedBudget ? <p><b>Estimated Budget:</b> {this.state.estimatedBudget}{edited}</p> : <p><b>Estimated Budget:</b>{this.props.project.projectBudget}</p>}

                </Modal.Body>
                <Modal.Footer>
                    <span><button className="cancelButton" onClick={() => this.setState({ confirmCreation: false })}>Cancel</button></span><span><button className="genericButton" onClick={this.handleConfirmation.bind(this)}>Confirm</button> </span>
                </Modal.Footer>
            </div>)
    }

    renderProjEditFields() {

        if(this.props.project.projectStartDate.length > 0 && this.props.project.projectFinishDate.length > 0  ) {

        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title><Glyphicon className="pencilGreen" glyph="glyphicon glyphicon-edit" /> Edit Project </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form >
                        <FormGroup bsSize="large">
                            <ControlLabel>Project Name : {this.props.project.projectName}</ControlLabel>
                            <FormControl id="nameForm"
                                autoFocus
                                type="text"
                                value={this.state.name}
                                onChange={this.handleNameChange}
                                placeholder="Enter new name"
                            />
                        </FormGroup>
                        <FormGroup bsSize="large">
                            <ControlLabel>Project Description : {this.props.project.projectDescription}</ControlLabel>
                            <FormControl id="descriptionForm"
                                autoFocus
                                type="text"
                                value={this.state.description}
                                onChange={this.handleDescriptionChange}
                                placeholder="Enter new description"
                            />
                        </FormGroup>
                        <FormGroup bsSize="large">
                            <ControlLabel>Estimated Dates: Start Date: {formatDate(this.props.project.projectStartDate)} | Finish Date: {formatDate(this.props.project.projectFinishDate)}</ControlLabel>
                            <div className="calendar">
                                <DateRangePicker
                                    startDatePlaceholderText='Estimated Start'
                                    endDatePlaceholderText='Estimated Finish'
                                    endDateId='endDate1'
                                    startDateId='startDate1'
                                    startDate={this.state.startDate ? this.state.startDate : null}
                                    endDate={this.state.finishDate ? this.state.finishDate : null}
                                    onDatesChange={({ startDate, endDate }) => this.setState({ startDate: startDate, finishDate: endDate })}
                                    focusedInput={this.state.focusedInput ? this.state.focusedInput : null}
                                    onFocusChange={focusedInput => this.setState({ focusedInput })}
                                    showDefaultInputIcon
                                    inputIconPosition="after"
                                />
                            </div>
                        </FormGroup>
                        <div className="budgetAndEffort">
                            <FormGroup bsSize="large">
                                <ControlLabel>Estimated Budget: {this.props.project.projectBudget}</ControlLabel>
                                <FormControl id="projectBudget"
                                    autoFocus
                                    type="text"
                                    pattern="[0-9]*"
                                    value={this.state.estimatedBudget}
                                    onChange={this.handleBudgetChange}
                                    placeholder="Enter new budget"
                                />
                            </FormGroup>

                        </div>


                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <button className="genericButton" onClick={() => this.setState({ confirmCreation: true })}>Edit Project</button>
                </Modal.Footer>
            </div>
        )

    } else{

        return (
            <div>
                <Modal.Header closeButton>
                    <Modal.Title><Glyphicon className="pencilGreen" glyph="glyphicon glyphicon-edit" /> Edit Project </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form >
                        <FormGroup bsSize="large">
                            <ControlLabel>Project Name : {this.props.project.projectName}</ControlLabel>
                            <FormControl id="nameForm"
                                autoFocus
                                type="text"
                                value={this.state.name}
                                onChange={this.handleNameChange}
                                placeholder="Enter new name"
                            />
                        </FormGroup>
                        <FormGroup bsSize="large">
                            <ControlLabel>Project Description : {this.props.project.projectDescription}</ControlLabel>
                            <FormControl id="descriptionForm"
                                autoFocus
                                type="text"
                                value={this.state.description}
                                onChange={this.handleDescriptionChange}
                                placeholder="Enter new description"
                            />
                        </FormGroup>
                        <FormGroup bsSize="large">
                            <ControlLabel>Estimated Dates: </ControlLabel>
                            <div className="calendar">
                                <DateRangePicker
                                    startDatePlaceholderText='Estimated Start'
                                    endDatePlaceholderText='Estimated Finish'
                                    endDateId='endDate1'
                                    startDateId='startDate1'
                                    startDate={this.state.startDate ? this.state.startDate : null}
                                    endDate={this.state.finishDate ? this.state.finishDate : null}
                                    onDatesChange={({ startDate, endDate }) => this.setState({ startDate: startDate, finishDate: endDate })}
                                    focusedInput={this.state.focusedInput ? this.state.focusedInput : null}
                                    onFocusChange={focusedInput => this.setState({ focusedInput })}
                                    showDefaultInputIcon
                                    inputIconPosition="after"
                                />
                            </div>
                        </FormGroup>
                        <div className="budgetAndEffort">
                            <FormGroup bsSize="large">
                                <ControlLabel>Estimated Budget: {this.props.project.projectBudget}</ControlLabel>
                                <FormControl id="projectBudget"
                                    autoFocus
                                    type="text"
                                    pattern="[0-9]*"
                                    value={this.state.estimatedBudget}
                                    onChange={this.handleBudgetChange}
                                    placeholder="Enter new budget"
                                />
                            </FormGroup>

                        </div>


                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <button className="genericButton" onClick={() => this.setState({ confirmCreation: true })}>Edit Project</button>
                </Modal.Footer>
            </div>
        )

    }
    }

    render() {

        let toRender = this.renderProjEditFields()
        if (this.state.confirmCreation) {
            toRender = this.displayConfirmation()
        }


        if (this.props.project.projectManagerEmail === this.AuthService.getProfile().sub || this.props.userProfile === "DIRECTOR") {

            return (
                <div>
                    <span onClick={this.handleShow.bind(this)}> <Glyphicon className="pencil" glyph="glyphicon glyphicon-edit" /></ span>

                    <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
                        {toRender}
                    </Modal>
                </div>
            )
        }


        else {
            return null
        }

    }


}

const mapStateToProps = state => {
    return {
        userProfile: state.authenthication.user.userProfile
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            refreshProjects
        },
        dispatch
    );

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(EditProject);