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
                        <span
                            className={
                                'status-project-icon ' +
                                (this.props.project.projectActive
                                    ? 'active'
                                    : '')
                            }
                        />
                    </td>
                    <td>{this.props.project.projectName}</td>
                    <td id="project-manager-badge-cell">
                        {this.getManagerOptions()}
                    </td>
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
                        'project-details project-row ' +
                        (this.state.isOpen ? 'open' : 'hide')
                    }
                >
                    <td> </td>
                    <td colSpan="2">
                        <div>
                            <b>Description:&nbsp;</b>
                            <hr />
                            {this.props.project.projectDescription}
                            <br />
                            <b>Project Manager:&nbsp;</b>
                            {this.props.project.projectManagerName}
                            <br />
                            <b>Start date:&nbsp;</b>
                            {this.props.project.projectStartDate}
                            <br />
                            <b>Finish date:&nbsp;</b>
                            {this.props.project.projectFinishDate}
                            <br />
                        </div>
                    </td>
                    <td>
                        <div>
                            <b>Budget:&nbsp;</b>
                            {this.props.project.projectBudget}
                            <br /> <b>Calculation method:&nbsp;</b>
                            {this.props.project.projectCalculationMethod}
                            <br />
                            {this.props.project.button}
                            <br />
                        </div>
                    </td>
                    <td>Buttons here!!!! </td>
                </tr>
            </Fragment>
        );
    }
}

export default ProjectsTableRow;
