import React, { Component } from "react";
import decode from 'jwt-decode';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import { Link } from "react-router-dom";
import CreateReport from './CreateReport';
import UpdateReport from './UpdateReport';
import "./ReportsStyle.css"
import "./UpdateReport.css"


class Reports extends Component {
    constructor(props){
        super(props);
        this.match;
        this.state={
            reports: [],
            task:{},
            taskCollab: {}
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService()
        
}


async componentDidMount() {
    this.refreshPage();
}

async refreshPage() {
    this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/tasks/${this.props.match.params.taskID}/reports/`, {
        method: "get" })
        .then(responseData => {
            console.log(responseData)
            this.setState({
                reports: responseData,
                message: responseData.error
            });
        })
}



renderReports(){
    
    return this.state.reports.map(reportItem => {        

        return (          
            <tr className="line">
                <td className="tdteste">{reportItem.reportDbId}</td>
                <td>{reportItem.cost}</td>
                <td>{reportItem.reportedTime}</td>
                <td><Moment format="YYYY/MM/DD">
                    {reportItem.firstDateOfReport}
                </Moment></td>
                <td><Moment format="YYYY/MM/DD">
                    {reportItem.dateOfUpdate}
                </Moment></td>
                <td><UpdateReport taskId={this.props.match.params.taskID} projId={this.props.match.params.projectID} reportId={reportItem.reportDbId} onSubmit={this.refreshPage}/></td>

            </tr>
        );
    });

}



render() {

    if (this.state.message != null) {
        return (<Error message={this.state.message} />)
    }
    else {
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
               
            </div>)
    }
}
}

    export default Reports;




