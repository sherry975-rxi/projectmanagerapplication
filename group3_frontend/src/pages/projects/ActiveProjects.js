import React, { Component } from "react";
import axios from "axios";
import "./ActiveProjects.css";
import { Link } from "react-router-dom";
import AuthService from './../loginPage/AuthService';
import Error from './../../components/error/error';

class ActiveProjects extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            message: ""
        };

        this.AuthService = new AuthService()
    }


    async componentDidMount() {
        this.AuthService.fetch("/projects/active", {
            method: "get"
        })
            .then(responseData => {
                this.setState({
                    projects: responseData,
                    message: responseData.status
                });
            });
    }



    renderProjects() {
        return this.state.projects.map(projectItem => {
            return (
                <tr className="line">
                    <td>{projectItem.projectId}</td>
                    <td>{projectItem.name}</td>
                    <td>{projectItem.description}</td>
                    <td>{projectItem.projectManager.name}</td>
                    <td>{projectItem.projectManager.email}</td>
                    <Link
                        to={"/projectdetails/" + projectItem.projectId}
                        activeClassName="active"
                    >
                        <button className="btn btn-primary">Details</button>
                    </Link>{" "}
                    &nbsp;
                    {/* <a href="/selectprojectcostcalculation" className="btn btn-warning" role="button">Cost Method</a> */}
                    {/* <a href="/projectcost" >
                <button className="btn btn-info" >Cost</button></a> &nbsp; */}
                </tr>
            );
        });
    }

    render() {
        if (this.state.message != null) {
            return <Error message={this.state.message} />
        }
        else {
            return (
                <div className="ActiveProjects">
                    <h3>
                        <b>Active Projects</b>
                    </h3>
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Project ID</th>
                                <th>Name</th>
                                <th>Description Date</th>
                                <th>Project Manager Name</th>
                                <th>Project Manager Email</th>
                            </tr>
                        </thead>
                        <tbody>{this.renderProjects()}</tbody>
                    </table>
                </div>
            );
        }
    }
}

export default ActiveProjects;
