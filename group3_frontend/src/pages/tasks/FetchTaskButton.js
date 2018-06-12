import React, { Component } from "react";
import MediumButton from '../../components/button/mediumButton'
import MediumButtonSelected from '../../components/button/mediumButtonSelected'
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
        switch (this.props.filter) {
            case 'all':
                return (
                    <table className="table table-title">
                        <thead>
                        <tr>
                            <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButtonSelected text="All Tasks"/> </th></td>
                            <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButton text="On Going"/> </th></td>
                            <td onClick={(e) => this.props.changeToFinished()}><th><MediumButton text="Finished"/> </th></td>
                            <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButton text="Not Started"/> </th></td>
                            <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButton text="Stand By"/> </th></td>


                        </tr>
                        </thead>
                    </table>
                )
            case 'unfinished':
                return (
                    <table className="table table-title">
                        <thead>
                        <tr>
                            <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButton text="All Tasks"/> </th></td>
                            <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButtonSelected text="On Going"/> </th></td>
                            <td onClick={(e) => this.props.changeToFinished()}><th><MediumButton text="Finished"/> </th></td>
                            <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButton text="Not Started"/> </th></td>
                            <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButton text="Stand By"/> </th></td>


                        </tr>
                        </thead>
                    </table>
                )
            case 'finished':
                return (
                    <table className="table table-title">
                        <thead>
                        <tr>
                            <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButton text="All Tasks"/> </th></td>
                            <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButton text="On Going"/> </th></td>
                            <td onClick={(e) => this.props.changeToFinished()}><th><MediumButtonSelected text="Finished"/> </th></td>
                            <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButton text="Not Started"/> </th></td>
                            <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButton text="Stand By"/> </th></td>


                        </tr>
                        </thead>
                    </table>
                )
            case 'withoutCollaborators':
                return (
                    <table className="table table-title">
                        <thead>
                        <tr>
                            <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButton text="All Tasks"/> </th></td>
                            <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButton text="On Going"/> </th></td>
                            <td onClick={(e) => this.props.changeToFinished()}><th><MediumButton text="Finished"/> </th></td>
                            <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButton text="Not Started"/> </th></td>
                            <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButtonSelected text="Stand By"/> </th></td>


                        </tr>
                        </thead>
                    </table>
                )
            case 'notstarted':
                return (
                    <table className="table table-title">
                        <thead>
                        <tr>
                            <td onClick={(e) => this.props.changeToAllTasks()}><th><MediumButton text="All Tasks"/> </th></td>
                            <td onClick={(e) => this.props.changeToOnGoing()}><th><MediumButton text="On Going"/> </th></td>
                            <td onClick={(e) => this.props.changeToFinished()}><th><MediumButton text="Finished"/> </th></td>
                            <td onClick={(e) => this.props.changeToNotStarted()}><th><MediumButtonSelected text="Not Started"/> </th></td>
                            <td onClick={(e) => this.props.changeToStandBy()}><th><MediumButton text="Stand By"/> </th></td>


                        </tr>
                        </thead>
                    </table>
                )
            default:
                return (
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

}

const mapStateToProps = state => { return ({ filter: state.filterReducer.filterType}) }
const mapDispatchToProps = dispatch => bindActionCreators({ changeToFinished, changeToNotStarted, changeToOnGoing, changeToStandBy, changeToAllTasks}, dispatch)
export default connect(mapStateToProps, mapDispatchToProps)(FetchTaskButton);
