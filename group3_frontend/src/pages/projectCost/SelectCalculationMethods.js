import React, { Component } from 'react';
import {
    Dropdown,
    FormGroup,
    Button,
    Checkbox
} from 'react-bootstrap';
import AuthService from "../loginPage/AuthService";
import { toastr } from "react-redux-toastr";
import { getActiveProjects } from "../../actions/projectActions";
import { connect } from "react-redux";
import { bindActionCreators } from "redux";


class SelectCalculationMethods extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedMethods: this.props.project.projectAvaliableCalculationMethods.split(','),
            submission: false,
            hideSuccessInfo: 'hide-code'
        }

        this.handleChange = this.handleChange.bind(this);
        this.AuthService = new AuthService();
    }

    validateForm() {
        return this.state.selectedMethods.length;
    }

    validateArray(calculationMethod) {
        return calculationMethod === "CI" || calculationMethod === "CF" || calculationMethod === "CM";
    }

    handleChange(event) {

        var selectedMethods = this.state.selectedMethods;

        if (selectedMethods.includes(event.target.value)) {
            var index = selectedMethods.indexOf(event.target.value)
            selectedMethods.splice(index, 1);
        } else {
            selectedMethods.push(event.target.value);
        }
        selectedMethods.filter(this.validateArray);
        this.setState({
            selectedMethods: selectedMethods
        })
    }

    handleSubmit = event => {
        event.preventDefault();

        const selectedMethods = this.state.selectedMethods;


        const projectDTO = {
            availableCalculationMethods: selectedMethods.join(','),
            calculationMethod: selectedMethods[0]
        }

        this.AuthService.fetch(`/projects/${this.props.project.projectId}`, {
            body: JSON.stringify(projectDTO),
            method: 'PATCH'
        })
            .then(responseData => {
                toastr.success('Available Calculation Methods Changed!');
                this.props.getActiveProjects(this.AuthService.getUserId());
            })
            .catch(err => {
                toastr.error('An error occurred!');
            });

    }

    dropdownToggle(newValue) {
        if (this._forceOpen) {
            this.setState({ menuOpen: true });
            this._forceOpen = false;
        } else {
            this.setState({ menuOpen: newValue });
        }
    }


    renderDropdownButton(title, i) {

        return (
            <Dropdown

                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
                open={this.state.menuOpen}
                onToggle={val => this.dropdownToggle(val)}
            >
                <Dropdown.Toggle className="dependencyGenericButton">{title}</Dropdown.Toggle>
                <Dropdown.Menu>
                    <form onSubmit={this.handleSubmit} className="availableMethodsDropDown">

                        <Checkbox value="CI" checked={this.state.selectedMethods.includes("CI")} onChange={this.handleChange}>
                            Cost Initial
                                </Checkbox>{' '}
                        <Checkbox value="CF" checked={this.state.selectedMethods.includes("CF")} onChange={this.handleChange}>
                            Cost Final
                                </Checkbox>{' '}
                        <Checkbox value="CM" checked={this.state.selectedMethods.includes("CM")} onChange={this.handleChange}>
                            Cost Average
                                </Checkbox>

                        <button block disabled={!this.validateForm()} type="submit" className="genericButton">
                            Update
                            </button>
                    </form>
                </Dropdown.Menu>
            </Dropdown>

        );
    }

    render() {
        return this.renderDropdownButton('Select Cost Methods', 0);
    }

}
export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ getActiveProjects }, dispatch);
};

export default connect(
    null,
    mapDispatchToProps
)(SelectCalculationMethods);