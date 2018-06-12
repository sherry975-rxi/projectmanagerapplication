import React, { Component, Fragment } from 'react';
import { Glyphicon, Button, PanelGroup, Panel } from 'react-bootstrap';

class ProjectsTableRow extends Component {
    state = { isOpen: false };

    handleRotate = () => {
        this.setState({ isOpen: !this.state.isOpen });
    };

    render() {
        return (
            <Fragment>
                <tr className="project-row">
                    <td>
                        <span className={'status-project-icon'} />
                    </td>
                    <td>{this.props.project.projectName}</td>
                    <td>{this.props.project.projectStatusName}</td>
                    <td>
                        <Button onClick={this.handleRotate}>
                            <Glyphicon
                                glyph="triangle-right"
                                className={this.state.isOpen ? 'rotate' : ''}
                            />
                        </Button>
                    </td>
                </tr>
                <tr
                    className={
                        'project-row ' + (this.state.isOpen ? 'open' : 'hide')
                    }
                >
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
