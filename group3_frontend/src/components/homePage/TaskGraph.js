import React, { Component } from 'react';
import './Homepage.css';
import AuthService from '../../pages/loginPage/AuthService.js';
import Moment from 'react-moment';
import ProgBar from './ProgBar';
import momentus from 'moment';
import loading from './images/loading.gif';

class TaskGraph extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tasks: [],
            project: {},
            percent: 0,
            actualDate: new Date(),
            userID: '',
            hasFetched: false
        };
        this.AuthService = new AuthService();
    }

    async componentDidMount() {
        this.AuthService.fetch(
            `/users/${this.AuthService.getUserId()}/tasks/sortedbydeadline`,
            {
                method: 'get'
            }
        ).then(responseData => {
            this.setState({
                tasks: responseData,
                message: responseData.error,
                hasFetched: true
            });
        }).catch(err => {
            this.setState({
                tasks: []
            });
        });
        
    }

    

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
            if (this.state.tasks.length > 0) {
                return this.state.tasks.map((taskItem, index) => {
                    const today = momentus(this.state.actualDate);
                    const taskDeadline = momentus(taskItem.taskDeadline);
                    var difference = taskDeadline.diff(today, 'days');
                    var deadlineIsOver = difference;

                    if (difference < 0) {
                        difference = 100;
                        deadlineIsOver = '0';
                    }

                    console.log("TAsK ")

                    console.log(difference)

                    return (
                        <div className="GraphContainer" key={index}>
                            <ProgBar limit={30} />
                            <table>
                                <tbody>
                                    <tr className="line">
                                        <td className="tdGraphStyle">
                                            Task Deadline:
                                            <Moment format="YYYY/MM/DD">
                                                {taskItem.taskDeadline}
                                            </Moment>
                                        </td>
                                        <td className="tdGraphStyleEnd">
                                            Days left until deadline:
                                            {deadlineIsOver}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="tdGraphStyle">
                                            Description: {taskItem.description}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td className="tdGraphStyle">
                                            Project ID: {taskItem.project.projectId}
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
                        <h2>You don't have any active Task</h2>
                    </div>
                );
            }
        }
    }
}

export default TaskGraph;
