import React, { Component, Fragment } from 'react';

class ProjectsTableRow extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Fragment>
                {console.log(this.props.project)}
                <tr className="rowAccordian">
                    <td>{this.props.project.projectActive}</td>
                    <td>{this.props.project.projectName}</td>
                    <td>{this.props.project.projectStatusName}</td>
                    <td>botton triangle</td>
                </tr>
                <tr className="rowAccordian collapsable">
                    <td colSpan="2">
                        Description:
                        {this.props.project.projectDescription}
                        <br />Project Manager:
                        {this.props.project.projectManagerName}
                        <br />Start date:
                        {this.props.project.projectStartDate}
                        <br />Finish date:
                        {this.props.project.projectFinishDate}
                        <br />
                    </td>
                    <td colSpan="2">
                        Budget: {this.props.project.projectBudget}
                        <br /> Calculation method:
                        {this.props.project.projectCalculationMethod}
                        <br /> Cost:
                        {this.props.project.projectCost}
                        <br />
                        {this.props.project.button}
                        <br />
                    </td>
                </tr>
            </Fragment>
        );
    }
}

export default ProjectsTableRow;
