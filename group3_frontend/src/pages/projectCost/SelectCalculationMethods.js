import { Component } from 'react';

class SelectCalculationMethods extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (<div>
            <p>This is a test</p>
            <p>"Project ID: " {this.props.project.projectId}</p>
            <p>"Project Cost Calculation Methods: "  {this.props.project.projectAvaliableCalculationMethods}</p>
        </div>);
    }
}
export default SelectCalculationMethods;