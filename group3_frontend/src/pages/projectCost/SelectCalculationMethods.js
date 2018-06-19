import React, { Component } from 'react';
import {
    FormGroup,
    ControlLabel,
    Button,
    Checkbox
} from 'react-bootstrap';

class SelectCalculationMethods extends Component {
    constructor(props) {
        super(props);
        this.state = {
            selectedMethods: this.props.project.projectAvaliableCalculationMethods.split(','),
        }
    }

    validateForm() {
        return this.state.selectedMethods.length;
    }

    handleChange(event) {
        const selectedMethods = this.state.selectedMethods;

        if(selectedMethods.includes(event.target.value)) {
            selectedMethods.remove(event.target.value);
        } else {
            selectedMethods.add(event.target.value);
        }
        this.setState({
            selectedMethods: selectedMethods
        })
    }

    render() {
        console.log(this.state.selectedMethods);
        return (<div>
            <p>This is a test</p>
            <p>"Project ID: " {this.props.project.projectId}</p>
            <p>"Project Cost Calculation Methods: "  {this.props.project.projectAvaliableCalculationMethods}</p>


            <form onSubmit>

                <FormGroup controlId="selectedMethods">
                    <ControlLabel className="formTitle"><b>Available Calculation Methods</b></ControlLabel>

                    <Checkbox value="CI" checked={this.state.selectedMethods.includes("CI")} onChange={this.handleChange}>
                        Cost Initial
                    </Checkbox>
                    <Checkbox value="CF" checked={this.state.selectedMethods.includes("CF")} onChange={this.handleChange}>
                        Cost Final
                    </Checkbox>{' '}
                    <Checkbox value="CM" checked={this.state.selectedMethods.includes("CM")} onChange={this.handleChange}>
                        Cost Initial
                    </Checkbox>

                </FormGroup>


                <Button block className="btn btn-primary" disabled={!this.validateForm()} type="submit" >
                    Update
                </Button>
            </form>
        </div>);
    }
}
export default SelectCalculationMethods;