import React, { Component } from 'react';
import './Homepage.css';
import Weather from './Weather';
import TaskGraph from './TaskGraph.js';
import ProjGraph from './ProjectGraph';
import Calendar from './Calendar';
import AuthService from '../../pages/loginPage/AuthService';
import { getTotalTimeSpentOnTasksLastMonth } from '../../actions/metaActions';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import StatsWidgetsContainer from '../StatsWidgetsContainer/StatsWidgetsContainer';

class HomepageCollaborator extends Component {
    constructor(props) {
        super(props);
        this.AuthService = new AuthService();
        this.props.getTotalTimeSpentOnTasksLastMonth(
            this.AuthService.getUserId()
        );
    }

    render() {
        return (
            <div>
                <table className="HomepageContainer">
                    <tbody className="HomeTable">
                        <tr>
                            <td className="HomeTableTDTopLeft">
                                <Calendar />
                            </td>
                            <td className="HomeTableTDLeft">
                                <Weather className="teste" />
                            </td>
                        </tr>
                        <tr>
                            <td className="HomeTableTDBottomLeft">
                                <div className="ProjUpperContainer">
                                    <h1 className="GraphTitle">
                                        Active Projects
                                    </h1>
                                    <ProjGraph />
                                </div>
                            </td>
                            <td className="HomeTableTDBottomLeft">
                                <div className="TaskGraphUpperContainer">
                                    <h1 className="GraphTitle">
                                        Tasks Deadline
                                    </h1>
                                    <TaskGraph />
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <StatsWidgetsContainer data={this.props.widgetData} />
            </div>
        );
    }
}

export const mapStateToProps = state => {
    const totalTimeSpentOnTasksLastMonth = {
        title: 'Total time spent on tasks last month',
        value: state.meta.totalTimeSpentOnTasksLastMonth,
        unit: 'hours'
    };
    const widgetData = [];
    widgetData.push(totalTimeSpentOnTasksLastMonth);
    return { widgetData };
};

export const mapDispatchToProps = dispatch => {
    return bindActionCreators({ getTotalTimeSpentOnTasksLastMonth }, dispatch);
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomepageCollaborator);
