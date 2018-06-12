import React, { Component } from "react";
import MediumButton from '../../../src/components/button/mediumButton'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {changeToFinished, changeToOnGoing, changeToStandBy, changeToNotStarted, changeToAllTasks}  from '../../actions/filterActions';

class FetchTaskButton extends Component{


    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'        
        };
    }


    render(){
        return(
            <table className="table table-title">
            <thead>
                <tr>
                     <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButton text="All Tasks"/> </th></td>
                     <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButton text="On Going"/> </th></td>
                     <td onClick={(e) => this.props.changeToFinished()}><th><MediumButton text="Finished"/> </th></td>
                     <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButton text="Not Started"/> </th></td>
                     <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButton text="Stand By"/> </th></td>


                </tr>
            </thead>
        </table>
        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ changeToFinished, changeToNotStarted, changeToOnGoing, changeToStandBy, changeToAllTasks}, dispatch)
export default connect(null, mapDispatchToProps)(FetchTaskButton);
