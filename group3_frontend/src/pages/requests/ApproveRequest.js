import React, { Component } from 'react';
import AuthService from '../loginPage/AuthService';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';

import { getAllRequests, getOpenedRequests, getClosedRequests, searchList } from '../../actions/requestsActions'
import { Glyphicon } from 'react-bootstrap';
import {toastr} from "react-redux-toastr";




class ApproveRequest extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
    }

    handleClick = () => {

            console.log(this.props.projectId)
            console.log(this.props.taskId)
            console.log(this.props.requestId)
        this.AuthService.fetch(
            `/projects/${this.props.projectId}/tasks/${this.props.taskId}/requests/${this.props.requestId}/approval`,
            {
                method: 'PUT'
            }
        ).then(responseData => {
            console.log("teste response")
            console.log(responseData)
            if(responseData.type === 'ASSIGNMENT'){
            toastr.success('Request Approved! Collaborator was added to task');

            this.updateRequests();
            }
            else if (responseData.type === 'REMOVAL'){
                toastr.success('Request Approved! Collaborator was removed from task');

            this.updateRequests();
            }
        }).catch((error) => {
            toastr.error('Something went wrong! Request not approved');
        });

        
        
    };

    updateRequests() {

        if (this.props.filter === 'allRequests')
            this.props.getAllRequests(this.props.projectId);
        else if (this.props.filter === 'openedRequests')
            this.props.getOpenedRequests(this.props.projectId);
        else if (this.props.filter === 'closedRequests')
            this.props.getClosedRequests(this.props.projectId);
        else
            this.props.getAllRequests(this.props.projectId);
    }

    render() {
        return (
            <div>
                 <span onClick={this.handleClick.bind(this)}> <Glyphicon className="pencil" glyph="ok" /></ span>
                 
                    {/* <Glyphicon className="ok-button" glyph="ok" onClick={this.handleClick.bind(this)}/> */}
            </div>
        );
    }
}

const mapStateToProps = state => { 
    return { 
    filter: state.requestsFilter.filterType,
    itemIsLoading: state.requests.itemIsLoading,
    allRequests: state.requests.allRequests,
    openedRequests: state.requests.openedRequests,
    closedRequests: state.requests.closedRequests,
    searchList: state.requests.searchList,
    error: state.requests.error }
 }
const mapDispatchToProps = dispatch => bindActionCreators({ 
    getAllRequests, getOpenedRequests, getClosedRequests, searchList },
     dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(ApproveRequest);

