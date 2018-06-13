import React, { Component, Fragment } from 'react';
import { Glyphicon } from 'react-bootstrap';
import AuthService from '../../pages/loginPage/AuthService';

class ProjectsTableRow extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();

        this.state = { isOpen: false };
    }

    handleRotate = () => {
        this.setState({ isOpen: !this.state.isOpen });
    };

    getManagerOptions() {
        if (
            this.props.project.projectManagerEmail ==
            this.AuthService.getProfile().sub
        ) {
            return <span className="project-badge">PM</span>;
        } else return <div> </div>;
    }

    render() {
        return (
            <Fragment>
                <tr className="project-row">
                    <td>
                        <span className={'status-project-icon'} />
                    </td>
                    <td>{this.props.project.projectName}</td>
                    <td>{this.getManagerOptions()}</td>
                    <td>{this.props.project.projectStatusName}</td>
                    <td className="action-buttons-cell">
                        <span
                            onClick={this.handleRotate}
                            className="open-project-details-button"
                        >
                            <Glyphicon
                                glyph="triangle-right"
                                className={this.state.isOpen ? 'rotate' : ''}
                            />
                        </span>
                    </td>
                </tr>
                <tr
                    className={
                        'project-row ' + (this.state.isOpen ? 'open' : 'hide')
                    }
                >
                    <td colSpan="2">
                        <div>
                            Description:
                            {this.props.project.projectDescription}
                            <br />Project Manager:
                            {this.props.project.projectManagerName}
                            <br />Start date:
                            {this.props.project.projectStartDate}
                            <br />Finish date:
                            {this.props.project.projectFinishDate}
                            <br />
                        </div>
                    </td>
                    <td colSpan="2">
                        <div>
                            Budget: {this.props.project.projectBudget}
                            <br /> Calculation method:
                            {this.props.project.projectCalculationMethod}
                            <br /> Cost:
                            {this.props.project.projectCost}
                            <br />
                            {this.props.project.button}
                            <br />
                        </div>
                    </td>
                </tr>
            </Fragment>
        );
    }
}

export default ProjectsTableRow;
