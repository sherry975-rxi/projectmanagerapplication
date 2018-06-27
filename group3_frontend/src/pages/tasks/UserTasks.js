import React, { Component } from 'react';
import './AddTask';
import './MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import { Redirect } from 'react-router-dom';
import UserTasksFilter from '../tasks/UserTasksFilter';
import AccordionMenu from '../../components/accordianMenuTasks/AccordionMenuTasks.jsx';
import { connect } from 'react-redux';
import LoadingComponent from './../../components/loading/LoadingComponent';
import {
    searchList
} from './../../actions/userTasksActions';



class UserTasks extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
        this.renderTasks = this.renderTasks.bind(this);

    }

    //TODO: Add sort by ascending or descending order to these tables



   
    renderTasks() {


        if (this.props.tasksLoading) {
            return (<LoadingComponent />)
        }

        else if (this.props.error) {
            return <Redirect to="/login" />;
        }

        else if (this.props.filter === 'myAll'){

            return <AccordionMenu list={this.props.myAllTasks} />;
        }

        else if (this.props.filter === 'myFinished'){
            return <AccordionMenu list={this.props.myFinishedTasks} />;
        }
        else if (this.props.filter === 'myUnfinished'){
            return <AccordionMenu list={this.props.myOngoingTasks} />;
        }
        else if (this.props.filter === 'lastMonthFinished'){
            return <AccordionMenu list={this.props.lastMonthFinishedTasks} />
        }
        else if (this.props.filter === 'searchList'){
            return <AccordionMenu list={this.props.updatedList} />
        }
        else {
            return <AccordionMenu list={this.props.myAllTasks} />;
        }
    }


    render() {
      
            return (
                <div className=" table-striped">
                                        <h2>My Tasks</h2>

                    <UserTasksFilter userID={this.AuthService.getUserId()} />
                        <h3>
                        </h3>
                        &nbsp;
                        {this.renderTasks()}
                                            
                </div>
            );
        }
    }


const mapStateToProps = state => {
    return {
        filter: state.filterReducer.filterType,
        myFinishedTasks: state.userTasks.myFinishedTasks,
        myOngoingTasks: state.userTasks.myOngoingTasks,
        myAllTasks: state.userTasks.myAllTasks,
        lastMonthFinishedTasks: state.userTasks.lastMonthFinishedTasks,
        updatedList: state.userTasks.updatedList
    };
};

export default connect(
    mapStateToProps,
    null
)(UserTasks);