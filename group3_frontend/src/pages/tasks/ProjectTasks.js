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



class ProjectTasks extends Component {
    constructor(props) {
        super(props);
        this.match;
        this.state = {
            tasks: [],
            project: {},
            externalData: null
        };

        this.refreshPage = this.refreshPage.bind(this);
        this.AuthService = new AuthService()
    }

    //TODO: Add sort by ascending or descending order to these tables

    componentDidMount() {
        this.refreshPage();
    }

    componentDidUpdate(prevProps) {
        if (prevProps.filter !== this.props.filter) {
          this.refreshPage();
        }
      }

    refreshPage() {

        this.AuthService.fetch(`/projects/${this.props.match.params.projectID}/tasks/${this.props.filter}`, { method: 'get' })
            .then((responseData) => {
                console.log(responseData);
                this.setState({
                    tasks: responseData,
                    message: responseData.error
                });
            })

    }


    render() {

        if (this.state.message != null) {
            return (<Error message={this.state.message} />)
        }
        else {
            return (
                 <div>
                   {console.log("AAAAA")}
                    {console.log(this.props.filter)}
                    <FetchTaskButton />
                  <AccordionMenu list={this.state.tasks} />
                </div>

            )
        }
    }
}

const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType}) }
export default connect(mapStateToProps, null)(ProjectTasks);

