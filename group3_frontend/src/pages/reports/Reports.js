import React, { Component } from "react";
import decode from 'jwt-decode';
import AuthService from '../loginPage/AuthService';
import Moment from 'react-moment';
import Error from './../../components/error/error';
import { Link } from "react-router-dom";

class Reports extends Component {
    constructor(props){
        super(props);
        this.match;
        this.state={
            reports: [],
            task:{}
        };
        
        this.AuthService = new AuthService()
}

async componentDidMount() {
    this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/tasks/${this.props.match.params.taskID}/reports/`, { 
        method: "get" })
        .then(responseData => {
            console.log(responseData)
            this.setState({
                reports: responseData,
                message: responseData.error
            });
        });
}

renderReports(){

    
    return this.state.reports.map(reportItem => {
        return (
            <tr className="line">
                <td>{reportItem.reportId}</td>
                <td>{reportItem.cost}</td>
                <td>{reportItem.reportedTime}</td>
                <td><Moment format="YYYY/MM/DD">
                    {reportItem.dateUpdate}
                </Moment></td>
                <td><Link 
                        to={"/projects/" + reportItem.project + "/tasks/" + reportItem.taskID + "/updatereport" }
                        activeClassName="active"
                    >
                        <button className="btn btn-primary">
                            Update report
                    </button>
                </Link>{" "}</td>
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
                            <th>Cost</th>
                            <th>Time</th>
                            <th>Date of update</th>
                        </tr>
                    </thead>
                    <tbody>{this.renderReports()}</tbody>
                </table>
               
            </div>)
    }
}
}

    export default Reports;




