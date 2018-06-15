import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import UpdateReport from './UpdateReport';
import './ReportsStyle.css';
import './UpdateReport.css';

class Reports extends Component {
    constructor(props) {
        super(props);
        this.state = {
            reports: [],
            project: {},
            task: {},
            taskCollab: {}
        };
        this.AuthService = new AuthService();
    }

    componentDidMount() {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                project: responseData,
                projectManager: responseData.projectManager.name,
                message: responseData.message
            });
        });

        this.chooseWhichToRender();
    }

    chooseWhichToRender = () => {
        if (this.state.projectManager === this.AuthService.getUserId()) {
            this.refreshPage();
        } else {
            this.reportsFromCollaborator();
        }
    };

    refreshPage = () => {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/${
                this.props.match.params.taskID
            }/reports/`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                reports: responseData,
                message: responseData.error
            });
        });
    };

    reportsFromCollaborator() {
        this.AuthService.fetch(
            `/projects/${this.props.match.params.projectID}/tasks/${
                this.props.match.params.taskID
            }/reports/users/${this.AuthService.getUserId()}`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                reports: responseData,
                message: responseData.error
            });
        });
    }

    renderReports() {
        return this.state.reports.map(reportItem => {
            return (
                <tr className="line">
                    <td className="tdteste">{reportItem.reportDbId}</td>
                    <td>{reportItem.cost}</td>
                    <td>{reportItem.reportedTime}</td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {reportItem.firstDateOfReport}
                        </Moment>
                    </td>
                    <td>
                        <Moment format="YYYY/MM/DD">
                            {reportItem.dateOfUpdate}
                        </Moment>
                    </td>
                    <td>
                        <UpdateReport
                            taskId={this.props.match.params.taskID}
                            projId={this.props.match.params.projectID}
                            reportId={reportItem.reportDbId}
                            onSubmit={this.chooseWhichToRender}
                        />
                    </td>
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message="Unauthorized"/>;
        } else {
            return (
                <div className=" table-striped">
                    <h3>
                        <b>Reports for {this.props.match.params.taskID} </b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Report ID</th>
                                <th>Cost per Effort</th>
                                <th>Time</th>
                                <th>Date of Creation</th>
                                <th>Date of Update</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderReports()}</tbody>
                    </table>
                </div>
            );
        }
    }
}

export default Reports;
