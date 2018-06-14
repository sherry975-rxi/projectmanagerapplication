import React, { Component } from "react";
import Moment from 'react-moment';
import Error from './../../components/error/error';
import MarkTaskAsFinished from "./MarkTaskAsFinished";
import CreateReport from "../reports/CreateReport";
import Reports from "../reports/Reports"
import { Link } from "react-router-dom";
import MediumButton from './../../components/button/mediumButton';
import FetchTaskButton from '../tasks/FetchTaskButton'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import AuthService from './../loginPage/AuthService';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx'
import { handleTaskHeaders } from '../../components/utils/handleList'
import { updateAllTasks, updateFinishedTasks, updateNotStartedTasks, updateStandByTasks, updateOngoingTasks } from './../../actions/projectTasksActions';



class ProjectTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {},
            externalData: null
        };
        this.AuthService = new AuthService()
        this.renderTasks = this.renderTasks.bind(this);
    }

    //TODO: Add sort by ascending or descending order to these tables


    renderTasks() {

        switch (this.props.filter) {
            case ("all"):
                return (<AccordionMenu list={this.props.allTasks} />);
            case ("unfinished"):
                return (<AccordionMenu list={this.props.ongoingTasks} />);
            case ("finished"):
                return (<AccordionMenu list={this.props.finishedTasks} />);
            case ("withoutCollaborators"):
                return (<AccordionMenu list={this.props.standByTasks} />);
            case ("notstarted"):
                return (<AccordionMenu list={this.props.notStartedTasks} />);
            default: {
                this.setState({
                    message: "ERROR"
                })
            }

        }
    }


    render() {
        
        if (this.state.message != null) {
            return (<Error message={this.state.message} />)
        }
        else {
            return (
                <div>
                    <FetchTaskButton projectID={this.props.match.params.projectID} />
                    {this.renderTasks()}
                </div>
            )
        }
    }
}

const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType, finishedTasks: state.projectTasks.finishedTasks, ongoingTasks: state.projectTasks.ongoingTasks, standByTasks: state.projectTasks.wihoutCollab, notStartedTasks: state.projectTasks.notStartedTasks, allTasks: state.projectTasks.allTasks }) }

export default connect(mapStateToProps, null)(ProjectTasks);

