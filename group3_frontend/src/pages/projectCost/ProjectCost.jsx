import React, { Component } from 'react';
import './ProjectCost.css'
import { Modal, FormGroup, ControlLabel, FormControl, Form } from 'react-bootstrap'
import ProjectCostWidget from './ProjectCostWidget';
import Chart from '../../components/chart/Chart.jsx'
import AuthService from './../loginPage/AuthService';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { changeCalculationMethod } from '../../actions/projectActions'

class ProjectCost extends Component {

    constructor() {
        super()
        this.state = {
            show: false,
            projectCost: '',
            projectCalculationMethod: ''
        }
        this.AuthService = new AuthService()
    }

    handleShow() {
        this.setState({ show: true });
    }

    handleClose() {
        this.setState({ show: false, confirmCreation: false });
    }

    handleChange = (event) => {

        const projectDTOData = {
            calculationMethod: event.target.value,
        };

        this.props.changeCalculationMethod(this.props.project.projectId, projectDTOData, this.AuthService.getUserId())
    }

    loadAvailableMethods() {
        let calculationMethods = this.props.project.projectAvaliableCalculationMethods.split(',')
        return calculationMethods.map((option, index) => {
            return (
                <option key={index} value={option}>
                    {option}
                </option>
            );
        });
    }


    chooseCalculationMethod() {
        return (
            <div styles={{ width: '600px' }} >
                <Form inline>
                    <FormGroup controlId="formInlineName">
                        <ControlLabel>
                            Change Calculation Method:
        </ControlLabel>
                        <FormControl
                            value={this.state.calculationMethod}
                            onChange={this.handleChange}
                            componentClass="select"
                            placeholder="select"
                        >
                            <option >
                                Select your option
            </option>
                            {this.loadAvailableMethods()}
                        </FormControl>
                    </FormGroup>
                </Form>
            </ div>
        )
    }

    render() {
        return (
            <div>
                <a className="items-menu"> <button className="itemsButton" onClick={this.handleShow.bind(this)}>Project Cost</button> </a>

                <Modal show={this.state.show} onHide={this.handleClose.bind(this)}>
                    <Modal.Header closeButton>
                        <Modal.Title>Project Cost</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p><b>Project ID:</b>{this.props.project.projectId}</p>
                        <p><b>Project Name:</b>{this.props.project.projectName}</p>
                        <p><b>Available Cost Calculation Methods:</b>{this.props.project.projectAvaliableCalculationMethods}</p>
                        {this.chooseCalculationMethod()}
                        <span align="center"><Chart budget={this.props.project.projectBudget} cost={this.props.projectCost} /></span>
                        <center><ProjectCostWidget /> </center>
                    </Modal.Body>
                </Modal>
            </div>
        )
    }

}

const mapStateToProps = state => {
    return {
        projectCost: state.projects.projectCost
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            changeCalculationMethod
        },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ProjectCost);
