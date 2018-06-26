import React, { Component } from 'react';
import './Homepage.css';
import AuthService from '../../pages/loginPage/AuthService.js';
import ProgBarCircle from './ProgBarCircle';
import momentus from 'moment';
import loading from './images/loading.gif';

class ProjectGraphDirector extends Component {
    constructor(props) {
        super(props);
        this.state = {
            projects: [],
            percent: 0,
            actualDate: new Date(),
            hasFetched: false
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch(
            `/projects/active`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                projects: responseData,
                message: responseData.error,
                hasFetched: true
            });
        }).catch(err => {
            this.setState({
                projects: []
            });
        });
        };

    render() {
        if (this.state.hasFetched === false) {
            return (
                <div className="loadings">
                    <img
                        className="loadingGifs"
                        with="300"
                        height="200"
                        src={loading}
                        alt="logoImage"
                    />
                </div>
            );
        } else {
            if (this.state.projects.length > 0) {
                return this.state.projects.map((projectItem, index) => {
                    const today = momentus(this.state.actualDate);
                    const projectStartDay = momentus(projectItem.startdate);
                    const projectFinishDay = momentus(projectItem.finishdate);
                    var totalDays = projectFinishDay.diff(
                        projectStartDay,
                        'days'
                    );
                    if (totalDays < 1) {
                        totalDays = 1;
                    }
                    var actualDaysLeft = projectFinishDay.diff(today, 'days');
                    if (actualDaysLeft < 0) {
                        actualDaysLeft = 0;
                    }
                    const difference = actualDaysLeft;
                    var mappedPercent = 100 - (difference * 100) / totalDays;
                    if (mappedPercent > 100) {
                        mappedPercent = 100;
                    }

                    return (
                        
                        <div className="ProjectGraphContainer" key={index}>
                            <div className="ProgBarCircleContainer">
                                <ProgBarCircle  limit={100 - actualDaysLeft} />
                            </div>
                            <table className="ProjectGraphTable">
                                <tbody>
                                    <tr>
                                        <td className="tdGraphStyleLeft">
                                            Project Start Date
                                        </td>
                                        <td className="tdGraphStyleRight">
                                            Project Finish Date
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="tdGraphStyleLeft">
                                            {projectStartDay.format(
                                                'YYYY/MM/D'
                                            )}
                                        </td>
                                        <td className="tdGraphStyleRight">
                                            {projectFinishDay.format(
                                                'YYYY/MM/D'
                                            )}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td> &nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td className="tdGraphStyleLeft">
                                            <h2>Project:</h2>
                                        </td>
                                        <td className="tdGraphStyleRight">
                                            <h2>{projectItem.name}</h2>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="tdGraphStyleLeft">
                                            Project ID: {projectItem.projectId}
                                        </td>
                                        <td className="tdGraphStyleRight">
                                            Number of days left:
                                            {actualDaysLeft}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    );
                });
            } else {
                return (
                    <div className="EmptyTaskContainer">
                        <h2>You don't have any active project</h2>
                    </div>
                );
            }
        }
    }
}

export default ProjectGraphDirector;
